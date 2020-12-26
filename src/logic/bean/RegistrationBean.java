package logic.bean;

import logic.control.RegistrationController;

public class RegistrationBean {
	private String email;
	private String password;
	private String name;
	private String surname;
	private String birthday;
	private String passwordconf;
	
	//RegistrationBean Constructor
	public RegistrationBean() {
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
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public boolean validate() {
		System.out.println(email+" "+name+" "+surname+" "+password+" "+passwordconf+" "+birthday+" ");
		
		// registration values control
		if (name== null || surname==null || email == null || password == null || birthday == null) {
			return false;
		}
		
		return RegistrationController.getInstance().register(email, password, name, surname, birthday);
		
		
	}
	
}
