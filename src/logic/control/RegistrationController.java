package logic.control;

import java.sql.SQLException;
import java.util.Date;

import logic.bean.SessionBean;
import logic.persistence.dao.UserDaoDB;
import logic.persistence.exceptions.DBConnectionException;
import logic.persistence.exceptions.DatabaseException;
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
	
	public synchronized SessionBean register(String email, String password, String name, String surname, String birthday) throws DatabaseException {
		User user;
		Date birth = ConversionController.getInstance().parseDate(birthday);
		user = new User(name, surname, birth, email, password);
		try {
			if (UserDaoDB.getInstance().save(user)) {
				SessionBean session = new SessionBean(); 
				session.setSessionEmail(email);
				session.setSessionName(name);
				session.setSessionSurname(surname);
				session.setSessionPoints(0);
				return session;
			}
		} catch (DBConnectionException | SQLException e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
		return null;
	}
	
}
