package logic.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import logic.model.Trip;

public class JoinTripGraphic implements Initializable {
	@FXML
	private TextField txtSearch;
	
	@FXML
	private Button btnSearch;
	
	@FXML
	private Button btnPlan;
	
	@FXML
	private Label lblMessage;
	
	@FXML
	private ListView<Trip> tripList;
	
	Trip trip1 = new Trip(1, "That's Amore", "", 200, "Adventure", "Relax", "22/04/2020", "22/05/2020");
	Trip trip2 = new Trip(2, "Japanese Temples", "", 1200, "Adventure", "Culture", "22/04/2020", "22/05/2020");
//	ObservableList<String> listView = FXCollections.observableArrayList("That's amore", "Japanese Temples", "Trip3", "Trip 4", "Trip 5", "Trip 6", "Trip 7", "Trip 8", "Trip 9", "Trip 10", "Trip 11", "Trip 12");
	ObservableList<Trip> listView = FXCollections.observableArrayList(trip1, trip2);
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		tripList.setItems(listView);
		tripList.setCellFactory(param -> new TripCardCell());
		
	}
}
