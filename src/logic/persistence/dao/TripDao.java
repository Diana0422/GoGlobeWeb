package logic.persistence.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.model.Trip;
import logic.model.TripCategory;
import logic.persistence.ConnectionManager;
import logic.persistence.exceptions.DBConnectionException;

public class TripDao {
	
	public static final String STORE_TRIP = "call register_trip(?, ?, ?, ?, ?, ?)";
	public static final String GET_TRIP = "call fetch_trip(?)";
	public static final String GET_TRIPS = "call fetch_trips()";
	public static final String GET_SHARED_TRIPS = "call fetch_shared_trips()";
	public static final String STORE_PARTICIPANT = "call register_trip_participation(?, ?)";
	public static final String TITLE_COLUMN = "title";
	public static final String PRICE_COLUMN = "price";
	public static final String DEPARTURE_COLUMN = "departure";
	public static final String RETURN_COLUMN = "return_date";
	public static final String CATEGORY1_COLUMN = "category1";
	public static final String CATEGORY2_COLUMN = "category2";
	
	
	private static TripDao instance = null;
	
	private TripDao() {/*empty*/}
	
	public static TripDao getInstance() {
		if (instance == null) {
			instance = new TripDao();
		}
		return instance;
	}
	
	public boolean saveTrip(Trip t) throws DBConnectionException {
		try (Connection conn = ConnectionManager.getInstance().getConnection();
			CallableStatement stmt = conn.prepareCall(STORE_TRIP)) {
			stmt.setString(1, t.getTitle());
			stmt.setString(2, t.getCategory1().toString());
			stmt.setString(3, t.getCategory2().toString());
			stmt.setDate(4, new java.sql.Date(t.getDepartureDate().getTime()));
			stmt.setDate(5, new java.sql.Date(t.getReturnDate().getTime()));
			stmt.setString(6, t.getOrganizer().getEmail());
			stmt.execute();
			return true;
		} catch (SQLException e) {
			//TODO
			return false;
		}
	}
	
	public Trip getTripByTitle(String tripTitle) throws DBConnectionException {
		Trip trip = new Trip();
		ResultSet rs = null;
		
		try (Connection conn = ConnectionManager.getInstance().getConnection();
				CallableStatement stmt = conn.prepareCall(GET_TRIP, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			stmt.setString(1, tripTitle);
			if (stmt.execute()) rs = stmt.getResultSet();
			if (rs != null) {
				rs.first();
				int price = rs.getInt(PRICE_COLUMN);
				Date dep = rs.getDate(DEPARTURE_COLUMN);
				Date ret = rs.getDate(RETURN_COLUMN);
				TripCategory cat1 = TripCategory.valueOf(rs.getString(CATEGORY1_COLUMN));
				TripCategory cat2 = TripCategory.valueOf(rs.getString(CATEGORY2_COLUMN));
				trip.setTitle(tripTitle);
				trip.setPrice(price);
				trip.setCategory1(cat1);
				trip.setCategory2(cat2);
				trip.setDepartureDate(dep);
				trip.setReturnDate(ret);
			}
			
			return trip;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return trip;
		}
	}
	
	public List<Trip> getTrips() throws DBConnectionException {
		List<Trip> trips = new ArrayList<>();
		ResultSet rs = null;
		
		try (Connection conn = ConnectionManager.getInstance().getConnection();
			CallableStatement stmt = conn.prepareCall(GET_TRIPS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			
			if (stmt.execute()) {
				rs = stmt.getResultSet();
			}
			if (rs != null) {
				rs.first();
				do {
					// reading columns
					String title = rs.getString(TITLE_COLUMN);
					Date departure = rs.getDate(DEPARTURE_COLUMN);
					Date returnDate = rs.getDate(RETURN_COLUMN);
					TripCategory cat1 = TripCategory.valueOf(rs.getString(CATEGORY1_COLUMN));
					TripCategory cat2 = TripCategory.valueOf(rs.getString(CATEGORY2_COLUMN));
					int price = rs.getInt(PRICE_COLUMN);
					
					// Instantiate new trip
					Trip t = new Trip();
					
					t.setTitle(title);
					t.setCategory1(cat1);
					t.setCategory2(cat2);
					t.setDepartureDate(departure);
					t.setReturnDate(returnDate);
					t.setShared(false);
					t.setPrice(price);
					trips.add(t);
				} while(rs.next());
			}
			
			return trips;
		} catch (SQLException e) {
			//TODO
			return trips;
		}
	}
	
	
	public List<Trip> getSharedTrips() throws DBConnectionException {
		List<Trip> trips = new ArrayList<>();
		ResultSet rs = null;
		
		try (Connection conn = ConnectionManager.getInstance().getConnection();
			CallableStatement stmt = conn.prepareCall(GET_SHARED_TRIPS)) {
			
			if (stmt.execute()) {
				rs = stmt.getResultSet();
			}
			
			if (rs != null) {
				rs.first();
				do {
					// reading columns
					String title = rs.getString(TITLE_COLUMN);
					Date departure = rs.getDate(DEPARTURE_COLUMN);
					Date returnDate = rs.getDate(RETURN_COLUMN);
					TripCategory cat1 = TripCategory.valueOf(rs.getString(CATEGORY1_COLUMN));
					TripCategory cat2 = TripCategory.valueOf(rs.getString(CATEGORY2_COLUMN));
					String desc = rs.getString("description");
					int minAge = rs.getInt("min_age");
					int maxAge = rs.getInt("max_age");
					int maxParticipants = rs.getInt("max_participants");
					
					// Instantiate new trip
					Trip t = new Trip();
					
					t.setTitle(title);
					t.setCategory1(cat1);
					t.setCategory2(cat2);
					t.setDepartureDate(departure);
					t.setReturnDate(returnDate);
					t.setMaxParticipants(maxParticipants);
					t.setMaxAge(maxAge);
					t.setMinAge(minAge);
					t.setDescription(desc);
					t.setShared(true);
					
					trips.add(t);
				} while(rs.next());
			}
			
			return trips;
		} catch (SQLException e) {
			//TODO
			return trips;
		}
	}
	
	
	public boolean saveParticipant(String userEmail, String tripTitle) throws DBConnectionException {
		try (Connection conn = ConnectionManager.getInstance().getConnection();
				CallableStatement stmt = conn.prepareCall(STORE_PARTICIPANT)) {
			stmt.setString(1, tripTitle);
			stmt.setString(2, userEmail);
			stmt.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
}
