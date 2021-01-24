package logic.view;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
import logic.view.filterstrategies.AdventureCategoryStrategy;
import logic.view.filterstrategies.AlphabeticalFilterStrategy;
import logic.view.filterstrategies.CultureCategoryStrategy;
import logic.view.filterstrategies.FunCategoryStrategy;
import logic.view.filterstrategies.RelaxCategoryStrategy;
import logic.view.filterstrategies.StrategyContext;

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
	
	StrategyContext filterContext;
	
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
		filterContext = new StrategyContext();
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
		loadGrid(this.tripBeans);
	}
	
	@FXML 
	public void filterByAdventure(MouseEvent event) {
		this.filterContext.setFilter(new AdventureCategoryStrategy());
		filteredTripBeans = filterContext.filter(tripBeans);
		loadGrid(this.filteredTripBeans);
	}
	
	@FXML 
	public void filterByFun(MouseEvent event) {
		this.filterContext.setFilter(new FunCategoryStrategy());
		filteredTripBeans = filterContext.filter(tripBeans);
		loadGrid(this.filteredTripBeans);
	}
	
	@FXML 
	public void filterByCulture(MouseEvent event) {
		this.filterContext.setFilter(new CultureCategoryStrategy());
		filteredTripBeans = filterContext.filter(tripBeans);
		loadGrid(this.filteredTripBeans);
	}
	
	@FXML 
	public void filterByRelax(MouseEvent event) {
		this.filterContext.setFilter(new RelaxCategoryStrategy());
		filteredTripBeans = filterContext.filter(tripBeans);
		loadGrid(this.filteredTripBeans);
	}
	
	@FXML 
	public void filterFromAToZ(MouseEvent event) {
		this.filterContext.setFilter(new AlphabeticalFilterStrategy());
		filteredTripBeans = filterContext.filter(tripBeans);
		loadGrid(this.filteredTripBeans);
	}
	
	@FXML
	public void priceBtnClick(MouseEvent event) {
		try {
			Desktop.getDesktop().browse(new URL("https://maps.google.com/?q=Colosseo").toURI());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadGrid(List<TripBean> trips) {
		
		if (!cardsLayout.getChildren().isEmpty()) {
			cardsLayout.getChildren().clear();
		}
		int column = 0;
		int row = 1;
		
		for (int i=0; i<trips.size(); i++) {
			System.out.println(trips.get(i).getTitle());

					CardGraphic cc = new CardGraphic();
					AnchorPane anchor;
				try {
					if (column == 3) {
						row++;
						column = 0;
					}
					anchor = (AnchorPane) cc.initializeNode(trips.get(i));	
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

	
//	private boolean isFiltered(TripBean bean, String searchVal) {	// Da aggiungere a interfaccia di filtri
//		return (bean.getTitle().toLowerCase().contains(searchVal) || bean.getCategory1().contains(searchVal) || bean.getCategory2().contains(searchVal));
//	}
}