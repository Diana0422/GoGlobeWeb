package logic.bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
	private int planningDay = 0;
	
	
	public PlanTripBean(){
		//Bean classes are supposed to have empty constructors
	}       
	
	public boolean validateLocation() {
		return (this.location == null || this.location.equals(""));
	}



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

		System.out.println("RIASSUNTO DEL VIAGGIO:\n");
		System.out.println("titolo: " + this.tripBean.getTitle()+ "\n");
		System.out.println("lunghezza: " + this.tripBean.getTripLength() + "giorni\n");
		System.out.println("partenza: "+this.tripBean.getDepartureDate());
		System.out.println("arrivo: "+this.tripBean.getReturnDate());
		System.out.println("categoria1: "+this.tripBean.getCategory1());
		System.out.println("categoria2: "+this.tripBean.getCategory2());
		for (int i = 0; i < this.tripBean.getTripLength(); i++) {
			System.out.println("Giorno 1:" + this.tripBean.getDays().get(i).getActivities().size() + "attività\n");

		}
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
	
	//GUI CONTROLLER
	public List<DayBean> getTripDays() {
		return this.tripBean.getDays();
	}

	public int getPlanningDay() {
		return planningDay;
	}

	public void setPlanningDay(int planningDay) {
		this.planningDay = planningDay;
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
	
	
	
	public void saveLocation() {
		System.out.println("SAVING LOCATION: " + this.location);
		this.tripBean.getDays().get(planningDay).setLocation(location);
	}
	
	public boolean checkDay() {
		String locationCheck = tripBean.getDays().get(planningDay).getLocation();
		System.out.println("LOCATION CHECK: " + locationCheck);
		return (locationCheck == null || locationCheck.equals(""));
	}	
	
	public String getDayLocation() {
		return this.tripBean.getDays().get(planningDay).getLocation();
	}
}
