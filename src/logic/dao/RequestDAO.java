package logic.dao;

import java.util.List;

import logic.model.Request;
import logic.model.exceptions.SerializationException;

public interface RequestDAO {
	
	public boolean saveRequest(Request request) throws SerializationException;
	public boolean deleteRequest(Request request) throws SerializationException;
	public List<Request> getAllRequests() throws SerializationException;
	public Request getRequest(String tripTitle, String senderEmail, String receiverEmail) throws SerializationException;
	
}
