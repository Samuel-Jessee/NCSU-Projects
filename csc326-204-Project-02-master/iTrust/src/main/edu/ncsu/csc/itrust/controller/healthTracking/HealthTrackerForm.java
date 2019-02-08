package edu.ncsu.csc.itrust.controller.healthTracking;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.event.SelectEvent;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ValidationFormat;
import edu.ncsu.csc.itrust.model.healthTracking.HealthTracker;

@ManagedBean(name = "health_tracking_form")
@ViewScoped
public class HealthTrackerForm {
	private HealthTrackerController controller;
	private HealthTracker ht;
	private Long trackerID;
	private Date date;
	private int caloriesBurned;
	private int steps;
	private double distance;
	private int floors;
	private int minutesSedentary;
	private int minutesLightlyActive;
	private int minutesFairlyActive;
	private int minutesVeryActive;
	private int activityCalories;
	private int hrLowest;
	private int hrHighest;
	private int hrAverage;
	private int activeHours;
	private int uvExposure;
	private long patient;
	private long hcp;
	
	/**
	 * Returns the trackerID for this bean.
	 * 
	 * @return 
	 */
	public Long getTrackerID() {
		return trackerID;
	}

	/**
	 * Sets the trackerID for this bean.
	 * 
	 * @return 
	 */
	public void setTrackerID(Long trackerID) {
		this.trackerID = trackerID;
	}
	
	/**
	 * Returns the UV_Exposure for this bean.
	 * 
	 * @return 
	 */
	public int getUvExposure() {
		return uvExposure;
	}

	/**
	 * Sets the UVExposure for this bean.
	 * 
	 * @return 
	 */
	public void setUvExposure(int uvExposure) {
		this.uvExposure = uvExposure;
	}

	/**
	 * Returns the HR_Lowest for this bean.
	 * 
	 * @return 
	 */
	public int getHrLowest() {
		return hrLowest;
	}

	/**
	 * Sets the HR_Lowest for this bean.
	 * 
	 * @return 
	 */
	public void setHrLowest(int hrLowest) {
		this.hrLowest = hrLowest;
	}

	/**
	 * Returns the HRHighest for this bean.
	 * 
	 * @return 
	 */
	public int getHrHighest() {
		return hrHighest;
	}

	/**
	 * Sets the HR_Highest the date for this bean.
	 * 
	 * @return 
	 */
	public void setHrHighest(int hrHighest) {
		this.hrHighest = hrHighest;
	}

	/**
	 * Returns the HR_Average for this bean.
	 * 
	 * @return 
	 */
	public int getHrAverage() {
		return hrAverage;
	}

	/**
	 * Sets the HR_Average for this bean.
	 * 
	 * @return 
	 */
	public void setHrAverage(int hrAverage) {
		this.hrAverage = hrAverage;
	}

	/**
	 * Returns the ActiveHours for this bean.
	 * 
	 * @return 
	 */
	public int getActiveHours() {
		return activeHours;
	}

	/**
	 * Sets the ActiveHours for this bean.
	 * 
	 * @return 
	 */
	public void setActiveHours(int activeHours) {
		this.activeHours = activeHours;
	}

	/**
	 * Returns the date for this bean.
	 * 
	 * @return 
	 */
	public Date getDate() {
		if (date == null) {
			return null;
		}
		return (Date) date.clone();
	}

	/**
	 * Sets the date for this bean.
	 * 
	 * @param date
	 */
	public void setDate(Date date) {
		if (null != date)
			this.date = (Date) date.clone();
		else
			this.date = null;
	}

	/**
	 * Returns the caloriesBurned for this date.
	 * 
	 * @return
	 */
	public int getCaloriesBurned() {
		return caloriesBurned;
	}

	/**
	 * Sets the caloriesBurned for this date.
	 * 
	 * @param caloriesBurned
	 */
	public void setCaloriesBurned(int caloriesBurned) {
		this.caloriesBurned = caloriesBurned;
	}

	/**
	 * Returns the steps for this date.
	 * 
	 * @return
	 */
	public int getSteps() {
		return steps;
	}

