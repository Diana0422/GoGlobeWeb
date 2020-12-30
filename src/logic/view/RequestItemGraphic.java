package logic.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logic.model.Trip;
import logic.model.User;

public class RequestItemGraphic implements Initializable {

    @FXML
    private Button btnProfile;

    @FXML
    private Button btnAccept;

    @FXML
    private Button btnDecline;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblUser;

    @FXML
    private Label lblAge;
    
    public void setData(Trip trip, User user) {
    	lblTitle.setText(trip.getTitle());
    	lblUser.setText(user.getName()+" "+user.getSurname());
    }
    
    public void accept(ActionEvent event) {
    	//TODO dummy
    }
    
    public void viewProfile(ActionEvent event) {
    	//TODO dummy
    }
    
    public void decline(ActionEvent event) {
    	//TODO dummy
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// empty
	}
}