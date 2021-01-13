package logic.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AlertGraphic {
	
	 @FXML
	 private Label lblAlertMessage;

	 @FXML
	 private Label lblAlertDescription;

	 @FXML
	 private Button btnOption1;

	 @FXML
	 private Button btnOption2;
	 
	 public void setData(String message, String description, String buttonOption1, String buttonOption2) {
		 lblAlertMessage.setText(message);
		 lblAlertDescription.setText(description);
		 btnOption1.setText(buttonOption1);
		 btnOption1.setMaxWidth(buttonOption1.length());
		 btnOption2.setText(buttonOption2);
		 btnOption2.setMaxWidth(buttonOption2.length());
		 
		 
//		 btnOption1.setOnAction(e -> 
////			 UpperNavbarControl.getInstance().loadUI(buttonOption1)
//		 );
//		 
//		 btnOption2.setOnAction(e ->
//			 UpperNavbarControl.getInstance().loadUI(buttonOption2)
//		 );
	 }

}
