package logic.dao;

import java.util.List;
import logic.model.Trip;

public interface TripDAO {
	
	public List<Trip> getAllTrips();
	public Trip getTrip(String title);
	public boolean updateTrip(Trip newTrip, Trip oldTrip);
	public boolean saveTrip(Trip trip);
}
