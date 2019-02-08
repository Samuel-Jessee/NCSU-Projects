package edu.ncsu.csc.itrust.model.fitness;

import java.time.LocalDate;
import java.util.List;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.DataBean;

/**
 * Interface defining methods to be implemented by fitness data classes.
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
public interface FitnessData extends DataBean<Fitness> {

	/**
	 * Add fitness data to the database and return the generated fitnessID.
	 * 
	 * @param fitness
	 *            Fitness to add to the database
	 * @return fitnessID generated from the database insertion, -1 if nothing
	 *         was generated
	 * @throws DBException
	 *             if error occurred in inserting fitness data
	 */
	public long addReturnGeneratedID(Fitness fitness) throws DBException;

	/**
	 * Gets Fitness object for the patient on a given date
	 * 
	 * @param patientID
	 *            patient ID
	 * @param date
	 *            date to retrieve
	 * @return Fitness object
	 * @throws DBException
	 */
	public Fitness getFitnessByDate(Long patientID, LocalDate date) throws DBException;

	/**
	 * Gets fitness data for a patient in a given date range.
	 * 
	 * @param patientID
	 *            patient id
	 * @param startDate
	 *            start of date range
	 * @param endDate
	 *            end of date range
	 * @return fitness data from date range
	 * @throws DBException
	 */
	public List<Fitness> getFitnessDateRange(Long patientID, LocalDate startDate, LocalDate endDate) throws DBException;

	/**
	 * Returns all Fitness objects associated with the given patient
	 * 
	 * @param patientID
	 *            ID of patient
	 * @return list of Fitness objects
	 * @throws DBException
	 */
	public List<Fitness> getFitnessForPatient(Long patientID) throws DBException;

	/**
	 * Retrieves the patient's date of birth from database.
	 * 
	 * @param patientMID
	 *            MID of the patient
	 * @return patient's date of birth
	 */
	public LocalDate getPatientDOB(Long patientID);
}
