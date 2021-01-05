package logic.dao;

import java.util.List;
import logic.model.Trip;

public interface TripDAO {
	
	public List<Trip> getAllTrips();
	public Trip getTrip(String title);
	public boolean saveTrip(Trip trip);
	boolean updateTrip(Trip newTrip, String tripTitle);
}
