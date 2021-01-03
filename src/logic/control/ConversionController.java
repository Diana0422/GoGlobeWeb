package logic.control;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import logic.bean.ActivityBean;
import logic.bean.DayBean;
import logic.bean.TripBean;
import logic.model.Activity;
import logic.model.Day;
import logic.model.Trip;
import logic.model.TripCategory;

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
	
	public List<Day> convertDayBeanList(List<DayBean> dayBeans){
		List<Day> days = new ArrayList<>();
		for (int i = 0; i < dayBeans.size(); i++) {
			Day day = new Day();
			day.setLocation(dayBeans.get(i).getLocation());
			day.setActivities(convertActivityBeanList(dayBeans.get(i).getActivities()));
			days.add(day);
		}
		return days;
	}
	
	
	private List<Activity> convertActivityBeanList(List<ActivityBean> activityBeans){
		List<Activity> activities = new ArrayList<>();
		for (int i = 0; i < activityBeans.size(); i++) {
			String title = activityBeans.get(i).getTitle();
			String time = activityBeans.get(i).getTime();
			String description = activityBeans.get(i).getDescription();
			Activity activity = new Activity(title, time, description);
			activities.add(activity);			
		}
		
		return activities;
		
	}
	
	
	public List<DayBean> convertDayList(List<Day> days){
		List<DayBean> dayBeans = new ArrayList<>();
		for (int i = 0; i < days.size(); i++) {
			DayBean dayBean = new DayBean();
			dayBean.setLocation(days.get(i).getLocation());
			dayBean.setActivities(convertActivityList(days.get(i).getActivities()));
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
			ActivityBean activityBean = new ActivityBean();
			activityBean.setTitle(title);
			activityBean.setTime(time);
			activityBean.setDescription(description);
			activityBeans.add(activityBean);			
		}
		
		return activityBeans;
		
	}
	
	public List<TripBean> convertTripList(List<Trip> trips){
		List<TripBean> tripBeans = new ArrayList<>();
		for (int i=0; i<trips.size(); i++) {
			Trip t = trips.get(i);
			TripBean bean = new TripBean();
			bean.setId(t.getId());
			bean.setTitle(t.getTitle());
			bean.setPrice(t.getPrice());
			bean.setCategory1(t.getCategory1().toString());
			bean.setCategory2(t.getCategory2().toString());
			bean.setImgSrc(t.getImgSrc());
			
			// Converting Dates to String
			DateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
			String depDateStr = formatter.format(t.getDepartureDate());
			String retDateStr = formatter.format(t.getReturnDate());
			bean.setDepartureDate(depDateStr);
			bean.setReturnDate(retDateStr);
			bean.setTripLength(t.getTripLength());
			
			// Adding days and activities to the trip bean
			bean.setDays(ConversionController.getInstance().convertDayList(t.getDays()));
			tripBeans.add(bean);
		}
		return tripBeans;
		
	}
	
	public TripCategory parseTripCategory(String category) {
		if (category.equals("Fun")) return TripCategory.FUN;	
		if (category.equals("Culture")) return TripCategory.CULTURE;	
		if (category.equals("Relax")) return TripCategory.RELAX;
		if (category.equals("Adventure")) return TripCategory.ADVENTURE;
			
		return TripCategory.NONE;
	}	
}
