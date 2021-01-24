package logic.control;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import logic.bean.SessionBean;
import logic.bean.TripBean;
import logic.persistence.dao.RequestDao;
import logic.persistence.dao.TripDao;
import logic.persistence.dao.UserDaoDB;
import logic.persistence.exceptions.DBConnectionException;
import logic.persistence.exceptions.DatabaseException;
import logic.model.Request;
import logic.model.Trip;
 
public class JoinTripController {
	
	private static JoinTripController instance = null;
	
	private JoinTripController() {}
	
	public static JoinTripController getInstance() {
		if (instance == null) {
			instance = new JoinTripController();
		}
		
		
		return instance;
	}

	public List<TripBean> searchTrips(String value) throws DatabaseException {
		String logStr = "Search trips by value started.\n";
		Logger.getGlobal().info(logStr);
		List<Trip> trips;
		List<Trip> filteredTrips = new ArrayList<>();
		try {
			trips = TripDao.getInstance().getTrips();
			for (Trip trip: trips) {
				if (trip.getTitle().toLowerCase().contains(value.toLowerCase())) filteredTrips.add(trip);
				
			}
			/* Convert List<Trip> into List<TripBean> */
			return ConversionController.getInstance().convertTripList(filteredTrips);			
		} catch (DBConnectionException | SQLException e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}

	}
	
	public boolean joinTrip(TripBean tripBean, SessionBean session) throws DatabaseException {
		
		// Pick the trip corresponding to the tripBean from persistence
		Trip trip;
		try {
			trip = TripDao.getInstance().getTripByTitle(tripBean.getTitle());
			
			// Set the organizer
			trip.setOrganizer(UserDaoDB.getInstance().getTripOrganizer(tripBean.getTitle()));
			
			if (!trip.getOrganizer().getEmail().equals(session.getSessionEmail())) { // only if the user is not the organizer
				// Instantiate a new request
				Request request = new Request();
				request.setSender(UserDaoDB.getInstance().get(session.getSessionEmail()));
				request.setReceiver(trip.getOrganizer());
				request.setTarget(trip);
				
				// Save the request in persistence
				if (RequestDao.getInstance().getRequest(request.getTarget().getTitle(), request.getSender().getEmail())== null) return RequestDao.getInstance().save(request);
			} 
			
			return false;
		} catch (DBConnectionException | SQLException e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}
}