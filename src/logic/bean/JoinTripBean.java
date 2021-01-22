package logic.bean;

import java.util.List;


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
}
