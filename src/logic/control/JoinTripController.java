package logic.control;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import logic.bean.TripBean;
import logic.persistence.dao.RequestDao;
import logic.persistence.dao.TripDao;
import logic.persistence.dao.UserDaoDB;
import logic.persistence.exceptions.DBConnectionException;
import logic.persistence.exceptions.DatabaseException;
import logic.model.Request;
import logic.model.Trip;
import logic.model.TripCategory;
import logic.model.User;
import logic.model.utils.Cookie;
import logic.model.utils.converters.BeanConverter;
import logic.model.utils.converters.TripBeanConverter;
 
public class JoinTripController {
	
	private BeanConverter<Trip,TripBean> converter;
	
	public JoinTripController() {
		this.converter = new TripBeanConverter();
	}

	public List<TripBean> searchTrips(String value) throws DatabaseException {
		String logStr = "Search trips by value started.\n";
		Logger.getGlobal().info(logStr);
		List<Trip> filteredTrips = new ArrayList<>();
		try {
			List<Trip> trips = TripDao.getInstance().getSharedTrips();
			if (value == null) return converter.convertToListBean(trips);
			for (Trip trip: trips) {
				if (trip.getTitle().toLowerCase().contains(value.toLowerCase())) filteredTrips.add(trip);
				
			}
			/* Convert List<Trip> into List<TripBean> */
			return converter.convertToListBean(filteredTrips);			
		} catch (DBConnectionException | SQLException e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}

	}
	
	public List<TripBean> getSuggestedTrips(String userEmail) throws DatabaseException {
		// Fetch user from cookie
		TripCategory favourite = null;
		Integer topValue = 0;
		try {
			if (userEmail != null) {
				User logged = Cookie.getInstance().getLoggedUser(userEmail);
				
				// Get user favorite category
				Map<TripCategory, Integer> attitude = logged.getAttitude();
				for (Map.Entry<TripCategory, Integer> entry: attitude.entrySet()) {
					if (topValue <= entry.getValue()) {
						topValue = entry.getValue();
						favourite = entry.getKey();
					}
				}
				
				if (favourite != null && !(TripDao.getInstance().getTripsForCategory(favourite)).isEmpty()) return converter.convertToListBean(TripDao.getInstance().getTripsForCategory(favourite));
			}
			return converter.convertToListBean(TripDao.getInstance().getSharedTrips());
		} catch (SQLException | DBConnectionException e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}
	
	private boolean checkIfUserIsCompliant(String userEmail, String tripTitle) throws DatabaseException {
		Trip trip;
		boolean check = true;
		try {
			trip = TripDao.getInstance().getTripByTitle(tripTitle);
			User appliant = Cookie.getInstance().getLoggedUser(userEmail);
			int userAge = appliant.calculateUserAge();
			
			// Fetch trip organizer from database
			trip.setOrganizer(UserDaoDB.getInstance().getTripOrganizer(tripTitle));
			if (trip.getOrganizer().getEmail().equals(appliant.getEmail())) check = false;
			if (userAge<trip.getMinAge() || userAge>trip.getMaxAge()) check = false;
			return check;
		} catch (DBConnectionException | SQLException e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}
	
	
	public boolean sendRequest(String tripTitle, String appliantEmail) throws DatabaseException {
		if (checkIfUserIsCompliant(appliantEmail, tripTitle)) {
			// Instantiate a new request
			Request request = new Request();
			try {
				request.setTarget(TripDao.getInstance().getTripByTitle(tripTitle));
				request.setAccepted(false);
				User senderUser = Cookie.getInstance().getLoggedUser(appliantEmail);
				User receiverUser;
				if (Cookie.getInstance().getLoggedUser(request.getTarget().getOrganizer().getEmail()) == null) {
					receiverUser = request.getTarget().getOrganizer();
				} else {
					receiverUser = Cookie.getInstance().getLoggedUser(request.getTarget().getOrganizer().getEmail());
				}
				senderUser.addToSentRequests(request);
				receiverUser.addToIncRequests(request);
				
				// Save the request in persistence
				if (RequestDao.getInstance().getRequest(request.getTarget().getTitle(), appliantEmail)== null) return RequestDao.getInstance().save(request, appliantEmail);
			} catch (DBConnectionException | SQLException e) {
				throw new DatabaseException(e.getMessage(), e.getCause());
			}
		}
		return true;
	}
	
	public boolean joinTrip(String tripTitle, String userEmail) throws DatabaseException {
		
		// Pick the trip corresponding to the tripBean from persistence
		Trip trip;
		User appliant;
		try {
			trip = TripDao.getInstance().getTripByTitle(tripTitle);
			appliant = Cookie.getInstance().getLoggedUser(userEmail);
			trip.addParticipant(appliant);
			return TripDao.getInstance().saveParticipant(userEmail, tripTitle);
		} catch (DBConnectionException | SQLException e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}
}