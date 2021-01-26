package logic.control;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import logic.bean.RequestBean;
import logic.bean.SessionBean;
import logic.bean.UserBean;
import logic.persistence.dao.RequestDao;
import logic.persistence.dao.TripDao;
import logic.persistence.dao.UserDaoDB;
import logic.persistence.exceptions.DBConnectionException;
import logic.persistence.exceptions.DatabaseException;
import logic.model.Request;
import logic.model.User;
import logic.model.utils.converters.RequestBeanConverter;

public class ManageRequestController {

	private static ManageRequestController instance = null;
	private static RequestBeanConverter converter = null;
	
	private ManageRequestController() {
		//empty constructor
	}
	
	public static ManageRequestController getInstance() {
		if (instance == null) {
			instance = new ManageRequestController();
			converter = new RequestBeanConverter();
		}
		
		return instance;
	} 
	
	
	public UserBean getSenderBean(RequestBean requestBean) throws DatabaseException {
		User sender;//TODO levare
		try {
			sender = UserDaoDB.getInstance().get(requestBean.getSenderEmail());
			return ConversionController.getInstance().convertToUserBean(sender);
		} catch (DBConnectionException | SQLException e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}
	
	public synchronized boolean acceptRequest(RequestBean requestBean) throws DatabaseException {
		// get request from persistence
		Request req;
		try {
			System.out.println(requestBean.getSenderEmail());
			req = RequestDao.getInstance().getRequest(requestBean.getSenderEmail(), requestBean.getTripTitle());
			System.out.println(req);
			
			// Get request sender and receiver
			System.out.println(requestBean.getSenderEmail());
			System.out.println(req.getTarget());
			User receiver = UserDaoDB.getInstance().getRequestReceiver(requestBean.getSenderEmail(), req.getTarget().getTitle());
			System.out.println(receiver.getIncRequests());
			
			// Add sender user to trip participants
			req.getTarget().addParticipant(req.getSender());
			String logStr = "Participants: "+req.getTarget().getParticipants();
			Logger.getGlobal().info(logStr);
			
			// Update trip's participant users in persistence
			System.out.println(receiver.getIncRequests());
			System.out.println(req.getSender().getSentRequests());
			receiver.deleteIncomingRequest(req);
			req.getSender().deleteSentRequest(req);
			System.out.println(receiver.getIncRequests());
			System.out.println(req.getSender().getSentRequests());
			return TripDao.getInstance().saveParticipant(req.getSender().getEmail(), req.getTarget().getTitle()) && RequestDao.getInstance().delete(req, req.getSender().getEmail());
		} catch (DBConnectionException | SQLException e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}
	
	public synchronized boolean declineRequest(RequestBean requestBean) throws DatabaseException {
		try {
			// get request from persistence
			Request r = RequestDao.getInstance().getRequest(requestBean.getSenderEmail(), requestBean.getTripTitle());
			// Delete request
			return RequestDao.getInstance().delete(r, requestBean.getSenderEmail());
		} catch (DBConnectionException | SQLException e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}
	
	public List<RequestBean> getUserIncomingRequests(SessionBean session) throws DatabaseException {
		List<Request> incRequests;
		try { // Qua serve il sender
			User sessionUser = UserDaoDB.getInstance().get(session.getSessionEmail());
			incRequests = RequestDao.getInstance().getRequestsByReceiver(sessionUser.getEmail());
			return converter.convertToListBean(incRequests);
		} catch (DBConnectionException | SQLException e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}
	
	
	public List<RequestBean> getUserSentRequests(SessionBean session) throws DatabaseException{
		try { // Qua non serve perché l'utente è il sender
			List<Request> sentRequests = RequestDao.getInstance().getRequestsBySender(session.getSessionEmail());
			return converter.convertToListBean(sentRequests);
		} catch (DBConnectionException | SQLException e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}
}
