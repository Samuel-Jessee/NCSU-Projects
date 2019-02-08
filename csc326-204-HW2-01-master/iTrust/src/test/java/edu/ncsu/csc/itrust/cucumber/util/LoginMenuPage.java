package edu.ncsu.csc.itrust.cucumber.util;

import org.openqa.selenium.By;

/**PageObject for /iTrust/loginMenu.jsp
 * @author smmahaff
 *
 */

public class LoginMenuPage extends AbstractPageObject {
	private final By user = By.id("j_username");
	private final By pass = By.id("j_password");

	/**Constructor
	 * 
	 */
	public LoginMenuPage(){
		driver = null;
	}
	
	public void goToKelleyDoctor() {
		
	}
}
