package edu.ncsu.csc.itrust.controller.obstetricsOfficeVisit;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;

import edu.ncsu.csc.itrust.action.AddApptAction;
import edu.ncsu.csc.itrust.controller.iTrustController;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.ValidationFormat;
import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.ObstetricsOfficeVisit;
import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.ObstetricsOfficeVisitData;
import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.ObstetricsOfficeVisitMySQL;
import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.Ultrasound;
import edu.ncsu.csc.itrust.model.old.beans.ApptBean;
import edu.ncsu.csc.itrust.model.old.beans.PersonnelBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;
import edu.ncsu.csc.itrust.webutils.SessionUtils;


@ManagedBean(name = "obstetrics_office_visit_controller")
@SessionScoped
public class ObstetricsOfficeVisitController extends iTrustController {
	
	/**
	 * Constant for the error message to be displayed if the Obstetrics Office Visit is
	 * invalid.
	 */
	private static final String OBSTETRICS_OFFICE_VISIT_CANNOT_BE_UPDATED = "Invalid Obstetrics Office Visit";

	/**
	 * Constant for the message to be displayed if the Obstetrics office visit was
	 * unsuccessfully updated
	 */
	private static final String OBSTETRICS_OFFICE_VISIT_CANNOT_BE_CREATED = "Obstetrics Office Visit Cannot Be Updated";

	/**
	 * Constant for the message to be displayed if the Obstetrics office visit was
	 * successfully created
	 */
	private static final String OBSTETRICS_OFFICE_VISIT_SUCCESSFULLY_CREATED = "Obstetrics Office Visit Successfully Created";

	/**
	 * Constant for the message to be displayed if the Obstetrics office visit was
	 * successfully updated
	 */
	private static final String OBSTETRICS_OFFICE_VISIT_SUCCESSFULLY_UPDATED = "Obstetrics Office Visit Successfully Updated";
	/**
	 * Constant for the error message to be displayed if the Obstetrics Office Visit is
	 * invalid.
	 */
	private static final String ULTRASOUND_CANNOT_BE_UPDATED = "Invalid Ultrasound";

	/**
	 * Constant for the message to be displayed if the Obstetrics office visit was
	 * unsuccessfully updated
	 */
	private static final String ULTRASOUND_CANNOT_BE_CREATED = "Ultrasound Cannot Be Updated";

	/**
	 * Constant for the message to be displayed if the Obstetrics office visit was
	 * successfully created
	 */
	private static final String ULTRASOUND_SUCCESSFULLY_CREATED = "Ultrasound Successfully Created";

	/**
	 * Constant for the message to be displayed if the Obstetrics office visit was
	 * successfully updated
	 */
	private static final String ULTRASOUND_SUCCESSFULLY_UPDATED = "Ultrasound Successfully Updated";

	private ObstetricsOfficeVisitData OBOVData;
	private SessionUtils sessionUtils;
	
	public ObstetricsOfficeVisitController()  throws DBException{
		try {
			OBOVData = new ObstetricsOfficeVisitMySQL();
		} catch (DBException e) {
			// TODO Auto-generated catch block
			OBOVData = new ObstetricsOfficeVisitMySQL(ConverterDAO.getDataSource());
		}
		sessionUtils = SessionUtils.getInstance();
	}
	
	/**
	 * For testing purposes
	  * @param ds
	 *            DataSource
	 * @param sessionUtils
	 *            SessionUtils instance
	 */
	public ObstetricsOfficeVisitController(DataSource ds, SessionUtils sessionUtils) {
		OBOVData = new ObstetricsOfficeVisitMySQL(ds);
		this.sessionUtils = sessionUtils;
	}
	
	/**
	 * For testing purposes
	  * @param ds
	 *            DataSource
	 */
	public ObstetricsOfficeVisitController(DataSource ds) {
		OBOVData = new ObstetricsOfficeVisitMySQL(ds);
		this.sessionUtils = SessionUtils.getInstance();
	}
	
