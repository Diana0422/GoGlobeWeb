package logic.view;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logic.bean.RequestBean;
import logic.control.ManageRequestController;
import logic.model.exceptions.LoadGraphicException;

public class ManageRequestGraphic implements GraphicController {
	@FXML
	private VBox incResults;

	@FXML
	private VBox sentResults;


	@Override
	public void initializeData(Object recBundle, Object forBundle) {
		List<RequestBean> inc = ManageRequestController.getInstance().getUserIncomingRequests(DesktopSessionContext.getInstance().getSession());
		List<RequestBean> sent = ManageRequestController.getInstance().getUserSentRequests(DesktopSessionContext.getInstance().getSession());
		AnchorPane anchor;
		if (inc != null) {
			for (int i=0; i<inc.size(); i++) {
				RequestItemGraphic graphic = new RequestItemGraphic();
				try {
					anchor = (AnchorPane) graphic.initializeNode(inc.get(i), RequestType.INCOMING);
					incResults.getChildren().add(anchor);
				} catch (LoadGraphicException e) {
					AlertGraphic alert = new AlertGraphic();
					alert.display(GUIType.REQUESTS, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Widget loading error.", "Something unexpected occurred loading the requests.");
				}
			}
		}
			
		if (sent != null) {
			for (int i=0; i<sent.size(); i++) {
				RequestItemGraphic graphic = new RequestItemGraphic();
				try {
					anchor = (AnchorPane) graphic.initializeNode(sent.get(i), RequestType.SENT);
					sentResults.getChildren().add(anchor);
				} catch (LoadGraphicException e) {
					AlertGraphic alert = new AlertGraphic();
					alert.display(GUIType.REQUESTS, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Widget loading error.", "Something unexpected occurred loading the requests.");
				}
			}
		}
		
	}
}
