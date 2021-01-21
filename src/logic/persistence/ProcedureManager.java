package logic.persistence;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProcedureManager {
	
	public static final String GET_USER = "call login_user(?)";
	public static final String STORE_USER = "call register_user(?, ?, ?, ?, ?)";
	public static final String UPDATE_USER = "call update_user(?, ?, ?, ?, ?)";
	
	public static ResultSet getUserById(CallableStatement stmt, String id) throws SQLException {
		stmt.setString(1, id);
		stmt.execute();
		return stmt.getResultSet();
	}

}
