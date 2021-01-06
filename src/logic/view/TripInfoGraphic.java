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
import logic.bean.SessionBean;
import logic.bean.TripBean;
import logic.bean.UserBean;
import logic.control.JoinTripController;

public class TripInfoGraphic {
	
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
	
	private TripBean tripBean;
	
	private SessionBean session;
	
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
	
	public TripBean getTripBean() {
		return tripBean;
	}

	public void setTripBean(TripBean tripBean) {
		this.tripBean = tripBean;
	}

	public SessionBean getSession() {
		return session;
	}

	public void setSession(SessionBean session) {
		this.session = session;
		System.out.println("Trip Info session: "+session);
	}
	
	public void addDayTabs(List<DayBean> days) {
		for (int i=0; i<days.size(); i++) {
			AnchorPane pane = new AnchorPane();
			pane.setId("tab-container");
			pane.setPadding(new Insets(10));
			ScrollPane scroll = new ScrollPane();
			pane.getChildren().add(scroll);
			VBox vbox = new VBox();
			vbox.setSpacing(10);
			vbox.setPadding(new Insets(10));
			scroll.setContent(vbox);
			displayActivityList(vbox, days.get(i).getActivities());
			Tab tab = new Tab("Day "+Integer.toString(i+1), pane);
			tabPane.getTabs().add(tab);
		}
	}
	
	
	private void displayActivityList(VBox vbox, List<ActivityBean> activities) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/logic/view/ActivityItem.fxml"));
		try {
			for (ActivityBean bean: activities) {
				AnchorPane anchor = loader.load();
				ActivityItemGraphic graphic = loader.getController();
				graphic.setData(bean);
				vbox.getChildren().add(anchor);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void joinTrip(ActionEvent e) {
		JoinTripController.getInstance().joinTrip(getTripBean(), UpperNavbarControl.getInstance().getSession());
		UpperNavbarControl.getInstance().loadHome(e);
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
		
		try {
			AnchorPane pane = loader.load();
			UserItemGraphic graphic = loader.getController();
			if (participants != null) {
				for (UserBean user: participants) {
					graphic.setData(user);
					boxTravelers.getChildren().add(pane);
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}