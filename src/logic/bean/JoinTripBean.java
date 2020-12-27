package logic.bean;

import java.util.List;

import logic.control.JoinTripController;


public class JoinTripBean {
	private String searchVal;
	
	private List<TripBean> objects;
	private TripBean trip;

	public JoinTripBean() {
		//Empty constructor
	}
	
	
	public String getSearchVal() {
		return searchVal;
	}
	
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	
	public List<TripBean> getObjects() {
		return objects;
	}
	
	public void setObjects(List<TripBean> objects) {
		this.objects = objects;
	}
	
	public TripBean getTrip() {
		return trip;
	}

	public void setTrip(TripBean trip) {
		this.trip = trip;
	}
	
	public boolean searchTripsByValue() {
		// Calls the controller to search list of available trips
		this.setObjects(JoinTripController.getInstance().searchTrips(this.searchVal));
		
		return !this.getObjects().isEmpty();
	}
}
