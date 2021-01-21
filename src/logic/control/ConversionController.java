package logic.control;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import logic.bean.ActivityBean;
import logic.bean.DayBean;
import logic.bean.RequestBean;
import logic.bean.ReviewBean;
import logic.bean.TripBean;
import logic.bean.UserBean;
import logic.dao.TripDAO;
import logic.dao.TripDAOFile;
import logic.model.Activity;
import logic.model.Day;
import logic.model.Location;
import logic.model.Request;
import logic.model.Review;
import logic.model.Trip;
import logic.model.TripCategory;
import logic.model.User;
import logic.model.exceptions.SerializationException;
import logic.persistence.dao.ActivityDao;
import logic.persistence.dao.DayDao;
import logic.persistence.dao.ReviewDao;
import logic.persistence.dao.UserDaoDB;
import logic.persistence.dao.UserStatsDao;

public class ConversionController {

	private static ConversionController instance = null;
	
	private ConversionController() {
		// This constructor must be empty
	}
	
	public static ConversionController getInstance() {
		if (instance == null) {
			instance = new ConversionController();
		}
		
		return instance;
	}
	
	
	/* Controller METHODS */
	
	public List<Day> convertDayBeanList(List<DayBean> dayBeans, String tripTitle){
		List<Day> days = new ArrayList<>();
		for (int i = 0; i < dayBeans.size(); i++) {
			Day day = new Day();
			day.setId(i+1);
			Location loc = new Location(dayBeans.get(i).getLocationCity(), dayBeans.get(i).getLocationCountry(), null);
			day.setLocation(loc);
			day.calclulateBudget();
			
			// save day to persistence
			if (DayDao.getInstance().saveDay(day, tripTitle)) {
				System.out.println("day saved.");
			} else {
				System.out.println("day not saved");
			}
			day.setActivities(convertActivityBeanList(dayBeans.get(i).getActivities(), day.getId(), tripTitle));
			days.add(day);
		}
		return days;
	}
	
	
	private List<Activity> convertActivityBeanList(List<ActivityBean> activityBeans, int dayId, String tripTitle){
		List<Activity> activities = new ArrayList<>();
		for (int i = 0; i < activityBeans.size(); i++) {
			String title = activityBeans.get(i).getTitle();
			String time = activityBeans.get(i).getTime();
			String description = activityBeans.get(i).getDescription();
			int cost = Integer.parseInt(activityBeans.get(i).getEstimatedCost());
			Activity activity = new Activity(title, time, description, cost);
			activities.add(activity);
			
			//save the activity in persistence
			if (ActivityDao.getInstance().saveActivity(activity, dayId, tripTitle)) {
				System.out.println("activity saved.");
			}
		}
		
		return activities;
	}
	
	
	public List<DayBean> convertDayList(List<Day> days, String tripTitle){
		List<DayBean> dayBeans = new ArrayList<>();
		for (int i = 0; i < days.size(); i++) {
			int dayNum = i+1;
			DayBean dayBean = new DayBean();
			dayBean.setLocationCity(days.get(i).getLocation().getCity());
			dayBean.setLocationCountry(days.get(i).getLocation().getCountry());
			dayBean.setActivities(convertActivityList(ActivityDao.getInstance().getActivitiesByTrip(tripTitle, dayNum)));
			dayBeans.add(dayBean);
		}
		return dayBeans;
	}
	
	private List<ActivityBean> convertActivityList(List<Activity> activities){
		List<ActivityBean> activityBeans = new ArrayList<>();
		for (int i = 0; i < activities.size(); i++) {
			String title = activities.get(i).getTitle();
			String time = activities.get(i).getTime();
			String description = activities.get(i).getDescription();
			String cost = String.valueOf(activities.get(i).getEstimatedCost());
			ActivityBean activityBean = new ActivityBean();
			activityBean.setTitle(title);
			activityBean.setTime(time);
			activityBean.setDescription(description);
			activityBean.setEstimatedCost(cost);
			activityBeans.add(activityBean);			
		}
		
		return activityBeans;
		
	}
	
