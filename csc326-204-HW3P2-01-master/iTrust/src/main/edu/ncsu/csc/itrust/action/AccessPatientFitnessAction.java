package edu.ncsu.csc.itrust.action;

import java.sql.Timestamp;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.FitnessBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.FitnessDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO;

/**
 * This class will act as a middle-backend file between the jsf/xhtml file and
 * the FitnessDAO for viewing/editing/adding patient data
 * 
 * @author Louis
 *
 */
@ManagedBean(name = "access_patient")
@ViewScoped
public class AccessPatientFitnessAction {
	private long loggedInMID;
	private FitnessDAO fitnessDAO;
	private PatientDAO patientDAO;
	private PersonnelDAO personnelDAO;

	/**
	 * Constructor that initializes class instances.
	 * 
	 * @param factory
	 * @param loggedInMID
	 */
	public AccessPatientFitnessAction(DAOFactory factory, long loggedInMID) {
		this.loggedInMID = loggedInMID;
		fitnessDAO = factory.getFitnessDAO();
		patientDAO = factory.getPatientDAO();
		personnelDAO = factory.getPersonnelDAO();
		// Transaction logged code
	}

	/**
	 * Updates the fitness at a given date, if the date doesn't exist, it will
	 * add it.
	 * 
	 * @param fitnessBean
	 *            Bean that contains data that will be updated
	 * @throws DBException
	 *             Thrown if it runs into any errors
	 */
	public void updateFitnessAtDate(FitnessBean fitnessBean) throws DBException {
		fitnessDAO.addFitness(fitnessBean);
	}

	/**
	 * Returns fitness bean of data at a given date for a specified patient
	 * 
	 * @param mid
	 *            ID of patient
	 * @param date
	 *            Date of data to retrieve
	 * @return
	 * @throws DBException
	 */
	public FitnessBean getFitnessAtDate(long mid, Timestamp date) throws DBException {
		return fitnessDAO.getFitnessForDate(mid, date);
	}
	
	/**
	 * Gets a patient's name from their MID
	 * 
	 * @param mid the MID of the patient
	 * @return the patient's name
	 * @throws ITrustException
	 */
	public String getName(long mid) throws ITrustException {
		if(mid < 7000000000L)
			return patientDAO.getName(mid);
		else
			return personnelDAO.getName(mid);
	}
	
	/**
	 * Gets a personnel's name from their MID
	 * 
	 * @param mid the MID of the personnel
	 * @return the personnel's name
	 * @throws ITrustException
	 */
	public String getPersonnelName(long mid) throws ITrustException {
		return personnelDAO.getName(mid);
	}
}