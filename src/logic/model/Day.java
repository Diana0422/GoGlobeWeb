package logic.model;

import java.util.List;

public class Day {
	
	private String name;
	private List<String> locations;
	String plan;
	
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
	
}
