package logic.control;

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
		
		/*Convert birthday string to real date */
		SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
		Date bd = null;
		try {
			bd = formatter.parse(birthday);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user = new User(name, surname, bd, email, password);
		return PersistenceController.getInstance().saveUserOnFile(user);
	}
	
}
