package edu.ncsu.csc.itrust.model.healthTracking;

import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.DataBean;

public interface HealthTrackerData extends DataBean<HealthTracker>{
	public List<HealthTracker> getDataForPatient(Long patientID) throws DBException;
	
	/**
	 * Add health tracking to the database and return the generated VisitID.
	 * 
	 * @param ht
	 * 			Health Tracking Data to add to the database
	 * @return VisitID generated from the database insertion, -1 if nothing was generated
	 * @throws DBException if error occurred in inserting data
	 */
	public long addReturnGeneratedId(HealthTracker ht) throws DBException;
	
	public Long dateExists(Long patientID, Long hcpID, Date date) throws DBException;
}
