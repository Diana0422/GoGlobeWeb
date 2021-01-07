package logic.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.control.PersistenceController;
import logic.model.ModelClassType;
import logic.model.User;

public class UserDAOFile implements UserDAO{
	
	private PersistenceController pc = PersistenceController.getInstance();

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers() {
		// Reads a list of trips from the back-end file
		
		List<User> empty = new ArrayList<>();
		try (FileInputStream fis= new FileInputStream(pc.getBackendFile(ModelClassType.USER));
			ObjectInputStream ois = new ObjectInputStream(fis)) {
					
			if (fis.available() != 0) {
				Logger.getGlobal().info("FileInputStream is available.");
				Logger.getGlobal().info("ObjectInputStream is available.");
				return (List<User>) ois.readObject();
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
	public User getUser(String email) {
		// Gets a specific user from back-end file
		
		List<User> users = getAllUsers();
				
		for (User user : users) {
			if (user.getEmail().equals(email)) return user;
		}
		return null;
	}
	

	@Override
	public boolean updateUser(User newUser, String oldUserEmail) {
		// Updates trip information into back-end file
		
		List<User> users = getAllUsers();
				
		for (User tmp: users) {
			if (tmp.getEmail().equals(oldUserEmail)) {
				tmp.setName(newUser.getName());
				tmp.setSurname(newUser.getSurname());
				tmp.setPoints(newUser.getPoints());
				tmp.setBirthday(newUser.getBirthday());
				tmp.setReviews(newUser.getReviews());
				tmp.setRedeemedPrizes(newUser.getRedeemedPrizes());
				Logger.getGlobal().info("Add update profile picture.");
				break;
			}
		}
				
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(pc.getBackendFile(ModelClassType.USER)))) {
			Logger.getGlobal().info("Serializing instance of USER \n");
			out.writeObject(users);
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getGlobal().log(Level.SEVERE, "The file was not found.");
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.getGlobal().log(Level.SEVERE, "Io Issue");
			e.printStackTrace();
			return false;
		}
		
	}
	

	@Override
	public boolean saveUser(User user) {
		// Saves a trip into back-end file
		List<User> users = getAllUsers();
		users.add(user);
				
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(pc.getBackendFile(ModelClassType.USER)))) {
			Logger.getGlobal().info("Serializing instance of USER \n");
			out.writeObject(users);
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
