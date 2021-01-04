package logic.bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import logic.control.PlanTripController;

public class PlanTripBean {
	
	private static final String DATE_FORMAT = "dd/mm/yyyy"; 
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
	private boolean shared;
	private int planningDay = 0;

	public PlanTripBean(){
		//Bean classes are supposed to have empty constructors
	}   
	
	//Validates all the fields in the share trip view
	public boolean validateSharingPref() {
		if (this.tripDescription == null || this.tripDescription.equals("")) {
			this.setErrorMsg("Error on trip description.");
			return false;
		}
			
	
		try {
			int min = Integer.parseInt(this.minAge);
			int max = Integer.parseInt(this.maxAge);
			if (min > max) {
				this.setErrorMsg("Minimum age must be lesser than maximum age.");
				return  false;
			}
		}catch (NumberFormatException e) {
			return false;
		}
		
		if (this.minAge == null || this.minAge.equals("")) {
			this.setErrorMsg("Error on minimum age.");
			return false;
		}
		
		if (this.maxAge == null || this.maxAge.equals("")) {
			this.setErrorMsg("Error on maximum age.");
			return false;
		}
		
		
		String logStr = "SHARE FORM INFO: "+"trip description: " + this.tripDescription+" min age: " + this.minAge+" max age: " + this.maxAge;
		Logger.getGlobal().info(logStr);
		return true;
	}
	
	
	//Validates location string in the Plan trip view
	public boolean validateLocation() {
		return (!(this.location == null || this.location.equals("")));
	}

	//Validates the whole trip 
	public boolean validateTrip() {
		return this.tripBean.validateTrip();
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
//			Date currentDate = new Date();
			
			
//			if (depDate.before(currentDate)) {
//				this.setErrorMsg("Departure date must be later than current date");
//				return false;
//			}
			
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
			this.setErrorMsg("Date format must follow the format dd/mm/yyyy");
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
		
		this.setTripBean(PlanTripController.getInstance().setPreferencesBean(this.tripName, departureDate, returnDate, category1, category2));

		String logStr = "RIASSUNTO DEL VIAGGIO:\n"+"titolo: " + this.tripBean.getTitle()+ "\n"+"lunghezza: " + this.tripBean.getTripLength() + "giorni\n"+"partenza: "+this.tripBean.getDepartureDate()+"\n"+"arrivo: "+this.tripBean.getReturnDate()+"\n"+"categoria1: "+this.tripBean.getCategory1()+"\n"+"categoria2: "+this.tripBean.getCategory2();
		for (int i = 0; i < this.tripBean.getTripLength(); i++) {
			logStr = logStr.concat("Giorno 1:" + this.tripBean.getDays().get(i).getActivities().size() + "attivita \n");
		}
		Logger.getGlobal().info(logStr);
	}
	
	public void setSharingPreferences() {
		this.setTripBean(PlanTripController.getInstance().setSharingTripPreferences(this.tripBean, this.minAge, this.maxAge, this.tripDescription));
	}
	
	public void saveLocation() {
		String logStr = "SAVING LOCATION: " + this.location;
		Logger.getGlobal().info(logStr);
		this.tripBean.getDays().get(planningDay).setLocation(location);
	}
	
	//Checks if the day has a location 
	public boolean checkDay() {
		String locationCheck = tripBean.getDays().get(planningDay).getLocation();
		String logStr = "LOCATION CHECK: " + locationCheck;
		Logger.getGlobal().info(logStr);
		return (locationCheck == null || locationCheck.equals(""));
	}	
	
	//Gets the location for the current planning day
	public String getDayLocation() {
		return this.tripBean.getDays().get(planningDay).getLocation();
	}
	
	//GUI CONTROLLER
	public List<DayBean> getTripDays() {
		return this.tripBean.getDays();
	}

	//GUI CONTROLLER
	public int getActivitiesNum() {
		 return this.tripBean.getDays().get(planningDay).getActivities().size();
	}
	
	
	public void addActivity(ActivityBean activityBean) {
		PlanTripController.getInstance().addActivity(this.tripBean, planningDay, activityBean);
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
	
	public boolean saveTrip() {
		return PlanTripController.getInstance().saveTrip(this.tripBean);
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
}
