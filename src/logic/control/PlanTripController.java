package logic.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import logic.bean.ActivityBean;
import logic.model.Activity;
import logic.model.Day;
import logic.model.Trip;
import logic.model.TripCategory;

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
	
	public Trip setPreferences(String title, Date departureDate, Date returnDate, TripCategory category1, TripCategory category2){
			
		Trip trip = new Trip();
		long tripLength = calculateTripLength(departureDate, returnDate) + 1;
		List<Day> days = new ArrayList<>();
		for (int i = 0; i < (int)tripLength; i++) {
			List<Activity> activities = new ArrayList<>();
			Day day = new Day();
			day.setActivities(activities);
			days.add(day);
		}


		trip.setTitle(title);
		trip.setDepartureDate(departureDate);
		trip.setReturnDate(returnDate);
		trip.setCategory1(category1);
		trip.setCategory2(category2);
		trip.setTripLength(tripLength);
		trip.setDays(days);	
		
		
		return trip;	
	}
	
	public long calculateTripLength(Date depDate, Date retDate) {
		long diff = retDate.getTime() - depDate.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	public void addActivity(Trip trip, int planningDay, ActivityBean activity ) {
		System.out.println("NEW ACTIVITY ESSERE COME:");
		System.out.println("\ntitle: " + activity.getTitle() );
		System.out.println("\ntime: " + activity.getTime() );
		System.out.println("\ndescription: " + activity.getDescription() );

		Activity newActivity = new Activity(activity.getTitle(), activity.getTime(), activity.getDescription());
		trip.addActivity(planningDay, newActivity);
	}
}







