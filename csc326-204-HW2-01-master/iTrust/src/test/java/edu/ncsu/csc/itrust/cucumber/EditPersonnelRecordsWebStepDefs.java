package edu.ncsu.csc.itrust.cucumber;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust.cucumber.util.ITrustDriver;
import edu.ncsu.csc.itrust.cucumber.util.DemographicsPage;

/**
 * Steps defs for Bug 6
 * @author alrichma
 *
 */
public class EditPersonnelRecordsWebStepDefs {
	
	/** ADDRESS */
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	
	ITrustDriver driver;
	DemographicsPage demoPage;	
	
	public EditPersonnelRecordsWebStepDefs(ITrustDriver itrustDriver, DemographicsPage demo){
		this.driver = itrustDriver;
		demoPage = demo;
		demoPage.setDriver(itrustDriver.getWrappedDriver());
	}
	
	@Given("^I click My Demographics$")
	public void goToDemo(){
		WebElement demo = driver.findElement(By.linkText("My Demographics"));
		demo.click();
		Assert.assertTrue(driver.getTitle().equals("iTrust - Edit Personnel"));
		
	}
	
	@When("^user attempts to change (.+), (.+), (.+), (.+), (.+), (.+)$")
	public void user_change_field(String firstName, String lastName, String streetAddress1, String streetAddress2, String city, String state){
		Assert.assertTrue(driver.verifyLocation("/iTrust/auth/staff/editMyDemographics.jsp"));	
		demoPage.inputFirstName(firstName);
		demoPage.inputLastName(lastName);
		demoPage.inputStreetAddress1(streetAddress1);
		demoPage.inputStreetAddress2(streetAddress2);
		demoPage.inputState(state);
		demoPage.inputCity(city);
	}
	
	@When("^I click submit$")
	public void submitValues(){
		Assert.assertTrue(driver.verifyLocation("/iTrust/auth/staff/editMyDemographics.jsp"));	  
		demoPage.submitRecipe();
	}
	
	@Then("^the values are successfully changed$")
	public void field_is_changed(){
		Assert.assertTrue(demoPage.findStatusMessage());
	}
	
}
