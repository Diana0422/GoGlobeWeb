package logic.control;

import logic.bean.LoginBean;

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
		
		System.out.println(username);
		System.out.println(password);

		
		if (username.equals("lorenzo.tanzi997@gmail.com")  && password.equals("password")){
			loginBean.setNome("Lorenzo");
			loginBean.setCognome("Tanzi");
		}else {
			loginBean = null;
		}
		return loginBean;
	}	
}