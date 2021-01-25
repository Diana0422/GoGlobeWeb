package test.selenium;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import logic.persistence.dao.TripDao;
import logic.persistence.exceptions.DBConnectionException;

public class TestSearchTripSelenium {
	
	/* Diana Pasquali Test Class */
	private static final String TRIP_TITLE = "TestViaggio";

	@Test
	void testSearchTripSeleniumTitle() {
		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:8080/ISPWProject20-21/view/joinTrip.jsp");
		
		// Find search bar element and search trip
		driver.findElement(By.xpath("//*[@id=\"bar\"]")).sendKeys(TRIP_TITLE);
		driver.findElement(By.xpath("//*[@id=\"search-btn\"]")).click();
		
		// Read cards?
		WebElement titleResult = driver.findElement(By.xpath("//*[@id=\"title\"]"));
		String title = titleResult.getText();
		Logger.getGlobal().log(Level.INFO, title);
		assertEquals(TRIP_TITLE, title);
		driver.close();
	}
	
	@Test
	void testSearchTripSeleniumPrice() {
		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:8080/ISPWProject20-21/view/joinTrip.jsp");
		
		// Find search bar element and search trip
		driver.findElement(By.xpath("//*[@id=\"bar\"]")).sendKeys(TRIP_TITLE);
		driver.findElement(By.xpath("//*[@id=\"search-btn\"]")).click();
		
		WebElement priceResult = driver.findElement(By.xpath("//*[@id=\"bootstrap-override\"]/div[2]/form/div/div/div/div/div/div/h5[2]"));
		String priceStr = priceResult.getText();
		Logger.getGlobal().log(Level.INFO, priceStr);
		int price = Integer.parseInt(priceStr);
		try {
			int expected = TripDao.getInstance().getTripByTitle(TRIP_TITLE).getPrice();
			assertEquals(expected, price);
			driver.close();
		} catch (DBConnectionException | SQLException e) {
			Logger.getGlobal().log(Level.SEVERE, "EXCEPTION IN TEST SEARCH TRIP PRICE: "+TRIP_TITLE+" NOT FOUND IN DATABASE.");
		}
	}

}
