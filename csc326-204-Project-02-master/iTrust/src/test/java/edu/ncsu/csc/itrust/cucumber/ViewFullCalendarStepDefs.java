package edu.ncsu.csc.itrust.cucumber;

import static org.junit.Assert.fail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;
import edu.ncsu.csc.itrust.selenium.iTrustSeleniumTest;

public class ViewFullCalendarStepDefs extends iTrustSeleniumTest {

	/*private AuthDAO authController;
    private UserDataShared sharedUser;
    private long patientMID;*/
    
    private WebDriver webDriver = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp(); // clear tables is called in super
		gen.clearAllTables();
		gen.standardData();
	}

	@Override
	protected void tearDown() throws Exception {
		gen.clearAllTables();
		//gen.standardData();
	}
	
	/**
	 * User will click on Patient 2 to login
	 * @throws Exception
	 */
	@Given("^I login as Patient 2$")
	public void test_patient2_login() throws Exception{
		webDriver = login("2", "pw");
		assertEquals("iTrust - Patient Home", webDriver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
	}
	
    
    @When("^I click on Full Calendar$")
    public void patient_clicks_full_calendar() {
    	webDriver.findElement(By.linkText("Full Calendar")).click();
    }
    
    @Then("^the full calendar is displayed for the current month.$")
    public void alendar_displayed () {
    	assertEquals("iTrust - Appointment Calendar", webDriver.getTitle());
    }
    
}
