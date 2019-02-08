package edu.ncsu.csc.itrust.controller.obstetricsInitialization;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
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
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.ValidationFormat;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitialization;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitializationData;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitializationMySQL;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.PreviousPregnancyInfo;
import edu.ncsu.csc.itrust.model.old.beans.PersonnelBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

@ManagedBean(name= "obstentrics_initialization_controller")
@SessionScoped
public class ObstetricsInitializationController extends iTrustController {
	/**
	 * Constant for the error message to be displayed if the Office Visit is
	 * invalid.
	 */
	private static final String OBS_INI_CANNOT_BE_UPDATED = "Invalid Obstetrics Initialization";

	/**
	 * Constant for the message to be displayed if the office visit was
	 * unsuccessfully updated
	 */
	private static final String OBS_INI_CANNOT_BE_CREATED = "Obstetrics Initialization Cannot Be Updated";

	/**
	 * Constant for the message to be displayed if the office visit was
	 * successfully created
	 */
	private static final String OBS_INI_SUCCESSFULLY_CREATED = "Obstetrics Initialization Successfully Created";

	/**
	 * Constant for the message to be displayed if the office visit was
	 * unsuccessfully updated
	 */
	private static final String PREV_PREG_CANNOT_BE_CREATED = "Previous Pregnancy Information Cannot Be Updated";

	/**
	 * Constant for the message to be displayed if the office visit was
	 * successfully created
	 */
	private static final String PREV_PREG_SUCCESSFULLY_CREATED = "Previous Pregnancy Information Successfully Created";

	/**
	 * Constant for the message to be displayed if the office visit was
	 * successfully updated
	 */
	private static final String OBS_INI_SUCCESSFULLY_UPDATED = "Obstetrics Initialization Successfully Updated";

	private ObstetricsInitializationData obsIniData;
	private SessionUtils sessionUtils;
	
	public ObstetricsInitializationController() throws DBException{
		try {
			obsIniData = new ObstetricsInitializationMySQL();
		} catch (DBException e) {
			obsIniData = new ObstetricsInitializationMySQL(ConverterDAO.getDataSource());
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
	public ObstetricsInitializationController(DataSource ds, SessionUtils sessionUtils) {
		obsIniData = new ObstetricsInitializationMySQL(ds);
		this.sessionUtils = sessionUtils;
	}
	
	/**
	 * For testing purposes
	  * @param ds
	 *            DataSource
	 */
	public ObstetricsInitializationController(DataSource ds) {
		obsIniData = new ObstetricsInitializationMySQL(ds);
		this.sessionUtils = SessionUtils.getInstance();
	}
	/**
	 * Adding obstetrics initialization used in testing.
	 * 
	 * @param oi
	 *            Obstetrics Initialization
	 * @return true if successfully added, false if otherwise
	 */
	public boolean addReturnResult(ObstetricsInitialization oi) {
		boolean res = false;

		try {
			res = obsIniData.add(oi);
		} catch (Exception e) {
			// do nothing
		}
		if (res) {
			// FIX
			logEditBasicHealthInformation();
		}

		return res;
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
	public long addReturnGeneratedId(ObstetricsInitialization oi) {
		long generatedId = -1;

		try {
			generatedId = obsIniData.addReturnGeneratedId(oi);
		} catch (DBException e) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, OBS_INI_CANNOT_BE_CREATED, e.getExtendedMessage(), null);
		} catch (Exception e) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, OBS_INI_CANNOT_BE_CREATED,
					OBS_INI_CANNOT_BE_CREATED, null);
		}

