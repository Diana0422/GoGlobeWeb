package logic.control;

import java.util.Date;
import java.util.List;

import logic.model.Day;
import logic.model.Trip;
import logic.model.factories.IPFinderAdapterFactory;
import logic.model.factories.PositionAdapterFactory;

public class ParticipationController {
	
	private String userIP;
	private String userPos;
	private Date today;
	
	public ParticipationController() {
		this.today = new Date();
	}
	
	public boolean checkParticipation(Trip trip) {
		// TODO dummy 
		Date demoDate = new Date();
		
		Day d = new Day();
		d.setDate(new Date());
		d.setLocation("Rome");
		
		trip.getDays().add(d);
		
		List<Day> days = trip.getDays();
		for (Day day: days) {
			System.out.println(day);
			if (day.getDate().compareTo(demoDate) == 0) {
				System.out.println("Here");
				userIP = IPFinderAdapterFactory.getInstance().createIPFinderAdapter().getCurrentIP();
				userPos = PositionAdapterFactory.getInstance().createIPLocationAdapter().getIPCurrentPosition(userIP);
				System.out.println(userPos);
				if (!userPos.equals(day.getLocation())) {
					System.out.println("Participation CHECKED. ok");
					return true;
				} else {
					System.out.println("Participation INVALID. no");
					return false;
				}
			}
		}
		return false;
		
	}
	
	public static void main(String [] args) {
		Trip trip = new Trip();
		ParticipationController c = new ParticipationController();
		c.checkParticipation(trip);
	}

}
