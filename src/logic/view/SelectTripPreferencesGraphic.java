package logic.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import logic.bean.PlanTripBean;
import logic.bean.TripBean;
import logic.control.PlanTripController;
import logic.model.TripCategory;
import logic.model.exceptions.FormInputException;

public class SelectTripPreferencesGraphic implements GraphicController, Initializable {

    @FXML
    private TextField tfTripTitle;
    
    @FXML
    private Label lblErrorMsg;

    @FXML
    private TextField tfDepartureDate;

    @FXML
    private TextField tfReturnDate;

    @FXML
    private ComboBox<String> cbCategory1;

    @FXML
    private ComboBox<String> cbCategory2;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnNext;

    @FXML
    void onCancelClick(ActionEvent event) {
    	DesktopSessionContext.getGuiLoader().loadGUI(null, DesktopSessionContext.getInstance().getSession(), GUIType.HOME);
    }

    @FXML
    void onNextClick(ActionEvent event) {
    	PlanTripBean planTripBean = new PlanTripBean();
    	planTripBean.setTripName(tfTripTitle.getText());
    	planTripBean.setDepartureDate(tfDepartureDate.getText());
    	planTripBean.setReturnDate(tfReturnDate.getText());
    	planTripBean.setCategory1(cbCategory1.getValue());
    	planTripBean.setCategory2(cbCategory2.getValue());
    	//Create new instance of trip bean and assign it to planTripBean
    	TripBean newTripBean = new TripBean();
    	planTripBean.setTripBean(newTripBean);
    	PlanTripController.getInstance().setupTripBean(planTripBean);
    	
    	//Validate form and load next GUI
    	try {
	    	if (planTripBean.validateForm()) {
	    		DesktopSessionContext.getGuiLoader().loadGUI(null, planTripBean, GUIType.PLAN);
	    	} 
    	}catch (FormInputException e){
    		lblErrorMsg.setText(e.getMessage());
    	}
    }

	@Override
	public void initializeData(Object recBundle, Object forBundle) {
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		TripCategory[] categories = TripCategory.values();
		for (int i = 0; i < categories.length; i++) {
			cbCategory1.getItems().add((categories[i].toString()));
			cbCategory2.getItems().add((categories[i].toString()));			
		}
		cbCategory1.setValue("NONE");
		cbCategory2.setValue("NONE");
		
	}
	

}
