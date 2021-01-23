package logic.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import logic.persistence.exceptions.DBConnectionException;

public class ConnectionManager {
	
	private static ConnectionManager instance = null;
	
	private String user = "root";
    private String pass = "rootpass";
    private String dbUrl = "jdbc:mysql://localhost:3306/goglobedb?serverTimezone=Europe/Rome";
    private String driverClassName = "com.mysql.cj.jdbc.Driver";
    
    private ConnectionManager() {/*empty for singleton*/}
    
    public static ConnectionManager getInstance() {
    	if (instance == null) {
    		instance = new ConnectionManager();
    	}
    	
    	return instance;
    }
    
    public Connection getConnection() throws DBConnectionException {

		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			throw new DBConnectionException("Cannot establish connection to database.", e.getCause());
		}
		
		try {
			return DriverManager.getConnection(dbUrl, user, pass);
		} catch (SQLException e) {
			throw new DBConnectionException("Cannot establish connection to database.", e.getCause());
		}
	}
    
    
    public void closeConnection(Connection conn) throws SQLException {
    	if (conn != null) {
    		conn.close();
    	}
    }
    
    
    
}
