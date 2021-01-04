package logic.control;

import java.util.ArrayList;
import java.util.List;

import logic.bean.RequestBean;
import logic.bean.SessionBean;
import logic.dao.RequestDAO;
import logic.dao.RequestDAOFile;
import logic.dao.TripDAO;
import logic.dao.TripDAOFile;
import logic.model.Request;
import logic.model.Trip;

public class ManageRequestController {

	private static ManageRequestController instance = null;
	
	private ManageRequestController() {
		//empty constructor
	}
	
	public static ManageRequestController getInstance() {
		if (instance == null) {
			instance = new ManageRequestController();
		}
		
		return instance;
	} 
	
	public void acceptRequest(RequestBean requestBean) {
		// get request from persistence
		RequestDAO reqDao = new RequestDAOFile();
		Request req = reqDao.getRequest(requestBean.getTripTitle(), requestBean.getSenderEmail(), requestBean.getReceiverEmail());
		Trip oldTrip = req.getTarget();
		
		// Add sender user to trip participants
		req.getTarget().addParticipant(req.getSender());
		TripDAO tripDao = new TripDAOFile();
		
		// Update trip in persistence
		tripDao.updateTrip(req.getTarget(), oldTrip); 
		
		// Delete request
		reqDao.deleteRequest(req);
	}
	
	public void declineRequest(RequestBean requestBean) {
		// get request from persistence
		RequestDAO reqDao = new RequestDAOFile();
		Request req = reqDao.getRequest(requestBean.getTripTitle(), requestBean.getSenderEmail(), requestBean.getReceiverEmail());
				
		// Delete request
		reqDao.deleteRequest(req);
	}
	
	public List<RequestBean> getUserIncomingRequests(SessionBean session) {
		RequestDAO reqDao = new RequestDAOFile();
		List<Request> requests = reqDao.getAllRequests();
		List<Request> incRequests = new ArrayList<>();
		
		for (Request req: requests) {
			if (req.getReceiver().getEmail().equals(session.getEmail())) incRequests.add(req);
		}
		
		return ConversionController.getInstance().convertRequestList(incRequests);
	}
	
	
	public List<RequestBean> getUserSentRequests(SessionBean session) {
		RequestDAO reqDao = new RequestDAOFile();
		List<Request> requests = reqDao.getAllRequests();
		List<Request> sentRequests = new ArrayList<>();
		
		for (Request req: requests) {
			if (req.getSender().getEmail().equals(session.getEmail())) sentRequests.add(req);
		}
		
		return ConversionController.getInstance().convertRequestList(sentRequests);
	}
}
