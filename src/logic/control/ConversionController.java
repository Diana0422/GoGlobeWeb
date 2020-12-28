package logic.control;

import java.util.ArrayList;
import java.util.List;

import logic.bean.ActivityBean;
import logic.bean.DayBean;
import logic.model.Activity;
import logic.model.Day;

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
	
	
}
