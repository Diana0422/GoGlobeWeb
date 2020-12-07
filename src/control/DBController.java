package control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import model.User;

public class DBController {
	
	public static DBController INSTANCE = null;
	
	private DBController() {}
	
	public static DBController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DBController();
		}
		
		return INSTANCE;
	}
	
	public void saveUserOnFile(User user) throws IOException, FileNotFoundException {
		// Saves an instance of a user in file
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream("users.dat");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(user);
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
		} finally {
			fos.close();
			oos.close();
		}
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

}
	

	