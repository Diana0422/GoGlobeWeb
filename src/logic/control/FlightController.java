package logic.control;

import logic.model.Trip;
import logic.model.adapters.FlightFinderAdapter;
import logic.model.apis.SkyscannerAPI;
import logic.model.factories.IPFinderAdapterFactory;
import logic.model.factories.PositionAdapterFactory;

public class FlightController {

	private static FlightController instance = null;
	private static String userLocation;
	
	private FlightController() {
		//empty
	}
	
	public static FlightController getInstance() {
		if (instance == null) {
			instance = new FlightController();
			String userIP = IPFinderAdapterFactory.getInstance().createIPFinderAdapter().getCurrentIP();
			userLocation = PositionAdapterFactory.getInstance().createIPLocationAdapter().getIPCurrentPosition(userIP);
			
		}
		
		return instance;
	}
	
	public int retrieveFlightPrice(Trip trip) {
		// Adding variable flight ticket price
		String destination = trip.getDays().get(0).getLocation();
		int ticketPrice = new FlightFinderAdapter(new SkyscannerAPI()).getFlightPrice(userLocation, destination, trip.getDepartureDate());
		trip.setTicketPrice(ticketPrice);
		System.out.println("Ticket price: "+trip.getTicketPrice());
		
		return ticketPrice;
	}
}
