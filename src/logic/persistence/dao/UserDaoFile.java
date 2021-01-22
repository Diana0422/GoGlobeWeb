package logic.persistence.dao;

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
import logic.model.User;
import logic.model.exceptions.SerializationException;
import logic.model.utils.UserSerialObject;

public class UserDaoFile {
	
	private PersistenceController pc = PersistenceController.getInstance();
	private static final String ERROR_FILENOTFOUND = "The file specified was not found in the workingspace.";
	
	public List<User> getAllUsers() throws SerializationException {
		// Reads a list of trips from the back-end file
		List<User> empty = new ArrayList<>();
		
		try (FileInputStream fis = new FileInputStream(pc.getBackendFile(ModelClassType.USER));
			ObjectInputStream ois = new ObjectInputStream(fis)) {
			if (fis.available() != 0) {
				Logger.getGlobal().info("ObjectInputStream is available.");
				UserSerialObject o = (UserSerialObject) ois.readObject();
				return o.getList();
			}
		} catch (FileNotFoundException e) {
			throw new SerializationException(e.getCause(), ERROR_FILENOTFOUND);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			throw new SerializationException(e.getCause(), "Error reading object from the specified file.");
		}
		Logger.getGlobal().info("Returning empty");
		return empty;
	}

	
	public User getUser(String email) throws SerializationException {
		// Gets a specific user from back-end file
		
		List<User> users = getAllUsers();
				
		for (User user : users) {
			if (user.getEmail().equals(email)) {
				return user;
			}
		}
		return null;
	}
	

	public boolean updateUser(User newUser, String oldUserEmail) throws SerializationException {
		// Updates trip information into back-end file
		
		List<User> users = getAllUsers();
				
		for (User tmp: users) {
			if (tmp.getEmail().equals(oldUserEmail)) {
				tmp.setName(newUser.getName());
				tmp.setSurname(newUser.getSurname());
				tmp.setPoints(newUser.getPoints());
				tmp.setBirthday(newUser.getBirthday());
				tmp.setReviews(newUser.getReviews());
				tmp.setStats(newUser.getStats());
				Logger.getGlobal().info("Add update profile picture.");
				break;
			}
		}
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(pc.getBackendFile(ModelClassType.USER)))) {
			Logger.getGlobal().info("Serializing instance of USER \n");
			UserSerialObject o = new UserSerialObject(users);
			out.writeObject(o);
			return true;
		} catch (FileNotFoundException e) {
			throw new SerializationException(e.getCause(), ERROR_FILENOTFOUND);
		} catch (IOException e) {
			throw new SerializationException(e.getCause(), "Error opening the output stream to the specified file.");
		}
	}
	

	public boolean saveUser(User user) throws SerializationException {
		// Saves a trip into back-end file
		List<User> users = getAllUsers();
		users.add(user);
				
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(pc.getBackendFile(ModelClassType.USER)))) {
			Logger.getGlobal().info("Serializing instance of USER \n");
			UserSerialObject o = new UserSerialObject(users);
			out.writeObject(o);
			return true;
		} catch (FileNotFoundException e) {
			throw new SerializationException(e.getCause(), ERROR_FILENOTFOUND);
		} catch (IOException e) {
			throw new SerializationException(e.getCause(), "Error opening the output stream to the specified file.");
		}
		
	}

}
