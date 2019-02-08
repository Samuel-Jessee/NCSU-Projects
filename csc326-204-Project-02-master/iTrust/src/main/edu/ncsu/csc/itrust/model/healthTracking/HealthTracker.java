/**
 * 
 */
package edu.ncsu.csc.itrust.model.healthTracking;

import java.util.Date;

import javax.faces.bean.ManagedBean;

/**
 * @author smmahaff
 *
 */
@ManagedBean(name="health_tracker")
public class HealthTracker {
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
	 * Default constructor.
	 */
	public HealthTracker() {
		
	}
	
	/**
	 * Returns the UV_Exposure for this bean.
	 * 
	 * @return 
	 */
	public int getUVExposure() {
		return uvExposure;
	}

	/**
	 * Sets the UVExposure for this bean.
	 * 
	 * @return 
	 */
	public void setUVExposure(int uvExposure) {
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
	 * Returns the trackerID for this bean.
	 * 
	 * @return 
	 */
	public long getTrackerID() {
		return this.trackerID;
	}
	
	/**
	 * Sets the trackerID for this bean.
	 * 
	 * @param trackerID
	 */
	public void setTrackerID(long trackerID) {
		this.trackerID = trackerID;
	}

	/**
	 * Returns the date for this bean.
	 * 
	 * @return 
	 */
	public Date getDate() {
		
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
}

