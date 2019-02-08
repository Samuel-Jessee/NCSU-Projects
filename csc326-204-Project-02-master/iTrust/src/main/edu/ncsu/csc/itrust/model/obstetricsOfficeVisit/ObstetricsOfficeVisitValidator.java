package edu.ncsu.csc.itrust.model.obstetricsOfficeVisit;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.sql.DataSource;

import edu.ncsu.csc.itrust.controller.obstetricsOfficeVisit.ObstetricsOfficeVisitController;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.POJOValidator;
import edu.ncsu.csc.itrust.model.ValidationFormat;

public class ObstetricsOfficeVisitValidator extends POJOValidator<ObstetricsOfficeVisit> {

	private DataSource ds;
	
	public ObstetricsOfficeVisitValidator(DataSource ds) {
		this.ds = ds;
	}

	public ObstetricsOfficeVisitValidator() {

	}
	
	public void validate(Ultrasound obj) throws FormValidationException {
		// TODO Auto-generated method stub
		ErrorList errorList = new ErrorList();
		
		Long patientMID = obj.getPatientMID();
		
		if (patientMID == null) {
			errorList.addIfNotNull("Cannot add obstetrics office visit information: invalid patient MID");
			throw new FormValidationException(errorList);
		}
		errorList.addIfNotNull(checkFormat("Crown Rump Length", obj.getCRL(), ValidationFormat.LENGTH_OV, true));
		errorList.addIfNotNull(checkFormat("Biparietal Diameter", obj.getBPD(), ValidationFormat.LENGTH_OV, true));
		errorList.addIfNotNull(checkFormat("Head Circumference", obj.getHC(), ValidationFormat.HEAD_CIRCUMFERENCE_OV, true));
		errorList.addIfNotNull(checkFormat("Femur Length", obj.getFL(), ValidationFormat.LENGTH_OV, true));
		errorList.addIfNotNull(checkFormat("Occipitofrontal Diameter", obj.getOFD(), ValidationFormat.LENGTH_OV, true));
		errorList.addIfNotNull(checkFormat("Abdominal Circumference", obj.getAC(), ValidationFormat.LENGTH_OV, true));
		errorList.addIfNotNull(checkFormat("Humerus Length", obj.getHL(), ValidationFormat.LENGTH_OV, true));
		errorList.addIfNotNull(checkFormat("Estimated Fetal Weight", obj.getEFW(), ValidationFormat.WEIGHT_OV, true));

		if (errorList.hasErrors()) {
			throw new FormValidationException(errorList);
		}
	}

	@Override
	public void validate(ObstetricsOfficeVisit obj) throws FormValidationException {
		ObstetricsOfficeVisitController ovc = new ObstetricsOfficeVisitController(ds);
		ErrorList errorList = new ErrorList();
		
		Date date = obj.getDate();
		Long patientMID = obj.getPatientMID();
		
		if (patientMID == null) {
			errorList.addIfNotNull("Cannot add obstetrics office visit information: invalid patient MID");
			throw new FormValidationException(errorList);
		}
		
		Date patientDOB = ovc.getPatientDOB(patientMID);
		if (patientDOB == null) {
			errorList.addIfNotNull("Cannot add office visit information: patient does not have a birthday");
			throw new FormValidationException(errorList);
		}
		
		if (date.before(patientDOB)) {
			SimpleDateFormat format = new SimpleDateFormat("MM/DD/YYYY");
			errorList.addIfNotNull("Date: date cannot be earlier than patient's birthday at " + format.format(patientDOB));
		}
		
		errorList.addIfNotNull(checkFormat("Patient MID", patientMID, ValidationFormat.NPMID, false));
		if (obj.getVisitID() != null) {
			if (obj.getVisitID() <= 0) {
				errorList.addIfNotNull("Visit ID: Invalid Visit ID");
			}
		}
		errorList.addIfNotNull(checkFormat("Weight", obj.getWeight(), ValidationFormat.WEIGHT_OV, true));
		errorList.addIfNotNull(checkFormat("Blood Pressure", obj.getBloodPressure(), ValidationFormat.BLOOD_PRESSURE_OV, true));
		if (obj.getNumWeeks() < 0 || obj.getNumWeeks() > 49) {
			errorList.addIfNotNull("Invalid Number of Weeks Pregnant");
		}
		errorList.addIfNotNull(checkFormat("Fetal Heart Rate", obj.getFHR(), ValidationFormat.FHR, true));
		if (obj.getNumFetus() < 1 || obj.getNumFetus() > 12) {
			errorList.addIfNotNull("Invalid number of children");
		}
		
		if (errorList.hasErrors()) {
			throw new FormValidationException(errorList);
		}
	}

}
