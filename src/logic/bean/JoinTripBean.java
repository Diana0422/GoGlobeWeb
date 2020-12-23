package logic.bean;

import java.awt.image.BufferedImage;
import java.util.List;

import logic.control.JoinTripController;


public class JoinTripBean {
	private String searchVal;
	
	private List<TripBean> objects;
	private TripBean trip;
	
	private String title;
	private int price;
	private String category1;
	private String category2;
	private BufferedImage img;
	
	//JoinTripBean Constructor
	public JoinTripBean() {
		System.out.println("JoinTripBeanInitialized");
		System.out.println("searchVal: "+this.searchVal);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getCategory1() {
		return category1;
	}
	
	public void setCategory1(String category1) {
		this.category1 = category1;
	}
	
	public String getCategory2() {
		return category2;
	}
	
	public void setCategory2(String category2) {
		this.category2 = category2;
	}
	
	public BufferedImage getImg() {
		return img;
	}
	
	public void setImg(BufferedImage img) {
		this.img = img;
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
		this.setTitle(trip.getTitle());
		this.setPrice(trip.getPrice());
		this.setImg(trip.getImg());
		this.setCategory1(trip.getCategory1());
		this.setCategory2(trip.getCategory2());
	}
	
	public boolean searchTripsByValue() {
		// Calls the controller to search list of available trips
		this.setObjects(JoinTripController.getInstance().searchTrips(this.searchVal));
		
		if (this.getObjects().isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
}
