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

public class TripDAOFile implements TripDAO {
	
	private PersistenceController pc = PersistenceController.getInstance();
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Trip> getAllTrips() {
		// Reads a list of trips from the back-end file
		
		List<Trip> empty = new ArrayList<>();
		try (FileInputStream fis= new FileInputStream(pc.getBackendFile(ModelClassType.TRIP));
			ObjectInputStream ois = new ObjectInputStream(fis)) {
					
			if (fis.available() != 0) {
				Logger.getGlobal().info("FileInputStream is available.");
				Logger.getGlobal().info("ObjectInputStream is available.");
				return (List<Trip>) ois.readObject();
			} 
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getGlobal().info("Returning empty");
			return empty;
		}
		
		Logger.getGlobal().info("Returning empty");
		return empty;
	}

	
	@Override
	public Trip getTrip(String title) {
		// Gets a specific trip from back-end file
		
		List<Trip> trips = getAllTrips();
		
		for (Trip trip : trips) {
			if (trip.getTitle().equals(title)) return trip;
		}
		return null;
	}
	


	@Override
	public boolean saveTrip(Trip trip) {
		// Saves a trip into back-end file
		List<Trip> trips = getAllTrips();
		trips.add(trip);
		
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(pc.getBackendFile(ModelClassType.TRIP)))) {
			Logger.getGlobal().info("Serializing instance of trip \n");
			out.writeObject(trips);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}


	@Override
	public boolean updateTrip(Trip newTrip, String tripTitle) {
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
			out.writeObject(trips);
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
