package logic.bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import logic.control.PlanTripController;
import logic.model.exceptions.DateFormatException;
import logic.model.exceptions.FormInputException;

public class PlanTripBean {
	
	private static final String DATE_FORMAT = "dd/MM/yyyy"; 
	private TripBean tripBean;
	private String tripName;
	private String departureDate;
	private String returnDate;
	private String category1;
	private String category2;
	private String errorMsg;
	private String location;
	private String tripDescription;
	private String maxAge;
	private String minAge;
	private String maxParticipants;
	private boolean shared;
	private int planningDay = 0;

	public PlanTripBean(){
		//Bean classes are supposed to have empty constructors
	}   
	
	//Validates all the fields in the share trip view
	public boolean validateSharingPref() throws FormInputException {
		if (this.tripDescription == null || this.tripDescription.equals("")) {
			throw new FormInputException("Error on trip description.");
		}
		
		if (this.minAge == null || this.minAge.equals("")) {
			throw new FormInputException("Error on minimum age.");
		}
		
		if (this.maxAge == null || this.maxAge.equals("")) {
			throw new FormInputException("Error on maximum age.");			
		}
		
		if (this.maxParticipants == null || this.maxParticipants.equals("")) {
			throw new FormInputException("Insert maximum participants.");
		}
		
		try {
			int min = Integer.parseInt(this.minAge);
			int max = Integer.parseInt(this.maxAge);
			if (min > max) {
				throw new FormInputException("Age input not valid.");
			}
		}catch (NumberFormatException e){
			throw new FormInputException("Age input not valid");

		}
			
		String logStr = "SHARE FORM INFO: "+"trip description: " + this.tripDescription+" min age: " + this.minAge+" max age: " + this.maxAge;
		Logger.getGlobal().info(logStr);
		return true;
	}
	
	
	//Validates location string in the Plan trip view
	public boolean validateLocation() throws FormInputException{
		if ((this.location == null || this.location.equals(""))) {
			throw new FormInputException("Insert a valid location");
		}		
		return true;
	}

	//Validates the whole trip 
	public boolean validateTrip(){	
		//TODO PENSARE AD EXCEPTION PER IL TRIP NON COMPLETATO
		return this.tripBean.validateTrip();
	}
	
	//Validate all the inputs in the form
	public boolean validateForm()throws FormInputException{
		
		Boolean res = validateData(this.tripName, this.departureDate, this.returnDate, this.category1, this.category2);
		if (Boolean.TRUE.equals(res)){
			try {
				validateDates(this.departureDate, this.returnDate);
			}catch(DateFormatException e) {
				throw new FormInputException(e.getMessage());
			}
			
			return validateCategories(this.category1, this.category2);
		}else{
			return false;
		}		
	}
	
	//Check if the dates are set correctly
	private boolean validateDates(String departureDate, String returnDate) throws FormInputException, DateFormatException {
			
		try {
			Date depDate = new SimpleDateFormat(DATE_FORMAT).parse(departureDate);
			Date retDate = new SimpleDateFormat(DATE_FORMAT).parse(returnDate);
			Date currentDate = new Date();			
			if (depDate.before(currentDate)) {
				this.setErrorMsg("Departure date must be later than current date");
				return false;
			}
			
			if (retDate.before(depDate)) {
				throw new FormInputException("Return date must be later than departure date");
			}			
		} catch (ParseException e) {			
			throw new DateFormatException(DATE_FORMAT);
		}	
		
		return true;

	}
	
	//Check if categories are not equal
	private boolean validateCategories(String category1, String category2) throws FormInputException{
		
		
		if (category1.equals(category2)) {
			throw new FormInputException("Categories can not be of the same type.");	
		}
		
		
		return true;
	}
	
	//Check if all the inputs have been inserted 
	private Boolean validateData(String tripName, String departureDate, String returnDate, String category1, String category2) throws FormInputException {
		
		if (tripName == null || tripName.equals("")) {
			throw new FormInputException("Insert trip name");
		}
		
		if (departureDate == null || departureDate.equals("")) {
			throw new FormInputException("Insert Departure date");
		}
		if (returnDate == null || returnDate.equals("")) {
			throw new FormInputException("Insert return date");
		}
		
		try {
			validateDateString(departureDate);
			validateDateString(returnDate);
			
		} catch (DateFormatException e) {
			throw new FormInputException(e.getMessage());
		} 
		if (category1.equals("NONE") || category2.equals("NONE")) {
			throw new FormInputException("Both categories must be selected");
		}
		return true;
	}
	
	//Check if dates match the right format
	private boolean validateDateString(String date) throws DateFormatException {
		
		DateFormat format = new SimpleDateFormat(DATE_FORMAT);
		format.setLenient(false);
		try {	
			format.parse(date);
			
		}catch(ParseException e){
			throw new DateFormatException(DATE_FORMAT);
		}
		
		return true;
	}
	
	public void saveLocation() {
		String logStr = "SAVING LOCATION: " + this.location;
		Logger.getGlobal().info(logStr);
		this.tripBean.getDays().get(planningDay).setLocationCity(location);
	}
	
	//Checks if the day has a location 
	public boolean checkDay() {
		String locationCheck = tripBean.getDays().get(planningDay).getLocationCity();
		String logStr = "LOCATION CHECK: " + locationCheck;
		Logger.getGlobal().info(logStr);
		return (locationCheck == null || locationCheck.equals(""));
	}	
	
	//Gets the location for the current planning day
	public String getDayLocation() {
		return this.tripBean.getDays().get(planningDay).getLocationCity();
	}
	
	//GUI CONTROLLER
	public List<DayBean> getTripDays() {
		return this.tripBean.getDays();
	}

	//GUI CONTROLLER
	public int getActivitiesNum() {
		 return this.tripBean.getDays().get(planningDay).getActivities().size();
	}
	
	//GUI CONTROLLER
	public String displayActivityTime(int activityNum) {
		return this.getTripDays().get(planningDay).getActivities().get(activityNum).getTime();
	}
	
	//GUI CONTROLLER
	public String displayActivityTitle(int activityNum) {
		return this.getTripDays().get(planningDay).getActivities().get(activityNum).getTitle();
	}
	
	//GUI CONTROLLER
	public String displayActivityDescription(int activityNum) {
		return this.getTripDays().get(planningDay).getActivities().get(activityNum).getDescription();
	}
	
	//GUI CONTROLLER 
	public String displayActivityCost(int activityNum) {
		return this.getTripDays().get(planningDay).getActivities().get(activityNum).getEstimatedCost();
	}
	
	public boolean saveTrip(SessionBean session) {
		return PlanTripController.getInstance().saveTrip(this.tripBean, session);
	}

	public TripBean getTripBean() {
		return tripBean;
	}

	public void setTripBean(TripBean tripBean) {
		this.tripBean = tripBean;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getTripDescription() {
		return tripDescription;
	}

	public void setTripDescription(String tripDescription) {
		this.tripDescription = tripDescription;
	}

	public String getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(String maxAge) {
		this.maxAge = maxAge;
	}

	public String getMinAge() {
		return minAge;
	}

	public void setMinAge(String minAge) {
		this.minAge = minAge;
	}	
	
	public int getPlanningDay() {
		return planningDay;
	}

	public void setPlanningDay(int planningDay) {
		this.planningDay = planningDay;
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

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public boolean isShared() {
		return shared;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
	}

	public String getMaxParticipants() {
		return maxParticipants;
	}

	public void setMaxParticipants(String maxParticipants) {
		this.maxParticipants = maxParticipants;
	}
	
	
}
