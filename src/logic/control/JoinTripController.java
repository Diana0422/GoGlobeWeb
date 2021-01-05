package logic.control;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import logic.bean.SessionBean;
import logic.bean.TripBean;
import logic.dao.RequestDAO;
import logic.dao.RequestDAOFile;
import logic.dao.TripDAO;
import logic.dao.TripDAOFile;
import logic.dao.UserDAO;
import logic.dao.UserDAOFile;
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
		TripDAO dao = new TripDAOFile();
		List<Trip> trips = dao.getAllTrips();
		List<Trip> filteredTrips = new ArrayList<>();
		
		for (Trip trip: trips) {
			if (trip.getTitle().contains(value)) filteredTrips.add(trip);
		}
		
		/* Convert List<Trip> into List<TripBean> */
		List<TripBean> list = ConversionController.getInstance().convertTripList(filteredTrips);
		
		logStr = "Trip Beans: "+list;
		Logger.getGlobal().info(logStr);
		return list;
	}
	
	public boolean joinTrip(TripBean tripBean, SessionBean session) {
		TripDAO tripDao = new TripDAOFile();
		UserDAO userDao = new UserDAOFile();
		
		// Pick the trip corresponding to the tripBean from persistence
		Trip trip = tripDao.getTrip(tripBean.getTitle());
		
		System.out.println(session.getEmail());
		if (!trip.getOrganizer().getEmail().equals(session.getEmail())) { // only if the user is not the organizer
			// Instantiate a new request
			Request request = new Request();
			request.setSender(userDao.getUser(session.getEmail()));
			request.setReceiver(trip.getOrganizer());
			request.setTarget(trip);
			
			// Save the request in persistence
			RequestDAO reqDao = new  RequestDAOFile();
			if (reqDao.getRequest(request.getTarget().getTitle(), request.getSender().getEmail(), request.getReceiver().getEmail())== null) return reqDao.saveRequest(request);
		} 
		
		return false;
		
	}
}