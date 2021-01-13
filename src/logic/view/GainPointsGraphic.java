package logic.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import logic.bean.TripBean;
import logic.control.GainPointsController;

public class GainPointsGraphic implements GraphicController {
	
	@FXML
	private GridPane cardsLayout;

	@FXML
	private AnchorPane alertPane;

    @FXML
    private Label lblPoints;
    
    private TripBean trip;

	@FXML
	void onGainPoints(MouseEvent event) {
		if (GainPointsController.getInstance().verifyParticipation(DesktopSessionContext.getInstance().getSession(), getTrip())) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/logic/view/Alert.fxml"));
				AnchorPane pane = loader.load();
				AlertGraphic graphic = loader.getController();
				graphic.display(null, GUIType.HOME, DesktopSessionContext.getInstance().getSession(),  "Trip validated successfully.", "You gained 100 points");
				alertPane.getChildren().add(pane);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/logic/view/Alert.fxml"));
				AnchorPane pane = loader.load();
				AlertGraphic graphic = loader.getController();
				graphic.display(null, GUIType.HOME, DesktopSessionContext.getInstance().getSession(), "Can't validate trip.", "You don't gain any points");
				alertPane.getChildren().add(pane);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setPointsText(String text) {
		lblPoints.setText(text);
	}

	public TripBean getTrip() {
		return trip;
	}

	public void setTrip(TripBean trip) {
		this.trip = trip;
	}
	
	public void loadTrip() {
		
		setTrip(GainPointsController.getInstance().getTripOfTheDay(DesktopSessionContext.getInstance().getSession().getEmail()));
		System.out.println(getTrip());
		if (getTrip() != null) {
			int column = 0;
			int row = 1;
		
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/logic/view/TripCard.fxml"));
							
			AnchorPane anchor = null;
			try {
				anchor = loader.load();
				
				CardGraphic cc = loader.getController();
				cc.setData(getTrip());
								
				cardsLayout.add(anchor, column, row);
				GridPane.setMargin(anchor, new Insets(20));
								
				// Set grid height
				cardsLayout.setMaxHeight(Region.USE_PREF_SIZE);
				cardsLayout.setPrefHeight(Region.USE_COMPUTED_SIZE);
				cardsLayout.setMinHeight(Region.USE_COMPUTED_SIZE);
								
				// Set grid width
				cardsLayout.setMaxWidth(Region.USE_PREF_SIZE);
				cardsLayout.setPrefWidth(Region.USE_COMPUTED_SIZE);
				cardsLayout.setMinWidth(Region.USE_COMPUTED_SIZE);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
					
	}

	@Override
	public void initializeData(FXMLLoader loader, Object bundle) {
		loadTrip();
		lblPoints.setText("You have "+DesktopSessionContext.getInstance().getSession().getPoints()+" points.");
		System.out.println("initializing card data");
	}
				
}
