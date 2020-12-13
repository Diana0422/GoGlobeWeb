package logic.control;


public class PlanTripController {
	
private static PlanTripController INSTANCE;
	
	private PlanTripController(){
		
	}
	
	public static synchronized PlanTripController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PlanTripController();
		}
		return INSTANCE;
	}
}
