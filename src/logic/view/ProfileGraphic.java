package logic.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.controlsfx.control.Rating;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import logic.bean.ReviewBean;
import logic.bean.SessionBean;
import logic.bean.TripBean;
import logic.bean.UserBean;
import logic.control.ProfileController;
import logic.control.ReviewUserController;
import logic.model.UserStats;
import logic.model.exceptions.LoadGraphicException;
import logic.model.exceptions.SerializationException;
import logic.model.interfaces.Observer;
import logic.model.interfaces.Subject;

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
    private TextField txtTitle;

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
    private Rating ratingTra;

    @FXML
    private Rating ratingOrg;
    
    private Object bundle;
    
    private UserBean target;
    
    private Number vote;
    
    private double obsOrgRating;
    
    private double obsTravRating;


    @FXML
    void back(MouseEvent event) {
    	DesktopSessionContext.getGuiLoader().loadGUI(null, this.bundle, null);
    }
    
    @FXML
    void postReview(MouseEvent event) {
    	double d = (double) getVote();
    	
    	Date today = Calendar.getInstance().getTime();
    	DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    	String date = formatter.format(today);
    	
    	ReviewBean bean = new ReviewBean();
    	bean.setTitle(txtTitle.getText());
    	bean.setVote(d);
    	bean.setDate(date);
    	bean.setComment(txtComment.getText());
    	bean.setReviewerName(DesktopSessionContext.getInstance().getSession().getName());
    	bean.setReviewerSurname(DesktopSessionContext.getInstance().getSession().getSurname());
    	
    	if (rdTraveler.isSelected()) {
    		try {
    			System.out.println(ReviewUserController.getInstance());
				ReviewUserController.getInstance().postReview("TRAVELER", d, txtComment.getText(), txtTitle.getText(), DesktopSessionContext.getInstance().getSession().getEmail(), target.getEmail(), this);
			} catch (SerializationException e) {
				AlertGraphic graphic = new AlertGraphic();
				graphic.display(GUIType.PROFILE, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Serialization Error", "Something unexpected occurred loading trips.");
			}
    	} else {
    		try {
    			System.out.println(ReviewUserController.getInstance());
				ReviewUserController.getInstance().postReview("ORGANIZER", d, txtComment.getText(), txtTitle.getText(), DesktopSessionContext.getInstance().getSession().getEmail(), target.getEmail(), this);
			} catch (SerializationException e) {
				AlertGraphic graphic = new AlertGraphic();
				graphic.display(GUIType.PROFILE, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Serialization Error", "Something unexpected occurred loading trips.");
			}
    	}
    	
    	ReviewItemGraphic graphic = new ReviewItemGraphic();
		try {
			AnchorPane anchor = (AnchorPane) graphic.initializeNode(bean);
			vbReviews.getChildren().add(anchor);
		} catch (LoadGraphicException e) {
			AlertGraphic alert = new AlertGraphic();
			alert.display(GUIType.PROFILE, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Widget loading error.", "Something unexpected occurred displaying review.");
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
		ratingOrg.setRating(obsOrgRating);
		System.out.println("Organizer rating displayed.");
	}
	
	private void displayTravelerRating() {
		ratingTra.setRating(obsTravRating);
		System.out.println("Traveler rating displayed.");
	}
	
	private void displayReviews(List<ReviewBean> list) {
		ReviewItemGraphic graphic = new ReviewItemGraphic();
		for (ReviewBean bean: list) {
			try {
				AnchorPane anchor = (AnchorPane) graphic.initializeNode(bean);
				vbReviews.getChildren().add(anchor);
			} catch (LoadGraphicException e) {
				AlertGraphic alert = new AlertGraphic();
				alert.display(GUIType.PROFILE, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Widget loading error.", "Something unexpected occurred displaying review.");
			}
		}
	}

	/* Initialization method */
	@Override
	public void initializeData(Object recBundle, Object forBundle) {
		System.out.println(recBundle);
		this.target = (UserBean) recBundle;
		this.bundle = forBundle;
		List<TripBean> myTripBeans = null;
		List<TripBean> upcomingTripBeans = null;
		List<TripBean> previousTripBeans = null;
		
		// Display trips planned by user
		myTripBeans = ProfileController.getInstance().getMyTrips();
		loadGrid(myTripsGrid, myTripBeans);
	
		// Display target user's upcoming trips
		upcomingTripBeans = ProfileController.getInstance().getUpcomingTrips();
		loadGrid(upcomingGrid, upcomingTripBeans);
		
		// Display target user's passed trips
		previousTripBeans = ProfileController.getInstance().getRecentTrips();
		loadGrid(previousGrid, previousTripBeans);
		
		txtNameSurname.setText(target.getName()+" "+target.getSurname());
		txtAge.setText(Integer.toString(target.getAge()));
		
		// Display ratings
		System.out.println(target.getOrgRating());
		System.out.println(target.getTravRating());
		this.obsOrgRating = target.getOrgRating();
		this.obsTravRating = target.getTravRating();
		displayOrganizerRating();
		displayTravelerRating();
		
		// Fetch bean reviews and display them
		System.out.println(target.getReviews());
		displayReviews(target.getReviews());
		
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
	public void updateValue(Subject s) {
		System.out.println("Updating user profile.");
		this.obsOrgRating = ((UserStats) s).getOrganizerRating();
		this.obsTravRating = ((UserStats) s).getTravelerRating();
		displayOrganizerRating();
		displayTravelerRating();
	}

	public UserBean getTarget() {
		return target;
	}

	public void setTarget(UserBean target) {
		this.target = target;
	}


}

	
	

