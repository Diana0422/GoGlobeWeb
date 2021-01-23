package logic.view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import logic.bean.TripBean;
import logic.control.GainPointsController;
import logic.model.exceptions.LoadGraphicException;
import logic.persistence.exceptions.DatabaseException;

public class GainPointsGraphic implements GraphicController {
	
	@FXML
	private GridPane cardsLayout;

	@FXML
	private AnchorPane alertPane;

    @FXML
    private Label lblPoints;
    
    private TripBean trip;
    
    private Object bundle;

    @FXML
    private Button btnBack;

    @FXML
    void back(MouseEvent event) {
    	DesktopSessionContext.getGuiLoader().loadGUI(null, this.bundle, null);
    }

	@FXML
	void onGainPoints(MouseEvent event) {
		try {
			if (GainPointsController.getInstance().verifyParticipation(DesktopSessionContext.getInstance().getSession(), getTrip())) {
				AlertGraphic graphic = new AlertGraphic();
				graphic.display(GUIType.GAIN, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(),  "Trip validated successfully.", "You gained 100 points");
			
			} else {
				AlertGraphic graphic = new AlertGraphic();
				graphic.display(GUIType.GAIN, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Can't validate trip.", "You don't gain any points");
			}
		} catch (DatabaseException e) {
			AlertGraphic alert = new AlertGraphic();
			alert.display(GUIType.GAIN, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), e.toString(), e.getMessage());
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
		try {
			setTrip(GainPointsController.getInstance().getTripOfTheDay(DesktopSessionContext.getInstance().getSession().getSessionEmail()));
		} catch (DatabaseException e) {
			AlertGraphic alert = new AlertGraphic();
			alert.display(GUIType.GAIN, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), e.getMessage(), e.getCause().toString());
		}
		if (getTrip() != null) {
			int column = 0;
			int row = 1;
		
			CardGraphic cc = new CardGraphic();
			AnchorPane anchor;
			try {
				anchor = (AnchorPane) cc.initializeNode(trip);
				cardsLayout.add(anchor, column, row);
				GridPane.setMargin(anchor, new Insets(20));
			} catch (LoadGraphicException e) {
				AlertGraphic graphic = new AlertGraphic();
				graphic.display(GUIType.JOIN, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Widget loading error.", "Something unexpected occurred loading the trip card.");
			}
								
			// Set grid height
			cardsLayout.setMaxHeight(Region.USE_PREF_SIZE);
			cardsLayout.setPrefHeight(Region.USE_COMPUTED_SIZE);
			cardsLayout.setMinHeight(Region.USE_COMPUTED_SIZE);
								
			// Set grid width
			cardsLayout.setMaxWidth(Region.USE_PREF_SIZE);
			cardsLayout.setPrefWidth(Region.USE_COMPUTED_SIZE);
			cardsLayout.setMinWidth(Region.USE_COMPUTED_SIZE);
		}
					
	}

	@Override
	public void initializeData(Object recBundle, Object forBundle) {
		loadTrip();
		bundle = forBundle;
		lblPoints.setText("You have "+DesktopSessionContext.getInstance().getSession().getSessionPoints()+" points.");
	}
				
}
