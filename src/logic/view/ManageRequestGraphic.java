package logic.view;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logic.bean.RequestBean;
import logic.control.ManageRequestController;
import logic.model.exceptions.LoadGraphicException;
import logic.persistence.exceptions.DBConnectionException;

public class ManageRequestGraphic implements GraphicController {
	@FXML
	private VBox incResults;

	@FXML
	private VBox sentResults;


	@Override
	public void initializeData(Object recBundle, Object forBundle) {
		List<RequestBean> inc;
		try {
			inc = ManageRequestController.getInstance().getUserIncomingRequests(DesktopSessionContext.getInstance().getSession());
			
			if (inc != null) {
				for (int i=0; i<inc.size(); i++) {
					displayRequests(RequestType.INCOMING, inc.get(i));
				}
			}
			
			List<RequestBean> sent = ManageRequestController.getInstance().getUserSentRequests(DesktopSessionContext.getInstance().getSession());
				
			if (sent != null) {
				for (int i=0; i<sent.size(); i++) {
					displayRequests(RequestType.SENT, sent.get(i));
				}
			}
		} catch (DBConnectionException e) {
			AlertGraphic alert = new AlertGraphic();
			alert.display(GUIType.REQUESTS, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Database connection error", "Please retry later.");
		}	
	}
	
	private void displayRequests(RequestType type, RequestBean bean) {
		AnchorPane anchor;
		RequestItemGraphic graphic = new RequestItemGraphic();
		if (type == RequestType.INCOMING) {
			try {
				anchor = (AnchorPane) graphic.initializeNode(bean, type);
				incResults.getChildren().add(anchor);
			} catch (LoadGraphicException e) {
				AlertGraphic alert = new AlertGraphic();
				alert.display(GUIType.REQUESTS, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Widget loading error.", "Something unexpected occurred loading the requests.");
			}
		} else {
			try {
				anchor = (AnchorPane) graphic.initializeNode(bean, type);
				sentResults.getChildren().add(anchor);
			} catch (LoadGraphicException e) {
				AlertGraphic alert = new AlertGraphic();
				alert.display(GUIType.REQUESTS, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Widget loading error.", "Something unexpected occurred loading the requests.");
			}
		}
	}
	
}
