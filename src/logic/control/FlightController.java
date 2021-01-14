package logic.control;

import java.util.logging.Level;
import java.util.logging.Logger;

import logic.bean.TripBean;
import logic.dao.TripDAO;
import logic.dao.TripDAOFile;
import logic.model.Trip;
import logic.model.adapters.FlightFinderAdapter;
import logic.model.apis.SkyscannerAPI;
import logic.model.exceptions.APIException;
import logic.model.exceptions.FlightNotFoundException;
import logic.model.exceptions.IPNotFoundException;
import logic.model.exceptions.LocationNotFoundException;
import logic.model.exceptions.SerializationException;
import logic.model.utils.GeolocationPicker;

public class FlightController {

	private static FlightController instance = null;
	private static String userLocation;
	private static TripDAO dao;
	private static final String ND = "N/D"; 
	
	private FlightController() {
		//empty
	}
	
	public static FlightController getInstance() {
		if (instance == null) {
			instance = new FlightController();
			String userIP = null;
			try {
				userIP = GeolocationPicker.getInstance().forwardIPRequestToAPI();
			} catch (IPNotFoundException e) {
				Logger.getGlobal().log(Level.WARNING, e.getMessage());
				userIP = ND;
			}
			try {
				userLocation = GeolocationPicker.getInstance().forwardLocationRequestToAPI(userIP);
			} catch (LocationNotFoundException e) {
				Logger.getGlobal().log(Level.WARNING, e.getMessage());
				userLocation = ND;
			}
			dao = new TripDAOFile();
		}
		
		return instance;
	}
	
	public int retrieveFlightPrice(TripBean bean) {
		// Adding variable flight ticket price
		Trip trip = null;
		int ticketPrice;
		try {
			trip = dao.getTrip(bean.getTitle());
		} catch (SerializationException e1) {
			Logger.getGlobal().log(Level.WARNING, e1.getMessage());
			e1.printStackTrace();
			return 0;
		}
		String destination = trip.getDays().get(0).getLocation();
		try {
			ticketPrice = new FlightFinderAdapter(new SkyscannerAPI()).getFlightPrice(userLocation, destination, trip.getDepartureDate());
			trip.setTicketPrice(ticketPrice);
		} catch (FlightNotFoundException | APIException e) {
			Logger.getGlobal().log(Level.WARNING, e.getMessage());
			e.printStackTrace();
			ticketPrice = 0;
		}
		
		return ticketPrice;
	}
	
	public String retrieveFlightOrigin(TripBean bean) {
		// Retrieve flight origin airport name
		Trip trip;
		try {
			trip = dao.getTrip(bean.getTitle());
		} catch (SerializationException e1) {
			Logger.getGlobal().log(Level.WARNING, e1.getMessage());
			e1.printStackTrace();
			return ND;
		}
		String destination = trip.getDays().get(0).getLocation();
		try {
			return new FlightFinderAdapter(new SkyscannerAPI()).getFlightOrigin(userLocation, destination, trip.getDepartureDate());
		} catch (FlightNotFoundException | APIException e) {
			Logger.getGlobal().log(Level.WARNING, e.getMessage());
			e.printStackTrace();
			return ND;
		}
	}
	
	public String retrieveFlightDestination(TripBean bean) {
		// Retrieve flight destination airport name
		Trip trip;
		try {
			trip = dao.getTrip(bean.getTitle());
		} catch (SerializationException e1) {
			Logger.getGlobal().log(Level.WARNING, e1.getMessage());
			e1.printStackTrace();
			return ND;
		}
		String destination = trip.getDays().get(0).getLocation();
		try {
			return new FlightFinderAdapter(new SkyscannerAPI()).getFlightDestination(userLocation, destination, trip.getDepartureDate());
		} catch (FlightNotFoundException | APIException e) {
			Logger.getGlobal().log(Level.WARNING, e.getMessage());
			e.printStackTrace();
			return ND;
		}
	}
	
	public String retrieveFlightCarrier(TripBean bean) {
		// Retrieve flight carrier name
		Trip trip;
		try {
			trip = dao.getTrip(bean.getTitle());
		} catch (SerializationException e1) {
			Logger.getGlobal().log(Level.WARNING, e1.getMessage());
			e1.printStackTrace();
			return ND;
		}
		String destination = trip.getDays().get(0).getLocation();
		try {
			return new FlightFinderAdapter(new SkyscannerAPI()).getFlightCarrier(userLocation, destination, trip.getDepartureDate());
		} catch (FlightNotFoundException | APIException e) {
			Logger.getGlobal().log(Level.WARNING, e.getMessage());
			e.printStackTrace();
			return ND;
		}
	}
}
