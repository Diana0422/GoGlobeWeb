package logic.view.control.dynamic;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import logic.bean.RequestBean;
import logic.control.ManageRequestController;
import logic.model.exceptions.LoadGraphicException;
import logic.persistence.exceptions.DatabaseException;
import logic.view.control.AlertGraphic;
import logic.view.utils.RequestType;

public class RequestItemGraphic implements Initializable {

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
    
    @FXML
    private Label lblStatus;
    
    private RequestBean request;
    private ManageRequestController controller;
    private VBox container;
    
    public RequestBean getRequest() {
		return request;
	}

	public void setRequest(RequestBean request) {
		this.request = request;
	}
    
    private void setData(RequestBean bean, RequestType type) {
    	setRequest(bean);
    	
    	if (type == RequestType.INCOMING) {
    		lblTitle.setText(bean.getTripTitle());
	    	lblUser.setText(bean.getSenderName()+" "+bean.getSenderSurname());
	    	lblAge.setText("Age: "+bean.getSenderAge());
    	} else {
    		lblTitle.setText(bean.getTripTitle());
    		lblUser.setText(bean.getReceiverName()+" "+bean.getReceiverSurname());
    		lblAge.setVisible(false);
    		btnAccept.setVisible(false);
    		btnDecline.setVisible(false);
    	}
    }
    
    @FXML
    public void accept(MouseEvent event) {
    	try {
			controller.acceptRequest(getRequest());
	    	lblStatus.setVisible(true);
	    	lblStatus.setText("Accepted");
	    	lblStatus.setStyle("-fx-text-fill: green;");
	    	System.out.println(container);
	    	System.out.println(container.getChildren());
	    	System.out.println(btnAccept.getParent());
	    	container.getChildren().remove(btnAccept.getParent().getParent());
		} catch (DatabaseException e) {
			AlertGraphic alert = new AlertGraphic();
			alert.display(e.getMessage(), e.getCause().toString());
		}
    }
   
    
    @FXML
    public void decline(MouseEvent event) {
    	try {
			controller.declineRequest(getRequest());
	    	lblStatus.setVisible(true);
	    	lblStatus.setText("Declined");
	    	lblStatus.setStyle("-fx-text-fill: red;");
	    	container.getChildren().remove(btnDecline.getParent().getParent());
		} catch (DatabaseException e) {
			AlertGraphic alert = new AlertGraphic();
			alert.display(e.getMessage(), e.getCause().toString());
		}
    }
    
    public Node initializeNode(RequestBean bean, VBox container, RequestType type) throws LoadGraphicException {
    	FXMLLoader itemLoader = new FXMLLoader();
		itemLoader.setLocation(getClass().getResource("/logic/view/res/fxml/dynamic/RequestItem.fxml"));
		
		Node node;
		try {
			node = itemLoader.load();
			RequestItemGraphic ric = itemLoader.getController();
			ric.setData(bean, type);
			ric.setContainer(container);
			return node;
		} catch (IOException e) {
			throw new LoadGraphicException(e.getMessage(), e);
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.controller = new ManageRequestController();
	}

	public VBox getContainer() {
		return container;
	}

	public void setContainer(VBox container) {
		this.container = container;
	}
}