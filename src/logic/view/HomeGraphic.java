package logic.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeGraphic {
	@FXML
	private Label lblWelcome;
	
	public void setLabelText(String text) {
		lblWelcome.setText(text);
	}
}
