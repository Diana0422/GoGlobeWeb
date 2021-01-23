package logic.view;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logic.bean.LoginBean;
import logic.bean.SessionBean;
import logic.control.LoginController;
import logic.persistence.exceptions.DBConnectionException;

public class LoginGraphic {
	
	@FXML
	private TextField tfEmail;
	
	@FXML
	private PasswordField pfPassword;

    @FXML
    private Button btnSignin;

    @FXML
    private Button btnSignup;
    
    @FXML
    private Label lblError;

    @FXML
    public void forwardHome(ActionEvent event) {
    	LoginBean loginBean = new LoginBean();
    	loginBean.setUsername(tfEmail.getText());
    	loginBean.setPassword(pfPassword.getText());
    	String logStr = "LOGIN TRIAL:"+"\n"+"USERNAME: " + tfEmail.getText()+"\n"+"PASSWORD: " + pfPassword.getText();
    	Logger.getGlobal().info(logStr);
    	if (loginBean.validate()) {
    		try {
				if ((loginBean = LoginController.getInstance().login(loginBean.getUsername(), loginBean.getPassword())) != null) {
					SessionBean session = new SessionBean();
					session.setSessionEmail(loginBean.getUsername());
					session.setSessionName(loginBean.getNome());
					session.setSessionSurname(loginBean.getCognome());
					session.setSessionPoints(loginBean.getPoints());
					DesktopSessionContext.getInstance().setSession(session);
					DesktopSessionContext.getGuiLoader().loadGUI(null, session, GUIType.HOME);
				} else {
					lblError.setText("This user isn't registered.");
					lblError.setVisible(true);
				}
			} catch (DBConnectionException e) {
				AlertGraphic alert = new AlertGraphic();
				alert.display(GUIType.LOGIN, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Database connection error", "Please retry later.");
			}
    	}else {
			lblError.setText("Some fields are still empty.");
			lblError.setVisible(true);
    		logStr = "INVALID LOGIN.";
    		Logger.getGlobal().info(logStr);
    	}
    }

    @FXML
    public void forwardRegistration(ActionEvent event){
    	DesktopSessionContext.getGuiLoader().loadGUI(null, null, GUIType.REGISTER);
    }

}
