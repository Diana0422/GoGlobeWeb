package logic.view;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			/* Set FXML controller factory */
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/logic/view/UpperNavbar.fxml"));
			
			loader.setControllerFactory((Callback<Class<?>, Object>) new Callback<Class<?>, Object>(){
				@Override
				public Object call(Class<?> arg0) {
					return UpperNavbarControl.getInstance();
				}
			});
			Parent root = loader.load();
			
			/* Set the scene */
			Scene scene = new Scene(root,1024,600);
			scene.getStylesheets().addAll(
					getClass().getResource("/logic/view/css/navbar.css").toExternalForm(),
					getClass().getResource("/logic/view/css/login.css").toExternalForm(),
					getClass().getResource("/logic/view/css/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
