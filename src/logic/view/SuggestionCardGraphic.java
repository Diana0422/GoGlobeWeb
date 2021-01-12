package logic.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.model.Place;

public class SuggestionCardGraphic {

    @FXML
    private ImageView ivPlaceIcon;

    @FXML
    private Label lblPlaceName;

    @FXML
    private Label lblPlaceAddress;

    @FXML
    private Label lblOpeningHours;

    @FXML
    private Button btnAddSuggestion;
    
    public void setData(Place place) {
    	System.out.println(place.getName());
    	System.out.println(place.getAddress());
    	System.out.println(place.getOpeningHours());

    	lblPlaceName.setText(place.getName());
    	lblPlaceAddress.setText(place.getAddress());
    	lblOpeningHours.setText(place.getOpeningHours());
    	Image image = new Image(place.getIcCategoryRef());
    	ivPlaceIcon.setImage(image);
    }

}
