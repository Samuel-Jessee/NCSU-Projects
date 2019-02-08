package edu.ncsu.csc.itrust.cucumber.util;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/** PageObject for /iTrust/editMyDemographics.jsp
 * @author alrichma
 *
 */

public class DemographicsPage extends AbstractPageObject{
	private final By firstName = By.name("firstName");
	private final By lastName = By.name("lastName");
	private final By streetAddress1 = By.name("streetAddress1");
	private final By streetAddress2 = By.name("streetAddress2");
	private final By city = By.name("city");
	private final By state = By.name("state");
	private final By editDemographics = By.cssSelector("input[type=\"submit\"]");
	private final By successMessage = By.className("iTrustMessage");
	
	/**Constructor
	 * 
	 */
	
	public DemographicsPage(){
		driver = null;
	}
	
	/**
	 * Clears out any previous information and sets the
	 * specified string as the value in the textbox for
	 * first name
	 * @param name String
	 */
	public void inputFirstName(String name){
		driver.findElement(firstName).clear();
		driver.findElement(firstName).sendKeys(name);
	}
	
	/**
	 * Clears out any previous information and sets the
	 * specified string as the value in the textbox for
	 * last name
	 * @param name String
	 */
	public void inputLastName(String name){
		driver.findElement(lastName).clear();
		driver.findElement(lastName).sendKeys(name);
	}
	
	/**
	 * Clears out any previous information and sets the
	 * specified string as the value in the textbox for
	 * street address 1
	 * @param sa1 String
	 */
	public void inputStreetAddress1(String sa1){
		driver.findElement(streetAddress1).clear();
		driver.findElement(streetAddress1).sendKeys(sa1);
	}
	
	/**
	 * Clears out any previous information and sets the
	 * specified string as the value in the textbox for
	 * street address 2
	 * @param sa2 String
	 */
	public void inputStreetAddress2(String sa2){
		driver.findElement(streetAddress2).clear();
		driver.findElement(streetAddress2).sendKeys(sa2);
	}
	
	/**
	 * Clears out any previous information and sets the
	 * specified string as the value in the textbox for
	 * city
	 * @param City String
	 */
	public void inputCity(String City){
		driver.findElement(city).clear();
		driver.findElement(city).sendKeys(City);
	}
	
	/**
	 * Clears out any previous information and sets the
	 * specified string as the value in the textbox for
	 * State
	 * @param State String
	 */
	public void inputState(String State){
		Select states = new Select(driver.findElement(state));
		states.selectByVisibleText(State);
	}
	
	/**
	 * Click the "Edit Demographics" button
	 */
	public void submitRecipe(){
		driver.findElement(editDemographics).click();
	}
	
	/**
	 * @return Boolean value indicating whether the specified status message was found
	 */
	public boolean findStatusMessage(){
		try{
			driver.findElement(successMessage);
		}
		catch(Exception e){
			return false;	
		}
		return true;
	}
}
