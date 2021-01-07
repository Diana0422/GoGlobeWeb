package logic.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class HomeGraphic {
	@FXML
	private Label lblWelcome;
	
	@FXML
    private Label lblPoints;
	
	/*Action methods */
	
	public void setWelcomeText(String text) {
		lblWelcome.setText(text);
	}
	
	public void setPointsText(String text) {
		lblPoints.setText(text);
	}
	
	@FXML
	void forwardGainPoints(MouseEvent event) {
		UpperNavbarControl.getInstance().loadGainPoints();
	}
	
	@FXML
	void forwardJoinTrip(MouseEvent event) {
		UpperNavbarControl.getInstance().loadJoinTrip();
	}

	@FXML
	void forwardPlanTrip(MouseEvent event) {
		UpperNavbarControl.getInstance().loadPlanTrip();
	}
}