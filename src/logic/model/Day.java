package logic.model;

import java.io.Serializable;
import java.util.List;

public class Day implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String location;
	private List<Activity> activities;

	
	public Day() {
		//Empty constructor is needed for plan trip use case	
	}
	
	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}
	
}
