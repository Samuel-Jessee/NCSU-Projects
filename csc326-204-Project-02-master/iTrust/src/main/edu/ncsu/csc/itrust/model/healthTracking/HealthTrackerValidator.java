package edu.ncsu.csc.itrust.model.healthTracking;

import java.util.Date;

import javax.sql.DataSource;

import edu.ncsu.csc.itrust.controller.healthTracking.HealthTrackerController;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.POJOValidator;
import edu.ncsu.csc.itrust.model.ValidationFormat;

public class HealthTrackerValidator extends POJOValidator<HealthTracker> {
	
	private DataSource ds;

	/**
	 * Default constructor for OfficeVisitValidator. 
	 */
	public HealthTrackerValidator(DataSource ds) {
		this.ds = ds;
	}

	/**
	 * Used to Validate a health tracking object. If the validation does not
	 * succeed, a {@link FormValidationException} is thrown. only performs
	 * checks on the values stored in the object (e.g. Patient MID) Does NOT
	 * validate the format of the visit date and other attributes that are NOT
	 * stored in the object itself
	 * 
	 * @param obj
	 *            the Health Tracker to be validated
	 */
	@Override
	public void validate(HealthTracker obj) throws FormValidationException {
		HealthTrackerController htc = new HealthTrackerController(ds);
		ErrorList errorList = new ErrorList();
		
		Date date = obj.getDate();
		Long patientMID = obj.getPatient();
		
		if (patientMID == null) {
			errorList.addIfNotNull("Cannot add health tracker information: invalid patient MID");
			throw new FormValidationException(errorList);
		}
		
		errorList.addIfNotNull(checkFormat("Patient MID", patientMID, ValidationFormat.NPMID, false));

		if (errorList.hasErrors()) {
			throw new FormValidationException(errorList);
		}
	}
}
