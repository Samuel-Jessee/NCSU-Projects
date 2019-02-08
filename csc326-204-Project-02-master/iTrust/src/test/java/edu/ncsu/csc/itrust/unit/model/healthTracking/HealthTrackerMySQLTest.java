package edu.ncsu.csc.itrust.unit.model.healthTracking;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.healthTracking.HealthTracker;
import edu.ncsu.csc.itrust.model.healthTracking.HealthTrackerData;
import edu.ncsu.csc.itrust.model.healthTracking.HealthTrackerMySQL;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import junit.framework.TestCase;

public class HealthTrackerMySQLTest extends TestCase {
	
	private DataSource ds;
	private TestDataGenerator gen;
	private HealthTrackerMySQL healthTrackerData;
	private HealthTracker testht;
	private Date date;
	
	@Override
	public void setUp() throws DBException, FileNotFoundException, IOException, SQLException {
		ds = ConverterDAO.getDataSource();
		gen = new TestDataGenerator();
		
		healthTrackerData = new HealthTrackerMySQL(ds);
		
		date = new Date();
		
		testht = new HealthTracker();
		testht.setDate(date);
		testht.setHCP(9000000000L);
		testht.setPatient(1L);
		testht.setCaloriesBurned(1);
		testht.setSteps(2);
		testht.setDistance(3.0);
		testht.setFloors(4);
		testht.setMinutesSedentary(5);
		testht.setMinutesLightlyActive(6);
		testht.setMinutesFairlyActive(7);
		testht.setMinutesVeryActive(8);
		testht.setActivityCalories(9);
		testht.setHrLowest(10);
		testht.setHrHighest(11);
		testht.setHrAverage(12);
		testht.setActiveHours(13);
		testht.setUVExposure(14);
	}
	
	@Override
	protected void tearDown() throws Exception {
		gen.clearAllTables();
	}

	
	public void testGetDataForPatient() {
		try {
			gen.patient1();
			assertTrue(healthTrackerData.add(testht));
			List<HealthTracker> hts = healthTrackerData.getDataForPatient(1L);
			assertTrue(hts.get(0).getCaloriesBurned() == testht.getCaloriesBurned());
		} catch (Exception e) {
			fail();
		}
		healthTrackerData.close();
	}
	
	public void testGetByID() {
		try {
			gen.patient1();
			Long id = healthTrackerData.addReturnGeneratedId(testht);
			testht.setTrackerID(id);
			HealthTracker result = healthTrackerData.getByID(id);
			assertTrue(result.getActivityCalories() == testht.getActivityCalories());
		} catch (Exception e) {
			fail();
		}
		healthTrackerData.close();
	}
	
	public void testDateExists() {
		try {
			gen.patient1();
			healthTrackerData.addReturnGeneratedId(testht);
			assertTrue(healthTrackerData.dateExists(1L, 9000000000L, date) > -1);
		} catch (Exception e) {
			fail();
		}
		healthTrackerData.close();
	}
	
	public void testGetAll() {
		try {
			gen.patient1();
			assertTrue(healthTrackerData.add(testht));
			List<HealthTracker> hts = healthTrackerData.getAll();
			assertTrue(hts.get(0).getFloors() == testht.getFloors());
		} catch (Exception e) {
			fail();
		}
		healthTrackerData.close();
	}
	
	public void testUpdate() {
		try {
			gen.patient1();
			long trackerID = healthTrackerData.addReturnGeneratedId(testht);
			testht.setTrackerID(trackerID);
			HealthTracker testht2 = testht;
			testht2.setCaloriesBurned(300);
			assertTrue(healthTrackerData.update(testht2));
			List<HealthTracker> hts = healthTrackerData.getAll();
			assertTrue(hts.get(0).getCaloriesBurned() == testht2.getCaloriesBurned());
		} catch (Exception e) {
			fail();
		}
		healthTrackerData.close();
	}
}
