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
import logic.persistence.exceptions.DBConnectionException;

public class RequestDao {
	
	private static final String GET_REQUEST = "call fetch_request(?, ?)";
	private static final String GET_INC = "call fetch_incoming_requests(?)";
	private static final String GET_SENT = "call fetch_sent_requests(?)";
	private static final String STORE_REQUEST = "call register_request(?, ?)";
	private static final String DELETE_REQUEST = "call delete_request(?, ?)";
	private static final String ID_COLUMN = "id";
	private static final String STATE_COLUMN ="state";
	private static final String TRIP_COLUMN = "trip_target";
	private static final String SEND_EMAIL_COLUMN = "user_email";
	private static final String USER_NAME_COLUMN = "name";
	private static final String USER_SURNAME_COLUMN = "surname";
	private static final String USER_BIRTH_COLUMN = "birthday";
	private static final String ORGANIZATOR_COLUMN = "organizator";
	
	private static RequestDao instance = null;
	
	private RequestDao() {/*empty*/}
	
	public static RequestDao getInstance() {
		if (instance == null) {
			instance = new RequestDao();
		}
		return instance;
	}


	public Request getRequest(String senderEmail, String tripTitle) throws DBConnectionException, SQLException {
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
				if (!rs.next()) return r;
				rs.first();
				int id = rs.getInt(ID_COLUMN);
				boolean state = rs.getBoolean(STATE_COLUMN);
				r = new Request();
				r.setAccepted(state);
				r.setId(id);
			}
			return r;
		} catch (SQLException e) {
			throw new SQLException("Cannot get request from user:"+senderEmail+" for trip:"+tripTitle+" from database", new Throwable(e.getMessage()));
		}
	}
	
	public List<Request> getRequestsByReceiver(String recEmail) throws DBConnectionException, SQLException {
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
					boolean state = rs.getBoolean(STATE_COLUMN);
					String tripTitle = rs.getString(TRIP_COLUMN);
					String sender = rs.getString(SEND_EMAIL_COLUMN);
					String senderName = rs.getString(USER_NAME_COLUMN);
					String senderSurname = rs.getString(USER_SURNAME_COLUMN);
					Date birth = rs.getDate(USER_BIRTH_COLUMN);
					
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
			throw new SQLException("Cannot retrieve incoming requests for user:"+recEmail, new Throwable(e.getMessage()));
		}
	}
	
	
	public List<Request> getRequestsBySender(String sendEmail) throws DBConnectionException, SQLException {
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
					boolean state = rs.getBoolean(STATE_COLUMN);
					String tripTitle = rs.getString(TRIP_COLUMN);
					String receiver = rs.getString(ORGANIZATOR_COLUMN);
					String receiverName = rs.getString(USER_NAME_COLUMN);
					String receiverSurname = rs.getString(USER_SURNAME_COLUMN);
					Date birth = rs.getDate(USER_BIRTH_COLUMN);
					
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
			throw new SQLException("Cannot retrieve sent requests for user:"+sendEmail, new Throwable(e.getMessage()));
		}
	}


	public boolean save(Request t) throws DBConnectionException, SQLException {
		try (Connection conn = ConnectionManager.getInstance().getConnection();
			CallableStatement stmt = conn.prepareCall(STORE_REQUEST)) {	
			stmt.setString(1, t.getSender().getEmail());
			stmt.setString(2, t.getTarget().getTitle());
			stmt.execute();
			return true;
		} catch (SQLException e) {
			throw new SQLException("Cannot save request on database.", new Throwable(e.getMessage()));
		}
	}

	public boolean delete(Request t) throws DBConnectionException, SQLException {
		try (Connection conn = ConnectionManager.getInstance().getConnection();
				CallableStatement stmt = conn.prepareCall(DELETE_REQUEST)) {
			stmt.setString(1, t.getTarget().getTitle());
			stmt.setString(2, t.getSender().getEmail());
			stmt.execute();
			return true;
		} catch (SQLException e) {
			throw new SQLException("Cannot delete request on database.", new Throwable(e.getMessage()));
		}
	}

}
