package edu.ncsu.csc.itrust.model.obstetricsOfficeVisit;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.ValidationFormat;
/**
 * Class that interacts with the controller and databases to add Obstetrics Office Visits
 * @author abbyr
 *
 */
public class ObstetricsOfficeVisitMySQL implements Serializable, ObstetricsOfficeVisitData {
	@Resource(name = "jbdc/itrust2")
	private static final long serialVersionUID = -672480913077894085L;
	private ObstetricsOfficeVisitSQLLoader ovLoader;
	private BasicDataSource ds;
	private ObstetricsOfficeVisitValidator validator;

	/**
	 * Default constructor for ObstetricsOfficeVisitMySQL.
	 * 
	 * @throws DBException
	 *             if there is a context lookup naming exception
	 */
	public ObstetricsOfficeVisitMySQL() throws DBException {
		ovLoader = new ObstetricsOfficeVisitSQLLoader();
		try {
			Context ctx = new InitialContext();
			this.ds = (BasicDataSource) ((DataSource) (((Context) ctx.lookup("java:comp/env"))).lookup("jdbc/itrust"));
		} catch (NamingException e) {
			throw new DBException(new SQLException("Context Lookup Naming Exception: " + e.getMessage()));
		}
		validator = new ObstetricsOfficeVisitValidator(this.ds);
	}
	
	/**
	 * Constructor for testing.
	 * 
	 * @param ds
	 *            DataSource
	 */
	public ObstetricsOfficeVisitMySQL(DataSource ds) {
		ovLoader = new ObstetricsOfficeVisitSQLLoader();
		this.ds = (BasicDataSource) ds;
		validator = new ObstetricsOfficeVisitValidator(this.ds);
	}
	
