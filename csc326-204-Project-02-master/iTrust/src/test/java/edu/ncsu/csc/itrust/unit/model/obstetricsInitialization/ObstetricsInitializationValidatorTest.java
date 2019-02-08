package edu.ncsu.csc.itrust.unit.model.obstetricsInitialization;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitialization;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitializationValidator;

public class ObstetricsInitializationValidatorTest {
	ObstetricsInitialization o;
	ObstetricsInitializationValidator v;
    @Before
	public void setUp() {
    	o = new ObstetricsInitialization();
    	v = new ObstetricsInitializationValidator();
        o.setDate(LocalDate.of(2014, 5, 19).atTime(10, 30));
        o.setLMP(LocalDate.of(2014, 4, 7));
        o.setPatientMID((long) 1);
        o.setVisitID((long) 1);
    }
    @Test
    public void testNull() {
    	try {
			v.validate(o);
		} catch (FormValidationException e1) {
			assertTrue(false);
		}
    	o.setDate(null);
    	try {
			v.validate(o);
		} catch (FormValidationException e) {
			List<String> errors = e.getErrorList();
			assertEquals(errors.get(0), "Cannot add obstetrics information: invalid Appointment Date");
		}
    	o.setDate(LocalDate.of(2014, 5, 19).atTime(10, 30));
    	
    	o.setLMP(null);
    	try {
			v.validate(o);
		} catch (FormValidationException e) {
			List<String> errors = e.getErrorList();
			assertEquals(errors.get(0), "Cannot add obstetrics information: invalid LMP Date");
		}
    	o.setLMP(LocalDate.of(2014, 4, 7));
    	
    	o.setPatientMID(null);
    	try {
			v.validate(o);
		} catch (FormValidationException e) {
			List<String> errors = e.getErrorList();
			assertEquals(errors.get(0), "Cannot add obstetrics information: invalid patient MID");
		}
    	o.setPatientMID((long) 1);
    	
    	o.setVisitID(null);
    	try {
			v.validate(o);
		} catch (FormValidationException e) {
			List<String> errors = e.getErrorList();
			assertEquals(errors.get(0), "Visit ID: 1-10 digit number not beginning with 9");
		}
    	o.setVisitID((long) 1);
    }
    
    @Test
    public void testInvalidDates() {
    	o.setDate(LocalDate.of(2014, 3, 19).atTime(10, 30));
    	try {
			v.validate(o);
		} catch (FormValidationException e) {
			List<String> errors = e.getErrorList();
			assertEquals(errors.get(0), "Date: Appointment Date cannot come before LMP");
		}
    	o.setDate(LocalDate.of(2014, 5, 19).atTime(10, 30));
    }
}
