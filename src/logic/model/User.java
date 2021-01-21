package logic.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import logic.persistence.dao.UserStatsDao;



public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String surname;
	private String email;
	private String password;
	private Date birthday;
	private int points;
	private String bio;
	private List<Request> request;
	private List<Review> reviews;
	private UserStats stats;
	private Map<TripCategory, String> attitude;
	
	
	// User Constructor
	public User(String name, String surname, Date birthday, String email, String password) {
		this.name = name;
		this.surname = surname;
		this.setBirthday(birthday);
		this.setEmail(email);
		this.setPassword(password);
		
		// Set default values for remaining attributes
		this.points = 0;
		this.bio ="";
		this.request = new ArrayList<>();
		this.reviews = new ArrayList<>();
		this.stats = new UserStats();
	}
	
	public User() {
		this.request = new ArrayList<>();
		this.reviews = new ArrayList<>();
		this.stats = new UserStats();
	}
	
	public int calculateUserAge() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int today = c.get(Calendar.YEAR);
		c.setTime(birthday);
		int birth = c.get(Calendar.YEAR);
		return today-birth;
	}
	
	public void addPoints(int i) {
		setPoints(getPoints()+i);
	}

	public boolean addReview(Review rev) {
		reviews.add(rev);
		System.out.println("Added review to user:"+this);
		calculateAverageRating(rev.getType());
		return false;
		
	}
	
	private void calculateAverageRating(RoleType role) {
		float sum = 0;
		float avg = 0;
		int count = 0;
		
		for (Review review: this.reviews) {
			if (review.getType() == role){
				sum += review.getVote();
				count++;
			}
		}
		
		if (count != 0) {
			avg = sum/count;
		}
		
		if (role == RoleType.ORGANIZER) {
			System.out.println("Setting average organizer rating:"+avg);
			stats.setOrganizerRating(avg);
			UserStatsDao.getInstance().updateStats(this.getEmail(), this.getPoints(), this.getStats().getOrganizerRating(), this.getStats().getTravelerRating());
			System.out.println(stats.getOrganizerRating());
		} else {
			System.out.println("Setting average traveler rating:"+avg);
			stats.setTravelerRating(avg);
			UserStatsDao.getInstance().updateStats(this.getEmail(), this.getPoints(), this.getStats().getOrganizerRating(), this.getStats().getTravelerRating());
			System.out.println(stats.getTravelerRating());
		}
	}
 	
	/* getters and setters */
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public String getBio() {
		return bio;
	}
	
	public void setBio(String bio) {
		this.bio = bio;
	}
	
	public List<Request> getRequest() {
		return request;
	}
	
	public void setRequest(List<Request> request) {
		this.request = request;
	}

	public Map<TripCategory, String> getAttitude() {
		return attitude;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public UserStats getStats() {
		return stats;
	}

	public void setStats(UserStats stats) {
		this.stats = stats;
	}
}
