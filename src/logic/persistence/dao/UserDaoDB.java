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

public class UserDaoDB {
	
	private static final String GET_USER = "call login_user(?)";
	private static final String STORE_USER = "call register_user(?, ?, ?, ?, ?)";
	private static final String UPDATE_USER = "call update_user(?, ?, ?, ?)";
	private static final String DELETE_USER = "call delete_user(?)";
	private static final String GET_ORGANIZER = "call fetch_trip_organizer(?)";
	private static final String GET_PARTICIPANTS = "call fetch_trip_participants(?)";
	
	private static UserDaoDB instance = null;
	
	private UserDaoDB() {/*empty*/}
	
	public static UserDaoDB getInstance() {
		if (instance == null) {
			instance = new UserDaoDB();
		}
		return instance;
	}
	

	public User get(String...params) {
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
				
				String email = rs.getString("email");
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				String password = rs.getNString("password");
				Date date = rs.getDate("birthday");
				String bio = rs.getString("bio");
				int points = rs.getInt("points");
			
				u = new User();				
					
				u.setName(name);
				u.setSurname(surname);
				u.setEmail(email);
				u.setPassword(password);
				u.setBirthday(date);
				u.setBio(bio);
				u.setPoints(points);
			}

			System.out.println(u.getName()+" "+u.getSurname()+" "+u.getEmail()+" "+u.getPassword()+" "+u.getPoints()+" "+u.getBirthday()+" "+u.getBio());
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return u;
		
	}


	public boolean save(User t) {
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


	public boolean update(User t, String...params) {
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


	public boolean delete(User t) {
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
	
	public User getTripOrganizer(String tripTitle) {
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
				String email = rs.getString("email");
				String pass = rs.getString("password");
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				Date birthday = rs.getDate("birthday");
				String bio = rs.getString("bio");
				
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
			return null;
		}
	}
	
	public List<User> getTripParticipants(String tripTitle) {
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
					String name = rs.getString("name");
					String surname = rs.getString("surname");
					String email = rs.getString("email");
					String pass = rs.getString("password");
					Date birthday = rs.getDate("birthday");
					String bio = rs.getString("bio");
					
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
