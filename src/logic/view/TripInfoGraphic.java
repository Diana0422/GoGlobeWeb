package logic.view;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logic.bean.ActivityBean;
import logic.bean.DayBean;
import logic.bean.TripBean;
import logic.bean.UserBean;
import logic.control.FlightController;
import logic.control.JoinTripController;
import logic.model.exceptions.LoadGraphicException;

public class TripInfoGraphic implements GraphicController {
	
	@FXML
    private AnchorPane tripBackground;
	
	@FXML
	private TabPane tabPane;
	
	@FXML
	private Label lblTitle;
	
	@FXML
	private Label lblPrice;
	
	@FXML
	private Label lblDescription;
	
	@FXML
	private Label lblDeparture;
	
	@FXML
	private Label lblReturn;
	
    @FXML
    private Label lblOrigin;

    @FXML
    private Label lblDestination;

    @FXML
    private Label lblCarrier;

    @FXML
    private Label lblTicketPrice;
	
	@FXML
	private Label lblCategory1;
	
	@FXML
	private Label lblCategory2;
	
	@FXML
	private ImageView imgCategory1;
	
	@FXML
	private ImageView imgCategory2;
	
	@FXML
    private VBox boxOrganizer;

    @FXML
    private VBox boxTravelers;
    
    @FXML
    private VBox boxFlight;
    
    @FXML
    private Button btnBack;

    @FXML
    void back(MouseEvent event) {
    	DesktopSessionContext.getGuiLoader().loadGUI(null, null, GUIType.JOIN);
    }
	
	private TripBean tripBean;
	
	public void setCategory1Image(Image cat1Img) {
		imgCategory1.setImage(cat1Img);
	}
	
	public void setCategory2Image(Image cat2Img) {
		imgCategory2.setImage(cat2Img);
	}
	
	private void initializeTripData() {
		lblTitle.setText(getTripBean().getTitle());
		lblPrice.setText(getTripBean().getPrice()+"�");
		lblDescription.setText(getTripBean().getDescription());
		lblDeparture.setText(getTripBean().getDepartureDate());
		lblReturn.setText(getTripBean().getReturnDate());
		lblCategory1.setText(getTripBean().getCategory1());
		lblCategory2.setText(getTripBean().getCategory2());
	}
	
	private TripBean getTripBean() {
		return tripBean;
	}

	private void setTripBean(TripBean tripBean) {
		this.tripBean = tripBean;
	}
	
	private void addDayTabs(List<DayBean> days) {
		for (int i=0; i<days.size(); i++) {
			
			VBox table = new VBox();
			VBox container = new VBox();
		
			container.setPrefSize(500f,500f);
			container.setPadding(new Insets(10));
			container.setId("tab-container");
			
			Label locationTitle = new Label("Location:");
			container.getChildren().add(locationTitle);
			
			Label location = new Label();
			location.setText(days.get(i).getLocation());
			container.getChildren().add(location);
			
			Label activitiesTitle = new Label("Activities of the day:");
			container.getChildren().add(activitiesTitle);
			
			List<ActivityBean> activities = days.get(i).getActivities();
			for (ActivityBean bean: activities) {
				displayActivity(table, bean);
			}
			ScrollPane scroll = new ScrollPane(table);
			scroll.setPrefSize(500f, 500f);
			
			container.getChildren().add(scroll);
			
			Tab tab = new Tab("Day "+Integer.toString(i+1));
			tab.setContent(container);
			tabPane.getTabs().add(tab);
		}
	}
	
	
	private void displayFlightInfo() {
		String notDefined = "N/D";
		int price; 
		
		if ((price = FlightController.getInstance().retrieveFlightPrice(tripBean)) == 0) {
			lblTicketPrice.setText(notDefined);
		} else {
			lblTicketPrice.setText(price+"�");
		}
		
		lblOrigin.setText(FlightController.getInstance().retrieveFlightOrigin(tripBean));
		lblDestination.setText(FlightController.getInstance().retrieveFlightDestination(tripBean));
		lblCarrier.setText(FlightController.getInstance().retrieveFlightCarrier(tripBean));
	}
	
	
	private void displayActivity(VBox vbox, ActivityBean activity) {
		ActivityItemGraphic graphic = new ActivityItemGraphic();
		AnchorPane anchor;
		try {
			anchor = (AnchorPane) graphic.initializeNode(activity);
			vbox.getChildren().add(anchor);
		} catch (LoadGraphicException e) {
			AlertGraphic alert = new AlertGraphic();
			alert.display(GUIType.JOIN, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Widget loading error.", "Something unexpected occurred loading activity cards.");
		}
	}

	public void joinTrip(ActionEvent e) {
		if (DesktopSessionContext.getInstance().getSession() != null) {
			if (JoinTripController.getInstance().joinTrip(getTripBean(), DesktopSessionContext.getInstance().getSession())) {
				AlertGraphic alert = new AlertGraphic();
				alert.display(GUIType.INFO, GUIType.REQUESTS, null, getTripBean(), "Request sent to organizer.", "Choose an option.");
			} else {
				AlertGraphic alert = new AlertGraphic();
				alert.display(GUIType.INFO, GUIType.REQUESTS, null, getTripBean(), "You are not eligible to join.", "Choose an option.");
			}
		} else {
			AlertGraphic alert = new AlertGraphic();
			alert.display(GUIType.INFO, GUIType.LOGIN, null, getTripBean(), "You are not logged in.", "Register or login first.");
		}
	}
	
	private void displayOrganizer(TripBean trip) {
		UserBean organizer = trip.getOrganizer();
		UserItemGraphic graphic = new UserItemGraphic();
		try {
			AnchorPane anchor = (AnchorPane) graphic.initilizeNode(organizer, trip);
			boxOrganizer.getChildren().add(anchor);
		} catch (LoadGraphicException e) {
			AlertGraphic alert = new AlertGraphic();
			alert.display(GUIType.JOIN, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Widget loading error.", "Something unexpected occurred displaying organizer.");
		}
	}
	
	private void initializeParticipants(TripBean bean) {
		List<UserBean> participants = bean.getParticipants();
		
		if (participants != null) {
			for (UserBean user: participants) {
				displayParticipant(user, bean);
			}
		}
	}

	private void displayParticipant(UserBean user, TripBean trip) {
		UserItemGraphic graphic = new UserItemGraphic();
		AnchorPane anchor;
		try {
			anchor = (AnchorPane) graphic.initilizeNode(user, trip);
			boxTravelers.getChildren().add(anchor);
		} catch (LoadGraphicException e) {
			AlertGraphic alert = new AlertGraphic();
			alert.display(GUIType.JOIN, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Widget loading error.", "Something unexpected occurred displaying user.");
		}	
	}

	@Override
	public void initializeData(Object recBundle, Object forBundle) {
		TripBean bean = (TripBean) recBundle;
		setTripBean(bean);
		initializeParticipants(getTripBean());
		displayOrganizer(getTripBean());
		initializeTripData();
		addDayTabs(bean.getDays());
		displayFlightInfo();
	}

}