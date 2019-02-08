/**
 * 
 */
package edu.ncsu.csc.itrust.cucumber.util;

import org.openqa.selenium.WebDriver;

/**
 * @author seelder
 *Abstract Class providing shared elements and methods between
 *PageObjects
 */
public abstract class AbstractPageObject {
	protected WebDriver driver;
	
	/**
	 * Constructor
	 */
	public AbstractPageObject(){
		driver = null;
		
	}
	
	/**
	 * Generic function used to set the webdriver used by each page
	 * Does NOT enforce the singleton pattern (should be handled elsewhere)
	 * @param wd current WebDriver
	 */
	public void setDriver(WebDriver wd){
		driver = wd;
	}


}
