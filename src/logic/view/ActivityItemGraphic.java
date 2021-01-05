package logic.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import logic.bean.ActivityBean;

public class ActivityItemGraphic implements Initializable {
	
    @FXML
    private Label lblActivityName;

    @FXML
    private Label lblActivityTime;

    @FXML
    private Label lblDescription;
    
    private void setTextActivityName(String text) {
    	lblActivityName.setText(text);
    }
    
    private void setTextActivityTime(String text) {
    	lblActivityTime.setText(text);
    }
    
    private void setTextActivityDescription(String text) {
    	lblDescription.setText(text);
    }
    
    public void setData(ActivityBean bean) {
    	setTextActivityName(bean.getTitle());
    	setTextActivityTime(bean.getTime());
    	setTextActivityDescription(bean.getDescription());
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// empty method: nothing to do when initialized
		
	}
}
