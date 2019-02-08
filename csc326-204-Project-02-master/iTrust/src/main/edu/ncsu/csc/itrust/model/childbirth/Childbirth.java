package edu.ncsu.csc.itrust.model.childbirth;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;

import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.user.patient.Patient;

@ManagedBean(name = "childbirth")
public class Childbirth {
	private LocalDateTime date;
	private Long visitID;
	private Long patientMID;
	private Double pitocin;
	private Double nitrousOxide;
	private Double pethidine;
	private Double epiduralAnaesthesia;
	private Double magnesiumSulfate;
	private Double rhimmuneglobulin;
	private String method;
	private String childrenIDs;
	private ArrayList<ChildInChildbirth> childbirthchildren;
	private Integer hoursInLabor;
	
	public Childbirth(){
		
	}
	
	/**
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	/**
	 * @return the visitID
	 */
	public Long getVisitID() {
		return visitID;
	}

	/**
	 * @param visitID the visitID to set
	 */
	public void setVisitID(Long visitID) {
		this.visitID = visitID;
	}

	/**
	 * @return the patientMID
	 */
	public Long getPatientMID() {
		return patientMID;
	}

	/**
	 * @param patientMID the patientMID to set
	 */
	public void setPatientMID(Long patientMID) {
		this.patientMID = patientMID;
	}

	/**
	 * @return the pitocin
	 */
	public Double getPitocin() {
		return pitocin;
	}

	/**
	 * @param pitocin the pitocin to set
	 */
	public void setPitocin(Double pitocin) {
		this.pitocin = pitocin;
	}

	/**
	 * @return the nitrousOxide
	 */
	public Double getNitrousOxide() {
		return nitrousOxide;
	}

	/**
	 * @param nitrousOxide the nitrousOxide to set
	 */
	public void setNitrousOxide(Double nitrousOxide) {
		this.nitrousOxide = nitrousOxide;
	}

	/**
	 * @return the pethidine
	 */
	public Double getPethidine() {
		return pethidine;
	}

	/**
	 * @param pethidine the pethidine to set
	 */
	public void setPethidine(Double pethidine) {
		this.pethidine = pethidine;
	}

	/**
	 * @return the epiduralAnaesthesia
	 */
	public Double getEpiduralAnaesthesia() {
		return epiduralAnaesthesia;
	}

	/**
	 * @param epiduralAnaesthesia the epiduralAnaesthesia to set
	 */
	public void setEpiduralAnaesthesia(Double epiduralAnaesthesia) {
		this.epiduralAnaesthesia = epiduralAnaesthesia;
	}

	/**
	 * @return the magnesiumSulfate
	 */
	public Double getMagnesiumSulfate() {
		return magnesiumSulfate;
	}

	/**
	 * @param magnesiumSulfate the magnesiumSulfate to set
	 */
	public void setMagnesiumSulfate(Double magnesiumSulfate) {
		this.magnesiumSulfate = magnesiumSulfate;
	}

	/**
	 * @return the rhimmuneglobulin
	 */
	public Double getRhimmuneglobulin() {
		return rhimmuneglobulin;
	}

	/**
	 * @param rhimmuneglobulin the rhimmuneglobulin to set
	 */
	public void setRhimmuneglobulin(Double rhimmuneglobulin) {
		this.rhimmuneglobulin = rhimmuneglobulin;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the childrenMIDs
	 */
	public String getChildrenIDs() {
		return childrenIDs;
	}

	/**
	 * @param childrenMIDs the childrenMIDs to set
	 */
	public void setChildrenIDs(String childrenMIDs) {
		this.childrenIDs = childrenMIDs;
	}

	/**
	 * @return the childbirthchildren
	 */
	public ArrayList<ChildInChildbirth> getChildbirthchildren() {
		return childbirthchildren;
	}

	/**
	 * @param childbirthchildren the childbirthchildren to set
	 */
	public void setChildbirthchildren(ArrayList<ChildInChildbirth> childbirthchildren) {
		this.childbirthchildren = childbirthchildren;
	}

	/**
	 * @return the hoursInLabor
	 */
	public Integer getHoursInLabor() {
		return hoursInLabor;
	}

	/**
	 * @param hoursInLabor the hoursInLabor to set
	 */
	public void setHoursInLabor(Integer hoursInLabor) {
		this.hoursInLabor = hoursInLabor;
	}
	
}
