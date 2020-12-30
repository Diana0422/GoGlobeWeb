package logic.model;

public class Request {
	private int id;
	private User sender;
	private User receiver;
	private Trip target;
	private Boolean accepted;
	
	public Boolean getAccepted() {
		return accepted;
	}
	
	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public Trip getTarget() {
		return target;
	}

	public void setTarget(Trip target) {
		this.target = target;
	}
}
