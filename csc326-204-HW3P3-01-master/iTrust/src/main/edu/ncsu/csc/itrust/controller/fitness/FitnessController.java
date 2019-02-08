package edu.ncsu.csc.itrust.controller.fitness;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
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
import edu.ncsu.csc.itrust.model.ValidationFormat;
import edu.ncsu.csc.itrust.model.fitness.Fitness;
import edu.ncsu.csc.itrust.model.fitness.FitnessData;
import edu.ncsu.csc.itrust.model.fitness.FitnessMySQL;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

/**
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
@ManagedBean(name = "fitness_controller")
@SessionScoped
public class FitnessController extends iTrustController {

	/**
	 * Constant for the error message to be displayed if the Fitness data is
	 * invalid.
	 */
	private static final String FITNESS_CANNOT_BE_UPDATED = "Invalid Fitness Data";
	/**
	 * Constant for the message to be displayed if the fitness data was
	 * unsuccessfully updated
	 */
	private static final String FITNESS_CANNOT_BE_CREATED = "Fitness Data Cannot Be Updated";
	/**
	 * Constant for the message to be displayed if the fitness data was
	 * successfully created
	 */
	private static final String FITNESS_SUCCESSFULLY_CREATED = "Fitness Data Successfully Created";
	/**
	 * Constant for the message to be displayed if the fitness data was
	 * successfully updated
	 */
	private static final String FITNESS_SUCCESSFULLY_UPDATED = "Fitness Data Successfully Updated";

	/**
	 * year
	 */
	private int year;

	/**
	 * day
	 */
	private int day;

	/**
	 * month
	 */
	private int month;

	/**
	 * date from form
	 */
	private LocalDate date;

	/**
	 * String representation of date
	 */
	private String dateString;

	/**
	 * FitnessData object
	 */
	private FitnessData fitnessData;

	/**
	 * SessionUtils object
	 */
	private SessionUtils sessionUtils;

	/**
	 * default constructor
	 * 
	 * @throws DBException
	 */
	public FitnessController() throws DBException {
		fitnessData = new FitnessMySQL();
		sessionUtils = SessionUtils.getInstance();
	}

	/**
	 * For testing purposes
	 * 
	 * @param ds
	 *            DataSource
	 */
	public FitnessController(DataSource ds) {
		fitnessData = new FitnessMySQL(ds);
		sessionUtils = SessionUtils.getInstance();
	}

	/**
	 * For testing purposes
	 * 
	 * @param ds
	 *            DataSource
	 * @param sessionUtils
	 *            SessionUtils instance
	 */
	public FitnessController(DataSource ds, SessionUtils sessionUtils) {
		fitnessData = new FitnessMySQL(ds);
		this.sessionUtils = sessionUtils;
	}

	/**
	 * Add Fitness data
	 * 
	 * @param fitness
	 *            Fitness data to add
	 */
	public void add(Fitness fitness) {
		boolean res = false;
		try {
			res = fitnessData.add(fitness);
		} catch (DBException e) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, FITNESS_CANNOT_BE_CREATED, e.getExtendedMessage(), null);
		} catch (Exception e) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, FITNESS_CANNOT_BE_CREATED, FITNESS_CANNOT_BE_CREATED, null);
		}
		if (res) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, FITNESS_SUCCESSFULLY_CREATED, FITNESS_SUCCESSFULLY_CREATED,
					null);
			// logEditBasicHealthInformation();
		}
	}

	/**
	 * Add fitness to the database and return the generated FitnessID.
	 * 
	 * @param fitness
	 *            Fitness to add to the database
	 * @return FitnessID generated from the database insertion, -1 if nothing
	 *         was generated
	 * @throws DBException
	 *             if error occurred in inserting fitness
	 */
	public long addReturnGeneratedID(Fitness fitness) {
		long generatedID = -1;
		try {
			generatedID = fitnessData.addReturnGeneratedID(fitness);
		} catch (DBException e) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, FITNESS_CANNOT_BE_CREATED, e.getExtendedMessage(), null);
		} catch (Exception e) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, FITNESS_CANNOT_BE_CREATED, FITNESS_CANNOT_BE_CREATED, null);
		}
		if (generatedID >= 0) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, FITNESS_SUCCESSFULLY_CREATED, FITNESS_SUCCESSFULLY_CREATED,
					null);
			// logEditBasicHealthInformation();
		}
		return generatedID;
	}

	/**
	 * Adding fitness used in testing.
	 * 
	 * @param fitness
	 *            Fitness
	 * @return true if successfully added, false if otherwise
	 */
	public boolean addReturnResult(Fitness fitness) {
		boolean res = false;
		try {
			res = fitnessData.add(fitness);
		} catch (Exception e) {
			// do nothing
		}
		if (res) {
			// logEditBasicHealthInformation();
		}
		return false;
	}

	/**
	 * @return true if patient selected in HCP session has at least 1 fitness
	 *         data, false if otherwise
	 */
	public boolean currentPatientHasFitness() {
		return patientHasFitness(sessionUtils.getCurrentPatientMID());
	}

	/**
	 * Edit fitness data with data from a new fitness object
	 * 
	 * @param fitness
	 *            new fitness data
	 */
	public void edit(Fitness fitness) {
		boolean res = false;
		try {
			res = fitnessData.update(fitness);
		} catch (DBException e) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, FITNESS_CANNOT_BE_UPDATED, e.getExtendedMessage(), null);
		} catch (Exception e) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, FITNESS_CANNOT_BE_UPDATED, FITNESS_CANNOT_BE_UPDATED, null);
		}
		if (res) {
			printFacesMessage(FacesMessage.SEVERITY_INFO, FITNESS_SUCCESSFULLY_UPDATED, FITNESS_SUCCESSFULLY_UPDATED,
					null);
			// logEditBasicHealthInformation();
		}
	}

	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Get fitness data for current patient on given date
	 * 
	 * @param date
	 *            date to retrieve
	 * @return fitness data for given date
	 */
	public Fitness getDateForCurrentPatient(LocalDate date) {
		return getDateForPatient(sessionUtils.getCurrentPatientMID(), date);
	}

	/**
	 * Retrieve fitness data for a given patient on a given date
	 * 
	 * @param pid
	 *            patient id
	 * @param date
	 *            date to retrieve
	 * @return fitness data on the given date
	 */
	public Fitness getDateForPatient(String pid, LocalDate date) {
		Fitness ret = null;
		long mid = -1;
		if ((pid != null) && ValidationFormat.NPMID.getRegex().matcher(pid).matches()) {
			mid = Long.parseLong(pid);
			try {
				ret = fitnessData.getFitnessByDate(mid, date);
			} catch (Exception e) {
				printFacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Retrieve Fitness Data",
						"Unable to Retrieve Fitness Data", null);
			}
		}
		return ret;
	}

	/**
	 * Get fitness data in a date range for the current patient
	 * 
	 * @param startDate
	 *            start of range
	 * @param endDate
	 *            end of range
	 * @return fitness data within date range
	 */
	public List<Fitness> getDateRangeForCurrentPatient(LocalDate startDate, LocalDate endDate) {
		return getDateRangeForPatient(sessionUtils.getCurrentPatientMID(), startDate, endDate);
	}

	/**
	 * Retrieves fitness data for a patient in a given date range
	 * 
	 * @param pid
	 *            patient id
	 * @param startDate
	 *            start of range
	 * @param endDate
	 *            end of range
	 * @return fitness data in date range
	 */
	public List<Fitness> getDateRangeForPatient(String pid, LocalDate startDate, LocalDate endDate) {
		List<Fitness> ret = Collections.emptyList();
		long mid = -1;
		if ((pid != null) && ValidationFormat.NPMID.getRegex().matcher(pid).matches()) {
			mid = Long.parseLong(pid);
			try {
				ret = fitnessData.getFitnessDateRange(mid, startDate, endDate).stream().sorted((o1, o2) -> {
					return o2.getDate().compareTo(o1.getDate());
				}).collect(Collectors.toList());
			} catch (Exception e) {
				printFacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Retrieve Fitness Data",
						"Unable to Retrieve Fitness Data", null);
			}
		}
		return ret;
	}

	/**
	 * @return the dateString
	 */
	public String getDateString() {
		return dateString;
	}

	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Get Fitness data by ID
	 * 
	 * @param FitnessID
	 *            ID of data
	 * @return Fitness object
	 */
	public Fitness getFitnessByID(String FitnessID) {
		long id = -1;
		try {
			id = Long.parseLong(FitnessID);
		} catch (NumberFormatException ne) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Retrieve Fitness Data",
					"Unable to Retrieve Fitness Data", null);
			return null;
		}
		try {
			return fitnessData.getByID(id);
		} catch (Exception e) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Retrieve Fitness Data",
					"Unable to Retrieve Fitness Data", null);
			return null;
		}
	}

	/**
	 * @return list of fitness data sorted by date, empty list if no fitness
	 *         data exists
	 */
	public List<Fitness> getFitnessForCurrentPatient() {
		return getFitnessForPatient(sessionUtils.getCurrentPatientMID());
	}

	/**
	 * @param pid
	 *            patient mid
	 * @return sorted list of fitness data for the given patient
	 */
	public List<Fitness> getFitnessForPatient(String pid) {
		List<Fitness> ret = Collections.emptyList();
		long mid = -1;
		if ((pid != null) && ValidationFormat.NPMID.getRegex().matcher(pid).matches()) {
			mid = Long.parseLong(pid);
			try {
				ret = fitnessData.getFitnessForPatient(mid).stream().sorted((o1, o2) -> {
					return o2.getDate().compareTo(o1.getDate());
				}).collect(Collectors.toList());
			} catch (Exception e) {
				printFacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Retrieve Fitness Data",
						"Unable to Retrieve Fitness Data", null);
			}
		}
		return ret;
	}

	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @param patientID
	 *            The patient's MID
	 * @return the patient's date of birth
	 */
	public LocalDate getPatientDOB(Long patientID) {
		return fitnessData.getPatientDOB(patientID);
	}

	/**
	 * @return Fitness of the selected patient in the HCP session
	 */
	public Fitness getSelectedFitness() {
		String fitnessID = sessionUtils.getRequestParameter("fitnessID");
		if (fitnessID == null || fitnessID.isEmpty()) {
			return null;
		}
		return getFitnessByID(fitnessID);
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param patientID
	 *            Patient MID
	 * @return true if selected patient MID has at least 1 fitness data, false
	 *         otherwise
	 */
	public boolean patientHasFitness(String patientID) {
		boolean ret = false;
		if ((patientID != null) && (ValidationFormat.NPMID.getRegex().matcher(patientID).matches())) {
			if (getFitnessForPatient(patientID).size() > 0) {
				ret = true;
			}
		}
		return ret;
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
	@Override
	public void printFacesMessage(Severity severity, String summary, String detail, String clientId) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		if (ctx == null) {
			return;
		}
		ctx.getExternalContext().getFlash().setKeepMessages(true);
		ctx.addMessage(clientId, new FacesMessage(severity, summary, detail));
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * @param dateString
	 *            the dateString to set
	 */
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	/**
	 * @param day
	 *            the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * @param month
	 *            the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * Stores the date from the form
	 * 
	 * @throws IOException
	 * @throws DBException
	 */
	public void submitDate() throws IOException, DBException {
		dateString = year + "-" + month + "-" + day;
		date = Date.valueOf(dateString).toLocalDate();
		Long id = getDateForCurrentPatient(date).getFitnessID();
		NavigationController.editFitnessHealth(id);
	}
}
