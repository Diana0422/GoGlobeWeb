package logic.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import logic.bean.SessionBean;

public class HomeGraphic implements GraphicController {
	
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
	
	@FXML
	void forwardGainPoints(MouseEvent event) {
		btnGain.setDisable(true);
		DesktopSessionContext.getGuiLoader().loadGUIStateful(null, DesktopSessionContext.getInstance().getSession(), GUIType.GAIN, GUIType.HOME);
		btnGain.setDisable(false);
	}
	
	@FXML
	void forwardJoinTrip(MouseEvent event) {
		btnJoin.setDisable(true);
		DesktopSessionContext.getGuiLoader().loadGUI(null, null, GUIType.JOIN);
		btnJoin.setDisable(false);
	}

	@FXML
	void forwardPlanTrip(MouseEvent event) {
		DesktopSessionContext.getGuiLoader().loadGUI(null, null, GUIType.PREFTRIP);
	}


	@Override
	public void initializeData(Object recBundle, Object forBundle) {
		System.out.println("3 Using bundle:"+ recBundle);
		System.out.println("3 Forwarding bundle: "+forBundle);
		SessionBean session = (SessionBean) recBundle;
		lblWelcome.setText("Welcome "+session.getName()+" "+session.getSurname());
		lblPoints.setText(Integer.toString(session.getPoints()));
	}
}