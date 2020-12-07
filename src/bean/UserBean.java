package bean;

import java.io.FileNotFoundException;
import java.io.IOException;

import control.DBController;
import model.User;

public class UserBean {
	private String name;
	private String surname;
	private int age;
	private String email;
	private String password;
	
	//UserBean Constructor
	public UserBean(String name, String surname, int age, String email, String password) throws FileNotFoundException {
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.email = email;
		this.password = password;
		
		//Initialize User 
		User user = new User(this.name, this.surname, this.age, this.email, this.password);
		
		//Call the DBController to save the user 
		DBController db = DBController.getInstance();
		try {
			db.saveUserOnFile(user);
			System.out.print("User Saved");
		} catch (FileNotFoundException e1) {
			System.err.print("File not found.\n");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.print("Io Exception.\n");
		}
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
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
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
	
	
}
