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

public class TripDao {
	
	public static final String STORE_TRIP = "call register_trip(?, ?, ?, ?, ?, ?)";
	public static final String GET_TRIP = "call fetch_trip(?)";
	public static final String GET_TRIPS = "call fetch_trips()";
	public static final String GET_SHARED_TRIPS = "call fetch_shared_trips()";
	public static final String STORE_PARTICIPANT = "call register_trip_participation(?, ?)";
	
	private static TripDao instance = null;
	
	private TripDao() {/*empty*/}
	
	public static TripDao getInstance() {
		if (instance == null) {
			instance = new TripDao();
		}
		return instance;
	}
	
	public boolean saveTrip(Trip t) {
		try (Connection conn = ConnectionManager.getInstance().getConnection();
			CallableStatement stmt = conn.prepareCall(STORE_TRIP)) {
			System.out.println("trip title:"+t.getTitle());
			System.out.println("trip cat1:"+t.getCategory1().toString());
			System.out.println("trip cat2:"+t.getCategory2().toString());
			System.out.println("trip dep:"+new java.sql.Date(t.getDepartureDate().getTime()));
			System.out.println("trip ret:"+new java.sql.Date(t.getReturnDate().getTime()));
			System.out.println("org email:"+t.getOrganizer().getEmail());
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
	
	public Trip getTripByTitle(String tripTitle) {
		Trip trip = new Trip();
		ResultSet rs = null;
		
		try (Connection conn = ConnectionManager.getInstance().getConnection();
				CallableStatement stmt = conn.prepareCall(GET_TRIP, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			stmt.setString(1, tripTitle);
			if (stmt.execute()) rs = stmt.getResultSet();
			if (rs != null) {
				rs.first();
				int price = rs.getInt("price");
				Date dep = rs.getDate("departure");
				Date ret = rs.getDate("return_date");
				TripCategory cat1 = TripCategory.valueOf(rs.getString("category1"));
				TripCategory cat2 = TripCategory.valueOf(rs.getString("category2"));
//				String description = rs.getString("description");
//				int minAge = rs.getInt("min_age");
//				int maxAge = rs.getInt("max_age");
//				int maxPart = rs.getInt("max_participants");
				
				trip.setTitle(tripTitle);
				trip.setPrice(price);
				trip.setCategory1(cat1);
				trip.setCategory2(cat2);
				trip.setDepartureDate(dep);
				trip.setReturnDate(ret);
//				trip.setDescription(description);
//				trip.setMinAge(minAge);
//				trip.setMaxAge(maxAge);
//				trip.setMaxParticipants(maxPart);
			}
			
			return trip;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return trip;
		}
	}
	
	public List<Trip> getTrips() {
		List<Trip> trips = new ArrayList<>();
		ResultSet rs = null;
		
		try (Connection conn = ConnectionManager.getInstance().getConnection();
			CallableStatement stmt = conn.prepareCall(GET_TRIPS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			
			if (stmt.execute()) {
				System.out.println("Statement executed.");
				rs = stmt.getResultSet();
				System.out.println(rs);
			}
			System.out.println("Here");
			if (rs != null) {
				System.out.println("rs is not null.");
				System.out.println(rs.first());
				rs.first();
				do {
					// reading columns
					String title = rs.getString("title");
					System.out.println("title:"+title);
					Date departure = rs.getDate("departure");
					System.out.println("departure:"+departure);
					Date returnDate = rs.getDate("return_date");
					System.out.println("return:"+returnDate);
					TripCategory cat1 = TripCategory.valueOf(rs.getString("category1"));
					System.out.println("category1:"+cat1);
					TripCategory cat2 = TripCategory.valueOf(rs.getString("category2"));
					System.out.println("category2:"+cat2);
					int price = rs.getInt("price");
					
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
			return null;
		}
	}
	
	
	public List<Trip> getSharedTrips() {
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
					String title = rs.getString("title");
					Date departure = rs.getDate("departure");
					Date returnDate = rs.getDate("return_date");
					TripCategory cat1 = TripCategory.valueOf(rs.getString("category1"));
					TripCategory cat2 = TripCategory.valueOf(rs.getString("category2"));
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
			return null;
		}
	}
	
	
	public boolean saveParticipant(String userEmail, String tripTitle) {
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
