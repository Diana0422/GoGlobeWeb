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
	
	public static final String STORE_TRIP = "call register_trip(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String GET_TRIP = "call fetch_trip(?)";
	public static final String GET_TRIPS = "call fetch_trips()";
	public static final String GET_SHARED_TRIPS = "call fetch_shared_trips()";
	public static final String GET_FOR_CATEGORY = "call fetch_trips_category(?)";
	public static final String STORE_PARTICIPANT = "call register_trip_participation(?, ?)";
	public static final String TITLE_COLUMN = "title";
	public static final String PRICE_COLUMN = "price";
	public static final String DEPARTURE_COLUMN = "departure";
	public static final String RETURN_COLUMN = "return_date";
	public static final String CATEGORY1_COLUMN = "category1";
	public static final String CATEGORY2_COLUMN = "category2";
	public static final String ORG_COLUMN = "organizator";
	public static final String MAX_AGE_COLUMN = "max_age";
	public static final String MIN_AGE_COLUMN = "min_age";
	public static final String MAX_PART_COLUMN = "max_participants";
	public static final String DESC_COLUMN = "description";
	
	
	private static TripDao instance = null;
	
	private TripDao() {/*empty*/}
	
	public static TripDao getInstance() {
		if (instance == null) {
			instance = new TripDao();
		}
		return instance;
	}
	
	public boolean saveTrip(Trip t, boolean shared) throws DBConnectionException, SQLException {
		try (Connection conn = ConnectionManager.getInstance().getConnection();
			CallableStatement stmt = conn.prepareCall(STORE_TRIP)) {
			stmt.setBoolean(1, shared);
			stmt.setString(2, t.getTitle());
			stmt.setString(3, t.getCategory1().toString());
			stmt.setString(4, t.getCategory2().toString());
			stmt.setDate(5, new java.sql.Date(t.getDepartureDate().getTime()));
			stmt.setDate(6, new java.sql.Date(t.getReturnDate().getTime()));
			stmt.setString(7, t.getOrganizer().getEmail());
			stmt.setString(8, t.getDescription());
			stmt.setInt(9, t.getMinAge());
			stmt.setInt(10, t.getMaxAge());
			stmt.setInt(11, t.getMaxParticipants());
			stmt.execute();
			return true;
		} catch (SQLException e) {
			throw new SQLException("Cannot save trip on database.", e);
		}
	}
	
	public Trip getTripByTitle(String tripTitle) throws DBConnectionException, SQLException {
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
				String organizer = rs.getString(ORG_COLUMN);
				String tripDesc = rs.getString(DESC_COLUMN);
				int minAge = rs.getInt(MIN_AGE_COLUMN);
				int maxAge = rs.getInt(MAX_AGE_COLUMN);
				int maxPart = rs.getInt(MAX_PART_COLUMN);
				trip.setTitle(tripTitle);
				trip.setPrice(price);
				trip.setCategory1(cat1);
				trip.setCategory2(cat2);
				trip.setDepartureDate(dep);
				trip.setReturnDate(ret);
				trip.setDescription(tripDesc);
				trip.setMaxAge(maxAge);
				trip.setMaxParticipants(maxPart);
				trip.setMinAge(minAge);
				trip.setOrganizer(UserDaoDB.getInstance().get(organizer));
			}
			
			return trip;
		} catch (SQLException e) {
			throw new SQLException("Cannot get trip with title:"+tripTitle+" from database.", e);
		}
	}
	
	public List<Trip> getTrips() throws DBConnectionException, SQLException {
		List<Trip> trips = new ArrayList<>();
		ResultSet rs = null;
		
		try (Connection conn = ConnectionManager.getInstance().getConnection();
			CallableStatement stmt = conn.prepareCall(GET_TRIPS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			
			if (stmt.execute()) {
				rs = stmt.getResultSet();
			}
			if (rs != null) {
				if (!rs.next()) return trips;
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
			throw new SQLException("Cannot get not shared trips from database.", e);
		}
	}
	
	
	public List<Trip> getSharedTrips() throws DBConnectionException, SQLException {
		List<Trip> trips = new ArrayList<>();
		ResultSet rs = null;
		
		try (Connection conn = ConnectionManager.getInstance().getConnection();
			CallableStatement stmt = conn.prepareCall(GET_SHARED_TRIPS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			
			if (stmt.execute()) {
				rs = stmt.getResultSet();
			}
			
			if (rs != null) {
				if (!rs.next()) return trips;
				rs.first();
				do {
					// reading columns
					String title = rs.getString(TITLE_COLUMN);
					Date departure = rs.getDate(DEPARTURE_COLUMN);
					Date returnDate = rs.getDate(RETURN_COLUMN);
					TripCategory cat1 = TripCategory.valueOf(rs.getString(CATEGORY1_COLUMN));
					TripCategory cat2 = TripCategory.valueOf(rs.getString(CATEGORY2_COLUMN));
					int price = rs.getInt(PRICE_COLUMN);
					String desc = rs.getString(DESC_COLUMN);
					int minAge = rs.getInt(MIN_AGE_COLUMN);
					int maxAge = rs.getInt(MAX_AGE_COLUMN);
					int maxParticipants = rs.getInt(MAX_PART_COLUMN);
					
					// Instantiate new trip
					Trip t = new Trip();
					
					t.setTitle(title);
					t.setCategory1(cat1);
					t.setCategory2(cat2);
					t.setDepartureDate(departure);
					t.setReturnDate(returnDate);
					t.setPrice(price);
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
			throw new SQLException("Cannot get shared trips from database.", e);
		}
	}
	
	
	public boolean saveParticipant(String userEmail, String tripTitle) throws DBConnectionException, SQLException {
		try (Connection conn = ConnectionManager.getInstance().getConnection();
				CallableStatement stmt = conn.prepareCall(STORE_PARTICIPANT)) {
			stmt.setString(1, tripTitle);
			stmt.setString(2, userEmail);
			stmt.execute();
			return true;
		} catch (SQLException e) {
			throw new SQLException("Cannot save participant for the trip:"+tripTitle+" on database.", e);
		}
	}

	public List<Trip> getTripsForCategory(TripCategory favourite) throws SQLException, DBConnectionException {
		ResultSet rs = null;
		List<Trip> trips = new ArrayList<>();
		try (Connection conn = ConnectionManager.getInstance().getConnection();
				CallableStatement stmt = conn.prepareCall(GET_FOR_CATEGORY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			stmt.setString(1, favourite.toString());
			if (stmt.execute()) {
				rs = stmt.getResultSet();
			}
			
			
			if (rs != null) {
				if (!rs.first()) return trips;
				do {
					// reading columns
					String title = rs.getString(TITLE_COLUMN);
					Date departure = rs.getDate(DEPARTURE_COLUMN);
					Date returnDate = rs.getDate(RETURN_COLUMN);
					TripCategory cat1 = TripCategory.valueOf(rs.getString(CATEGORY1_COLUMN));
					TripCategory cat2 = TripCategory.valueOf(rs.getString(CATEGORY2_COLUMN));
					int price = rs.getInt(PRICE_COLUMN);
					String desc = rs.getString(DESC_COLUMN);
					int minAge = rs.getInt(MIN_AGE_COLUMN);
					int maxAge = rs.getInt(MAX_AGE_COLUMN);
					int maxParticipants = rs.getInt(MAX_PART_COLUMN);
					
					// Instantiate new trip
					Trip t = new Trip();
					
					t.setTitle(title);
					t.setCategory1(cat1);
					t.setCategory2(cat2);
					t.setDepartureDate(departure);
					t.setReturnDate(returnDate);
					t.setPrice(price);
					t.setMaxParticipants(maxParticipants);
					t.setMaxAge(maxAge);
					t.setMinAge(minAge);
					t.setDescription(desc);
					t.setShared(true);
					
					trips.add(t);
				} while (rs.next());
			}
			return trips;
		} catch (SQLException e) {
			throw new SQLException("Cannot get trips for category: "+favourite+" from database.", e);
		}
	}
	
}
