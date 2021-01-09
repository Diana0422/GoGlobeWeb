package logic.model.interfaces;

import java.util.Date;

public interface FlightFinder {

	public String getFlightOrigin(String userLocation, String destination, Date depDate);
	public String getFlightDestination(String userLocation, String destination, Date depDate);
	public String getFlightCarrier(String userLocation, String destination, Date depDate);
	public int getFlightPrice(String userLocation, String destination, Date depDate);
	
}
