package logic.control;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logic.bean.TripBean;
import logic.bean.UserBean;
import logic.bean.UserStatsBean;
import logic.model.Trip;
import logic.model.TripCategory;
import logic.model.User;
import logic.model.UserStats;
import logic.model.utils.converters.ReviewBeanConverter;
import logic.model.utils.converters.TripBeanConverter;
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
	   TripBeanConverter tripConverter = new TripBeanConverter();
	   try {
		   trips = TripDao.getInstance().getTrips();
		   return tripConverter.convertToListBean(trips);
	   } catch (DBConnectionException | SQLException e) {
		   throw new DatabaseException(e.getMessage(), e.getCause());
	   }
   }
   
	public List<TripBean> getUpcomingTrips() throws DatabaseException {
	   List<Trip> trips;
	   TripBeanConverter tripConverter = new TripBeanConverter();
	   try {
		   trips = TripDao.getInstance().getTrips();
		   return tripConverter.convertToListBean(trips);
	   } catch (DBConnectionException | SQLException e) {
		   throw new DatabaseException(e.getMessage(), e.getCause());
	   }
	}
	
	public List<TripBean> getMyTrips() throws DatabaseException {
	   List<Trip> trips;
	   TripBeanConverter tripConverter = new TripBeanConverter();
	   try {
		   trips = TripDao.getInstance().getTrips();
		   return tripConverter.convertToListBean(trips);
	   } catch (DBConnectionException | SQLException e) {
		   throw new DatabaseException(e.getMessage(), e.getCause());
	   }
	}
	
	public UserBean getProfileUser(String userEmail) throws DatabaseException {
		ReviewBeanConverter reviewConverter = new ReviewBeanConverter();
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
			bean.setReviews(reviewConverter.convertToListBean(ReviewDao.getInstance().getUserReviews(userEmail)));
			return bean;
		} catch (DBConnectionException | SQLException e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}
	
	public void updateUserBio (String userEmail, String newUserBio) throws DatabaseException {
		try {
			UserDaoDB.getInstance().updateUserBio(userEmail, newUserBio);
		} catch (DBConnectionException | SQLException e ) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}
	
	public Map<String, Integer> getPercentageAttitude(String userEmail) throws DatabaseException {
		/* Calculate user's traveling attitude (in percentage) */
		Map<String, Integer> percAttitude = new HashMap<>();
		int total = 0;
		int percent = 0;
		// Find user attitude
		User user;
		try {
			user = UserDaoDB.getInstance().get(userEmail);
		} catch (DBConnectionException | SQLException e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
		Map<TripCategory, Integer> attitude = user.getAttitude();
		System.out.println(attitude);
		for (Map.Entry<TripCategory, Integer> entry: attitude.entrySet()) {
			total += entry.getValue();
		}

		for (Map.Entry<TripCategory, Integer> entry: attitude.entrySet()) {
			if (total != 0) {
				percent = entry.getValue()*100/total;
			} else {
				percent = 0;
			}
			percAttitude.putIfAbsent(entry.getKey().toString(), percent);
		}

		
		return percAttitude;
	}
}
