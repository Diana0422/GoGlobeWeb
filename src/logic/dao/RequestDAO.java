package logic.dao;

import java.util.List;

import logic.model.Request;

public interface RequestDAO {
	
	public boolean saveRequest(Request request);
	public boolean deleteRequest(Request request);
	public List<Request> getAllRequests();
	public Request getRequest(String tripTitle, String senderEmail, String receiverEmail);
	
}
