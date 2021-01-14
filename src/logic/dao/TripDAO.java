package logic.dao;

import java.util.List;
import logic.model.Trip;
import logic.model.exceptions.SerializationException;

public interface TripDAO {
	
	public List<Trip> getAllTrips() throws SerializationException;
	public Trip getTrip(String title) throws SerializationException;
	public boolean saveTrip(Trip trip) throws SerializationException;
	public boolean updateTrip(Trip newTrip, String tripTitle) throws SerializationException;
}
