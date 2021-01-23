package logic.control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import logic.bean.ActivityBean;
import logic.bean.DayBean;
import logic.bean.PlanTripBean;
import logic.bean.SessionBean;
import logic.bean.TripBean;
import logic.persistence.dao.TripDao;
import logic.persistence.dao.UserDaoDB;
import logic.persistence.exceptions.DBConnectionException;
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
		long diff = retDate.getTime() - depDate.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	
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
	
	
	
	public boolean saveTrip(TripBean tripBean, SessionBean organizerBean) throws DBConnectionException {
		Trip trip = TripFactory.getInstance().createModel();
		trip.setOrganizer(UserDaoDB.getInstance().get(organizerBean.getSessionEmail()));
		
		trip.setTitle(tripBean.getTitle());
		
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

			
		/* save trip on persistence */
		if (!TripDao.getInstance().saveTrip(trip)) return false;
	
		/*Converting and setting Days list (and activities)*/
		List<Day> days;
		if ((days = ConversionController.getInstance().convertDayBeanList(tripBean.getDays(), trip.getTitle())) != null) {
			trip.setDays(days); 
			
			if (tripBean.isShared()) {
				trip.setShared(true);
				trip.setDescription(tripBean.getDescription());
				trip.setMinAge(Integer.parseInt(tripBean.getMinAge()));
				trip.setMaxAge(Integer.parseInt(tripBean.getMaxAge()));
			}
			
			return true;
		}
		
		return false;
		
	}
	
	//Use HereAPI to get nearby places suggestions
	public List<Place> getNearbyPlaces(String locationName, String category){
		LocationFinder hereAPI = HereAdapterFactory.getInstance().createHereAdapter();
		Location dayLocation = hereAPI.getLocationInfo(locationName);
		return hereAPI.getNearbyPlaces(dayLocation.getCoordinates(), category);		
	}
	
	private TripCategory parseTripCategory(String category) {
		if (category.equalsIgnoreCase("Fun")) return TripCategory.FUN;	
		if (category.equalsIgnoreCase("Culture")) return TripCategory.CULTURE;	
		if (category.equalsIgnoreCase("Relax")) return TripCategory.RELAX;
		if (category.equalsIgnoreCase("Adventure")) return TripCategory.ADVENTURE;
			
		return TripCategory.NONE;
	}
}







