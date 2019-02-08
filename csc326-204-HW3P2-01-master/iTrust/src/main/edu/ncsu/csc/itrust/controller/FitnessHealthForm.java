package edu.ncsu.csc.itrust.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import edu.ncsu.csc.itrust.action.AccessPatientFitnessAction;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.beans.FitnessBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.FitnessDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO;

@ManagedBean(name = "fitness_health_controller")
@SessionScoped
public class FitnessHealthForm extends iTrustController {
	
		/**
		 * FitnessBean generated using form data
		 */
		private FitnessBean fitnessBean;
		/**
		 * Patient ID
		 */
		private long patientID;
		/**
		 * Month
		 */
		private int month;
		/**
		 * Day
		 */
		private int day;
		/**
		 * year
		 */
		private int year;
		/**
		 * File
		 */
		private File file;
		/**
		 * Date
		 */
		private Timestamp date;
		/**
		 * String version of date
		 */
		private String dateString;
		/**
		 * Calories Burned
		 */
		private int caloriesBurned;
		/**
		 * Steps
		 */
		private int steps;
		/**
		 * Distance
		 */
		private double distance;
		/**
		 * Floors
		 */
		private int floors;
		/**
		 * Minutes Sedentary
		 */
		private int minutesSedentary;
		/**
		 * Minutes Lightly Active
		 */
		private int minutesLightlyActive;
		/**
		 * Minutes Fairly Active
		 */
		private int minutesFairlyActive;
		/**
		 * Minutes Very Active
		 */
		private int minutesVeryActive;
		/**
		 * Activity Calories
	 	*/
		private int activityCalories;
	
		/**
		 * FitnessDAO used to add fitness data to the DB
		 */
		private FitnessDAO fitnessDAO;
		private PatientDAO patientDAO;
		private PersonnelDAO personnelDAO;

		/**
		 * Constructor for FitnessHealthForm class
		 */
		public FitnessHealthForm(DAOFactory factory) {
			fitnessBean = new FitnessBean();
			fitnessDAO = factory.getFitnessDAO();
			patientDAO = factory.getPatientDAO();
			personnelDAO = factory.getPersonnelDAO();
		}
		
		public FitnessHealthForm() {
			fitnessBean = new FitnessBean();
			fitnessDAO = new FitnessDAO(null);
		}
	
		/**
		 * Submits form data to the database
		 * 
		 * @throws DBException
		 * @throws IOException 
		 * 
		 */
		public void submitForm() throws DBException, IOException {
			fitnessBean.setPatient(patientID);
			fitnessBean.setDate(date);
			fitnessBean.setCalBurned(caloriesBurned);
			fitnessBean.setSteps(steps);
			fitnessBean.setDist(distance);
			fitnessBean.setFloors(floors);
			fitnessBean.setMinSed(minutesSedentary);
			fitnessBean.setMinLight(minutesLightlyActive);
			fitnessBean.setMinFair(minutesFairlyActive);
			fitnessBean.setMinVery(minutesVeryActive);
			fitnessBean.setActCal(activityCalories);
			fitnessDAO.addFitness(fitnessBean);
			NavigationController.baseFitnessHealth();
		}
		
		public FitnessDAO getFitnessDAO() {
			return fitnessDAO;
		}

		public void setFitnessDAO(FitnessDAO fitnessDAO) {
			this.fitnessDAO = fitnessDAO;
		}

		public PatientDAO getPatientDAO() {
			return patientDAO;
		}

		public void setPatientDAO(PatientDAO patientDAO) {
			this.patientDAO = patientDAO;
		}

		public PersonnelDAO getPersonnelDAO() {
			return personnelDAO;
		}

		public void setPersonnelDAO(PersonnelDAO personnelDAO) {
			this.personnelDAO = personnelDAO;
		}

		public String getDateString() {
			return dateString;
		}

		public void setDateString(String dateString) {
			this.dateString = dateString;
		}
		
		public int getMonth() {
			return month;
		}

		public void setMonth(int month) {
			this.month = month;
		}

		public int getDay() {
			return day;
		}

		public void setDay(int day) {
			this.day = day;
		}

		public int getYear() {
			return year;
		}

		public void setYear(int year) {
			this.year = year;
		}

