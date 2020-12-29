package logic.bean;

import java.util.List;

public class TripBean {
	
	private int id;
	private String title;
	private int price;
	private String category1;
	private String category2;
	private String imgSrc;
	private String departureDate;
	private String returnDate;	
	private long tripLength;
	private List<DayBean> days;
	
	
	public void addActivity(int day, ActivityBean activity) {
		this.getDays().get(day).getActivities().add(activity);
		System.out.println("New activity added to day" + day + "\n");
	}
	
	public boolean validateTrip() {
		boolean ret = true;
		for (int i = 0; i < days.size(); i++) {
			if (days.get(i).getActivities().isEmpty()) ret = false;
		}
		//TODO: Aggiungere controllo sulla location		
		return ret;
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

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	
}