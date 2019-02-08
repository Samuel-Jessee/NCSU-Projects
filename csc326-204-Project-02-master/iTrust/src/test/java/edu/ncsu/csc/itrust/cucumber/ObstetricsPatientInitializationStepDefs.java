package edu.ncsu.csc.itrust.cucumber;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust.selenium.iTrustSeleniumTest;

public class ObstetricsPatientInitializationStepDefs {
	private WebDriver webDriver = null;
	
//	@Override
//	protected void setUp() throws Exception {
//		super.setUp(); // clear tables is called in super
//		gen.clearAllTables();
//		gen.standardData();
//	}
//	
//	@Override
//	protected void tearDown() throws Exception {
//		gen.clearAllTables();
//	}
	
	/**
     * See if the user credentials are valid when they try to login
     * @param mid
     * @param password
     */
    @Given("^I login with mid: (.+) and password: (.+)$")
    public void obstetrics_hcp_login(String mid, String password) throws Exception {
//    	webDriver = login(mid, password);
//		assertEquals("iTrust - HCP Home", webDriver.getTitle());
    }
    
    @When("^I navigate to the obstetrics page$")
    public void go_to_obstetrics_page() {
//    	webDriver.findElement(By.linkText("Obstetrics Records")).click();
    }
    
    @When("^I search for the obstetrics patient: (.+)$")
    public void search_for_obstetrics_patient(String patient) {
//    	webDriver.findElement(By.name("FIRST_NAME")).sendKeys(patient);
//    	webDriver.findElement(By.name("FIRST_NAME")).submit();
//    	webDriver.findElement(By.id("selectPatient1")).submit();
//    	assertTrue(webDriver.getTitle().equals("Obstetrics Patient Info"));
    }
    
    @When("^click Add New Obstetrics Patient$")
    public void add_new_obstetrics_visit() {
//    	webDriver.findElement(By.id("newRecordButton")).click();
    }
    
    @When("^input the LMP: (.+) and Date: (.+) and submit$")
    public void enter_lmp(String lmp, String date) {
    	//webDriver.findElement(By.id("lmp")).sendKeys(lmp);
    	//webDriver.findElement(By.id("date")).sendKeys();
    	//webDriver.findElement(By.linkText("Save Obstetrics Initialization")).click();
    }
    
    @When("^I enter (.+), (.+), (.+), (.+), (.+), (.+) for the fields$")
    public void enter_prior_pregnancy_fields(String year, String weeks, String hours, String weight, String type, String multiple) {
//    	webDriver.findElement(By.id("conceptionYear")).sendKeys(year);
//    	webDriver.findElement(By.id("weeksPreg")).sendKeys(weeks);
//    	webDriver.findElement(By.id("hoursInLabor")).sendKeys(hours);
//    	webDriver.findElement(By.id("weightGained")).sendKeys(weight);
//    	Select dropdown = new Select(webDriver.findElement(By.id("delType")));
//    	dropdown.selectByValue(type);
//    	webDriver.findElement(By.id("amtChildren")).sendKeys(multiple);
    }
    
    @When("^I submit$")
    public void i_submit() {
    	//webDriver.findElement(By.linkText("Add Prior Pregnacy")).click();
    }
    
    @Then("^the patient's record is shown$")
    public void patients_record_is_shown() {
//    	webDriver.getPageSource().contains("No Obstetrics Records for this Patient!");
    }
    
    @Then("^the EDD and number of weeks pregnant are displayed$")
    public void edd_and_number_weeks_pregnant_displayed() {
    	//webDriver.navigate().back();
    	//webDriver.findElement(By.linkText("Select")).click();
    	//webDriver.findElement(By.id("edd")).toString().isEmpty();
    	//webDriver.findElement(By.id("weeksPreg")).toString().isEmpty();
    }
    
    @Then("^an error appears$")
    public void an_error_appears() {
    	//webDriver.getPageSource().contains("invalid");
    }
    
    @Then("^the message The patient is not eligible for obstetric care is displayed")
    public void patient_not_eligible_message_displayed() {
//    	webDriver.getPageSource().contains("The patient is not eligible for obstetric care");
    }
    
    @Then("^the values I edited are saved$")
    public void values_are_saved() {
    	//webDriver.findElement(By.id("previousVisits")).isDisplayed();
    }
    
    @When("^view an existing record$")
    public void view_an_existing_record() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("^I enter (\\d+), (\\d+), (\\d+), , Vaginal Delivery, no for the fields$")
    public void i_enter_Vaginal_Delivery_no_for_the_fields(int arg1, int arg2, int arg3) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }
}