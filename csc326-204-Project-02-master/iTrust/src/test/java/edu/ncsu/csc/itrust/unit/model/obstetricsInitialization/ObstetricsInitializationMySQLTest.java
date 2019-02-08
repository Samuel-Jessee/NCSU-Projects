/**
 * 
 */
package edu.ncsu.csc.itrust.unit.model.obstetricsInitialization;

import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitialization;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitializationMySQL;
import edu.ncsu.csc.itrust.model.officeVisit.OfficeVisitMySQL;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.PreviousPregnancyInfo;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;

/**
 * Test case for ObstetricsInitializationMySQL
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
public class ObstetricsInitializationMySQLTest {

	private static DataSource ds;
	private static TestDataGenerator gen;
	private static ObstetricsInitializationMySQL sql;

	@Mock
	private BasicDataSource mockDataSource;
	@Mock
	private Connection mockConnection;
	@Mock
	private PreparedStatement mockPreparedStatement;
	@Mock
	private ResultSet mockResultSet;
	private ObstetricsInitializationMySQL mockOIsql;
	
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
		
		mockDataSource = Mockito.mock(BasicDataSource.class);
		mockOIsql = new ObstetricsInitializationMySQL(mockDataSource);
		
		mockConnection = Mockito.mock(Connection.class);
		mockResultSet = Mockito.mock(ResultSet.class);
		mockPreparedStatement = Mockito.mock(PreparedStatement.class);

		pi = new PreviousPregnancyInfo();
		pi.setAmtChildren(1);
		pi.setDeliveryType(1);
		pi.setHoursInLabor(1);
		pi.setPatientMID(1L);
		pi.setWeeksPreg(100);
		pi.setWeightGained(20);
		pi.setYear(2001);
		pi.setInfoID(1L);
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitializationMySQL#add(edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitialization)}.
	 */
	@Test
	public void testAdd() {
		try {
			assertTrue(sql.add(oi));
		} catch (DBException e) {
			fail("DBException");
			e.printStackTrace();
		} catch (FormValidationException e) {
			fail("FormValidationException");
			e.printStackTrace();
		}
		try {
			assertTrue(sql.add(pi));
		} catch (DBException e) {
			fail("DBException");
			e.printStackTrace();
		} catch (FormValidationException e) {
			fail("FormValidationException");
			e.printStackTrace();
		}
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitializationMySQL#addReturnGeneratedId(edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitialization)}.
	 */
	@Test
	public void testAddReturnGeneratedId() {
		Long id = null;
		try {
			id = sql.addReturnGeneratedId(oi);
		} catch (DBException e) {
			fail("DBException");
			e.printStackTrace();
		}
		assertTrue(id != null);
	}

	@Test
	public void testAddReturnGeneratedIdPreg() {
		Long id = null;
		try {
			id = sql.addReturnGeneratedId(pi);
		} catch (DBException e) {
			fail("DBException");
			e.printStackTrace();
		}
		assertTrue(id != null);
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitializationMySQL#getAll()}.
	 */
	@Test
	public void testGetAll() {
		List<ObstetricsInitialization> list = null;
		try {
			list = sql.getAll();
		} catch (DBException e) {
			fail("DBException");
			e.printStackTrace();
		}
		assertTrue(list != null);
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitializationMySQL#getByID(long)}.
	 */
	@Test
	public void testGetByID() {
		ObstetricsInitialization x = null;
		Long id = null;
		try {
			id = sql.addReturnGeneratedId(oi);
		} catch (DBException e) {
			fail("DBException");
			e.printStackTrace();
		}
		try {
			x = sql.getByID(id);
		} catch (DBException e) {
			fail("DBException");
			e.printStackTrace();
		}
		assertTrue(x != null);
		assertEquals(id, x.getVisitID());
		assertEquals(oi.getDate(), x.getDate());
		assertEquals(oi.getPatientMID(), x.getPatientMID());
		assertEquals(oi.getLMP(), x.getLMP());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitializationMySQL#getPrevPregForPatient(java.lang.Long)}.
	 */
	@Test
	public void testGetPrevPregForPatient() {
		List<PreviousPregnancyInfo> list = null;
		try {
			list = sql.getPrevPregForPatient(oi.getPatientMID());
		} catch (DBException e) {
			fail("DBException");
			e.printStackTrace();
		}
		assertTrue(list != null);
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitializationMySQL#getVisitsForPatient(java.lang.Long)}.
	 */
	@Test
	public void testGetVisitsForPatient() {
		List<ObstetricsInitialization> list = null;
		try {
			list = sql.getVisitsForPatient(oi.getPatientMID());
		} catch (DBException e) {
			fail("DBException");
			e.printStackTrace();
		}
		assertTrue(list != null);
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitializationMySQL#ObstetricsInitializationMySQL()}.
	 */
	@Test
	public void testObstetricsInitializationMySQL() {
		ObstetricsInitializationMySQL sql = null;
		try {
			sql = new ObstetricsInitializationMySQL();
		} catch (DBException e) {
			e.printStackTrace();
		}
		assertTrue(sql == null);
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitializationMySQL#ObstetricsInitializationMySQL(javax.sql.DataSource)}.
	 */
	@Test
	public void testObstetricsInitializationMySQLDataSource() {
		assertTrue(sql != null);
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitializationMySQL#update(edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitialization)}.
	 */
	@Test
	public void testUpdate() {
//		oi.setVisitID(1L);
//		try {
//			sql.update(oi);
//		} catch (DBException e) {
//			fail("DBException");
//			e.printStackTrace();
//		} catch (FormValidationException e) {
//			fail("FormValidationException");
//			e.printStackTrace();
//		}
	}

}
