package logic.control;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.bean.SessionBean;
import logic.bean.TripBean;
import logic.bean.UserBean;
import logic.dao.TripDAO;
import logic.dao.TripDAOFile;
import logic.persistence.dao.UserDaoDB;
import logic.persistence.dao.UserStatsDao;
import logic.model.Trip;
import logic.model.User;
import logic.model.exceptions.SerializationException;

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
	
	
	public TripBean getTripOfTheDay(String userEmail) throws SerializationException {
		Date today = new Date();
		TripDAO tripDao = new TripDAOFile();
		
		UserBean logged = ConversionController.getInstance().convertToUserBean(UserDaoDB.getInstance().get(userEmail));
		
		List<TripBean> list = ConversionController.getInstance().convertTripList(tripDao.getAllTrips());
		for (TripBean bean: list) {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dep = null;
			Date ret = null;
			try {
				dep = formatter.parse(bean.getDepartureDate());
				ret = formatter.parse(bean.getReturnDate());

			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
			
			if (today.after(dep) && today.before(ret)) {
				if (bean.getOrganizer().getEmail().equals(logged.getEmail())) return bean;
				
				if (isParticipant(logged.getEmail(), bean.getParticipants())) return bean;
			}
		}
		return null;
	}
	
	private boolean isParticipant(String email, List<UserBean> participants) {
		for (UserBean user: participants) {
			if (user.getEmail().equals(email)) return true;
		}
		return false;
	}

	public boolean verifyParticipation(SessionBean session, TripBean tripBean) {
		Trip trip = null;
		try {
			trip = ConversionController.getInstance().convertToTrip(tripBean);
		} catch (SerializationException e) {
			Logger.getGlobal().log(Level.WARNING, e.getMessage());
			e.printStackTrace();
			return false;
		}
		if (ParticipationController.getInstance().checkParticipation(trip)) {
			
			User user;
			user = UserDaoDB.getInstance().get(session.getEmail());
			user.addPoints(100);
			session.setPoints(user.getPoints());
			UserStatsDao.getInstance().updateStats(session.getEmail(), user.getPoints(), user.getStats().getOrganizerRating(), user.getStats().getTravelerRating());
			return true;
		}
		return false;
	}
}
