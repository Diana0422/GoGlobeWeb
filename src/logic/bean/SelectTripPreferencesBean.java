package logic.bean;

import logic.control.SelectTripPreferencesController;

public class SelectTripPreferencesBean {
	
	private String tripName;
//	private Date departureDate = new Date();
//	private Date returnDate = new Date();
	private String departureDate;
	private String returnDate;
	private String category1;
	private String category2;
	
	public SelectTripPreferencesBean(){
		
	}
	
	public boolean validateForm() {
		return SelectTripPreferencesController.getInstance().validateForm(tripName, category1, category2, departureDate, returnDate);	
	}

	public String getTripName() {
		return tripName;
	}

	public void setTripName(String tripName) {
		this.tripName = tripName;
	}


//	public Date getDepartureDate() {
//		return departureDate;
//	}
//
//	public void setDepartureDate(Date departureDate) {
//		this.departureDate = departureDate;
//	}
//
//	public Date getReturnDate() {
//		return returnDate;
//	}
//
//	public void setReturnDate(Date returnDate) {
//		this.returnDate = returnDate;
//	}

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

}
