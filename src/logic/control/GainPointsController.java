package logic.control;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import logic.bean.SessionBean;
import logic.bean.TripBean;
import logic.bean.UserBean;
import logic.persistence.dao.TripDao;
import logic.persistence.dao.UserDaoDB;
import logic.persistence.dao.UserStatsDao;
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
	
	
	public TripBean getTripOfTheDay(String userEmail) {
		Date today = new Date();
		
		UserBean logged = ConversionController.getInstance().convertToUserBean(UserDaoDB.getInstance().get(userEmail));
		
		List<TripBean> list = ConversionController.getInstance().convertTripList(TripDao.getInstance().getTrips());
		System.out.println("get trip of the day (days):"+list.get(1).getDays());
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
				System.out.println(bean.getDays());
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
		Trip trip = ConversionController.getInstance().convertToTrip(tripBean);
		System.out.println(trip.getDays());
		if (ParticipationController.getInstance().checkParticipation(trip)) {
			
			User user;
			user = UserDaoDB.getInstance().get(session.getSessionEmail());
			user.addPoints(100);
			session.setSessionPoints(user.getPoints());
			UserStatsDao.getInstance().updateStats(session.getSessionEmail(), user.getPoints(), user.getStats().getOrganizerRating(), user.getStats().getTravelerRating());
			return true;
		}
		return false;
	}
}
