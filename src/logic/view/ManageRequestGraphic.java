package logic.view;

import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logic.bean.RequestBean;
import logic.control.ManageRequestController;

public class ManageRequestGraphic implements GraphicController {
	@FXML
	private VBox incResults;

	@FXML
	private VBox sentResults;


	@Override
	public void initializeData(Object bundle) {
		List<RequestBean> inc = ManageRequestController.getInstance().getUserIncomingRequests(DesktopSessionContext.getInstance().getSession());
		List<RequestBean> sent = ManageRequestController.getInstance().getUserSentRequests(DesktopSessionContext.getInstance().getSession());
		
		try {
			if (inc != null) {
				for (int i=0; i<inc.size(); i++) {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/logic/view/RequestItem.fxml"));
					
					AnchorPane anchor = loader.load();
					RequestItemGraphic ric = loader.getController();
					ric.setData(inc.get(i));
					
					incResults.getChildren().add(anchor);
				}
			}
			
			if (sent != null) {
				for (int i=0; i<sent.size(); i++) {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/logic/view/RequestItem.fxml"));
					
					AnchorPane anchor = loader.load();
					RequestItemGraphic ric = loader.getController();
					ric.setData(sent.get(i));
					
					sentResults.getChildren().add(anchor);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
