package edu.ncsu.csc.itrust.model.fitness;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.ValidationFormat;

/**
 * This class is the main control panel for retrieving fitness tracking data
 * from the SQL table.
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
@ManagedBean
public class FitnessMySQL implements Serializable, FitnessData {
	private static final long serialVersionUID = 5606358386260654998L;
	@Resource(name = "jdbc/itrust2")
	private FitnessSQLLoader fitnessLoader;
	private DataSource ds;
	private FitnessValidator validator;

	/**
	 * Default constructor for FitnessMySQL.
	 * 
	 * @throws DBException
	 */
	public FitnessMySQL() throws DBException {
		fitnessLoader = new FitnessSQLLoader();
		try {
			Context ctx = new InitialContext();
			this.ds = ((DataSource) (((Context) ctx.lookup("java:comp/env"))).lookup("jdbc/itrust"));
		} catch (NamingException e) {
			throw new DBException(new SQLException("Context Lookup Naming Exception: " + e.getMessage()));
		}
		validator = new FitnessValidator(this.ds);
	}

	/**
	 * Constructor for testing.
	 * 
	 * @param ds
	 */
	public FitnessMySQL(DataSource ds) {
		fitnessLoader = new FitnessSQLLoader();
		this.ds = ds;
		validator = new FitnessValidator(this.ds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean add(Fitness fitness) throws DBException {
		return addReturnGeneratedID(fitness) >= 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long addReturnGeneratedID(Fitness fitness) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			validator.validate(fitness);
		} catch (FormValidationException e1) {
			throw new DBException(new SQLException(e1.getMessage()));
		}
		long generatedId = -1;
		try {
			conn = ds.getConnection();
			pstring = fitnessLoader.loadParameters(conn, pstring, fitness, true);
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
	public List<Fitness> getAll() throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		try {
			conn = ds.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM fitness");
			results = pstring.executeQuery();
			final List<Fitness> fitnessList = fitnessLoader.loadList(results);
			return fitnessList;
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
	public Fitness getByID(long id) throws DBException {
		Fitness ret = null;
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		List<Fitness> fitnessList = null;
		try {
			conn = ds.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM fitness WHERE fitness_id=?");

			pstring.setLong(1, id);

			results = pstring.executeQuery();

			/* May update with loader instead */
			fitnessList = fitnessLoader.loadList(results);
			if (fitnessList.size() > 0) {
				ret = fitnessList.get(0);
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
	public Fitness getFitnessByDate(Long patientID, LocalDate date) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet result = null;
		if (ValidationFormat.NPMID.getRegex().matcher(Long.toString(patientID)).matches()) {
			try {
				conn = ds.getConnection();
				pstring = conn.prepareStatement("SELECT * FROM fitness WHERE patient_id=? AND fitness_date=?");

				pstring.setLong(1, patientID);

				pstring.setDate(2, Date.valueOf(date));

				result = pstring.executeQuery();

				final Fitness fitness = fitnessLoader.loadSingle(result);
				return fitness;
			} catch (SQLException e) {
				throw new DBException(e);
			} finally {
				try {
					if (result != null) {
						result.close();
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
	public List<Fitness> getFitnessDateRange(Long patientID, LocalDate startDate, LocalDate endDate)
			throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		if (ValidationFormat.NPMID.getRegex().matcher(Long.toString(patientID)).matches()) {
			try {
				conn = ds.getConnection();
				pstring = conn
						.prepareStatement("SELECT * FROM fitness WHERE patient_id=? AND fitness_date BETWEEN ? AND ?");

				pstring.setLong(1, patientID);

				pstring.setDate(2, Date.valueOf(startDate));

				pstring.setDate(3, Date.valueOf(endDate));

				results = pstring.executeQuery();

				final List<Fitness> fitnessList = fitnessLoader.loadList(results);
				return fitnessList;
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
	public List<Fitness> getFitnessForPatient(Long patientID) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		if (ValidationFormat.NPMID.getRegex().matcher(Long.toString(patientID)).matches()) {
			try {
				conn = ds.getConnection();
				pstring = conn.prepareStatement("SELECT * FROM fitness WHERE patient_id=?");

				pstring.setLong(1, patientID);

				results = pstring.executeQuery();

				final List<Fitness> fitnessList = fitnessLoader.loadList(results);
				return fitnessList;
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
	public LocalDate getPatientDOB(final Long patientID) {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		java.sql.Date patientDOB = null;
		try {
			conn = ds.getConnection();
			pstring = conn.prepareStatement("SELECT DateOfBirth FROM patients WHERE MID=?");
			pstring.setLong(1, patientID);
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

		return patientDOB.toLocalDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean update(Fitness fitness) throws DBException {
		boolean retval = false;
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			validator.validate(fitness);
		} catch (FormValidationException e1) {
			throw new DBException(new SQLException(e1.getMessage()));
		}
		int results;

		try {
			conn = ds.getConnection();
			pstring = fitnessLoader.loadParameters(conn, pstring, fitness, false);
			results = pstring.executeUpdate();
			retval = (results > 0);
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
		return retval;
	}
}
