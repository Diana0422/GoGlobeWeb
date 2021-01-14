package logic.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import logic.control.PersistenceController;
import logic.model.ModelClassType;
import logic.model.Trip;
import logic.model.exceptions.SerializationException;
import logic.model.utils.TripSerialObject;

public class TripDAOFile implements TripDAO {
	
	private PersistenceController pc = PersistenceController.getInstance();
	

	@Override
	public List<Trip> getAllTrips() throws SerializationException {
		// Reads a list of trips from the back-end file
		List<Trip> empty = new ArrayList<>();
		
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(pc.getBackendFile(ModelClassType.TRIP)))) {
			if (ois.available() != 0) {
				Logger.getGlobal().info("ObjectInputStream is available.");
				TripSerialObject o = (TripSerialObject) ois.readObject();
				return o.getList();
			} 
		} catch (FileNotFoundException e) {
			throw new SerializationException(e.getCause(), "The file specified was not found in the workingspace.");
		} catch (IOException e) {
			throw new SerializationException(e.getCause(), "Error in opening the stream to the file.");
		} catch (ClassNotFoundException e) {
			throw new SerializationException(e.getCause(), "Error reading object from the specified file.");
		}
		Logger.getGlobal().info("Returning empty");
		return empty;
	}

	
	@Override
	public Trip getTrip(String title) throws SerializationException {
		// Gets a specific trip from back-end file
		
		List<Trip> trips = getAllTrips();
		
		for (Trip trip : trips) {
			if (trip.getTitle().equals(title)) return trip;
		}
		return null;
	}
	


	@Override
	public boolean saveTrip(Trip trip) throws SerializationException {
		// Saves a trip into back-end file
		List<Trip> trips = getAllTrips();
		trips.add(trip);
		
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(pc.getBackendFile(ModelClassType.TRIP)))) {
			Logger.getGlobal().info("Serializing instance of trip \n");
			TripSerialObject o = new TripSerialObject(trips);
			out.writeObject(o);
			return true;
		} catch (IOException e) {
			throw new SerializationException(e.getCause(),"Error opening output stream to the specified file.");
		}
	}


	@Override
	public boolean updateTrip(Trip newTrip, String tripTitle) throws SerializationException {
		// Updates trip information into back-end file
		
		List<Trip> trips = getAllTrips();
		
		for (Trip tmp: trips) {
			if (tmp.getTitle().equals(tripTitle)) {
				tmp.setTitle(newTrip.getTitle());
				tmp.setCategory1(newTrip.getCategory1());
				tmp.setCategory2(newTrip.getCategory2());
				tmp.setParticipants(newTrip.getParticipants());
				break;
			}
		}
		
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(pc.getBackendFile(ModelClassType.TRIP)))) {
			Logger.getGlobal().info("Serializing instance of trip \n");
			TripSerialObject o = new TripSerialObject(trips);
			out.writeObject(o);
			return true;
		} catch (FileNotFoundException e) {
			throw new SerializationException(e.getCause(), "The file specified was not found in the workingspace.");
		} catch (IOException e) {
			throw new SerializationException(e.getCause(), "Error opening the output stream to the specified file.");
		}
	}

}
