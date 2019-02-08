package edu.ncsu.csc.itrust.unit.controller.healthTracking;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import edu.ncsu.csc.itrust.controller.healthTracking.HealthTrackerController;
import edu.ncsu.csc.itrust.controller.officeVisit.OfficeVisitController;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.healthTracking.HealthTracker;
import edu.ncsu.csc.itrust.model.healthTracking.HealthTrackerMySQL;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.webutils.SessionUtils;
import junit.framework.TestCase;

public class HealthTrackerControllerTest extends TestCase {

	@Spy private HealthTrackerController htc;
	@Spy private HealthTrackerController htcWithNullDataSource;
	@Spy private SessionUtils sessionUtils;
	
	@Mock private HttpServletRequest mockHttpServletRequest;
	@Mock private HttpSession mockHttpSession;
	@Mock private SessionUtils mockSessionUtils;
	
	TestDataGenerator gen;
	DataSource ds;
	
	@Before
    public void setUp() throws FileNotFoundException, SQLException, IOException {
		ds = ConverterDAO.getDataSource();
		gen = new TestDataGenerator();
		mockSessionUtils = Mockito.mock(SessionUtils.class);
		htc = Mockito.spy(new HealthTrackerController(ds, mockSessionUtils));
		Mockito.doNothing().when(htc).printFacesMessage(Matchers.any(FacesMessage.Severity.class), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString());
		htcWithNullDataSource = new HealthTrackerController(null, mockSessionUtils);
     }
	
	@Test
	public void testAddValidValuesReturnGeneratedID() {
		HealthTracker ht = new HealthTracker();
		HealthTrackerController controller = new HealthTrackerController(ds);
		ht.setDate(new Date());
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
		ht.setUVExposure(0);
		Assert.assertTrue(controller.addReturnGeneratedId(ht) > 0);
		htc.close();
	}
	
	@Test
	public void testEdit() {
		HealthTracker ht = new HealthTracker();
		HealthTrackerController controller = new HealthTrackerController(ds);
		ht.setDate(new Date());
		ht.setCaloriesBurned(0);
		ht.setSteps(0);
		ht.setDistance(0.00);
		ht.setFloors(2);
		ht.setMinutesSedentary(0);
		ht.setMinutesLightlyActive(0);
		ht.setMinutesFairlyActive(0);
		ht.setMinutesVeryActive(0);
		ht.setActivityCalories(0);
		ht.setHrLowest(0);
		ht.setHrHighest(0);
		ht.setHrAverage(0);
		ht.setActiveHours(0);
		ht.setUVExposure(0);
		controller.edit(ht);
		htc.close();
	}
}
