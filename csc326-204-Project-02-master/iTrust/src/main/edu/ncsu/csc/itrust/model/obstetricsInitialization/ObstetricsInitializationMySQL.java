/**
 * 
 */
package edu.ncsu.csc.itrust.model.obstetricsInitialization;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * Class that interacts directly with the obstetricsInitialization and
 * previousPregnancyInfo databases.
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
@ManagedBean
public class ObstetricsInitializationMySQL implements Serializable, ObstetricsInitializationData {
	@Resource(name = "jdbc/itrust2")
	private static final long serialVersionUID = -672480913077894085L;
	private ObstetricsInitializationSQLLoader oiLoader;
	private BasicDataSource ds;
	private ObstetricsInitializationValidator validator;

	/**
	 * Default constructor for OfficeVisitMySQL.
	 * 
	 * @throws DBException
	 *             if there is a context lookup naming exception
	 */
	public ObstetricsInitializationMySQL() throws DBException {
		oiLoader = new ObstetricsInitializationSQLLoader();
		try {
			Context ctx = new InitialContext();
			this.ds = (BasicDataSource) ((DataSource) (((Context) ctx.lookup("java:comp/env"))).lookup("jdbc/itrust"));
		} catch (NamingException e) {
			throw new DBException(new SQLException("Context Lookup Naming Exception: " + e.getMessage()));
		}
		validator = new ObstetricsInitializationValidator(this.ds);
	}

	/**
	 * Constructor for testing.
	 * 
	 * @param ds
	 *            DataSource
	 */
	public ObstetricsInitializationMySQL(DataSource ds) {
		oiLoader = new ObstetricsInitializationSQLLoader();
		this.ds = (BasicDataSource) ds;
		validator = new ObstetricsInitializationValidator(this.ds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean add(ObstetricsInitialization oi) throws FormValidationException, DBException {
		return addReturnGeneratedId(oi) >= 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean add(PreviousPregnancyInfo pi) throws FormValidationException, DBException {
		return addReturnGeneratedId(pi) >= 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long addReturnGeneratedId(ObstetricsInitialization oi) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			validator.validate(oi);
		} catch (FormValidationException e1) {
			throw new DBException(new SQLException(e1.getMessage()));
		}
		long generatedId = -1;
		try {
			conn = ds.getConnection();
			pstring = oiLoader.loadParameters(conn, pstring, oi, true);
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long addReturnGeneratedId(PreviousPregnancyInfo pi) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			validator.validate(pi);
		} catch (FormValidationException e1) {
			throw new DBException(new SQLException(e1.getMessage()));
		}
		long generatedId = -1;
		try {
			conn = ds.getConnection();
			pstring = oiLoader.loadParameters(conn, pstring, pi, true);
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ObstetricsInitialization> getAll() throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		try {
			conn = ds.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM obstetricsInitialization");
			results = pstring.executeQuery();
			final List<ObstetricsInitialization> visitList = oiLoader.loadList(results);
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
	public ObstetricsInitialization getByID(long id) throws DBException {
		ObstetricsInitialization ret = null;
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		List<ObstetricsInitialization> visitList = null;
		try {
			conn = ds.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM obstetricsInitialization WHERE visitID=?");
			pstring.setLong(1, id);
			results = pstring.executeQuery();
			/* May update with loader instead */
			visitList = oiLoader.loadList(results);
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
	public List<PreviousPregnancyInfo> getPrevPregForPatient(Long patientID) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		if (ValidationFormat.NPMID.getRegex().matcher(Long.toString(patientID)).matches()) {
			try {
				conn = ds.getConnection();
				pstring = conn.prepareStatement("SELECT * FROM previousPregnancyInfo WHERE patientMID=?");
				pstring.setLong(1, patientID);
				results = pstring.executeQuery();
				final List<PreviousPregnancyInfo> pregList = oiLoader.loadPregList(results);
				return pregList;
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
	@Override
	public List<ObstetricsInitialization> getVisitsForPatient(Long patientID) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		if (ValidationFormat.NPMID.getRegex().matcher(Long.toString(patientID)).matches()) {
			try {
				conn = ds.getConnection();
				pstring = conn.prepareStatement("SELECT * FROM obstetricsInitialization WHERE patientMID=?");
				pstring.setLong(1, patientID);
				results = pstring.executeQuery();
				final List<ObstetricsInitialization> visitList = oiLoader.loadList(results);
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
	@Override
	public boolean update(ObstetricsInitialization oi) throws DBException, FormValidationException {
		boolean retval = false;
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			validator.validate(oi);
		} catch (FormValidationException e1) {
			throw new DBException(new SQLException(e1.getMessage()));
		}
		int results;
		try {
			conn = ds.getConnection();
			pstring = oiLoader.loadParameters(conn, pstring, oi, false);
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
	public void close() {
		try {
			ds.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
