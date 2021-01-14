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
import logic.dao.UserDAO;
import logic.dao.UserDAOFile;
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
		UserDAO userDao = new UserDAOFile();
		
		UserBean logged = ConversionController.getInstance().convertToUserBean(userDao.getUser(userEmail));
		
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
			
			System.out.println(bean.getOrganizer());
			System.out.println("Today: "+today);
			System.out.println("Dep: "+dep);
			System.out.println("Ret:"+ret);
			
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
			UserDAO userDao = new UserDAOFile();
			User user = userDao.getUser(session.getEmail());
			user.addPoints(100);
			session.setPoints(user.getPoints());
			userDao.updateUser(user, session.getEmail());
			return true;
		}
		return false;
	}
}
