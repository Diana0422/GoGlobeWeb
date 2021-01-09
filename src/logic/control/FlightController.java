package logic.control;

import logic.bean.TripBean;
import logic.dao.TripDAO;
import logic.dao.TripDAOFile;
import logic.model.Trip;
import logic.model.adapters.FlightFinderAdapter;
import logic.model.apis.SkyscannerAPI;
import logic.model.factories.IPFinderAdapterFactory;
import logic.model.factories.PositionAdapterFactory;

public class FlightController {

	private static FlightController instance = null;
	private static String userLocation;
	private static TripDAO dao;
	
	private FlightController() {
		//empty
	}
	
	public static FlightController getInstance() {
		if (instance == null) {
			instance = new FlightController();
			String userIP = IPFinderAdapterFactory.getInstance().createIPFinderAdapter().getCurrentIP();
			userLocation = PositionAdapterFactory.getInstance().createIPLocationAdapter().getIPCurrentPosition(userIP);
			dao = new TripDAOFile();
		}
		
		return instance;
	}
	
	public int retrieveFlightPrice(TripBean bean) {
		// Adding variable flight ticket price
		Trip trip = dao.getTrip(bean.getTitle());
		String destination = trip.getDays().get(0).getLocation();
		int ticketPrice = new FlightFinderAdapter(new SkyscannerAPI()).getFlightPrice(userLocation, destination, trip.getDepartureDate());
		trip.setTicketPrice(ticketPrice);
		System.out.println("Ticket price: "+trip.getTicketPrice());
		
		return ticketPrice;
	}
	
	public String retrieveFlightOrigin(TripBean bean) {
		// Retrieve flight origin airport name
		Trip trip = dao.getTrip(bean.getTitle());
		String destination = trip.getDays().get(0).getLocation();
		return new FlightFinderAdapter(new SkyscannerAPI()).getFlightOrigin(userLocation, destination, trip.getDepartureDate());
	}
	
	public String retrieveFlightDestination(TripBean bean) {
		// Retrieve flight destination airport name
		Trip trip = dao.getTrip(bean.getTitle());
		String destination = trip.getDays().get(0).getLocation();
		return new FlightFinderAdapter(new SkyscannerAPI()).getFlightDestination(userLocation, destination, trip.getDepartureDate());
	}
	
	public String retrieveFlightCarrier(TripBean bean) {
		// Retrieve flight carrier name
		Trip trip = dao.getTrip(bean.getTitle());
		String destination = trip.getDays().get(0).getLocation();
		return new FlightFinderAdapter(new SkyscannerAPI()).getFlightCarrier(userLocation, destination, trip.getDepartureDate());
	}
}
