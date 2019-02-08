/**
 * 
 */
package edu.ncsu.csc.itrust.model.obstetricsInitialization;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.model.SQLLoader;

/**
 * Handles loading data from the database
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
public class ObstetricsInitializationSQLLoader implements SQLLoader<ObstetricsInitialization> {

	/**
	 * Class that handles loading pregnancy data from the database
	 * 
	 * @author Samuel Jessee (sijessee)
	 *
	 */
	private class PregLoader implements SQLLoader<PreviousPregnancyInfo> {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public List<PreviousPregnancyInfo> loadList(ResultSet rs) throws SQLException {
			ArrayList<PreviousPregnancyInfo> list = new ArrayList<PreviousPregnancyInfo>();
			while (rs.next()) {
				list.add(loadSingle(rs));
			}
			return list;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public PreparedStatement loadParameters(Connection conn, PreparedStatement ps, PreviousPregnancyInfo pi,
				boolean newInstance) throws SQLException {
			String stmt = "";
			if (newInstance) {
				stmt = "INSERT INTO previousPregnancyInfo(amtChildren, deliveryType, hoursInLabor, patientMID, "
						+ "weeksPreg, weightGained, year ) " + "VALUES (?, ?, ?, ?, ?, ?, ?);";
			} else {
				long id = pi.getInfoID();
				stmt = "UPDATE previousPregnancyInfo SET " + "amtChildren=?, " + "deliveryType=?, "
						+ "hoursInLabor=?, " + "patientMID=?, " + "weeksPreg=?, " + "weightGained=?, " + "year=? "
						+ "WHERE infoID=" + id + ";";
			}
			ps = conn.prepareStatement(stmt, java.sql.Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, pi.getAmtChildren());
			ps.setInt(2, pi.getDeliveryType());
			ps.setInt(3, pi.getHoursInLabor());
			ps.setLong(4, pi.getPatientMID());
			ps.setInt(5, pi.getWeeksPreg());
			ps.setInt(6, pi.getWeightGained());
			ps.setInt(7, pi.getYear());
			return ps;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public PreviousPregnancyInfo loadSingle(ResultSet rs) throws SQLException {
			PreviousPregnancyInfo ret = new PreviousPregnancyInfo();
			ret.setAmtChildren(rs.getInt("amtChildren"));
			ret.setDeliveryType(rs.getInt("deliveryType"));
			ret.setHoursInLabor(rs.getInt("hoursInLabor"));
			ret.setInfoID(Long.parseLong(rs.getString("infoID")));
			ret.setPatientMID(Long.parseLong(rs.getString("patientMID")));
			ret.setWeeksPreg(rs.getInt("weeksPreg"));
			ret.setWeightGained(rs.getInt("weightGained"));
			ret.setYear(rs.getInt("year"));
			return ret;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ObstetricsInitialization> loadList(ResultSet rs) throws SQLException {
		ArrayList<ObstetricsInitialization> list = new ArrayList<ObstetricsInitialization>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PreparedStatement loadParameters(Connection conn, PreparedStatement ps, ObstetricsInitialization oi,
			boolean newInstance) throws SQLException {
		String stmt = "";
		if (newInstance) {
			stmt = "INSERT INTO obstetricsInitialization(oDate, EDD, LMP, patientMID, weeksPreg, RhNeg) "
					+ "VALUES (?, ?, ?, ?, ?, ?);";
		} else {
			long id = oi.getVisitID();
			stmt = "UPDATE obstetricsInitialization SET " + "oDate=?, " + "EDD=?, " + "LMP=?, " + "patientMID=?, "
					+ "weeksPreg=? " + "RhNeg=?" + "WHERE visitID=" + id + ";";
		}
		ps = conn.prepareStatement(stmt, java.sql.Statement.RETURN_GENERATED_KEYS);
		ps.setDate(1, Date.valueOf(oi.getDate().toLocalDate()));
		ps.setDate(2, Date.valueOf(oi.getEDD()));
		ps.setDate(3, Date.valueOf(oi.getLMP()));
		ps.setLong(4, oi.getPatientMID());
		ps.setInt(5, oi.getWeeksPreg());
		ps.setBoolean(6, oi.isRH());
		return ps;
	}

	/**
	 * Used for an insert or update, this method contains the instructions for
	 * mapping the fields within a bean of type T into a prepared statement
	 * which modifies the appropriate table.
	 * 
	 * @param conn
	 *            The connection to the database
	 * @param ps
	 *            The prepared statement to be loaded.
	 * @param insertObject
	 *            The object of type T containing the data to be placed.
	 * @param newInstance
	 *            True if a new instance of the object should be created, false
	 *            if the prepared statement should update an existing instance
	 *            of the object
	 * @return A prepared statement with the appropriately loaded parameters.
	 * @throws SQLException
	 */
	public PreparedStatement loadParameters(Connection conn, PreparedStatement pstring, PreviousPregnancyInfo pi,
			boolean newInstance) throws SQLException {
		PregLoader pl = new PregLoader();
		return pl.loadParameters(conn, pstring, pi, newInstance);
	}

	/**
	 * Method to load prior pregnancy list
	 * 
	 * @param results
	 *            database results
	 * @return list of prior pregnancies
	 * @throws SQLException
	 *             if error occurs while loading list
	 */
	public List<PreviousPregnancyInfo> loadPregList(ResultSet results) throws SQLException {
		PregLoader pl = new PregLoader();
		return pl.loadList(results);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ObstetricsInitialization loadSingle(ResultSet rs) throws SQLException {
		ObstetricsInitialization retVisit = new ObstetricsInitialization();
		retVisit.setDate(rs.getDate("oDate").toLocalDate().atStartOfDay());
		// EDD is set automatically
		retVisit.setLMP(rs.getDate("LMP").toLocalDate());
		retVisit.setRH(rs.getBoolean("RhNeg"));
		retVisit.setPatientMID(Long.parseLong(rs.getString("patientMID")));
		retVisit.setVisitID(Long.parseLong(rs.getString("visitID")));
		// weeksPreg set automatically
		return retVisit;
	}
}
