package logic.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import logic.bean.SessionBean;
import logic.bean.TripBean;

public class CardGraphic implements Initializable {
	
	@FXML
    private ImageView imgTrip;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblPrice;
    
    @FXML
    private Label lblCat1;
    
    @FXML
    private Label lblCat2;

    @FXML
    private ImageView imgCat1;

    @FXML
    private ImageView imgCat2;

    @FXML
    private Button btnMore;
    
    /* Beans */
    
    private TripBean tripBean;
    
    private SessionBean session;
    
    public TripBean getTripBean() {
		return tripBean;
	}

	public void setTripBean(TripBean tripBean) {
		this.tripBean = tripBean;
	}

	public void setSession(SessionBean session) {
		System.out.println(session);
		this.session = session;
		System.out.println("Card session: "+session);
	}
    
	/* Action methods */
	@FXML
    void loadTripInfo(MouseEvent event) {
    	UpperNavbarControl.getInstance().loadTripInfo(tripBean, session);
    }
    
    public void setData(TripBean tripBean, SessionBean session) {
//    	Image img = new Image(getClass().getResourceAsStream(tripBean.getImgSrc()));
//    	imgTrip.setImage(img);
    	
    	setTripBean(tripBean);
    	setSession(session);
    	lblTitle.setText(tripBean.getTitle());
    	lblPrice.setText(tripBean.getPrice()+"€");
    	Image catImg = new Image(getClass().getResourceAsStream("/logic/view/images/icons8-greek-pillar-capital-50.png"));
    	imgCat1.setImage(catImg);
    	imgCat2.setImage(catImg);
    	lblCat1.setText(tripBean.getCategory1());
    	lblCat2.setText(tripBean.getCategory2());
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// empty method: nothing to do when initialized
		
	}

}
