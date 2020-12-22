package logic.model;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	private Date departureDate;
	private Date returnDate;	
	private List<Day> days;	
	private long tripLength;
	//Meglio lasciare solo id  e List<Days> e incapsulare gli altri attributi in una 
	//classe associata trip info
	
	
	public Trip() {
		
	}
	
	public Trip(int id, String title, String imgFilename, int price, String cat1, String cat2, String departureDate, String returnDate) {
		this.id = id;
		this.title = title;
		this.price = price;
		this.category1 = TripCategory.valueOf(cat1);
		this.category2 = TripCategory.valueOf(cat2);
		
		DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
		try {
			Date dep = df.parse(departureDate);
			Date ret = df.parse(returnDate);
			this.departureDate = dep;
			this.returnDate = ret;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public long getTripLength() {
		return tripLength;
	}

	public void setTripLength(long tripLength) {
		this.tripLength = tripLength;
	}

}
