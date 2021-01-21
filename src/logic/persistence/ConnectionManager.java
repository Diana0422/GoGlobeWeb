package logic.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    
    public Connection getConnection() throws SQLException {

		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return DriverManager.getConnection(dbUrl, user, pass);
	}
    
    
    public void closeConnection(Connection conn) throws SQLException {
    	if (conn != null) {
    		conn.close();
    	}
    }
    
    
    
}
