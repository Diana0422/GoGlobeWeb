package logic.model;

import java.io.Serializable;
import java.util.List;

public class Day implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String name;
	private List<String> locations;
	private List<Activity> activities;
	String plan;
	
	public Day() {
		//Empty constructor is needed for plan trip use case	
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<String> getLocations() {
		return locations;
	}
	
	public void setLocations(List<String> locations) {
		this.locations = locations;
	}
	
	public String getPlan() {
		return plan;
	}
	
	public void setPlan(String plan) {
		this.plan = plan;
	}
	
	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	
}
