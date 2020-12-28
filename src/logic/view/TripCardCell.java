package logic.view;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logic.model.Trip;

public class TripCardCell extends ListCell<Trip>{
	private Button actionBtn;
	private Label lblTitle;
	private Label lblPrice;
	private HBox hbox = new HBox();
	
	
	public TripCardCell() {
		super();
		
		actionBtn = new Button("More Info");
		actionBtn.setId("btn");
		actionBtn.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			
				try {
					System.out.println("Action: "+getItem());
					Trip item = getItem();
					
					Stage primaryStage = new Stage();
					FXMLLoader loader = new FXMLLoader(getClass().getResource("TripInfo.fxml"));
					Parent root;
					root = loader.load();
					Scene scene = new Scene(root,1024,600);
					scene.getStylesheets().addAll(getClass().getResource("application.css").toExternalForm(), getClass().getResource("info.css").toExternalForm());
					
					/* Set the label and images in the trip info view */
					TripInfoGraphic c = loader.getController();
					c.setTitleText(item.getTitle());
					c.setPriceText(Integer.toString(item.getPrice()));
//					TODO c.setDescriptionText(item.getDescription());
					
					DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
					String strDeparture = df.format(item.getDepartureDate());
					String strReturn = df.format(item.getReturnDate());
					
					c.setDepartureText(strDeparture);
					c.setReturnText(strReturn);
					
//					TODO c.setTripImg(item.getImg());
//					TODO c.setCategory1Image(ImagePairing.getCategoryImg(item.getCategory1()));
//					TODO c.setCategory2Image(ImagePairing.getCategoryImg(item.getCategory2()));
					c.setCategory1Text(item.getCategory1().toString());
					c.setCategory2Text(item.getCategory2().toString());
					
					/* Add a new tab to the tab pane for each day (example 3 days) */
					c.addDayTabs(3);
					
					/* Set and show the new scene */
					primaryStage.setScene(scene);
					primaryStage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		});
		
		lblTitle = new Label();
		lblPrice = new Label();
		hbox.setPadding(new Insets(15, 12, 15, 12));
	    hbox.setSpacing(30);
		hbox.getChildren().addAll(lblTitle, lblPrice, actionBtn);
		setText(null);
	}
	
	@Override
	public void updateItem(Trip item, boolean empty) {
		super.updateItem(item, empty);
		setEditable(false);
		setText(null);
		setGraphic(null);
		
		if (item != null && !empty) {
            lblTitle.setText(item.getTitle());
            lblPrice.setText("Price: "+item.getPrice());
            setGraphic(hbox);
        }
	}
}