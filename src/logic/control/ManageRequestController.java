package logic.control;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import logic.bean.RequestBean;
import logic.bean.SessionBean;
import logic.bean.UserBean;
import logic.dao.RequestDAO;
import logic.dao.RequestDAOFile;
import logic.dao.TripDAO;
import logic.dao.TripDAOFile;
import logic.dao.UserDAO;
import logic.dao.UserDAOFile;
import logic.model.Request;
import logic.model.User;

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
	
	
	public UserBean getSenderBean(RequestBean requestBean) {
		UserDAO userDao = new UserDAOFile();
		User sender = userDao.getUser(requestBean.getSenderEmail());
		return ConversionController.getInstance().convertToUserBean(sender);
	}
	
	public synchronized void acceptRequest(RequestBean requestBean) {
		// get request from persistence
		RequestDAO reqDao = new RequestDAOFile();
		Request req = reqDao.getRequest(requestBean.getTripTitle(), requestBean.getSenderEmail(), requestBean.getReceiverEmail());
		
		System.out.println("Participants: "+req.getTarget().getParticipants());
		
		// Add sender user to trip participants
		req.getTarget().addParticipant(req.getSender());
		String logStr = "Participants: "+req.getTarget().getParticipants();
		Logger.getGlobal().info(logStr);
		TripDAO tripDao = new TripDAOFile();
		
		// Update trip in persistence
		tripDao.updateTrip(req.getTarget(), req.getTarget().getTitle()); 
		
		// Delete request
		reqDao.deleteRequest(req);
	}
	
	public synchronized void declineRequest(RequestBean requestBean) {
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
