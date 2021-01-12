package logic.control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import logic.bean.ActivityBean;
import logic.bean.DayBean;
import logic.bean.PlanTripBean;
import logic.bean.SessionBean;
import logic.bean.TripBean;
import logic.dao.TripDAO;
import logic.dao.TripDAOFile;
import logic.dao.UserDAO;
import logic.dao.UserDAOFile;
import logic.model.Day;
import logic.model.Location;
import logic.model.Place;
import logic.model.Trip;
import logic.model.TripCategory;
import logic.model.factories.HereAdapterFactory;
import logic.model.factories.TripFactory;
import logic.model.interfaces.LocationFinder;

public class PlanTripController {
	
	private static final String DATE_FORMAT = "dd/MM/yyyy";

		
	private static PlanTripController instance;
	
	private PlanTripController(){
		
	}
	
	public static synchronized PlanTripController getInstance() {
		if (instance == null) {
			instance = new PlanTripController();
		}
		return instance;
	}
	
//	public TripBean setPreferencesBean(String title, String departureDate, String returnDate, String category1, String category2){
//			
//		TripBean tripBean = new TripBean();
//		
//		//converts Strings to Dates 
//		Date depDate; 
//		Date retDate;
//		try {
//			depDate = new SimpleDateFormat(DATE_FORMAT).parse(departureDate);
//			retDate = new SimpleDateFormat(DATE_FORMAT).parse(returnDate);
//			long tripLength = calculateTripLength(depDate, retDate) + 1;
//			List<DayBean> days = new ArrayList<>();
//			for (int i = 0; i < (int)tripLength; i++) {
//				List<ActivityBean> activities = new ArrayList<>();
//				DayBean day = new DayBean();
//				day.setActivities(activities);
//				days.add(day);
//			}
//
//			tripBean.setTitle(title);
//			tripBean.setDepartureDate(departureDate);
//			tripBean.setReturnDate(returnDate);
//			tripBean.setCategory1(category1);
//			tripBean.setCategory2(category2);
//			tripBean.setTripLength(tripLength);
//			tripBean.setDays(days);	
//			
//			
//			return tripBean;
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}			
//	}
	
