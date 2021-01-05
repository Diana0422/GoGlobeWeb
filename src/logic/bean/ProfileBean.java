package logic.bean;

public class ProfileBean {
	
	private UserBean user;
	
	public ProfileBean() {
		//empty constructor
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

}
