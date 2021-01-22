package logic.view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.bean.PlanTripBean;
import logic.control.PlanTripController;
import logic.model.exceptions.FormInputException;

//TODO FARE IN MODO CHE LE VIEW PRENDANO IL VALORE DI ESTIMATEDCOST E INCLUDERE IL VALIDATE-COST NEL VALIDATE ACTIVITY

public class ShareTripGraphic implements GraphicController {
	
	private static final String IC_ADDIMAGE = "icAddImage.png";
	private final FileChooser fileChooser = new FileChooser();
	private Desktop desktop = Desktop.getDesktop();
	PlanTripBean planTripBean;
	

    @FXML
    private TextField tfMaxParticipants;

    @FXML
    private TextField tfMinimumAge;

    @FXML	
    private TextField tfMaximumAge;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnNext;

    @FXML
    private TextArea taTripDescription;

    @FXML
    private Button btnChooseFile;

    @FXML
    private Label lblFilePath;
    
    @FXML 
    private Label lblErrorMsg;

    @FXML
    void onCancelClick(ActionEvent event) {
		DesktopSessionContext.getGuiLoader().loadGUI(null, this.planTripBean, GUIType.PLAN);
    }

    @FXML
    void onNextClick(ActionEvent event) {
    	planTripBean.setMinAge(tfMinimumAge.getText());
    	planTripBean.setMaxAge(tfMaximumAge.getText());
    	planTripBean.setTripDescription(taTripDescription.getText());
    	planTripBean.setMaxParticipants(tfMaxParticipants.getText());
    	
    	
    	try {
			if (planTripBean.validateSharingPref()){ 
				PlanTripController.getInstance().setSharingPreferences(planTripBean);
				PlanTripController.getInstance().saveTrip(planTripBean.getTripBean(), DesktopSessionContext.getInstance().getSession()); 
				
				DesktopSessionContext.getGuiLoader().loadGUI(null, DesktopSessionContext.getInstance().getSession(), GUIType.HOME);
			}
		} catch (FormInputException e) {
			lblErrorMsg.setText(e.getMessage());
		}
    }
    
    @FXML 
    void openFilePicker(ActionEvent event){
    	  fileChooser.getExtensionFilters().addAll(
                  new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG", "*.jpg","*.JPEG", "*.jpeg"),
                  new FileChooser.ExtensionFilter("PNG", "*.png", "*.PNG")
              );
    	 Stage filePickerStage = new Stage();
    	 File file = fileChooser.showOpenDialog(filePickerStage);
         if (file != null) {
             openFile(file);
             lblFilePath.setText(file.getPath());
         }
    }
    
    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException e) {
           //TODO exception
        }
    }

	@Override
	public void initializeData(Object recBundle, Object forBundle) {
		this.planTripBean = (PlanTripBean) recBundle;
    	Image icAddImage = new Image("/logic/view/images/" + IC_ADDIMAGE);
		ImageView imgView = new ImageView(icAddImage);
		btnChooseFile.setGraphic(imgView);
	}

}
