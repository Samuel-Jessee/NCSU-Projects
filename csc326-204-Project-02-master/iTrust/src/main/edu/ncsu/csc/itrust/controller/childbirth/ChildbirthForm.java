package edu.ncsu.csc.itrust.controller.childbirth;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.ValidationFormat;
import edu.ncsu.csc.itrust.model.childbirth.ChildInChildbirth;
import edu.ncsu.csc.itrust.model.childbirth.Childbirth;
import edu.ncsu.csc.itrust.model.old.beans.ApptBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.user.patient.Patient;

@ManagedBean(name= "childbirth_form")
@ViewScoped
public class ChildbirthForm {
	private Childbirth cb;
	private ChildbirthController controller;
	private LocalDateTime date;
	private Long visitID;
	private Long patientMID;
	private Double pitocin;
	private Double nitrousOxide;
	private Double pethidine;
	private Double epiduralAnaesthesia;
	private Double magnesiumSulfate;
	private Double rhimmuneglobulin;
	private String prefMethod;
	private String method;
	private String childrenMIDs;
	private ArrayList<ChildInChildbirth> children;
	private boolean newCB;
	private Integer hoursInLabor;
	private boolean prescheduled;
	private ApptBean cbAppt;
	
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
	 * @return the preferred delivery method
	 */
	public String getPrefMethod() {
		return prefMethod;
	}

	/**
	 * @param prefMethod the preferred method to set
	 */
	public void setPrefMethod(String prefMethod) {
		this.prefMethod = prefMethod;
	}

	/**
	 * @return the actual delivery method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param actMethod the actMethod to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the childrenMIDs
	 */
	public String getChildrenMIDs() {
		return childrenMIDs;
	}

	/**
	 * @param childrenMIDs the childrenMIDs to set
	 */
	public void setChildrenMIDs(String childrenMIDs) {
		this.childrenMIDs = childrenMIDs;
	}

	/**
	 * @return the children
	 */
	public ArrayList<ChildInChildbirth> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(ArrayList<ChildInChildbirth> children) {
		this.children = children;
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

	/**
	 * @return the prescheduled
	 */
	public boolean isPrescheduled() {
		return prescheduled;
	}

	/**
	 * @param prescheduled the prescheduled to set
	 */
	public void setPrescheduled(boolean prescheduled) {
		this.prescheduled = prescheduled;
	}

	/**
	 * Initialize form with patient id, current date
	 */
	@PostConstruct
	public void init() {
		cb = new Childbirth();
		try {
			controller = new ChildbirthController();
		} catch (Exception e1) {
			controller = new ChildbirthController(ConverterDAO.getDataSource());
			e1.printStackTrace();
		}
		newCB = controller.isNewVisit();
		if (!newCB) {
			cb = controller.getSelectedVisit();
			date = cb.getDate();
			visitID = cb.getVisitID();
			patientMID = cb.getPatientMID();
			pitocin = cb.getPitocin();
			nitrousOxide = cb.getNitrousOxide();
			pethidine = cb.getPethidine();
			epiduralAnaesthesia = cb.getEpiduralAnaesthesia();
			magnesiumSulfate = cb.getMagnesiumSulfate();
			rhimmuneglobulin = cb.getRhimmuneglobulin();
			method = cb.getMethod();
			childrenMIDs = cb.getChildrenIDs();
			children = cb.getChildbirthchildren();
		} else {
		
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
			} catch (NullPointerException e) {
			
			}
			if (controller.isChildbirthAppointment()) {
				cbAppt = controller.getCbAppt();
				String temp = cbAppt.getApptType();
				String[] apptType = temp.split(",");
				prefMethod = apptType[1];
				if (cbAppt.getComment() != null && cbAppt.getComment().equals("Emergency Childbirth Appointment")) {
					prescheduled = false;
				} else {
					prescheduled = true;
				}
			} else {
				prefMethod = "Vaginal Delivery";
			}
			// prefMethod should be set from appointment data
			date = LocalDateTime.now();
			// Set drug dosage to 0.0
			pitocin = 0.0;
			nitrousOxide = 0.0;
			pethidine = 0.0;
			epiduralAnaesthesia = 0.0;
			magnesiumSulfate = 0.0;
			rhimmuneglobulin = 0.0;
			hoursInLabor = 0;
			// Initialize array list
			children = new ArrayList<ChildInChildbirth>();
		}
	}
	
	/**
	 * Adds a child to the children list
	 */
	public void addChild() {
		ChildInChildbirth child = new ChildInChildbirth();
		child.setDate(LocalDateTime.now());
		children.add(child);
	}
	
	/**
	 * Checks if the list of children is empty
	 * @return true if empty, false otherwise
	 */
	public boolean noBaby() {
		if (children.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void checkIfNull() {
		if (date == null) {
			date = LocalDateTime.now();
		}
		if (pitocin == null) {
			pitocin = 0.0;
		}
		if (epiduralAnaesthesia == null) {
			epiduralAnaesthesia = 0.0;
		}
		if (magnesiumSulfate == null) {
			magnesiumSulfate = 0.0;
		}
		if (rhimmuneglobulin == null) {
			rhimmuneglobulin = 0.0;
		}
		if (pethidine == null) {
			pethidine = 0.0;
		}
		if (nitrousOxide == null) {
			nitrousOxide = 0.0;
		}
	}
	
	/**
	 * Submit the form
	 */
	public void submit() {
		checkIfNull();
		cb.setPatientMID(patientMID);
		cb.setDate(date);
		cb.setEpiduralAnaesthesia(epiduralAnaesthesia);
		cb.setMagnesiumSulfate(magnesiumSulfate);
		cb.setRhimmuneglobulin(rhimmuneglobulin);
		cb.setMethod(method);
		cb.setNitrousOxide(nitrousOxide);
		cb.setPethidine(pethidine);
		cb.setPitocin(pitocin);
		cb.setChildbirthchildren(children);
		cb.setHoursInLabor(hoursInLabor);
		if (newCB) {
			cb.setVisitID(null);
			visitID = controller.addReturnGeneratedID(cb);
			cb.setVisitID(visitID);
		} else {
			controller.update(cb);
		}
		
	}
}