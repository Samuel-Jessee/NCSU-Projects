package edu.ncsu.csc.itrust.model.fitness;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.sql.DataSource;

import edu.ncsu.csc.itrust.controller.fitness.FitnessController;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.POJOValidator;
import edu.ncsu.csc.itrust.model.ValidationFormat;

/**
 * Validates fitness data
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
public class FitnessValidator extends POJOValidator<Fitness> {

	/**
	 * DataSource used by this class
	 */
	private DataSource ds;

	/**
	 * Default constructor for FitnessValidator.
	 */
	public FitnessValidator(DataSource ds) {
		this.ds = ds;
	}

	/**
	 * Used to Validate a fitness object. If the validation does not succeed, a
	 * {@link FormValidationException} is thrown. only performs checks on the
	 * values stored in the object (e.g. Patient ID) Does NOT validate the
	 * format of the date and other attributes that are NOT stored in the object
	 * itself
	 * 
	 * @param obj
	 *            the Fitness to be validated
	 */
	@Override
	public void validate(Fitness obj) throws FormValidationException {
		FitnessController controller = new FitnessController(ds);
		ErrorList errorList = new ErrorList();

		LocalDate date = obj.getDate();
		Long patientID = obj.getPatientID();

		if (patientID == null) {
			errorList.addIfNotNull("Cannot add fitness information: invalid patient MID");
			throw new FormValidationException(errorList);
		}

		LocalDate patientDOB = controller.getPatientDOB(patientID);
		if (patientDOB == null) {
			errorList.addIfNotNull("Cannot add fitness information: patient does not have a birthday");
			throw new FormValidationException(errorList);
		}

		if (date.isBefore(patientDOB)) {
			errorList.addIfNotNull("Date: date cannot be earlier than patient's birthday at "
					+ patientDOB.format(DateTimeFormatter.ISO_DATE));
		}

		errorList.addIfNotNull(checkFormat("Patient MID", patientID, ValidationFormat.NPMID, false));

		if (errorList.hasErrors()) {
			throw new FormValidationException(errorList);
		}

		// TODO
	}
}
