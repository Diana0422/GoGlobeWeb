package logic.view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import logic.bean.SessionBean;
import logic.bean.TripBean;
import logic.control.JoinTripController;
import logic.model.exceptions.LoadGraphicException;
import logic.persistence.exceptions.DatabaseException;

public class JoinTripGraphic implements Initializable {
	@FXML
	private TextField txtSearch;
	
	@FXML
	private Button btnSearch;
	
	@FXML
	private Button btnPlan;
	
	@FXML
	private Label lblMessage;
	
	@FXML
    private GridPane cardsLayout;
	
    @FXML
    private Button btnBack;

    @FXML
    void back(MouseEvent event) {
    	DesktopSessionContext.getGuiLoader().loadGUI(null, DesktopSessionContext.getInstance().getSession(), GUIType.HOME);
    }
	
	/* Beans */
	
	private List<TripBean> tripBeans;
	
	private SessionBean session;
	
	public SessionBean getSession() {
		return session;
	}

	public void setSession(SessionBean session) {
		this.session = session;
	}
	
	/* Action methods */
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			this.tripBeans = JoinTripController.getInstance().searchTrips(txtSearch.getText());
		} catch (DatabaseException e) {
			AlertGraphic alert = new AlertGraphic();
			alert.display(GUIType.JOIN, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), e.getMessage(), e.getCause().toString());
		}
	}
	
	@FXML
	public void search(MouseEvent event) {
		try {
			this.tripBeans = JoinTripController.getInstance().searchTrips(txtSearch.getText());
		} catch (DatabaseException e) {
			AlertGraphic alert = new AlertGraphic();
			alert.display(GUIType.JOIN, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), e.getMessage(), e.getCause().toString());
		}
		loadGrid(txtSearch.getText(), false);
	}
	
	public void loadGrid(String searchVal, boolean skipFilter) {
		
		int column = 0;
		int row = 1;
		
		for (int i=0; i<this.tripBeans.size(); i++) {
			if (isFiltered(tripBeans.get(i), searchVal) || skipFilter) {
					CardGraphic cc = new CardGraphic();
					AnchorPane anchor;
				try {
					if (column == 3) {
						row++;
						column = 0;
					}
					anchor = (AnchorPane) cc.initializeNode(tripBeans.get(i));	
					cardsLayout.add(anchor, column++, row);
					GridPane.setMargin(anchor, new Insets(20));
				} catch (LoadGraphicException e) {
					AlertGraphic graphic = new AlertGraphic();
					graphic.display(GUIType.JOIN, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Widget loading error.", "Something unexpected occurred loading the trip cards.");
				}
						
				// Set grid height
				cardsLayout.setMaxHeight(Region.USE_PREF_SIZE);
				cardsLayout.setPrefHeight(Region.USE_COMPUTED_SIZE);
				cardsLayout.setMinHeight(Region.USE_COMPUTED_SIZE);
						
				// Set grid width
				cardsLayout.setMaxWidth(Region.USE_PREF_SIZE);
				cardsLayout.setPrefWidth(Region.USE_COMPUTED_SIZE);
				cardsLayout.setMinWidth(Region.USE_COMPUTED_SIZE);
			}
					
		}
	}

	
	private boolean isFiltered(TripBean bean, String searchVal) {	// Da aggiungere a interfaccia di filtri
		return (bean.getTitle().toLowerCase().contains(searchVal) || bean.getCategory1().contains(searchVal) || bean.getCategory2().contains(searchVal));
	}
}