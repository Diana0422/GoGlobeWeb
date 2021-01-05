package logic.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import logic.control.PersistenceController;
import logic.model.ModelClassType;
import logic.model.Request;

public class RequestDAOFile implements RequestDAO {
	
	private PersistenceController pc = PersistenceController.getInstance();

	@Override
	public boolean saveRequest(Request request) {
		// Saves a trip into back-end file
		List<Request> requests = getAllRequests();
		requests.add(request);
				
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(pc.getBackendFile(ModelClassType.REQUEST)))) {
			Logger.getGlobal().info("Serializing instance of request \n");
			out.writeObject(requests);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Request> getAllRequests() {
		// Reads a list of trips from the back-end file
		List<Request> empty = new ArrayList<>();
		try (FileInputStream fis= new FileInputStream(pc.getBackendFile(ModelClassType.REQUEST));
			ObjectInputStream ois = new ObjectInputStream(fis)) {
							
			if (fis.available() != 0) {
				Logger.getGlobal().info("FileInputStream is available.");
				Logger.getGlobal().info("ObjectInputStream is available.");
				return (List<Request>) ois.readObject();
			} 
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getGlobal().info("Returning empty");
			return empty;
		}
				
		Logger.getGlobal().info("Returning empty");
		return empty;
	}

	@Override
	public Request getRequest(String tripTitle, String senderEmail, String receiverEmail) {
		// Gets a specific request from back-end file
		
		List<Request> requests = getAllRequests();
				
		for (Request req : requests) {
			if (req.getTarget().getTitle().equals(tripTitle) && req.getSender().getEmail().equals(senderEmail) && req.getReceiver().getEmail().equals(receiverEmail)) return req;
		}
		return null;
	}

	@Override
	public boolean deleteRequest(Request request) {
		// Delete the request from file
	
		List<Request> requests = getAllRequests();
		String logStr = "Requests before delete: "+requests;
		Logger.getGlobal().info(logStr);
		
		for (int i=0; i<requests.size(); i++) {
			if (requests.get(i).getReceiver().getEmail().equals(request.getReceiver().getEmail()) && requests.get(i).getSender().getEmail().equals(request.getSender().getEmail()) && requests.get(i).getTarget().getTitle().equals(request.getTarget().getTitle())) requests.remove(requests.get(i)); 
		}
		logStr = "Requests after delete: "+requests;
		Logger.getGlobal().info(logStr);
						
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(pc.getBackendFile(ModelClassType.REQUEST)))) {
			Logger.getGlobal().info("Serializing instance of request \n");
			out.writeObject(requests);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
