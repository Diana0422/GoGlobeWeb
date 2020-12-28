package logic.control;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import logic.model.User;

public class RegistrationController {
	
	private static RegistrationController INSTANCE = null;
	
	private RegistrationController() {}
	
	public static RegistrationController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new RegistrationController();
		}
		
		return INSTANCE;
	}
	
	public synchronized boolean register(String email, String password, String name, String surname, String birthday) {
		User user;
		DateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
		Date birth;
		try {
			birth = formatter.parse(birthday);
			user = new User(name, surname, birth, email, password);
			return PersistenceController.getInstance().saveUserOnFile(user);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
}
