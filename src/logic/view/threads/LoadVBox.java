package logic.view.threads;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logic.model.Place;
import logic.view.SuggestionCardGraphic;

public class LoadVBox implements Runnable{
	
	private VBox vbox;
	List<Place> places;
	
	public LoadVBox(VBox vbox, List<Place> places) {
		this.vbox = vbox;
		this.places = places;
	}

	@Override
	public void run() {
		for (int i = 0; i < places.size(); i++) {
			loadSuggestion(places.get(i));
		}
	}
	
	
	//load suggestion in the GUI
		private void loadSuggestion(Place place) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/logic/view/PlaceSuggestion.fxml"));
				
			try {
				AnchorPane anchor = loader.load();
				SuggestionCardGraphic controller = loader.getController();
				controller.setData(place);
				vbox.getChildren().add(anchor);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

}
