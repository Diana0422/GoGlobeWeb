package logic.view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logic.bean.RequestBean;
import logic.bean.SessionBean;
import logic.control.ManageRequestController;

public class ManageRequestGraphic implements Initializable {
	@FXML
	private VBox incResults;

	@FXML
	private VBox sentResults;
	
	private SessionBean session;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		List<RequestBean> inc = ManageRequestController.getInstance().getUserIncomingRequests(this.getSession());
		List<RequestBean> sent = ManageRequestController.getInstance().getUserSentRequests(this.getSession());
		
		try {
			for (int i=0; i<inc.size(); i++) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/logic/view/RequestItem.fxml"));
				
				AnchorPane anchor = loader.load();
				RequestItemGraphic ric = loader.getController();
				ric.setData(inc.get(i).getTripTitle(), inc.get(i).getSenderName(), inc.get(i).getSenderSurname());
				
				incResults.getChildren().add(anchor);
			}
			
			for (int i=0; i<sent.size(); i++) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/logic/view/RequestItem.fxml"));
				
				AnchorPane anchor = loader.load();
				RequestItemGraphic ric = loader.getController();
				ric.setData(inc.get(i).getTripTitle(), inc.get(i).getSenderName(), inc.get(i).getSenderSurname());
				
				sentResults.getChildren().add(anchor);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public SessionBean getSession() {
		return session;
	}

	public void setSession(SessionBean session) {
		this.session = session;
	}
}