	@Override
	public List<ObstetricsOfficeVisit> getAll() throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		try {
			conn = ds.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM obstetricsofficevisit");
			results = pstring.executeQuery();
			List<ObstetricsOfficeVisit> visitList = ovLoader.loadList(results);
			//adds ultrasounds
			for(int i = 0; i < visitList.size(); i++){
				if(visitList.get(i).getUltrasoundString() != null && !(visitList.get(i).getUltrasoundString().isEmpty())){
					String[] ids = visitList.get(i).getUltrasoundString().split(",");
					//load ultrasounds into arraylist.
					ArrayList<Ultrasound> ultra = new ArrayList<Ultrasound>();
					for(int j = 0; j < ids.length; j++){
						ultra.add(getUltrasoundByID(j));
					}
					visitList.get(i).setUltrasounds(ultra);
				}
			}
			return visitList;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			try {
				if (results != null) {
					results.close();
				}
			} catch (SQLException e) {
				throw new DBException(e);
			} finally {
				DBUtil.closeConnection(conn, pstring);
			}
		}
	}

	@Override
	public ObstetricsOfficeVisit getByID(long id) throws DBException {
		ObstetricsOfficeVisit ret = null;
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		List<ObstetricsOfficeVisit> visitList = null;
		try {
			conn = ds.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM obstetricsofficevisit WHERE visitID=?");
			pstring.setLong(1, id);
			results = pstring.executeQuery();
			/* May update with loader instead */
			visitList = ovLoader.loadList(results);
			if (visitList.size() > 0) {
				ret = visitList.get(0);
			}
			if(ret.getUltrasoundString() != null && !(ret.getUltrasoundString().isEmpty())){
				String[] ids = ret.getUltrasoundString().split(",");
				//load ultrasounds into arraylist.
				ArrayList<Ultrasound> ultra = new ArrayList<Ultrasound>();
				for(int i = 0; i < ids.length; i++){
					ultra.add(getUltrasoundByID(Integer.parseInt(ids[i])));
				}
				ret.setUltrasounds(ultra);
			}
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			try {
				if (results != null) {
					results.close();
				}
			} catch (SQLException e) {
				throw new DBException(e);
			} finally {
				DBUtil.closeConnection(conn, pstring);
			}
		}
		return ret;
	}
	
	@Override
	public Ultrasound getUltrasoundByID(long id) throws DBException {
		Ultrasound ret = null;
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		List<Ultrasound> ultraList = null;
		try {
			conn = ds.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM ultrasound WHERE ultraID=?");
			pstring.setLong(1, id);
			results = pstring.executeQuery();
			/* May update with loader instead */
			ultraList = ovLoader.loadUltraList(results);
			if (ultraList.size() > 0) {
				ret = ultraList.get(0);
			}
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			try {
				if (results != null) {
					results.close();
				}
			} catch (SQLException e) {
				throw new DBException(e);
			} finally {
				DBUtil.closeConnection(conn, pstring);
			}
		}
		return ret;
	}

	@Override
	public boolean add(ObstetricsOfficeVisit obov) throws FormValidationException, DBException {
		return addReturnGeneratedId(obov) >= 0;
	}
	
	@Override
	public boolean update(ObstetricsOfficeVisit ov) throws DBException, FormValidationException {
		boolean retval = false;
		//ArrayList<Ultrasound> ultra = ov.getUltrasounds();
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			validator.validate(ov);
		} catch (FormValidationException e1) {
			throw new DBException(new SQLException(e1.getMessage()));
		}
		int results;
		try {
			conn = ds.getConnection();
			pstring = ovLoader.loadParameters(conn, pstring, ov, false);
			results = pstring.executeUpdate();
			retval = (results > 0);
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}

		
		return retval;
	}

	
	@Override
	public long addReturnGeneratedId(ObstetricsOfficeVisit ov) throws DBException {
		ArrayList<Ultrasound> us = ov.getUltrasounds();
		String idList = "";
		for(int i = 0; i < us.size(); i++){
			long id = addReturnGeneratedId(us.get(i));
			if(id > 0){
				idList += id + ",";
			}
		}
		ov.setUltrasoundString(idList);
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			validator.validate(ov);
		} catch (FormValidationException e1) {
			throw new DBException(new SQLException(e1.getMessage()));
		}
		long generatedId = -1;
		try {
			conn = ds.getConnection();
			pstring = ovLoader.loadParameters(conn, pstring, ov, true);
			int results = pstring.executeUpdate();
			if (results != 0) {
				ResultSet generatedKeys = pstring.getGeneratedKeys();
				if (generatedKeys.next()) {
					generatedId = generatedKeys.getLong(1);
				}
			}
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
		return generatedId;
	}
	
	@Override
	public long addReturnGeneratedId(Ultrasound us) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			validator.validate(us);
		} catch (FormValidationException e1) {
			throw new DBException(new SQLException(e1.getMessage()));
		}
		long generatedId = -1;
		try {
			conn = ds.getConnection();
			pstring = ovLoader.loadParameters(conn, pstring, us, true);
			int results = pstring.executeUpdate();
			if (results != 0) {
				ResultSet generatedKeys = pstring.getGeneratedKeys();
				if (generatedKeys.next()) {
					generatedId = generatedKeys.getLong(1);
				}
			}
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
		return generatedId;
	}

	@Override
	public List<ObstetricsOfficeVisit> getVisitsForPatient(Long patientID) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		if (ValidationFormat.NPMID.getRegex().matcher(Long.toString(patientID)).matches()) {
			try {
				conn = ds.getConnection();
				pstring = conn.prepareStatement("SELECT * FROM obstetricsofficevisit WHERE patientMID=?");
				pstring.setLong(1, patientID);
				results = pstring.executeQuery();
				List<ObstetricsOfficeVisit> visitList = ovLoader.loadList(results);
				for(int i = 0; i < visitList.size(); i++){
					if(visitList.get(i).getUltrasoundString() != null && !(visitList.get(i).getUltrasoundString().isEmpty())){
						String[] ids = visitList.get(i).getUltrasoundString().split(",");
						//load ultrasounds into arraylist.
						ArrayList<Ultrasound> ultra = new ArrayList<Ultrasound>();
						for(int j = 0; j < ids.length; j++){
							ultra.add(getUltrasoundByID(Long.parseLong(ids[j])));
						}
						visitList.get(i).setUltrasounds(ultra);
					}
				}
				return visitList;
			} catch (SQLException e) {
				throw new DBException(e);
			} finally {
				try {
					if (results != null) {
						results.close();
					}
				} catch (SQLException e) {
					throw new DBException(e);
				} finally {
					DBUtil.closeConnection(conn, pstring);
				}
			}
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Date getPatientDOB(final Long patientMID) {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		java.sql.Date patientDOB = null;
		try {
			conn = ds.getConnection();
			pstring = conn.prepareStatement("SELECT DateOfBirth FROM patients WHERE MID=?");
			pstring.setLong(1, patientMID);
			results = pstring.executeQuery();
			if (!results.next()) {
				return null;
			}
			patientDOB = results.getDate("DateOfBirth");
		} catch (SQLException e) {
			return null;
		} finally {
			try {
				if (results != null) {
					results.close();
				}
			} catch (SQLException e) {
				return null;
			} finally {
				DBUtil.closeConnection(conn, pstring);
			}
		}

		if (patientDOB == null) {
			return null;
		}

		return patientDOB;
	}
	
	public Date getPatientLMP(final Long patientMID) {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		Date patientLMP = null;
		try {
			conn = ds.getConnection();
			pstring = conn.prepareStatement("SELECT LMP FROM obstetricsInitialization WHERE patientMID=?");
			pstring.setLong(1, patientMID);
			results = pstring.executeQuery();
			if (!results.next()) {
				return null;
			}
			patientLMP = results.getDate("LMP");
			while (results.next()) {
				Date tmpLMP = results.getDate("LMP");
				if ( tmpLMP.after(patientLMP) ) {
					patientLMP = tmpLMP;
				}
			}
			
		} catch (SQLException e) {
			return null;
		} finally {
			try {
				if (results != null) {
					results.close();
				}
			} catch (SQLException e) {
				return null;
			} finally {
				DBUtil.closeConnection(conn, pstring);
			}
		}

		return patientLMP;
	}
	
	public void close() {
		try {
			ds.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
