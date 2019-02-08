package edu.ncsu.csc.itrust.model.obstetricsOfficeVisit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.model.SQLLoader;

/**
 * 
 * @author abbyr
 *
 */
public class ObstetricsOfficeVisitSQLLoader implements SQLLoader<ObstetricsOfficeVisit>  {

	@Override
	public List<ObstetricsOfficeVisit> loadList(ResultSet rs) throws SQLException {
		ArrayList<ObstetricsOfficeVisit> list = new ArrayList<ObstetricsOfficeVisit>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	@Override
	public ObstetricsOfficeVisit loadSingle(ResultSet rs) throws SQLException {
		ObstetricsOfficeVisit obov = new ObstetricsOfficeVisit();
		obov.setVisitID(Long.parseLong(rs.getString("visitID")));
		obov.setPatientMID(Long.parseLong(rs.getString("patientMID")));
		obov.setDate(rs.getDate("oDate"));
		obov.setNumWeeks(rs.getInt("numWeeks"));
		obov.setWeight(rs.getDouble("weight"));
		obov.setBloodPressure(rs.getString("bloodPressure"));
		obov.setFHR(rs.getInt("FHR"));
		obov.setNumFetus(rs.getInt("numFetus"));
		obov.setLowLyingPlacenta(rs.getBoolean("lowLyingPlacenta"));
		obov.setUltrasoundString(rs.getString("ultrasound"));
		obov.setHighgenetic(rs.getBoolean("highgenetic"));
		obov.setComplications(rs.getString("complications"));
		InputStream fis = rs.getBinaryStream("picture");
		if(fis != null){
			File f = new File("UltraPic");
			FileOutputStream os = null;
			try {
				os = new FileOutputStream(f);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(os != null){
				byte[] buffer = new byte[16777215];
				int read = -1;
				try {
					while((read = fis.read(buffer)) != -1){
						os.write(buffer, 0 , read);
						os.flush();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
			obov.setPicture(f);
		}
		else{
			obov.setPicture(null);
		}
		return obov;
	}

	@Override
	public PreparedStatement loadParameters(Connection conn, PreparedStatement ps, ObstetricsOfficeVisit ov,
			boolean newInstance) throws SQLException {
		String stmt = "";
		if (newInstance) {
			stmt = "INSERT INTO obstetricsofficevisit(oDate, numWeeks, weight, bloodPressure, FHR, numFetus,"
					+ "lowLyingPlacenta, ultrasound, patientMID, picture, highgenetic, complications) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			ps = conn.prepareStatement(stmt, java.sql.Statement.RETURN_GENERATED_KEYS);
			LocalDate date = new java.sql.Date(ov.getDate().getTime()).toLocalDate();
			ps.setDate(1, Date.valueOf(date));
			ps.setInt(2, ov.getNumWeeks());
			ps.setDouble(3, ov.getWeight());
			ps.setString(4, ov.getBloodPressure());
			ps.setInt(5, ov.getFHR());
			ps.setInt(6, ov.getNumFetus());
			ps.setBoolean(7, ov.isLowLyingPlacenta());
			ps.setString(8, ov.getUltrasoundString());
			ps.setLong(9, ov.getPatientMID());
			if(ov.getPicture() != null){
				File f = new File(ov.getPicture().getPath());
				FileInputStream fr = null;
				try {
					fr = new FileInputStream(f);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				ps.setBinaryStream(10, fr);
			}
			else{
				ps.setBinaryStream(10, null);
			}
			ps.setBoolean(11, ov.isHighgenetic());
			ps.setString(12, ov.getComplications());
		} else {
			long id = ov.getVisitID();
			stmt = "UPDATE obstetricsofficevisit SET " + "weight=?, " + "bloodPressure=?, "
				    + "FHR=?, " + "numFetus=?, " + "LowLyingPlacenta=?, " 
				    + "highgenetic=?, " + "complications=? " + "WHERE visitID=" + id + ";";
			ps = conn.prepareStatement(stmt, java.sql.Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, ov.getWeight());
			ps.setString(2, ov.getBloodPressure());
			ps.setInt(3, ov.getFHR());
			ps.setInt(4, ov.getNumFetus());
			ps.setBoolean(5, ov.isLowLyingPlacenta());
			ps.setBoolean(6, ov.isHighgenetic());
			ps.setString(7, ov.getComplications());
		}
		return ps;
	}
	
	public List<Ultrasound> loadUltraList(ResultSet rs) throws SQLException {
		ArrayList<Ultrasound> list = new ArrayList<Ultrasound>();
		while (rs.next()) {
			list.add(loadUltraSingle(rs));
		}
		return list;
	}
	
	public Ultrasound loadUltraSingle(ResultSet rs) throws SQLException {
		Ultrasound us = new Ultrasound();
		us.setUltraID(Long.parseLong(rs.getString("ultraID")));
		us.setPatientMID(Long.parseLong(rs.getString("patientMID")));
		us.setCRL(rs.getDouble("CRL"));
		us.setBPD(rs.getDouble("BPD"));	
		us.setHC(rs.getDouble("HC"));
		us.setFL(rs.getDouble("FL"));
		us.setOFD(rs.getDouble("OFD"));
		us.setAC(rs.getDouble("AC"));
		us.setHL(rs.getDouble("HL"));
		us.setEFW(rs.getDouble("EFW"));
		return us;
	}

	public PreparedStatement loadParameters(Connection conn, PreparedStatement ps, Ultrasound us,
			boolean newInstance) throws SQLException {
		String stmt = "";
		if (newInstance) {
			stmt = "INSERT INTO ultrasound(CRL, BPD, HC, FL, OFD, AC,"
					+ "HL, EFW, patientMID) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		} else {
			long id = us.getUltraID();
			stmt = "UPDATE ultrasound SET " + "CRL=?, " + "BPD=?, "
					+ "HC=?, " + "FL=?, " + "OFD=?, " + "AC=?, " + "HL=? " + "EFW=?"
					+ "patientMID=?, " + "WHERE ultraID=" + id + ";";
		}
		ps = conn.prepareStatement(stmt, java.sql.Statement.RETURN_GENERATED_KEYS);
		ps.setDouble(1, us.getCRL());
		ps.setDouble(2, us.getBPD());
		ps.setDouble(3, us.getHC());
		ps.setDouble(4, us.getFL());
		ps.setDouble(5, us.getOFD());
		ps.setDouble(6, us.getAC());
		ps.setDouble(7, us.getHL());
		ps.setDouble(8, us.getEFW());
		ps.setLong(9, us.getPatientMID());
		return ps;
	}

}
