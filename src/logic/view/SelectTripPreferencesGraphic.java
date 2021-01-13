package logic.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import logic.bean.PlanTripBean;
import logic.bean.TripBean;
import logic.control.PlanTripController;
import logic.model.TripCategory;

public class SelectTripPreferencesGraphic implements GraphicController {

    @FXML
    private TextField tfTripTitle;

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
    	if (planTripBean.validateForm()) {
    		DesktopSessionContext.getGuiLoader().loadGUI(null, planTripBean, GUIType.PLAN);
    	}   	
    }

	@Override
	public void initializeData(FXMLLoader loader, Object bundle) {
		TripCategory[] categories = TripCategory.values();
		for (int i = 0; i < categories.length; i++) {
			cbCategory1.getItems().add((categories[i].toString()));
			cbCategory2.getItems().add((categories[i].toString()));			
		}
		cbCategory1.setValue("NONE");
		cbCategory2.setValue("NONE");
	}
	

}
