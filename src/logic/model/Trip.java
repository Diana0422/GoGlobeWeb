package logic.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Trip implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int id;
	private String title;
	private int price;
	private TripCategory category1;
	private TripCategory category2;
	private String imgSrc;
	private Date departureDate;
	private Date returnDate;	
	private List<Day> days;	
	private long tripLength;
	
	/* SHARED TRIP ELEMENTS */
	private boolean shared;
	private String description;
	private int minAge;
	private int maxAge;
	private int maxParticipants;
	private User organizer;
	private List<User> participants;
	//Meglio lasciare solo id  e List<Days> e incapsulare gli altri attributi in una 
	//classe associata trip info
	
	
	public Trip() {
		this.days = new ArrayList<>();
		this.participants = new ArrayList<>();
	}
	
	public Trip(int id, String title, int price, String cat1, String cat2, String departureDate, String returnDate) {
		this.id = id;
		this.title = title;
		this.price = price;
		this.category1 = TripCategory.valueOf(cat1);
		this.category2 = TripCategory.valueOf(cat2);
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date dep = df.parse(departureDate);
			Date ret = df.parse(returnDate);
			this.departureDate = dep;
			this.returnDate = ret;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addActivity(int day, Activity activity) {
		this.getDays().get(day).getActivities().add(activity);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public TripCategory getCategory1() {
		return category1;
	}
	
	public void setCategory1(TripCategory category1) {
		this.category1 = category1;
	}
	
	public TripCategory getCategory2() {
		return category2;
	}
	
	public void setCategory2(TripCategory category2) {
		this.category2 = category2;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public List<Day> getDays() {
		return days;
	}

	public void setDays(List<Day> days) {
		this.days = days;
		this.calculatePrice();
	}
	
	public int getPrice() {
		return price;
	}

	public void calculatePrice() {
		int sumPrice = 0;
		for (int i=0; i<getDays().size(); i++) {
			sumPrice += getDays().get(i).getBudget();
		}
		this.price = sumPrice;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public long getTripLength() {
		return tripLength;
	}

	public void setTripLength(long tripLength) {
		this.tripLength = tripLength;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public boolean isShared() {
		return shared;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMinAge() {
		return minAge;
	}

	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	public User getOrganizer() {
		return organizer;
	}

	public void setOrganizer(User organizer) {
		this.organizer = organizer;
	}

	public List<User> getParticipants() {
		return participants;
	}

	public void setParticipants(List<User> participants) {
		this.participants = participants;
	}

	public int getMaxParticipants() {
		return maxParticipants;
	}

	public void setMaxParticipants(int maxParticipants) {
		this.maxParticipants = maxParticipants;
	}
	
	public void addParticipant(User participant) {
		if (!getParticipants().contains(participant)) getParticipants().add(participant);
	}
	
}
