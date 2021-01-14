package logic.bean;

import java.util.logging.Level;
import java.util.logging.Logger;

import logic.control.LoginController;
import logic.model.exceptions.SerializationException;

public class LoginBean {
	
	private String username;
	private String password;
	protected String nome;
	protected String cognome;
	private int points;
	
	public LoginBean() {
		//empty constructor
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public boolean validate(){
		if (username == null || username.equals("") || password==null || password.equals("")) {
			return false;
		}
		
		System.out.println("TRYING TO LOGIN AS: " + this.getUsername() + " " + this.getPassword());

		LoginBean utenteTrovato;
		try {
			utenteTrovato = LoginController.getInstance().login(username, password); 	//TODO move this into graphic controller

			if (utenteTrovato != null) {
				setNome(utenteTrovato.getNome());
				setCognome(utenteTrovato.getCognome());
				setPoints(utenteTrovato.getPoints());
			}
			return utenteTrovato != null;
		} catch (SerializationException e) {
			Logger.getGlobal().log(Level.WARNING, e.getMessage());
			e.printStackTrace();
			return false;
		}
		
	}


	public int getPoints() {
		return points;
	}


	public void setPoints(int points) {
		this.points = points;
	}
	

}