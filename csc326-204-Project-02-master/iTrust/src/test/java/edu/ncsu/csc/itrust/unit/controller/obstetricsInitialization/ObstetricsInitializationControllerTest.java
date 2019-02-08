package edu.ncsu.csc.itrust.unit.controller.obstetricsInitialization;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

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

import edu.ncsu.csc.itrust.controller.obstetricsInitialization.ObstetricsInitializationController;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitialization;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitializationData;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitializationMySQL;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.PreviousPregnancyInfo;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.webutils.SessionUtils;
import junit.framework.TestCase;

public class ObstetricsInitializationControllerTest extends TestCase {
	
	private static final long DEFAULT_PATIENT_MID = 1L;
	//UPDATE TO HCP WITH OBS
	private static final long DEFAULT_HCP_MID = 900000000L;

	@Spy private ObstetricsInitializationController oic;
	@Spy private ObstetricsInitializationController oicWithNullDataSource;
	@Spy private SessionUtils sessionUtils;
	
	@Mock private HttpServletRequest mockHttpServletRequest;
	@Mock private HttpSession mockHttpSession;
	@Mock private SessionUtils mockSessionUtils;
	
	private ObstetricsInitializationData oiData;
	private DataSource ds;
	private TestDataGenerator gen; // remove when ApptType, Patient, and other
									// files are finished
	private ObstetricsInitialization testOI;
	private PreviousPregnancyInfo testPreg;
	
	@Before
	public void setUp() throws Exception {
		ds = ConverterDAO.getDataSource();
		mockSessionUtils = Mockito.mock(SessionUtils.class);
		oic = Mockito.spy(new ObstetricsInitializationController(ds, mockSessionUtils));
		Mockito.doNothing().when(oic).printFacesMessage(Matchers.any(FacesMessage.Severity.class), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString());
		Mockito.doNothing().when(oic).redirectToBaseObstetricsInitialization();
		oiData = new ObstetricsInitializationMySQL(ds);
		
		// remove when these modules are built and can be called
		gen = new TestDataGenerator();
		gen.uc93();

		// Setup test 
		testOI = new ObstetricsInitialization();
		testOI.setPatientMID(DEFAULT_PATIENT_MID);
		testOI.setDate(LocalDateTime.now());
		LocalDate lmp = LocalDate.of(2010, 07, 10);
		testOI.setLMP(lmp);
		testPreg = new PreviousPregnancyInfo();
		testPreg.setPatientMID(DEFAULT_PATIENT_MID);
		testPreg.setAmtChildren(2);
		testPreg.setDeliveryType(1);
		testPreg.setHoursInLabor(3);
		testPreg.setWeeksPreg(30);
		testPreg.setWeightGained(60);
		testPreg.setYear(2009);

		// Initialize a obstetrics Initialization controller with null data source
		oicWithNullDataSource = new ObstetricsInitializationController(null, sessionUtils);

		// Mock HttpServletRequest
		mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);

