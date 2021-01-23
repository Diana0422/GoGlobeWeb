package logic.control;

import java.util.List;

import logic.bean.TripBean;
import logic.model.Trip;
import logic.persistence.dao.TripDao;
import logic.persistence.exceptions.DBConnectionException;

public class ProfileController {
	
	private static ProfileController instance;

	
   public static synchronized ProfileController getInstance() {
    	if (instance == null) {
    		instance = new ProfileController();
    	}	    	
    	return instance;
    }
   
   public List<TripBean> getRecentTrips() throws DBConnectionException {
	   List<Trip> trips = TripDao.getInstance().getTrips();
	   return ConversionController.getInstance().convertTripList(trips);
   }
   
	public List<TripBean> getUpcomingTrips() throws DBConnectionException {
	   List<Trip> trips = TripDao.getInstance().getTrips();
	   return ConversionController.getInstance().convertTripList(trips);
   }
	
	public List<TripBean> getMyTrips() throws DBConnectionException {
	   List<Trip> trips = TripDao.getInstance().getTrips();
	   return ConversionController.getInstance().convertTripList(trips);

	}
}
