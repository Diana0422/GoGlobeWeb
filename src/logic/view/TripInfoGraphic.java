package logic.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class TripInfoGraphic {
	@FXML
	private TabPane tabPane;
	
	@FXML
	private Label lblTitle;
	
	@FXML
	private Label lblPrice;
	
	@FXML
	private Label lblDescription;
	
	@FXML
	private Label lblDeparture;
	
	@FXML
	private Label lblReturn;
	
	@FXML
	private Label lblCategory1;
	
	@FXML
	private Label lblCategory2;
	
	@FXML
	private ImageView imgTrip;
	
	@FXML
	private ImageView imgCategory1;
	
	@FXML
	private ImageView imgCategory2;
	
	public void setTitleText(String text) {
		lblTitle.setText(text);
	}
	
	public void setPriceText(String text) {
		lblPrice.setText(text);
	}
	
	public void setDescriptionText(String text) {
		lblDescription.setText(text);
	}
	
	public void setDepartureText(String text) {
		lblDeparture.setText(text);
	}
	
	public void setReturnText(String text) {
		lblReturn.setText(text);
	}
	
	public void setCategory1Text(String text) {
		lblCategory1.setText(text);
	}
	
	public void setCategory2Text(String text) {
		lblCategory2.setText(text);
	}
	
	public void setTripImg(Image tripImg) {
		imgTrip.setImage(tripImg);
	}
	
	public void setCategory1Image(Image cat1Img) {
		imgCategory1.setImage(cat1Img);
	}
	
	public void setCategory2Image(Image cat2Img) {
		imgCategory2.setImage(cat2Img);
	}
	
	public void addDayTabs(int dayNum) {
		for (int i=0; i<dayNum; i++) {
			AnchorPane pane = new AnchorPane();
			pane.setId("tab-container");
//			TODO pane.displayActivityList();
			Tab tab = new Tab("Day "+Integer.toString(i+1), pane);
			tabPane.getTabs().add(tab);
		}
	}
	
	
	public void joinTrip(ActionEvent e) {
		//TODO
	}
}