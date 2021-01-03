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

public class UpperNavbarControl implements Initializable {

	private static UpperNavbarControl instance = null;
	
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

    @FXML
    void displayJoinTrip(MouseEvent event) {
    	loadUI("/logic/view/JoinTrip");
    }

    @FXML
    void displayPlanTrip(MouseEvent event) {
    	loadUI("/logic/view/PlanTrip");
    }

    @FXML
    void displayProfile(MouseEvent event) {
    	loadUI("/logic/view/Profile");
    }

    @FXML
    void displayRequests(MouseEvent event) {
    	loadUI("/logic/view/ManageRequests");
    }

    @FXML
    void displaySettings(MouseEvent event) {
    	loadUI("/logic/view/Settings");
    }

    @FXML
    void loadLogin(ActionEvent event) {
    	loadUI("/logic/view/Login");
    }

    @FXML
    void loadPlanTripView(ActionEvent event) {
    	loadUI("/logic/view/PlanTrip");
    }

    @FXML
    void loadRegistration(ActionEvent event) {
    	loadUI("/logic/view/Registration");
    }
    
    @FXML 
    void loadHome(ActionEvent event) {
    	loadUI("logic/view/Home");
    }

    @FXML
    void loadRequests(ActionEvent event) {
    	//TODO
    }

    @FXML
    void loadSettings(ActionEvent event) {
    	//TODO
    }
    
    private UpperNavbarControl() {
    }
    
    public static UpperNavbarControl getInstance() {
    	if (instance == null) {
    		instance = new UpperNavbarControl();
    	}
    	
    	return instance;
    }
    
    
    public void loadUI(String ui) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource(ui+".fxml"));
			addToPane(root);
		} catch (IOException e) {
			Logger.getLogger(UpperNavbarControl.class.getName()).log(Level.SEVERE, null, e);
		}
    }
    
    public void addToPane(Parent root) {
		borderpane.setCenter(root);
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//empty
	}

}