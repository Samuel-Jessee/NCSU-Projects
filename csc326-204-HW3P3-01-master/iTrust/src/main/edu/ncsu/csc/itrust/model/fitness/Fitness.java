package edu.ncsu.csc.itrust.model.fitness;

import java.time.LocalDate;

import javax.faces.bean.ManagedBean;

/**
 * Fitness object that stores a patient's fitness data for a specific day
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
@ManagedBean(name = "fitness")
public class Fitness {

	/**
	 * ID of the data in the SQL table
	 */
	private Long fitnessID;

	/**
	 * ID of the patient
	 */
	private Long patientID;

	/**
	 * Date
	 */
	private LocalDate date;

	/**
	 * Calories Burned (Calories)
	 */
	private Integer calBurned;

	/**
	 * Steps
	 */
	private Integer steps;

	/**
	 * Distance (Total Miles Moved)
	 */
	private Double distance;

	/**
	 * Floors (Floors Climbed)
	 */
	private Integer floors;

	/**
	 * Minutes Sedentary
	 */
	private Integer minSedentary;

	/**
	 * Minutes Lightly Active
	 */
	private Integer minLight;

	/**
	 * Minutes Fairly Active
	 */
	private Integer minFair;

	/**
	 * Minutes Very Active
	 */
	private Integer minVery;

	/**
	 * Activity Calories
	 */
	private Integer actCal;

	/**
	 * HR Lowest
	 */
	private Integer lowHR;

	/**
	 * HR Highest
	 */
	private Integer highHR;

	/**
	 * HR Average
	 */
	private Integer averageHR;

	/**
	 * Active Hours
	 */
	private Integer actHours;

	/**
	 * UV Exposure Minutes
	 */
	private Integer minUV;

	/**
	 * @return the actCal
	 */
	public Integer getActCal() {
		return actCal;
	}

	/**
	 * @return the actHours
	 */
	public Integer getActHours() {
		return actHours;
	}

	/**
	 * @return the averageHR
	 */
	public Integer getAverageHR() {
		return averageHR;
	}

	/**
	 * @return the calBurned
	 */
	public Integer getCalBurned() {
		return calBurned;
	}

	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @return the distance
	 */
	public Double getDistance() {
		return distance;
	}

	/**
	 * @return the fitnessID
	 */
	public Long getFitnessID() {
		return fitnessID;
	}

	/**
	 * @return the floors
	 */
	public Integer getFloors() {
		return floors;
	}

	/**
	 * @return the highHR
	 */
	public Integer getHighHR() {
		return highHR;
	}

	/**
	 * @return the lowHR
	 */
	public Integer getLowHR() {
		return lowHR;
	}

	/**
	 * @return the minFair
	 */
	public Integer getMinFair() {
		return minFair;
	}

	/**
	 * @return the minLight
	 */
	public Integer getMinLight() {
		return minLight;
	}

	/**
	 * @return the minSedentary
	 */
	public Integer getMinSedentary() {
		return minSedentary;
	}

	/**
	 * @return the minUV
	 */
	public Integer getMinUV() {
		return minUV;
	}

	/**
	 * @return the minVery
	 */
	public Integer getMinVery() {
		return minVery;
	}

	/**
	 * @return the patientID
	 */
	public Long getPatientID() {
		return patientID;
	}

	/**
	 * @return the steps
	 */
	public Integer getSteps() {
		return steps;
	}

	/**
	 * @param actCal
	 *            the actCal to set
	 */
	public void setActCal(Integer actCal) {
		this.actCal = actCal;
	}

	/**
	 * @param actHours
	 *            the actHours to set
	 */
	public void setActHours(Integer actHours) {
		this.actHours = actHours;
	}

	/**
	 * @param averageHR
	 *            the averageHR to set
	 */
	public void setAverageHR(Integer averageHR) {
		this.averageHR = averageHR;
	}

	/**
	 * @param calBurned
	 *            the calBurned to set
	 */
	public void setCalBurned(Integer calBurned) {
		this.calBurned = calBurned;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * @param distance
	 *            the distance to set
	 */
	public void setDistance(Double distance) {
		this.distance = distance;
	}

	/**
	 * @param fitnessID
	 *            the fitnessID to set
	 */
	public void setFitnessID(Long fitnessID) {
		this.fitnessID = fitnessID;
	}

	/**
	 * @param floors
	 *            the floors to set
	 */
	public void setFloors(Integer floors) {
		this.floors = floors;
	}

	/**
	 * @param highHR
	 *            the highHR to set
	 */
	public void setHighHR(Integer highHR) {
		this.highHR = highHR;
	}

	/**
	 * @param lowHR
	 *            the lowHR to set
	 */
	public void setLowHR(Integer lowHR) {
		this.lowHR = lowHR;
	}

	/**
	 * @param minFair
	 *            the minFair to set
	 */
	public void setMinFair(Integer minFair) {
		this.minFair = minFair;
	}

	/**
	 * @param minLight
	 *            the minLight to set
	 */
	public void setMinLight(Integer minLight) {
		this.minLight = minLight;
	}

	/**
	 * @param minSedentary
	 *            the minSedentary to set
	 */
	public void setMinSedentary(Integer minSedentary) {
		this.minSedentary = minSedentary;
	}

	/**
	 * @param minUV
	 *            the minUV to set
	 */
	public void setMinUV(Integer minUV) {
		this.minUV = minUV;
	}

	/**
	 * @param minVery
	 *            the minVery to set
	 */
	public void setMinVery(Integer minVery) {
		this.minVery = minVery;
	}

	/**
	 * @param patientID
	 *            the patientID to set
	 */
	public void setPatientID(Long patientID) {
		this.patientID = patientID;
	}

	/**
	 * @param steps
	 *            the steps to set
	 */
	public void setSteps(Integer steps) {
		this.steps = steps;
	}
}
