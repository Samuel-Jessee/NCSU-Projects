/**
 * 
 */
package edu.ncsu.csc.itrust.model.healthTracking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
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
 * @author smmahaff
 *
 */
@ManagedBean
public class HealthTrackerMySQL implements HealthTrackerData {
	@Resource(name = "jdbc/itrust2")
	private HealthTrackerSQLLoader htLoader;
	private BasicDataSource ds;
	private HealthTrackerValidator validator;

	/**
	 * Default constructor for OfficeVisitMySQL.
	 * 
	 * @throws DBException if there is a context lookup naming exception
	 */
	public HealthTrackerMySQL() throws DBException {
		htLoader = new HealthTrackerSQLLoader();
		try {
			Context ctx = new InitialContext();
			this.ds = (BasicDataSource) ((DataSource) (((Context) ctx.lookup("java:comp/env"))).lookup("jdbc/itrust"));
		} catch (NamingException e) {
			throw new DBException(new SQLException("Context Lookup Naming Exception: " + e.getMessage()));
		}
		validator = new HealthTrackerValidator(this.ds);
	}

	/**
	 * Constructor for testing.
	 * 
	 * @param ds
	 */
	public HealthTrackerMySQL(DataSource ds) {
		htLoader = new HealthTrackerSQLLoader();
		this.ds = (BasicDataSource) ds;
		validator = new HealthTrackerValidator(this.ds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthTracker> getDataForPatient(Long patientID) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		if (ValidationFormat.NPMID.getRegex().matcher(Long.toString(patientID)).matches()) {
			try {
				conn = ds.getConnection();
				pstring = conn.prepareStatement("SELECT * FROM fitnessinfo WHERE Patient=?");
				pstring.setLong(1, patientID);
				results = pstring.executeQuery();
				final List<HealthTracker> visitList = htLoader.loadList(results);
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
	 * If health tracking info for the given patient, hcp, and date already exists, returns the ID
	 * of the info.  Otherwise return null.
	 * @param patientID
	 * @param date
	 * @return
	 * @throws DBException
	 */
	public Long dateExists(Long patientID, Long hcpID, Date date) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		try {
			conn = ds.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM fitnessinfo WHERE Patient=? AND HCP=? AND Date=?");
			pstring.setLong(1, patientID);
			pstring.setLong(2, hcpID);
			pstring.setDate(3, new java.sql.Date(date.getTime()));
			results = pstring.executeQuery();
			List<HealthTracker> htInfoList = htLoader.loadList(results);
			if (htInfoList.isEmpty()) {
				return null;
			} else {
				return htInfoList.get(0).getTrackerID();
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
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean add(HealthTracker ht) throws DBException {
		return addReturnGeneratedId(ht) >= 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public long addReturnGeneratedId(HealthTracker ht) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			validator.validate(ht);
		} catch (FormValidationException e1) {
			throw new DBException(new SQLException(e1.getMessage()));
		}
		long generatedId = -1;
		try {
			conn = ds.getConnection();
			pstring = htLoader.loadParameters(conn, pstring, ht, true);
			int results = pstring.executeUpdate();
			if (results != 0) {
				ResultSet generatedKeys = pstring.getGeneratedKeys();
				if(generatedKeys.next()) {
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthTracker> getAll() throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		try {
			conn = ds.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM fitnessinfo");
			results = pstring.executeQuery();
			final List<HealthTracker> visitList = htLoader.loadList(results);
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthTracker getByID(long id) throws DBException {
		HealthTracker ret = null;
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		List<HealthTracker> visitList = null;
		try {
			conn = ds.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM fitnessinfo WHERE ID=?");

			pstring.setLong(1, id);

			results = pstring.executeQuery();

			/* May update with loader instead */
			visitList = htLoader.loadList(results);
			if (visitList.size() > 0) {
				ret = visitList.get(0);
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean update(HealthTracker ht) throws DBException {
		boolean retval = false;
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			validator.validate(ht);
		} catch (FormValidationException e1) {
			throw new DBException(new SQLException(e1.getMessage()));
			
		}
		int results;

		try {
			conn = ds.getConnection();
			pstring = htLoader.loadParameters(conn, pstring, ht, false);
			
			results = pstring.executeUpdate();
			retval = (results > 0);
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
		return retval;
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
