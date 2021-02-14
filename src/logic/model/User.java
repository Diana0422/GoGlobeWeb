package logic.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import logic.model.exceptions.DuplicateException;
import logic.persistence.dao.ReviewDao;
import logic.persistence.dao.UserDao;
import logic.persistence.dao.UserStatsDao;
import logic.persistence.exceptions.DBConnectionException;
import logic.persistence.exceptions.DatabaseException;



public class User {
	
	private String name;
	private String surname;
	private String email;
	private String password;
	private Date birthday;
	private String bio;
	private List<Request> incRequests;
	private List<Request> sentRequests;
	private List<Review> reviews;
	private UserStats stats;
	private Map<TripCategory, Integer> attitude;
	
	
	// User Constructor
	public User(String name, String surname, Date birthday, String email, String password) {
		this.name = name;
		this.surname = surname;
		this.setBirthday(birthday);
		this.setEmail(email);
		this.setPassword(password);
		
		// Set default values for remaining attributes
		this.bio ="";
		this.incRequests = new ArrayList<>();
		this.sentRequests = new ArrayList<>();
		this.reviews = new ArrayList<>();
		this.stats = new UserStats();
		this.attitude = new EnumMap<>(TripCategory.class);
	}
	
	public User() {
		this.incRequests = new ArrayList<>();
		this.sentRequests = new ArrayList<>();
		this.reviews = new ArrayList<>();
		this.stats = new UserStats();
		this.attitude = new EnumMap<>(TripCategory.class);
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
		this.getStats().setPoints(this.getStats().getPoints()+i);
	}

	public boolean addReview(Review rev) throws DatabaseException {
		reviews.add(rev);
		calculateAverageRating(rev.getType());
		try {
			return ReviewDao.getInstance().save(rev, this.email);
		} catch (DBConnectionException | SQLException e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
		
	}
	
	private void calculateAverageRating(RoleType role) throws DatabaseException {
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
		
		try {
			if (role == RoleType.ORGANIZER) {
				stats.setOrganizerRating(avg);
				UserStatsDao.getInstance().updateStats(this.getEmail(), this.getStats().getPoints(), this.getStats().getOrganizerRating(), this.getStats().getTravelerRating());
			} else {
				stats.setTravelerRating(avg);
				UserStatsDao.getInstance().updateStats(this.getEmail(), this.getStats().getPoints(), this.getStats().getOrganizerRating(), this.getStats().getTravelerRating());
			}	
		} catch(DBConnectionException | SQLException e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}
	
	public void recalculateAttitude(TripCategory category1, TripCategory category2) throws DatabaseException {
		// Calculate user's traveling attitude
		this.setAttitude(category1);
		this.setAttitude(category2);
		try {
			UserStatsDao.getInstance().updateAttitude(this.email, this.getAttitudeValue(TripCategory.FUN), this.getAttitudeValue(TripCategory.CULTURE), this.getAttitudeValue(TripCategory.RELAX), this.getAttitudeValue(TripCategory.ADVENTURE));
		} catch (SQLException | DBConnectionException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

	
	public void addToIncRequests(Request r) {
		Request incRequest = new Request();
		incRequest.setAccepted(r.getAccepted());
		incRequest.setId(r.getId());
		incRequest.setTarget(r.getTarget());
		incRequest.setSender(r.getSender());
		this.incRequests.add(incRequest);
	}
	
	public boolean addToSentRequests(Request r) throws DatabaseException, DuplicateException {
		Request sentRequest = new Request();
		sentRequest.setAccepted(r.getAccepted());
		sentRequest.setId(r.getId());
		sentRequest.setTarget(r.getTarget());
		sentRequest.setSender(r.getSender());
		this.sentRequests.add(sentRequest);
		return sentRequest.storeRequest(this.email);
	}
	
	
	public void setIncRequests(List<Request> incRequests) {
		for (Request r: incRequests) {
			this.addToIncRequests(r);
		}
	}
	
	public void setSentRequests(List<Request> sentRequests) throws DatabaseException, DuplicateException {
		for (Request r: sentRequests) {
			this.addToSentRequests(r);
		}
	}
	
	public void deleteIncomingRequest(Request req) throws DatabaseException {
		req.deleteRequest();
		this.getIncRequests().remove(req);
		
	}
	
	public void deleteSentRequest(Request req) {
		for (Request r: this.getSentRequests()) {
			if (r.getTarget().getTitle().equals(req.getTarget().getTitle()) && (r.getSender().equals(req.getSender()))) this.getSentRequests().remove(r);
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
	
	public String getBio() {
		return bio;
	}
	
	public void setBio(String bio) {
		this.bio = bio;
	}


	public Map<TripCategory, Integer> getAttitude() {
		return attitude;
	}
	
	public Integer getAttitudeValue(TripCategory category) {
		return this.attitude.get(category);
	}
	
	private void setAttitude(TripCategory category) {
		Integer value = attitude.get(category)+1;
		for (Map.Entry<TripCategory, Integer> entry: attitude.entrySet()) {
			if (entry.getKey().equals(category)) entry.setValue(value);
		}
	}
	
	public void copyAttitude(Map<TripCategory, Integer> mapping) {
		this.attitude.putAll(mapping);
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
		for (Review r: reviews) {
			this.reviews.add(r);
		}
	}

	public UserStats getStats() {
		return stats;
	}

	public void setStats(UserStats stats) {
		this.stats = new UserStats();
		this.stats.setOrganizerRating(stats.getOrganizerRating());
		this.stats.setTravelerRating(stats.getTravelerRating());
		this.stats.setPoints(stats.getPoints());
	}

	public List<Request> getIncRequests() {
		return incRequests;
	}

	public List<Request> getSentRequests() {
		return sentRequests;
	}

	public boolean storeUser() throws DatabaseException {
		try {
			return UserDao.getInstance().save(this);
		} catch (DBConnectionException | SQLException e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}
	
	public static User getUserByEmail(String email) throws DatabaseException {
		try {
			return UserDao.getInstance().get(email);
		} catch (DBConnectionException | SQLException e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}
	
	public boolean updateUserInfo(String newName, String newSurname, String newBio) throws DatabaseException {
		try {
			return UserDao.getInstance().update(this, newName, newSurname, newBio);
		} catch (DBConnectionException | SQLException e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}
	
	public boolean updateStats() throws DatabaseException {
		return this.stats.updateUserStats(this.email);
	}
	
	public static User getRequestReceiver(String sender, String title) throws DatabaseException {
		try {
			return UserDao.getInstance().getRequestReceiver(sender, title);
		} catch (DBConnectionException | SQLException e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}
}
