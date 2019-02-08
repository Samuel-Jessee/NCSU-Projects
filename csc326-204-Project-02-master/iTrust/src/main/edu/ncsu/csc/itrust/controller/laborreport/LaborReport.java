package edu.ncsu.csc.itrust.controller.laborreport;

import java.time.LocalDate;
import java.util.List;

import javax.faces.bean.ManagedBean;

import edu.ncsu.csc.itrust.model.diagnosis.Diagnosis;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.PreviousPregnancyInfo;
import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.ObstetricsOfficeVisit;
import edu.ncsu.csc.itrust.model.old.beans.AllergyBean;
import edu.ncsu.csc.itrust.model.old.enums.BloodType;

@ManagedBean(name = "labor_report")
public class LaborReport {
	
	long patientMID;
	List<PreviousPregnancyInfo> pastPreg;
	LocalDate EstimatedDeliveryDate;
	BloodType bloodtype;
	List<ObstetricsOfficeVisit> obOfficeVisit;
	boolean rhFlag;
	boolean highBloodPressure;
	boolean advancedMaternalAge;
	boolean preExisitngConditions;
	boolean maternalAllergies;
	boolean lowLyingPlacenta;
	boolean geneticPotential;
	boolean abnormalFetalHeartRate;
	boolean multiplePreg;
	boolean atypicalWeightChange;
	boolean hyperemesisGravidarum;
	boolean hypothyroidism;
	List<Diagnosis> conditions;
	List<AllergyBean> allergies;
	
