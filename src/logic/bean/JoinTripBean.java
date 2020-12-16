package logic.bean;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import logic.model.TripCategory;
import logic.control.PersistenceController;
import logic.model.Trip;

public class JoinTripBean {
	private String searchVal;
	
	private List<Trip> objects;
	private Trip trip;
	
	private String title;
	private int price;
	private TripCategory category1;
	private TripCategory category2;
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
	public TripCategory getCategory1() {
		return category1;
	}
	public void setCategory1(TripCategory category1) {
		this.category1 = category1;
	}
	public TripCategory getCategory2() {
		return category2;
	}
	public void setCategory2(TripCategory category2) {
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
	
	public List<Trip> getObjects() {
		return objects;
	}
	
	public void setObjects(List<Trip> objects) {
		this.objects = objects;
	}
	
	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
		this.setTitle(trip.getTitle());
		this.setPrice(trip.getPrice());
		this.setImg(trip.getImg());
		this.setCategory1(trip.getCategory1());
		this.setCategory2(trip.getCategory2());
	}
	
	public boolean searchTripsByValue() {
		System.out.println("Search trips by value started.\n");
		System.out.println("Val: "+ this.getSearchVal());
		File f = new File("C:\\Users\\dayli\\git\\GoGlobeWeb\\src\\trips.txt");
		
		List<Trip> trips = null;
		trips = PersistenceController.getInstance().readTripFromFile(f);
		this.setObjects(trips);

		if ((trips == null) || (trips.size() == 1)) {
			System.out.println("No trips to visualize.\n");
			return false;
		} else {
			return true;
		}
	}
	
}
