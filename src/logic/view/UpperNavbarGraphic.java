package logic.view;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class UpperNavbarGraphic implements Initializable {
	
	@FXML
    private ImageView imgLogo;

    @FXML
    private Label lblLogo;

    @FXML
    private Button btnJoinTrip;

    @FXML
    private Button btnPlanTrip;

    @FXML
    private Button btnProfile;
    
    @FXML
    private Button btnPrizes;

    @FXML
    private Button btnRequests;

    @FXML
    private Button btnSettings;

    @FXML
    private MenuButton menuAccount;

    @FXML
    private MenuItem itemRegister;

    @FXML
    private MenuItem itemLogin;

    @FXML
    private MenuItem itemLogout;

    @FXML
    private BorderPane borderpane;
    
    public void setVisibleLoggedButtons(boolean bool) {
    	btnProfile.setVisible(bool);
    	btnRequests.setVisible(bool);
    	btnSettings.setVisible(bool);
    	itemLogout.setVisible(bool);
    }

    @FXML
    void displayJoinTrip(MouseEvent event) {
    	DesktopSessionContext.getGuiLoader().loadGUI(null, null, GUIType.JOIN);
    }

    @FXML
    void displayPlanTrip(MouseEvent event) {
    	DesktopSessionContext.getGuiLoader().loadGUI(null, null, GUIType.PREFTRIP);
    }
    
    @FXML
    void displayPrizes(MouseEvent event) {
    	DesktopSessionContext.getGuiLoader().loadGUI(null, null, GUIType.PRIZES);
    }

    @FXML
    void displayProfile(MouseEvent event) {
    	DesktopSessionContext.getGuiLoader().loadGUI(null, null, GUIType.PROFILE);
    }

    @FXML
    void displayRequests(MouseEvent event) {
    	DesktopSessionContext.getGuiLoader().loadGUI(null, null, GUIType.REQUESTS);
    }

    @FXML
    void displaySettings(MouseEvent event) {
    	DesktopSessionContext.getGuiLoader().loadGUI(null, null, GUIType.SETTINGS);
    }

    @FXML
    void loadLogin(ActionEvent event) {
    	DesktopSessionContext.getGuiLoader().loadGUI(null, null, GUIType.LOGIN);
    }
   
    @FXML
    void logout(ActionEvent event) {
    	DesktopSessionContext.getInstance().setSession(null);
    	DesktopSessionContext.getGuiLoader().loadGUI(null, null, GUIType.LOGIN);
    }

    @FXML
    void loadRegistration(ActionEvent event) {
    	DesktopSessionContext.getGuiLoader().loadGUI(null, null, GUIType.REGISTER);
    }

    @FXML
    void loadRequests(ActionEvent event) {
    	DesktopSessionContext.getGuiLoader().loadGUI(null, null, GUIType.REQUESTS);
    }

    @FXML
    void loadSettings(ActionEvent event) {
    	DesktopSessionContext.getGuiLoader().loadGUI(null, null, GUIType.SETTINGS);
    }
   
    
    public void loadUI(String ui) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource(ui+".fxml"));
			borderpane.setCenter(root);
		} catch (IOException e) {
			Logger.getLogger(UpperNavbarGraphic.class.getName()).log(Level.SEVERE, null, e);
		}
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lblLogo.setText("GoGlobe");
		if (DesktopSessionContext.getInstance().getSession() == null) this.setVisibleLoggedButtons(false);
	}

}
