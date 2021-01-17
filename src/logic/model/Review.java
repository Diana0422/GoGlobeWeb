package logic.model;

import java.io.Serializable;
import java.util.Date;

public class Review implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private double vote;
	private String comment;
	private Date date;
	private RoleType type;
	private User reviewer;
	
	public double getVote() {
		return vote;
	}
	
	public void setVote(double rating) {
		this.vote = rating;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public RoleType getType() {
		return type;
	}
	
	public void setType(RoleType type) {
		this.type = type;
	}

	public User getReviewer() {
		return reviewer;
	}

	public void setReviewer(User reviewer) {
		this.reviewer = reviewer;
	}

}
