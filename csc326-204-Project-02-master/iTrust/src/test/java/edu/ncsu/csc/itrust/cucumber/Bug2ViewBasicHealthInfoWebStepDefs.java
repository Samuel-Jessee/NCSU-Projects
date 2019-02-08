package edu.ncsu.csc.itrust.cucumber;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;
import edu.ncsu.csc.itrust.selenium.iTrustSeleniumTest;
import cucumber.api.java.en.Then;

public class Bug2ViewBasicHealthInfoWebStepDefs extends iTrustSeleniumTest {
	private WebDriver webDriver = null;
	
	/**
	 * Basic setup to do before testing.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}

	/**
	 * User will click on HCP 1 from sample users to login
	 * @throws Exception
	 */
	@Given("^user clicks on HCP 1 sample user from login page$")
	public void testhcp1LoginFromSampleUsers() throws Exception{
		webDriver = login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", webDriver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	}
	
	/**
	 * User clicks on Basic Health Information from the Patient drop down list from the 
	 * menu on the left side of the page.
	 */
	@When("^user clicks on Basic Health Information from Patient dropdown list$")
	public void hcp1PatientDropList(){
		webDriver.findElement(By.linkText("Basic Health Information")).click();
		assertEquals("iTrust - Please Select a Patient", webDriver.getTitle());
	}
	
	/**
	 * User types MID 1 into search box
	 */
	@When("^user types in 1 into search box$")
	public void hcp1SearchBox(){
		webDriver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
	}
	
	/**
	 * User clicks on Patient MID 1
	 */
	@When("^user clicks on Patient Random Person with MID 1")
	public void hcp1LookAtPatientRecords(){
		webDriver.findElement(By.name("UID_PATIENTID")).submit();
	}
	
	/**
	 * The Basic Health Information for Patient 1 is visible
	 */
	@Then("^the Basic Health Information for Patient 1 is visible")
	public void hcp1BasicHealthInfoVisible(){
		assertEquals(webDriver.getTitle(), "iTrust - View Patient Records");
	}
}
