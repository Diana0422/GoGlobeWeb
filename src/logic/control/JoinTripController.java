package logic.control;
import java.util.List;
import java.util.logging.Logger;

import logic.bean.TripBean;
import logic.dao.TripDAO;
import logic.dao.TripDAOFile;
import logic.model.Trip;
 
public class JoinTripController {
	
	private static JoinTripController instance = null;
	
	private JoinTripController() {}
	
	public static JoinTripController getInstance() {
		if (instance == null) {
			instance = new JoinTripController();
		}
		
		return instance;
	}

	public List<TripBean> searchTrips(String value) {
		String logStr = "Search trips by value started.\n";
		Logger.getGlobal().info(logStr);
		TripDAO dao = new TripDAOFile();
		List<Trip> trips = dao.getAllTrips();
		
		/* Convert List<Trip> into List<TripBean> */
		List<TripBean> list = ConversionController.getInstance().convertTripList(trips);
		
		logStr = "Trip Beans: "+list;
		Logger.getGlobal().info(logStr);
		return list;
	}
}