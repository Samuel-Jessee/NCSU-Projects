package edu.ncsu.csc.itrust.model.childbirth;

import java.util.List;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.DataBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.user.patient.Patient;
public interface ChildbirthData extends DataBean<Childbirth>{
	
	/**
	 * Add Childbirth to the database and return the generated VisitID.
	 * 
	 * @param ov
	 *            childbirth to add to the database
	 * @return VisitID generated from the database insertion, -1 if nothing was
	 *         generated
	 * @throws DBException
	 *             if error occurred in inserting childbirth
	 */
	long addReturnGeneratedId(Childbirth cb, long loggedInMID, long parentMID) throws DBException;

	/**
	 * Gets a list of previous childbirths for the certain patient
	 * 
	 * @param patientMID
	 * @return the list of previous childbirths
	 * @throws DBException
	 */
	public List<Childbirth> getChildbirthsForPatient(Long patientMID) throws DBException;

	boolean add(Childbirth cb, long loggedInMID, long parentMID) throws FormValidationException, DBException;



}
