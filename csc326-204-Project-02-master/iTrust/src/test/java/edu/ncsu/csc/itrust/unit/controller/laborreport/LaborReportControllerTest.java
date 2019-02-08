/**
 * 
 */
package edu.ncsu.csc.itrust.unit.controller.laborreport;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import edu.ncsu.csc.itrust.controller.laborreport.LaborReport;
import edu.ncsu.csc.itrust.controller.laborreport.LaborReportController;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.old.beans.AllergyBean;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AllergyDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

/**
 * @author Samuel Jessee
 *
 */
public class LaborReportControllerTest {

	private TestDataGenerator gen;
	private LaborReportController controller;
	private DataSource ds;
	@Mock
	private SessionUtils mockSessionUtils;
	private AllergyDAO allergyDAO;
	private LaborReport report;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
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

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructors() {
		try {
			controller = new LaborReportController(ds);
		} catch (DBException e) {
			fail("DBException");
			e.printStackTrace();
		}
		try {
			controller = new LaborReportController();
		} catch (DBException e) {
			e.printStackTrace();
		}
		controller.close();
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.controller.laborreport.LaborReportController#getReport(long)}.
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 * 
	 * @throws DBException
	 * @throws SQLException
	 */
	@Test
	public void testGetReport() throws FileNotFoundException, IOException, SQLException {
		try {
			report = controller.getReport(1L);
		} catch (DBException e) {
			fail("DBException");
			e.printStackTrace();
		}
		assertTrue(report.getAllergies().get(0).getDescription().equals("Drugs"));
		assertTrue(report.isAdvancedMaternalAge());
		assertFalse(report.isAbnormalFetalHeartRate());
		assertFalse(report.isAtypicalWeightChange());
		assertFalse(report.isGeneticPotential());
		assertFalse(report.isHighBloodPressure());
		assertFalse(report.isHyperemesisGravidarum());
		assertFalse(report.isHypothyroidism());
		assertFalse(report.isLowLyingPlacenta());
		assertTrue(report.isMaternalAllergies());
		assertFalse(report.isMultiplePreg());
		assertFalse(report.isPreExisitngConditions());
		assertTrue(report.isRhFlag());

		gen.standardData();
		gen.uc95();
		try {
			report = controller.getReport(1L);
		} catch (DBException e) {
			fail("DBException");
			e.printStackTrace();
		}
		controller.close();
	}

}
