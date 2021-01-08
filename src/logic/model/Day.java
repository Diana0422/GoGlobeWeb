package logic.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Day implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String location;
	private List<Activity> activities;
	private Date date;
	private int budget;
	
	public Day() {
		activities = new ArrayList<>();	
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}
	
	public int calclulateBudget() {
		int costSum = 0;
		
		for (Activity act: this.activities) {
			costSum += act.getEstimatedCost();
		}
		setBudget(costSum);
		return costSum;
		
	}
	
}
