package logic.view;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import logic.bean.SessionBean;
import logic.bean.TripBean;
import logic.control.JoinTripController;
import logic.persistence.exceptions.DatabaseException;
import logic.view.filterstrategies.TripFilterManager;

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
	
	private List<TripBean> filteredTripBeans;
	
	TripFilterManager filterManager;
	
	JoinTripController controller;
	
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
		filterManager = new TripFilterManager();
		controller = new JoinTripController();
		try {
			if (DesktopSessionContext.getInstance().getSession() != null) {
				this.tripBeans = controller.getSuggestedTrips(DesktopSessionContext.getInstance().getSession().getSessionEmail());
			} else {
				this.tripBeans = controller.searchTrips(txtSearch.getText());
			}
			CardGraphic cc = new CardGraphic();
			cc.loadCardGrid(cardsLayout, this.tripBeans);
		} catch (DatabaseException e) {
			AlertGraphic alert = new AlertGraphic();
			alert.display(GUIType.JOIN, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), e.getMessage(), e.getCause().toString());
		}
	}
	
	@FXML
	public void search(MouseEvent event) {
		try {
			this.tripBeans = controller.searchTrips(txtSearch.getText());
		} catch (DatabaseException e) {
			AlertGraphic alert = new AlertGraphic();
			alert.display(GUIType.JOIN, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), e.getMessage(), e.getCause().toString());
		}
		CardGraphic cc = new CardGraphic();
		cc.loadCardGrid(cardsLayout, this.tripBeans);
	}
	
	@FXML 
	public void filterByAdventure(MouseEvent event) {
		filterManager.setAdventureFilter();
		filteredTripBeans = filterManager.filterTrips(tripBeans);
		CardGraphic cc = new CardGraphic();
		cc.loadCardGrid(cardsLayout, this.filteredTripBeans);
	}
	
	@FXML 
	public void filterByFun(MouseEvent event) {
		filterManager.setFunFilter();
		filteredTripBeans = filterManager.filterTrips(tripBeans);
		CardGraphic cc = new CardGraphic();
		cc.loadCardGrid(cardsLayout, this.filteredTripBeans);
	}
	
	@FXML 
	public void filterByCulture(MouseEvent event) {
		filterManager.setCultureFilter();
		filteredTripBeans = filterManager.filterTrips(tripBeans);
		CardGraphic cc = new CardGraphic();
		cc.loadCardGrid(cardsLayout, this.filteredTripBeans);
	}
	
	@FXML 
	public void filterByRelax(MouseEvent event) {
		filterManager.setRelaxFilter();
		filteredTripBeans = filterManager.filterTrips(tripBeans);
		CardGraphic cc = new CardGraphic();
		cc.loadCardGrid(cardsLayout, this.filteredTripBeans);
	}
	
	@FXML 
	public void filterFromAToZ(MouseEvent event) {
		filterManager.setAlphabeticFilter();
		filteredTripBeans = filterManager.filterTrips(tripBeans);
		CardGraphic cc = new CardGraphic();
		cc.loadCardGrid(cardsLayout, this.filteredTripBeans);
	}
	
	@FXML
	public void priceBtnClick(MouseEvent event) {
		//empty
	}
	

    @FXML
    void onPlanTrip(MouseEvent event) {
    	DesktopSessionContext.getGuiLoader().loadGUI(null, null, GUIType.PREFTRIP);
    }
}