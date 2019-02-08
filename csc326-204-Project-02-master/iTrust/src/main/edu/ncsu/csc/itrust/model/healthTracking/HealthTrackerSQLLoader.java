package edu.ncsu.csc.itrust.model.healthTracking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import edu.ncsu.csc.itrust.model.SQLLoader;

public class HealthTrackerSQLLoader implements SQLLoader<HealthTracker>{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthTracker> loadList(ResultSet rs) throws SQLException {
		ArrayList<HealthTracker> list = new ArrayList<HealthTracker>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthTracker loadSingle(ResultSet rs) throws SQLException {
		HealthTracker ht = new HealthTracker();
		ht.setActivityCalories(rs.getInt("ActivityCalories"));
		ht.setCaloriesBurned(rs.getInt("CaloriesBurned"));
		ht.setDate(rs.getTimestamp("Date"));
		ht.setDistance(rs.getDouble("Distance"));
		ht.setFloors(rs.getInt("Floors"));
		ht.setHCP(rs.getLong("HCP"));
		ht.setMinutesFairlyActive(rs.getInt("MinutesFairlyActive"));
		ht.setMinutesLightlyActive(rs.getInt("MinutesLightlyActive"));
		ht.setMinutesSedentary(rs.getInt("MinutesSedentary"));
		ht.setMinutesVeryActive(rs.getInt("MinutesVeryActive"));
		ht.setPatient(rs.getLong("Patient"));
		ht.setTrackerID(rs.getLong("ID"));
		ht.setSteps(rs.getInt("Steps"));
		ht.setHrLowest(rs.getInt("HRLowest"));
		ht.setHrHighest(rs.getInt("HRHighest"));
		ht.setHrAverage(rs.getInt("HRAverage"));
		ht.setActiveHours(rs.getInt("ActiveHours"));
		ht.setUVExposure(rs.getInt("UVExposure"));
		return ht;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public PreparedStatement loadParameters(Connection conn, PreparedStatement ps, HealthTracker ht, boolean newInstance)
			throws SQLException {
		String stmt = "";
		if (newInstance) {
			stmt = "INSERT INTO fitnessinfo(Date, CaloriesBurned, Steps, Distance, Floors, MinutesSedentary, MinutesLightlyActive, MinutesFairlyActive, MinutesVeryActive, ActivityCalories, HRLowest, HRHighest, HRAverage, ActiveHours, UVExposure, Patient, HCP) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		} else {
			long trackerID = ht.getTrackerID();
			stmt = "UPDATE fitnessinfo SET Date=?, "
					+ "CaloriesBurned=?, "
					+ "Steps=?, "
					+ "Distance=?, "
					+ "Floors=?, "
					+ "MinutesSedentary=?, "
					+ "MinutesLightlyActive=?, "
					+ "MinutesFairlyActive=?, "
					+ "MinutesVeryActive=?, "
					+ "ActivityCalories=?, "
					+ "HRLowest=?, "
					+ "HRHighest=?, "
					+ "HRAverage=?, "
					+ "ActiveHours=?, "
					+ "UVExposure=?, "
					+ "Patient=?, "
					+ "HCP=? "
					+ "WHERE ID=" + trackerID + ";";
		}
		ps = conn.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
		
		ps.setDate(1, new java.sql.Date(ht.getDate().getTime()));
		ps.setInt(2, ht.getCaloriesBurned());
		ps.setInt(3, ht.getSteps());
		ps.setDouble(4, ht.getDistance());
		ps.setInt(5, ht.getFloors());
		ps.setInt(6, ht.getMinutesSedentary());
		ps.setInt(7, ht.getMinutesLightlyActive());
		ps.setInt(8, ht.getMinutesLightlyActive());
		ps.setInt(9, ht.getMinutesVeryActive());
		ps.setInt(10, ht.getActivityCalories());
		ps.setInt(11, ht.getHrLowest());
		ps.setInt(12, ht.getHrHighest());
		ps.setInt(13, ht.getHrAverage());
		ps.setInt(14, ht.getActiveHours());
		ps.setInt(15, ht.getUVExposure());
		ps.setLong(16, ht.getPatient());
		ps.setLong(17, ht.getHCP());
		
		return ps;
	}
	
	/**
	 * Get the integer value if initialized in DB, otherwise get null.
	 * 
	 * @param rs 
	 * 		ResultSet object
	 * @param field
	 * 		name of DB attribute 
	 * @return Integer value or null
	 * @throws SQLException when field doesn't exist in the result set
	 */
	public Integer getIntOrNull(ResultSet rs, String field) throws SQLException {
		Integer ret = rs.getInt(field);
		if (rs.wasNull()) {
			ret = null;
		}
		return ret;
	}
	
	/**
	 * Get the float value if initialized in DB, otherwise get null.
	 * 
	 * @param rs 
	 * 		ResultSet object
	 * @param field
	 * 		name of DB attribute 
	 * @return Float value or null
	 * @throws SQLException when field doesn't exist in the result set
	 */
	public Float getFloatOrNull(ResultSet rs, String field) throws SQLException {
		Float ret = rs.getFloat(field);
		if (rs.wasNull()) {
			ret = null;
		}
		return ret;
	}
	
	/**
	 * Set integer placeholder in statement to a value or null
	 * 
	 * @param ps
	 * 		PreparedStatement object
	 * @param index
	 * 		Index of placeholder in the prepared statement
	 * @param value
	 * 		Value to set to placeholder, the value may be null 
	 * @throws SQLException
	 * 		When placeholder is invalid
	 */
	public void setIntOrNull(PreparedStatement ps, int index, Integer value) throws SQLException {
		if (value == null) {
			ps.setNull(index, java.sql.Types.INTEGER);
		} else {
			ps.setInt(index, value);
		}
	}
	
	/**
	 * Set float placeholder in statement to a value or null
	 * 
	 * @param ps
	 * 		PreparedStatement object
	 * @param index
	 * 		Index of placeholder in the prepared statement
	 * @param value
	 * 		Value to set to placeholder, the value may be null 
	 * @throws SQLException
	 * 		When placeholder is invalid
	 */
	public void setFloatOrNull(PreparedStatement ps, int index, Float value) throws SQLException {
		if (value == null) {
			ps.setNull(index, java.sql.Types.FLOAT);
		} else {
			ps.setFloat(index, value);
		}
	}

}