	/**
	 * Sets the steps for this date.
	 * 
	 * @param steps
	 */
	public void setSteps(int steps) {
		this.steps = steps;
	}

	/**
	 * Returns the distance for this date.
	 * 
	 * @return
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * Sets the distance for this date.
	 * 
	 * @param distance
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * Gets the floors for this date.
	 * 
	 * @return
	 */
	public int getFloors() {
		return floors;
	}

	/**
	 * Sets the floors for this date.
	 * 
	 * @param floors
	 */
	public void setFloors(int floors) {
		this.floors = floors;
	}

	/**
	 * Gets the minutesSedentary for this date.
	 * 
	 * @return
	 */
	public int getMinutesSedentary() {
		return minutesSedentary;
	}

	/**
	 * Sets the minutesSedentary for this date.
	 * 
	 * @param minutesSedentary
	 */
	public void setMinutesSedentary(int minutesSedentary) {
		this.minutesSedentary = minutesSedentary;
	}

	/**
	 * Gets the minutesLightlyActive for this date.
	 * 
	 * @return
	 */
	public int getMinutesLightlyActive() {
		return minutesLightlyActive;
	}

	/**
	 * Sets the minutesLightlyActive for this date.
	 * 
	 * @param minutesLightlyActive
	 */
	public void setMinutesLightlyActive(int minutesLightlyActive) {
		this.minutesLightlyActive = minutesLightlyActive;
	}

	/**
	 * Gets the minutesFairlyActive for this date.
	 * 
	 * @return
	 */
	public int getMinutesFairlyActive() {
		return minutesFairlyActive;
	}

	/**
	 * Sets the minutesFairlyActive for this date.
	 * 
	 * @param minutesFairlyActive
	 */
	public void setMinutesFairlyActive(int minutesFairlyActive) {
		this.minutesFairlyActive = minutesFairlyActive;
	}

	/**
	 * Gets the minutesVeryActive for this date.
	 * 
	 * @return
	 */
	public int getMinutesVeryActive() {
		return minutesVeryActive;
	}

	/**
	 * Sets the minutesVeryActive for this date.
	 * 
	 * @param minutesVeryActive
	 */
	public void setMinutesVeryActive(int minutesVeryActive) {
		this.minutesVeryActive = minutesVeryActive;
	}

	/**
	 * Gets the activityCalories for this date.
	 * 
	 * @return
	 */
	public int getActivityCalories() {
		return activityCalories;
	}

	/**
	 * Sets the activityCalories for this date.
	 * 
	 * @param activityCalories
	 */
	public void setActivityCalories(int activityCalories) {
		this.activityCalories = activityCalories;
	}
	
	/**
	 * Gets the patient id.
	 * 
	 * @return
	 */
	public long getPatient() {
		return patient;
	}
	
	/**
	 * Sets the patient id.
	 * 
	 * @param patient
	 */
	public void setPatient(long patient) {
		this.patient = patient;
	}
	
	/**
	 * Gets the HCP id.
	 * 
	 * @return
	 */
	public long getHCP() {
		return hcp;
	}
	
	/**
	 * Sets the HCP id.
	 * 
	 * @param hcp
	 */
	public void setHCP(long hcp) {
		this.hcp = hcp;
	}

	/**
	 * Default constructor for HealthTrackerForm
	 */
	public HealthTrackerForm() {
		this(null);
	}
	
