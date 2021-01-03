package logic.bean;

import logic.model.User;

public class SessionBean {
	private User loggedUser;
	private String name;
	private String surname;
	
	public SessionBean() {
		// empty constructor
	}

	public User getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
		setName(loggedUser.getName());
		setSurname(loggedUser.getSurname());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	
}
