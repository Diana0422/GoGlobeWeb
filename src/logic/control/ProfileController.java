package logic.control;

import java.sql.SQLException;
import java.util.List;

import logic.bean.TripBean;
import logic.bean.UserBean;
import logic.bean.UserStatsBean;
import logic.model.Trip;
import logic.model.User;
import logic.model.UserStats;
import logic.persistence.dao.ReviewDao;
import logic.persistence.dao.TripDao;
import logic.persistence.dao.UserDaoDB;
import logic.persistence.dao.UserStatsDao;
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
	
	public UserBean getProfileUser(String userEmail) throws DatabaseException {
		try {
			User user = UserDaoDB.getInstance().get(userEmail);
			UserBean bean = new UserBean();
			bean.setEmail(user.getEmail());
			bean.setAge(user.calculateUserAge());
			bean.setBio(user.getBio());
			bean.setName(user.getName());
			bean.setSurname(user.getSurname());
			bean.setPoints(user.getStats().getPoints());
			UserStats stats = UserStatsDao.getInstance().getUserStats(userEmail);
			UserStatsBean statsBean = new UserStatsBean();
			statsBean.setOrgRating(stats.getOrganizerRating());
			statsBean.setTravRating(stats.getTravelerRating());
			bean.setStatsBean(statsBean);
			bean.setReviews(ConversionController.getInstance().convertReviewList(ReviewDao.getInstance().getUserReviews(userEmail)));
			return bean;
		} catch (DBConnectionException | SQLException e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}
}