	/**
	 * @return the patientMID
	 */
	public long getPatientMID() {
		return patientMID;
	}
	/**
	 * @param patientMID the patientMID to set
	 */
	public void setPatientMID(long patientMID) {
		this.patientMID = patientMID;
	}
	/**
	 * @return the pastPreg
	 */
	public List<PreviousPregnancyInfo> getPastPreg() {
		return pastPreg;
	}
	/**
	 * @param pastPreg the pastPreg to set
	 */
	public void setPastPreg(List<PreviousPregnancyInfo> pastPreg) {
		this.pastPreg = pastPreg;
	}
	/**
	 * @return the estimatedDeliveryDate
	 */
	public LocalDate getEstimatedDeliveryDate() {
		return EstimatedDeliveryDate;
	}
	/**
	 * @param estimatedDeliveryDate the estimatedDeliveryDate to set
	 */
	public void setEstimatedDeliveryDate(LocalDate estimatedDeliveryDate) {
		EstimatedDeliveryDate = estimatedDeliveryDate;
	}
	/**
	 * @return the bloodtype
	 */
	public BloodType getBloodtype() {
		return bloodtype;
	}
	/**
	 * @param bloodtype the bloodtype to set
	 */
	public void setBloodtype(BloodType bloodtype) {
		this.bloodtype = bloodtype;
	}
	/**
	 * @return the obOfficeVisit
	 */
	public List<ObstetricsOfficeVisit> getObOfficeVisit() {
		return obOfficeVisit;
	}
	/**
	 * @param obOfficeVisit the obOfficeVisit to set
	 */
	public void setObOfficeVisit(List<ObstetricsOfficeVisit> obOfficeVisit) {
		this.obOfficeVisit = obOfficeVisit;
	}
	/**
	 * @return the rhFlag
	 */
	public boolean isRhFlag() {
		return rhFlag;
	}
	/**
	 * @param rhFlag the rhFlag to set
	 */
	public void setRhFlag(boolean rhFlag) {
		this.rhFlag = rhFlag;
	}
	/**
	 * @return the highBloodPressure
	 */
	public boolean isHighBloodPressure() {
		return highBloodPressure;
	}
	/**
	 * @param highBloodPressure the highBloodPressure to set
	 */
	public void setHighBloodPressure(boolean highBloodPressure) {
		this.highBloodPressure = highBloodPressure;
	}
	/**
	 * @return the advancedMaternalAge
	 */
	public boolean isAdvancedMaternalAge() {
		return advancedMaternalAge;
	}
	/**
	 * @param advancedMaternalAge the advancedMaternalAge to set
	 */
	public void setAdvancedMaternalAge(boolean advancedMaternalAge) {
		this.advancedMaternalAge = advancedMaternalAge;
	}
	/**
	 * @return the preExisitngConditions
	 */
	public boolean isPreExisitngConditions() {
		return preExisitngConditions;
	}
	/**
	 * @param preExisitngConditions the preExisitngConditions to set
	 */
	public void setPreExisitngConditions(boolean preExisitngConditions) {
		this.preExisitngConditions = preExisitngConditions;
	}
	/**
	 * @return the maternalAllergies
	 */
	public boolean isMaternalAllergies() {
		return maternalAllergies;
	}
	/**
	 * @param maternalAllergies the maternalAllergies to set
	 */
	public void setMaternalAllergies(boolean maternalAllergies) {
		this.maternalAllergies = maternalAllergies;
	}
	/**
	 * @return the lowLyingPlacenta
	 */
	public boolean isLowLyingPlacenta() {
		return lowLyingPlacenta;
	}
	/**
	 * @param lowLyingPlacenta the lowLyingPlacenta to set
	 */
	public void setLowLyingPlacenta(boolean lowLyingPlacenta) {
		this.lowLyingPlacenta = lowLyingPlacenta;
	}
	/**
	 * @return the geneticPotential
	 */
	public boolean isGeneticPotential() {
		return geneticPotential;
	}
	/**
	 * @param geneticPotential the geneticPotential to set
	 */
	public void setGeneticPotential(boolean geneticPotential) {
		this.geneticPotential = geneticPotential;
	}
	/**
	 * @return the abnormalFetalHeartRate
	 */
	public boolean isAbnormalFetalHeartRate() {
		return abnormalFetalHeartRate;
	}
	/**
	 * @param abnormalFetalHeartRate the abnormalFetalHeartRate to set
	 */
	public void setAbnormalFetalHeartRate(boolean abnormalFetalHeartRate) {
		this.abnormalFetalHeartRate = abnormalFetalHeartRate;
	}
	/**
	 * @return the multiplePreg
	 */
	public boolean isMultiplePreg() {
		return multiplePreg;
	}
	/**
	 * @param multiplePreg the multiplePreg to set
	 */
	public void setMultiplePreg(boolean multiplePreg) {
		this.multiplePreg = multiplePreg;
	}
	/**
	 * @return the atypicalWeightChange
	 */
	public boolean isAtypicalWeightChange() {
		return atypicalWeightChange;
	}
	/**
	 * @param atypicalWeightChange the atypicalWeightChange to set
	 */
	public void setAtypicalWeightChange(boolean atypicalWeightChange) {
		this.atypicalWeightChange = atypicalWeightChange;
	}
	/**
	 * @return the hyperemesisGravidarum
	 */
	public boolean isHyperemesisGravidarum() {
		return hyperemesisGravidarum;
	}
	/**
	 * @param hyperemesisGravidarum the hyperemesisGravidarum to set
	 */
	public void setHyperemesisGravidarum(boolean hyperemesisGravidarum) {
		this.hyperemesisGravidarum = hyperemesisGravidarum;
	}
	/**
	 * @return the hypothyroidism
	 */
	public boolean isHypothyroidism() {
		return hypothyroidism;
	}
	/**
	 * @param hypothyroidism the hypothyroidism to set
	 */
	public void setHypothyroidism(boolean hypothyroidism) {
		this.hypothyroidism = hypothyroidism;
	}
	/**
	 * @return the conditions
	 */
	public List<Diagnosis> getConditions() {
		return conditions;
	}
	/**
	 * @param conditions the conditions to set
	 */
	public void setConditions(List<Diagnosis> conditions) {
		this.conditions = conditions;
	}
	/**
	 * @return the allergies
	 */
	public List<AllergyBean> getAllergies() {
		return allergies;
	}
	/**
	 * @param allergies the allergies to set
	 */
	public void setAllergies(List<AllergyBean> allergies) {
		this.allergies = allergies;
	}

}
