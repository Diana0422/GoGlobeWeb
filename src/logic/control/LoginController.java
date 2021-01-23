package logic.control;

import java.sql.SQLException;

import logic.bean.LoginBean;
import logic.persistence.dao.UserDaoDB;
import logic.persistence.exceptions.DBConnectionException;
import logic.persistence.exceptions.DatabaseException;
import logic.model.User;

public class LoginController {
	
	private static LoginController instance;
	
	private LoginController(){
		
	}
	
	public static synchronized LoginController getInstance() {
		if (instance == null) {
			instance = new LoginController();
		}
		return instance;
	}
	
	public LoginBean login(String username, String password) throws DatabaseException { 
		
		
		LoginBean loginBean = new LoginBean();
		User tempUser = null;
		try {
			if ((tempUser = UserDaoDB.getInstance().get(username)) != null) {
				if (tempUser.getEmail().equals(username) && tempUser.getPassword().equals(password)){
					loginBean.setUsername(username);
					loginBean.setPassword(password);
					loginBean.setNome(tempUser.getName());
					loginBean.setCognome(tempUser.getSurname());
					loginBean.setPoints(tempUser.getPoints());			
				}
			} else {
				loginBean = null;
			}
			return loginBean;
		} catch (DBConnectionException | SQLException e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}	
}