package logic.control;

import java.sql.SQLException;
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
import logic.persistence.exceptions.DatabaseException;
import logic.model.Day;
import logic.model.Location;
import logic.model.Place;
import logic.model.Trip;
import logic.model.TripCategory;
import logic.model.factories.HereAdapterFactory;
import logic.model.factories.TripFactory;
import logic.model.interfaces.LocationFinder;
import logic.model.utils.converters.DayBeanConverter;

public class PlanTripController {
		
	private static PlanTripController instance;
	
	private PlanTripController(){
		
	}
	
	public static synchronized PlanTripController getInstance() {
		if (instance == null) {
			instance = new PlanTripController();
		}
		return instance;
	}
	
	public long calculateTripLength(Date depDate, Date retDate) {
		long diff = retDate.getTime() - depDate.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	
	public void addActivity(PlanTripBean planTripBean, ActivityBean newActivityBean) {
		
		DayBean currentDayBean = planTripBean.getTripBean().getDays().get(planTripBean.getPlanningDay());
		currentDayBean.addActivity(newActivityBean);		
	}
	
	public boolean saveTrip(TripBean tripBean, SessionBean organizerBean) throws DatabaseException{
		Trip trip = TripFactory.getInstance().createModel();
		try {
			trip.setOrganizer(UserDaoDB.getInstance().get(organizerBean.getSessionEmail()));
			trip.setTitle(tripBean.getTitle());
			
			//parsing and setting categories
			trip.setCategory1(parseTripCategory(tripBean.getCategory1()));
			trip.setCategory2(parseTripCategory(tripBean.getCategory2()));
			
			//Converting dates
			Date depDate = FormatManager.parseDate(tripBean.getDepartureDate()); 
			Date retDate = FormatManager.parseDate(tripBean.getReturnDate());
	
			//Setting dates
			trip.setDepartureDate(depDate);
			trip.setReturnDate(retDate);
			Logger.getGlobal().info("departure date: "+trip.getDepartureDate());
			Logger.getGlobal().info("return date: "+trip.getReturnDate());
			
			// Setting shared trip preferences
			if (tripBean.isShared()) {
				trip.setShared(true);
				trip.setDescription(tripBean.getDescription());
				trip.setMinAge(Integer.parseInt(tripBean.getMinAge()));
				trip.setMaxAge(Integer.parseInt(tripBean.getMaxAge()));
				trip.setMaxParticipants(Integer.parseInt(tripBean.getMaxParticipants()));
			}	
				
			/* save trip on persistence */
			if (!TripDao.getInstance().saveTrip(trip, tripBean.isShared())) return false;
		
			/*Converting and setting Days list (and activities)*/
			List<Day> days;
			DayBeanConverter dayConverter = new DayBeanConverter(trip.getTitle());
			if ((days = dayConverter.convertFromListBean(tripBean.getDays())) != null) {
				trip.setDays(days); 
				return true;
			}
			return false;
		} catch (DBConnectionException | SQLException e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
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







