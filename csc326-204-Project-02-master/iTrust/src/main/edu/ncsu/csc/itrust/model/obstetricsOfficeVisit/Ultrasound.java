package edu.ncsu.csc.itrust.model.obstetricsOfficeVisit;

import javax.faces.bean.ManagedBean;

@ManagedBean( name = "ultrasound")
public class Ultrasound {
	private Long ultraID;
	private Long patientMID;
	private Double CRL;
	private Double BPD;
	private Double HC;
	private Double FL;
	private Double OFD;
	private Double AC;
	private Double HL;
	private Double EFW;
	
	public Ultrasound(){
		
	}

	/**
	 * @return the ultraID
	 */
	public Long getUltraID() {
		return ultraID;
	}

	/**
	 * @param ultraID the ultraID to set
	 */
	public void setUltraID(Long ultraID) {
		this.ultraID = ultraID;
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
	 * Crown rump length
	 * @return the cRL
	 */
	public Double getCRL() {
		return CRL;
	}

	/**
	 * @param cRL the Crown rump length to set
	 */
	public void setCRL(Double cRL) {
		CRL = cRL;
	}

	/**
	 * Biparietal diameter
	 * @return the bPD
	 */
	public Double getBPD() {
		return BPD;
	}

	/**
	 * @param bPD the Biparietal diameter to set
	 */
	public void setBPD(Double bPD) {
		BPD = bPD;
	}

	/**
	 * Head circumference
	 * @return the hC
	 */
	public Double getHC() {
		return HC;
	}

	/**
	 * @param hC the Head circumference to set
	 */
	public void setHC(Double hC) {
		HC = hC;
	}

	/**
	 * Femur Length
	 * @return the fL
	 */
	public Double getFL() {
		return FL;
	}

	/**
	 * @param fL the Femur Length to set
	 */
	public void setFL(Double fL) {
		FL = fL;
	}

	/**
	 * Occipitofrontal diameter
	 * @return the oFD
	 */
	public Double getOFD() {
		return OFD;
	}

	/**
	 * @param oFD the Occipitofrontal diameter to set
	 */
	public void setOFD(Double oFD) {
		OFD = oFD;
	}

	/**
	 * Abdominal circumference
	 * @return the aC
	 */
	public Double getAC() {
		return AC;
	}

	/**
	 * @param aC the Abdominal circumference to set
	 */
	public void setAC(Double aC) {
		AC = aC;
	}

	/**
	 * Humerus length
	 * @return the hL
	 */
	public Double getHL() {
		return HL;
	}

	/**
	 * @param hL the Humerus length to set
	 */
	public void setHL(Double hL) {
		HL = hL;
	}

	/**
	 * Estimated fetal weight
	 * @return the eFW
	 */
	public Double getEFW() {
		return EFW;
	}

	/**
	 * @param eFW the Estimated fetal weight to set
	 */
	public void setEFW(Double eFW) {
		EFW = eFW;
	}
	
	
}
