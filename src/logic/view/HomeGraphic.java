package logic.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class HomeGraphic {
	@FXML
	private Label lblWelcome;
	
	public void setLabelText(String text) {
		lblWelcome.setText(text);
	}
	
	
	@FXML
	void forwardJoinTrip(MouseEvent event) {
			UpperNavbarControl.getInstance().displayJoinTrip(event);
	}

	@FXML
	void forwardPlanTrip(MouseEvent event) {
			UpperNavbarControl.getInstance().displayPlanTrip(event);
	}
}