		// Mock HttpSession
		mockHttpSession = Mockito.mock(HttpSession.class);
	}
	
	@Test
	public void testRetrieveObstetricsInitialization() throws DBException {
		// Get the visit ID from the DB
		List<ObstetricsInitialization> all = oiData.getAll();
		long visitID = -1;
		ObstetricsInitialization ovI = all.get(0);
		boolean bEDD = ovI.getEDD().equals(testOI.getEDD());
		boolean bLMP = ovI.getLMP().equals(testOI.getLMP());
		boolean bDate = false;
		long time = ChronoUnit.MINUTES.between(testOI.getDate(), ovI.getDate());
		bDate = (time < 1);
		if (bEDD && bDate && bLMP) {
			visitID = ovI.getVisitID();
		}
		Assert.assertNotEquals(-1L, visitID);
		ObstetricsInitialization check = oic.getVisitByID(Long.toString(visitID));
		Assert.assertEquals(testOI.getEDD(), check.getEDD());
		long dif = ChronoUnit.MINUTES.between(testOI.getDate(), check.getDate());
		Assert.assertTrue(dif < 1);
		Assert.assertEquals(testOI.getLMP(), check.getLMP());
		
		testOI.setVisitID(visitID);
		
		oic.edit(testOI);
        oic.close();
	}
	
	@Test
	public void testRetrievePreviousPreg() throws DBException {
		// Get the visit ID from the DB
		List<PreviousPregnancyInfo> all = oiData.getPrevPregForPatient(1L);
		List<PreviousPregnancyInfo> all2 = oic.getPreviousPregnancyInfoForCurrentPatient();
		List<PreviousPregnancyInfo> all3 = oic.getPreviousPregnancyInfoForPatient("1");
		boolean btest = oic.doesCurrentPatientHavePreviousPregnancy();
		boolean btest2 = oic.doesPatientHavePreviousPregnancy("1");
		long visitID = -1;
		PreviousPregnancyInfo preg = all.get(0);
		boolean bchild = preg.getAmtChildren().equals(testPreg.getAmtChildren()); 
		boolean bDeliv = preg.getDeliveryType().equals(testPreg.getDeliveryType());
		boolean bHours = preg.getHoursInLabor().equals(testPreg.getHoursInLabor());
		boolean bweeks = preg.getWeeksPreg().equals(testPreg.getWeeksPreg());
		boolean bweight = preg.getWeightGained().equals(testPreg.getWeightGained());
		boolean byear = preg.getYear().equals(testPreg.getYear());
		if (bchild && bDeliv && bHours && bweeks && bweight && bweight && byear) {
			visitID = preg.getInfoID();
		}
		Assert.assertNotEquals(-1L, visitID);
		oic.close();

	}

	@Test
	public void testGetObstetricsInitializationsForPatient() {
		//Assert.assertEquals(1, oic.getObstetricsInitializationForPatient(Long.toString(DEFAULT_PATIENT_MID)).size());
	}

	@Test
	public void testGetObstetricsInitializationsForPatientWithException() {
		Assert.assertEquals(0,
				oicWithNullDataSource.getObstetricsInitializationForPatient(Long.toString(DEFAULT_PATIENT_MID)).size());
	}

	@Test
	public void testGetObstetricsInitializationsForPatientWithNullPid() {
		Assert.assertEquals(0, oic.getObstetricsInitializationForPatient(null).size());
		oic.close();
	}

	@Test
	public void testGetObstetricsInitializationsForPatientWithHCPPid() {
		Assert.assertEquals(0, oic.getObstetricsInitializationForPatient(Long.toString(DEFAULT_HCP_MID)).size());
		oic.close();
	}

	@Test
	public void testGetObstetricsInitializationsForCurrentPatient() {
//		Mockito.doReturn(Long.toString(DEFAULT_PATIENT_MID)).when(mockSessionUtils).getCurrentPatientMID();
//		Assert.assertEquals(1, oic.getObstetricsInitializationForCurrentPatient().size());
//		Mockito.doReturn("101").when(mockSessionUtils).getCurrentPatientMID();
//		Assert.assertEquals(0, oic.getObstetricsInitializationForCurrentPatient().size());
//		Mockito.doReturn("102").when(mockSessionUtils).getCurrentPatientMID();
//		Assert.assertEquals(0, oic.getObstetricsInitializationForCurrentPatient().size());
//		Mockito.doReturn("103").when(mockSessionUtils).getCurrentPatientMID();
//		Assert.assertEquals(0, oic.getObstetricsInitializationForCurrentPatient().size());
//		Mockito.doReturn("104").when(mockSessionUtils).getCurrentPatientMID();
//		Assert.assertEquals(0, oic.getObstetricsInitializationForCurrentPatient().size());
	}

	@Test
	public void testGetObstetricsInitializationsForCurrentPatientWithInvalidMID() {
		Mockito.doReturn(Long.toString(DEFAULT_HCP_MID)).when(mockSessionUtils).getCurrentPatientMID();
		Assert.assertEquals(0, oic.getObstetricsInitializationForCurrentPatient().size());
		Mockito.doReturn("-1").when(mockSessionUtils).getCurrentPatientMID();
		Assert.assertEquals(0, oic.getObstetricsInitializationForCurrentPatient().size());
		Mockito.doReturn(null).when(mockSessionUtils).getCurrentPatientMID();
		Assert.assertEquals(0, oic.getObstetricsInitializationForCurrentPatient().size());
		oic.close();
	}

	@Test
	public void testGetVisitByIDWithInvalidID() {
		Assert.assertNull(oic.getVisitByID("invalid id"));
		Assert.assertNull(oic.getVisitByID("-1"));
		oic.close();
	}

	@Test
	public void testGetSelectedVisit() throws DBException {
		// Add a test office visit
		oic.add(testOI);
		List<ObstetricsInitialization> ObstetricsInitializationList = oiData.getAll();

		Assert.assertNotNull(ObstetricsInitializationList);
		Assert.assertFalse(ObstetricsInitializationList.isEmpty());

		// Return office visit id in mocked httpServletRequest
		ObstetricsInitialization expected = ObstetricsInitializationList.get(0);
		Mockito.doReturn(expected.getVisitID().toString()).when(mockSessionUtils).parseString(Mockito.any());
		Mockito.doReturn(expected.getVisitID().toString()).when(mockSessionUtils).getRequestParameter("visitID");
		
		ObstetricsInitialization actual = oic.getSelectedVisit();
		Assert.assertNotNull(actual);
		Assert.assertEquals(expected.getPatientMID(), actual.getPatientMID());
		long dif = ChronoUnit.MINUTES.between(expected.getDate(), actual.getDate());
		Assert.assertTrue(dif < 1);
		oic.close();
	}
	
	@Test
	public void testAddPrevPreg() throws DBException{
		oic.add(testPreg);
		oic.close();
	}
	
	@Test
	public void testOBGY(){
		oic.isOBGYN();
		oic.close();
	}

	@Test
	public void testGetSelectedVisitWithNullRequest() {
		Mockito.doReturn(null).when(mockSessionUtils).getSessionVariable("visitID");
		ObstetricsInitialization ov = oic.getSelectedVisit();
		Assert.assertNull(ov);
		oic.close();
	}

	@Test
	public void testGetSelectedVisitWithNullVisitId() {
		Mockito.doReturn(null).when(mockSessionUtils).getSessionVariable("visitID");
		ObstetricsInitialization ov = oic.getSelectedVisit();
		Assert.assertNull(ov);
		oic.close();
	}

	@Test
	public void testHasPatientVisitedWithNulls() {
		Assert.assertFalse(oic.hasPatientVisited(null));
		Assert.assertFalse(oic.hasPatientVisited("-1"));
		oic.close();
	}

	@Test
	public void testCurrentPatientHasVisited() {
		final String MID = "101";
		final String PATIENT = "patient";

		Mockito.doReturn(PATIENT).when(mockSessionUtils).getSessionUserRole();
		Mockito.doReturn(MID).when(mockSessionUtils).getCurrentPatientMID();

		Assert.assertFalse(oic.CurrentPatientHasVisited());
		oic.close();
	}
	
	@Test
	public void testAddReturnGeneratedId() {
		long id = oic.addReturnGeneratedId(testOI);
		Assert.assertTrue(id >= 0);
		Mockito.verify(oic).printFacesMessage(Mockito.eq(FacesMessage.SEVERITY_INFO), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString());
		oic.close();
	}
	
	@Test
	public void testAddReturnGeneratedIdPreg() {
		long id = oic.addReturnGeneratedId(testPreg);
		Assert.assertTrue(id >= 0);
		Mockito.verify(oic).printFacesMessage(Mockito.eq(FacesMessage.SEVERITY_INFO), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString());
		oic.close();
	}
	
	@Test
	public void testLogViewObstetricsInitializationNoneSelected() {
		Mockito.when(mockSessionUtils.getCurrentObstetricsInitializationId()).thenReturn(null);
		oic.setSessionUtils(mockSessionUtils);
		oic.logViewObstetricsInitialization();
		Mockito.verify(oic, Mockito.times(0)).logTransaction(TransactionType.OFFICE_VISIT_VIEW, new Long(2).toString());
		oic.close();
	}

}
