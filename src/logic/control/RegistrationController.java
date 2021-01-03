package logic.control;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import logic.dao.UserDAO;
import logic.dao.UserDAOFile;
import logic.model.User;

public class RegistrationController {
	
	private static RegistrationController instance = null;
	
	private RegistrationController() {}
	
	public static RegistrationController getInstance() {
		if (instance == null) {
			instance = new RegistrationController();
		}
		
		return instance;
	}
	
	public synchronized boolean register(String email, String password, String name, String surname, String birthday) {
		User user;
		DateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
		Date birth;
		try {
			birth = formatter.parse(birthday);
			user = new User(name, surname, birth, email, password);
			UserDAO dao = new UserDAOFile();
			if (dao.getUser(email) == null) {
				return dao.saveUser(user);
			} else {
				return false;
			}
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
}
