package logic.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.controlsfx.control.Rating;
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
import logic.model.exceptions.LoadGraphicException;
import logic.persistence.exceptions.DBConnectionException;

public class ProfileGraphic implements GraphicController {

	
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

    private static final String WIDGET_ERROR = "Widget loading error.";

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
    	bean.setReviewerName(DesktopSessionContext.getInstance().getSession().getSessionName());
    	bean.setReviewerSurname(DesktopSessionContext.getInstance().getSession().getSessionSurname());
    	
    	try {
        	if (rdTraveler.isSelected()) {
    			ReviewUserController.getInstance().postReview("TRAVELER", d, txtComment.getText(), txtTitle.getText(), DesktopSessionContext.getInstance().getSession().getSessionEmail(), target.getEmail(), target);
        	} else {
    			ReviewUserController.getInstance().postReview("ORGANIZER", d, txtComment.getText(), txtTitle.getText(), DesktopSessionContext.getInstance().getSession().getSessionEmail(), target.getEmail(), target);
        	}
    	} catch (DBConnectionException e) {
			AlertGraphic alert = new AlertGraphic();
			alert.display(GUIType.PROFILE, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Database connection error", "Please retry later.");
    	}
    	
    	ReviewItemGraphic graphic = new ReviewItemGraphic();
		try {
			AnchorPane anchor = (AnchorPane) graphic.initializeNode(bean);
			vbReviews.getChildren().add(anchor);
		} catch (LoadGraphicException e) {
			AlertGraphic alert = new AlertGraphic();
			alert.display(GUIType.PROFILE, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), WIDGET_ERROR, "Something unexpected occurred displaying review.");
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
				graphic.display(GUIType.PROFILE, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), WIDGET_ERROR, "Something unexpected occurred loading the trip cards.");
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
	
	public void displayOrganizerRating(double rating) {
		ratingOrg.setRating(rating);
	}
	
	public void displayTravelerRating(double rating) {
		ratingTra.setRating(rating);
	}
	
	private void displayReviews(List<ReviewBean> list) {
		ReviewItemGraphic graphic = new ReviewItemGraphic();
		for (ReviewBean bean: list) {
			try {
				AnchorPane anchor = (AnchorPane) graphic.initializeNode(bean);
				vbReviews.getChildren().add(anchor);
			} catch (LoadGraphicException e) {
				AlertGraphic alert = new AlertGraphic();
				alert.display(GUIType.PROFILE, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), WIDGET_ERROR, "Something unexpected occurred displaying review.");
			}
		}
	}

	/* Initialization method */
	@Override
	public void initializeData(Object recBundle, Object forBundle) {
		this.target = (UserBean) recBundle;
		target.setGraphic(this); // Set the reference to this controller in the user bean to receive updates
		this.bundle = forBundle;
		List<TripBean> myTripBeans = null;
		List<TripBean> upcomingTripBeans = null;
		List<TripBean> previousTripBeans = null;
		
		// Display trips planned by user
		try {
			myTripBeans = ProfileController.getInstance().getMyTrips();
			loadGrid(myTripsGrid, myTripBeans);
		
			// Display target user's upcoming trips
			upcomingTripBeans = ProfileController.getInstance().getUpcomingTrips();
			loadGrid(upcomingGrid, upcomingTripBeans);
			
			// Display target user's passed trips
			previousTripBeans = ProfileController.getInstance().getRecentTrips();
			loadGrid(previousGrid, previousTripBeans);
		} catch (DBConnectionException e) {
			AlertGraphic alert = new AlertGraphic();
			alert.display(GUIType.PROFILE, GUIType.HOME, null, DesktopSessionContext.getInstance().getSession(), "Database connection error", "Please retry later.");
		}
		
		txtNameSurname.setText(target.getName()+" "+target.getSurname());
		txtAge.setText(Integer.toString(target.getAge()));
		
		// Display ratings
		displayOrganizerRating(target.getStatsBean().getOrgRating());
		displayTravelerRating(target.getStatsBean().getTravRating());
		
		// Fetch bean reviews and display them
		displayReviews(target.getReviews());
		
		// Initialize listener on rating control.
		rating.ratingProperty().addListener((arg0, arg1, arg2) -> setVote(arg2));
	}

	public Number getVote() {
		return vote;
	}

	public void setVote(Number vote) {
		this.vote = vote;
	}

	public UserBean getTarget() {
		return target;
	}

	public void setTarget(UserBean target) {
		this.target = target;
	}


}

	
	

