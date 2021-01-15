package logic.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import logic.bean.TripBean;
import logic.model.exceptions.LoadGraphicException;

public class CardGraphic implements Initializable {
	
	@FXML
    private ImageView imgTrip;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblPrice;
    
    @FXML
    private Label lblTicketPrice;
    
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

    
    public TripBean getTripBean() {
		return tripBean;
	}

	public void setTripBean(TripBean tripBean) {
		this.tripBean = tripBean;
	}
    
	/* Action methods */
	@FXML
    void loadTripInfo(MouseEvent event) {
		DesktopSessionContext.getGuiLoader().loadGUI(null, getTripBean(), GUIType.INFO);
    }
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// empty method: nothing to do when initialized
		
	}

	private void setData(TripBean tripBean) {
//		Image img = new Image(getClass().getResourceAsStream(tripBean.getImgSrc()));
//    	imgTrip.setImage(img);
    	
    	setTripBean(tripBean);
    	lblTitle.setText(tripBean.getTitle());
    	lblPrice.setText(tripBean.getPrice()+"€");
    	lblTicketPrice.setText(tripBean.getTicketPrice()+"€");
    	Image catImg = new Image(getClass().getResourceAsStream("/logic/view/images/icons8-greek-pillar-capital-50.png"));
    	imgCat1.setImage(catImg);
    	imgCat2.setImage(catImg);
    	lblCat1.setText(tripBean.getCategory1());
    	lblCat2.setText(tripBean.getCategory2());
	}
	
	public Node initializeNode(TripBean bean) throws LoadGraphicException {
			
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/logic/view/TripCard.fxml"));
			Node node = loader.load();
			CardGraphic cc = loader.getController();
			cc.setData(bean);
			return node;
		} catch (IOException e) {
			throw new LoadGraphicException(e.getMessage(), e);
		}
		
	}

}
