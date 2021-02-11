package logic.view.control;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logic.bean.RequestBean;
import logic.control.ManageRequestController;
import logic.model.exceptions.LoadGraphicException;
import logic.persistence.exceptions.DatabaseException;
import logic.util.Session;
import logic.view.control.dynamic.RequestItemGraphic;
import logic.view.utils.GraphicControl;
import logic.view.utils.RequestType;

public class ManageRequestGraphic implements GraphicControl {
	@FXML
	private VBox incResults;

	@FXML
	private VBox sentResults;

	private Session session;
	
	private void displayRequests(RequestType type, RequestBean bean) {
		AnchorPane anchor;
		RequestItemGraphic graphic = new RequestItemGraphic();
		if (type == RequestType.INCOMING) {
			try {
				anchor = (AnchorPane) graphic.initializeNode(bean, this.incResults, type);
				incResults.getChildren().add(anchor);
			} catch (LoadGraphicException e) {
				AlertGraphic alert = new AlertGraphic();
				alert.display(e.getMessage(), e.getCause().toString());
			}
		} else {
			try {
				graphic.setContainer(this.sentResults);
				anchor = (AnchorPane) graphic.initializeNode(bean, this.sentResults, type);
				sentResults.getChildren().add(anchor);
			} catch (LoadGraphicException e) {
				AlertGraphic alert = new AlertGraphic();
				alert.display(e.getMessage(), e.getCause().toString());
			}
		}
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		List<RequestBean> inc;
		ManageRequestController controller = new ManageRequestController();
		try {
			inc = controller.getUserIncomingRequests(session.getUserEmail());
			
			if (inc != null) {
				for (int i=0; i<inc.size(); i++) {
					displayRequests(RequestType.INCOMING, inc.get(i));
				}
			}
			
			List<RequestBean> sent = controller.getUserSentRequests(session.getUserEmail());
				
			if (sent != null) {
				for (int i=0; i<sent.size(); i++) {
					displayRequests(RequestType.SENT, sent.get(i));
				}
			}
		} catch (DatabaseException e) {
			AlertGraphic alert = new AlertGraphic();
			alert.display(e.getMessage(), e.getCause().toString());
		}	
	}


	@Override
	public void setSession(Session session) {
		this.session = session;
	}
	
}
