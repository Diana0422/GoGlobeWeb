package logic.bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import logic.control.PlanTripController;
import logic.model.Day;
import logic.model.Trip;
import logic.model.TripCategory;

public class PlanTripBean {
	
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private Trip trip;    
	private String tripName;
	private String departureDate;
	private String returnDate;
	private String category1;
	private String category2;
	private String errorMsg;
	
	
	public PlanTripBean(){
		//Bean classes are supposed to have  empty constructors
	}
	
	//Validate all the inputs in the form
	public boolean validateForm(){
		
		Boolean res = validateData(this.tripName, this.departureDate, this.returnDate, this.category1, this.category2);
		if (Boolean.TRUE.equals(res)){
			return (validateDates(this.departureDate, this.returnDate) && validateCategories(this.category1, this.category2));
		}else{
			return false;
		}		
	}
	
	//Check if the dates are set correctly
	private boolean validateDates(String departureDate, String returnDate) {
			
		try {
			Date depDate = new SimpleDateFormat(DATE_FORMAT).parse(departureDate);
			Date retDate = new SimpleDateFormat(DATE_FORMAT).parse(returnDate);
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
			this.setErrorMsg("Dates must follow the format " + DATE_FORMAT);
			return false;
		}			
		return true;
	}
	
	//Check if categories are not equal
	private boolean validateCategories(String category1, String category2) {
		
		
		if (category1.equals(category2)) {
			this.setErrorMsg("Categories");
			return false;		
		}
		return true;
	}
	
	//Check if all the inputs have been inserted 
	private Boolean validateData(String tripName, String departureDate, String returnDate, String category1, String category2) {
		
		if (tripName == null || tripName.equals("")) {
			this.setErrorMsg("Insert trip name");
			return false;		
		}
		if (departureDate == null || departureDate.equals("")) {
			this.setErrorMsg("Insert departure date");
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
	
	//Check if dates match the right format
	private boolean validateDateString(String date) {
		
		DateFormat format = new SimpleDateFormat(DATE_FORMAT);
		format.setLenient(false);
		try {
			format.parse(date);
		}catch(ParseException e){
			return false;	
		}
		return true;
	}
	
	//Create new trip instance and set 
	public void setPreferences(){
		
		try {
			Date depDate = new SimpleDateFormat(DATE_FORMAT).parse(departureDate);
			Date retDate = new SimpleDateFormat(DATE_FORMAT).parse(returnDate);
			TripCategory categoryValue1 = parseTripCategory(category1);
			TripCategory categoryValue2 = parseTripCategory(category2);

			this.setTrip(PlanTripController.getInstance().setPreferences(this.tripName, depDate, retDate, categoryValue1, categoryValue2));
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		

	}
	
	private TripCategory parseTripCategory(String category) {
		if (category.equals("Fun")) return TripCategory.Fun;	
		if (category.equals("Culture")) return TripCategory.Culture;	
		if (category.equals("Relax")) return TripCategory.Relax;
		if (category.equals("Adventure")) return TripCategory.Adventure;
			
		return TripCategory.None;
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
	
	public List<Day> getTripDays() {
		return this.trip.getDays();
	}

}
