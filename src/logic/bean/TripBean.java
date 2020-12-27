package logic.bean;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.List;

import logic.model.Activity;

public class TripBean {
	
	private int id;
	private String title;
	private int price;
	private String category1;
	private String category2;
	private BufferedImage img;
	private String departureDate;
	private String returnDate;	
	private long tripLength;
	private List<DayBean> days;
	
	
	public void addActivity(int day, ActivityBean activity) {
		this.getDays().get(day).getActivities().add(activity);
		System.out.println("New activity added to day" + day + "\n");
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public long getTripLength() {
		return tripLength;
	}

	public void setTripLength(long tripLength) {
		this.tripLength = tripLength;
	}

	public List<DayBean> getDays() {
		return days;
	}

	public void setDays(List<DayBean> days) {
		this.days = days;
	}
	
	
}