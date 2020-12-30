package logic.control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import logic.bean.ActivityBean;
import logic.bean.DayBean;
import logic.bean.TripBean;
import logic.model.Trip;
import logic.model.TripCategory;

public class PlanTripController {
	
	private static final String DATE_FORMAT = "dd/mm/yyyy";

		
	private static PlanTripController instance;
	
	private PlanTripController(){
		
	}
	
	public static synchronized PlanTripController getInstance() {
		if (instance == null) {
			instance = new PlanTripController();
		}
		return instance;
	}
	
	public TripBean setPreferencesBean(String title, String departureDate, String returnDate, String category1, String category2){
			
		TripBean tripBean = new TripBean();
		
		//converts Strings to Dates 
		Date depDate; 
		Date retDate;
		try {
			depDate = new SimpleDateFormat(DATE_FORMAT).parse(departureDate);
			retDate = new SimpleDateFormat(DATE_FORMAT).parse(returnDate);
			long tripLength = calculateTripLength(depDate, retDate) + 1;
			List<DayBean> days = new ArrayList<>();
			for (int i = 0; i < (int)tripLength; i++) {
				List<ActivityBean> activities = new ArrayList<>();
				DayBean day = new DayBean();
				day.setActivities(activities);
				days.add(day);
			}

			tripBean.setTitle(title);
			tripBean.setDepartureDate(departureDate);
			tripBean.setReturnDate(returnDate);
			tripBean.setCategory1(category1);
			tripBean.setCategory2(category2);
			tripBean.setTripLength(tripLength);
			tripBean.setDays(days);	
			
			
			return tripBean;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}			
	}
	
	public TripBean setSharingTripPreferences(TripBean tripBean, String minAge, String maxAge, String description) {
		tripBean.setShared(true);
		tripBean.setDescription(description);
		tripBean.setMinAge(minAge);
		tripBean.setMaxAge(maxAge);	
		return tripBean;
	}
	
	public long calculateTripLength(Date depDate, Date retDate) {
		long diff = retDate.getTime() - depDate.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	public void addActivity(TripBean tripBean, int planningDay, ActivityBean activity ) {
		System.out.println("NEW ACTIVITY ESSERE COME:");
		System.out.println("\ntitle: " + activity.getTitle() );
		System.out.println("\ntime: " + activity.getTime() );
		System.out.println("\ndescription: " + activity.getDescription() );

		ActivityBean newActivity = new ActivityBean();
		newActivity.setTitle(activity.getTitle());
		newActivity.setTime(activity.getTime());
		newActivity.setDescription(activity.getDescription());
		tripBean.addActivity(planningDay, newActivity);
	}
	
	public boolean saveTrip(TripBean tripBean) {
		Trip trip = new Trip();
		trip.setTitle(tripBean.getTitle());
		trip.setTripLength(tripBean.getTripLength());
		
		//parsing and setting categories
		trip.setCategory1(parseTripCategory(tripBean.getCategory1()));
		trip.setCategory2(parseTripCategory(tripBean.getCategory2()));
		
		//Converting dates
		Date depDate; 
		Date retDate;
	
			try {
				depDate = new SimpleDateFormat(DATE_FORMAT).parse(tripBean.getDepartureDate());
				retDate = new SimpleDateFormat(DATE_FORMAT).parse(tripBean.getReturnDate());
				//Setting dates
				trip.setDepartureDate(depDate);
				trip.setReturnDate(retDate);
				System.out.println(trip.getDepartureDate());
				System.out.println(trip.getReturnDate());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		//Converting and setting Days list (and activities)
		trip.setDays(ConversionController.getInstance().convertDayBeanList(tripBean.getDays()));	
		System.out.println("Trip Days: "+trip.getDays());
		
		if (tripBean.isShared()) {
			trip.setShared(true);
			trip.setDescription(tripBean.getDescription());
			trip.setMinAge(Integer.parseInt(tripBean.getMinAge()));
			trip.setMaxAge(Integer.parseInt(tripBean.getMaxAge()));
		}

		return PersistenceController.getInstance().saveTripOnFile(trip);
		
	}

	
	private TripCategory parseTripCategory(String category) {
		if (category.equals("Fun")) return TripCategory.Fun;	
		if (category.equals("Culture")) return TripCategory.Culture;	
		if (category.equals("Relax")) return TripCategory.Relax;
		if (category.equals("Adventure")) return TripCategory.Adventure;
			
		return TripCategory.None;
	}
}







