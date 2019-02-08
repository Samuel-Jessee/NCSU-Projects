package edu.ncsu.csc.itrust.controller.childbirth;

import javax.sql.DataSource;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import edu.ncsu.csc.itrust.action.AddApptAction;
import edu.ncsu.csc.itrust.action.EditApptAction;
import edu.ncsu.csc.itrust.action.ViewMyApptsAction;
import edu.ncsu.csc.itrust.controller.iTrustController;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.childbirth.ChildInChildbirth;
import edu.ncsu.csc.itrust.model.childbirth.Childbirth;
import edu.ncsu.csc.itrust.model.childbirth.ChildbirthData;
import edu.ncsu.csc.itrust.model.childbirth.ChildbirthMySQL;
import edu.ncsu.csc.itrust.model.old.beans.ApptBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ApptDAO;
import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.ObstetricsOfficeVisit;
import edu.ncsu.csc.itrust.model.user.patient.Patient;
import edu.ncsu.csc.itrust.webutils.SessionUtils;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;


@ManagedBean(name= "childbirth_controller")
@SessionScoped
public class ChildbirthController extends iTrustController {

	/**
	 * Constant for the error message to be displayed if the Childbirth is invalid.
	 */
	private static final String CHILDBIRTH_CANNOT_BE_UPDATED = "Invalid Childbirth";

	/**
	 * Constant for the message to be displayed if the Childbirth was unsuccessfully updated
	 */
	private static final String CHILDBIRTH_CANNOT_BE_CREATED = "Childbirth Cannot Be Updated";
	
	/**
	 * Constant for the message to be displayed if the Childbirth was unsuccessfully retrieved
	 */
	private static final String CHILDBIRTH_CANNOT_BE_RETRIEVED = "Unable to Retrieve Childbirth";

	/**
	 * Constant for the message to be displayed if the Childbirth was successfully created
	 */
	private static final String CHILDBIRTH_SUCCESSFULLY_CREATED = "Childbirth Successfully Created";

	/**
	 * Constant for the message to be displayed if the Childbirth was successfully updated
	 */
	private static final String CHILDBIRTH_SUCCESSFULLY_UPDATED = "Childbirth Successfully Updated";
	
	
	private ChildbirthMySQL CBData;
	private SessionUtils sessionUtils;
	private ApptBean cbAppt;
	private String prefMethod;
	
	public ChildbirthController() throws DBException{
		CBData = new ChildbirthMySQL();
		sessionUtils = SessionUtils.getInstance();
	}
	
	/**
	 * For testing purposes
	  * @param ds
	 *            DataSource
	 * @param sessionUtils
	 *            SessionUtils instance
	 */
	public ChildbirthController(DataSource ds, SessionUtils sessionUtils) {
		CBData = new ChildbirthMySQL(ds);
		this.sessionUtils = sessionUtils;
	}
	
	/**
	 * For testing purposes
	  * @param ds
	 *            DataSource
	 */
	public ChildbirthController(DataSource ds) {
		CBData = new ChildbirthMySQL(ds);
		this.sessionUtils = SessionUtils.getInstance();
	}
	
	/**
	 * @return the cbAppt
	 */
	public ApptBean getCbAppt() {
		return cbAppt;
	}

	/**
	 * @param cbAppt the cbAppt to set
	 */
	public void setCbAppt(ApptBean cbAppt) {
		this.cbAppt = cbAppt;
	}

	/**
	 * @return the prefMethod
	 */
	public String getPrefMethod() {
		return prefMethod;
	}

	/**
	 * @param prefMethod the prefMethod to set
	 */
	public void setPrefMethod(String prefMethod) {
		this.prefMethod = prefMethod;
	}

