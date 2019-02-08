package edu.ncsu.csc.itrust.controller.obstetricsInitialization;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mockito.Mockito;

import edu.ncsu.csc.itrust.controller.officeVisit.OfficeVisitController;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ValidationFormat;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitialization;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.PreviousPregnancyInfo;
import edu.ncsu.csc.itrust.model.officeVisit.OfficeVisit;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;

@ManagedBean(name= "obstentrics_initialization_form")
@ViewScoped
public class ObstetricsInitializationForm {
	private ObstetricsInitializationController controller;
	private ObstetricsInitialization oi;
	private Long visitID;
	private Long infoID;
	private Long patientMID;
	private LocalDateTime date;
	private LocalDate LMP;
	private boolean RH;
	private int priorPregYear;
	private int priorPregWeeksPreg;
	private int priorPregHoursInLabor;
	private int priorPregWeightGained;
	private String priorPregDeliveryType;
	private int priorPregAmtChildren;
	private List<PreviousPregnancyInfo> priorPregs;
	
	public Long getInfoID() {
		return infoID;
	}
	public void setInfoID(Long infoID) {
		this.infoID = infoID;
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
	 * @return the lMP
	 */
	public LocalDate getLMP() {
		return LMP;
	}
	/**
	 * @param lMP the lMP to set
	 */
	public void setLMP(LocalDate lMP) {
		LMP = lMP;
	}
	
	/**
	 * @return the rH
	 */
	public boolean isRH() {
		return RH;
	}
	/**
	 * @param rH the rH to set
	 */
	public void setRH(boolean rH) {
		RH = rH;
	}
	public int getPriorPregYear() {
		return priorPregYear;
	}
	
	public void setPriorPregYear(int priorPregYear) {
		this.priorPregYear = priorPregYear;
	}
	/**
	 * @return the priorPregWeeksPreg
	 */
	public int getPriorPregWeeksPreg() {
		return priorPregWeeksPreg;
	}
	/**
	 * @param priorPregWeeksPreg the priorPregWeeksPreg to set
	 */
	public void setPriorPregWeeksPreg(int priorPregWeeksPreg) {
		this.priorPregWeeksPreg = priorPregWeeksPreg;
	}
	/**
	 * @return the priorPregHoursInLabor
	 */
	public int getPriorPregHoursInLabor() {
		return priorPregHoursInLabor;
	}
	/**
	 * @param priorPregHoursInLabor the priorPregHoursInLabor to set
	 */
	public void setPriorPregHoursInLabor(int priorPregHoursInLabor) {
		this.priorPregHoursInLabor = priorPregHoursInLabor;
	}
	/**
	 * @return the priorPregWeightGained
	 */
	public int getPriorPregWeightGained() {
		return priorPregWeightGained;
	}
	/**
	 * @param priorPregWeightGained the priorPregWeightGained to set
	 */
	public void setPriorPregWeightGained(int priorPregWeightGained) {
		this.priorPregWeightGained = priorPregWeightGained;
	}
	/**
	 * @return the priorPregAmtChildren
	 */
	public int getPriorPregAmtChildren() {
		return priorPregAmtChildren;
	}
	/**
	 * @param priorPregAmtChildren the priorPregAmtChildren to set
	 */
	public void setPriorPregAmtChildren(int priorPregAmtChildren) {
		this.priorPregAmtChildren = priorPregAmtChildren;
	}
	/**
	 * @return the priorPregDeliveryType
	 */
	public String getPriorPregDeliveryType() {
		return priorPregDeliveryType;
	}
	/**
	 * @param priorPregDeliveryType the priorPregDeliveryType to set
	 */
	public void setPriorPregDeliveryType(String priorPregDeliveryType) {
		this.priorPregDeliveryType = priorPregDeliveryType;
	}
	
	/**
	 * Initialize form with patient id, current date
	 */
	@PostConstruct
	public void init() {
		oi = new ObstetricsInitialization();
		try {
			controller = new ObstetricsInitializationController();
		} catch (DBException e1) {
			e1.printStackTrace();
		}
		long pid = -1;
		
		FacesContext ctx = FacesContext.getCurrentInstance();

		String patientID = "";
		try {	
		if (ctx.getExternalContext().getRequest() instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext().getRequest();
			HttpSession httpSession = req.getSession(false);
			patientID = (String) httpSession.getAttribute("pid");
		}
		if (ValidationFormat.NPMID.getRegex().matcher(patientID).matches()) {
			pid = Long.parseLong(patientID);
		}
		patientMID = pid;
		priorPregs = controller.getPreviousPregnancyInfoForPatient(patientID);
		} catch (NullPointerException e) {
			
		}
		date = LocalDateTime.now();
	}
	
	
	public void submit() {
		if (oi == null) {
			oi = new ObstetricsInitialization();
		}
		if (controller == null) {
			try {
				controller = new ObstetricsInitializationController();
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		oi.setDate(this.date);
		oi.setLMP(LMP);		
		oi.setPatientMID(patientMID);
		oi.setRH(RH);
		oi.setVisitID(null);
		long generatedVisitId = controller.addReturnGeneratedId(oi);
		setVisitID(generatedVisitId);
		oi.setVisitID(generatedVisitId);
		controller.logTransaction(TransactionType.CREATE_INITIAL_OBSTETRIC_RECORD, oi.getEDD().toString());
	}
	
	public void submitPriorPreg() {
		PreviousPregnancyInfo ppi = new PreviousPregnancyInfo();
		if (controller == null) {
			try {
				controller = new ObstetricsInitializationController();
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ppi.setAmtChildren(priorPregAmtChildren);
		ppi.setHoursInLabor(priorPregHoursInLabor);
		ppi.setPatientMID(patientMID);
		ppi.setWeeksPreg(priorPregWeeksPreg);
		ppi.setWeightGained(priorPregWeightGained);
		ppi.setYear(priorPregYear);
		if (priorPregDeliveryType.equals("Vaginal Delivery")) {
			ppi.setDeliveryType(1);
		} else if (priorPregDeliveryType.equals("Vaginal Delivery Vacuum Assist")){
			ppi.setDeliveryType(2);
		} else if (priorPregDeliveryType.equals("Vaginal Delivery forceps")) {
			ppi.setDeliveryType(3);
		} else if (priorPregDeliveryType.equals("Caesarean section")) {
			ppi.setDeliveryType(4);
		} else if (priorPregDeliveryType.equals("Miscarriage")) {
			ppi.setDeliveryType(5);
		} else {
			ppi.setDeliveryType(0);
		}
		long generatedInfoID = controller.addReturnGeneratedId(ppi);
		setInfoID(generatedInfoID);
		ppi.setInfoID(generatedInfoID);
	}
}
