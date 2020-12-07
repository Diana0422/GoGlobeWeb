package control;

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
	
	public synchronized User register(String email, String password, String name, String surname, int age) {
		User user;
		user = new User(name, surname, age, email, password);
		return user;
	}
	
}
