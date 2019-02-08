package edu.ncsu.csc.itrust.unit.controller.obstetricsInitialization;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

import edu.ncsu.csc.itrust.controller.obstetricsInitialization.ObstetricsInitializationController;
import edu.ncsu.csc.itrust.controller.obstetricsInitialization.ObstetricsInitializationForm;
import edu.ncsu.csc.itrust.controller.officeVisit.OfficeVisitController;
import edu.ncsu.csc.itrust.controller.officeVisit.OfficeVisitForm;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.apptType.ApptTypeData;
import edu.ncsu.csc.itrust.model.apptType.ApptTypeMySQLConverter;
import edu.ncsu.csc.itrust.model.hospital.HospitalData;
import edu.ncsu.csc.itrust.model.hospital.HospitalMySQLConverter;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitialization;
import edu.ncsu.csc.itrust.model.officeVisit.OfficeVisit;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;

public class ObstetricsInitializationFormTest {
	private ObstetricsInitializationForm oif;
	@Spy
	private ObstetricsInitializationForm spyoif;
	@Spy
	private ObstetricsInitializationController oic;
	//private ApptTypeData apptData;
	private TestDataGenerator gen;
	private DataSource ds;
	//private HospitalData hData;
	private ObstetricsInitialization oi;
	@Spy
	private ObstetricsInitializationController mockoic;
	
	@Before
	public void setUp() throws Exception {
		ds = ConverterDAO.getDataSource();
		gen = new TestDataGenerator();
		oic = Mockito.spy(new ObstetricsInitializationController(ds));
		mockoic = Mockito.mock(ObstetricsInitializationController.class);
		
		createOI();
		
		gen = new TestDataGenerator();
	}

	private void createOI() {
		oif = new ObstetricsInitializationForm();
		oif.setDate(LocalDateTime.of(2010, Month.JANUARY, 1, 0, 0));
		oif.setLMP(LocalDate.of(2010, Month.JANUARY, 1));
		oif.setPatientMID((long) 1);
		oif.setVisitID((long) 0);
		
	}
	
	@Test
	public void testGetVisitID() {
		Mockito.when(mockoic.getSelectedVisit()).thenReturn(oi);
		oif = new ObstetricsInitializationForm();
		oif.setVisitID(1L);
		Assert.assertTrue(1L == oif.getVisitID());
	}

	@Test
	public void testGetPatientMID() {
		Mockito.when(mockoic.getSelectedVisit()).thenReturn(oi);
		oif = new ObstetricsInitializationForm();
		oif.setPatientMID(2L);
		Assert.assertTrue(oif.getPatientMID().equals(2L));
	}

	@Test
	public void testGetDate() {
		Mockito.when(mockoic.getSelectedVisit()).thenReturn(oi);
		oif = new ObstetricsInitializationForm();
		LocalDateTime test = LocalDateTime.of(2010, Month.JANUARY, 1, 0, 0);
		oif.setDate(test);
		Assert.assertTrue(oif.getDate().equals(test));
	}
	
	@Test
	public void testGetLMP() {
		Mockito.when(mockoic.getSelectedVisit()).thenReturn(oi);
		oif = new ObstetricsInitializationForm();
		LocalDate test = LocalDate.of(2010, Month.JANUARY, 1);
		oif.setLMP(test);
		Assert.assertTrue(oif.getLMP().equals(test));
	}
	
	@Test
	public void testSubmit() {
		//Mockito.when(mockoic.getSelectedVisit()).thenReturn(oi);
		createOI();
		oif.setDate(LocalDateTime.of(2010, Month.JANUARY, 1, 0, 0));
		Assert.assertTrue(oif.getDate().equals(LocalDateTime.of(2010, Month.JANUARY, 1, 0, 0)));
		//commenting out in the meantime
		//oif.submit();
		Assert.assertTrue(oif.getPatientMID().equals((long)1));	
		Assert.assertTrue(oif.getVisitID().equals((long)0));
		Assert.assertTrue(oif.getDate().equals(LocalDateTime.of(2010, Month.JANUARY, 1, 0, 0)));
		Assert.assertTrue(oif.getLMP().equals(LocalDate.of(2010, Month.JANUARY, 1)));

	}
}
