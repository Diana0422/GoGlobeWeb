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
    		if ((loginBean = LoginController.getInstance().login(loginBean.getUsername(), loginBean.getPassword())) != null) {
        		SessionBean session = new SessionBean();
        		System.out.println("email: "+loginBean.getUsername());
        		session.setEmail(loginBean.getUsername());
        		session.setName(loginBean.getNome());
        		session.setSurname(loginBean.getCognome());
        		session.setPoints(loginBean.getPoints());
        		DesktopSessionContext.getInstance().setSession(session);
            	DesktopSessionContext.getGuiLoader().loadGUI(null, session, GUIType.HOME);
    		} else {
    			lblError.setText("This user isn't registered.");
    			lblError.setVisible(true);
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
