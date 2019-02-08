package edu.ncsu.csc.itrust.model.obstetricsOfficeVisit;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
/**
 * Class for the Obstetrics Office Visit
 * @author abbyr
 *
 */
@ManagedBean(name = "obstetrics_office_visit")
public class ObstetricsOfficeVisit {
	private Long visitID;
	private Long patientMID;
	private Date date;
	private Integer numWeeks;
	private Double weight;
	private String bloodPressure;
	private Integer FHR;
	private Integer numFetus;
	private boolean lowLyingPlacenta;
	private ArrayList<Ultrasound> ultrasounds;
	private String ultrasoundString;
	private File picture;
	private String prefferedMethod;
	private boolean highgenetic;
	private String complications;

	public ObstetricsOfficeVisit(){
		
	}

	/**
	 * @return the picture
	 */
	public File getPicture() {
		return picture;
	}

	/**
	 * @param picture the picture to set
	 */
	public void setPicture(File picture) {
		this.picture = picture;
	}



	public Long getVisitID() {
		return visitID;
	}

	public void setVisitID(Long visitID) {
		this.visitID = visitID;
	}

	public Long getPatientMID() {
		return patientMID;
	}

	public void setPatientMID(Long patientMID) {
		this.patientMID = patientMID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getNumWeeks() {
		return numWeeks;
	}

	public void setNumWeeks(Integer numWeeks) {
		this.numWeeks = numWeeks;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getBloodPressure() {
		return bloodPressure;
	}

	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}

	/**
	 * Fetal Heart Rate
	 * @return
	 */
	public Integer getFHR() {
		return FHR;
	}

	/**
	 * Fetal Heart Rate
	 * @param fHR
	 */
	public void setFHR(Integer fHR) {
		FHR = fHR;
	}

	/**
	 * Number of Fetus in the current Prenancy
	 * @return
	 */
	public Integer getNumFetus() {
		return numFetus;
	}

	/**
	 * Number of Fetus in the current Prenancy
	 * @param numFetus
	 */
	public void setNumFetus(Integer numFetus) {
		this.numFetus = numFetus;
	}

	public boolean isLowLyingPlacenta() {
		return lowLyingPlacenta;
	}

	public void setLowLyingPlacenta(boolean lowLyingPlacenta) {
		this.lowLyingPlacenta = lowLyingPlacenta;
	}

	public ArrayList<Ultrasound> getUltrasounds() {
		return ultrasounds;
	}

	public void setUltrasounds(ArrayList<Ultrasound> ultrasounds) {
		this.ultrasounds = ultrasounds;
	}

	/**
	 * @return the ultrasoundString
	 */
	public String getUltrasoundString() {
		return ultrasoundString;
	}

	/**
	 * @param ultrasoundString the ultrasoundString to set
	 */
	public void setUltrasoundString(String ultrasoundString) {
		this.ultrasoundString = ultrasoundString;
	}

	/**
	 * @return the prefferedMethod
	 */
	public String getPrefferedMethod() {
		return prefferedMethod;
	}

	/**
	 * @param prefferedMethod the prefferedMethod to set
	 */
	public void setPrefferedMethod(String prefferedMethod) {
		this.prefferedMethod = prefferedMethod;
	}

	/**
	 * @return the highgenetic
	 */
	public boolean isHighgenetic() {
		return highgenetic;
	}

	/**
	 * @param highgenetic the highgenetic to set
	 */
	public void setHighgenetic(boolean highgenetic) {
		this.highgenetic = highgenetic;
	}

	/**
	 * @return the complications
	 */
	public String getComplications() {
		return complications;
	}

	/**
	 * @param complications the complications to set
	 */
	public void setComplications(String complications) {
		this.complications = complications;
	}
	
	

}
