package logic.persistence.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import logic.model.User;
import logic.persistence.ConnectionManager;
import logic.persistence.exceptions.DBConnectionException;

public class UserDaoDB {
	
	private static final String GET_USER = "call login_user(?)";
	private static final String STORE_USER = "call register_user(?, ?, ?, ?, ?)";
	private static final String UPDATE_USER = "call update_user(?, ?, ?, ?)";
	private static final String DELETE_USER = "call delete_user(?)";
	private static final String GET_ORGANIZER = "call fetch_trip_organizer(?)";
	private static final String GET_PARTICIPANTS = "call fetch_trip_participants(?)";
	private static final String EMAIL_COLUMN = "email";
	private static final String NAME_COLUMN = "name";
	private static final String SURNAME_COLUMN = "surname";
	private static final String PASS_COLUMN = "password";
	private static final String BIRTH_COLUMN = "birthday";
	private static final String BIO_COLUMN = "bio";
	private static final String POINTS_COLUMN = "points";
	
	private static UserDaoDB instance = null;
	
	private UserDaoDB() {/*empty*/}
	
	public static UserDaoDB getInstance() {
		if (instance == null) {
			instance = new UserDaoDB();
		}
		return instance;
	}
	

	public User get(String...params) throws DBConnectionException {
		ResultSet rs = null;
		User u = null;
		
		try (Connection conn = ConnectionManager.getInstance().getConnection();
			CallableStatement stmt = conn.prepareCall(GET_USER, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
			
			stmt.setString(1, params[0]);
			if (stmt.execute()) {
				rs = stmt.getResultSet();
			}
			
			if (rs != null) {
				if (!rs.first()) return null;
				
				String email = rs.getString(EMAIL_COLUMN);
				String name = rs.getString(NAME_COLUMN);
				String surname = rs.getString(SURNAME_COLUMN);
				String password = rs.getNString(PASS_COLUMN);
				Date date = rs.getDate(BIRTH_COLUMN);
				String bio = rs.getString(BIO_COLUMN);
				int points = rs.getInt(POINTS_COLUMN);
			
				u = new User();				
					
				u.setName(name);
				u.setSurname(surname);
				u.setEmail(email);
				u.setPassword(password);
				u.setBirthday(date);
				u.setBio(bio);
				u.setPoints(points);
			}
			return u;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return u;
		}
		
	}


	public boolean save(User t) throws DBConnectionException {
		try (Connection conn = ConnectionManager.getInstance().getConnection();
			  CallableStatement stmt = conn.prepareCall(STORE_USER)) {
			stmt.setString(1, t.getEmail());
			stmt.setString(2, t.getPassword());
			stmt.setString(3, t.getName());
			stmt.setString(4, t.getSurname());
			stmt.setDate(5, new java.sql.Date(t.getBirthday().getTime()));
			stmt.execute();
			return true;
		} catch (SQLException e) {
			//TODO
			return false;
		}
		
	}


	public boolean update(User t, String...params) throws DBConnectionException {
		try (Connection conn = ConnectionManager.getInstance().getConnection();
			CallableStatement stmt = conn.prepareCall(UPDATE_USER)) {
			
			//Parameter binding and conversion
			stmt.setString(1, t.getEmail());
			stmt.setString(2, params[0]);
			stmt.setString(3, params[1]);
			stmt.setString(4, params[2]);
			stmt.execute();
			return true;
		} catch (SQLException e) {
			//TODO
			return false;
		}
		
	}


	public boolean delete(User t) throws DBConnectionException {
		try (Connection conn = ConnectionManager.getInstance().getConnection();
			CallableStatement stmt = conn.prepareCall(DELETE_USER)) {
			stmt.setString(1, t.getEmail());
			stmt.execute();
			return true;
		} catch (SQLException e) {
				//TODO
			return false;
		}
		
	}
	
	public User getTripOrganizer(String tripTitle) throws DBConnectionException {
		ResultSet rs = null;
		User u = null;
		
		try (Connection conn = ConnectionManager.getInstance().getConnection();
				CallableStatement stmt = conn.prepareCall(GET_ORGANIZER, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
			stmt.setString(1, tripTitle);
			if (stmt.execute()) {
				rs = stmt.getResultSet();
			}
			
			if (rs != null) {
				rs.first();
				// Only one row: fetch columns
				String email = rs.getString(EMAIL_COLUMN);
				String pass = rs.getString(PASS_COLUMN);
				String name = rs.getString(NAME_COLUMN);
				String surname = rs.getString(SURNAME_COLUMN);
				Date birthday = rs.getDate(BIRTH_COLUMN);
				String bio = rs.getString(BIO_COLUMN);
				
				u = new User();
				u.setEmail(email);
				u.setBio(bio);
				u.setBirthday(birthday);
				u.setName(name);
				u.setPassword(pass);
				u.setSurname(surname);
			}
			
			return u;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return u;
		}
	}
	
	public List<User> getTripParticipants(String tripTitle) throws DBConnectionException {
		List<User> participants = new ArrayList<>();
		ResultSet rs = null;
		
		try (Connection conn = ConnectionManager.getInstance().getConnection();
				CallableStatement stmt = conn.prepareCall(GET_PARTICIPANTS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			
			stmt.setString(1, tripTitle);
			
			if (stmt.execute()) {
				rs = stmt.getResultSet();
			}
			
			if (rs != null) {
				rs.first();
				do {
					String name = rs.getString(NAME_COLUMN);
					String surname = rs.getString(SURNAME_COLUMN);
					String email = rs.getString(EMAIL_COLUMN);
					String pass = rs.getString(PASS_COLUMN);
					Date birthday = rs.getDate(BIRTH_COLUMN);
					String bio = rs.getString(BIO_COLUMN);
					
					User u = new User();
					u.setBio(bio);
					u.setEmail(email);
					u.setPassword(pass);
					u.setName(name);
					u.setSurname(surname);
					u.setBirthday(birthday);
					participants.add(u);
				} while (rs.next());
			}
			return participants;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return participants;
		}
		
	}

}
