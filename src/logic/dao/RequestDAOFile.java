package logic.dao;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import logic.model.exceptions.SerializationException;
import logic.model.utils.RequestSerialObject;

public class RequestDAOFile implements RequestDAO {
	
	private PersistenceController pc = PersistenceController.getInstance();

	@Override
	public boolean saveRequest(Request request) throws SerializationException {
		// Saves a trip into back-end file
		List<Request> requests = getAllRequests();
		requests.add(request);
				
		try (FileOutputStream fos = new FileOutputStream(pc.getBackendFile(ModelClassType.REQUEST));
			ObjectOutputStream out = new ObjectOutputStream(fos)) {
			Logger.getGlobal().info("Serializing instance of request \n");
			RequestSerialObject o = new RequestSerialObject(requests);
			out.writeObject(o);
			return true;
		} catch (IOException e) {
			throw new SerializationException(e.getCause(), "Error in opening the stream to the file.");
		}
	}
	
	@Override
	public List<Request> getAllRequests() throws SerializationException {
		// Reads a list of trips from the back-end file
		List<Request> empty = new ArrayList<>();
		try (FileInputStream fis= new FileInputStream(pc.getBackendFile(ModelClassType.REQUEST));
			ObjectInputStream ois = new ObjectInputStream(fis)) {
							
			if (fis.available() != 0) {
				Logger.getGlobal().info("FileInputStream is available.");
				Logger.getGlobal().info("ObjectInputStream is available.");
				RequestSerialObject o = (RequestSerialObject) ois.readObject();
				return o.getList();
			} 
		} catch (FileNotFoundException e) {
			throw new SerializationException(e.getCause(), "The file specified was not found in the workingspace.");
		} catch (EOFException e) {
			RequestSerialObject o = new RequestSerialObject(empty);
			initializeFile(o);
		} catch (IOException e) {
			throw new SerializationException(e.getCause(), "Error in opening the stream to the file.");
		} catch (ClassNotFoundException e) {
			throw new SerializationException(e.getCause(), "Error reading object from the specified file.");
		}
				
		Logger.getGlobal().info("Returning empty");
		return empty;
	}

	private void initializeFile(RequestSerialObject o) throws SerializationException {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(pc.getBackendFile(ModelClassType.REQUEST)))) {
			Logger.getGlobal().info("Initializing requests file.");
			out.writeObject(o);
		} catch (IOException e) {
			throw new SerializationException(e.getCause(),"Error initializing file.");
		}
	}

	@Override
	public Request getRequest(String tripTitle, String senderEmail, String receiverEmail) throws SerializationException {
		// Gets a specific request from back-end file
		
		List<Request> requests = getAllRequests();
				
		for (Request req : requests) {
			if (req.getTarget().getTitle().equals(tripTitle) && req.getSender().getEmail().equals(senderEmail) && req.getReceiver().getEmail().equals(receiverEmail)) return req;
		}
		return null;
	}

	@Override
	public boolean deleteRequest(Request request) throws SerializationException {
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
			RequestSerialObject o = new RequestSerialObject(requests);
			out.writeObject(o);
			return true;
		} catch (IOException e) {
			throw new SerializationException(e.getCause(), "Error in opening the stream to the file.");
		}
	}

}