		public File getFile() {
			return file;
		}

		public void setFile(File file) {
			this.file = file;
		}
	
		/**
		 * @return the fitnessBean
		 */
		public FitnessBean getFitnessBean() {
			return fitnessBean;
		}
	
		/**
		 * @param fitnessBean
		 *            the fitnessBean to set
		 */
		public void setFitnessBean(FitnessBean fitnessBean) {
			this.fitnessBean = fitnessBean;
		}
	
	 	/**
		 * @return the patientID
		 */
		public long getPatientID() {
			return patientID;
		}
	
		/**
		 * @param patientID
		 *            the patientID to set
		 */
		public void setPatientID(long patientID) {
			this.patientID = patientID;
		}
	
		/**
		 * @return the date
		 */
		public Timestamp getDate() {
			return date;
		}
		
		/**
		 * @param date
		 *            the date to set
		 */
		public void setDate(Timestamp date) {
			this.date = date;
		}
	
		/**
		 * @return the caloriesBurned
		 */
		public int getCaloriesBurned() {
			return caloriesBurned;
		}
	
		/**
		 * @param caloriesBurned
		 *            the caloriesBurned to set
		 */
		public void setCaloriesBurned(int caloriesBurned) {
			this.caloriesBurned = caloriesBurned;
		}
	
		/**
		 * @return the steps
		 */
		public int getSteps() {
			return steps;
		}
	
		/**
		 * @param steps
		 *            the steps to set
		 */
		public void setSteps(int steps) {
			this.steps = steps;
		}
	
		/**
		 * @return the distance
		 */
		public double getDistance() {
			return distance;
		}
	
		/**
  	     *  @param distance
		 *            the distance to set
		 */
		public void setDistance(double distance) {
			this.distance = distance;
		}
	
		/**
		 * @return the floors
		 */
		public int getFloors() {
			return floors;
		}
	
		/**
		 * @param floors
		 *            the floors to set
		 */
	    public void setFloors(int floors) {
			this.floors = floors;
		}
	
		/**
		 * @return the minutesSedentary
		 */
		public int getMinutesSedentary() {
			return minutesSedentary;
		}
	
		/**
		 * @param minutesSedentary
		 *            the minutesSedentary to set
		 */
		public void setMinutesSedentary(int minutesSedentary) {
			this.minutesSedentary = minutesSedentary;
		}
	
		/**
		 * @return the minutesLightlyActive
		 */
		public int getMinutesLightlyActive() {
			return minutesLightlyActive;
		}
	
		/**
		 * @param minutesLightlyActive
		 *            the minutesLightlyActive to set
		 */
		public void setMinutesLightlyActive(int minutesLightlyActive) {
			this.minutesLightlyActive = minutesLightlyActive;
		}
	
		/**
		 * @return the minutesFairlyActive
		 */
		public int getMinutesFairlyActive() {
			return minutesFairlyActive;
		}
	
		/**
		 * @param minutesFairlyActive
		 *            the minutesFairlyActive to set
		 */
		public void setMinutesFairlyActive(int minutesFairlyActive) {
			this.minutesFairlyActive = minutesFairlyActive;
		}
	
		/**
		 * @return the minutesVeryActive
		 */
		public int getMinutesVeryActive() {
			return minutesVeryActive;
		}
	
		/**
		 * @param minutesVeryActive
		 *            the minutesVeryActive to set
		 */
		public void setMinutesVeryActive(int minutesVeryActive) {
			this.minutesVeryActive = minutesVeryActive;
		}
	
		/**
		 * @return the activityCalories
		 */
		public int getActivityCalories() {
			return activityCalories;
		}
	
		/**
		 * @param activityCalories
		 *            the activityCalories to set
		 */
		public void setActivityCalories(int activityCalories) {
			this.activityCalories = activityCalories;
		}
		/**
		 * Submit Date
		 * @throws IOException 
		 * @throws DBException 
		 * 
		 */
		@SuppressWarnings("deprecation")
		public void submitDate() throws IOException, DBException{
			dateString = month + "/" + day + "/" + year;
			date = new Timestamp(year, month, day,0,0,0,0);
			NavigationController.editFitnessHealth();
		}
}
