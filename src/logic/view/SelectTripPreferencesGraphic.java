package logic.view;

import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import logic.bean.PlanTripBean;
import logic.bean.TripBean;
import logic.control.ConversionController;
import logic.control.PlanTripController;
import logic.model.TripCategory;
import logic.model.exceptions.FormInputException;

public class SelectTripPreferencesGraphic implements GraphicController {

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
    	TripBean tripBean = new TripBean();
    	tripBean.setTitle(tfTripTitle.getText());
    	tripBean.setDepartureDate(tfDepartureDate.getText());
    	tripBean.setReturnDate(tfReturnDate.getText());
    	tripBean.setCategory1(cbCategory1.getValue());
    	tripBean.setCategory2(cbCategory2.getValue());
    	Date depDate = ConversionController.getInstance().parseDate(tripBean.getDepartureDate());
		Date retDate = ConversionController.getInstance().parseDate(tripBean.getReturnDate());
    	long tripLength = PlanTripController.getInstance().calculateTripLength(depDate, retDate) + 1;
    	tripBean.setTripLength(tripLength);
    	tripBean.createDays();
    	planTripBean.setTripBean(tripBean);
    	
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
		TripCategory[] categories = TripCategory.values();
		for (int i = 0; i < categories.length; i++) {
			cbCategory1.getItems().add((categories[i].toString()));
			cbCategory2.getItems().add((categories[i].toString()));			
		}
		cbCategory1.setValue("NONE");
		cbCategory2.setValue("NONE");
		
	}
	

}
