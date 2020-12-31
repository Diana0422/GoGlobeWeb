package logic.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
	
	
	public void validate(ActionEvent event) throws IOException {
		String email = txtEmail.getText();
		String password = txtPassword.getText();
		String name = txtName.getText();
		String surname = txtSurname.getText();
		String birthday = txtBirthday.getText();
		
		if (email.equals("") || password.equals("") || name.equals("") || surname.equals("") || birthday.equals("")) {
			lblMessage.setText("Input values not valid.");
		} else {
			/* Call the controller to register the user */
			if (RegistrationController.getInstance().register(email, password, name, surname, birthday)) {
				
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/view/Home.fxml"));
				Parent root = loader.load();
				UpperNavbarControl parentCtrl = UpperNavbarControl.getInstance();
				parentCtrl.addToPane(root);
				
				/* Set the label in the home view */
				String text = "Welcome"+" "+name+" "+surname;
				HomeGraphic c = loader.getController();
				c.setLabelText(text);
			} else {
				lblMessage.setText("User already registered with this email.");
			}
		}
		
	}
}