package edu.ncsu.csc.itrust.cucumber;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import edu.ncsu.csc.itrust.cucumber.util.ITrustDriver;
import edu.ncsu.csc.itrust.cucumber.util.DemographicsPage;
/**
 * Step Defs for Logging in and out of iTrust
 * @author alrichma
 *
 */
public class ProcessingWebStepDefs {
	
	/** ADDRESS */
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	/** Default timeout for Selenium webdriver */
	public static final int DEFAULT_TIMEOUT = 2;
	ITrustDriver driver;
	
	public ProcessingWebStepDefs(ITrustDriver itrustDriver){
		this.driver = itrustDriver;
	}
	
	@Before
	public void before(){
		driver.manage().deleteAllCookies();
	}
	/**
	 * inspired from iTrustSeleniumTest
	 */
	@Given("^user logs in as HCP with MID: (.+) with PASS: (.+)$")
	public void correct_LoginHCP(String MID, String PW){
		driver.get(ADDRESS);
		// log in using the given username and password
		WebElement user = driver.findElement(By.name("j_username"));
		WebElement pass = driver.findElement(By.name("j_password"));
		user.sendKeys(MID);
		pass.sendKeys(PW);
		pass.submit();
		Assert.assertTrue(driver.getTitle().equals("iTrust - HCP Home"));
	}
	
	@Given("^user logs in as Patient with MID: (.+) with PASS: (.+)$")
	public void correct_LoginPatient(String MID, String PW){
		driver.get(ADDRESS);
		// log in using the given username and password
		WebElement user = driver.findElement(By.name("j_username"));
		WebElement pass = driver.findElement(By.name("j_password"));
		user.sendKeys(MID);
		pass.sendKeys(PW);
		pass.submit();
		Assert.assertTrue(driver.getTitle().equals("iTrust - Patient Home"));
	}
	

}
