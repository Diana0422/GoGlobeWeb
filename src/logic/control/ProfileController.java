package logic.control;

import java.util.List;

import logic.bean.TripBean;
import logic.model.Trip;
import logic.persistence.dao.TripDao;

public class ProfileController {
	
	private static ProfileController instance;

	
   public static synchronized ProfileController getInstance() {
    	if (instance == null) {
    		instance = new ProfileController();
    	}	    	
    	return instance;
    }
   
   public List<TripBean> getRecentTrips() {
	   List<Trip> trips = TripDao.getInstance().getTrips();
	   return ConversionController.getInstance().convertTripList(trips);
   }
   
	public List<TripBean> getUpcomingTrips() {
	   List<Trip> trips = TripDao.getInstance().getTrips();
	   return ConversionController.getInstance().convertTripList(trips);
   }
	
	public List<TripBean> getMyTrips() {
	   List<Trip> trips = TripDao.getInstance().getTrips();
	   return ConversionController.getInstance().convertTripList(trips);

	}
}