	public List<TripBean> convertTripList(List<Trip> trips){
		List<TripBean> tripBeans = new ArrayList<>();
		for (int i=0; i<trips.size(); i++) {
			Trip t = trips.get(i);
			TripBean bean = new TripBean();
			bean.setOrganizer(convertToUserBean(UserDaoDB.getInstance().getTripOrganizer(t.getTitle())));
			bean.setDays(convertDayList(DayDao.getInstance().getTripDays(t.getTitle()), t.getTitle()));
			bean.setParticipants(convertUserList(UserDaoDB.getInstance().getTripParticipants(t.getTitle())));
			bean.setDescription(t.getDescription());
			bean.setId(t.getId());
			bean.setTitle(t.getTitle());
			bean.setPrice(t.getPrice());
			bean.setTicketPrice(t.getTicketPrice());
			bean.setCategory1(t.getCategory1().toString());
			bean.setCategory2(t.getCategory2().toString());
			bean.setImgSrc(t.getImgSrc());
			bean.setShared(t.isShared());
			bean.setMinAge(Integer.toString(t.getMinAge()));
			bean.setMaxAge(Integer.toString(t.getMaxAge()));
			bean.setMaxParticipants(Integer.toString(t.getMaxParticipants()));
			
			// Converting Dates to String
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String depDateStr = formatter.format(t.getDepartureDate());
			String retDateStr = formatter.format(t.getReturnDate());
			bean.setDepartureDate(depDateStr);
			bean.setReturnDate(retDateStr);
//			bean.setTripLength(t.getTripLength());
			
			tripBeans.add(bean);
		}
		return tripBeans;
		
	}
	

	public List<UserBean> convertUserList(List<User> users) {
		List<UserBean> beans = new ArrayList<>();
		
		for (User user: users) {
			UserBean bean = convertToUserBean(user);
			beans.add(bean);
		}
		
		return beans;
	}

	public List<RequestBean> convertRequestList(List<Request> requests) {
		List<RequestBean> requestBeans = new ArrayList<>();
		
		for (Request req: requests) {
			RequestBean bean = new RequestBean();
			bean.setTripTitle(req.getTarget().getTitle());
			if (req.getSender() != null) {
				bean.setSenderName(req.getSender().getName());
				bean.setSenderSurname(req.getSender().getSurname());
				bean.setSenderEmail(req.getSender().getEmail());
				bean.setSenderAge(req.getSender().calculateUserAge());	
			}
			
			if (req.getReceiver() != null) {
				bean.setReceiverEmail(req.getReceiver().getEmail());
				bean.setReceiverName(req.getReceiver().getName());
				bean.setReceiverSurname(req.getReceiver().getSurname());
			}
			requestBeans.add(bean);
		}
		System.out.println(requestBeans);
		return requestBeans;
		
	}
	
	public TripCategory parseTripCategory(String category) {
		if (category.equals("Fun")) return TripCategory.FUN;	
		if (category.equals("Culture")) return TripCategory.CULTURE;	
		if (category.equals("Relax")) return TripCategory.RELAX;
		if (category.equals("Adventure")) return TripCategory.ADVENTURE;
			
		return TripCategory.NONE;
	}	
	
	public UserBean convertToUserBean(User user) {
		UserBean bean = new UserBean();
		bean.setEmail(user.getEmail());
		bean.setName(user.getName());
		bean.setSurname(user.getSurname());
		bean.setBio(user.getBio());
		bean.setPoints(user.getPoints());
		bean.setAge(user.calculateUserAge());
		System.out.println("In conversion to user bean reviews:"+user.getReviews());
		bean.setReviews(convertReviewList(ReviewDao.getInstance().getUserReviews(user.getEmail())));
		bean.setOrgRating(UserStatsDao.getInstance().getUserStats(user.getEmail()).getOrganizerRating());
		bean.setTravRating(UserStatsDao.getInstance().getUserStats(user.getEmail()).getTravelerRating());
		return bean;
	}

	public List<ReviewBean> convertReviewList(List<Review> reviews) {
		List<ReviewBean> list = new ArrayList<>();
		if (reviews.isEmpty()) return list;
		for (Review rev: reviews) {
			ReviewBean bean = new ReviewBean();
			bean.setReviewerName(rev.getReviewer().getName());
			bean.setReviewerSurname(rev.getReviewer().getSurname());
			bean.setTitle(rev.getTitle());
			bean.setComment(rev.getComment());
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    	String date = formatter.format(rev.getDate());
	    	bean.setDate(date);
	    	bean.setVote(rev.getVote());
	    	list.add(bean);
		}
		
		return list;
	}

	public Trip convertToTrip(TripBean tripBean) throws SerializationException {
		TripDAO tripDao = new TripDAOFile();
		List<Trip> list;
		try {
			list = tripDao.getAllTrips();
			
			for (Trip trip: list) {
				if (tripBean.getTitle().equals(trip.getTitle())) return trip;
			}
		} catch (SerializationException e) {
			throw new SerializationException(e, "Error in trip conversion.");
		}
		return null;
		
	}
}
