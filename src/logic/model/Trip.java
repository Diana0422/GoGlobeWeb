package logic.model;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Trip implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int id;
	private String title;
	private int price;
	private TripCategory category1;
	private TripCategory category2;
	private BufferedImage img;
	private Date departureDate; //write get and set
	private Date returnDate;	//write get and set
	private List<Day> days;	
	
	public Trip(int id, String title, String imgFilename, int price, TripCategory cat1, TripCategory cat2) {
		this.id = id;
		this.title = title;
		this.price = price;
		this.category1 = cat1;
		this.category2 = cat2;	
	}
	
	public BufferedImage getImg() {
		return img;
	}
	
	public void setImg(BufferedImage img) {
		this.img = img;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public List<Day> getDays() {
		return days;
	}

	public void setDays(List<Day> days) {
		this.days = days;
	}

}
