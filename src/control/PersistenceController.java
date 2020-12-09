package control;

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
	
	@SuppressWarnings({ "unchecked", "resource" })
	public boolean saveUserOnFile(User user) throws IOException, FileNotFoundException {
		// Saves an instance of a user in file
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		Vector<User> objects;
		Vector<User> object = new Vector<User>();
		byte[] buffer = new byte[1024];
		try { 
			fis = new FileInputStream("C:\\Users\\Utente\\eclipse-workspace\\ISPWProject20-21\\src\\db\\users.bin");
			ois = new ObjectInputStream(fis);
			fos = new FileOutputStream("C:\\Users\\Utente\\eclipse-workspace\\ISPWProject20-21\\src\\db\\users.bin");
			oos = new ObjectOutputStream(fos);
			
			int bytesRead = ois.read(buffer);
			
			if (bytesRead == -1) {
				object.add(new User("dummy", "dummy", 0, "dummy", "dummy"));
				oos.writeObject(object);
			}
			
			objects = (Vector<User>) ois.readObject();
			System.out.println("Objects in persistency: " +objects);
				
			boolean found = false;		
			for (int i=0; i<objects.size(); i++) {
				if (objects.get(i).getEmail().equals(user.getEmail())) {
					System.out.println("User already registered.");
					found=true;
					return false;
				}
			}
				
			if (found == false) {
				// Add new user as registered
				System.out.println("Add new user as registered.");
				objects.add(user);
				fos = new FileOutputStream("C:\\Users\\Utente\\git\\GoGlobeWeb\\src\\db\\users.bin");		
				oos = new ObjectOutputStream(fos);
				oos.writeObject(objects);
				return true;
			}
				
			fis.close();
			ois.close();
			fos.close();
			oos.close();
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			fos.close();
			oos.close();
		}
		return false;
	}
	
	public User searchUserByEmail(String email) {
		// Gets an instance of a user in file
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		User user = null;
		try {
			fis = new FileInputStream("users.dat");
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
	

	