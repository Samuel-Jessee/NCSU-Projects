package edu.ncsu.csc.itrust.unit.controller.healthTracking;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.primefaces.event.SelectEvent;

import edu.ncsu.csc.itrust.controller.healthTracking.HealthTrackerController;
import edu.ncsu.csc.itrust.controller.healthTracking.HealthTrackerForm;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.webutils.SessionUtils;
import junit.framework.TestCase;

public class HealthTrackerFormTest extends TestCase {

	TestDataGenerator gen;
	DataSource ds;
	HealthTrackerForm ht;
	Date date;
	
	@Mock private HttpServletRequest mockHttpServletRequest;
	@Mock private HttpSession mockHttpSession;
	@Mock private SessionUtils mockSessionUtils;
	
	@Override
    public void setUp() throws FileNotFoundException, SQLException, IOException{
		ds = ConverterDAO.getDataSource();
		HealthTrackerController controller = new HealthTrackerController(ds);
        gen = new TestDataGenerator();
        gen.clearAllTables();
        date = new Date();
        ht = new HealthTrackerForm(controller);
        ht.init();
        ht.setDate(date);
		ht.setCaloriesBurned(0);
		ht.setSteps(0);
		ht.setDistance(0.00);
		ht.setFloors(0);
		ht.setMinutesSedentary(0);
		ht.setMinutesLightlyActive(0);
		ht.setMinutesFairlyActive(0);
		ht.setMinutesVeryActive(0);
		ht.setActivityCalories(0);
		ht.setHrLowest(0);
		ht.setHrHighest(0);
		ht.setHrAverage(0);
		ht.setActiveHours(0);
		ht.setUvExposure(0);
		ht.setPatient(0);
		ht.setHCP(0);
		ht.setTrackerID((long) 0);
		ht.close();
    }
	
	@Test
	public void testGetters() {
		assertTrue(ht.getDate().equals(date));
		assertTrue(ht.getCaloriesBurned() == 0);
		assertTrue(ht.getSteps() == 0);
		assertTrue(ht.getDistance() == 0);
		assertTrue(ht.getFloors() == 0);
		assertTrue(ht.getMinutesSedentary() == 0);
		assertTrue(ht.getMinutesLightlyActive() == 0);
		assertTrue(ht.getMinutesFairlyActive() == 0);
		assertTrue(ht.getMinutesVeryActive() == 0);
		assertTrue(ht.getActivityCalories() == 0);
		assertTrue(ht.getHrLowest() == 0);
		assertTrue(ht.getHrHighest() == 0);
		assertTrue(ht.getHrAverage() == 0);
		assertTrue(ht.getActiveHours() == 0);
		assertTrue(ht.getUvExposure() == 0);
		assertTrue(ht.getPatient() == 0);
		assertTrue(ht.getHCP() == 0);
		assertTrue(ht.getTrackerID() == 0);
		ht.close();
	}
	
	@Test
	public void testSubmit() {
		try {
			ht.submit();
		} catch (DBException e) {

		}
		ht.close();
	}
	
	@Test
	public void testImportFitBit() {
		ht.importCSV("testing-files/fitbit_export_HW3.csv"); 
		ht.close();
	}
	
	@Test
	public void testImportMSBand() {
		ht.importCSV("testing-files/MS_Band_Data.csv"); 
		ht.close();
	}
}
