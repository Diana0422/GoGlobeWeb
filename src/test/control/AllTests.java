package test.control;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	TestJoinTripController.class,
	TestRegistrationController.class
})

public class AllTests {

}
