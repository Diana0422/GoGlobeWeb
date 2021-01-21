package logic.control;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import logic.bean.SessionBean;
import logic.persistence.dao.UserDaoDB;
import logic.model.User;
import logic.model.exceptions.SerializationException;

public class RegistrationController {
	
	private static RegistrationController instance = null;
	
	private RegistrationController() {}
	
	public static RegistrationController getInstance() {
		if (instance == null) {
			instance = new RegistrationController();
		}
		
		return instance;
	}
	
	public synchronized SessionBean register(String email, String password, String name, String surname, String birthday) throws SerializationException {
		User user;
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date birth;
		try {
			birth = formatter.parse(birthday);
			user = new User(name, surname, birth, email, password);
			if (UserDaoDB.getInstance().save(user)) {
				SessionBean session = new SessionBean(); 
				session.setEmail(email);
				session.setName(name);
				session.setSurname(surname);
				session.setPoints(0);
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
