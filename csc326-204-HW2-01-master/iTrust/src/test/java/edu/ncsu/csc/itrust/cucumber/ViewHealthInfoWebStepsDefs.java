package edu.ncsu.csc.itrust.cucumber;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust.cucumber.util.ITrustDriver;
/**
 * Step Defs for Bug 2
 * @author alrichma
 *
 */
public class ViewHealthInfoWebStepsDefs {
	/** ADDRESS */
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	/** Default timeout for Selenium webdriver */
	public static final int DEFAULT_TIMEOUT = 2;
	ITrustDriver driver;

	public ViewHealthInfoWebStepsDefs(ITrustDriver itrustDriver){
		this.driver = itrustDriver;
	}
	
	@When("^user navigates to view basic health info page for patient$")
	public void goToBasicHealth(){
		WebElement demo = driver.findElement(By.linkText("Basic Health Information"));
		demo.click();
		Assert.assertTrue(driver.getTitle().equals("iTrust - Please Select a Patient"));
	}
	
	@When("^user enters (.+) in first name field$")
	public void enterMID(String patientName){
		WebElement text = driver.findElement(By.name("FIRST_NAME"));
		text.sendKeys(patientName);
		text.submit();
	}
	
	@When("^user chooses patient with MID (.+)$")
	public void selectMID(String MID){
		driver.findElement(By.id("selectPatient1")).submit();
	}
	
	@Then("^the patient's basic health info page is successfully viewed$")
	public void viewBasicInfoSuccessfully(){
		Assert.assertTrue(driver.getTitle().equals("iTrust - View Patient Records"));
		Assert.assertTrue(driver.findElement(By.id("iTrustSelectedPatient")).isDisplayed());
	}

}
