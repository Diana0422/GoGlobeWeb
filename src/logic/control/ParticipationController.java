package logic.control;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.model.Day;
import logic.model.Trip;
import logic.model.exceptions.IPNotFoundException;
import logic.model.exceptions.LocationNotFoundException;
import logic.model.utils.GeolocationPicker;

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
		}
		
		return instance;
	}
	
	public boolean checkParticipation(Trip trip) {
		String userIP = null;
		String userPos = null;
		List<Day> days = trip.getDays();
		
		// TODO sistemare formattazione date in maniera univoca
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String todayStr = formatter.format(today);
		
		for (Day day: days) {
			Logger.getGlobal().info("Date associated: "+day.getDate());
			String dayStr = formatter.format(day.getDate());
			
			if (dayStr.equals(todayStr)) {
				//TODO FARE IN MODO CHE LA POSIZIONE VENGA PRELEVATA 1 VOLTA SOLA AL MOMENTO DEL LOGIN E SIA FISSA PER L'INTERA SESSIONE
				try {
					userIP = GeolocationPicker.getInstance().forwardIPRequestToAPI();
				} catch (IPNotFoundException e) {
					userIP = "N/D";
					Logger.getGlobal().log(Level.WARNING, e.getMessage());
				}
				try {
					userPos = GeolocationPicker.getInstance().forwardLocationRequestToAPI(userIP);
				} catch (LocationNotFoundException e) {
					userPos = "N/D";
					Logger.getGlobal().log(Level.WARNING, e.getMessage());
				}
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
