/**
 * 
 */
package edu.ncsu.csc.itrust.unit.model.obstetricsInitialization;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitialization;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitializationMySQL;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitializationSQLLoader;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.PreviousPregnancyInfo;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;

/**
 * @author Samuel Jessee (sijessee)
 *
 */
public class ObstetricsInitializationSQLLoaderTest {

	private static DataSource ds;
	private static TestDataGenerator gen;
	private static ObstetricsInitializationMySQL sql;
	private static ObstetricsInitializationSQLLoader loader;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ds = ConverterDAO.getDataSource();
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.uc93();
		sql = new ObstetricsInitializationMySQL(ds);
		loader = new ObstetricsInitializationSQLLoader();

	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		gen.clearAllTables();
		gen.standardData();
	}

	private ObstetricsInitialization oi;

	private PreviousPregnancyInfo pi;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		oi = new ObstetricsInitialization();
		oi.setDate(LocalDateTime.of(2011, 03, 10, 0, 0));
		oi.setLMP(LocalDate.of(2011, 02, 10));
		oi.setPatientMID(1L);
		// oi.setVisitID(1L);
		pi = new PreviousPregnancyInfo();
		pi.setAmtChildren(1);
		pi.setDeliveryType(1);
		pi.setHoursInLabor(1);
		pi.setPatientMID(1L);
		pi.setWeeksPreg(100);
		pi.setWeightGained(20);
		pi.setYear(2001);
		// pi.setInfoID(1L);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitializationSQLLoader#loadList(java.sql.ResultSet)}.
	 */
	@Test
	public void testLoadList() {
		List<ObstetricsInitialization> list = null;
		try {
			list = sql.getVisitsForPatient(1L);
		} catch (DBException e) {
			fail("DBException");
			e.printStackTrace();
		}
		assertTrue(list != null);
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitializationSQLLoader#loadParameters(java.sql.Connection, java.sql.PreparedStatement, edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitialization, boolean)}.
	 */
	@Test
	public void testLoadParameters() {
		PreparedStatement pstring = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			fail("SQLException");
			e.printStackTrace();
		}
		try {
			pstring = loader.loadParameters(conn, pstring, oi, true);
		} catch (SQLException e) {
			fail("SQLException");
			e.printStackTrace();
		}
		assertTrue(pstring != null);
		pi.setInfoID(1L);
		try {
			pstring = loader.loadParameters(conn, pstring, pi, false);
		} catch (SQLException e) {
			fail("SQLException");
			e.printStackTrace();
		}
		assertTrue(pstring != null);
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitializationSQLLoader#loadPregList(java.sql.ResultSet)}.
	 */
	@Test
	public void testLoadPregList() {
		List<PreviousPregnancyInfo> list = null;
		try {
			list = sql.getPrevPregForPatient(1L);
		} catch (DBException e) {
			fail("DBException");
			e.printStackTrace();
		}
		assertTrue(list != null);
	}

}
