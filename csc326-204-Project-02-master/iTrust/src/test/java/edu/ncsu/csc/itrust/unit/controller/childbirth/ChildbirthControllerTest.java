package edu.ncsu.csc.itrust.unit.controller.childbirth;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import edu.ncsu.csc.itrust.controller.childbirth.ChildbirthController;
import edu.ncsu.csc.itrust.controller.emergencyRecord.EmergencyRecordController;
import edu.ncsu.csc.itrust.controller.obstetricsInitialization.ObstetricsInitializationController;
import edu.ncsu.csc.itrust.controller.obstetricsOfficeVisit.ObstetricsOfficeVisitController;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.childbirth.ChildInChildbirth;
import edu.ncsu.csc.itrust.model.childbirth.Childbirth;
import edu.ncsu.csc.itrust.model.childbirth.ChildbirthData;
import edu.ncsu.csc.itrust.model.childbirth.ChildbirthMySQL;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitialization;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitializationData;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitializationMySQL;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.PreviousPregnancyInfo;
import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.ObstetricsOfficeVisit;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AllergyDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.webutils.SessionUtils;
import junit.framework.Assert;

public class ChildbirthControllerTest {
    TestDataGenerator gen;
    DataSource ds;
    @Mock private SessionUtils mockSessionUtils;
    ChildbirthController c;
    private ChildbirthData cbData;
    private Childbirth testCb;
    
    @Mock private HttpServletRequest mockHttpServletRequest;
	@Mock private HttpSession mockHttpSession;
	private ChildbirthController cbcWithNullDataSource;
	private SessionUtils sessionUtils = SessionUtils.getInstance();
	private ChildbirthController cbcWithNoParams;

	
    @Before
    public void setUp() throws FileNotFoundException, SQLException, IOException, DBException{
    	ds = ConverterDAO.getDataSource();

		mockSessionUtils = Mockito.mock(SessionUtils.class);
		c = Mockito.spy(new ChildbirthController(ds, mockSessionUtils));
		Mockito.doNothing().when(c).printFacesMessage(Matchers.any(FacesMessage.Severity.class), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString());

		cbData = new ChildbirthMySQL(ds);
		
		// remove when these modules are built and can be called
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.hcp0();
		gen.hcp3();
		gen.patient1();
		gen.patient2();
		gen.patient3();
		gen.uc96();
		gen.uc63();
		gen.uc94();

		// Setup test 
		testCb = new Childbirth();
        testCb.setPatientMID(1L);
        testCb.setDate(LocalDateTime.now());
        testCb.setHoursInLabor(1);
        testCb.setChildrenIDs("1534");
    	ChildInChildbirth child = new ChildInChildbirth();
    	child.setApproximate(false);
    	LocalDateTime time = LocalDateTime.now();
    	child.setDate(time);
    	child.setChildID(14L);
    	child.setGender("Male");
    	child.setMid(1534L);
    	ArrayList<ChildInChildbirth> children = new ArrayList<ChildInChildbirth>();
    	children.add(child);
    	testCb.setChildbirthchildren(children);
    	testCb.setEpiduralAnaesthesia(1.2);
    	testCb.setHoursInLabor(2);
    	testCb.setMagnesiumSulfate(3.4);
    	testCb.setMethod("Vaginal Delivery");
    	testCb.setNitrousOxide(0.7);
    	testCb.setPatientMID(1L);
    	testCb.setPethidine(7.8);
    	testCb.setPitocin(1.3);
    	testCb.setRhimmuneglobulin(8.1);
    	testCb.setDate(time);
    	testCb.setVisitID(1L);
    	
    	

		// Initialize a obstetrics Initialization controller with null data source
		cbcWithNullDataSource = new ChildbirthController(null, sessionUtils);

		try {
			cbcWithNoParams = new ChildbirthController();
		} catch (DBException e) {
			
		}
		// Mock HttpServletRequest
		mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);

