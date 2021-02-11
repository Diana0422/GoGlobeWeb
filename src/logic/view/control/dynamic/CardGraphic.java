  
package logic.view.control.dynamic;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import logic.bean.DayBean;
import logic.bean.TripBean;
import logic.control.ProfileController;
import logic.model.exceptions.LoadGraphicException;
import logic.persistence.exceptions.DatabaseException;
import logic.util.Session;
import logic.view.control.AlertGraphic;
import logic.view.control.TripInfoGraphic;
import logic.view.utils.GUIType;
import logic.view.utils.GraphicItem;
import logic.view.utils.GraphicLoader;

public class CardGraphic implements Initializable {
	

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblPrice;
    
    @FXML
    private Label lblCat1;
    
    @FXML
    private Label lblCat2;
    
    @FXML
    private Label lblAge;
    
    @FXML
    private Label lblSpots;

    @FXML
    private Button btnMore;
    
    @FXML 
    private Button btnDelete;
    
    @FXML 
    private Label lblLocations;
    
    @FXML
    private Label lblDates;
    
    
    
    /* Beans */
    
    private TripBean tripBean;
    private GridPane container;
    private Session session;

    
    public TripBean getTripBean() {
		return tripBean;
	}

	public void setTripBean(TripBean tripBean) {
		this.tripBean = tripBean;
	}
	
	private void setContainer(GridPane container) {
		this.container = container;
	}
	
	private void setSession(Session session) {
		this.session = session;
	}
    
	/* Action methods */
	@FXML
    void loadTripInfo(MouseEvent event) {
		Stage stage = (Stage) container.getScene().getWindow();
		if (session == null) stage.setScene(GraphicLoader.switchView(GUIType.INFO, new TripInfoGraphic(getTripBean())));
		if (session != null) stage.setScene(GraphicLoader.switchView(GUIType.INFO, new TripInfoGraphic(getTripBean()), session));
    }
	
	@FXML
    void deleteTrip(MouseEvent event) {
		try {
			new ProfileController().deleteTrip(tripBean.getTitle());
			container.getChildren().remove(lblTitle.getParent().getParent());
		} catch (DatabaseException e) {
			AlertGraphic alert = new AlertGraphic();
			alert.display(e.getMessage(), e.getCause().toString());
		}
    }
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// empty method: nothing to do when initialized
		
	}

	private void setSharedData(TripBean tripBean) {
    	setTripBean(tripBean);
    	lblTitle.setText(tripBean.getTitle());
    	lblAge.setText("age "+tripBean.getMinAge()+" - "+tripBean.getMaxAge());
    	lblSpots.setText(tripBean.getAvailability()+" spots left");
    	lblPrice.setText(tripBean.getPrice()+"€");
    	lblCat1.setText(tripBean.getCategory1());
    	lblCat2.setText(tripBean.getCategory2());
	}
	
	private void setData(TripBean tripBean) {
    	setTripBean(tripBean);
    	lblTitle.setText(tripBean.getTitle());
    	lblCat1.setText(tripBean.getCategory1());
    	lblCat2.setText(tripBean.getCategory2());
    	String locationList = "";
    	for (DayBean day: tripBean.getDays()) {
    		locationList = locationList.concat(day.getLocationCity()+" • ");
    	}
    	lblLocations.setText(locationList);
    	lblDates.setText(tripBean.getDepartureDate()+" - "+tripBean.getReturnDate());
	}
	
	public Node initializeNode(TripBean bean, GridPane container, Session session, GraphicItem type) throws LoadGraphicException {
		Node node = null;
		FXMLLoader loader;
		CardGraphic cc;
		try {
			switch(type) {
			case CARD_SHARED:
				loader = GraphicLoader.loadFXML(GraphicItem.CARD_SHARED);
				node = loader.load();
				cc = loader.getController();
				cc.setContainer(container);
				cc.setSession(session);
				cc.setSharedData(bean);
				break;
			case CARD:
				loader = GraphicLoader.loadFXML(GraphicItem.CARD);
				node = loader.load();
				cc = loader.getController();
				cc.setContainer(container);
				cc.setSession(session);
				cc.setData(bean);
				break;
			case CARD_OWNED:
				loader = GraphicLoader.loadFXML(GraphicItem.CARD_OWNED);
				node = loader.load();
				cc = loader.getController();
				cc.setContainer(container);
				cc.setSession(session);
				cc.setData(bean);
				break;
			default:
				break;
			}
			return node;
		} catch (IOException e) {
			throw new LoadGraphicException(e.getMessage(), e);
		}
		
	}
	
	public void loadCardGrid(GridPane cardGrid, List<TripBean> trips, Session session) {
		
		if (!cardGrid.getChildren().isEmpty()) {
			cardGrid.getChildren().clear();
		}
		
		int column = 0;
		int row = 1;
		
		if (trips != null) {
			for (int i=0; i<trips.size(); i++) {
				AnchorPane anchor;
				try {
					if (column == 3) {
						row++;
						column = 0;
					}
					this.setContainer(cardGrid);
					if (trips.get(i).getOrganizer().getEmail().equals(session.getUserEmail()) && session.getCurrentView().equals(GUIType.PROFILE)) {
						anchor = (AnchorPane) this.initializeNode(trips.get(i), cardGrid, session, GraphicItem.CARD_OWNED);
					}else if(trips.get(i).isShared()){
						anchor = (AnchorPane) this.initializeNode(trips.get(i), cardGrid, session, GraphicItem.CARD_SHARED);					
					}else {    
						anchor = (AnchorPane) this.initializeNode(trips.get(i), cardGrid, session, GraphicItem.CARD);
					}
					cardGrid.add(anchor, column++, row);
					GridPane.setMargin(anchor, new Insets(20));
				} catch (LoadGraphicException e) {
					AlertGraphic alert = new AlertGraphic();
					alert.display(e.getMessage(), e.getCause().toString());
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

}