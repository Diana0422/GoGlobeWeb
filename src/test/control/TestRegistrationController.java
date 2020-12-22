package test.control;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import logic.control.PersistenceController;
import logic.control.RegistrationController;

class TestRegistrationController {

	/*Test case for a valid registration of a new user */
	@Test
	void testRegisterValid() {
		// New test user
		boolean result;
		result = RegistrationController.getInstance().register("test", "test", "test", "test", "11/11/1111");
		assertEquals(true, result);
	}
	
	/* Test case for an invalid registration of an already registered user */
	@Test
	void testRegisterInvalid() {
		// Already registered test user
		boolean result;
		result = RegistrationController.getInstance().register("test", "test", "test", "test", "11/11/1111");
		// Re-initialize the back-end file
		PersistenceController.initializeFile("C:\\Users\\dayli\\git\\GoGlobeWeb\\src\\users.txt", "user");
		assertEquals(false, result);
	}
	
	/* Test case for an invalid registration with date parsing error */
	@Test
	void testRegisterParseException() {
		// Already registered test user
		boolean result;
		result = RegistrationController.getInstance().register("newtest", "test", "test", "test", "test");
		// Re-initialize the back-end file
		PersistenceController.initializeFile("C:\\Users\\dayli\\git\\GoGlobeWeb\\src\\users.txt", "user");
		assertEquals(false, result);
	}

}
