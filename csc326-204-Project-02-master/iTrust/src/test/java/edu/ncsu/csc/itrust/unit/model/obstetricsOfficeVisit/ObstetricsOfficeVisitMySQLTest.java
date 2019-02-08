package edu.ncsu.csc.itrust.unit.model.obstetricsOfficeVisit;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;

import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.ObstetricsOfficeVisit;
import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.ObstetricsOfficeVisitMySQL;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import junit.framework.TestCase;

public class ObstetricsOfficeVisitMySQLTest extends TestCase{
	private DataSource ds;
	private ObstetricsOfficeVisitMySQL ovsql;
	private TestDataGenerator gen;
	
	@Override
	public void setUp() throws Exception {
		ds = ConverterDAO.getDataSource();
		ovsql = new ObstetricsOfficeVisitMySQL(ds);
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.uc94();
	}
	
	@Test
	public void testGetVisitsForPatient() throws Exception {
		List<ObstetricsOfficeVisit> list101 = ovsql.getVisitsForPatient(101L);
		assertEquals(0, list101.size());
		ovsql.close();
	}
	
	@Test
	public void testGetPatientDOB() throws Exception {
		
		Date invalidPatient106DOB = ovsql.getPatientDOB(101L);
		assertNull(invalidPatient106DOB);
		
		Date invalidPatient107DOB = ovsql.getPatientDOB(101L);
		assertNull(invalidPatient107DOB);
		ovsql.close();
	}

	public void testGetPatientLMP(){
		Date patient = ovsql.getPatientLMP(101L);
		assertNull(patient);
		ovsql.close();
	}
}
