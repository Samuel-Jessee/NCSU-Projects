package edu.ncsu.csc.itrust.cucumber;

import static org.junit.Assert.fail;

import java.sql.Timestamp;

import javax.sql.DataSource;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.apptType.ApptTypeMySQLConverter;
import edu.ncsu.csc.itrust.model.old.beans.ApptBean;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class AppointmentRequestStepDefs {
	private AuthDAO authController;
    private UserDataShared sharedUser;
    private ApptBean appt; //this has methods to get appointment type, name, duration, price, etc
    private long patientMID;
    
    private WebDriver webDriver = null; //starting to add selenium tests to check user flow
    
    /**
     * Initializes controller for user authentication 
     * @param sharedUser 
     */
    public AppointmentRequestStepDefs (UserDataShared sharedUser){
		this.authController = new AuthDAO(TestDAOFactory.getTestInstance());
		this.sharedUser = sharedUser;
		
	}
    
    /**
     * See if the user credentials are valid when they try to login
     * @param mid
     * @param password
     */
    @Given("^I login as Patient with (.+) (.+)$")
    public void patient_login(long mid, String password) {
    	this.patientMID = mid;
    	
    	try {
			if(authController.authenticatePassword(mid, password)){
				sharedUser.loginID = mid;
			}
		} catch (DBException e) {
			fail("Unable to authenticate Password");
		}
    	
    }
    
    /**
     * A new appointment is created and checked to make sure it is valid.
     * 
     * @throws Throwable
     */
    @When("^I navigate to Appointment Requests page$")
	public void patient_navigates_to_appointment_request_page() throws Throwable {
    	appt = new ApptBean();
    	
    	Assert.assertTrue(appt != null);
    		
	}
    
    /**
     * Appointment fields for ApptBean are set from the patient's input on the appointment requests page
     * @param apttype
     * @param date
     * @param comments
     * @throws Throwable
     */
    @When("^select (.+), enter (.+), enter (.+), select (.+)$")
    public void patient_enters_appointment_info (String apttype, String date, String comments, long hmid) throws Throwable {
    	Timestamp date2 = Timestamp.valueOf(date);
    	appt.setPatient(patientMID);
    	appt.setHcp(hmid);
    	appt.setApptType(apttype);  
    	appt.setDate(date2);
    	appt.setComment(comments);
    	//set the patient's info at this point
   
    }
    
    /**
     * Check if the fields for the appointment are valid
     * @throws Throwable
     */
    @When("^clicks request$")
    public void patient_clicks_request() throws Throwable {
    	//appt request has been saved and is pending...
    	Assert.assertTrue(appt.getApptType() != null);
    	Assert.assertTrue(appt.getDate() != null);
    	Assert.assertTrue(appt.getComment() != null);
    	//check if the above items have been set correctly from the previous method
    }
    
    /**
     * Check if the right hcp is linked to what the patient chose (if they are supposed to choose from a list of all of them?)
     * @param hmid
     * @throws Throwable
     */
    @Then("^the appointment request is linked to the HCP (.+)$")
    public void request_linked_to_HCP (long hmid) throws Throwable {
    	//getHCP()?
    	//System.out.print(appt.getHcp()); part of testing if hmid was really being set, but wasnt - fixed test and feature file 2/2/2017 Carolyn 
    	//System.out.print(hmid);
    	Assert.assertTrue(appt.getHcp() == hmid);
    }   
}