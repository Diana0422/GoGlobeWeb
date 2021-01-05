package logic.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import logic.bean.RequestBean;
import logic.control.ManageRequestController;

public class RequestItemGraphic implements Initializable {

    @FXML
    private Button btnProfile;

    @FXML
    private Button btnAccept;

    @FXML
    private Button btnDecline;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblUser;

    @FXML
    private Label lblAge;
    
    private RequestBean request;
    
    public RequestBean getRequest() {
		return request;
	}

	public void setRequest(RequestBean request) {
		this.request = request;
	}
    
    public void setData(RequestBean bean) {
    	setRequest(bean);
    	lblTitle.setText(bean.getTripTitle());
    	lblUser.setText(bean.getSenderName()+" "+bean.getSenderSurname());
    }
    
    @FXML
    public void accept(MouseEvent event) {
    	ManageRequestController.getInstance().acceptRequest(getRequest());
    }
    
    @FXML
    public void viewProfile(MouseEvent event) {
    	ManageRequestController.getInstance().declineRequest(getRequest());
    }
    
    @FXML
    public void decline(MouseEvent event) {
    	ManageRequestController.getInstance().declineRequest(getRequest());
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// empty
	}
}