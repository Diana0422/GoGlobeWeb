package logic.model;

import java.io.Serializable;

public class Activity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String time;
	private String title;
	private String description;
	private String estimatedCost;
	
	public Activity( String title, String time, String description, String estimatedCost) {
		this.time = time;
		this.title = title;
		this.description = description;
		this.estimatedCost = estimatedCost;
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEstimatedCost() {
		return estimatedCost;
	}

	public void setEstimatedCost(String estimatedCost) {
		this.estimatedCost = estimatedCost;
	}
	
	
	
	
	

}
