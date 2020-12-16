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
		Vector<Trip> objects = null;
		
		try {
			objects = readTripFromFile(f);
			
			System.err.print("Objects: "+objects);
			for (int i=0; i<objects.size(); i++) {
				if (objects.get(i).getId() == trip.getId()) {
					System.err.print("Trip already saved to back-end.");
					return false;
				}
			}
			
			objects.add(trip);
			if (writeTripsToFile(f, objects)) {
				System.err.print("Trip saved on back-end file.");
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	
	
	@SuppressWarnings({ "unchecked" })
	public Vector<Trip> readTripFromFile(File f) throws IOException {
		// Reads a list of trips from the back-end file
		Vector<Trip> empty = new Vector<Trip>();
		FileInputStream fis = new FileInputStream(f);
		ObjectInputStream ois = new ObjectInputStream(fis);
		try {
			if (fis.available() != 0) {
				System.err.print("FileInputStream is available.");
				System.err.print("ObjectInputStream is available.");
				Vector<Trip> deserialization = (Vector<Trip>) ois.readObject();
				System.err.print(deserialization);
				return deserialization;

			} else {
				System.err.println("FileInputStream not available:");
				System.err.println("No data to read from file.\n");
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
		} finally {
			fis.close();
			ois.close();
		}
		
	}
	
	
	public boolean writeTripsToFile(File f, Vector<Trip> objects) throws FileNotFoundException, IOException {
		// Writes a list of trips to the back-end file
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		try {
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
		} finally {
			fos.close();
			oos.close();
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
	
	
	@SuppressWarnings({ "unchecked" })
	public Vector<User> readUserFromFile(File f) throws IOException {
		Vector<User> empty = new Vector<User>();
		ObjectInputStream ois = null;
		FileInputStream fis = new FileInputStream(f);
		if (fis.available() != 0) {
			System.out.println("FileInputStream is available.");
			ois = new ObjectInputStream(fis);
		} else {
			System.out.println("FileInputStream not available:");
			System.out.println("No data to read from file.\n");
			empty.add(new User("", "", 0, "", ""));
			fis.close();
			return empty;
		}
		
		try {	
				System.out.println("ObjectInputStream is available.\n");
				Vector<User> deserialization = (Vector<User>) ois.readObject();
				System.out.println(deserialization);
				return deserialization;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			fis.close();
			ois.close();
		}
		return null;
	}
	
	
	public boolean writeUsersToFile(File f, Vector<User> objects) throws IOException {
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		try {
			oos.writeObject(objects);
			return true;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			fos.close();
			oos.close();
		}
	}
	
	public static void initializeFile(String filename) throws IOException {
		FileOutputStream fos = new FileOutputStream(filename);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		Vector<User> object = new Vector<User>();
		
		User user = new User("dummy", "dummy", 0, "dummy", "dummy");
		object.add(user);
		try {
			oos.writeObject(object);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			fos.close();
			oos.close();
		}
		
	}
	
	
	public static void main(String [] args) {
		Trip trip = new Trip(1,"Japanese Temples", "C:\\Users\\Utente\\git\\GoGlobeWeb\\WebContent\\res\\images\\kyoto", 2300, "Adventure", "Culture", "22/04/2021", "13/05/2021");
//		Trip trip2 = new Trip(2,"Trippissimo", "C:\\Users\\Utente\\git\\GoGlobeWeb\\WebContent\\res\\images\\boh", 500, null, null);
		PersistenceController.getInstance().saveTripOnFile(trip);
	}
}
	

	