/**
 * 
 */
package edu.ncsu.csc.itrust.unit.controller.laborreport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.ncsu.csc.itrust.controller.laborreport.LaborReport;
import edu.ncsu.csc.itrust.controller.laborreport.LaborReportForm;

/**
 * @author Samuel Jessee
 *
 */
public class LaborReportFormTest {

	private LaborReportForm form;
	private LocalDate date;
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
		form = new LaborReportForm();
		form.init();
		date = LocalDate.now();
		form.setEdd(date);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.controller.laborreport.LaborReportForm#getLr()}.
	 */
	@Test
	public void testGetLr() {
		report = form.getLr();
		assertTrue(null == report);
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.controller.laborreport.LaborReportForm#getEdd()}
	 * and
	 * {@link edu.ncsu.csc.itrust.controller.laborreport.LaborReportForm#setEdd(java.time.LocalDate)}.
	 */
	@Test
	public void testGetSetEdd() {
		assertEquals(date, form.getEdd());
	}

}
