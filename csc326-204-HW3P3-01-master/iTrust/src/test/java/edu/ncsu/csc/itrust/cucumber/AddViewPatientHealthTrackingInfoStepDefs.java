package edu.ncsu.csc.itrust.cucumber;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust.cucumber.util.FitnessMainPage;
import edu.ncsu.csc.itrust.cucumber.util.ITrustDriver;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;

public class AddViewPatientHealthTrackingInfoStepDefs {
	
	/** ADDRESS */
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	/** Default timeout for Selenium webdriver */
	public static final int DEFAULT_TIMEOUT = 2;
	
	FitnessMainPage fitnessMain;
	
	private ITrustDriver driver;
	private String month = "";
	private String day = "";
	private String year = "";
	private String calBurned = "1";
	private String steps = "2";
	private String distance = "3";
	private String floors = "4";
	private String minSed = "5";
	private String minLight = "6";
	private String minFair = "7";
	private String minVery = "8";
	private String actCal = "9";

	Connection conn = null;
	PreparedStatement stmt = null;
	
	/** gen taken from iTrustSeleniumTest class */
	protected TestDataGenerator gen = new TestDataGenerator();
	
	public AddViewPatientHealthTrackingInfoStepDefs(ITrustDriver itrustDriver) throws Exception {
		gen.clearAllTables();
		gen.standardData();
		driver = itrustDriver;
		fitnessMain = new FitnessMainPage();
	}
	
	/**
	 * Checks that it is on right page and then proceeds to insert date and
	 * submit
	 * 
	 * @throws InterruptedException
	 */
	@When("^input (.+)/(.+)/(.+) date$")
	public void inputDate(String month, String day, String year) throws InterruptedException {
		this.month = month;
		this.day = day;
		this.year = year;
		// Fixed by reading this piazza post
		// https://piazza.com/class/iwds9fn98eu2ut?cid=143
		Assert.assertEquals("iTrust - Main Patient Health Tracking Information Page", driver.getTitle());
		fitnessMain.pickDate(month, day, year);
	}

	/**
	 * Adds health tracking data
	 * 
	 * @throws InterruptedException
	 */
	@When("^I can add (.+) data on the patient$")
	public void addData(String state) throws InterruptedException {
		Assert.assertEquals("iTrust - Edit Patient Heath Tracking Information", driver.getTitle());
		Assert.assertEquals("Date: " + month + "/" + day + "/" + year, driver.findElement(By.name("DateLabel")));

		if (state != "valid") {
			minSed = "asdf";
		}
		WebElement element = driver.findElement(By.name("CalBurned"));
		Assert.assertNotNull(element);
		element.clear();
		element.sendKeys(calBurned);

		element = driver.findElement(By.name("Steps"));
		Assert.assertNotNull(element);
		element.clear();
		element.sendKeys(steps);

		element = driver.findElement(By.name("Distance"));
		Assert.assertNotNull(element);
		element.clear();
		element.sendKeys(distance);

		element = driver.findElement(By.name("Floors"));
		Assert.assertNotNull(element);
		element.clear();
		element.sendKeys(floors);

		element = driver.findElement(By.name("MinSed"));
		Assert.assertNotNull(element);
		element.clear();
		element.sendKeys(minSed);

		element = driver.findElement(By.name("MinLight"));
		Assert.assertNotNull(element);
		element.clear();
		element.sendKeys(minLight);

		element = driver.findElement(By.name("MinFair"));
		Assert.assertNotNull(element);
		element.clear();
		element.sendKeys(minFair);

		element = driver.findElement(By.name("MinVery"));
		Assert.assertNotNull(element);
		element.clear();
		element.sendKeys(minVery);

		element = driver.findElement(By.name("ActCal"));
		Assert.assertNotNull(element);
		element.clear();
		element.sendKeys(actCal);
	}
	

	/**
	 * Checks to see if user is able to view the correct patient's basic health info.
	 */
	@Then("^I can view the adjusted data on the patient and see message: (.+)$")
	public void checkCanViewBasicHealthinfo(String message) {
		Assert.assertEquals("iTrust - View Patient Heath Tracking Information", driver.getTitle());
		Assert.assertEquals("Date: " + month + "/" + day + "/" + year, driver.findElement(By.name("DateLabel")));

		WebElement element = driver.findElement(By.name("StateMessage"));
		Assert.assertNotNull(element);
		Assert.assertEquals("message", element.getAttribute("value"));
		
		element = driver.findElement(By.name("CalBurned"));
		Assert.assertNotNull(element);
		Assert.assertEquals(calBurned, element.getAttribute("value"));
		
		element = driver.findElement(By.name("Steps"));
		Assert.assertNotNull(element);
		Assert.assertEquals(steps, element.getAttribute("value"));

		element = driver.findElement(By.name("Distance"));
		Assert.assertNotNull(element);
		Assert.assertEquals(distance, element.getAttribute("value"));
		
		element = driver.findElement(By.name("Floors"));
		Assert.assertNotNull(element);
		Assert.assertEquals(floors, element.getAttribute("value"));
		
		element = driver.findElement(By.name("MinSed"));
		Assert.assertNotNull(element);
		Assert.assertEquals(minSed, element.getAttribute("value"));
		
		element = driver.findElement(By.name("MinLight"));
		Assert.assertNotNull(element);
		Assert.assertEquals(minLight, element.getAttribute("value"));
		
		element = driver.findElement(By.name("MinFair"));
		Assert.assertNotNull(element);
		Assert.assertEquals(minFair, element.getAttribute("value"));
		
		element = driver.findElement(By.name("MinVery"));
		Assert.assertNotNull(element);
		Assert.assertEquals(minVery, element.getAttribute("value"));
		
		element = driver.findElement(By.name("ActCal"));
		Assert.assertNotNull(element);
		Assert.assertEquals(actCal, element.getAttribute("value"));
	}
}