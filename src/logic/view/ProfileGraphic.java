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
import logic.bean.TripBean;
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
    
    private List<TripBean> myTripBeans;
    private List<TripBean> upcomingTripBeans;
    private List<TripBean> previousTripBeans;


    
	private void loadGrid(GridPane tripsGrid, List<TripBean> trips ) {
		
		int column = 0;
		int row = 1;
		
		try {
			for (int i=0; i<trips.size(); i++) {
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/logic/view/TripCard.fxml"));
					
				AnchorPane anchor = loader.load();
					
				CardGraphic cc = loader.getController();
				cc.setData(trips.get(i));
					
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
	
//	private boolean isFiltered(TripBean bean, String searchVal) {	// Da aggiungere a interfaccia di filtri
//		return (bean.getTitle().toLowerCase().contains(searchVal) || bean.getCategory1().contains(searchVal) || bean.getCategory2().contains(searchVal));
//	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		myTripBeans = ProfileController.getInstance().getMyTrips();
		upcomingTripBeans = ProfileController.getInstance().getUpcomingTrips();
		previousTripBeans = ProfileController.getInstance().getRecentTrips();
		loadGrid(upcomingGrid, upcomingTripBeans);
		loadGrid(myTripsGrid, myTripBeans);
		loadGrid(previousGrid, previousTripBeans);
		
	}




}

	
	

