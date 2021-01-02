package logic.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logic.bean.LoginBean;

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
    public void forwardHome(ActionEvent event) {
    	LoginBean loginBean = new LoginBean();
    	loginBean.setUsername(tfEmail.getText());
    	loginBean.setPassword(pfPassword.getText());
    	System.out.println("TENTATIVO DI LOGIN:");
    	System.out.println("USERNAME: " + tfEmail.getText());
    	System.out.println("PASSWORD: " + pfPassword.getText());
    	if (loginBean.validate()) {
        	UpperNavbarControl.getInstance().loadUI("Home");
    	}else {
    		System.out.println("VALIDATE ANDATO MALINO EH");
    	}
    }

    @FXML
    public void forwardRegistration(ActionEvent event){
    	
    	UpperNavbarControl.getInstance().loadUI("Registration");
//    	try {
//			Stage primaryStage = new Stage();
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/view/Registration.fxml"));
//			Parent root;
//			root = loader.load();
//			Scene scene = new Scene(root,1024,600);
//			scene.getStylesheets().addAll(getClass().getResource("/logic/view/css/application.css").toExternalForm(), getClass().getResource("/logic/view/css/registration.css").toExternalForm());
//			
//			/* Set and show the new scene */
//			primaryStage.setScene(scene);
//			primaryStage.show();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();   		
//		}
    }

}
