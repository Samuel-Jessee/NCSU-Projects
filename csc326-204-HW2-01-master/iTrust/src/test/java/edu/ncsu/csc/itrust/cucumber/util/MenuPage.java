package edu.ncsu.csc.itrust.cucumber.util;

import org.openqa.selenium.By;

/**PageObject for /iTrust/menu.jsp
 * @author smmahaff
 * 
 */
public class MenuPage extends AbstractPageObject{
	private final By fullCalendarLink = By.linkText("Full Calendar");
	private final By myDemographics = By.linkText("My Demographics");
	
	/**Constructor
	 * 
	 */
	public MenuPage(){
		driver = null;
	}
	
	/**Clicks the link to go to the "Full Calendar" page
	 * 
	 */
	public void goToFullCalendar() {
		driver.findElement(fullCalendarLink).click();
	}
	
	public void goToMyDemographics(){
		driver.findElement(myDemographics).click();
	}
}
