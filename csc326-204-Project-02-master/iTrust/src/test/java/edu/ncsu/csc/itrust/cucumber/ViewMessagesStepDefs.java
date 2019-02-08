package edu.ncsu.csc.itrust.cucumber;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.fail;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;
import edu.ncsu.csc.itrust.selenium.iTrustSeleniumTest;
import org.openqa.selenium.*;

import com.meterware.httpunit.HttpUnitOptions;


public class ViewMessagesStepDefs extends iTrustSeleniumTest {

	/** ADDRESS */
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	private WebDriver driver;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		HttpUnitOptions.setScriptingEnabled(true);
		// turn off htmlunit warnings
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
	}
	
	@Given("^the user is logged in as Kelly Doctor$")
	public void testuser_logs_in_with_valid_credentials() throws Throwable {		
		driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	}
	
	@When("^user navigates to My Messages page$")
	public void user_navigates_to_my_messages_page() throws Throwable {
		driver.findElement(By.linkText("Message Inbox")).click();
		assertLogged(TransactionType.INBOX_VIEW, 9000000000L, 0L, "");
		//makes sure the user has an unread message called "Scratchy throat"
		assertTrue(driver.getPageSource().contains("Scratchy Throat"));
		assertTrue(driver.getPageSource().contains("Andy Programmer"));
	}
	
	@When("^clicks read on an unread message$")
	public void user_clicks_read_message() {
		//<a href="viewMessageInbox.jsp?msg=0">Read</a>
		//clicks the first message on the screen, which is unread
		driver.findElement(By.xpath("//a[@href='viewMessageInbox.jsp?msg=0']")).click();
	}
	
	@When("^clicks back to return to the My Messages page$")
	public void user_clicks_back() {
		driver.navigate().back();
	}
	
	@Then("^the message should no longer be bold$")
	public void the_message_is_not_bold() throws Throwable {
		//tests for boldness, should be passing since back() refreshed the page
		String fontWeight = driver.findElement(By.xpath("//table/tbody/tr[2]")).getAttribute("style");
				//getCssValue("font-weight");
		boolean isBold = "font-weight: bold".equals(fontWeight); //this should technically be false for read messages
		assertFalse(isBold);
	}
	
}
