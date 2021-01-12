package logic.view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

//TODO FARE IN MODO CHE LE VIEW PRENDANO IL VALORE DI ESTIMATEDCOST E INCLUDERE IL VALIDATE-COST NEL VALIDATE ACTIVITY

public class ShareTripGraphic implements Initializable {
	
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
    void onCancelClick(ActionEvent event) {
		UpperNavbarControl.getInstance().loadPlanTrip(this.planTripBean);

    }

    @FXML
    void onNextClick(ActionEvent event) {
    	planTripBean.setMinAge(tfMinimumAge.getText());
    	planTripBean.setMaxAge(tfMaximumAge.getText());
    	planTripBean.setTripDescription(taTripDescription.getText());
    	planTripBean.setMaxParticipants(tfMaxParticipants.getText());

    	if (planTripBean.validateSharingPref()){
    		//DELETEME: vecchia alternativa -> chiamare setSharingPreferences dal planTripBean 
    		PlanTripController.getInstance().setSharingPreferences(planTripBean);
    		PlanTripController.getInstance().saveTrip(planTripBean.getTripBean(), UpperNavbarControl.getInstance().getSession()); 
			System.out.println("VIAGGIO SALVATO COME CONDIVISO");
			
			UpperNavbarControl.getInstance().loadHome();
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
    
    public void initPlanTripBean(PlanTripBean planTripBean) {
    	this.planTripBean = planTripBean;
    	System.out.println("PROVA"  + this.planTripBean.getReturnDate());
    }
    
    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException e) {
           //TODO exception
        }
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image icAddImage = new Image("/logic/view/images/" + IC_ADDIMAGE);
		ImageView imgView = new ImageView(icAddImage);
		btnChooseFile.setGraphic(imgView);
	}

}