	/**
	 * Add obstetrics initialization to the database and return the generated VisitID.
	 * 
	 * @param oi
	 *            Obstetrics Initialization to add to the database
	 * @return VisitID generated from the database insertion, -1 if nothing was
	 *         generated
	 * @throws DBException
	 *             if error occurred in inserting obstetrics initlization
	 */
	public long addReturnGeneratedId(ObstetricsOfficeVisit ov) {
		long generatedId = -1;

		try {
			generatedId = OBOVData.addReturnGeneratedId(ov);
		} catch (DBException e) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, OBSTETRICS_OFFICE_VISIT_CANNOT_BE_CREATED, e.getExtendedMessage(), null);
		} catch (Exception e) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, OBSTETRICS_OFFICE_VISIT_CANNOT_BE_CREATED,
					OBSTETRICS_OFFICE_VISIT_CANNOT_BE_CREATED, null);
		}

		if (generatedId >= 0) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, OBSTETRICS_OFFICE_VISIT_SUCCESSFULLY_CREATED,
					OBSTETRICS_OFFICE_VISIT_SUCCESSFULLY_CREATED, null);
			logEditBasicHealthInformation();
		}
		
		createNextAppointment(ov);

		return generatedId;
	}
	
	public long addReturnGeneratedId(Ultrasound us) {
		long generatedId = -1;

		try {
			generatedId = OBOVData.addReturnGeneratedId(us);
		} catch (DBException e) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, ULTRASOUND_CANNOT_BE_CREATED, e.getExtendedMessage(), null);
		} catch (Exception e) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, ULTRASOUND_CANNOT_BE_CREATED,
					ULTRASOUND_CANNOT_BE_CREATED, null);
		}

		if (generatedId >= 0) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, ULTRASOUND_SUCCESSFULLY_CREATED,
					ULTRASOUND_SUCCESSFULLY_CREATED, null);
			logEditBasicHealthInformation();
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
	
	public void add(ObstetricsOfficeVisit ov) {
		boolean res = false;

		try {
			res = OBOVData.add(ov);
		} catch (DBException e) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, OBSTETRICS_OFFICE_VISIT_CANNOT_BE_CREATED, e.getExtendedMessage(),
					null);
		} catch (Exception e) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, OBSTETRICS_OFFICE_VISIT_CANNOT_BE_CREATED,
					OBSTETRICS_OFFICE_VISIT_CANNOT_BE_CREATED, null);
		}
		if (res) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, OBSTETRICS_OFFICE_VISIT_SUCCESSFULLY_CREATED,
					OBSTETRICS_OFFICE_VISIT_SUCCESSFULLY_CREATED, null);
			logEditBasicHealthInformation();
		}
	}
	
	public void logViewBasicHealthInformation() {
		logTransaction(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW, "");
	}

	/**
	 * Editing basic health information is synonymous with editing or adding an
	 * office visit, so this method should be called whenever an OV is
	 * added/edited.
	 */
	private void logEditBasicHealthInformation() {
		logTransaction(TransactionType.PATIENT_HEALTH_INFORMATION_EDIT, "");
	}
	
	/**
	 * Checks if the current user's specialty is OB/GYN
	 * @return true if OB/GYN, false otherwise
	 */
	public boolean isOBGYN() {
		DAOFactory prodDAO = DAOFactory.getProductionInstance();
		long hcpID = sessionUtils.getSessionLoggedInMIDLong();
		PersonnelBean hcp;
		try {
			hcp = prodDAO.getPersonnelDAO().getPersonnel(hcpID);
			String spec = hcp.getSpecialty();
			if (spec == null) {
				return false;
			}
			if (spec.equals("OB/GYN")) {
				return true;
			} else {
				return false;
			}
		} catch (DBException e) {
			
			e.printStackTrace();
			return false;
		}
	}
	
	public void edit(ObstetricsOfficeVisit ov) {
		boolean res = false;

		try {
			res = OBOVData.update(ov);
		} catch (DBException e) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, OBSTETRICS_OFFICE_VISIT_CANNOT_BE_CREATED, e.getExtendedMessage(),
					null);
		} catch (Exception e) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, OBSTETRICS_OFFICE_VISIT_CANNOT_BE_CREATED,
					OBSTETRICS_OFFICE_VISIT_CANNOT_BE_CREATED, null);
		}
		if (res) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, OBSTETRICS_OFFICE_VISIT_SUCCESSFULLY_CREATED,
					OBSTETRICS_OFFICE_VISIT_SUCCESSFULLY_CREATED, null);
			logEditBasicHealthInformation();
		}
	}
	
	public ObstetricsOfficeVisit getVisitByID(String VisitID) {
		long id = -1;
		try {
			id = Long.parseLong(VisitID);
		} catch (NumberFormatException ne) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Retrieve Obstetrics Office Visit",
					"Unable to Retrieve Obstetrics Office Visit", null);
			return null;
		}
		try {
			return OBOVData.getByID(id);
		} catch (Exception e) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Retrieve Obstetrics Office Visit",
					"Unable to Retrieve Obstetrics Office Visit", null);
			return null;
		}
	}
	
	public Ultrasound getVisitByUltraID(String UltraID) {
		long id = -1;
		try {
			id = Long.parseLong(UltraID);
		} catch (NumberFormatException ne) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Retrieve Obstetrics Office Visit",
					"Unable to Retrieve Obstetrics Office Visit", null);
			return null;
		}
		try {
			return OBOVData.getUltrasoundByID(id);
		} catch (Exception e) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Retrieve Obstetrics Office Visit",
					"Unable to Retrieve Obstetrics Office Visit", null);
			return null;
		}
	}
	
	/**
	 * @param pid
	 *            patient mid
	 * @return sorted list of obstetrics initializations for the given patient
	 */
	public List<ObstetricsOfficeVisit> getObstetricsVisitForPatient(String pid) {
		List<ObstetricsOfficeVisit> ret = Collections.emptyList();
		long mid = -1;
		if ((pid != null) && ValidationFormat.NPMID.getRegex().matcher(pid).matches()) {
			mid = Long.parseLong(pid);
			try {
				ret = OBOVData.getVisitsForPatient(mid).stream().sorted((o1, o2) -> {
					return o2.getDate().compareTo(o1.getDate());
				}).collect(Collectors.toList());
			} catch (Exception e) {
				printFacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Retrieve Obstetrics Office Visit",
						"Unable to Retrieve Obstetrics Office Visit", null);
			}
		}
		return ret;
	}
	
	/**
	 * @return list of obstetrics office visit sorted by date, empty list if no office
	 *         visit exists
	 */
	public List<ObstetricsOfficeVisit> getObstetricsVisitsForCurrentPatient() {
		return getObstetricsVisitForPatient(sessionUtils.getCurrentPatientMID());
	}
	
	/**
	 * @param patientID
	 *            Patient MID
	 * @return true if selected patient MID has at least 1 office visit, false
	 *         otherwise
	 */
	public boolean hasPatientVisited(String patientID) {
		boolean ret = false;
		if ((patientID != null) && (ValidationFormat.NPMID.getRegex().matcher(patientID).matches())) {
			if (getObstetricsVisitForPatient(patientID).size() > 0) {
				ret = true;
			}
		}
		return ret;
	}
	
	public boolean hasCurrentPatientVisited() {
		return hasPatientVisited(sessionUtils.getCurrentPatientMID());
	}
	
	/**
	 * Computes the difference between the LMP and Date of an obstetrics office visit for a given patient.
	 * @param patientMID
	 * 			Patient MID
	 * @return The difference between the current office visit date and LMP in weeks.
	 */
	public long calculateWeeksPregnant(long patientMID) {
		long diffInMS = 0;
		diffInMS = new Date().getTime() - OBOVData.getPatientLMP(patientMID).getTime();
		return TimeUnit.DAYS.convert(diffInMS, TimeUnit.MILLISECONDS) / 7;
	}
	
	public long calculateCurrentPatientWeeksPregnant() {
		return calculateWeeksPregnant(sessionUtils.getCurrentPatientMIDLong());
	}
	
	/**
	 * Gets the date of birth for the given patient from the patient's records in the database.
	 * @param patientMID
	 * 			Patient MID
	 * @return The patient's date of birth
	 */
	public Date getPatientDOB(final long patientMID) {
		return OBOVData.getPatientDOB(patientMID);
	}
	
	/**
	 * Checks if the current patient is a valid patient for an obstetrics office visit
	 * @return true if the patient is valid, false if not
	 */
	public boolean isCurrentPatientValid() {
		try {
			return calculateWeeksPregnant(sessionUtils.getCurrentPatientMIDLong()) < 49;
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	/**
	 * Creates a new appointment based on the current weeks pregnant of the patient
	 * @param ov the obstetrics office visit being created
	 */
	public void createNextAppointment(ObstetricsOfficeVisit ov) {
		DAOFactory factory = DAOFactory.getProductionInstance();
		ApptBean apt = new ApptBean();
		Calendar c = Calendar.getInstance(); 
		c.setTime(ov.getDate());
		if (ov.getNumWeeks() <= 13) {
			c.add(Calendar.DATE, 28);
			apt.setApptType("Ultrasound");
		} else if (ov.getNumWeeks() <= 28) {
			c.add(Calendar.DATE, 14);
			apt.setApptType("Ultrasound");
		} else if (ov.getNumWeeks() <= 40) {
			c.add(Calendar.DATE, 7);
			apt.setApptType("Ultrasound");
		} else if (ov.getNumWeeks() <= 42) {
			c.add(Calendar.DATE, 2);
			apt.setApptType("Ultrasound");
		} else if (ov.getNumWeeks() >= 42) {
			c.add(Calendar.DATE, 1);
			apt.setApptType(ov.getPrefferedMethod());
		}
		
		apt.setDate(new Timestamp(c.getTimeInMillis()));
		apt.setHcp(sessionUtils.getSessionLoggedInMIDLong());
		apt.setPatient(ov.getPatientMID());
		apt.setComment(null);
		apt.setPrice(0);
		AddApptAction action = new AddApptAction(factory, sessionUtils.getSessionLoggedInMIDLong());
		// While there are conflicts at the current Day and Time
		try {
			while (action.addAppt(apt, false).equals("Warning! This appointment conflicts with other appointments")) {
				// If the current day is a weekday
				if (apt.getDate().getDay() > 0 && apt.getDate().getDay() < 6) {
					// If the current time of day is during working hours
					if (apt.getDate().getHours() > 9 && apt.getDate().getHours() < 16) {
						// Try to schedule an appointment at the next hour
						c.setTime(apt.getDate());
						c.add(Calendar.HOUR_OF_DAY, 1);
						apt.setDate(new Timestamp(c.getTimeInMillis()));
					} else { // If we have gone past the end of the current work day
						// Try to schedule an appointment on the next day at 9am
						c.setTime(apt.getDate());
						c.add(Calendar.DATE, 1);
						c.set(Calendar.HOUR_OF_DAY, 9);
						apt.setDate(new Timestamp(c.getTimeInMillis()));
					}
				} else { // If the current day is not a week day
					//Try to schedule an appointment on the next day at 9am
					c.setTime(apt.getDate());
					c.add(Calendar.DATE, 1);
					c.set(Calendar.HOUR_OF_DAY, 9);
					apt.setDate(new Timestamp(c.getTimeInMillis()));
				}
			}
		} catch (Exception e) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Create Appointment",
					"Unable to Create Appointment", null);
		}
	}

	public boolean isEditVisit() {
		String visitID = sessionUtils.getRequestParameter("visitID");
		if (visitID != null && !visitID.isEmpty()) {
			return true;
		}
		return false;
	}
	
	/**
	 * @return Obstetrics Office Visit of the selected visit ID
	 */
	public ObstetricsOfficeVisit getSelectedVisit() {
		String visitID = sessionUtils.getRequestParameter("visitID");
		if (visitID == null || visitID.isEmpty()) {
			return null;
		}
		return getVisitByID(visitID);
	}
	
	public void close() {
		OBOVData.close();
	}
}
