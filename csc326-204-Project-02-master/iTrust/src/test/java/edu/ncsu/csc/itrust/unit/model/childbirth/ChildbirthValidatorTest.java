package edu.ncsu.csc.itrust.unit.model.childbirth;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.childbirth.ChildInChildbirth;
import edu.ncsu.csc.itrust.model.childbirth.Childbirth;
import edu.ncsu.csc.itrust.model.childbirth.ChildbirthValidator;

public class ChildbirthValidatorTest {
    @Test
    public void testValidator() {
    	ChildbirthValidator validator = new ChildbirthValidator();
    	Childbirth cb = new Childbirth();
    	cb.setPatientMID(null);
    	try {
			validator.validate(cb);
			Assert.fail();
		} catch (FormValidationException e) {
			//yay!
		}
    	cb.setPatientMID(1L);
    	cb.setNitrousOxide(-47.7);
    	try {
			validator.validate(cb);
			Assert.fail();
		} catch (FormValidationException e) {
			//yay!
		}
    	ChildInChildbirth cic = new ChildInChildbirth();
    	cic.setDate(LocalDateTime.of(2017, 04, 05, 11, 01, 02));
    	cic.setGender("Bob");
    	try {
			validator.validate(cic);
			Assert.fail();
		} catch (FormValidationException e) {
			//yay
		}
    	cic.setGender("Male");
    	try {
			validator.validate(cic);
			
		} catch (FormValidationException e) {
			System.out.println(e);
			Assert.fail();
		}	
    	
    }
}
