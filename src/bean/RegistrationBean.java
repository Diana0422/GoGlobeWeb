package bean;

import java.io.FileNotFoundException;
import java.io.IOException;

import control.RegistrationController;

public class RegistrationBean {
	private String email;
	private String password;
	private String name;
	private String surname;
	private String birthday;
	private String passwordconf;
	
	//UserBean Constructor
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
		int age= 0;
		System.out.println(email+" "+name+" "+surname+" "+password+" "+passwordconf+" "+birthday+" ");
		
		// registration values control
		if (name== null || surname==null || email == null || password == null || birthday == null) {
			return false;
		}
		
		try {
			return RegistrationController.getInstance().register(email, password, name, surname, age);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
		
	}
	
}
