package control;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.User;

public class RegistrationController {
	
	private static RegistrationController INSTANCE = null;
	
	private RegistrationController() {}
	
	public static RegistrationController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new RegistrationController();
		}
		
		return INSTANCE;
	}
	
	public synchronized boolean register(String email, String password, String name, String surname, int age) throws FileNotFoundException, IOException {
		User user;
		user = new User(name, surname, age, email, password);
		return PersistenceController.getInstance().saveUserOnFile(user);
	}
	
}
