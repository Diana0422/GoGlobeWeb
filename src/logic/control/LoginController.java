package logic.control;

import java.util.List;

import logic.bean.LoginBean;
import logic.dao.UserDAOFile;
import logic.model.User;

public class LoginController {
	
	private static LoginController INSTANCE;
	
	private LoginController(){
		
	}
	
	public static synchronized LoginController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new LoginController();
		}
		return INSTANCE;
	}
	
	public LoginBean login(String username, String password){ 
		
		
		LoginBean loginBean = new LoginBean();
		
		List<User> users = (new UserDAOFile()).getAllUsers();	
		
		for (int i = 0; i < users.size(); i++) {
			User tempUser = users.get(i);
			if (tempUser.getEmail().equals(username) && tempUser.getPassword().equals(password)){
				
					loginBean.setNome(tempUser.getName());
					loginBean.setCognome(tempUser.getSurname());
					return loginBean;			
			}	
			
		}
		loginBean = null;
		return loginBean;
	}	
}