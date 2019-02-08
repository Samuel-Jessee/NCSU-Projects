package edu.ncsu.csc.itrust.model.childbirth;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.sql.DataSource;

import edu.ncsu.csc.itrust.controller.obstetricsOfficeVisit.ObstetricsOfficeVisitController;
import edu.ncsu.csc.itrust.controller.officeVisit.OfficeVisitController;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.POJOValidator;
import edu.ncsu.csc.itrust.model.ValidationFormat;

public class ChildbirthValidator extends POJOValidator<Childbirth>{

	private DataSource ds;
	
	public ChildbirthValidator(DataSource ds) {
		this.ds = ds;
	}

	public ChildbirthValidator() {

	}
	
	@Override
	public void validate(Childbirth obj) throws FormValidationException {	
		ErrorList errorList = new ErrorList();
		Long patientMID = obj.getPatientMID();
		if (patientMID == null) {
			errorList.addIfNotNull("Cannot add obstetrics childbirth information: invalid patient MID");
		}
		
		LocalDateTime date = obj.getDate();
		if (date == null) {
			errorList.addIfNotNull("Cannot add childbirth: invalid date");
		}
		
		errorList.addIfNotNull(checkFormat("Pitocin", obj.getPitocin(), ValidationFormat.DRUG, true));
		errorList.addIfNotNull(checkFormat("Nitrous Oxide", obj.getNitrousOxide(), ValidationFormat.DRUG, true));
		errorList.addIfNotNull(checkFormat("Pethidine", obj.getPethidine(), ValidationFormat.DRUG, true));
		errorList.addIfNotNull(checkFormat("Epidural Anaesthesia", obj.getEpiduralAnaesthesia(), ValidationFormat.DRUG, true));
		errorList.addIfNotNull(checkFormat("Magnesium Sulfate", obj.getMagnesiumSulfate(), ValidationFormat.DRUG, true));
		errorList.addIfNotNull(checkFormat("RH Immune Globulin", obj.getRhimmuneglobulin(), ValidationFormat.DRUG, true));
		errorList.addIfNotNull(checkFormat("Method", obj.getMethod(), ValidationFormat.COMMENTS, true));
		errorList.addIfNotNull(checkFormat("Hours In Labor", obj.getHoursInLabor(), ValidationFormat.HOURS_LABOR, true));
		
		if (errorList.hasErrors()) {
			throw new FormValidationException(errorList);
		}
	}
	
	public void validate(ChildInChildbirth obj) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		
		LocalDateTime date = obj.getDate();
		if (date == null) {
			errorList.addIfNotNull("Cannot add childbirth: invalid date of birth");
		}
		
		errorList.addIfNotNull(checkFormat("Gender", obj.getGender(), ValidationFormat.GENDERCOD, true));
		
		if (errorList.hasErrors()) {
			throw new FormValidationException(errorList);
		}
	}
	
	

}