	public boolean addReturnResult(Childbirth cb) {
		boolean res = false;

		try {
			res = CBData.add(cb, sessionUtils.getSessionLoggedInMIDLong() ,sessionUtils.getCurrentPatientMIDLong());
		} catch (Exception e) {
			// do nothing
		}
		if (res) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, CHILDBIRTH_SUCCESSFULLY_CREATED, CHILDBIRTH_SUCCESSFULLY_CREATED, null);
			logCreateChildBirthVisit();
		}
		return res;
	}
	
	/**
	 * Gets the list of all childbirth data in the iTrust system. Returns null if there is a problem.
	 * @return
	 */
	public List<Childbirth> getAll() {
		try {
			return CBData.getAll();
		} catch (DBException e) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, CHILDBIRTH_CANNOT_BE_RETRIEVED, e.getExtendedMessage(), null);
			return null;
		}
	}
	
	/**
	 * Gets the childbirth in the iTrust records with the given MID.
	 * @param id 
	 * 			the MID of the child record to be retrieved
	 * @return the record with the given MID or null if it doesn't exist or an error occurs.
	 */
	public Childbirth getByID(long id) {
		try {
			return CBData.getByID(id);
		} catch (DBException e) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, CHILDBIRTH_CANNOT_BE_RETRIEVED, e.getExtendedMessage(), null);
			return null;
		}
	}
	
	/**
	 * 
	 * @param cb
	 * @return
	 */
	public boolean update(Childbirth cb) {
		try {
			boolean result = CBData.update(cb);
			printFacesMessage(FacesMessage.SEVERITY_INFO, CHILDBIRTH_SUCCESSFULLY_UPDATED, CHILDBIRTH_SUCCESSFULLY_UPDATED, null);
			logEditChildBirth();
			ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
			if (ctx != null)
				ctx.redirect("/iTrust/auth/hcp-uap/childbirthList.xhtml");
			return result;
		} catch (DBException e) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, CHILDBIRTH_CANNOT_BE_UPDATED, e.getExtendedMessage(), null);
			return false;
		} catch (Exception e) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, CHILDBIRTH_CANNOT_BE_UPDATED, CHILDBIRTH_CANNOT_BE_UPDATED, null);
			return false;
		}
	}
	
	/**
	 * 
	 * @param cb
	 * @return
	 */
	public long addReturnGeneratedID(Childbirth cb) {
		long id = -1;
		try {
			id = CBData.addReturnGeneratedId(cb, sessionUtils.getSessionLoggedInMIDLong(), sessionUtils.getCurrentPatientMIDLong());
		} catch (DBException e) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, CHILDBIRTH_CANNOT_BE_CREATED, e.getExtendedMessage(), null);
		}
		if (id >= 0) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, CHILDBIRTH_SUCCESSFULLY_CREATED, CHILDBIRTH_SUCCESSFULLY_CREATED, null);
			logCreateChildBirthVisit();
			// Remove childbirth appointment after childbirth is created
			EditApptAction apptEdit = new EditApptAction(DAOFactory.getProductionInstance(), sessionUtils.getSessionLoggedInMIDLong());
			try {
				apptEdit.removeAppt(cbAppt);
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				if (ctx != null)
					ctx.redirect("/iTrust/auth/hcp-uap/childbirthList.xhtml");
			} catch (DBException | SQLException | IOException e) {
				e.printStackTrace();
			}
		}
		return id;
	}
	
	/**
	 * 
	 * @param mid
	 * @return
	 */
	public List<Childbirth> getChildbirthsForPatient(long mid) {
		try {
			return CBData.getChildbirthsForPatient(mid);
		} catch (DBException e) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, CHILDBIRTH_CANNOT_BE_RETRIEVED, e.getExtendedMessage(), null);
			return null;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Childbirth> getChildbirthsForCurrentPatient() {
		return getChildbirthsForPatient(sessionUtils.getCurrentPatientMIDLong());
	}
	
	public boolean CurrentPatientHasVisited() {
		if (sessionUtils.getCurrentPatientMIDLong() == null) {
			return false;
		}
		if (getChildbirthsForCurrentPatient() == null || getChildbirthsForCurrentPatient().isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isNewVisit() {
		String visitID = sessionUtils.getRequestParameter("visitID");
		if (visitID != null && !visitID.isEmpty()) {
			return false;
		}
		return true;
	}
	
	/**
	 * @return Childbirth Office Visit of the selected visit ID
	 */
	public Childbirth getSelectedVisit() {
		String visitID = sessionUtils.getRequestParameter("visitID");
		if (visitID == null || visitID.isEmpty()) {
			return null;
		}
		return getByID(Long.parseLong(visitID));
	}
	
	/**
	 * Checks if the current patient has a childbirth appointment for the current day.
	 * Returns true if they do, false otherwise.
	 */
	public boolean isChildbirthAppointment() {
		DAOFactory factory = DAOFactory.getProductionInstance();
		if (sessionUtils.getCurrentPatientMIDLong() == null) {
			return false;
		}
		long patientMID = sessionUtils.getCurrentPatientMIDLong();
		ApptDAO apptDAO = new ApptDAO(factory);
		List<ApptBean> list = null;
		try {
			list = apptDAO.getAllApptsFor(patientMID);
		} catch (Exception e) {
			
		}
		for (int i = 0; i < list.size(); i++) {
			ApptBean tmpAppt = list.get(i);
			String tmpType = tmpAppt.getApptType();
			LocalDateTime tmpDate = tmpAppt.getDate().toLocalDateTime();
			LocalDateTime today = LocalDateTime.now();
			boolean sameDay = false;
			boolean sameType = false;
			if ( ( tmpDate.getYear() == today.getYear() ) && (tmpDate.getDayOfYear() == today.getDayOfYear()) ) {
				sameDay = true;
			}
			
			if (tmpType.equals("Childbirth,Vaginal Delivery") || 
					tmpType.equals("Childbirth,Vaginal Delivery Forceps Assist") || 
					tmpType.equals("Childbirth,Vaginal Delivery Vacuum Assist") || 
					tmpType.equals("Childbirth,Caesarean Section") ||
					tmpType.equals("Childbirth,Miscarriage")) {
				sameType = true;
				
			}
			if (sameDay && sameType) {
				setCbAppt(tmpAppt);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method to create a childbirth appointment in case of an Emergency Room visit
	 * Creates an appointment for 30 minutes from current time
	 */
	public void createEmergencyAppointment() {
		DAOFactory factory = DAOFactory.getProductionInstance();
		ApptBean erAppt = new ApptBean();
		Timestamp time = Timestamp.from(Instant.now());
		time.setTime(time.getTime() + (30*60*1000));
		erAppt.setDate(time); // Set appt date for 30 minutes from current time
		erAppt.setComment("Emergency Childbirth Appointment");
		erAppt.setHcp(sessionUtils.getSessionLoggedInMIDLong());
		erAppt.setPatient(sessionUtils.getCurrentPatientMIDLong());
		erAppt.setApptType(prefMethod);
		erAppt.setPrice(5000);
		AddApptAction apptAction = new AddApptAction(factory, sessionUtils.getSessionLoggedInMIDLong());
		try {
			apptAction.addAppt(erAppt, true);
		} catch (DBException | FormValidationException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**Logs that a child birth visit is being created **/
	private void logCreateChildBirthVisit() {
		logTransaction(TransactionType.CREATE_CHILDBIRTH, "");
		logAddChildBirthDrugs();
		logBabyBorn();
		logCreateBabyRecord();
	}
	
	/** Logs that childbirth drugs are being added to the childbirth visit **/
	private void logAddChildBirthDrugs() {
		logTransaction(TransactionType.ADD_CHILDBIRTH_DRUGS, "");
	}
	
	/** Logs that a baby is born **/
	private void logBabyBorn() {
		logTransaction(TransactionType.BABY_BORN, "");
	}
	
	/** logs that a baby record is being created **/
	private void logCreateBabyRecord() {
		logTransaction(TransactionType.CREATE_BABY, "");
	}
	
	/** logs that a child birth visit is being edited **/
	private void logEditChildBirth(){
		logTransaction(TransactionType.EDIT_CHILDBIRTH, "");
	}

	public void close() {
	    CBData.close();
		
	}

	
}