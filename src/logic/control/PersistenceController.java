package logic.control;

import java.io.File;
import java.util.logging.Logger;
import logic.model.ModelClassType;

public class PersistenceController {
	
    private static final String PROJ_PATH = "user.dir";

	private static PersistenceController instance = null;
	
	private PersistenceController() {}
	
	public static PersistenceController getInstance() {
		if (instance == null) {
			instance = new PersistenceController();
		}
		
		return instance;
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
	

	