		// Mock HttpSession
		mockHttpSession = Mockito.mock(HttpSession.class);
    }
    
    @Test
    public void testConstructors()  {
    	ChildbirthController controller = new ChildbirthController(ds);
    	Assert.assertNotNull(controller);
    	SessionUtils su = SessionUtils.getInstance();
    	ChildbirthController controller2 = new ChildbirthController(ds, su);
    	Assert.assertNotNull(controller2);
    	try {
			ChildbirthController controller3 = new ChildbirthController();
		} catch (DBException e) {
			// TODO Auto-generated catch block
		}
    	c.close();
    }
    
    @Test
    public void testAddReturnResult() throws DBException  {
        when(mockSessionUtils.getSessionLoggedInMID()).thenReturn("9000000012");
        when(mockSessionUtils.getCurrentPatientMID()).thenReturn("1L");
        when(mockSessionUtils.getCurrentOfficeVisitId()).thenReturn(100L);
        mockSessionUtils.setSessionVariable("loggedinMID", 9000000012L);
        c.createEmergencyAppointment();
        c.addReturnResult(testCb);
    	c.addReturnGeneratedID(testCb);
    	c.CurrentPatientHasVisited();
   	
		long visitID = c.addReturnGeneratedID(testCb);
		System.out.println(visitID);
    	Assert.assertFalse(c.CurrentPatientHasVisited());
    	c.getAll();
    	c.getByID((long)1);
    	c.getChildbirthsForCurrentPatient();
    	c.getChildbirthsForPatient((long)1);
    	c.addReturnResult(testCb);
    	c.getSelectedVisit();
    	try {
    	    c.isChildbirthAppointment();
    	} catch (NullPointerException e) {
    		//nothing
    	}
    
    	c.isNewVisit();
    	c.update(testCb);
    	c.close();
    }
    
    @Test
    public void testGettersSetters() throws DBException, FileNotFoundException, SQLException, IOException {
    	c.setPrefMethod("Vaginal Delivery");
    	Assert.assertTrue(c.getPrefMethod().equals("Vaginal Delivery"));
    	c.setCbAppt(null);
    	Assert.assertNull(c.getCbAppt());
    	gen.clearAllTables();
    	c.update(testCb);
    	when(mockSessionUtils.getCurrentPatientMIDLong()).thenReturn(null);
    	Assert.assertFalse(c.CurrentPatientHasVisited());
    	Assert.assertFalse(c.isChildbirthAppointment());
    	c.close();
    }
    
    @Test
    public void testGetById() throws DBException {
    	Childbirth test = cbData.getByID(1L);
    	Assert.assertNotNull(test);
    	Assert.assertTrue(test.getNitrousOxide().equals(2.7));
    	c.close();
    }
    
    @Test
    public void testCreateEmergencyAppointment() {
    	c.createEmergencyAppointment();
    	c.close();
    }
    
    @Test
    public void testCurrentPatientHasVisited() {
    	when(mockSessionUtils.getCurrentPatientMIDLong()).thenReturn(null);
    	Assert.assertFalse(c.CurrentPatientHasVisited());
    	when(mockSessionUtils.getCurrentPatientMID()).thenReturn("1L");
    	Assert.assertFalse(c.CurrentPatientHasVisited());
    	c.close();
    }
    
    @Test
    public void testIsChildbirthAppointment() {
    	when(mockSessionUtils.getCurrentPatientMIDLong()).thenReturn(null);
    	Assert.assertFalse(c.isChildbirthAppointment());
    	when(mockSessionUtils.getCurrentPatientMID()).thenReturn("1L");
    	Assert.assertFalse(c.isChildbirthAppointment());
    	c.close();
    }
    
    @Test
    public void testGetChildbirthsForPatient() {
    	List<Childbirth> all = c.getChildbirthsForPatient(1L);
    	Assert.assertTrue(all.get(0).getNitrousOxide().equals(2.7));
    	try {
			all = cbData.getAll();
		} catch (DBException e) {
			// nothing
		}
    	c.close();
    }
    
    @Test
    public void testIsNewVisit() {
    	when(mockSessionUtils.getCurrentPatientMIDLong()).thenReturn(null);
    	Assert.assertTrue(c.isNewVisit());
    	when(mockSessionUtils.getCurrentPatientMID()).thenReturn("1L");
    	Assert.assertTrue(c.isNewVisit());
    	c.close();
    }
}
