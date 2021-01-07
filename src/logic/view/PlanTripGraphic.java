package logic.view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.bean.ActivityBean;
import logic.bean.PlanTripBean;

public class PlanTripGraphic implements Initializable{
	
	PlanTripBean planTripBean;

    @FXML
    private VBox vbDays;

    @FXML
    private Text txtTripTitle;

    @FXML
    private Text txtDayNumber;

    @FXML
    private TextField tfLocation;

    @FXML
    private VBox vbActivities;
    
    @FXML	
    private ScrollPane spActivities;

    @FXML
    private Button btnSaveLocation;

    @FXML
    private TextField tfActivityTitle;

    @FXML
    private TextField tfActivityTime;
    
    @FXML
    private TextField tfActivityCost;

    @FXML
    private TextArea taActivityDescription;

    @FXML
    private Button btnAddActivity;
    
    @FXML
    private Button btnSaveTrip;
    
    @FXML
    private Button btnShareTrip;

    @FXML
    private Text txtLocation;
    
    @FXML	
    private Text txtLocationForm;
    
    @FXML	
    private AnchorPane apActivityForm;

    @FXML
    void addActivity(ActionEvent event) {
    	ActivityBean newActivity = new ActivityBean();
    	newActivity.setTitle(tfActivityTitle.getText());
    	newActivity.setTime(tfActivityTime.getText());
    	newActivity.setDescription(taActivityDescription.getText());
    	newActivity.setEstimatedCost(tfActivityCost.getText());
 
    	if (newActivity.validateActivity()) {
    		planTripBean.addActivity(newActivity);
    		loadActivity(newActivity);
    	}	
    }

    @FXML
    void saveLocation(ActionEvent event) {
    	planTripBean.setLocation(tfLocation.getText());
    	if (planTripBean.validateLocation())
    		planTripBean.saveLocation();
    	refresh();
    }
    
    @FXML
    void onSaveTripClick(ActionEvent event) {
    	if (planTripBean.validateTrip()) {
    		planTripBean.saveTrip(UpperNavbarControl.getInstance().getSession()); //EHHHHH QUANDO VIENE INIZIALIZZATA?
    		UpperNavbarControl.getInstance().loadHome("Welcome "+UpperNavbarControl.getInstance().getSession().getName()+" "+UpperNavbarControl.getInstance().getSession().getSurname());	
    	}
    }
    
    @FXML
    void onShareTripClick(ActionEvent event) {
    	if (planTripBean.validateTrip()) {
    		UpperNavbarControl.getInstance().loadShareTrip(this.planTripBean);
    	}    }
	
	public void initPlanTripBean(PlanTripBean planTripBean) {
		this.planTripBean = planTripBean;
		
		String logStr = "TRIP TITLE: " + this.planTripBean.getTripName();
		Logger.getGlobal().info(logStr);
		
		//set GUI elements visibility
		txtTripTitle.setText(planTripBean.getTripName());
		txtDayNumber.setText(Integer.toString(planTripBean.getPlanningDay() + 1));
		if (planTripBean.checkDay()) {
			logStr = "THIS DAY HAS NO LOCATION";
			Logger.getGlobal().info(logStr);

			txtLocationForm.setVisible(true);					
			tfLocation.setVisible(true);
			btnSaveLocation.setVisible(true);
			txtLocation.setVisible(false);
			apActivityForm.setVisible(false);
			vbActivities.setVisible(false);	
			spActivities.setVisible(false);
			
		}else{
			logStr = "LOCATION FOUND: SHOWING THINGS.";
			Logger.getGlobal().info(logStr);

			txtLocationForm.setVisible(false);
			tfLocation.setVisible(false);
			btnSaveLocation.setVisible(false);
			txtLocation.setVisible(true);
			apActivityForm.setVisible(true);
			vbActivities.setVisible(true);
			spActivities.setVisible(true);
			
			txtLocation.setText("Location: " +planTripBean.getDayLocation());
			
			//adds day's activities to the GUI
			List<ActivityBean> activities = planTripBean.getTripDays().get(planTripBean.getPlanningDay()).getActivities();
			for (int i = 0; i < activities.size(); i++) {
				loadActivity(activities.get(i));						
			}
		}
		
		//setup side bar buttons
		for (int i = 0; i < planTripBean.getTripDays().size(); i++) {
			Button btnDay = new Button("Day " + (i+1));
			btnDay.setPrefHeight(50);
			btnDay.setPrefWidth(210);
			btnDay.setFont(new Font(18));					
			btnDay.setId("btn");
			
			//setting event handler for button click
			btnDay.setUserData(i);
			btnDay.setOnAction( event -> {
				planTripBean.setPlanningDay((int)btnDay.getUserData());
				System.out.println("clicked day button: " + (int)btnDay.getUserData());
				refresh();					
			});					
			vbDays.getChildren().add(btnDay);
		}
	}
	
	//refresh the scene
	private void refresh() {
		UpperNavbarControl.getInstance().loadUI("/logic/view/PlanTrip", this.planTripBean);
	}
	
	//load an activity in GUI
	private void loadActivity(ActivityBean activityBean) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/logic/view/ActivityCard.fxml"));
			
		try {
			AnchorPane anchor = loader.load();
			ActivityCardGraphic controller = loader.getController();
			controller.setData(activityBean);
			vbActivities.getChildren().add(anchor);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//empty
	}
	
	
}

