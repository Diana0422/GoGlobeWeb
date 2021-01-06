package logic.view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import logic.bean.SessionBean;
import logic.bean.TripBean;
import logic.bean.UserBean;
import logic.control.ProfileController;

public class ProfileGraphic implements Initializable {

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
		
		try {
			for (int i=0; i<trips.size(); i++) {
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/logic/view/TripCard.fxml"));
					
				AnchorPane anchor = loader.load();
					
				CardGraphic cc = loader.getController();
				cc.setData(trips.get(i), getSession());
					
				if (column == 3) {
					row++;
					column = 0;
				}
					
				tripsGrid.add(anchor, column++, row);
				GridPane.setMargin(anchor, new Insets(20));
					
				// Set grid height
				tripsGrid.setMaxHeight(Region.USE_PREF_SIZE);
				tripsGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
				tripsGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
					
				// Set grid width
				tripsGrid.setMaxWidth(Region.USE_PREF_SIZE);
				tripsGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
				tripsGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
			}				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		List<TripBean> myTripBeans = ProfileController.getInstance().getMyTrips();
		List<TripBean> upcomingTripBeans = ProfileController.getInstance().getUpcomingTrips();
		List<TripBean> previousTripBeans = ProfileController.getInstance().getRecentTrips();
		loadGrid(upcomingGrid, upcomingTripBeans);
		loadGrid(myTripsGrid, myTripBeans);
		loadGrid(previousGrid, previousTripBeans);
		
	}

	public void setData(UserBean user) {
		txtNameSurname.setText(user.getName()+" "+user.getSurname());
		txtAge.setText(Integer.toString(user.getAge()));
		
	}

}

	
	