	/**
	 * Need to initialize form with patient and hcp id's
	 */
	@PostConstruct
	public void init() {
		long pid = -1;
		
		FacesContext ctx = FacesContext.getCurrentInstance();

		String patientID = "";
		try {	
		if (ctx.getExternalContext().getRequest() instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext().getRequest();
			HttpSession httpSession = req.getSession(false);
			patientID = (String) httpSession.getAttribute("pid");
			hcp = (Long) httpSession.getAttribute("loggedInMID");
		}
		if (ValidationFormat.NPMID.getRegex().matcher(patientID).matches()) {
			pid = Long.parseLong(patientID);
		}

		ht.setHCP(hcp);
		ht.setPatient(pid);
		} catch (NullPointerException e) {
			
		}
	}
	/**
	 * Determines if the file is MSBand or FitBit and calls the appropriate function.
	 * @param csvFileName
	 */
	public void importCSV(String csvFileName) {
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(csvFileName));
			line = br.readLine();
			String[] healthTrackingInfo = line.split(",");
			if (healthTrackingInfo[0].equals("Activities"))
				importFitBit(csvFileName);
			else if (healthTrackingInfo[0].equals("Date"))
				importMSBand(csvFileName);
			else
				throw new IOException("Not a FitBit or MSBand file.");
		} catch (FileNotFoundException e) {
			FacesMessage throwMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Health Tracker Form File Not Found",
					"Health Tracker Form File Not Found");
			FacesContext.getCurrentInstance().addMessage(null, throwMsg);
		} catch (IOException e) {
			FacesMessage throwMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Health Tracker Form Line Reader Error",
					"Health Tracker Form Line Reader Error");
			FacesContext.getCurrentInstance().addMessage(null, throwMsg);
		} finally {
			try {
				br.close();
			} catch (IOException | NullPointerException e) {
				FacesMessage throwMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Health Tracker Form File Closing Error",
						"Health Tracker Form File Closing Error");
				//FacesContext.getCurrentInstance().addMessage(null, throwMsg);
			}
		}
	}
	
	/**
	 * Parses a FitBit csv file and creates new HealthTrackers from the data.
	 * @param csvFileName
	 */
	public void importFitBit(String csvFileName) {
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(csvFileName));
			while ((line = br.readLine()) != null) {
				String[] healthTrackingInfo = line.split(",");
				if (healthTrackingInfo[0].equals("Date") || healthTrackingInfo[0].equals("Activities"))
					continue;
				for (int i = 0; i < healthTrackingInfo.length; i++) { // Rejoin numbers with commas in the middle
					if (!healthTrackingInfo[i].endsWith("\"") && healthTrackingInfo[i].startsWith("\"")) {
						healthTrackingInfo[i] += healthTrackingInfo[i+1];
						healthTrackingInfo[i] = healthTrackingInfo[i].replaceAll("\"", "");
						for (int j = i+1; j < healthTrackingInfo.length-1; j++) {
							healthTrackingInfo[j] = healthTrackingInfo[j+1];
						}
					}
					if (healthTrackingInfo[i].equals(""))
						healthTrackingInfo[i] = "-1";
				}
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				ht.setDate(formatter.parse(healthTrackingInfo[0]));
			ht.setCaloriesBurned(Integer.parseInt(healthTrackingInfo[1]));
				ht.setSteps(Integer.parseInt(healthTrackingInfo[2]));
				ht.setDistance(Double.parseDouble(healthTrackingInfo[3]));
				ht.setFloors(Integer.parseInt(healthTrackingInfo[4]));
				ht.setMinutesSedentary(Integer.parseInt(healthTrackingInfo[5]));
				ht.setMinutesLightlyActive(Integer.parseInt(healthTrackingInfo[6]));
				ht.setMinutesFairlyActive(Integer.parseInt(healthTrackingInfo[7]));
				ht.setMinutesVeryActive(Integer.parseInt(healthTrackingInfo[8]));
				ht.setActivityCalories(Integer.parseInt(healthTrackingInfo[9]));
				trackerID = controller.addReturnGeneratedId(ht);
				ht.setTrackerID(trackerID);
				//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("healthTrackerId", trackerID);
			}
		} catch (FileNotFoundException e) {
			FacesMessage throwMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Health Tracker Form File Not Found",
					"Health Tracker Form File Not Found");
			FacesContext.getCurrentInstance().addMessage(null, throwMsg);
		} catch (IOException e) {
			FacesMessage throwMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Health Tracker Form Line Reader Error",
					"Health Tracker Form Line Reader Error");
			FacesContext.getCurrentInstance().addMessage(null, throwMsg);
		} catch (java.text.ParseException e) {
			FacesMessage throwMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Health Tracker Form Date Reading Error",
					"Health Tracker Form Date Reading Error");
			FacesContext.getCurrentInstance().addMessage(null, throwMsg);
		} finally {
			try {
				br.close();
			} catch (IOException | NullPointerException e) {
				FacesMessage throwMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Health Tracker Form File Closing Error",
						"Health Tracker Form File Closing Error");
				FacesContext.getCurrentInstance().addMessage(null, throwMsg);
			}
		}
	}
	
	/**
	 * Parses a MSBand csv file and creates new HealthTrackers from the data.
	 * @param csvFileName
	 */
	public void importMSBand(String csvFileName) {
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(csvFileName));
			while ((line = br.readLine()) != null) {
				String[] healthTrackingInfo = line.split(",");
				if (healthTrackingInfo[0].equals("Date"))
					continue;
				for (int i = 0; i < healthTrackingInfo.length; i++) { // Rejoin numbers with commas in the middle
					if (!healthTrackingInfo[i].endsWith("\"") && healthTrackingInfo[i].startsWith("\"")) {
						healthTrackingInfo[i] += healthTrackingInfo[i+1];
						healthTrackingInfo[i] = healthTrackingInfo[i].replaceAll("\"", "");
						for (int j = i+1; j < healthTrackingInfo.length-1; j++) {
							healthTrackingInfo[j] = healthTrackingInfo[j+1];
						}
					}
					if (healthTrackingInfo[i].equals(""))
						healthTrackingInfo[i] = "-1";
				}
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				ht.setDate(formatter.parse(healthTrackingInfo[0]));
				ht.setSteps(Integer.parseInt(healthTrackingInfo[1]));
				ht.setCaloriesBurned(Integer.parseInt(healthTrackingInfo[2]));
				ht.setHrLowest(Integer.parseInt(healthTrackingInfo[3]));
				ht.setHrHighest(Integer.parseInt(healthTrackingInfo[4]));
				ht.setHrAverage(Integer.parseInt(healthTrackingInfo[5]));
				ht.setDistance(Double.parseDouble(healthTrackingInfo[6]));
				ht.setActiveHours(Integer.parseInt(healthTrackingInfo[7]));
				ht.setFloors(Integer.parseInt(healthTrackingInfo[8]));
				ht.setUVExposure(Integer.parseInt(healthTrackingInfo[9]));
				trackerID = controller.addReturnGeneratedId(ht);
				ht.setTrackerID(trackerID);
				//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("healthTrackerId", trackerID);
			}
		} catch (FileNotFoundException e) {
			FacesMessage throwMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Health Tracker Form File Not Found",
					"Health Tracker Form File Not Found");
			FacesContext.getCurrentInstance().addMessage(null, throwMsg);
		} catch (IOException e) {
			FacesMessage throwMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Health Tracker Form Line Reader Error",
					"Health Tracker Form Line Reader Error");
			FacesContext.getCurrentInstance().addMessage(null, throwMsg);
		} catch (java.text.ParseException e) {
			FacesMessage throwMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Health Tracker Form Date Reading Error",
					"Health Tracker Form Date Reading Error");
			FacesContext.getCurrentInstance().addMessage(null, throwMsg);
		} finally {
			try {
				br.close();
			} catch (IOException | NullPointerException e) {
				FacesMessage throwMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Health Tracker Form File Closing Error",
						"Health Tracker Form File Closing Error");
				FacesContext.getCurrentInstance().addMessage(null, throwMsg);
			}
		}
	}
	
	/**
	 * Constructor for OfficeVisitForm for testing purposes.
	 */
	public HealthTrackerForm(HealthTrackerController htc) {
		try {
			controller = (htc == null) ? new HealthTrackerController() : htc;
			ht = controller.getSelectedVisit();
			if (ht == null) {
				ht = new HealthTracker();
			}
			activityCalories = ht.getActivityCalories();
			caloriesBurned = ht.getCaloriesBurned();
			date = ht.getDate();
			distance = ht.getDistance();
			floors = ht.getFloors();
			hcp = ht.getHCP();
			minutesFairlyActive = ht.getMinutesFairlyActive();
			minutesLightlyActive = ht.getMinutesLightlyActive();
			minutesSedentary = ht.getMinutesSedentary();
			minutesVeryActive = ht.getMinutesVeryActive();
			patient = ht.getPatient();
			steps = ht.getSteps();
			hrLowest = ht.getHrLowest();
			hrHighest = ht.getHrHighest();
			hrAverage = ht.getHrAverage();
			activeHours = ht.getActiveHours();
			uvExposure = ht.getUVExposure();

		} catch (Exception e) {
			//FacesMessage throwMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Health Tracker Controller Error",
				//	"Health Tracker Controller Error");
			//FacesContext.getCurrentInstance().addMessage(null, throwMsg);
		}
	}
	
	/**
	 * Called when user clicks on the submit button in officeVisitInfo.xhtml. Takes data from form
	 * and sends to HealthTrackerMySQLLoader.java for storage and validation
	 * @throws DBException 
	 */
	public void submit() throws DBException {
		ht.setDate(date);
		
		ht.setCaloriesBurned(caloriesBurned);
		ht.setSteps(steps);
		ht.setDistance(distance);
		ht.setFloors(floors);
		ht.setMinutesSedentary(minutesSedentary);
		ht.setMinutesLightlyActive(minutesLightlyActive);
		ht.setMinutesFairlyActive(minutesFairlyActive);
		ht.setMinutesVeryActive(minutesVeryActive);
		ht.setActivityCalories(activityCalories);
		ht.setHrLowest(hrLowest);
		ht.setHrHighest(hrHighest);
		ht.setHrAverage(hrAverage);
		ht.setActiveHours(activeHours);
		ht.setUVExposure(uvExposure);
		
		if (isHealthTrackerCreated()) {
			controller.edit(ht);
		} else {
			trackerID = controller.addReturnGeneratedId(ht);
			ht.setTrackerID(trackerID);
			//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("healthTrackerId", trackerID);
		}
	}
	
	/**
	 * Checks if health info has already been entered with the same patient, hcp, and date.
	 * If so, sets the trackerID to the existing one.
	 * @return
	 * @throws DBException
	 */
	public boolean isHealthTrackerCreated() throws DBException {
		Long tmp = controller.dateExists(ht.getPatient(), ht.getHCP(), ht.getDate());
		if (tmp != null) {
			trackerID = tmp;
			ht.setTrackerID(tmp);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Update all fields when the date is changed
	 * @param event
	 * @throws DBException
	 */
	public void dateSelectedAction(SelectEvent event) throws DBException {
		this.date = (Date) event.getObject();
		ht.setDate(this.date);
		if (isHealthTrackerCreated()) {
			ht = controller.getDataByID(Long.toString(trackerID));
		} else {
			ht.setCaloriesBurned(0);
			ht.setSteps(0);
			ht.setDistance(0.00);
			ht.setFloors(0);
			ht.setMinutesSedentary(0);
			ht.setMinutesLightlyActive(0);
			ht.setMinutesFairlyActive(0);
			ht.setMinutesVeryActive(0);
			ht.setActivityCalories(0);
			ht.setHrLowest(0);
			ht.setHrHighest(0);
			ht.setHrAverage(0);
			ht.setActiveHours(0);
			ht.setUVExposure(0);
		}
		this.caloriesBurned = ht.getCaloriesBurned();
		this.steps = ht.getSteps();
		this.distance = ht.getDistance();
		this.floors = ht.getFloors();
		this.minutesSedentary = ht.getMinutesSedentary();
		this.minutesLightlyActive = ht.getMinutesLightlyActive();
		this.minutesFairlyActive = ht.getMinutesFairlyActive();
		this.minutesVeryActive = ht.getMinutesVeryActive();
		this.activityCalories = ht.getActivityCalories();
		this.hrLowest = ht.getHrLowest();
		this.hrHighest = ht.getHrHighest();
		this.hrAverage = ht.getHrAverage();
		this.activeHours = ht.getActiveHours();
		this.uvExposure = ht.getUVExposure();
	}

	public void close() {
		controller.close();
		
	}
}
