package logic.control;

import java.util.List;
import java.util.logging.Logger;

import logic.bean.RequestBean;
import logic.bean.SessionBean;
import logic.bean.UserBean;
import logic.persistence.dao.RequestDao;
import logic.persistence.dao.TripDao;
import logic.persistence.dao.UserDaoDB;
import logic.persistence.exceptions.DBConnectionException;
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
	
	
	public UserBean getSenderBean(RequestBean requestBean) throws DBConnectionException {
		User sender = UserDaoDB.getInstance().get(requestBean.getSenderEmail());
		return ConversionController.getInstance().convertToUserBean(sender);
	}
	
	public synchronized boolean acceptRequest(RequestBean requestBean) throws DBConnectionException {
		// get request from persistence
		Request req = RequestDao.getInstance().getRequest(requestBean.getSenderEmail(), requestBean.getTripTitle());
		//Add sender and target to request
		req.setSender(UserDaoDB.getInstance().get(requestBean.getSenderEmail()));
		req.setTarget(TripDao.getInstance().getTripByTitle(requestBean.getTripTitle()));
		
		// Add sender user to trip participants
		req.getTarget().addParticipant(req.getSender());
		String logStr = "Participants: "+req.getTarget().getParticipants();
		Logger.getGlobal().info(logStr);
		
		// Update trip's participant users in persistence
		return TripDao.getInstance().saveParticipant(req.getSender().getEmail(), req.getTarget().getTitle()) && RequestDao.getInstance().delete(req);
	}
	
	public synchronized boolean declineRequest(RequestBean requestBean) throws DBConnectionException {
		// get request from persistence
		Request r = RequestDao.getInstance().getRequest(requestBean.getSenderEmail(), requestBean.getTripTitle());

		// Delete request
		return RequestDao.getInstance().delete(r);
	}
	
	public List<RequestBean> getUserIncomingRequests(SessionBean session) throws DBConnectionException {
		List<Request> incRequests = RequestDao.getInstance().getRequestsByReceiver(session.getSessionEmail());
		return ConversionController.getInstance().convertRequestList(incRequests);
	}
	
	
	public List<RequestBean> getUserSentRequests(SessionBean session) throws DBConnectionException {
		List<Request> sentRequests = RequestDao.getInstance().getRequestsBySender(session.getSessionEmail());
		return ConversionController.getInstance().convertRequestList(sentRequests);
	}
}
