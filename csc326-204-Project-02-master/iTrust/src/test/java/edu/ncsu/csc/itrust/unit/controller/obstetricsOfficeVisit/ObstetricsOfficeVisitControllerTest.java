package edu.ncsu.csc.itrust.unit.controller.obstetricsOfficeVisit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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

import edu.ncsu.csc.itrust.controller.obstetricsOfficeVisit.ObstetricsOfficeVisitController;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.ObstetricsOfficeVisit;
import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.ObstetricsOfficeVisitData;
import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.ObstetricsOfficeVisitMySQL;
import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.Ultrasound;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.webutils.SessionUtils;
import junit.framework.TestCase;

public class ObstetricsOfficeVisitControllerTest extends TestCase {

	@Spy private ObstetricsOfficeVisitController ovc;
	@Spy private ObstetricsOfficeVisitController ovcWithNullDataSource;
	@Spy private ObstetricsOfficeVisitController ovcWithNoParams;
	@Spy private SessionUtils sessionUtils;
	
	@Mock private HttpServletRequest mockHttpServletRequest;
	@Mock private HttpSession mockHttpSession;
	@Mock private SessionUtils mockSessionUtils;
	
	private ObstetricsOfficeVisitData ovData;
	private DataSource ds;
	private TestDataGenerator gen;
	
	private ObstetricsOfficeVisit testOV;
	private Ultrasound testUS;
	private Date date;
	private Date birthDate;
	
	@Before
	public void setUp() {
		ds = ConverterDAO.getDataSource();
		mockSessionUtils = Mockito.mock(SessionUtils.class);
		ovc = Mockito.spy(new ObstetricsOfficeVisitController(ds, mockSessionUtils));
		Mockito.doNothing().when(ovc).printFacesMessage(Matchers.any(FacesMessage.Severity.class), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString());
		ovData = new ObstetricsOfficeVisitMySQL(ds);
		
		gen = new TestDataGenerator();
		try {
			gen.clearAllTables();
			gen.hcp0();
			gen.hcp3();
			gen.patient1();
			gen.patient2();
			gen.patient3();
		} catch (SQLException | IOException e) {
		}
		
		date = new Date();
		birthDate = ovc.getPatientDOB(1);
		
		// Create Test Ultrasound
		testUS = new Ultrasound();
		testUS.setAC(10.0);
		testUS.setBPD(10.1);
		testUS.setCRL(10.2);
		testUS.setEFW(10.3);
		testUS.setFL(10.4);
		testUS.setHC(10.5);
		testUS.setHL(10.6);
		testUS.setOFD(10.7);
		testUS.setPatientMID(1L);
		testUS.setUltraID(123456789L);
		ArrayList<Ultrasound> uSList = new ArrayList<Ultrasound>();
		uSList.add(testUS);
		
		// Create Test Obstetrics Office Visit
		testOV = new ObstetricsOfficeVisit();
		testOV.setBloodPressure("110/70");
		testOV.setDate(date);
		testOV.setFHR(80);
		testOV.setLowLyingPlacenta(true);
		testOV.setNumFetus(2);
		testOV.setNumWeeks(29);
		testOV.setPatientMID(1L);
		testOV.setUltrasounds(uSList);
		testOV.setVisitID(123456789L);
		testOV.setWeight(180.5);
		
		// Initialize an obstetrics office visit controller with null data source
		ovcWithNullDataSource = new ObstetricsOfficeVisitController(null, sessionUtils);
		
		try {
			ovcWithNoParams = new ObstetricsOfficeVisitController();
		} catch (DBException e) {
			
		}

		// Mock HttpServletRequest
		mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);

