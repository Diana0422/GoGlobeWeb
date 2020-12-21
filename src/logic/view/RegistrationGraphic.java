package logic.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root,700,600);
			scene.getStylesheets().addAll(getClass().getResource("application.css").toExternalForm(), getClass().getResource("home.css").toExternalForm());
			
			/* Set the label in the home view */
//			String text = "Welcome"+" "+name+" "+surname;
//			HomeGraphicControl c = loader.getController();
//			c.setLabelText(text);
			
			/* Set and show the new scene */
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		
	}
}
