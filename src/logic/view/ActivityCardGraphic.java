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
    
    public void setData(ActivityBean activity) {
    	txtTitle.setText(activity.getTitle());
    	txtTime.setText(activity.getTime());
    	txtDescription.setText(activity.getDescription());
    }

}
