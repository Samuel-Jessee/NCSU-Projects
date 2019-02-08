package edu.ncsu.csc.itrust.model.childbirth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.model.SQLLoader;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;

public class ChildbirthSQLLoader implements SQLLoader<Childbirth> {

	@Override
	public List<Childbirth> loadList(ResultSet rs) throws SQLException {
		ArrayList<Childbirth> list = new ArrayList<Childbirth>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	@Override
	public Childbirth loadSingle(ResultSet rs) throws SQLException {
		Childbirth cb = new Childbirth();
		cb.setDate(rs.getTimestamp("cbdate").toLocalDateTime());
		cb.setVisitID(Long.parseLong(rs.getString("visitID")));
		cb.setPatientMID(Long.parseLong(rs.getString("patientMID")));
		cb.setPitocin(rs.getDouble("pitocin"));
		cb.setNitrousOxide(rs.getDouble("nitrousOxide"));
		cb.setPethidine(rs.getDouble("pethidine"));
		cb.setEpiduralAnaesthesia(rs.getDouble("epiduralAnaesthesia"));
		cb.setMagnesiumSulfate(rs.getDouble("magnesiumSulfate"));
		cb.setRhimmuneglobulin(rs.getDouble("rhimmuneglobulin"));
		cb.setMethod(rs.getString("method"));
		cb.setChildrenIDs(rs.getString("childrenids"));
		return cb;
	}

	@Override
	public PreparedStatement loadParameters(Connection conn, PreparedStatement ps, Childbirth cb,
			boolean newInstance) throws SQLException {
		String stmt = "";
		if (newInstance) {
			stmt = "INSERT INTO childbirths(patientMID, pitocin, nitrousOxide, pethidine, epiduralAnaesthesia, magnesiumSulfate, rhimmuneglobulin, method, childrenids, cbdate) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			ps = conn.prepareStatement(stmt, java.sql.Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, cb.getPatientMID());
			ps.setDouble(2, cb.getPitocin());
			ps.setDouble(3, cb.getNitrousOxide());
			ps.setDouble(4, cb.getPethidine());
			ps.setDouble(5, cb.getEpiduralAnaesthesia());
			ps.setDouble(6, cb.getMagnesiumSulfate());
			ps.setDouble(7, cb.getRhimmuneglobulin());
			ps.setString(8, cb.getMethod());
			ps.setString(9, cb.getChildrenIDs());
			ps.setTimestamp(10, Timestamp.valueOf(cb.getDate()));
		} else {
			long id = cb.getVisitID();
			stmt = "UPDATE childbirths SET " + "pitocin=?, " + "nitrousOxide=?, "
				    + "pethidine=?, " + "epiduralAnaesthesia=?, " + "magnesiumSulfate=?," 
				    + "rhimmuneglobulin=?," + "method=?," + "childrenids=?," + "cbdate=?" + "WHERE visitID=" + id + ";";
			ps = conn.prepareStatement(stmt, java.sql.Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, cb.getPitocin());
			ps.setDouble(2, cb.getNitrousOxide());
			ps.setDouble(3, cb.getPethidine());
			ps.setDouble(4, cb.getEpiduralAnaesthesia());
			ps.setDouble(5, cb.getMagnesiumSulfate());
			ps.setDouble(6, cb.getRhimmuneglobulin());
			ps.setString(7, cb.getMethod());
			ps.setString(8, cb.getChildrenIDs());
			ps.setTimestamp(9, Timestamp.valueOf(cb.getDate()));
		}
		return ps;
	}
	
	public PreparedStatement loadParameters(Connection conn, PreparedStatement ps, ChildInChildbirth cb,
			boolean newInstance) throws SQLException {
		String stmt = "";
		if (newInstance) {
			stmt = "INSERT INTO childreninchildbirth(patientMID, approximate, gender, cbdate) "
					+ "VALUES (?, ?, ?, ?);";
			ps = conn.prepareStatement(stmt, java.sql.Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, cb.getMid());
			ps.setBoolean(2, cb.isApproximate());
			ps.setString(3, cb.getGender());
			ps.setTimestamp(4, Timestamp.valueOf(cb.getDate()));
		} else {
			long id = cb.getChildID();
			stmt = "UPDATE childreninchildbirth SET " + "approximate=?, " + "gender=?, " + "cbdate=?" + "WHERE childID=" + id + ";";
			ps = conn.prepareStatement(stmt, java.sql.Statement.RETURN_GENERATED_KEYS);
			ps.setBoolean(1, cb.isApproximate());
			ps.setString(2, cb.getGender());
			ps.setTimestamp(3, Timestamp.valueOf(cb.getDate()));
		}
		return ps;
	}

	public List<ChildInChildbirth> loadChildrenList(ResultSet results) throws SQLException {
		ArrayList<ChildInChildbirth> list = new ArrayList<ChildInChildbirth>();
		while (results.next()) {
			list.add(loadChildSingle(results));
		}
		return list;
	}
	
	public ChildInChildbirth loadChildSingle(ResultSet rs) throws SQLException {
		ChildInChildbirth cb = new ChildInChildbirth();
		cb.setDate(rs.getTimestamp("cbdate").toLocalDateTime());
		cb.setChildID(Long.parseLong(rs.getString("childID")));
		cb.setMid(Long.parseLong(rs.getString("patientMID")));
		cb.setApproximate(rs.getBoolean("approximate"));
		cb.setGender(rs.getString("gender"));
		return cb;
	}
	

}
