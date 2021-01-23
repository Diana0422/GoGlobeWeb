package logic.control;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import logic.bean.SessionBean;
import logic.persistence.dao.UserDaoDB;
import logic.persistence.exceptions.DBConnectionException;
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
	
	public synchronized SessionBean register(String email, String password, String name, String surname, String birthday) throws DBConnectionException {
		User user;
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date birth;
		try {
			birth = formatter.parse(birthday);
			user = new User(name, surname, birth, email, password);
			if (UserDaoDB.getInstance().save(user)) {
				SessionBean session = new SessionBean(); 
				session.setSessionEmail(email);
				session.setSessionName(name);
				session.setSessionSurname(surname);
				session.setSessionPoints(0);
				return session;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
}
