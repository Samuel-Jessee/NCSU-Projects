/**
 * 
 */
package edu.ncsu.csc.itrust.model.obstetricsInitialization;

import java.util.List;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.DataBean;

public interface ObstetricsInitializationData extends DataBean<ObstetricsInitialization> {

	/**
	 * Add pregnancy record to database
	 * 
	 * @param pi
	 *            pregnancy info
	 * @return boolean
	 * @throws FormValidationException
	 * @throws DBException
	 */
	boolean add(PreviousPregnancyInfo pi) throws FormValidationException, DBException;

	/**
	 * Add obstetrics initialization to the database and return the generated
	 * VisitID.
	 * 
	 * @param oi
	 *            obstetrics initialization to add to the database
	 * @return VisitID generated from the database insertion, -1 if nothing was
	 *         generated
	 * @throws DBException
	 *             if error occurred in inserting obstetrics initialization
	 */
	public long addReturnGeneratedId(ObstetricsInitialization oi) throws DBException;

	/**
	 * Add pregnancy to the database and return the generated infoID.
	 * 
	 * @param pi
	 *            pregnancy to add to the database
	 * @return infoID generated from the database insertion, -1 if nothing was
	 *         generated
	 * @throws DBException
	 *             if error occurred in inserting pregnancy
	 */
	public long addReturnGeneratedId(PreviousPregnancyInfo pi) throws DBException;

	/**
	 * Gets a list of previous pregnancies for the certain patient
	 * 
	 * @param patientID
	 * @return the list of previous pregnancies
	 * @throws DBException
	 */
	public List<PreviousPregnancyInfo> getPrevPregForPatient(Long patientID) throws DBException;

	/**
	 * Gets a list of previous obstetrics visits for the certain patient
	 * 
	 * @param patientID
	 * @return the list of previous obstetrics visits
	 * @throws DBException
	 */
	public List<ObstetricsInitialization> getVisitsForPatient(Long patientID) throws DBException;

	void close();
}
