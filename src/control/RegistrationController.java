package control;

import java.io.FileNotFoundException;

import bean.UserBean;
import model.User;

public class RegistrationController {
	
	private static RegistrationController INSTANCE = null;
	
	private RegistrationController() {}
	
	public static RegistrationController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new RegistrationController();
		}
		
		return INSTANCE;
	}
	
	public synchronized UserBean register(String name, String surname, int age, String email, String password) throws FileNotFoundException {
		UserBean userBean;
		try {
			userBean = new UserBean(name, surname, age, email, password);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return userBean;
	}
	
	
	public static void main(String args[]) {
		RegistrationController rc = RegistrationController.getInstance();
		
		try {
			rc.register("Diana", "Pasquali", 23, "diana.pasquali@students.uniroma2.eu", "ciao");
//			rc.register("Lorenzo", "Tanzi", 23, "lorenzo.tanzi997@gmail.com", "weee");
//			rc.register("Lisa", "Trombetti", 24, "lisa.trombetti@gmail.com", "helo");
//			rc.register("Andrea", "Molinari", 22, "andrea.molinari98@gmail.com", "aooo");
			
			DBController db = DBController.getInstance();
			User user = db.searchUserByEmail("diana.pasquali@students.uniroma2.eu");
//			User user = db.searchUserByEmail("lorenzo.tanzi997@gmail.com");
//			User user = db.searchUserByEmail("lisa.trombetti@gmail.com");
//			User user = db.searchUserByEmail("andrea.molinari98@gmail.com");
			System.out.println("User is: "+user.getName()+" "+user.getSurname());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.print("File not Found");
		}
	}
}
