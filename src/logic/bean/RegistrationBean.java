package logic.bean;
import java.util.logging.Logger;

public class RegistrationBean {
	private String email;
	private String password;
	private String name;
	private String surname;
	private String birthday;
	private String passwordconf;
	
	//RegistrationBean Constructor
	public RegistrationBean() {
		// empty constructor
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
		String infoStr = email+" "+name+" "+surname+" "+password+" "+passwordconf+" "+birthday+" ";
		Logger.getGlobal().info(infoStr);
		
		// registration values control
		return (name== null || surname==null || email == null || password == null || birthday == null);
	}
	
}
