package logic.model;

import java.io.Serializable;
import logic.model.interfaces.Observer;
import logic.model.interfaces.Subject;
import logic.model.utils.ReviewChangeManager;

public class UserStats implements Subject, Serializable{

	private static final long serialVersionUID = 1L;
	private double organizerRating;
	private double travelerRating;

	
	@Override
	public void attach(Observer o) {
		ReviewChangeManager.getInstance().register(this, o);
		System.out.println("Observer: "+o+" subscribed.");
		
	}
	
	@Override
	public void detach(Observer o) {
		ReviewChangeManager.getInstance().unregister(this, o);
		System.out.println("Observer: "+o+" unsubscribed.");
	}
	
	@Override
	public void notifyState() {
		System.out.println("Notifing to observers.");
		ReviewChangeManager.getInstance().notifySubject(this);
		
	}
	
	public double getOrganizerRating() {
		return organizerRating;
	}
	
	public void setOrganizerRating(float organizerRating) {
		System.out.println("Organizer rating adjourned");
		this.organizerRating = organizerRating;
		this.notifyState();
	}
	
	public double getTravelerRating() {
		return travelerRating;
	}
	
	public void setTravelerRating(float travelerRating) {
		System.out.println("Traveler rating adjourned");
		this.travelerRating = travelerRating;
		this.notifyState();
	}
}
