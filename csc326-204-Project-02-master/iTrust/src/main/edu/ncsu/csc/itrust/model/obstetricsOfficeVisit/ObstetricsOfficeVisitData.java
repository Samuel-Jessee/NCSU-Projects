package edu.ncsu.csc.itrust.model.obstetricsOfficeVisit;

import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.DataBean;

public interface ObstetricsOfficeVisitData extends DataBean<ObstetricsOfficeVisit>{
	
	/**
	 * Add obstetrics office visit to the database and return the generated
	 * VisitID.
	 * 
	 * @param ov
	 *            obstetrics office visit to add to the database
	 * @return VisitID generated from the database insertion, -1 if nothing was
	 *         generated
	 * @throws DBException
	 *             if error occurred in inserting obstetrics office visit
	 */
	public long addReturnGeneratedId(ObstetricsOfficeVisit ov) throws DBException;
	
	/**
	 * Add ultrasound to the database and return the generated ultraID.
	 * 
	 * @param oi
	 *            ultrasound to add to the database
	 * @return ultraID generated from the database insertion, -1 if nothing was
	 *         generated
	 * @throws DBException
	 *             if error occurred in inserting ultrasound
	 */
	public long addReturnGeneratedId(Ultrasound us) throws DBException;
	
	/**
	 * Gets a list of previous obstetrics visits for the certain patient
	 * 
	 * @param patientID
	 * @return the list of previous obstetrics visits
	 * @throws DBException
	 */
	public List<ObstetricsOfficeVisit> getVisitsForPatient(Long patientID) throws DBException;
	
	/**
	 * Retrieves the patient's date of birth from database.
	 * 
	 * @param patientMID
	 * 			MID of the patient
	 * @return patient's date of birth
	 */
	public Date getPatientDOB(Long patientID);
	
	public Date getPatientLMP(Long patientMID);

	public Ultrasound getUltrasoundByID(long id) throws DBException;
/*
	public boolean update(Ultrasound updateObj) throws DBException, FormValidationException;
*/

	public void close();

}
