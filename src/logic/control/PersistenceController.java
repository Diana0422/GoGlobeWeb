package logic.control;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;
import logic.model.ModelClassType;

public class PersistenceController {
	
	private static String user = "Diana0422";
    private static String pass = "adminpass";
    private static String dbUrl = "jdbc:mysql://localhost:3306/goglobedb";
    private static String driverClassName = "com.mysql.jdbc.Driver";
    private static final String PROJ_PATH = "user.dir";
	
    private static Connection connection = null;
	private static PersistenceController instance = null;
	
	private PersistenceController() {}
	
	public static PersistenceController getInstance() {
		if (instance == null) {
			instance = new PersistenceController();
		}
		
		return instance;
	}
	
	public static Connection getConnection() {
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(dbUrl, user, pass);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return connection;
	}
	
	public File getBackendFile(ModelClassType type) {
		String projectPath = System.getProperty(PROJ_PATH);
		Logger.getGlobal().info(projectPath);
		
		switch(type) {
			case TRIP:
				return new File(projectPath + "/trips.out");
			case USER:
				return new File(projectPath + "/users.out");
			case REQUEST:
				return new File(projectPath + "/requests.out");
			case REVIEW:
			case PRIZE:
			default:
				break;
		}
		
		// return file not found exception?
		return null;
	}
	
}
	

	