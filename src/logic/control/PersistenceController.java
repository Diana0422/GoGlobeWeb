package logic.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import logic.model.Trip;
import logic.model.User;

public class PersistenceController {
	
	private static PersistenceController INSTANCE = null;
	
	private PersistenceController() {}
	
	public static PersistenceController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PersistenceController();
//			PersistenceController.initializeFile("C:\\Users\\Utente\\eclipse-workspace\\ISPWProject20-21\\src\\db\\users.bin");
		}
		
		return INSTANCE;
	}
	
	
	/* TRIP METHODS */
	
	public boolean saveTripOnFile(Trip trip) {
		// Saves a trip on a back-end file
		File f = new File("C:\\Users\\dayli\\git\\GoGlobeWeb\\src\\trips.txt");
		Vector<Trip> objects;
		
		objects = readTripFromFile(f);
		System.out.println("Objects: "+objects);
		for (int i=0; i<objects.size(); i++) {
			if (objects.get(i).getId() == trip.getId()) {
				System.out.println("Trip already saved to back-end.");
				return false;
			}
		}
		
		objects.add(trip);
		if (writeTripsToFile(f, objects)) {
			System.out.println("Trip saved on back-end file.");
			return true;
		} else {
			return false;
		}
	}
	
	
	
	@SuppressWarnings({ "unchecked", "resource" })
	public Vector<Trip> readTripFromFile(File f) {
		// Reads a list of trips from the back-end file
		Vector<Trip> empty = new Vector<Trip>();
		FileInputStream fis;
		
		try {
			fis = new FileInputStream(f);
			if (fis.available() != 0) {
				System.out.println("FileInputStream is available.");
				ObjectInputStream ois = new ObjectInputStream(fis);
				
				System.out.println("ObjectInputStream is available.\n");
				Vector<Trip> deserialization = (Vector<Trip>) ois.readObject();
				System.out.println(deserialization);
				fis.close();
				ois.close();
				return deserialization;

			} else {
				System.out.println("FileInputStream not available:");
				System.out.println("No data to read from file.\n");
				empty.add(new Trip(0,"", "", 0, "", "", "", ""));
				return empty;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	public boolean writeTripsToFile(File f, Vector<Trip> objects) {
		// Writes a list of trips to the back-end file
		try {
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(objects);
			oos.close();
			fos.close();
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
	
	
	/* USER METHODS */
	
	public boolean saveUserOnFile(User user) throws IOException, FileNotFoundException {
		// Saves an instance of a user in file
		File f = new File("C:\\Users\\dayli\\git\\GoGlobeWeb\\src\\users.txt");
		Vector<User> objects;
			
		objects = readUserFromFile(f);
		System.out.println("Objects: "+objects);
		for (int i=0; i<objects.size(); i++) {
			if (objects.get(i).getEmail().equals(user.getEmail())) {
				System.out.println("User already registered.");
				return false;
			}
		}
		
		objects.add(user);
		if (writeUsersToFile(f, objects)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	@SuppressWarnings({ "resource", "unchecked" })
	public Vector<User> readUserFromFile(File f) {
		Vector<User> empty = new Vector<User>();
		try {
			FileInputStream fis = new FileInputStream(f);
			if (fis.available() != 0) {
				System.out.println("FileInputStream is available.");
				ObjectInputStream ois = new ObjectInputStream(fis);
				
				System.out.println("ObjectInputStream is available.\n");
				Vector<User> deserialization = (Vector<User>) ois.readObject();
				System.out.println(deserialization);
				fis.close();
				ois.close();
				return deserialization;

			} else {
				System.out.println("FileInputStream not available:");
				System.out.println("No data to read from file.\n");
				empty.add(new User("", "", 0, "", ""));
				return empty;
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public boolean writeUsersToFile(File f, Vector<User> objects) {
		try {
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(objects);
			oos.close();
			fos.close();
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
	
	public static void initializeFile(String filename) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		Vector<User> object = new Vector<User>();
		
		User user = new User("dummy", "dummy", 0, "dummy", "dummy");
		object.add(user);
		try {
			fos = new FileOutputStream(filename);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(object);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String [] args) {
		Trip trip = new Trip(1,"Japanese Temples", "C:\\Users\\Utente\\git\\GoGlobeWeb\\WebContent\\res\\images\\kyoto", 2300, "Adventure", "Culture", "22/04/2021", "13/05/2021");
//		Trip trip2 = new Trip(2,"Trippissimo", "C:\\Users\\Utente\\git\\GoGlobeWeb\\WebContent\\res\\images\\boh", 500, null, null);
		PersistenceController.getInstance().saveTripOnFile(trip);
	}
}
	

	