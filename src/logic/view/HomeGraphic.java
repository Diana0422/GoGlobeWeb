package logic.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HomeGraphic {
	@FXML
	private Label lblWelcome;
	
	public void setLabelText(String text) {
		lblWelcome.setText(text);
	}
	
	public void forwardJoinTrip(ActionEvent event) {
		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("JoinTrip.fxml"));
			Parent root;
			root = loader.load();
			Scene scene = new Scene(root,1024,600);
			scene.getStylesheets().addAll(getClass().getResource("application.css").toExternalForm(), getClass().getResource("joinTrip.css").toExternalForm());
			
			/* Set and show the new scene */
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void forwardPlanTrip(ActionEvent event) {

		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/view/JoinTrip.fxml"));
			Parent root;
			root = loader.load();
			Scene scene = new Scene(root,1024,600);
			scene.getStylesheets().addAll(getClass().getResource("/logic/view/css/application.css").toExternalForm(), getClass().getResource("/logic/view/css/joinTrip.css").toExternalForm());
			
			/* Set and show the new scene */
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}