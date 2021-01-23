package logic.persistence.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import logic.model.UserStats;
import logic.persistence.ConnectionManager;
import logic.persistence.exceptions.DBConnectionException;

public class UserStatsDao {
	
	private static final String UPDATE_STATS = "call update_user_stats(?, ?, ?, ?)";
	private static final String GET_STATS = "call fetch_user_stats(?)";
	
	private static UserStatsDao instance = null;
	
	private UserStatsDao() {/*empty*/}
	
	public static UserStatsDao getInstance() {
		if (instance == null) {
			instance = new UserStatsDao();
		}
		return instance;
	}

	public boolean updateStats(String userId, int newPoints, Double newOrgRating, Double newTravRating) throws DBConnectionException, SQLException {
		try (Connection conn = ConnectionManager.getInstance().getConnection();
			CallableStatement stmt = conn.prepareCall(UPDATE_STATS)) {
				
			//Parameter binding and conversion
			stmt.setString(1, userId);
			stmt.setInt(2, newPoints);
			stmt.setDouble(3, newOrgRating);
			stmt.setDouble(4, newTravRating);
			return stmt.execute();
		} catch (SQLException e) {
			throw new SQLException("Cannot update user stats on database.", e.getCause());
		}
	}
	
	public UserStats getUserStats(String userEmail) throws DBConnectionException, SQLException {
		ResultSet rs = null;
		UserStats us = null;
		
		try (Connection conn = ConnectionManager.getInstance().getConnection();
				CallableStatement stmt = conn.prepareCall(GET_STATS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			stmt.setString(1, userEmail);
			if (stmt.execute()) rs = stmt.getResultSet();
			
			if (rs != null) {
				rs.first();
				Double orgRating = rs.getDouble("organizer_rating");
				Double travRating = rs.getDouble("traveler_rating");
				
				us = new UserStats();
				us.setOrganizerRating(orgRating);
				us.setTravelerRating(travRating);
			}
			
			return us;
		} catch (SQLException e) {
			throw new SQLException("Cannot get user stats from database.", e.getCause());
		}
	}

}
