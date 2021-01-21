package logic.persistence.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import logic.model.Request;
import logic.model.Trip;
import logic.model.User;
import logic.model.factories.RequestFactory;
import logic.model.factories.TripFactory;
import logic.model.factories.UserFactory;
import logic.persistence.ConnectionManager;

public class RequestDao {
	
	private static final String GET_REQUEST = "call fetch_request(?, ?)";
	private static final String GET_INC = "call fetch_incoming_requests(?)";
	private static final String GET_SENT = "call fetch_sent_requests(?)";
	private static final String STORE_REQUEST = "call register_request(?, ?)";
	private static final String DELETE_REQUEST = "call delete_request(?, ?)";
	
	private static RequestDao instance = null;
	
	private RequestDao() {/*empty*/}
	
	public static RequestDao getInstance() {
		if (instance == null) {
			instance = new RequestDao();
		}
		return instance;
	}


	public Request getRequest(String senderEmail, String tripTitle) {
		ResultSet rs = null;
		Request r = null;
		try (Connection conn = ConnectionManager.getInstance().getConnection();
			CallableStatement stmt = conn.prepareCall(GET_REQUEST, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {	
			stmt.setString(1, tripTitle);
			stmt.setString(2, senderEmail);
			
			if (stmt.execute()) {
				rs = stmt.getResultSet();
			}
			
			if (rs != null) {
				rs.first();
				int id = rs.getInt("id");
				boolean state = rs.getBoolean("state");
				r = new Request();
				r.setAccepted(state);
				r.setId(id);
			}
			System.out.println("Returning request:"+r);
			return r;
		} catch (SQLException e) {
			//TODO
			return r;
		}
	}
	
	public List<Request> getRequestsByReceiver(String recEmail) {
		List<Request> list = new ArrayList<>();
		ResultSet rs = null;
		
		try (Connection conn = ConnectionManager.getInstance().getConnection();
				CallableStatement stmt = conn.prepareCall(GET_INC, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			stmt.setString(1, recEmail);
			if (stmt.execute()) rs = stmt.getResultSet();
			
			if (rs != null) {
				if (!rs.next()) return list;
				rs.first();
				do {
					boolean state = rs.getBoolean("state");
					String tripTitle = rs.getString("trip_target");
					String sender = rs.getString("user_email");
					String senderName = rs.getString("name");
					String senderSurname = rs.getString("surname");
					Date birth = rs.getDate("birthday");
					
					Request r = RequestFactory.getInstance().createModel();
					User u = UserFactory.getInstance().createModel();
					Trip t = TripFactory.getInstance().createModel();
					t.setTitle(tripTitle);
					u.setName(senderName);
					u.setSurname(senderSurname);
					u.setEmail(sender);
					u.setBirthday(birth);
					r.setSender(u);
					r.setTarget(t);
					r.setAccepted(state);
					list.add(r);
				} while (rs.next());
			}
			
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return list;
		}
	}
	
	
	public List<Request> getRequestsBySender(String sendEmail) {
		List<Request> list = new ArrayList<>();
		ResultSet rs = null;
		
		try (Connection conn = ConnectionManager.getInstance().getConnection();
				CallableStatement stmt = conn.prepareCall(GET_SENT, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			stmt.setString(1, sendEmail);
			if (stmt.execute()) {
				rs = stmt.getResultSet();
			}
			
			if (rs != null) {
				if (!rs.next()) return list;
				rs.first();
				do {
					boolean state = rs.getBoolean("state");
					String tripTitle = rs.getString("trip_target");
					String receiver = rs.getString("organizator");
					String receiverName = rs.getString("name");
					String receiverSurname = rs.getString("surname");
					Date birth = rs.getDate("birthday");
					
					Request r = RequestFactory.getInstance().createModel();
					User u = UserFactory.getInstance().createModel();
					Trip t = TripFactory.getInstance().createModel();
					t.setTitle(tripTitle);
					u.setName(receiverName);
					u.setSurname(receiverSurname);
					u.setEmail(receiver);
					u.setBirthday(birth);
					r.setReceiver(u);
					r.setTarget(t);
					r.setAccepted(state);
					list.add(r);
				} while (rs.next());
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return list;
		}
	}


	public boolean save(Request t) {
		try (Connection conn = ConnectionManager.getInstance().getConnection();
			CallableStatement stmt = conn.prepareCall(STORE_REQUEST)) {	
			stmt.setString(1, t.getSender().getEmail());
			stmt.setString(2, t.getTarget().getTitle());
			stmt.execute();
			return true;
		} catch (SQLException e) {
			//TODO
			return false;
		}
	}

	public boolean delete(Request t) {
		try (Connection conn = ConnectionManager.getInstance().getConnection();
				CallableStatement stmt = conn.prepareCall(DELETE_REQUEST)) {
			stmt.setString(1, t.getTarget().getTitle());
			stmt.setString(2, t.getSender().getEmail());
			stmt.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
