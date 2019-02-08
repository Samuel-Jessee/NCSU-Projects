package edu.ncsu.csc.itrust.cucumber;

import java.util.Date;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust.cucumber.util.SharedPatient;
import edu.ncsu.csc.itrust.cucumber.util.SharedPersonnel;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.beans.PersonnelBean;
import edu.ncsu.csc.itrust.model.old.enums.Role;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import edu.ncsu.csc.itrust.selenium.iTrustSeleniumTest;

/**
 * Tests bug #4, which involves a patient requesting an appointment.
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
public class RequestAppointmentStepDefs extends iTrustSeleniumTest {

	WebDriver driver;
	WebElement element;

	/**
	 * Setup for tests
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		gen.hcp9();
	}

	/** Shared personnel object used in tests */
	private SharedPersonnel sharedPersonnel;

	/** Shared patient object used in tests */
	private SharedPatient sharedPatient;

	/**
	 * Constructor for step defs. Initializes shared objects.
	 * 
	 * @param sharedPersonnel
	 *            shared personnel object
	 * @param sharedPatient
	 *            shared patient object
	 * @throws Exception
	 */
	public RequestAppointmentStepDefs(SharedPersonnel sharedPersonnel, SharedPatient sharedPatient) throws Exception {
		this.sharedPersonnel = sharedPersonnel;
		this.sharedPatient = sharedPatient;
	}

	/**
	 * Tests for existence of HCP with given MID
	 * 
	 * @param MID
	 *            mid of hcp
	 * @throws Throwable
	 */
	@Given("^(?:.*) is an active HCP with MID: (\\d+)$")
	public void hcp_is_an_active_HCP_with_MID(long MID) throws Throwable {
		PersonnelBean p = sharedPersonnel.getPersonnelDAO().getPersonnel(MID);
		assertNotNull(String.format("Personnel with MID: %d doesn't exist", MID), p);
		assertEquals(Role.HCP, p.getRole());
	}

	/**
	 * Tests for existence of patient with given MID
	 * 
	 * @param MID
	 *            patient mid
	 * @throws Throwable
	 */
	@Given("^(?:.*) is a patient with MID: (\\d+)$")
	public void patient_is_a_patient_with_MID(long MID) throws Throwable {
		PatientBean p = sharedPatient.getPatientDAO().getPatient(MID);
		assertNotNull(String.format("Patient with MID: %d doesn't exist", MID), p);
	}

	/**
	 * Logs in the patient
	 * 
	 * @param MID
	 *            patient mid
	 * @throws Throwable
	 */
	@When("^(?:.*) logs in as a patient with MID: (\\d+)")
	public void patient_logs_in_as_patient(Long MID) throws Throwable {
		String MIDString = Long.toString(MID);
		driver = login(MIDString, "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
	}

	/**
	 * Patient navigates to appointment request page
	 * 
	 * @throws Throwable
	 */
	@When("^requests an appointment$")
	public void requests_an_appointment() throws Throwable {
		element = driver.findElement(By.linkText("Appointment Requests"));
		element.click();
		assertEquals("iTrust - Appointment Requests", driver.getTitle());
	}

	/**
	 * Patient chooses HCP
	 * 
	 * @throws Throwable
	 */
	@When("^chooses (?:.*) as their HCP$")
	public void chooses_hcp() throws Throwable {
		Select select;
		select = new Select(driver.findElement(By.name("lhcp")));
		select.selectByVisibleText("Gandalf Stormcrow");
	}

	/**
	 * Patient chooses appointment type
	 * 
	 * @throws Throwable
	 */
	@When("^chooses appointment type (?:.*)$")
	public void chooses_appointment_type() throws Throwable {
		Select select;
		select = new Select(driver.findElement(By.name("apptType")));
		select.selectByValue("General Checkup");
	}

	/**
	 * Sets date and time for appointment request
	 * 
	 * @param date
	 * @throws Throwable
	 */
	@When("^chooses the date: (\\S+)$")
	public void chooses_the_date_to_be(Date date) throws Throwable {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		cal.add(Calendar.DAY_OF_YEAR, 1);
		Select select;
		select = new Select(driver.findElement(By.name("time1")));
		select.selectByValue("09");
		select = new Select(driver.findElement(By.name("time2")));
		select.selectByValue("45");
		select = new Select(driver.findElement(By.name("time3")));
		element = driver.findElement(By.name("startDate"));
		element.clear();
		element.sendKeys(format.format(cal.getTime()));
		// element.submit();
	}

	/**
	 * Enters a comment, which will also be used to identify the request later.
	 * 
	 * @param notes
	 *            patient's comment
	 * @throws Throwable
	 */
	@When("^enters the Comment “(.+)”$")
	public void enters_the_Comment(String notes) throws Throwable {
		element = driver.findElement(By.name("comment"));
		element.sendKeys(notes);
	}

	/**
	 * Submits form
	 * 
	 * @throws Throwable
	 */
	@When("^submits the request$")
	public void submit_request() throws Throwable {
		element = driver.findElement(By.name("request"));
		element.click();
	}

	/**
	 * Logs in HCP
	 * 
	 * @param MID
	 *            hcp mid
	 * @throws Throwable
	 */
	@When("^(?:.*) logs in with MID (\\d+)")
	public void hcp_logs_in(Long MID) throws Throwable {
		driver.get("http://localhost:8080/iTrust/logout.jsp");
		assertEquals("iTrust - Login", driver.getTitle());
		String MIDString = Long.toString(MID);
		driver = login(MIDString, "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
	}

	/**
	 * HCP views appointment requests
	 * 
	 * @throws Throwable
	 */
	@When("^chooses to view pending appointment requests$")
	public void chooses_to_view_appointment_requests() throws Throwable {
		element = driver.findElement(By.linkText("Appointment Requests"));
		element.click();
		assertEquals("iTrust - View My Appointment Requests", driver.getTitle());
	}

	/**
	 * Checks for the new request by searching for the unique comment
	 * 
	 * @param notes
	 *            patient comment
	 * @throws Throwable
	 */
	@Then("^the new appointment request will be listed with the comment “(.+)”$")
	public void appointment_request_is_listed(String notes) throws Throwable {
		assertTrue(driver.getPageSource().contains(notes));
	}
}
