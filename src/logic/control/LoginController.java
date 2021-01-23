package logic.control;

import logic.bean.LoginBean;
import logic.persistence.dao.UserDaoDB;
import logic.persistence.exceptions.DBConnectionException;
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
	
	public LoginBean login(String username, String password) throws DBConnectionException { 
		
		
		LoginBean loginBean = new LoginBean();
		User tempUser = null;
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
	}	
}