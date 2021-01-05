package logic.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class HomeGraphic {
	@FXML
	private Label lblWelcome;
	
	/*Action methods */
	
	public void setLabelText(String text) {
		lblWelcome.setText(text);
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