package logic.control;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.model.Day;
import logic.model.Trip;
import logic.model.factories.IPFinderAdapterFactory;
import logic.model.factories.PositionAdapterFactory;

public class ParticipationController {
	
	private static ParticipationController instance = null;
	
	private static Date today;
	
	private ParticipationController() {
		//empty constructor
	}
	
	public static ParticipationController getInstance() {
		if (instance == null) {
			instance = new ParticipationController();
			today = new Date();
			System.out.println("Today:"+ today);
		}
		
		return instance;
	}
	
	public boolean checkParticipation(Trip trip) {
		String userIP;
		String userPos;
		List<Day> days = trip.getDays();
		
		// TODO sistemare formattazione date in maniera univoca
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String todayStr = formatter.format(today);
		System.out.println("Today str: "+todayStr);
		
		for (Day day: days) {
			Logger.getGlobal().info("Date associated: "+day.getDate());
			String dayStr = formatter.format(day.getDate());
			System.out.println("Day str: "+dayStr);
			
			if (dayStr.equals(todayStr)) {
				userIP = IPFinderAdapterFactory.getInstance().createIPFinderAdapter().getCurrentIP();
				userPos = PositionAdapterFactory.getInstance().createIPLocationAdapter().getIPCurrentPosition(userIP);
				Logger.getGlobal().info(userPos);
				
				if (userPos.equals(day.getLocation())) {
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

}
