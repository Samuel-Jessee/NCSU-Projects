package edu.ncsu.csc.itrust.model.fitness;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.model.SQLLoader;

/**
 * Class that loads data into the table
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
public class FitnessSQLLoader implements SQLLoader<Fitness> {

	/**
	 * Get the Double value if initialized in DB, otherwise get null.
	 * 
	 * @param rs
	 *            ResultSet object
	 * @param field
	 *            name of DB attribute
	 * @return Double value or null
	 * @throws SQLException
	 *             when field doesn't exist in the result set
	 */
	public Double getDoubleOrNull(ResultSet rs, String field) throws SQLException {
		Double ret = rs.getDouble(field);
		if (rs.wasNull()) {
			ret = null;
		}
		return ret;
	}

	/**
	 * Get the integer value if initialized in DB, otherwise get null.
	 * 
	 * @param rs
	 *            ResultSet object
	 * @param field
	 *            name of DB attribute
	 * @return Integer value or null
	 * @throws SQLException
	 *             when field doesn't exist in the result set
	 */
	public Integer getIntOrNull(ResultSet rs, String field) throws SQLException {
		Integer ret = rs.getInt(field);
		if (rs.wasNull()) {
			ret = null;
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Fitness> loadList(ResultSet rs) throws SQLException {
		ArrayList<Fitness> list = new ArrayList<Fitness>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PreparedStatement loadParameters(Connection conn, PreparedStatement ps, Fitness fitness, boolean newInstance)
			throws SQLException {
		String stmt = "";
		if (newInstance) {
			stmt = "INSERT INTO fitness(" + "patient_id, " + "fitness_date, " + "cal_burned, " + "steps, "
					+ "distance, " + "floors, " + "min_sedentary, " + "min_light, " + "min_fair, " + "min_very, "
					+ "act_cal, " + "low_hr, " + "high_hr, " + "average_hr, " + "act_hours, " + "min_uv) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		} else {
			long id = fitness.getFitnessID();
			stmt = "UPDATE fitness SET " + "patient_id=?, " + "fitness_date=?, " + "cal_burned=?, " + "steps=?, "
					+ "distance=?, " + "floors=?, " + "min_sedentary=?, " + "min_light=?, " + "min_fair=?, "
					+ "min_very=?, " + "act_cal=?, " + "low_hr=?, " + "high_hr=?, " + "average_hr=?, " + "act_hours=?, "
					+ "min_uv=? " + "WHERE fitness_id=" + id + ";";
		}
		ps = conn.prepareStatement(stmt, java.sql.Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, fitness.getPatientID());
		Date d = Date.valueOf(fitness.getDate());
		ps.setDate(2, d);
		setIntOrNull(ps, 3, fitness.getCalBurned());
		setIntOrNull(ps, 4, fitness.getSteps());
		setDoubleOrNull(ps, 5, fitness.getDistance());
		setIntOrNull(ps, 6, fitness.getFloors());
		setIntOrNull(ps, 7, fitness.getMinSedentary());
		setIntOrNull(ps, 8, fitness.getMinLight());
		setIntOrNull(ps, 9, fitness.getMinFair());
		setIntOrNull(ps, 10, fitness.getMinVery());
		setIntOrNull(ps, 11, fitness.getActCal());
		setIntOrNull(ps, 12, fitness.getLowHR());
		setIntOrNull(ps, 13, fitness.getHighHR());
		setIntOrNull(ps, 14, fitness.getAverageHR());
		setIntOrNull(ps, 15, fitness.getActHours());
		setIntOrNull(ps, 16, fitness.getMinUV());
		return ps;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Fitness loadSingle(ResultSet rs) throws SQLException {
		Fitness retFit = new Fitness();
		retFit.setFitnessID(Long.parseLong(rs.getString("fitness_id")));
		retFit.setPatientID(Long.parseLong(rs.getString("patient_id")));
		retFit.setDate(rs.getDate("fitness_date").toLocalDate());
		retFit.setCalBurned(rs.getInt("cal_burned"));
		retFit.setSteps(rs.getInt("steps"));
		retFit.setDistance(rs.getDouble("distance"));
		retFit.setFloors(rs.getInt("floors"));
		retFit.setMinSedentary(rs.getInt("min_sedentary"));
		retFit.setMinLight(rs.getInt("min_light"));
		retFit.setMinFair(rs.getInt("min_fair"));
		retFit.setMinVery(rs.getInt("min_very"));
		retFit.setActCal(rs.getInt("act_cal"));
		retFit.setLowHR(rs.getInt("low_hr"));
		retFit.setHighHR(rs.getInt("high_hr"));
		retFit.setAverageHR(rs.getInt("average_hr"));
		retFit.setActHours(rs.getInt("act_hours"));
		retFit.setMinUV(rs.getInt("min_uv"));
		return retFit;
	}

	/**
	 * Set double placeholder in statement to a value or null
	 * 
	 * @param ps
	 *            PreparedStatement object
	 * @param index
	 *            Index of placeholder in the prepared statement
	 * @param value
	 *            Value to set to placeholder, the value may be null
	 * @throws SQLException
	 *             When placeholder is invalid
	 */
	public void setDoubleOrNull(PreparedStatement ps, int index, Double value) throws SQLException {
		if (value == null) {
			ps.setNull(index, java.sql.Types.DOUBLE);
		} else {
			ps.setDouble(index, value);
		}
	}

	/**
	 * Set integer placeholder in statement to a value or null
	 * 
	 * @param ps
	 *            PreparedStatement object
	 * @param index
	 *            Index of placeholder in the prepared statement
	 * @param value
	 *            Value to set to placeholder, the value may be null
	 * @throws SQLException
	 *             When placeholder is invalid
	 */
	public void setIntOrNull(PreparedStatement ps, int index, Integer value) throws SQLException {
		if (value == null) {
			ps.setNull(index, java.sql.Types.INTEGER);
		} else {
			ps.setInt(index, value);
		}
	}
}
