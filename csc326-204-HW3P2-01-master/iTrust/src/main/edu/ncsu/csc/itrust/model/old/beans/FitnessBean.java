package edu.ncsu.csc.itrust.model.old.beans;

import java.sql.Timestamp;

/**
 * @author Louis Le
 * 
 *         This class was inspired by MessageBean
 * 
 *         This will be used to hold information on the patient's health
 *         tracking data.
 */
public class FitnessBean {
	// ID of which data it is in the SQL table
	private long id;
	// ID of patient this data pertains to
	private long patient;
	// Date of health data
	private Timestamp date;
	// Calories burnt for this day
	private int calBurned;
	// Steps taken for this day
	private int steps;
	// Distance for this day
	private double dist;
	// Floors for this day
	private int floors;
	// Minutes sedentary for this day
	private int minSed;
	// Minutes lightly active for this day
	private int minLight;
	// Minutes fairly active for this day
	private int minFair;
	// Minutes very active for this day
	private int minVery;
	// Active calories burnt for this day
	private int actCal;

	/**
	 * Sets id
	 * 
	 * @param id
	 *            ID of this specific FitnessBean
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Sets patient ID
	 * 
	 * @param patient
	 *            ID of patients
	 */
	public void setPatient(long patient) {
		this.patient = patient;
	}

	/**
	 * Sets date of data
	 * 
	 * @param date2
	 *            date of data
	 */
	public void setDate(Timestamp date2) {
		this.date = date2;
	}

	/**
	 * Sets calories burnt
	 * 
	 * @param calBurned
	 *            Calories burnt
	 */
	public void setCalBurned(int calBurned) {
		this.calBurned = calBurned;
	}

	/**
	 * Sets steps
	 * 
	 * @param steps
	 *            Steps for this day
	 */
	public void setSteps(int steps) {
		this.steps = steps;
	}

	/**
	 * Sets distance
	 * 
	 * @param dist
	 *            distance for this day
	 */
	public void setDist(double dist) {
		this.dist = dist;
	}

	/**
	 * Sets floors
	 * 
	 * @param floors
	 *            Floors for this day
	 */
	public void setFloors(int floors) {
		this.floors = floors;
	}

	/**
	 * Sets minutes sedentary
	 * 
	 * @param minSed
	 *            Minutes sedentary for this day
	 */
	public void setMinSed(int minSed) {
		this.minSed = minSed;
	}

	/**
	 * Sets minutes lightly active
	 * 
	 * @param minLight
	 *            minutes lightly active for this day
	 */
	public void setMinLight(int minLight) {
		this.minLight = minLight;
	}

	/**
	 * Sets minutes fairly active
	 * 
	 * @param minFair
	 *            minutes fairly active for this day
	 */
	public void setMinFair(int minFair) {
		this.minFair = minFair;
	}

	/**
	 * Sets minutes very active
	 * 
	 * @param minVery
	 *            minutes very active for this day
	 */
	public void setMinVery(int minVery) {
		this.minVery = minVery;
	}

	/**
	 * Sets active calories
	 * 
	 * @param actCal
	 *            active calories for this day
	 */
	public void setActCal(int actCal) {
		this.actCal = actCal;
	}

	/**
	 * Gets id
	 * 
	 * @return ID of FitnessBean
	 */
	public long getId() {
		return id;
	}

	/**
	 * Gets Patient ID
	 * 
	 * @return ID of patient
	 */
	public long getPatient() {
		return patient;
	}

	/**
	 * Gets date of data
	 * 
	 * @return date of data
	 */
	public Timestamp getDate() {
		return date;
	}

	/**
	 * Gets calories burned
	 * 
	 * @return calories burned
	 */
	public int getCalBurned() {
		return calBurned;
	}

	/**
	 * Gets steps taken
	 * 
	 * @return steps taken
	 */
	public int getSteps() {
		return steps;
	}

	/**
	 * Gets distance
	 * 
	 * @return distance
	 */
	public double getDist() {
		return dist;
	}

	/**
	 * Gets floors
	 * 
	 * @return floors
	 */
	public int getFloors() {
		return floors;
	}

	/**
	 * Gets minutes sedentary
	 * 
	 * @return minutes sedentary
	 */
	public int getMinSed() {
		return minSed;
	}

	/**
	 * Gets minutes lightly active
	 * 
	 * @return minutes lightly active
	 */
	public int getMinLight() {
		return minLight;
	}

	/**
	 * Gets minutes fairly active
	 * 
	 * @return minutes fairly active
	 */
	public int getMinFair() {
		return minFair;
	}

	/**
	 * Gets minutes very active
	 * 
	 * @return minutes very active
	 */
	public int getMinVery() {
		return minVery;
	}

	/**
	 * Gets active calories
	 * 
	 * @return active calories
	 */
	public int getActCal() {
		return actCal;
	}
}
