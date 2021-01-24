package logic.view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
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
    
    private static final String WIDGET_ERROR = "Widget loading error.";
    
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
    	setTripBean(tripBean);
    	lblTitle.setText(tripBean.getTitle());
    	lblPrice.setText(tripBean.getPrice()+"�");
    	lblTicketPrice.setText(tripBean.getTicketPrice()+"�");
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
	
	public void loadCardGrid(GridPane cardGrid, List<TripBean> trips) {
		
		if (!cardGrid.getChildren().isEmpty()) {
			cardGrid.getChildren().clear();
		}
		
		int column = 0;
		int row = 1;
		
		for (int i=0; i<trips.size(); i++) {
			AnchorPane anchor;
			try {
				if (column == 3) {
					row++;
					column = 0;
				}
					
				anchor = (AnchorPane) this.initializeNode(trips.get(i));
				cardGrid.add(anchor, column++, row);
				GridPane.setMargin(anchor, new Insets(20));
			} catch (LoadGraphicException e) {
				AlertGraphic graphic = new AlertGraphic();
				graphic.display(GUIType.PROFILE, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), WIDGET_ERROR, "Something unexpected occurred loading the trip cards.");
			}
					
			// Set grid height
			cardGrid.setMaxHeight(Region.USE_PREF_SIZE);
			cardGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
			cardGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
					
			// Set grid width
			cardGrid.setMaxWidth(Region.USE_PREF_SIZE);
			cardGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
			cardGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
		}				
	}

}
