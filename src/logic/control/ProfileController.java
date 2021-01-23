package logic.control;

import java.sql.SQLException;
import java.util.List;

import logic.bean.TripBean;
import logic.model.Trip;
import logic.persistence.dao.TripDao;
import logic.persistence.exceptions.DBConnectionException;
import logic.persistence.exceptions.DatabaseException;

public class ProfileController {
	
	private static ProfileController instance;

	
   public static synchronized ProfileController getInstance() {
    	if (instance == null) {
    		instance = new ProfileController();
    	}	    	
    	return instance;
    }
   
   public List<TripBean> getRecentTrips() throws DatabaseException {
	   List<Trip> trips;
	   try {
		   trips = TripDao.getInstance().getTrips();
		   return ConversionController.getInstance().convertTripList(trips);
	   } catch (DBConnectionException | SQLException e) {
		   throw new DatabaseException(e.getMessage(), e.getCause());
	   }
   }
   
	public List<TripBean> getUpcomingTrips() throws DatabaseException {
	   List<Trip> trips;
	   try {
		   trips = TripDao.getInstance().getTrips();
		   return ConversionController.getInstance().convertTripList(trips);
	   } catch (DBConnectionException | SQLException e) {
		   throw new DatabaseException(e.getMessage(), e.getCause());
	   }
	}
	
	public List<TripBean> getMyTrips() throws DatabaseException {
	   List<Trip> trips;
	   try {
		   trips = TripDao.getInstance().getTrips();
		   return ConversionController.getInstance().convertTripList(trips);
	   } catch (DBConnectionException | SQLException e) {
		   throw new DatabaseException(e.getMessage(), e.getCause());
	   }
	}
}
