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
import logic.bean.SessionBean;
import logic.bean.TripBean;

public class UpperNavbarControl implements Initializable {
	
	// SINGLETON

	private static UpperNavbarControl instance = null;
	
	private SessionBean session;
	
	private UpperNavbarControl() {
    }
    
    public static UpperNavbarControl getInstance() {
    	if (instance == null) {
    		instance = new UpperNavbarControl();
    	}
    	
    	return instance;
    }
	
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
    private Button btnPrizes;

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
    
    /* Session Bean SETTERS AND GETTERS */
    
    public SessionBean getSession() {
		return session;
	}

	public void setSession(SessionBean session) {
		this.session = session;
	}
	
	/* Action methods */

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
    void displayPrizes(MouseEvent event) {
    	//TODO
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
    
    void loadJoinTrip() {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/logic/view/JoinTrip.fxml"));
    	String logStr = "Loaded UI: JoinTrip.fxml";
		Logger.getGlobal().info(logStr);
		
		try {
			Parent root = loader.load();
			JoinTripGraphic graphic = loader.getController();
	    	graphic.setSession(getSession());
			addToPane(root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//Error VIEW LOADING
			e.printStackTrace();
		}
    }
    
    void loadRequests() {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/logic/view/ManageRequests.fxml"));
    	String logStr = "Loaded UI: ManageRequests.fxml";
		Logger.getGlobal().info(logStr);
		
		try {
			Parent root = loader.load();
			addToPane(root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//Error VIEW LOADING
			e.printStackTrace();
		}
    }
    
    void loadPlanTrip() {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/logic/view/PlanTrip.fxml"));
    	String logStr = "Loaded UI: PlanTrip.fxml";
		Logger.getGlobal().info(logStr);
		//TODO
    }
    
    void loadHome(String welcome) {
    	FXMLLoader loader = new FXMLLoader();
    	setSession(session); //TODO move this to login and registration
    	loader.setLocation(getClass().getResource("/logic/view/Home.fxml"));
    	String logStr = "Loaded UI: Home.fxml";
		Logger.getGlobal().info(logStr);
    	try {
			Parent root = loader.load();
			HomeGraphic graphic = loader.getController();
	    	graphic.setLabelText(welcome);
			addToPane(root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//Error VIEW LOADING
			e.printStackTrace();
		}
    }
    
    void loadTripInfo(TripBean choice, SessionBean session) {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/logic/view/TripInfo.fxml"));
    	String logStr = "Loaded UI: TripInfo.fxml";
		Logger.getGlobal().info(logStr);
    	try {
			Parent root = loader.load();
			TripInfoGraphic tig = loader.getController();
	    	tig.setTitleText(choice.getTitle());
	    	tig.setPriceText(Integer.toString(choice.getPrice()));
	    	tig.setDescriptionText(choice.getDescription());
	    	tig.setDepartureText(choice.getDepartureDate());
	    	tig.setReturnText(choice.getReturnDate());
	    	tig.setCategory1Text(choice.getCategory1());
	    	tig.setCategory2Text(choice.getCategory2());
	    	tig.setTripBean(choice);
	    	tig.setSession(session);
	    	
	    	tig.addDayTabs(choice.getDays());
			addToPane(root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//Error VIEW LOADING
			e.printStackTrace();
		}
    }
    
    
    public void loadUI(String ui) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource(ui+".fxml"));
			String logStr = "Loaded UI: "+ui+".fxml";
			Logger.getGlobal().info(logStr);
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