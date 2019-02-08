/**
 * 
 */
package edu.ncsu.csc.itrust.cucumber;

import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust.controller.childbirth.ChildbirthController;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.childbirth.ChildInChildbirth;
import edu.ncsu.csc.itrust.model.childbirth.Childbirth;
import edu.ncsu.csc.itrust.model.childbirth.ChildbirthValidator;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

/**
 * @author Samuel Jessee (sijessee)
 *
 */
public class ChildbirthStepDefs {
	private ChildbirthController controller;
	private Childbirth cb;
	private DataSource ds;
	private static TestDataGenerator gen;
	@Mock
	private SessionUtils mockSessionUtils;
	private Long id;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		gen.standardData();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Given("^a childbirth visit was prescheduled: (.+)$")
	public void prescheduled(String scheduled) throws DBException, FileNotFoundException, IOException, SQLException {
		mockSessionUtils = Mockito.mock(SessionUtils.class);
		ds = ConverterDAO.getDataSource();
		controller = Mockito.spy(new ChildbirthController(ds, mockSessionUtils));
		Mockito.doNothing().when(controller).printFacesMessage(Matchers.any(FacesMessage.Severity.class),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		new ChildbirthValidator();
		cb = new Childbirth();
		cb.setChildbirthchildren(new ArrayList<ChildInChildbirth>());
	}

	@When("^I select patient (.+)$")
	public void select_patient(String mid) {
		cb.setPatientMID(Long.parseLong(mid));
	}

	@When("^I select (.+) as the preferred delivery method$")
	public void pref_method(String method) {
	}

	@When("^I enter (.+) for the amount of each drug administered$")
	public void drug_amounts(String amount) {
		Double amt = Double.parseDouble(amount);
		cb.setPitocin(amt);
		cb.setNitrousOxide(amt);
		cb.setPethidine(amt);
		cb.setEpiduralAnaesthesia(amt);
		cb.setMagnesiumSulfate(amt);
		cb.setRhimmuneglobulin(amt);
	}

	@When("^I enter the date and time: (.+)$")
	public void set_date(String date) {
		cb.setDate(LocalDateTime.parse(date));
	}

	@When("^I enter the delivery method: (.+)$")
	public void set_method(String method) {
		cb.setMethod(method);
	}

	@When("^I enter the hours in labor: (\\d+)$")
	public void set_hours(Integer hours) {
		cb.setHoursInLabor(hours);
	}

	@When("^I add the baby as a patient, entering (.+), (.+) and (.+)$")
	public void add_child(String sex, String estimate, String dob) {
		cb.getChildbirthchildren().add(new ChildInChildbirth());
	}

	@When("^I save the childbirth visit$")
	public void save_visit() throws Exception {
		when(mockSessionUtils.getSessionLoggedInMIDLong()).thenReturn(9000000012L);
		when(mockSessionUtils.getCurrentPatientMIDLong()).thenReturn(21L);
		when(mockSessionUtils.getCurrentOfficeVisitId()).thenReturn(100L);
		mockSessionUtils.setSessionVariable("loggedinMID", 9000000012L);
		id = controller.addReturnGeneratedID(cb);
	}

	@Then("^the visit was stored successfully$")
	public void then() throws Exception {
		if (id < 0) {
			throw new Exception();
		}
	}

}
