package logic.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class HomeGraphic {
	
	@FXML
	private Label lblWelcome;
	
	@FXML
    private Label lblPoints;
	
	@FXML
	private Button btnJoin;

	@FXML
	private Button btnPlan;
	
    @FXML
    private Button btnGain;
	
	/*Action methods */
	
	public void setWelcomeText(String text) {
		lblWelcome.setText(text);
	}
	
	public void setPointsText(String text) {
		lblPoints.setText(text);
	}
	
	@FXML
	void forwardGainPoints(MouseEvent event) {
		btnGain.setDisable(true);
		UpperNavbarControl.getInstance().loadGainPoints();
		btnGain.setDisable(false);
	}
	
	@FXML
	void forwardJoinTrip(MouseEvent event) {
		btnJoin.setDisable(true);
		UpperNavbarControl.getInstance().loadJoinTrip();
		btnJoin.setDisable(false);
	}

	@FXML
	void forwardPlanTrip(MouseEvent event) {
		UpperNavbarControl.getInstance().loadPlanTrip();
	}
}