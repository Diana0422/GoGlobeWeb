package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import logic.bean.SessionBean;
import logic.bean.TripBean;
import logic.control.JoinTripController;
import logic.persistence.exceptions.DatabaseException;

public class TestJoinTripController {
	
	/* Diana Pasquali Test Class */
	private static final String TRIP_TITLE = "TestViaggio";
	private static final String ORGANIZER_ID = "organizer@gmail.com";
	private static final String NOT_ORGANIZER_ID = "notorganizer@gmail.com";
	private static final String PARTICIPANT = "participant@gmail.com";

	@Test
	void testSearchTripsNoException() {
		String value = TRIP_TITLE;
		try {
			List<TripBean> trips = JoinTripController.getInstance().searchTrips(value);
			TripBean onlyTrip = trips.get(0);
			assertEquals(value, onlyTrip.getTitle());
		} catch (DatabaseException e) {
			String logStr = "No exception has occurred during testSearchTripsNoException";
			Logger.getGlobal().log(Level.INFO, logStr);
		}
	}
	
	@Test
	void testSearchTripsWithException() {
		// No trips are matching the searched value
		String value ="QuestoViaggioNonEsiste";
		List<TripBean> empty = null;
		try {
			empty = JoinTripController.getInstance().searchTrips(value);
			String logStr = "Trips search result for value ="+value+"is ---"+empty;
			Logger.getGlobal().log(Level.INFO, logStr);
		} catch (DatabaseException e) {
			assertEquals(null, empty);
		}
	}
	
	@Test
	void testJoinTripSuccessful() {
		// If the user isn't the organizer and its age is in the accepted age range
		String tripTitle = TRIP_TITLE;
		String userEmail = PARTICIPANT;
		TripBean trip = new TripBean();
		trip.setTitle(tripTitle);
		SessionBean session = new SessionBean();
		session.setSessionEmail(userEmail);
		
		try {
			boolean result = JoinTripController.getInstance().joinTrip(trip, session);
			assertEquals(true, result);
		} catch (DatabaseException e) {
			String logStr = "No exception has occurred during testJoinTripSuccessful";
			Logger.getGlobal().log(Level.INFO, logStr);
		}
		
	}
	
	@Test
	void testJoinTripNotSuccessfulUserOrganizer() {
		// If the user is the organizer
		String tripTitle = TRIP_TITLE;
		String userEmail = ORGANIZER_ID;
		TripBean trip = new TripBean();
		trip.setTitle(tripTitle);
		SessionBean session = new SessionBean();
		session.setSessionEmail(userEmail);
		
		try {
			boolean result = JoinTripController.getInstance().joinTrip(trip, session);
			assertEquals(false, result);
		} catch (DatabaseException e) {
			String logStr = "No exception has occurred during testJoinTripNotSuccessfulUserOganizer";
			Logger.getGlobal().log(Level.INFO, logStr);
		}
	}
	
	@Test
	void testJoinTripNotSuccessfulAgeNotInRange() {
		// If the user age is not in trip's range
		String title = TRIP_TITLE;
		String email = NOT_ORGANIZER_ID;
		TripBean trip = new TripBean();
		trip.setTitle(title);
		SessionBean userSession = new SessionBean();
		userSession.setSessionEmail(email);
		
		try {
			boolean result = JoinTripController.getInstance().joinTrip(trip, userSession);
			assertEquals(false, result);
		} catch (DatabaseException e) {
			String logStr = "No exception has occurred during testJoinTripNotSuccessfulAgeNotInRange";
			Logger.getGlobal().log(Level.INFO, logStr);
		}
	}
	
	@Test
	void testJoinTripWithTripException() {
		// The trip doesn't exist
		String tripTitle = "ThisTripNotExists";
		String userEmail = NOT_ORGANIZER_ID;
		TripBean trip = new TripBean();
		trip.setTitle(tripTitle);
		SessionBean session = new SessionBean();
		session.setSessionEmail(userEmail);
		
		try {
			JoinTripController.getInstance().joinTrip(trip, session);
		} catch (DatabaseException e) {
			String logStr = "Exception has occurred during testJoinTripWithTripException";
			Logger.getGlobal().log(Level.INFO, logStr);
			assertEquals("Cannot get trip with title:"+tripTitle+" from database.", e.getMessage());
		}
	}
	
	@Test
	void testJoinTripWithUserException() {
		String tripTitle = TRIP_TITLE;
		String userEmail = "notexistent@gmail.com";
		TripBean trip = new TripBean();
		trip.setTitle(tripTitle);
		SessionBean session = new SessionBean();
		session.setSessionEmail(userEmail);
		
		try {
			JoinTripController.getInstance().joinTrip(trip, session);
		} catch (DatabaseException e) {
			String logStr = "Exception has occurred during testJoinTripWithUserException";
			Logger.getGlobal().log(Level.INFO, logStr);
			assertEquals("Cannot get user from database.", e.getMessage());
		}
	}
}
