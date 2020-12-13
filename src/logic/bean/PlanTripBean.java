package logic.bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import logic.model.Trip;

public class PlanTripBean {
	
	private Trip trip;
	private String tripName;
	private String departureDate;
	private String returnDate;
	private String category1;
	private String category2;
	private String errorMsg;
	
	public PlanTripBean(){
		
	}
	
	public boolean validateForm(){

		if (validateData(this.tripName, this.departureDate, this.returnDate, this.category1, this.category2)){
			return (validateDates(this.departureDate, this.returnDate) && validateCategories(this.category1, this.category2));
		}else{
			return false;
		}		
	}
	
	private boolean validateDates(String departureDate, String returnDate) {
			
		try {
			Date depDate = new SimpleDateFormat("dd/MM/yyyy").parse(departureDate);
			Date retDate = new SimpleDateFormat("dd/MM/yyyy").parse(returnDate);
			Date currentDate = new Date();
			
			if (depDate.before(currentDate)) {
				this.setErrorMsg("Departure date must be later than current date");
				return false;
			}
			if (retDate.before(depDate)) {
				this.setErrorMsg("Return date must be later than departure date");
				return false;
			}
				
		} catch (ParseException e) {
			this.setErrorMsg("Dates must follow the format dd/MM/yyyy");
			return false;
		}			
		return true;
	}
	

	private boolean validateCategories(String category1, String category2) {
		
		
		if (category1.equals(category2)) {
			this.setErrorMsg("Categories");
			return false;		
		}
		return true;
	}
	
	private Boolean validateData(String tripName, String departureDate, String returnDate, String category1, String category2) {
		
		if (tripName == null || tripName.equals("")) {
			this.setErrorMsg("Insert trip name");
			return false;		
		}
		if (departureDate == null || departureDate.equals("")) {
			this.setErrorMsg("Insert deprture date");
			return false;	
		}
		if (returnDate == null || returnDate.equals("")) {
			this.setErrorMsg("Insert return date");
			return false;	
		}
		if (!(validateDateString(departureDate) || validateDateString(returnDate))) {
			this.setErrorMsg("Date format must follow the format dd/MM/yyyy");
			return false;		
		}	
		if (category1.equals("none") || category2.equals("none")) {
			this.setErrorMsg("Both categories must be selected");
			return false;	
		}
		return true;
	}
	
	private boolean validateDateString(String date) {
		
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		format.setLenient(false);
		try {
			format.parse(date);
		}catch(ParseException e){
			return false;	
		}
		return true;
	}

	public String getTripName() {
		return tripName;
	}

	public void setTripName(String tripName) {
		this.tripName = tripName;
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
	
	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
