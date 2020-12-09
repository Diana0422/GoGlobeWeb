package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import model.User;

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
	

	public boolean saveUserOnFile(User user) throws IOException, FileNotFoundException {
		// Saves an instance of a user in file
		File f = new File("C:\\Users\\Utente\\git\\GoGlobeWeb\\src\\users.txt");
		Vector<User> objects;
			
		objects = readFromFile(f);
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
	public Vector<User> readFromFile(File f) {
		Vector<User> empty = new Vector<User>();
		try {
			FileInputStream fis = new FileInputStream(f);
			if (fis.available() != 0) {
				System.out.println("FileInputStream is available.");
				ObjectInputStream ois = new ObjectInputStream(fis);
				
//				if (ois.available() != 0) {
					System.out.println("ObjectInputStream is available.\n");
					Vector<User> deserialization = (Vector<User>) ois.readObject();
					System.out.println(deserialization);
					fis.close();
					ois.close();
					return deserialization;
//				} else {
//					System.out.println("No data to read from file.\n");
//					empty.add(new User("", "", 0, "", ""));
//					return empty;
//				}
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
	
	public User searchUserByEmail(String email) {
		// Gets an instance of a user in file
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		User user = null;
		try {
			fis = new FileInputStream("C:\\Users\\Utente\\git\\GoGlobeWeb\\src\\db\\users.bin");
			ois = new ObjectInputStream(fis);
			user = (User) ois.readObject();
			while (!user.getEmail().equals(email)) {
				user = (User) ois.readObject();
			}
			fis.close();
			ois.close();
			return user;
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
}
	

	