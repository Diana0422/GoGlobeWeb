package logic.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import logic.model.Day;
import logic.model.Trip;
import logic.model.User;

public class PersistenceController {
	
	private static PersistenceController INSTANCE = null;
	private static final String PathLorenzo= "/Users/lorenzotanzi/git/GoGlobeWeb/src/trips.txt";
	private static final String relPath = "src/trips.txt";
	
	private PersistenceController() {}
	
	public static PersistenceController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PersistenceController();
//			PersistenceController.initializeFile("C:\\Users\\dayli\\git\\GoGlobeWeb\\src\\users.txt", "user");
//			PersistenceController.initializeFile("C:\\Users\\dayli\\git\\GoGlobeWeb\\src\\trips.txt", "trip");
		}
		
		return INSTANCE;
	}
	
	
	public String toString(Trip trip) {
		StringBuilder result = new StringBuilder(":");
		result.append(trip);
		result.append("(");
		for(Day d : trip.getDays())
			result.append(d);
		result.append(")");
		return result.toString();
	}
	
	
	/* TRIP METHODS */
	
	public boolean saveTripOnFile(Trip trip) {
		// Saves a trip on a back-end file
		String projectPath = System.getProperty("user.dir");
		System.out.println(projectPath);
		String filePath = projectPath + "/trips.txt";
		File f = new File(filePath);
		ArrayList<Trip> objects = (ArrayList<Trip>) readTripFromFile(f);
		
		System.out.println("Objects: "+objects);
		System.out.println("Serializing instance of trip = "+ this.toString(trip)+ "\n");
		objects.add(trip);
		
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
			System.out.println("Serializing instance of trip = "+ this.toString(trip)+ "\n");
			out.writeObject(objects); /*!!!*/
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
	
	
	
	@SuppressWarnings({ "unchecked" })
	public List<Trip> readTripFromFile(File f) {
		// Reads a list of trips from the back-end file
		List<Trip> empty = new ArrayList<>();
		try (FileInputStream fis= new FileInputStream(f);
			 ObjectInputStream ois = new ObjectInputStream(fis)) {
			
			if (fis.available() != 0) {
				System.out.println("FileInputStream is available.");
				System.out.println("ObjectInputStream is available.");
				List<Trip> deserialization = (ArrayList<Trip>) ois.readObject();
				System.out.println("Trips found: "+deserialization);
				return deserialization;

			} /*else {
				System.out.println("FileInputStream not available:");
				System.out.println("No data to read from file.\n");
				empty.add(new Trip(0,"", "", 0, "", "", "", ""));
				return empty;
			}*/
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return empty;
		}
		System.out.println("Returning empty");
		return empty;
		
	}
	
	
	public boolean writeTripsToFile(File f, List<Trip> objects) throws IOException {
		// Writes a list of trips to the back-end file
	
		try (FileOutputStream fos = new FileOutputStream(f);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(objects);
			return true;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	/* USER METHODS */
	
	public boolean saveUserOnFile(User user) {
		// Saves an instance of a user in file
		String projectPath = System.getProperty("user.dir");
		System.out.println(projectPath);
		String filePath = projectPath + "/users.txt";
		File f = new File(filePath);
		List<User> objects;
			
		objects = readUserFromFile(f);
		System.out.println("Objects: "+objects);
		for (int i=0; i<objects.size(); i++) {
			if (objects.get(i).getEmail().equals(user.getEmail())) {
				System.out.println("User already registered.");
				return false;
			}
		}
		
		objects.add(user);
		return writeUsersToFile(f, objects);
	}
	
	
	@SuppressWarnings({ "unchecked" })
	public List<User> readUserFromFile(File f) {
		List<User> empty = new ArrayList<>();
		try (FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis)) {
			if (fis.available() != 0) {
				System.out.println("FileInputStream is available.");
				System.out.println("ObjectInputStream is available.\n");
				List<User> deserialization = (ArrayList<User>) ois.readObject();
				System.out.println(deserialization);
				return deserialization;
			} else {
				System.out.println("FileInputStream not available:");
				System.out.println("No data to read from file.\n");
				empty.add(new User("", "", 0, "", ""));
				return empty;
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return empty;
	}
	
	
	public boolean writeUsersToFile(File f, List<User> objects) {
		try (FileOutputStream fos = new FileOutputStream(f);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(objects);
			return true;	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public static void initializeFile(String filename, String type) {
		try (FileOutputStream fos = new FileOutputStream(filename);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			if (type.equals("user")) {
				List<User> object = new ArrayList<>();
				User user = new User("dummy", "dummy", 0, "dummy", "dummy");
				object.add(user);
				oos.writeObject(object);
			} else if (type.equals("trip")) {
				List<Trip> object = new ArrayList<>();
				Trip trip = new Trip(0,"", "", 0, "None", "None", "00/00/0000", "00/00/0000");
				object.add(trip);
				oos.writeObject(object);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String [] args) {
		Trip trip = new Trip(1,"Japanese Temples", "C:\\Users\\Utente\\git\\GoGlobeWeb\\WebContent\\res\\images\\kyoto", 2300, "Adventure", "Culture", "22/04/2021", "13/05/2021");
		PersistenceController.getInstance().saveTripOnFile(trip);
		trip = new Trip(2, "That's Amore", "C:\\Users\\Utente\\git\\GoGlobeWeb\\WebContent\\res\\images\\kyoto", 1500, "Relax", "Fun", "21/03/2021", "29/03/2021");
		PersistenceController.getInstance().saveTripOnFile(trip);
	}
}
	

	