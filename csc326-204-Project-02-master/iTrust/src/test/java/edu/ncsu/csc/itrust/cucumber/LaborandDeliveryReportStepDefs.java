package edu.ncsu.csc.itrust.cucumber;

import static org.mockito.Mockito.spy;

import java.sql.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Assert;
import org.mockito.Mock;
import org.mockito.Mockito;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import edu.ncsu.csc.itrust.controller.laborreport.LaborReport;
import edu.ncsu.csc.itrust.controller.laborreport.LaborReportController;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.old.beans.AllergyBean;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AllergyDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

public class LaborandDeliveryReportStepDefs {
	private TestDataGenerator gen;
	private LaborReportController controller;
	private DataSource ds;
	@Mock private SessionUtils mockSessionUtils;
	private AllergyDAO allergyDAO;
	private LaborReport report;
	
	@Given("^I search for a patient with (\\d+), who has an obstetric initialization$")
	public void i_search_for_a_patient_with_who_has_an_obstetric_initialization(int patientMID) throws Throwable {
		allergyDAO = TestDAOFactory.getTestInstance().getAllergyDAO();
		
	    ds = ConverterDAO.getDataSource();
	    gen = new TestDataGenerator();	    
	    gen.clearAllTables();
	    gen.patient1();
	    gen.uc93();
	    AllergyBean allergy = new AllergyBean();
		allergy.setDescription("Drugs");
		allergy.setFirstFound(null);
		allergy.setId(1L);
		allergy.setNDCode("664662530");
		allergy.setPatientID(1L);
		allergyDAO.addAllergy(allergy);
	    controller = new LaborReportController(ds, TestDAOFactory.getTestInstance().getPatientDAO(), allergyDAO);
	    report = controller.getReport(patientMID);

	}
	@Then("^I should be shown the patient's Labor and Delivery Report$")
	public void i_should_be_shown_the_patient_s_Labor_and_Delivery_Report() throws Throwable {
	    Assert.assertTrue(report.getAllergies().get(0).getDescription().equals("Drugs"));
	    Assert.assertTrue(report.isAdvancedMaternalAge());
	    Assert.assertFalse(report.isAbnormalFetalHeartRate());
	    Assert.assertFalse(report.isAtypicalWeightChange());
	    Assert.assertFalse(report.isGeneticPotential());
	    Assert.assertFalse(report.isHighBloodPressure());
	    Assert.assertFalse(report.isHyperemesisGravidarum());
	    Assert.assertFalse(report.isHypothyroidism());
	    Assert.assertFalse(report.isLowLyingPlacenta());
	    Assert.assertTrue(report.isMaternalAllergies());
	    Assert.assertFalse(report.isMultiplePreg());
	    Assert.assertFalse(report.isPreExisitngConditions());
	    Assert.assertTrue(report.isRhFlag());
	}
}
