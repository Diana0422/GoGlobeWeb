package logic.model.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.model.User;

public class Cookie {

	private static Map<String, User> loggedUsers;
	private static Cookie instance;
	
	private Cookie() {}
	
	public static Cookie getInstance() {
		if (instance == null) {
			instance = new Cookie();
			loggedUsers = new HashMap<>();
		}
		
		return instance;
	}

	public void addToLoggedUsers(User user) {
		if (getLoggedUser(user.getEmail()) == null) {
			Cookie.loggedUsers.put(user.getEmail(), user);
			String logStr = "user added to logged users: "+loggedUsers;
			Logger.getGlobal().log(Level.INFO, logStr);
		}
	}

	public User getLoggedUser(String keyEmail) {
		return Cookie.loggedUsers.get(keyEmail);
	}
	
	public void removeLoggedUser(String keyEmail) {
		if (getLoggedUser(keyEmail) != null) Cookie.loggedUsers.remove(keyEmail);
	}
	
	public Map<String, User> getLoggedUsers() {
		return loggedUsers;
	}
}
