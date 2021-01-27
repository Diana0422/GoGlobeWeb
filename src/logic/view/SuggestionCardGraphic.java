package logic.view;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import logic.model.Place;

public class SuggestionCardGraphic {
	

	private static final String  IC_SUGG_BTN = "googlemaps-icon-50.png";
	private String placeName;

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
    
    @FXML 
    public void lookupPlace(MouseEvent event){
    	String url = "https://maps.google.com/?q=" + placeName.replace(" ", "+");
    	System.out.println(url);
    	try {
			Desktop.getDesktop().browse(new URL(url).toURI());
		} catch (MalformedURLException | URISyntaxException e) {
			AlertGraphic graphic = new AlertGraphic();
			graphic.display(GUIType.JOIN, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "The url is not correct.", e.toString());
		} catch (IOException e) {
			AlertGraphic graphic = new AlertGraphic();
			graphic.display(GUIType.JOIN, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), e.getMessage(), e.toString());
		}
    }
    
    public void setData(Place place) {

    	placeName = place.getName();
    	lblPlaceName.setText(place.getName());
    	String placeAddress = place.getAddress();
    	int ind = placeAddress.indexOf("<");
    	if (ind != -1) {
    		placeAddress = placeAddress.substring(0, ind);
    	}
    	lblPlaceAddress.setText(placeAddress);
    	lblOpeningHours.setText(place.getOpeningHours());
    	Image image = new Image(place.getIcCategoryRef());
    	ivPlaceIcon.setImage(image);
    	Image ic = new Image("/logic/view/images/" + IC_SUGG_BTN);
		ImageView icSuggestionBtn = new ImageView(ic);
		btnAddSuggestion.setGraphic(icSuggestionBtn);
		
    }
    
    

}
