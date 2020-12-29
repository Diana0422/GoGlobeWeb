package logic.bean;

import java.util.ArrayList;
import java.util.List;

public class DayBean {
	
	private String location;
	private List<ActivityBean> activities;
	
	public DayBean() {
		activities = new ArrayList<>();
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