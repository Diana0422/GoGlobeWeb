package logic.view;

import java.util.List;

import org.controlsfx.control.Rating;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import logic.bean.SessionBean;
import logic.bean.TripBean;
import logic.bean.UserBean;
import logic.control.ProfileController;
import logic.control.ReviewUserController;
import logic.dao.UserDAO;
import logic.dao.UserDAOFile;
import logic.model.RoleType;
import logic.model.User;
import logic.model.UserStats;
import logic.model.exceptions.LoadGraphicException;
import logic.model.exceptions.SerializationException;
import logic.model.interfaces.Observer;

public class ProfileGraphic implements GraphicController, Observer {


	@FXML
    private VBox vbReviews;
    
    @FXML
    private RadioButton rdTraveler;

    @FXML
    private RadioButton rdOrganizer;

    @FXML
    private Rating rating;

    @FXML
    private Button btnPost;

    @FXML
    private TextArea txtComment;

    @FXML
    private GridPane upcomingGrid;

    @FXML
    private GridPane previousGrid;

    @FXML
    private GridPane myTripsGrid;

    @FXML
    private Text txtNameSurname;

    @FXML
    private Text txtAge;

    @FXML
    private ImageView ivAdventureAttitude;

    @FXML
    private ImageView ivRelaxAttitude;

    @FXML
    private ImageView ivFunAttitude;

    @FXML
    private ImageView ivCultureAttitude;

    @FXML
    private ImageView ivProfilePic;
    
    @FXML
    private Button btnBack;
    
    @FXML
    private Label lblTRating;

    @FXML
    private Label lblORating;
    
    private Object bundle;
    
    private Number vote;
    
    private UserStats userStats;
    
    private User target;
    
    private double obsOrgRating;
    
    private double obsTravRating;


    @FXML
    void back(MouseEvent event) {
    	DesktopSessionContext.getGuiLoader().loadGUI(null, this.bundle, null);
    }
    
    @FXML
    void postReview(MouseEvent event) {
    	double d = (double) getVote();
    	System.out.println("vote: "+d);
    	System.out.println("comment: "+txtComment.getText());
    	if (rdTraveler.isSelected()) {
    		try {
				ReviewUserController.getInstance().postReview(RoleType.TRAVELER, d, txtComment.getText(), target, DesktopSessionContext.getInstance().getSession().getEmail(), this);
			} catch (SerializationException e) {
				AlertGraphic graphic = new AlertGraphic();
				graphic.display(GUIType.PROFILE, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Serialization Error", "Something unexpected occurred loading trips.");
			}
    	} else {
    		try {
				ReviewUserController.getInstance().postReview(RoleType.ORGANIZER, d, txtComment.getText(), target, DesktopSessionContext.getInstance().getSession().getEmail(), this);
			} catch (SerializationException e) {
				AlertGraphic graphic = new AlertGraphic();
				graphic.display(GUIType.PROFILE, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Serialization Error", "Something unexpected occurred loading trips.");
			}
    	}
    }

    /* Beans */
    private SessionBean session;
    
    
    public SessionBean getSession() {
		return session;
	}

	public void setSession(SessionBean session) {
		this.session = session;
	}
    
    /* Action methods */
	private void loadGrid(GridPane tripsGrid, List<TripBean> trips) {
		
		int column = 0;
		int row = 1;
		
		for (int i=0; i<trips.size(); i++) {
				
			CardGraphic cc = new CardGraphic();
			AnchorPane anchor;
			try {
				if (column == 3) {
					row++;
					column = 0;
				}
					
				anchor = (AnchorPane) cc.initializeNode(trips.get(i));
				tripsGrid.add(anchor, column++, row);
				GridPane.setMargin(anchor, new Insets(20));
			} catch (LoadGraphicException e) {
				AlertGraphic graphic = new AlertGraphic();
				graphic.display(GUIType.PROFILE, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Widget loading error.", "Something unexpected occurred loading the trip cards.");
			}
					
			// Set grid height
			tripsGrid.setMaxHeight(Region.USE_PREF_SIZE);
			tripsGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
			tripsGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
					
			// Set grid width
			tripsGrid.setMaxWidth(Region.USE_PREF_SIZE);
			tripsGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
			tripsGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
		}				
	}
	
	private void displayOrganizerRating() {
		lblORating.setText(Double.toString(obsOrgRating));
		System.out.println("Organizer rating displayed.");
	}
	
	private void displayTravelerRating() {
		lblTRating.setText(Double.toString(obsTravRating));
		System.out.println("Traveler rating displayed.");
	}

	/* Initialization method */
	@Override
	public void initializeData(Object recBundle, Object forBundle) {
//		this.target = (UserBean) recBundle;
		System.out.println(recBundle);
		UserBean bean = (UserBean) recBundle;
		this.bundle = forBundle;
		List<TripBean> myTripBeans = null;
		List<TripBean> upcomingTripBeans = null;
		List<TripBean> previousTripBeans = null;
		
		// Display trips planned by user
		try {
			myTripBeans = ProfileController.getInstance().getMyTrips();
			loadGrid(myTripsGrid, myTripBeans);
		} catch (SerializationException e) {
			AlertGraphic graphic = new AlertGraphic();
			graphic.display(GUIType.PROFILE, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Serialization Error", "Something unexpected occurred loading trips.");
		}
	
		// Display target user's upcoming trips
		try {
			upcomingTripBeans = ProfileController.getInstance().getUpcomingTrips();
			loadGrid(upcomingGrid, upcomingTripBeans);
		} catch (SerializationException e) {
			AlertGraphic graphic = new AlertGraphic();
			graphic.display(GUIType.PROFILE, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Serialization Error", "Something unexpected occurred loading trips.");
		}
		
		// Display target user's passed trips
		try {
			previousTripBeans = ProfileController.getInstance().getRecentTrips();
			loadGrid(previousGrid, previousTripBeans);
		} catch (SerializationException e) {
			AlertGraphic graphic = new AlertGraphic();
			graphic.display(GUIType.JOIN, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Serialization Error", "Something unexpected occurred loading trips.");
		}

		// Display user name, surname and age
//		txtNameSurname.setText(target.getName()+" "+target.getSurname());
//		txtAge.setText(Integer.toString(target.getAge()));
		
		txtNameSurname.setText(bean.getName()+" "+bean.getSurname());
		txtAge.setText(Integer.toString(bean.getAge()));
		
		//TODO BETTER
		UserDAO dao = new UserDAOFile();
		try {
			this.target = dao.getUser(bean.getEmail());
			userStats = target.getStats();
			
			// Display ratings
			System.out.println(userStats.getOrganizerRating());
			System.out.println(userStats.getTravelerRating());
			this.obsOrgRating = userStats.getOrganizerRating();
			this.obsTravRating = userStats.getTravelerRating();
			displayOrganizerRating();
			displayTravelerRating();
		} catch (SerializationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Initialize listener on rating control.
		rating.ratingProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				setVote(arg2);
			}
			
		});
	}

	public Number getVote() {
		return vote;
	}

	public void setVote(Number vote) {
		this.vote = vote;
	}

	/* observer method */
	
	@Override
	public void updateValue() {
		System.out.println("Updating user profile.");
		this.obsOrgRating = userStats.getOrganizerRating();
		this.obsTravRating = userStats.getTravelerRating();
		displayOrganizerRating();
		displayTravelerRating();
	}


}

	
	

