/**
 * 
 */
package edu.ncsu.csc.itrust.unit.controller.laborreport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.ncsu.csc.itrust.controller.laborreport.LaborReport;
import edu.ncsu.csc.itrust.model.diagnosis.Diagnosis;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.PreviousPregnancyInfo;
import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.ObstetricsOfficeVisit;
import edu.ncsu.csc.itrust.model.old.beans.AllergyBean;
import edu.ncsu.csc.itrust.model.old.enums.BloodType;

/**
 * @author Samuel Jessee
 *
 */
public class LaborReportTest {

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

	private LaborReport report;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		report = new LaborReport();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.controller.laborreport.LaborReport#getAllergies()}.
	 */
	@Test
	public void testGetAllergies() {
		List<AllergyBean> value = new ArrayList<AllergyBean>();
		report.setAllergies(value);
		assertEquals(value, report.getAllergies());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.controller.laborreport.LaborReport#getBloodtype()}.
	 */
	@Test
	public void testGetBloodtype() {
		BloodType value = BloodType.parse("A+");
		report.setBloodtype(value);
		assertEquals(value, report.getBloodtype());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.controller.laborreport.LaborReport#getConditions()}.
	 */
	@Test
	public void testGetConditions() {
		List<Diagnosis> value = new ArrayList<Diagnosis>();
		report.setConditions(value);
		assertEquals(value, report.getConditions());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.controller.laborreport.LaborReport#getEstimatedDeliveryDate()}.
	 */
	@Test
	public void testGetEstimatedDeliveryDate() {
		LocalDate value = LocalDate.now();
		report.setEstimatedDeliveryDate(value);
		assertEquals(value, report.getEstimatedDeliveryDate());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.controller.laborreport.LaborReport#getObOfficeVisit()}.
	 */
	@Test
	public void testGetObOfficeVisit() {
		List<ObstetricsOfficeVisit> value = new ArrayList<ObstetricsOfficeVisit>();
		report.setObOfficeVisit(value);
		assertEquals(value, report.getObOfficeVisit());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.controller.laborreport.LaborReport#getPastPreg()}.
	 */
	@Test
	public void testGetPastPreg() {
		List<PreviousPregnancyInfo> value = new ArrayList<PreviousPregnancyInfo>();
		report.setPastPreg(value);
		assertEquals(value, report.getPastPreg());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.controller.laborreport.LaborReport#getPatientMID()}.
	 */
	@Test
	public void testGetPatientMID() {
		long value = 201;
		report.setPatientMID(value);
		assertEquals(value, report.getPatientMID());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.controller.laborreport.LaborReport#isAbnormalFetalHeartRate()}.
	 */
	@Test
	public void testIsAbnormalFetalHeartRate() {
		report.setAbnormalFetalHeartRate(true);
		assertTrue(report.isAbnormalFetalHeartRate());

		report.setAbnormalFetalHeartRate(false);
		assertFalse(report.isAbnormalFetalHeartRate());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.controller.laborreport.LaborReport#isAdvancedMaternalAge()}.
	 */
	@Test
	public void testIsAdvancedMaternalAge() {
		report.setAdvancedMaternalAge(true);
		assertTrue(report.isAdvancedMaternalAge());

		report.setAdvancedMaternalAge(false);
		assertFalse(report.isAdvancedMaternalAge());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.controller.laborreport.LaborReport#isAtypicalWeightChange()}.
	 */
	@Test
	public void testIsAtypicalWeightChange() {
		report.setAtypicalWeightChange(true);
		assertTrue(report.isAtypicalWeightChange());

		report.setAtypicalWeightChange(false);
		assertFalse(report.isAtypicalWeightChange());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.controller.laborreport.LaborReport#isGeneticPotential()}.
	 */
	@Test
	public void testIsGeneticPotential() {
		report.setGeneticPotential(true);
		assertTrue(report.isGeneticPotential());

		report.setGeneticPotential(false);
		assertFalse(report.isGeneticPotential());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.controller.laborreport.LaborReport#isHighBloodPressure()}.
	 */
	@Test
	public void testIsHighBloodPressure() {
		report.setHighBloodPressure(true);
		assertTrue(report.isHighBloodPressure());

		report.setHighBloodPressure(false);
		assertFalse(report.isHighBloodPressure());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.controller.laborreport.LaborReport#isHyperemesisGravidarum()}.
	 */
	@Test
	public void testIsHyperemesisGravidarum() {
		report.setHyperemesisGravidarum(true);
		assertTrue(report.isHyperemesisGravidarum());

		report.setHyperemesisGravidarum(false);
		assertFalse(report.isHyperemesisGravidarum());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.controller.laborreport.LaborReport#isHypothyroidism()}.
	 */
	@Test
	public void testIsHypothyroidism() {
		report.setHypothyroidism(true);
		assertTrue(report.isHypothyroidism());

		report.setHypothyroidism(false);
		assertFalse(report.isHypothyroidism());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.controller.laborreport.LaborReport#isLowLyingPlacenta()}.
	 */
	@Test
	public void testIsLowLyingPlacenta() {
		report.setLowLyingPlacenta(true);
		assertTrue(report.isLowLyingPlacenta());

		report.setLowLyingPlacenta(false);
		assertFalse(report.isLowLyingPlacenta());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.controller.laborreport.LaborReport#isMaternalAllergies()}.
	 */
	@Test
	public void testIsMaternalAllergies() {
		report.setMaternalAllergies(true);
		assertTrue(report.isMaternalAllergies());

		report.setMaternalAllergies(false);
		assertFalse(report.isMaternalAllergies());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.controller.laborreport.LaborReport#isMultiplePreg()}.
	 */
	@Test
	public void testIsMultiplePreg() {
		report.setMultiplePreg(true);
		assertTrue(report.isMultiplePreg());

		report.setMultiplePreg(false);
		assertFalse(report.isMultiplePreg());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.controller.laborreport.LaborReport#isPreExisitngConditions()}.
	 */
	@Test
	public void testIsPreExisitngConditions() {
		report.setPreExisitngConditions(true);
		assertTrue(report.isPreExisitngConditions());

		report.setPreExisitngConditions(false);
		assertFalse(report.isPreExisitngConditions());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.controller.laborreport.LaborReport#isRhFlag()}.
	 */
	@Test
	public void testIsRhFlag() {
		report.setRhFlag(true);
		assertTrue(report.isRhFlag());

		report.setRhFlag(false);
		assertFalse(report.isRhFlag());
	}

}
