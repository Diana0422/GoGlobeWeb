package logic.control;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import logic.bean.SessionBean;
import logic.bean.TripBean;
import logic.persistence.dao.RequestDao;
import logic.persistence.dao.TripDao;
import logic.persistence.dao.UserDaoDB;
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

	public List<TripBean> searchTrips(String value) {
		String logStr = "Search trips by value started.\n";
		Logger.getGlobal().info(logStr);
		List<Trip> trips = TripDao.getInstance().getTrips();
		List<Trip> filteredTrips = new ArrayList<>();
		
		for (Trip trip: trips) {
			if (trip.getTitle().contains(value)) filteredTrips.add(trip);
			
		}
		
		/* Convert List<Trip> into List<TripBean> */
		List<TripBean> list = ConversionController.getInstance().convertTripList(filteredTrips);

		
		logStr = "Trip Beans: "+list;
		Logger.getGlobal().info(logStr);
		for (TripBean bean: list) {
			logStr = "Participants: "+bean.getParticipants();
			Logger.getGlobal().info(logStr);
		}
		return list;
	}
	
	public boolean joinTrip(TripBean tripBean, SessionBean session) {
		
		// Pick the trip corresponding to the tripBean from persistence
		Trip trip = TripDao.getInstance().getTripByTitle(tripBean.getTitle());
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
		
	}
}