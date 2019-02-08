package edu.ncsu.csc.itrust.cucumber;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust.cucumber.util.ITrustDriver;
import edu.ncsu.csc.itrust.cucumber.util.CalendarPage;
import edu.ncsu.csc.itrust.cucumber.util.LoginMenuPage;
import edu.ncsu.csc.itrust.cucumber.util.MenuPage;

/**
 * Step Defs for bug 1
 * @author smmahaff
 *
 */
public class ViewFullCalendarWebStepDefs {
	
	/** ADDRESS */
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	
	ITrustDriver driver;
	CalendarPage calendarPage;
	
	public ViewFullCalendarWebStepDefs(CalendarPage calendar, LoginMenuPage login, MenuPage menu, ITrustDriver d) {
		this.driver = d;
		
		calendarPage = calendar;
		calendar.setDriver(d.getWrappedDriver());
	}
	
	@When("^user navigates to the View Full Calendar page$")
	public void user_navigates_to_the_View_Full_Calendar_page() {
		WebElement demo = driver.findElement(By.linkText("Full Calendar"));
		demo.click();
	}
	
	@Then("^the Calendar page is displayed$")
	public void Calendar_page_is_displayed() {
		Assert.assertTrue(driver.getTitle().equals("iTrust - Appointment Calendar"));
	}
}
