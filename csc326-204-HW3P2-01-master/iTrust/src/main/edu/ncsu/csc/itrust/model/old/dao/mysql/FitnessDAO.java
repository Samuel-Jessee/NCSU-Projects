package edu.ncsu.csc.itrust.model.old.dao.mysql;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.beans.FitnessBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.FitnessBeanLoader;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;

/**
 * This class is the main control panel for retrieving health tracking data from
 * the sql table.
 * 
 * @author Louis Le
 *
 */
public class FitnessDAO {
	// Class instances used to run and interpret sql commands.
	private DAOFactory factory;
	private FitnessBeanLoader ftLoader;

	/**
	 * Constructor of FitnessDAO, sets up the factory used to run sql commands
	 * and the loader which interprets the sql ResultSet output.
	 * 
	 * @param factory
	 *            Factory variable used to run sql commands.
	 */
	public FitnessDAO(DAOFactory factory) {
		this.factory = factory;
		this.ftLoader = new FitnessBeanLoader();
	}

	/**
	 * Returns fitness data for a specific date
	 * 
	 * @param mid
	 *            Patient id
	 * @param date
	 *            Date of requested data
	 * @return FitnessBean containing data on specified patient and date
	 * @throws DBException
	 *             Thrown if the date does not exist
	 */
	public FitnessBean getFitnessForDate(long mid, Timestamp date) throws DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement stmt = conn
						.prepareStatement("SELECT * FROM fitness WHERE patient_id = ? AND fitness_date = ?")) {
			stmt.setLong(1, mid);
			stmt.setTimestamp(2, date);
			ResultSet rs = stmt.executeQuery();

			FitnessBean fitnessBean = this.ftLoader.loadSingle(rs);
			rs.close();
			return fitnessBean;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	/**
	 * Given a patient mid and start and end date, a list of FitnessBean(s) will
	 * be returned
	 * 
	 * @param mid
	 *            Patient id
	 * @param sDate
	 *            Start date
	 * @param eDate
	 *            End date
	 * @return List of data on specified patient between the given dates
	 *         (inclusive)
	 * @throws DBException
	 *             Thrown if sql error
	 */
	public List<FitnessBean> getFitnessRange(long mid, Timestamp sDate, Timestamp eDate) throws DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(
						"SELECT * FROM fitness WHERE patient_id = ? AND fitness_date between ? AND ?")) {
			stmt.setLong(1, mid);
			stmt.setTimestamp(2, sDate);
			stmt.setTimestamp(3, eDate);
			ResultSet rs = stmt.executeQuery();

			List<FitnessBean> fitnessList = this.ftLoader.loadList(rs);
			rs.close();
			return fitnessList;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	/**
	 * Adds a specified date's health tracking information of patient
	 * 
	 * @param fitnessBean
	 *            Data to be adding
	 * @throws DBException
	 *             Thrown if inserted
	 */
	public void addFitness(FitnessBean fitnessBean) throws DBException {
		try {
			// Tests to see if date is already in table
			editFitness(fitnessBean);
		} catch (DBException e) {
			// If date isn't in table, then add it
			try (Connection conn = factory.getConnection();
					PreparedStatement stmt = ftLoader.loadParameters(conn.prepareStatement(
							"INSERT INTO fitness (patient_id, fitness_date, calories_burned, steps, distance, floors, minutes_sedentary, minutes_light, minutes_fair, minutes_very, active_calories) "
									+ "  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
							fitnessBean)) {
				stmt.executeUpdate();
			} catch (SQLException f) {
				throw new DBException(f);
			}
		}
	}

	/**
	 * Adds a list of fitness beans
	 * 
	 * @param fitnessList
	 *            List of FitnessBean(s) that will be added to the table
	 * @throws DBException
	 *             Thrown if this tuple (row) doesn't exist in the table
	 */
	public void addFitnesses(List<FitnessBean> fitnessList) throws DBException {
		for (int i = 0; i < fitnessList.size(); i++) {
			addFitness(fitnessList.get(i));
		}
	}

	/**
	 * Edits tuple (row) of sql table given specified data.
	 * 
	 * @param fitnessBean
	 *            Fitness Bean containing date and patient's data to modify
	 * @throws DBException
	 *             Thrown if this tuple (row) doesn't exist in the table
	 */
	public void editFitness(FitnessBean fitnessBean) throws DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement stmt = conn
						.prepareStatement("UPDATE fitness WHERE patient_id = ? AND fitness_date = ?")) {
			stmt.setLong(1, fitnessBean.getPatient());
			stmt.setTimestamp(2, fitnessBean.getDate());
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
}
