package edu.ncsu.csc.itrust.model.obstetricsInitialization;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "obstetrics_initialization")
public class ObstetricsInitialization {
	private Long visitID;
	private Long patientMID;
	private LocalDateTime date;
	private LocalDate LMP;
	private LocalDate EDD;
	private Integer weeksPreg;
	private boolean RH;
	
	public ObstetricsInitialization(){
		
	}
	
	/** @return the current visitID **/
	public Long getVisitID() {
		return visitID;
	}
	
	/** @param visitID the visitID to be set **/
	public void setVisitID(Long visitID) {
		this.visitID = visitID;
	}
	
	/** @return the current patient MID **/
	public Long getPatientMID() {
		return patientMID;
	}
	
	/** @param patientMID the patient MID to be set **/
	public void setPatientMID(Long patientMID) {
		this.patientMID = patientMID;
	}
	
	/** @return the date of the last menstrual period (LMP) **/
	public LocalDate getLMP() {
		return LMP;
	}
	
	/** @param LMP the date of the last menstrual period **/
	public void setLMP(LocalDate lMP) {
		LMP = lMP;
		if(lMP != null){
			this.EDD = getEstimatedDueDate(LMP);
			this.weeksPreg = getNumberWeeksPregnant(LMP, date);
		}
		else{
			this.EDD = null;
			this.weeksPreg = null;
		}
		
	}
	
	/** @return the Estimated Due Date */
	public LocalDate getEDD() {
		return EDD;
	}
	
	/** @return the number of weeks pregnant **/
	public Integer getWeeksPreg() {
		return weeksPreg;
	}
	
	/** @return the date of the obstetrics initialization */
	public LocalDateTime getDate() {
		return date;
	}

	/** @param date the date of the obstetrics initialization */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	/**
	 * Adds 280 days to the last mestrual period date to find the 
	 * Estimated Due Date
	 * @param LMP Last Menstrual period date
	 * @return the estimated due date
	 */
	public LocalDate getEstimatedDueDate(LocalDate LMP){
		LocalDate EDD = LMP.plusDays(280);
		return EDD;
	}
	
	/**
	 * Calculates the number of weeks the patient has been patient
	 * @param LMP the last menstrual period date
	 * @param date the current date that the obstetrics initliation was made
	 * @return the number of weeks the patient has been pregnant
	 */
	public Integer getNumberWeeksPregnant(LocalDate LMP, LocalDateTime date){
		Integer weeks = 0;
		long days = 0;
		LocalDate current = date.toLocalDate();
		
		//checks to see if the current date is after the last mentrual period
		if(current.isAfter(LMP)){
			//finds the number of days between the two
			days = ChronoUnit.DAYS.between(LMP, current);
		}
		//divides by 7 for each day of the week (we want a whole number)
		weeks =  (int) days / 7;
		
		return weeks;
	}

	/**
	 * @return the rhNeg
	 */
	public boolean isRH() {
		return RH;
	}

	/**
	 * @param rhNeg the rhNeg to set
	 */
	public void setRH(boolean RH) {
		this.RH = RH;
	}
	
}
