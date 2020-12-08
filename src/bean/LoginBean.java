package bean;

import control.LoginController;

public class LoginBean {
	
	private String username;
	private String password;
	protected String nome;
	protected String cognome;
	
	public LoginBean() {
//		System.out.println("Ciao sono una loginBean");
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
		if (username == null || username == "" || password==null || password == "") {
//			System.out.println("Guarda sono pezzo di merda");
			return false;
		}
		
//		System.out.println(username);
//		System.out.println(password);

		LoginBean utenteTrovato = LoginController.getInstance().login(username, password);
//		System.out.println(utenteTrovato!=null);
		return utenteTrovato != null;
		
	}
	

}