//	public TripBean setSharingTripPreferences(TripBean tripBean, String minAge, String maxAge, String description, String maxParticipants) {
//		tripBean.setShared(true);
//		tripBean.setDescription(description);
//		tripBean.setMinAge(minAge);
//		tripBean.setMaxAge(maxAge);	
//		tripBean.setMaxParticipants(maxParticipants);
//		return tripBean;
//	}
	
	//Create tripBean and set trip's preferences
	public TripBean setupTripBean(PlanTripBean planTripBean) {
		TripBean tripBean = planTripBean.getTripBean();	
		//converts Strings to Dates 
			Date depDate; 
			Date retDate;
			try {
				depDate = new SimpleDateFormat(DATE_FORMAT).parse(planTripBean.getDepartureDate());
				retDate = new SimpleDateFormat(DATE_FORMAT).parse(planTripBean.getReturnDate());
				long tripLength = calculateTripLength(depDate, retDate) + 1;
				tripBean.createDays((int)tripLength);
//					List<DayBean> days = new ArrayList<>();
//					for (int i = 0; i < tripLength; i++) {
//						List<ActivityBean> activities = new ArrayList<>();
//						DayBean day = new DayBean();
//						day.setActivities(activities);
//						days.add(day);
//					}

				tripBean.setTitle(planTripBean.getTripName());
				tripBean.setDepartureDate(planTripBean.getDepartureDate());
				tripBean.setReturnDate(planTripBean.getReturnDate());
				tripBean.setCategory1(planTripBean.getCategory1());
				tripBean.setCategory2(planTripBean.getCategory2());
				tripBean.setTripLength(tripLength);
				tripBean.createDays((int)tripLength);
				
			}catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		return tripBean;
	}
	
	public long calculateTripLength(Date depDate, Date retDate) {
		
		System.out.println("la data di partenza e' " + depDate);
		System.out.println("la data di ritorno e' " + retDate);

		long diff = retDate.getTime() - depDate.getTime();
		
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	
//	public void addActivity(TripBean tripBean, int planningDay, ActivityBean activity ) {
//		Logger.getGlobal().info("\nNEW ACTIVITY ESSERE COME:");
//		Logger.getGlobal().info("title: " + activity.getTitle());
//		Logger.getGlobal().info("time: " + activity.getTime());
//		Logger.getGlobal().info("description: " + activity.getDescription());
//		Logger.getGlobal().info("cost: " + activity.getEstimatedCost());
//
//		ActivityBean newActivity = new ActivityBean();
//		newActivity.setTitle(activity.getTitle());
//		newActivity.setTime(activity.getTime());
//		newActivity.setDescription(activity.getDescription());
//		newActivity.setEstimatedCost(activity.getEstimatedCost());
//		tripBean.addActivity(planningDay, newActivity);
//	}
	
	public void addActivity(PlanTripBean planTripBean, ActivityBean newActivityBean) {
		DayBean currentDayBean = planTripBean.getTripDays().get(planTripBean.getPlanningDay());
		currentDayBean.addActivity(newActivityBean);		
	}
	
	public void setSharingPreferences(PlanTripBean planTripBean) {
		TripBean tripBean = planTripBean.getTripBean();
		tripBean.setShared(true);
		tripBean.setDescription(planTripBean.getTripDescription());
		tripBean.setMinAge(planTripBean.getMinAge());
		tripBean.setMaxAge(planTripBean.getMaxAge());	
		tripBean.setMaxParticipants(planTripBean.getMaxParticipants());
	}
	
	
	
	public boolean saveTrip(TripBean tripBean, SessionBean organizerBean) {
		Trip trip = TripFactory.getInstance().createTrip();
		UserDAO userDao = new UserDAOFile();
		trip.setOrganizer(userDao.getUser(organizerBean.getEmail()));
		trip.setTitle(tripBean.getTitle());
		trip.setTripLength(tripBean.getTripLength());
		
		//parsing and setting categories
		trip.setCategory1(parseTripCategory(tripBean.getCategory1()));
		trip.setCategory2(parseTripCategory(tripBean.getCategory2()));
		
		//Converting dates - TODO USARE IL CONVERSION CONTROLLER
		Date depDate = null; 
		Date retDate;
	
			try {
				depDate = new SimpleDateFormat(DATE_FORMAT).parse(tripBean.getDepartureDate());
				retDate = new SimpleDateFormat(DATE_FORMAT).parse(tripBean.getReturnDate());
				//Setting dates
				trip.setDepartureDate(depDate);
				trip.setReturnDate(retDate);
				Logger.getGlobal().info("departure date: "+trip.getDepartureDate());
				Logger.getGlobal().info("return date: "+trip.getReturnDate());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		/*Converting and setting Days list (and activities)*/
		List<Day> days = ConversionController.getInstance().convertDayBeanList(tripBean.getDays());
		for (int i=0; i<days.size(); i++){
			// Set the date for each day
			Calendar c = Calendar.getInstance();
			c.setTime(depDate);
			c.add(Calendar.DATE, i);
			days.get(i).setDate(c.getTime());
			
			// Test if correct
			days.get(i).getDate();
		}
		trip.setDays(days);
		System.out.println(trip.getPrice());
		
		if (tripBean.isShared()) {
			trip.setShared(true);
			trip.setDescription(tripBean.getDescription());
			trip.setMinAge(Integer.parseInt(tripBean.getMinAge()));
			trip.setMaxAge(Integer.parseInt(tripBean.getMaxAge()));
		}

		TripDAO tripDao = new TripDAOFile();
		return tripDao.saveTrip(trip);
		
	}
	
	//Use HereAPI to get nearby places suggestions
	public List<Place> getNearbyPlaces(String locationName, String category){
		LocationFinder hereAPI = HereAdapterFactory.getInstance().createHereAdapter();
		Location dayLocation = hereAPI.getLocationInfo(locationName);
		return hereAPI.getNearbyPlaces(dayLocation.getCoordinates(), category);		
	}
	
	private TripCategory parseTripCategory(String category) {
		if (category.equals("Fun")) return TripCategory.FUN;	
		if (category.equals("Culture")) return TripCategory.CULTURE;	
		if (category.equals("Relax")) return TripCategory.RELAX;
		if (category.equals("Adventure")) return TripCategory.ADVENTURE;
			
		return TripCategory.NONE;
	}
}







