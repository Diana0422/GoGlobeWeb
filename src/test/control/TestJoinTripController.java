package test.control;

import logic.bean.TripBean;
import logic.control.JoinTripController;
import logic.control.PersistenceController;
import logic.model.Trip;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

class TestJoinTripController {

	List<TripBean> listBean;
	List<Trip> listTrip;

	@Test
	void testSearchTrips() {
		this.listBean = JoinTripController.getInstance().searchTrips("Japan");
		assertNotEquals(null, listBean);
	}
	
	@Test
	void testSearchTripsCompareTitle() {
		boolean res = true;
		File f = new File("C:\\Users\\dayli\\git\\GoGlobeWeb\\src\\trips.txt");
		this.listTrip = PersistenceController.getInstance().readTripFromFile(f);
		this.listBean = JoinTripController.getInstance().searchTrips("Japan");
		for (int i=0; i<this.listTrip.size(); i++) {
			if (!this.listTrip.get(i).getTitle().equals(this.listBean.get(i).getTitle())) {
				res = false;
			}
		}
		
		assertEquals(true, res);
	}

}
