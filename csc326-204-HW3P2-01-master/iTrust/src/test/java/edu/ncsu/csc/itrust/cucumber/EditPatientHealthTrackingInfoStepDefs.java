package edu.ncsu.csc.itrust.cucumber;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;

/**
 * This file runs in conjunction with the Cucumber file
 * ManageMessageInbox.feature to test read messages in the Message Inbox
 * 
 * @author Louis Le
 *
 */
public class EditPatientHealthTrackingInfoStepDefs {

	// Class instances
	private HtmlUnitDriver driver;
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

	/** gen taken from iTrustSeleniumTest class */
	protected TestDataGenerator gen = new TestDataGenerator();

	/**
	 * Constructor sets up test
	 * 
	 * @throws Exception
	 */
	public EditPatientHealthTrackingInfoStepDefs() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		driver = new HtmlUnitDriver();
		driver.get("http://localhost:8080/iTrust/");
	}

	/**
	 * Navigates to the Patient health information page
	 */
	@When("^view to Patient Health Information$")
	public void goToBasicHealthInformation() {
		WebElement t = driver.findElement(By.linkText("Patient Health Tracking Information"));
		Assert.assertNotNull(t);
		t.click();
		Assert.assertEquals("iTrust - Please Select a Patient", driver.getTitle());
	}

	/**
	 * Searches given MID
	 * @param firstName First Name of patient
	 * @param lastName Last Name of patients
	 */
	@When("^search with: First Name: (.+) Last Name: (.+)$")
	public void searchMID(String firstName, String lastName) {
		Assert.assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		driver.findElement(By.name("FIRST_NAME")).clear();
		driver.findElement(By.name("FIRST_NAME")).sendKeys(firstName);

		driver.findElement(By.name("LAST_NAME")).clear();
		driver.findElement(By.name("LAST_NAME")).sendKeys(lastName);

		WebElement element = driver.findElement(By.xpath("//td/input[@type='submit'][@value='User Search']"));
		element.submit();
	}

	/**
	 * Clicks on the button of the first patient result
	 * 
	 * @throws InterruptedException
	 */
	@When("^click on first patient$")
	public void clickFirstPatient() throws InterruptedException {
		// Fixed by reading this piazza post
		// https://piazza.com/class/iwds9fn98eu2ut?cid=143
		Assert.assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		WebElement element = driver.findElement(By.xpath("//input[@width='100%']"));
		element.submit();
	}

	/**
	 * Clicks on the edit button
	 * 
	 * @throws InterruptedException
	 */
	@When("^click on edit$")
	public void clickEdit() throws InterruptedException {
		Assert.assertEquals("iTrust - View Patient Heath Tracking Information", driver.getTitle());
		Assert.assertEquals("Date: " + month + "/" + day + "/" + year, driver.findElement(By.name("DateLabel")));
		WebElement element = driver.findElement(By.name("EditButton"));
		Assert.assertNotNull(element);
		element.click();
	}

	/**
	 * Clicks on the button of the first patient result
	 * 
	 * @throws InterruptedException
	 */
	@When("^I can edit (.+) data on the patient$")
	public void editData(String state) throws InterruptedException {
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

}