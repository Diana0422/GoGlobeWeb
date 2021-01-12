package logic.bean;

import java.util.ArrayList;
import java.util.List;

public class DayBean {
	
	private String location;
	private List<ActivityBean> activities;
	
	public DayBean() {
		activities = new ArrayList<>();
	}
	
	public void addActivity(ActivityBean newActivityBean) {
		activities.add(newActivityBean);
		System.out.println("NELLA DAY BEAN:");
		System.out.println(newActivityBean);
	}
	
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public List<ActivityBean> getActivities() {
		return activities;
	}
	public void setActivities(List<ActivityBean> activities) {
		this.activities = activities;
	}

}
