package logic.model;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

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
		String logStr = "Observer "+o+" subscribed.";
		Logger.getGlobal().log(Level.INFO, logStr);
	}
	
	@Override
	public void detach(Observer o) {
		ReviewChangeManager.getInstance().unregister(this, o);
		String logStr = "Observer "+o+" unsubscribed.";
		Logger.getGlobal().log(Level.INFO, logStr);
	}
	
	@Override
	public void notifyState() {
		String logStr = "Notifing to observers.";
		Logger.getGlobal().log(Level.INFO, logStr);
		ReviewChangeManager.getInstance().notifySubject(this);
		
	}
	
	public double getOrganizerRating() {
		return organizerRating;
	}
	
	public void setOrganizerRating(double organizerRating) {
		this.organizerRating = organizerRating;
		this.notifyState();
	}
	
	public double getTravelerRating() {
		return travelerRating;
	}
	
	public void setTravelerRating(double travelerRating) {
		this.travelerRating = travelerRating;
		this.notifyState();
	}
}