		if (generatedId >= 0) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, OBS_INI_SUCCESSFULLY_CREATED,
					OBS_INI_SUCCESSFULLY_CREATED, null);
			logEditBasicHealthInformation();
		}

		return generatedId;
	}
	
	public long addReturnGeneratedId(PreviousPregnancyInfo oi) {
		long generatedId = -1;

		try {
			generatedId = obsIniData.addReturnGeneratedId(oi);
		} catch (DBException e) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, OBS_INI_CANNOT_BE_CREATED, e.getExtendedMessage(), null);
		} catch (Exception e) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, OBS_INI_CANNOT_BE_CREATED,
					OBS_INI_CANNOT_BE_CREATED, null);
		}

		if (generatedId >= 0) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, OBS_INI_SUCCESSFULLY_CREATED,
					OBS_INI_SUCCESSFULLY_CREATED, null);
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

	public void redirectToBaseObstetricsInitialization() throws IOException {
		if (FacesContext.getCurrentInstance() != null) {
			NavigationController.baseObstetricsInitialization();
		}
	}

	public void add(ObstetricsInitialization ov) {
		boolean res = false;

		try {
			res = obsIniData.add(ov);
		} catch (DBException e) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, OBS_INI_CANNOT_BE_CREATED, e.getExtendedMessage(),
					null);
		} catch (Exception e) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, OBS_INI_CANNOT_BE_CREATED,
					OBS_INI_CANNOT_BE_CREATED, null);
		}
		if (res) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, OBS_INI_SUCCESSFULLY_CREATED,
					OBS_INI_SUCCESSFULLY_CREATED, null);
			logEditBasicHealthInformation();
		}
	}
	
	public void add(PreviousPregnancyInfo pi) {
		boolean res = false;

		try {
			res = obsIniData.add(pi);
		} catch (DBException e) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, PREV_PREG_CANNOT_BE_CREATED, e.getExtendedMessage(),
					null);
		} catch (Exception e) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, PREV_PREG_CANNOT_BE_CREATED,
					PREV_PREG_CANNOT_BE_CREATED, null);
		}
		if (res) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, PREV_PREG_SUCCESSFULLY_CREATED,
					PREV_PREG_SUCCESSFULLY_CREATED, null);
			logEditBasicHealthInformation();
		}
	}

	/**
	 * @param pid
	 *            patient mid
	 * @return sorted list of obstetrics initializations for the given patient
	 */
	public List<ObstetricsInitialization> getObstetricsInitializationForPatient(String pid) {
		List<ObstetricsInitialization> ret = Collections.emptyList();
		long mid = -1;
		if ((pid != null) && ValidationFormat.NPMID.getRegex().matcher(pid).matches()) {
			mid = Long.parseLong(pid);
			try {
				ret = obsIniData.getVisitsForPatient(mid).stream().sorted((o1, o2) -> {
					return o2.getDate().compareTo(o1.getDate());
				}).collect(Collectors.toList());
			} catch (Exception e) {
				printFacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Retrieve Obstetrics Initializations",
						"Unable to Retrieve Obstetrics Initializations", null);
			}
		}
		return ret;
	}
	
	/**
	 * @param pid
	 *            patient mid
	 * @return sorted list of previous pregnancies for the given patient
	 */
	public List<PreviousPregnancyInfo> getPreviousPregnancyInfoForPatient(String pid) {
		List<PreviousPregnancyInfo> ret = Collections.emptyList();
		long mid = -1;
		if ((pid != null) && ValidationFormat.NPMID.getRegex().matcher(pid).matches()) {
			mid = Long.parseLong(pid);
			try {
				ret = obsIniData.getPrevPregForPatient(mid);
			} catch (Exception e) {
				printFacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Retrieve Previous Pregnancy Info",
						"Unable to Retrieve Previous Pregnancy Info", null);
			}
		}
		return ret;
	}
	
	public List<PreviousPregnancyInfo> getPreviousPregnancyInfoForCurrentPatient() {
		return getPreviousPregnancyInfoForPatient(sessionUtils.getCurrentPatientMID());
	}
	
	/**
	 * @param pid
	 *            patient mid
	 * @return sorted list of previous pregnancies for the given patient
	 */
	public boolean doesPatientHavePreviousPregnancy(String pid) {
		List<PreviousPregnancyInfo> ret = Collections.emptyList();
		long mid = -1;
		if ((pid != null) && ValidationFormat.NPMID.getRegex().matcher(pid).matches()) {
			mid = Long.parseLong(pid);
			try {
				ret = obsIniData.getPrevPregForPatient(mid);
			} catch (Exception e) {
				printFacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Retrieve Previous Pregnancy Info",
						"Unable to Retrieve Previous Pregnancy Info", null);
			}
		}
		if(ret == null || ret.isEmpty()){
			return false;
		}
		else{
			return true;
		}
	}
	
	public boolean doesCurrentPatientHavePreviousPregnancy() {
		return doesPatientHavePreviousPregnancy(sessionUtils.getCurrentPatientMID());
	}

	/**
	 * @return list of Obstetrics Initialization sorted by date, empty list if no 
	 * Obstetrics Initialization exists
	 */
	public List<ObstetricsInitialization> getObstetricsInitializationForCurrentPatient() {
		return getObstetricsInitializationForPatient(sessionUtils.getCurrentPatientMID());
	}

	public ObstetricsInitialization getVisitByID(String VisitID) {
		long id = -1;
		try {
			id = Long.parseLong(VisitID);
		} catch (NumberFormatException ne) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Retrieve Obstetrics Initialization",
					"Unable to Retrieve Obstetrics Initialization", null);
			return null;
		}
		try {
			return obsIniData.getByID(id);
		} catch (Exception e) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Retrieve ObstetricsInitialization",
					"Unable to Retrieve ObstetricsInitialization", null);
			return null;
		}
	}

	/**
	 * @return Obstetrics Initialization of the selected patient in the HCP session
	 */
	public ObstetricsInitialization getSelectedVisit() {
		String visitID = sessionUtils.getRequestParameter("visitID");
		if (visitID == null || visitID.isEmpty()) {
			return null;
		}
		return getVisitByID(visitID);
	}

	/**
	 * @param patientID
	 *            Patient MID
	 * @return true if selected patient MID has at least 1 Obstetrics Initialization, false
	 *         otherwise
	 */
	public boolean hasPatientVisited(String patientID) {
		boolean ret = false;
		if ((patientID != null) && (ValidationFormat.NPMID.getRegex().matcher(patientID).matches())) {
			if (getObstetricsInitializationForPatient(patientID).size() > 0) {
				ret = true;
			}
		}
		return ret;
	}

	/**
	 * @return true if patient selected in HCP session has at least 1 Obstetrics
	 * Initialization, false if otherwise
	 */
	public boolean CurrentPatientHasVisited() {
		return hasPatientVisited(sessionUtils.getCurrentPatientMID());
	}

	public void edit(ObstetricsInitialization ov) {
		boolean res = false;

		try {
			res = obsIniData.update(ov);
		} catch (DBException e) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, OBS_INI_CANNOT_BE_UPDATED, e.getExtendedMessage(),
					null);
		} catch (Exception e) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, OBS_INI_CANNOT_BE_UPDATED,
					OBS_INI_CANNOT_BE_UPDATED, null);
		}
		if (res) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, OBS_INI_SUCCESSFULLY_UPDATED,
					OBS_INI_SUCCESSFULLY_UPDATED, null);
			logEditBasicHealthInformation();
		}
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

	/**
	 * Logs that the currently selected Obstetrics Initialization has been viewed (if any
	 * Obstetrics Initialization is selected)
	 */
	public void logViewObstetricsInitialization() {
		Long id = getSessionUtils().getCurrentOfficeVisitId();
		if (id != null) {
			logTransaction(TransactionType.OFFICE_VISIT_VIEW, id.toString());
			ObstetricsInitialization ov = getVisitByID(Long.toString(id));
		}
	}
	
	/**
	 * Logs that the current user viewed a patient's health metrics page
	 */
	public void logViewHealthMetrics(){
	    String role = sessionUtils.getSessionUserRole();
	    if ("hcp".equals(role)){
	        logTransaction(TransactionType.HCP_VIEW_BASIC_HEALTH_METRICS, "");
	    } else if ("patient".equals(role)){
	        logTransaction(TransactionType.PATIENT_VIEW_BASIC_HEALTH_METRICS, Long.parseLong(sessionUtils.getSessionLoggedInMID()), null, "");
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

	public void close() {
		obsIniData.close();
		
	}

	
}
