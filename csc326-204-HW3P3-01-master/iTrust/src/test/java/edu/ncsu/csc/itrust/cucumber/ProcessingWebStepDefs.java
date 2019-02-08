package edu.ncsu.csc.itrust.cucumber;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust.cucumber.util.ITrustDriver;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.cucumber.util.DemographicsPage;

/**
 * Step Defs for Logging in and out of iTrust Also for getting into the Fitness
 * Information tab
 * 
 * @author alrichma
 *
 */
public class ProcessingWebStepDefs {

	/** ADDRESS */
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	/** Default timeout for Selenium webdriver */
	public static final int DEFAULT_TIMEOUT = 2;
	private HtmlUnitDriver driver;

	/** gen taken from iTrustSeleniumTest class */
	protected TestDataGenerator gen = new TestDataGenerator();

	/**
	 * Constructor
	 * 
	 * @throws Exception
	 */
	
	@Before
	public void before(){
		driver.manage().deleteAllCookies();
	}
	
	public ProcessingWebStepDefs(ITrustDriver itrustDriver) throws Exception {
		gen.clearAllTables();
		gen.standardData();
		driver = new HtmlUnitDriver();
		driver.get(ADDRESS);
	}

	/**
	 * inspired from iTrustSeleniumTest Logs in with given credentials as HCP
	 */
	@Given("^user logs in as HCP with MID: (.+) with PASS: (.+)$")
	public void correct_LoginHCP(String MID, String PW) {
		driver.get(ADDRESS);
		// log in using the given username and password
		try {
			driver.findElement(By.name("j_username")).clear();
			driver.findElement(By.name("j_username")).sendKeys(MID);

			driver.findElement(By.name("j_password")).clear();
			driver.findElement(By.name("j_password")).sendKeys(PW);
			driver.findElement(By.name("j_password")).submit();
		} catch (NoSuchElementException e) {
			Assert.fail();
		}
		Assert.assertTrue(driver.getTitle().equals("iTrust - HCP Home"));
	}

	/**
	 * Clicks on "View Fitness Health"
	 */
	@Given("^I click Fitness Health Information$")
	public void i_click_Fitness_Health_Information() {
		try {
			driver.findElement(By.linkText("View Fitness Health")).click();
		} catch (NoSuchElementException e) {
			Assert.fail();
		}
		Assert.assertTrue(driver.getTitle().equals("iTrust - View Fitness Health"));
	}

	/**
	 * Enters and clicks on patient
	 * 
	 * @param firstName
	 */
	@Given("^I enter patient (.+)$")
	public void enterFirstName(String firstName) {
		try {
			WebElement text = driver.findElement(By.name("FIRST_NAME"));
			text.sendKeys(firstName);
			text.submit();
			WebElement element = driver.findElement(By.xpath("//td/input[@type='submit'][@value='User Search']"));
			element.submit();
		} catch (NoSuchElementException e) {
			Assert.fail();
		}
	}

}
