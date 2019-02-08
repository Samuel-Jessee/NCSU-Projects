package edu.ncsu.csc.itrust.controller.fitness;

import java.time.LocalDate;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.ncsu.csc.itrust.model.ValidationFormat;
import edu.ncsu.csc.itrust.model.fitness.Fitness;

/**
 * Fitness form used to submit fitness data to the database
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
@ManagedBean(name = "fitness_form")
@ViewScoped
public class FitnessForm {

	/**
	 * Fitness controller
	 */
	private FitnessController controller;

	/**
	 * Fitness object
	 */
	private Fitness fitness;
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
	 * Default constructor for FitnessForm.
	 */
	public FitnessForm() {
		this(null);
	}

	/**
	 * Constructor for FitnessForm
	 * 
	 * @param c
	 *            fitness controller
	 */
	public FitnessForm(FitnessController c) {
		try {
			controller = (c == null) ? new FitnessController() : c;
			fitness = controller.getSelectedFitness();
			if (fitness == null) {
				fitness = new Fitness();
			}
			try {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fitnessID",
						fitness.getFitnessID());
			} catch (NullPointerException e) {
				// Do nothing
			}
			fitnessID = fitness.getFitnessID();
			patientID = fitness.getPatientID();
			if (patientID == null) {
				patientID = Long.parseLong(
						(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pid"));
			}
			date = fitness.getDate();
			calBurned = fitness.getCalBurned();
			steps = fitness.getSteps();
			distance = fitness.getDistance();
			floors = fitness.getFloors();
			minSedentary = fitness.getMinSedentary();
			minLight = fitness.getMinLight();
			minFair = fitness.getMinFair();
			minVery = fitness.getMinVery();
			actCal = fitness.getActCal();
			lowHR = fitness.getLowHR();
			highHR = fitness.getHighHR();
			averageHR = fitness.getAverageHR();
			actHours = fitness.getActHours();
			minUV = fitness.getMinUV();
		} catch (Exception e) {
			FacesMessage throwMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fitness Controller Error",
					"Fitness Controller Error");
			FacesContext.getCurrentInstance().addMessage(null, throwMsg);
		}
	}

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
	 * Returns true if Fitness object was successfully created, otherwise false.
	 * 
	 * @return true if created successfully
	 */
	public boolean isFitnessCreated() {
		return (fitnessID != null) && (fitnessID > 0);
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

	/**
	 * Called when user clicks submit in fitnessInfo.xhtml. Takes data from form
	 * and sends to FitnessSQLLoader.java for storage and validation.
	 */
	public void submit() {
		fitness.setPatientID(patientID);
		fitness.setDate(date);
		fitness.setCalBurned(calBurned);
		fitness.setSteps(steps);
		fitness.setDistance(distance);
		fitness.setFloors(floors);
		fitness.setMinSedentary(minSedentary);
		fitness.setMinLight(minLight);
		fitness.setMinFair(minFair);
		fitness.setMinVery(minVery);
		fitness.setActCal(actCal);
		fitness.setLowHR(lowHR);
		fitness.setHighHR(highHR);
		fitness.setAverageHR(averageHR);
		fitness.setActHours(actHours);
		fitness.setMinUV(minUV);
		if (isFitnessCreated()) {
			controller.edit(fitness);
			// controller.logTransaction(TransactionType.FITNESS_EDIT,
			// fitness.getFitnessID().toString());
		} else {
			long pid = -1;
			FacesContext ctx = FacesContext.getCurrentInstance();
			String patientID = "";
			if (ctx.getExternalContext().getRequest() instanceof HttpServletRequest) {
				HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext().getRequest();
				HttpSession httpSession = req.getSession(false);
				patientID = (String) httpSession.getAttribute("pid");
			}
			if (ValidationFormat.NPMID.getRegex().matcher(patientID).matches()) {
				pid = Long.parseLong(patientID);
			}
			fitness.setPatientID(pid);
			fitness.setFitnessID(null);
			long generatedFitnessID = controller.addReturnGeneratedID(fitness);
			setFitnessID(generatedFitnessID);
			fitness.setFitnessID(generatedFitnessID);
			// controller.logTransaction(TransactionType.FITNESS_CREATE,
			// fitness.getFitnessID().toString());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fitnessID", generatedFitnessID);
		}
	}
}
