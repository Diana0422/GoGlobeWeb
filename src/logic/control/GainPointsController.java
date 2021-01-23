package logic.control;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import logic.bean.SessionBean;
import logic.bean.TripBean;
import logic.bean.UserBean;
import logic.persistence.dao.TripDao;
import logic.persistence.dao.UserDaoDB;
import logic.persistence.dao.UserStatsDao;
import logic.persistence.exceptions.DBConnectionException;
import logic.persistence.exceptions.DatabaseException;
import logic.model.Trip;
import logic.model.User;

public class GainPointsController {
	
	private static GainPointsController instance = null;
	
	private GainPointsController() {
		//empty constructor for singleton
	}
	
	public static GainPointsController getInstance() {
		if (instance == null) {
			instance = new GainPointsController();
		}
		
		return instance;
	}
	
	
	public TripBean getTripOfTheDay(String userEmail) throws DatabaseException {
		Date today = new Date();
		
		UserBean logged;
		try {
			logged = ConversionController.getInstance().convertToUserBean(UserDaoDB.getInstance().get(userEmail));
			List<TripBean> list = ConversionController.getInstance().convertTripList(TripDao.getInstance().getTrips());
			
			for (TripBean bean: list) {
				Date dep = ConversionController.getInstance().parseDate(bean.getDepartureDate());
				Date ret = ConversionController.getInstance().parseDate(bean.getReturnDate());
				
				if (today.after(dep) && today.before(ret)) {
					if (bean.getOrganizer().getEmail().equals(logged.getEmail())) return bean;
					
					if (isParticipant(logged.getEmail(), bean.getParticipants())) return bean;
				}
			}
		} catch (DatabaseException | DBConnectionException | SQLException e1) {
			throw new DatabaseException(e1.getMessage(), e1.getCause());
		}
		return null;
	}
	
	private boolean isParticipant(String email, List<UserBean> participants) {
		for (UserBean user: participants) {
			if (user.getEmail().equals(email)) return true;
		}
		return false;
	}

	public boolean verifyParticipation(SessionBean session, TripBean tripBean) throws DatabaseException {
		Trip trip = ConversionController.getInstance().convertToTrip(tripBean);
		try {
			if (ParticipationController.getInstance().checkParticipation(trip)) {
				User user;
				user = UserDaoDB.getInstance().get(session.getSessionEmail());
				user.addPoints(100);
				session.setSessionPoints(user.getPoints());
				UserStatsDao.getInstance().updateStats(session.getSessionEmail(), user.getPoints(), user.getStats().getOrganizerRating(), user.getStats().getTravelerRating());
				return true;
			} else {
				return false;
			}
		} catch (DBConnectionException | SQLException e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}
	
}
