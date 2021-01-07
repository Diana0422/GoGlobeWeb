package logic.view;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import logic.bean.ActivityBean;

public class ActivityCardGraphic {

    @FXML
    private Text txtDescription;

    @FXML
    private Text txtTitle;

    @FXML
    private Text txtTime;
    
    @FXML
    private Text txtActivityCost;
    
    
    
    public void setData(ActivityBean activity) {
    	txtTitle.setText(activity.getTitle());
    	txtTime.setText(activity.getTime());
    	txtActivityCost.setText(activity.getEstimatedCost());
    	txtDescription.setText(activity.getDescription());
    	
    }

}
