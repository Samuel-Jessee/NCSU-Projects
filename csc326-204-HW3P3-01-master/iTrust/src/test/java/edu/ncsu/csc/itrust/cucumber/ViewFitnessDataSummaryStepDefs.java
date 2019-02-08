package edu.ncsu.csc.itrust.cucumber;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust.cucumber.util.ITrustDriver;

/**
 * Step defs for View Data Summary feature
 * 
 * @author Samuel Jessee
 *
 */
public class ViewFitnessDataSummaryStepDefs {

	/** ADDRESS */
	public static final String ADDRESS = "http://localhost:8080/iTrust/";

	/** Default timeout for Selenium webdriver */
	public static final int DEFAULT_TIMEOUT = 2;

	ITrustDriver driver;

	/**
	 * Constructor for step def class
	 * 
	 * @param itrustDriver
	 */
	public ViewFitnessDataSummaryStepDefs(ITrustDriver itrustDriver) {
		this.driver = itrustDriver;
	}

	/**
	 * User clicks View Summary Report button
	 */
	@When("^I click View Summary Report$")
	public void viewSummary() {
		WebElement button = driver.findElement(By.linkText("View Summary Report"));
		button.click();
		Assert.assertTrue(driver.getTitle().equals("iTrust - View Data Summary"));
	}

	/**
	 * User selects data to display
	 * 
	 * @param data
	 *            data to display in graph
	 */
	@When("^select (.+)$")
	public void selectData(String data) {
		Select select;
		select = new Select(driver.findElement(By.name("data")));
		select.selectByValue(data);
	}

	/**
	 * User enters start date
	 * 
	 * @param month
	 * @param day
	 * @param year
	 * @throws InterruptedException
	 */
	@When("^input start date: (.+)/(.+)/(.+)$")
	public void startDate(String month, String day, String year) throws InterruptedException {
		// Fixed by reading this piazza post
		// https://piazza.com/class/iwds9fn98eu2ut?cid=143
		WebElement element = driver.findElement(By.name("StartDateTextBox"));
		Assert.assertNotNull(element);
		element.clear();
		element.sendKeys(month + "/" + day + "/" + year);
		element = driver.findElement(By.name("DateSubmit"));
		Assert.assertNotNull(element);
		element.submit();
	}

	/**
	 * User enters end date
	 * 
	 * @param month
	 * @param day
	 * @param year
	 * @throws InterruptedException
	 */
	@When("^input end date: (.+)/(.+)/(.+)$")
	public void endDate(String month, String day, String year) throws InterruptedException {
		// Fixed by reading this piazza post
		// https://piazza.com/class/iwds9fn98eu2ut?cid=143
		WebElement element = driver.findElement(By.name("StartDateTextBox"));
		Assert.assertNotNull(element);
		element.clear();
		element.sendKeys(month + "/" + day + "/" + year);
		element = driver.findElement(By.name("DateSubmit"));
		Assert.assertNotNull(element);
		element.submit();
	}

	/**
	 * User clicks View Summary to display results
	 */
	@When("^click View Summary$")
	public void createGraph() {
		WebElement button = driver.findElement(By.linkText("View Summary"));
		button.click();
	}

	/**
	 * Checks for graph display
	 */
	@Then("^a graph displaying the data will be displayed$")
	public void checkGraph() {
		WebElement element = driver.findElement(By.name("graph"));
		Assert.assertNotNull(element);
	}

}
