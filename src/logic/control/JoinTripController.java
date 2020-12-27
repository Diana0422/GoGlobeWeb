package logic.control;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import logic.bean.TripBean;
import logic.model.Trip;
 
public class JoinTripController {
	
	private static JoinTripController INSTANCE = null;
	
	private JoinTripController() {}
	
	public static JoinTripController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new JoinTripController();
		}
		
		return INSTANCE;
	}

	public List<TripBean> searchTrips(String value) {
		System.out.println("Search trips by value started.\n");
		System.out.println("Val: "+ value);
		File f = new File("C:\\Users\\dayli\\git\\GoGlobeWeb\\src\\trips.txt");
		
		List<Trip> trips = null;
		trips = PersistenceController.getInstance().readTripFromFile(f);
		
		/* Convert List<Trip> into List<TripBean>*/
		List<TripBean> list = new ArrayList<>();
		for (int i=0; i<trips.size(); i++) {
			Trip t = trips.get(i);
			TripBean bean = new TripBean();
			bean.setId(t.getId());
			bean.setTitle(t.getTitle());
			bean.setPrice(t.getPrice());
			bean.setCategory1(t.getCategory1().toString());
			bean.setCategory2(t.getCategory2().toString());
			bean.setImg(t.getImg());
			
			// Converting Dates to String
			DateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
			String depDateStr = formatter.format(t.getDepartureDate());
			String retDateStr = formatter.format(t.getReturnDate());
			bean.setDepartureDate(depDateStr);
			bean.setReturnDate(retDateStr);
			bean.setTripLength(t.getTripLength());
			list.add(bean);
		}
		
		return list;
	}
}