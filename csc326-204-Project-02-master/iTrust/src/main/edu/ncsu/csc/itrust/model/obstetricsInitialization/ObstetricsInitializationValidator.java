package edu.ncsu.csc.itrust.model.obstetricsInitialization;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.sql.DataSource;

import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.POJOValidator;
import edu.ncsu.csc.itrust.model.ValidationFormat;

public class ObstetricsInitializationValidator extends POJOValidator<ObstetricsInitialization> {

	private DataSource ds;

	/**
	 * Default constructor for ObstetricsInitializationValidator.
	 */
	public ObstetricsInitializationValidator(DataSource ds) {
		this.ds = ds;
	}

	public ObstetricsInitializationValidator() {

	}

	/**
	 * Used to Validate an office visit object. If the validation does not
	 * succeed, a {@link FormValidationException} is thrown. only performs
	 * checks on the values stored in the object (e.g. Patient MID) Does NOT
	 * validate the format of the visit date and other attributes that are NOT
	 * stored in the object itself
	 * 
	 * @param obj
	 *            the Office Visit to be validated
	 */
	public void validate(ObstetricsInitialization obj) throws FormValidationException {
		ErrorList errorList = new ErrorList();

		LocalDateTime date = obj.getDate();
		Long patientMID = obj.getPatientMID();
		if (patientMID == null) {
			errorList.addIfNotNull("Cannot add obstetrics information: invalid patient MID");
			throw new FormValidationException(errorList);
		}
		if (date == null) {
			errorList.addIfNotNull("Cannot add obstetrics information: invalid Appointment Date");
			throw new FormValidationException(errorList);
		}
		LocalDate lmp = obj.getLMP();
		if (lmp == null) {
			errorList.addIfNotNull("Cannot add obstetrics information: invalid LMP Date");
			throw new FormValidationException(errorList);
		}

		if (date.toLocalDate().isBefore(lmp)) {
			errorList.addIfNotNull("Date: Appointment Date cannot come before LMP");
		}
		errorList.addIfNotNull(checkFormat("Patient MID", patientMID, ValidationFormat.NPMID, false));
		if (errorList.hasErrors()) {
			throw new FormValidationException(errorList);
		}
	}

	public void validate(PreviousPregnancyInfo pi) throws FormValidationException {
		// TODO Auto-generated method stub

	}
}
