package logic.view;

import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import logic.bean.SessionBean;
import logic.bean.TripBean;
import logic.bean.UserBean;
import logic.control.ProfileController;
import logic.model.exceptions.LoadGraphicException;
import logic.model.exceptions.SerializationException;

public class ProfileGraphic implements GraphicController {

    @FXML
    private VBox vbReviews;

    @FXML
    private GridPane upcomingGrid;

    @FXML
    private GridPane previousGrid;

    @FXML
    private GridPane myTripsGrid;

    @FXML
    private Text txtNameSurname;

    @FXML
    private Text txtAge;

    @FXML
    private ImageView ivAdventureAttitude;

    @FXML
    private ImageView ivRelaxAttitude;

    @FXML
    private ImageView ivFunAttitude;

    @FXML
    private ImageView ivCultureAttitude;

    @FXML
    private ImageView ivProfilePic;
    
    @FXML
    private Button btnBack;
    
    private Object bundle;

    @FXML
    void back(MouseEvent event) {
    	DesktopSessionContext.getGuiLoader().loadGUI(null, this.bundle, null);
    }

    /* Beans */
    private SessionBean session;
    
    
    public SessionBean getSession() {
		return session;
	}

	public void setSession(SessionBean session) {
		this.session = session;
	}
    
    /* Action methods */
	private void loadGrid(GridPane tripsGrid, List<TripBean> trips) {
		
		int column = 0;
		int row = 1;
		
		for (int i=0; i<trips.size(); i++) {
				
			CardGraphic cc = new CardGraphic();
			AnchorPane anchor;
			try {
				if (column == 3) {
					row++;
					column = 0;
				}
					
				anchor = (AnchorPane) cc.initializeNode(trips.get(i));
				tripsGrid.add(anchor, column++, row);
				GridPane.setMargin(anchor, new Insets(20));
			} catch (LoadGraphicException e) {
				AlertGraphic graphic = new AlertGraphic();
				graphic.display(GUIType.PROFILE, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Widget loading error.", "Something unexpected occurred loading the trip cards.");
			}
					
			// Set grid height
			tripsGrid.setMaxHeight(Region.USE_PREF_SIZE);
			tripsGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
			tripsGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
					
			// Set grid width
			tripsGrid.setMaxWidth(Region.USE_PREF_SIZE);
			tripsGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
			tripsGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
		}				
	}


	@Override
	public void initializeData(Object recBundle, Object forBundle) {
		UserBean user = (UserBean) recBundle;
		this.bundle = forBundle;
		List<TripBean> myTripBeans = null;
		List<TripBean> upcomingTripBeans = null;
		List<TripBean> previousTripBeans = null;
		
		try {
			myTripBeans = ProfileController.getInstance().getMyTrips();
			loadGrid(myTripsGrid, myTripBeans);
		} catch (SerializationException e) {
			AlertGraphic graphic = new AlertGraphic();
			graphic.display(GUIType.PROFILE, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Serialization Error", "Something unexpected occurred loading trips.");
		}
	
		try {
			upcomingTripBeans = ProfileController.getInstance().getUpcomingTrips();
			loadGrid(upcomingGrid, upcomingTripBeans);
		} catch (SerializationException e) {
			AlertGraphic graphic = new AlertGraphic();
			graphic.display(GUIType.PROFILE, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Serialization Error", "Something unexpected occurred loading trips.");
		}
		
		try {
			previousTripBeans = ProfileController.getInstance().getRecentTrips();
			loadGrid(previousGrid, previousTripBeans);
		} catch (SerializationException e) {
			AlertGraphic graphic = new AlertGraphic();
			graphic.display(GUIType.JOIN, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Serialization Error", "Something unexpected occurred loading trips.");
		}

		txtNameSurname.setText(user.getName()+" "+user.getSurname());
		txtAge.setText(Integer.toString(user.getAge()));	
	}


}

	
	

