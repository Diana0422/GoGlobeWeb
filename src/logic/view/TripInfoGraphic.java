package logic.view;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logic.bean.ActivityBean;
import logic.bean.DayBean;
import logic.bean.TripBean;
import logic.bean.UserBean;
import logic.control.FlightController;
import logic.control.JoinTripController;

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
	
	private TripBean tripBean;
	
	public void setTitleText(String text) {
		lblTitle.setText(text);
	}
	
	public void setPriceText(String text) {
		lblPrice.setText(text);
	}
	
	public void setDescriptionText(String text) {
		lblDescription.setText(text);
	}
	
	public void setDepartureText(String text) {
		lblDeparture.setText(text);
	}
	
	public void setReturnText(String text) {
		lblReturn.setText(text);
	}
	
	public void setCategory1Text(String text) {
		lblCategory1.setText(text);
	}
	
	public void setCategory2Text(String text) {
		lblCategory2.setText(text);
	}
	
	public void setCategory1Image(Image cat1Img) {
		imgCategory1.setImage(cat1Img);
	}
	
	public void setCategory2Image(Image cat2Img) {
		imgCategory2.setImage(cat2Img);
	}
	
	public void initializeTripData() {
		lblTitle.setText(getTripBean().getTitle());
		lblPrice.setText(getTripBean().getPrice()+"€");
		lblDescription.setText(getTripBean().getDescription());
		lblDeparture.setText(getTripBean().getDepartureDate());
		lblReturn.setText(getTripBean().getReturnDate());
		lblCategory1.setText(getTripBean().getCategory1());
		lblCategory2.setText(getTripBean().getCategory2());
	}
	
	public TripBean getTripBean() {
		return tripBean;
	}

	public void setTripBean(TripBean tripBean) {
		this.tripBean = tripBean;
	}
	
	public void addDayTabs(List<DayBean> days) {
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
	
	
	public void displayFlightInfo() {
		String notDefined = "N/D";
		int price; 
		
		if ((price = FlightController.getInstance().retrieveFlightPrice(tripBean)) == 0) {
			lblTicketPrice.setText(notDefined);
		} else {
			lblTicketPrice.setText(price+"€");
		}
		
		lblOrigin.setText(FlightController.getInstance().retrieveFlightOrigin(tripBean));
		lblDestination.setText(FlightController.getInstance().retrieveFlightDestination(tripBean));
		lblCarrier.setText(FlightController.getInstance().retrieveFlightCarrier(tripBean));
	}
	
	
	private void displayActivity(VBox vbox, ActivityBean activity) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/logic/view/ActivityItem.fxml"));
		try {
			AnchorPane anchor = loader.load();
			
			System.out.println("Bean: "+activity);
			ActivityItemGraphic graphic = loader.getController();
			graphic.setData(activity);
			vbox.getChildren().add(anchor);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void joinTrip(ActionEvent e) {
		JoinTripController.getInstance().joinTrip(getTripBean(), DesktopSessionContext.getInstance().getSession());
	}
	
	
	public void initializeOrganizer(TripBean trip) {
		UserBean organizer = trip.getOrganizer();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/logic/view/UserItem.fxml"));
		
		try {
			AnchorPane pane = loader.load();
			UserItemGraphic graphic = loader.getController();
			graphic.setData(organizer);
			boxOrganizer.getChildren().add(pane);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initializeParticipants(TripBean bean) {
		List<UserBean> participants = bean.getParticipants();
		System.out.println("Participants: "+participants);
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/logic/view/UserItem.fxml"));
		
		if (participants != null) {
			for (UserBean user: participants) {
				displayParticipant(user);
			}
		}
	}

	private void displayParticipant(UserBean user) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/logic/view/UserItem.fxml"));
		try {
			AnchorPane anchor = loader.load();
			
			System.out.println("Bean: "+user);
			UserItemGraphic graphic = loader.getController();
			graphic.setData(user);
			boxTravelers.getChildren().add(anchor);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void initializeData(Object bundle) {
		TripBean bean = (TripBean) bundle;
		setTripBean(bean);
		initializeParticipants(getTripBean());
		initializeOrganizer(getTripBean());
		initializeTripData();
		addDayTabs(bean.getDays());
	}

}