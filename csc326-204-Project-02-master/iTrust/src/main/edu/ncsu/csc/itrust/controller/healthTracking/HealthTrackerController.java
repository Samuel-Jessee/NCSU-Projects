package edu.ncsu.csc.itrust.controller.healthTracking;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;

import edu.ncsu.csc.itrust.controller.NavigationController;
import edu.ncsu.csc.itrust.controller.iTrustController;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ValidationFormat;
import edu.ncsu.csc.itrust.model.healthTracking.HealthTracker;
import edu.ncsu.csc.itrust.model.healthTracking.HealthTrackerData;
import edu.ncsu.csc.itrust.model.healthTracking.HealthTrackerMySQL;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

@ManagedBean(name = "health_tracker_controller")
@SessionScoped
public class HealthTrackerController extends iTrustController {

	/**
	 * Constant for the error message to be displayed if the Health Tracker is
	 * invalid.
	 */
	private static final String HEALTH_TRACKER_CANNOT_BE_UPDATED = "Invalid Health Tracker";

	/**
	 * Constant for the message to be displayed if the office visit was
	 * unsuccessfully updated
	 */
	private static final String HEALTH_TRACKER_CANNOT_BE_CREATED = "Health Tracker Cannot Be Updated";

	/**
	 * Constant for the message to be displayed if the office visit was
	 * successfully created
	 */
	private static final String HEALTH_TRACKER_SUCCESSFULLY_CREATED = "Health Tracker Successfully Created";

	/**
	 * Constant for the message to be displayed if the office visit was
	 * successfully updated
	 */
	private static final String HEALTH_TRACKER_SUCCESSFULLY_UPDATED = "Health Tracker Successfully Updated";

	private HealthTrackerMySQL healthTrackerData;
	private SessionUtils sessionUtils;

	public HealthTrackerController() throws DBException {
		healthTrackerData = new HealthTrackerMySQL();
		sessionUtils = SessionUtils.getInstance();
	}

	/**
	 * For testing purposes
	 * 
	 * @param ds
	 *            DataSource
	 */
	public HealthTrackerController(DataSource ds) {
		healthTrackerData = new HealthTrackerMySQL(ds);
		sessionUtils = SessionUtils.getInstance();
	}
	
	/**
	 * For testing purposes
	 * 
	 * @param ds
	 *            DataSource
	 *@param utils
	 *			  SessionUtils
	 */
	 
	public HealthTrackerController(DataSource ds, SessionUtils utils) {
		healthTrackerData = new HealthTrackerMySQL(ds);
		sessionUtils = utils;
	}

	/**
	 * Add health tracker to the database and return the generated VisitID.
	 * 
	 * @param ht
	 *            Health Tracker to add to the database
	 * @return VisitID generated from the database insertion, -1 if nothing was
	 *         generated
	 * @throws DBException
	 *             if error occurred in inserting health tracker
	 */
	public long addReturnGeneratedId(HealthTracker ht) {
		long generatedId = -1;

		try {
			generatedId = healthTrackerData.addReturnGeneratedId(ht);
		} catch (DBException e) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, HEALTH_TRACKER_CANNOT_BE_CREATED, e.getExtendedMessage(), null);
		} catch (Exception e) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, HEALTH_TRACKER_CANNOT_BE_CREATED,
					HEALTH_TRACKER_CANNOT_BE_CREATED, null);
		}

		if (generatedId >= 0) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, HEALTH_TRACKER_SUCCESSFULLY_CREATED,
					HEALTH_TRACKER_SUCCESSFULLY_CREATED, null);
		}

		return generatedId;
	}

	/**
	 * Sends a FacesMessage for FacesContext to display.
	 * 
	 * @param severity
	 *            severity of the message
	 * @param summary
	 *            localized summary message text
	 * @param detail
	 *            localized detail message text
	 * @param clientId
	 *            The client identifier with which this message is associated
	 *            (if any)
	 */
	public void printFacesMessage(Severity severity, String summary, String detail, String clientId) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		if (ctx == null) {
			return;
		}
		ctx.getExternalContext().getFlash().setKeepMessages(true);
		ctx.addMessage(clientId, new FacesMessage(severity, summary, detail));
	}

	public HealthTracker getDataByID(String VisitID) {
		long id = -1;
		try {
			id = Long.parseLong(VisitID);
		} catch (NumberFormatException ne) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Retrieve Office Visit",
					"Unable to Retrieve Office Visit", null);
			return null;
		}
		try {
			return healthTrackerData.getByID(id);
		} catch (Exception e) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Retrieve Office Visit",
					"Unable to Retrieve Office Visit", null);
			return null;
		}
	}

	/**
	 * @return health tracker of the selected patient in the HCP session
	 */
	public HealthTracker getSelectedVisit() {
		String visitID = sessionUtils.getRequestParameter("visitID");
		if (visitID == null || visitID.isEmpty()) {
			return null;
		}
		return getDataByID(visitID);
	}

	public void edit(HealthTracker ht) {
		boolean res = false;

		try {
			res = healthTrackerData.update(ht);
		} catch (DBException e) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, HEALTH_TRACKER_CANNOT_BE_UPDATED, e.getExtendedMessage(),
					null);
		} catch (Exception e) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, HEALTH_TRACKER_CANNOT_BE_UPDATED,
					HEALTH_TRACKER_CANNOT_BE_UPDATED, null);
		}
		if (res) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, HEALTH_TRACKER_SUCCESSFULLY_UPDATED,
					HEALTH_TRACKER_SUCCESSFULLY_UPDATED, null);
		}
	}
	
	public Long dateExists(Long patientID, Long hcpID, Date date) throws DBException {
		return healthTrackerData.dateExists(patientID, hcpID, date);
	}
	
	public void close() {
		healthTrackerData.close();
	}
}
