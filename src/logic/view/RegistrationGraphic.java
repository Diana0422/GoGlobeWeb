package logic.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logic.bean.SessionBean;
import logic.control.RegistrationController;

public class RegistrationGraphic {
	@FXML
	private TextField txtEmail;
	
	@FXML
	private PasswordField txtPassword;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtSurname;
	
	@FXML
	private TextField txtBirthday;
	
	@FXML
	private Label lblMessage;
	
	/* Beans */
	
	private SessionBean session;
	
	public SessionBean getSession() {
		return session;
	}

	public SessionBean setSession(SessionBean session) {
		this.session = session;
		return session;
	}
	
	/* Action methods */
	
	public void validate(ActionEvent event) {
		String email = txtEmail.getText();
		String password = txtPassword.getText();
		String name = txtName.getText();
		String surname = txtSurname.getText();
		String birthday = txtBirthday.getText();
		
		if (email.equals("") || password.equals("") || name.equals("") || surname.equals("") || birthday.equals("")) {
			lblMessage.setText("Input values not valid.");
		} else {
			/* Call the controller to register the user */
			if ((setSession(RegistrationController.getInstance().register(email, password, name, surname, birthday)))!= null) {
				
				/* Set the label in the home view */
				String welcome = "Welcome"+" "+name+" "+surname;
				UpperNavbarControl.getInstance().setSession(session);
				UpperNavbarControl.getInstance().loadHome(welcome);
			} else {
				lblMessage.setText("User already registered with this email.");
			}
		}
		
	}
}