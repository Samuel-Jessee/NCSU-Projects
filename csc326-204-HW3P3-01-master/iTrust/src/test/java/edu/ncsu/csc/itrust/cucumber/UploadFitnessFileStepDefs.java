package edu.ncsu.csc.itrust.cucumber;

import org.junit.Assert;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust.cucumber.util.FitnessMainPage;
import edu.ncsu.csc.itrust.cucumber.util.ITrustDriver;

/**
 * Step Defs to test the uploading of FitBit files
 * @author alrichma
 *
 */
public class UploadFitnessFileStepDefs {
	
	/** ADDRESS */
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	
	ITrustDriver driver;
	FitnessMainPage fitnessMain;
	
	public UploadFitnessFileStepDefs(ITrustDriver itrustDriver, FitnessMainPage fitnessPage){
		this.driver = itrustDriver;
		this.fitnessMain = fitnessPage;
	}
	
	/**
	 * Test whether the user can add the file
	 * @param file
	 */
	@When("user attempts to add file (.+)")
	public void add_file(String file){
		fitnessMain.submitFile(file);
		Assert.assertTrue(driver.getTitle().equals("iTrust - View Fitness Health"));
	}
	
	/**
	 * Tests whether the user clicks to upload
	 */
	@When("I click upload")
	public void click_upload(){
		fitnessMain.submitUploadFile();
		Assert.assertTrue(driver.getTitle().equals("iTrust - View Fitness Health"));
	}
	
	/**
	 * Tests whether the values are successfully uploaded
	 */
	@Then("the values are successfully uploaded")
	public void success_upload(){
		Assert.assertTrue(fitnessMain.findStatusMessage());
	}
	
	/**
	 * Tests whether the values are not successfully uploaded
	 */
	@Then("the values are not uploaded")
	public void not_upload(){
		Assert.assertTrue(!fitnessMain.findStatusMessage());
	}
	

}