		// Mock HttpSession
		mockHttpSession = Mockito.mock(HttpSession.class);
	}
	
	@Test
	public void testRetrieveOfficeVisit() throws DBException {
		long visitID = ovc.addReturnGeneratedId(testOV);
		Assert.assertTrue("Office visit should be added successfully", visitID > 0);
		ObstetricsOfficeVisit check = ovc.getVisitByID(Long.toString(visitID));
		assertEquals(testOV.getBloodPressure(), check.getBloodPressure());
		assertEquals(testOV.getFHR(), check.getFHR());
		assertEquals(testOV.isLowLyingPlacenta(), check.isLowLyingPlacenta());
		assertEquals(testOV.getNumFetus(), check.getNumFetus());
		assertEquals(testOV.getNumWeeks(), check.getNumWeeks());
		assertEquals(testOV.getPatientMID(), check.getPatientMID());
		assertEquals(testOV.getWeight(), check.getWeight());

		testOV.setVisitID(visitID);
		testOV.setBloodPressure("120/70");

		ovc.edit(testOV);
		ovc.close();
	}
	
	@Test
	public void testAddObstetricsOfficeVisitWithInvalidDate() throws DBException {
		Date testDate = birthDate;
		testDate.setYear(birthDate.getYear()-1);
		testOV.setDate(testDate);
		Assert.assertFalse("Office Visit date cannot be set prior to patient birthday", ovc.addReturnGeneratedId(testOV) > 0);
		ovc.close();
	}
	
	@Test
	public void testAddObstetricsOfficeVisitWithFacesContext() throws DBException {
		ovc.add(testOV);
		Mockito.verify(ovc).printFacesMessage(Mockito.eq(FacesMessage.SEVERITY_INFO), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString());
		ovc.close();
	}

	@Test
	public void testGetOfficeVisitsForPatient() {
		ovc.add(testOV);
		Assert.assertEquals(1, ovc.getObstetricsVisitForPatient(Long.toString(1L)).size());
		Assert.assertEquals(0, ovc.getObstetricsVisitForPatient(Long.toString(2L)).size());
		Assert.assertEquals(0, ovc.getObstetricsVisitForPatient(Long.toString(3L)).size());
		ovc.close();
	}
	
	@Test
	public void testGetOfficeVisitsForPatientWithException() {
		Assert.assertEquals(0,
				ovcWithNullDataSource.getObstetricsVisitForPatient(Long.toString(1L)).size());
		ovc.close();
	}
	
	@Test
	public void testGetOfficeVisitsForPatientWithNullPid() {
		Assert.assertEquals(0, ovc.getObstetricsVisitForPatient(null).size());
		ovc.close();
	}
	
	@Test
	public void testGetOfficeVisitsForPatientWithHCPPid() {
		Assert.assertEquals(0, ovc.getObstetricsVisitForPatient(Long.toString(900000000L)).size());
		ovc.close();
	}
	
	@Test
	public void testGetOfficeVisitsForCurrentPatient() {
		ovc.add(testOV);
		Mockito.doReturn("1").when(mockSessionUtils).getCurrentPatientMID();
		Assert.assertEquals(1, ovc.getObstetricsVisitsForCurrentPatient().size());
		Mockito.doReturn("2").when(mockSessionUtils).getCurrentPatientMID();
		Assert.assertEquals(0, ovc.getObstetricsVisitsForCurrentPatient().size());
		ovc.close();
	}
	
	@Test
	public void testGetOfficeVisitsForCurrentPatientWithInvalidMID() {
		Mockito.doReturn(900000000L).when(mockSessionUtils).getCurrentPatientMIDLong();
		Assert.assertEquals(0, ovc.getObstetricsVisitsForCurrentPatient().size());
		Mockito.doReturn(-1L).when(mockSessionUtils).getCurrentPatientMIDLong();
		Assert.assertEquals(0, ovc.getObstetricsVisitsForCurrentPatient().size());
		Mockito.doReturn(null).when(mockSessionUtils).getCurrentPatientMIDLong();
		Assert.assertEquals(0, ovc.getObstetricsVisitsForCurrentPatient().size());
		ovc.close();
	}
	
	@Test
	public void testGetVisitByIDWithInvalidID() {
		Assert.assertNull(ovc.getVisitByID("invalid id"));
		Assert.assertNull(ovc.getVisitByID("-1"));
		ovc.close();
	}
	
	@Test
	public void testGetVisitByUltraIDWithInvalidID() {
		Assert.assertNull(ovc.getVisitByUltraID("invalid id"));
		Assert.assertNull(ovc.getVisitByUltraID("-1"));
		ovc.close();
	}
	
	@Test
	public void testisOBGYN() {
		Mockito.doReturn(900000000L).when(mockSessionUtils).getSessionLoggedInMIDLong();
		Assert.assertFalse(ovc.isOBGYN());
		ovc.close();
	}
	
	@Test
	public void testHasPatientVisited() {
		ovc.add(testOV);
		Assert.assertTrue(ovc.hasPatientVisited("1"));
		Assert.assertFalse(ovc.hasPatientVisited("2"));
		ovc.close();
	}
	
	@Test
	public void testHasPatientVisitedWithNulls() {
		Assert.assertFalse(ovc.hasPatientVisited(null));
		Assert.assertFalse(ovc.hasPatientVisited("-1"));
		ovc.close();
	}
	
	@Test
	public void testHasCurrentPatientVisited() {
		final Long MID = 1L;
		final String PATIENT = "patient";

		Mockito.doReturn(PATIENT).when(mockSessionUtils).getSessionUserRole();
		Mockito.doReturn(MID).when(mockSessionUtils).getCurrentPatientMIDLong();

		Assert.assertFalse(ovc.hasCurrentPatientVisited());
		ovc.close();
	}
	
	@Test
	public void testAddReturnGeneratedId() {
		long id = ovc.addReturnGeneratedId(testOV);
		Assert.assertTrue(id >= 0);
		Mockito.verify(ovc).printFacesMessage(Mockito.eq(FacesMessage.SEVERITY_INFO), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString());
		ovc.close();
	}
	
	@Test
	public void testAddReturnGeneratedUltraId() {
		long id = ovc.addReturnGeneratedId(testUS);
		Assert.assertTrue(id >= 0);
		Mockito.verify(ovc).printFacesMessage(Mockito.eq(FacesMessage.SEVERITY_INFO), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString());
		ovc.close();
	}
	
	@Test
	public void testLogViewOfficeVisit() {
		Mockito.when(mockSessionUtils.getCurrentOfficeVisitId()).thenReturn(1L);
		ovc.setSessionUtils(mockSessionUtils);
		Mockito.doNothing().when(ovc).logTransaction(Mockito.any(), Mockito.anyString());
		ovc.logViewBasicHealthInformation();
		Mockito.verify(ovc, Mockito.times(1)).logTransaction(Mockito.any(), Mockito.anyString());
		ovc.close();
	}
	
	@Test
	public void testLogViewOfficeVisitNoneSelected() {
		Mockito.when(mockSessionUtils.getCurrentOfficeVisitId()).thenReturn(null);
		ovc.setSessionUtils(mockSessionUtils);
		ovc.logViewBasicHealthInformation();
		Mockito.verify(ovc, Mockito.times(0)).logTransaction(TransactionType.OFFICE_VISIT_VIEW, new Long(2).toString());
		ovc.close();
	}
	
	@Test
	public void testCalcuateCurrentPatientWeeksPregnant() {
		Mockito.doReturn(1L).when(mockSessionUtils).getCurrentPatientMIDLong();
		ovc.add(testOV);
		ovc.calculateCurrentPatientWeeksPregnant();
		ovc.close();
	}
	
	@Test
	public void testIsCurrentPatientValid() {
		Mockito.doReturn(1L).when(mockSessionUtils).getCurrentPatientMIDLong();
		Assert.assertTrue(ovc.isCurrentPatientValid());
		Mockito.doReturn(2L).when(mockSessionUtils).getCurrentPatientMIDLong();
		Assert.assertFalse(ovc.isCurrentPatientValid());
		ovc.close();
	}
}
