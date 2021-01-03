package logic.control;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.dao.TripDAO;
import logic.dao.TripDAOFile;
import logic.model.Day;
import logic.model.Trip;
import logic.model.factories.IPFinderAdapterFactory;
import logic.model.factories.PositionAdapterFactory;

public class ParticipationController {
	
	private Date today;
	
	public ParticipationController() {
		this.today = new Date();
	}
	
	public boolean checkParticipation(Trip trip) {
		String userIP;
		String userPos;
		List<Day> days = trip.getDays();
		for (Day day: days) {
			Logger.getGlobal().info("Date associated: "+day.getDate());
			Logger.getGlobal().log(Level.SEVERE, "Date for day saved on persistence is null. PLEASE DEBUG.");
			if (day.getDate().compareTo(today) == 0) {
				userIP = IPFinderAdapterFactory.getInstance().createIPFinderAdapter().getCurrentIP();
				userPos = PositionAdapterFactory.getInstance().createIPLocationAdapter().getIPCurrentPosition(userIP);
				Logger.getGlobal().info(userPos);
				if (!userPos.equals(day.getLocation())) {
					Logger.getGlobal().log(Level.INFO, "VALID PARTICIPATION. You are participating to this trip.");
					return true;
				} else {
					Logger.getGlobal().log(Level.INFO, "You are not participating to this trip.");
					return false;
				}
			}
		}
		
		Logger.getGlobal().log(Level.SEVERE, "This trip is not happening today.");
		return false;
		
	}
	
	public static void main(String [] args) {
		TripDAO dao = new TripDAOFile();
		Trip trip = dao.getTrip("I sospiri del mio Cuore");
		
		ParticipationController c = new ParticipationController();
		c.checkParticipation(trip);
	}

}
