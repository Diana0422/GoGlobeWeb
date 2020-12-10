package logic.control;


public class SelectTripPreferencesController {
	
private static SelectTripPreferencesController INSTANCE;
	
	private SelectTripPreferencesController(){
		
	}
	
	public static synchronized SelectTripPreferencesController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SelectTripPreferencesController();
		}
		return INSTANCE;
	}
	
	
	public boolean validateForm(String tripName, String Category1, String Category2, String departureDate, String returnDate){
			
		boolean res = validateDates();
		
		
		return res;
	}
	
	private boolean validateDates() {
			
		return true;
	}
	
}
