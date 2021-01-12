package logic.model.factories;

import logic.model.Trip;

public class TripFactory {
	
	private static TripFactory instance;
	
	private TripFactory() {
		
	}
	
	public static synchronized TripFactory getInstance() {
		if (instance == null) {
			instance = new TripFactory();
		}
		return instance;		
	}
	
	
	public Trip createTrip() {
		return new Trip();
	}
	
	

}
