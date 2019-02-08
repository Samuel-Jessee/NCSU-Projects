package edu.ncsu.csc.itrust.action;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;

import edu.ncsu.csc.itrust.CSVParser;
import edu.ncsu.csc.itrust.exception.CSVFormatException;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.AddPatientFitnessException;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.model.old.beans.FitnessBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.FitnessDAO;

/**
 * Action to add fitness info to the database from a file
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
@ManagedBean(name = "add_fitness_file")
@ViewScoped
public class AddPatientFitnessAction {

	/**
	 * DAOFactory that manages databases
	 */
	private DAOFactory factory;

	/**
	 * File being uploaded
	 */
	private Part uploadedFile;

	/**
	 * Holds the accumulated list of errors from the CSVParser and this class
	 */
	private ErrorList errors;

	/**
	 * Holds the CSV header from the CSVParser
	 */
	private ArrayList<String> CSVHeader;

	/**
	 * Holds the CSV data from the CSVParser
	 */
	private ArrayList<ArrayList<String>> CSVData;

	/**
	 * Holds the list of FitnessBeans for passing back to the UI
	 */
	private ArrayList<FitnessBean> fitnesses = new ArrayList<FitnessBean>();

	/**
	 * List of fields required to be in the CSV
	 */
	private String[] requiredFields = { "patient_id", "fitness_date" };

	/**
	 * List of valid fields which can be included in the CSV
	 */
	private String[] validFields = { "calories_burned", "steps", "distance", "floors", "minutes_sedentary",
			"minutes_light", "minutes_fair", "minutes_very", "active_calories" };

	/**
	 * Array to map the required field lists above to the uploaded CSV header
	 * list (which may be in any order)
	 */
	private Integer requiredFieldsMapping[] = new Integer[2];

	/**
	 * Array to map the valid field lists above to the uploaded CSV header list
	 * (which may be in any order)
	 */
	private Integer validFieldsMapping[] = new Integer[11];

	/**
	 * @return the factory
	 */
	public DAOFactory getFactory() {
		return factory;
	}

	/**
	 * @param factory
	 *            the factory to set
	 */
	public void setFactory(DAOFactory factory) {
		this.factory = factory;
	}

	/**
	 * @return the uploadedFile
	 */
	public Part getUploadedFile() {
		return uploadedFile;
	}

	/**
	 * @param uploadedFile
	 *            the uploadedFile to set
	 */
	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	/**
	 * FitnessDAO used to add fitness data to the DB
	 */
	private FitnessDAO fitnessDAO;

	// MID of the HCP performing the request
	// private long loggedInMID;

	/**
	 * Retrieves the needed DAO in preparation for parsing files.
	 * 
	 * @throws CSVFormatException
	 * @throws AddPatientFitnessException
	 */
	public AddPatientFitnessAction() throws CSVFormatException, AddPatientFitnessException {
		factory = DAOFactory.getProductionInstance();
		if (factory != null) {
			this.fitnessDAO = factory.getFitnessDAO();
			// this.loggedInMID = loggedInMID;
		}

	}

	/**
	 * Creates input stream from file and parses
	 * 
	 * @throws CSVFormatException
	 * @throws AddPatientFitnessException
	 */
	public void upload() throws CSVFormatException, AddPatientFitnessException {
		if (null != uploadedFile) {
			try {
				InputStream CSVStream = uploadedFile.getInputStream();
				CSVParser parser = new CSVParser(CSVStream);
				CSVHeader = parser.getHeader();
				CSVData = parser.getData();
				errors = parser.getErrors();
				buildMappings(CSVHeader);
				try {
					createFitnesses();
				} catch (DBException e) {
					throw new AddPatientFitnessException("Database error while adding new patient fitness data!");
				}
			} catch (IOException ex) {
			}
		}
	}

	/**
	 * Gets ArrayList of fitness data from the parsed file
	 * 
	 * @return fitness data
	 */
	public ArrayList<FitnessBean> getFitnesses() {
		return fitnesses;
	}

	/**
	 * Gets the list of all errors encountered while parsing
	 * 
	 * @return list of errors
	 */
	public ErrorList getErrors() {
		return errors;
	}

	/**
	 * Builds the mappings between the local arrays and the CSV file Also checks
	 * for missing required, duplicate, and invalid fields
	 * 
	 * @param CSVHeader
	 * @throws AddPatientFitnessException
	 */
	private void buildMappings(ArrayList<String> CSVHeader) throws AddPatientFitnessException {
		boolean valid;
		for (int i = 0; i < CSVHeader.size(); i++) {
			valid = false;
			for (int j = 0; j < requiredFields.length; j++) {
				if (CSVHeader.get(i).equalsIgnoreCase(requiredFields[j])) {
					if (requiredFieldsMapping[j] == null) {
						valid = true;
						requiredFieldsMapping[j] = i;
					} else {
						throw new AddPatientFitnessException("Duplicate field \"" + CSVHeader.get(i) + "\"!");
					}
				}
			}
			for (int j = 0; j < validFields.length; j++) {
				if (CSVHeader.get(i).equalsIgnoreCase(validFields[j])) {
					if (validFieldsMapping[j] == null) {
						valid = true;
						validFieldsMapping[j] = i;
					} else {
						throw new AddPatientFitnessException("Duplicate field \"" + CSVHeader.get(i) + "\"!");
					}
				}
			}
			if (valid == false) {
				throw new AddPatientFitnessException("Field \"" + CSVHeader.get(i) + "\" is invalid!");
			}
		}
		for (int i = 0; i < requiredFieldsMapping.length; i++) {
			if (requiredFieldsMapping[i] == null) {
				throw new AddPatientFitnessException("Required field \"" + requiredFields[i] + "\" is missing!");
			}
		}
	}

	/**
	 * Creates the fitness beans and adds them to the DB
	 * 
	 * @throws DBException
	 * @throws AddPatientFitnessException
	 */
	private void createFitnesses() throws DBException, AddPatientFitnessException {
		for (int i = 0; i < CSVData.size(); i++) {
			FitnessBean temp = new FitnessBean();

			// patient_id
			temp.setPatient(Long.parseLong(
					CSVData.get(i).get(requiredFieldsMapping[Arrays.asList(requiredFields).indexOf("patient_id")])));

			// fitness_date
			temp.setDate(Timestamp.valueOf(
					CSVData.get(i).get(requiredFieldsMapping[Arrays.asList(requiredFields).indexOf("fitness_date")])));

			// calories_burned
			try {
				temp.setCalBurned(Integer.parseInt(CSVData.get(i)
						.get(requiredFieldsMapping[Arrays.asList(requiredFields).indexOf("calories_burned")])));
			} catch (NullPointerException e) {
			}

			// steps
			try {
				temp.setSteps(Integer.parseInt(
						CSVData.get(i).get(requiredFieldsMapping[Arrays.asList(requiredFields).indexOf("steps")])));
			} catch (NullPointerException e) {
			}

			// distance
			try {
				temp.setDist(Double.parseDouble(
						CSVData.get(i).get(requiredFieldsMapping[Arrays.asList(requiredFields).indexOf("distance")])));
			} catch (NullPointerException e) {
			}

			// floors
			try {
				temp.setFloors(Integer.parseInt(
						CSVData.get(i).get(requiredFieldsMapping[Arrays.asList(requiredFields).indexOf("floors")])));
			} catch (NullPointerException e) {
			}

			// minutes_sedentary
			try {
				temp.setMinSed(Integer.parseInt(CSVData.get(i)
						.get(requiredFieldsMapping[Arrays.asList(requiredFields).indexOf("minutes_sedentary")])));
			} catch (NullPointerException e) {
			}

			// minutes_light
			try {
				temp.setMinLight(Integer.parseInt(CSVData.get(i)
						.get(requiredFieldsMapping[Arrays.asList(requiredFields).indexOf("minutes_light")])));
			} catch (NullPointerException e) {
			}

			// minutes_fair
			try {
				temp.setMinFair(Integer.parseInt(CSVData.get(i)
						.get(requiredFieldsMapping[Arrays.asList(requiredFields).indexOf("minutes_fair")])));
			} catch (NullPointerException e) {
			}

			// minutes_very
			try {
				temp.setMinVery(Integer.parseInt(CSVData.get(i)
						.get(requiredFieldsMapping[Arrays.asList(requiredFields).indexOf("minutes_very")])));
			} catch (NullPointerException e) {
			}

			// active_calories
			try {
				temp.setActCal(Integer.parseInt(CSVData.get(i)
						.get(requiredFieldsMapping[Arrays.asList(requiredFields).indexOf("active_calories")])));
			} catch (NullPointerException e) {
			}

			if (fitnessDAO != null) {
				fitnessDAO.addFitness(temp);
			}
			fitnesses.add(temp);
		}
	}
}
