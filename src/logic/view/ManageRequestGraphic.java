package logic.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logic.model.Request;
import logic.model.Trip;
import logic.model.User;

public class ManageRequestGraphic implements Initializable {
	@FXML
	private VBox incResults;

	@FXML
	private VBox sentResults;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		List<Request> inc = new ArrayList<>(incomingRequests());
		
		try {
			for (int i=0; i<inc.size(); i++) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/application/cards/RequestItem.fxml"));
				
				AnchorPane anchor = loader.load();
				RequestItemGraphic ric = loader.getController();
				ric.setData(inc.get(i).getTarget(), inc.get(i).getSender());
				
				incResults.getChildren().add(anchor);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Request> incomingRequests() {
		List<Request> ls = new ArrayList<>();
		Request req = new Request();
		User sender = new User();
		Trip target = new Trip();
		
		req.setId(1);
		sender.setName("Davide");
		sender.setSurname("Falessi");
		req.setSender(sender);
		target.setTitle("Il viaggio.");
		req.setTarget(target);
		ls.add(req);
		
		req = new Request();
		sender = new User();
		target = new Trip();
		req.setId(2);
		sender.setName("Linda");
		sender.setSurname("Pinta");
		req.setSender(sender);
		target.setTitle("Il viaggio.");
		req.setTarget(target);
		ls.add(req);
		
		req = new Request();
		sender = new User();
		target = new Trip();
		req.setId(3);
		sender.setName("Pippo");
		sender.setSurname("Baudo");
		req.setSender(sender);
		target.setTitle("Il viaggio.");
		req.setTarget(target);
		ls.add(req);
		
		req = new Request();
		sender = new User();
		target = new Trip();
		req.setId(4);
		sender.setName("Gaia");
		sender.setSurname("Pasquali");
		req.setSender(sender);
		target.setTitle("Il viaggio.");
		req.setTarget(target);
		ls.add(req);
		
		req = new Request();
		sender = new User();
		target = new Trip();
		req.setId(4);
		sender.setName("Lorenzo");
		sender.setSurname("Tanzi");
		req.setSender(sender);
		target.setTitle("Il viaggio.");
		req.setTarget(target);
		ls.add(req);
		
		return ls;
	}
}
