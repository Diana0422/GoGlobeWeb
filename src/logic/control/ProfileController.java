package logic.control;

import java.util.List;

import logic.bean.TripBean;
import logic.dao.TripDAOFile;
import logic.model.Trip;

public class ProfileController {
	
	private static ProfileController instance;

	
   public static synchronized ProfileController getInstance() {
    	if (instance == null) {
    		instance = new ProfileController();
    	}	    	
    	return instance;
    }
   
   public List<TripBean> getRecentTrips(){
	   TripDAOFile dao = new TripDAOFile();
	   List<Trip> trips = dao.getAllTrips();
	   return ConversionController.getInstance().convertTripList(trips);
   }
   
	public List<TripBean> getUpcomingTrips(){
	   TripDAOFile dao = new TripDAOFile();
	   List<Trip> trips = dao.getAllTrips();
	   return ConversionController.getInstance().convertTripList(trips);
   }
	
	public List<TripBean> getMyTrips(){
	   TripDAOFile dao = new TripDAOFile();
	   List<Trip> trips = dao.getAllTrips();
	   return ConversionController.getInstance().convertTripList(trips);

	}
}
