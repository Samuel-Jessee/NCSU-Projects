package edu.ncsu.csc.itrust.model.childbirth;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.action.AddPatientAction;
import edu.ncsu.csc.itrust.action.ViewMyApptsAction;
import edu.ncsu.csc.itrust.controller.obstetricsInitialization.ObstetricsInitializationController;
import edu.ncsu.csc.itrust.controller.obstetricsOfficeVisit.ObstetricsOfficeVisitController;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.ValidationFormat;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.PreviousPregnancyInfo;
import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.ObstetricsOfficeVisit;
import edu.ncsu.csc.itrust.model.old.beans.ApptBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;

public class ChildbirthMySQL implements Serializable, ChildbirthData  {
	@Resource(name = "jbdc/itrust2")
	private static final long serialVersionUID = -672480913077894085L;
	private ChildbirthSQLLoader childLoader;
	private BasicDataSource ds;
	private ChildbirthValidator validator;
	private static DAOFactory factory;

	/**
	 * Default constructor for ObstetricsOfficeVisitMySQL.
	 * 
	 * @throws DBException
	 *             if there is a context lookup naming exception
	 */
	public ChildbirthMySQL() throws DBException {
		childLoader = new ChildbirthSQLLoader();
		factory = DAOFactory.getProductionInstance();
		try {
			Context ctx = new InitialContext();
			this.ds = (BasicDataSource) ((DataSource) (((Context) ctx.lookup("java:comp/env"))).lookup("jdbc/itrust"));
		} catch (NamingException e) {
			throw new DBException(new SQLException("Context Lookup Naming Exception: " + e.getMessage()));
		}
		validator = new ChildbirthValidator(this.ds);
	}
	
	/**
	 * Constructor for testing.
	 * 
	 * @param ds
	 *            DataSource
	 */
	public ChildbirthMySQL(DataSource ds) {
		childLoader = new ChildbirthSQLLoader();
		this.ds = (BasicDataSource) ds;
		validator = new ChildbirthValidator(this.ds);
	}
	
	@Override
	public List<Childbirth> getAll() throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		try {
			conn = ds.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM obstetricsofficevisit");
			results = pstring.executeQuery();
			List<Childbirth> visitList = childLoader.loadList(results);
			//adds ultrasounds
			for(int i = 0; i < visitList.size(); i++){
				if(visitList.get(i).getChildrenIDs() != null && !(visitList.get(i).getChildrenIDs().isEmpty())){
					String[] ids = visitList.get(i).getChildrenIDs().split(",");
					//load ultrasounds into arraylist.
					ArrayList<ChildInChildbirth> children = new ArrayList<ChildInChildbirth>();
					for(int j = 0; j < ids.length; j++){
						children.add(getChildrenByID(j));
					}
					visitList.get(i).setChildbirthchildren(children);
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
	public Childbirth getByID(long id) throws DBException {
		Childbirth ret = null;
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		List<Childbirth> visitList = null;
		try {
			conn = ds.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM childbirths WHERE visitID=?");
			pstring.setLong(1, id);
			results = pstring.executeQuery();
			/* May update with loader instead */
			visitList = childLoader.loadList(results);
			if (visitList.size() > 0) {
				ret = visitList.get(0);
			}
			if(ret.getChildrenIDs() != null && !(ret.getChildrenIDs().isEmpty())){
				String[] ids = ret.getChildrenIDs().split(",");
				//load chiild patients into arraylist.
				ArrayList<ChildInChildbirth> children = new ArrayList<ChildInChildbirth>();
				for(int i = 0; i < ids.length; i++){
					children.add(getChildrenByID(Integer.parseInt(ids[i])));
				}
				ret.setChildbirthchildren(children);
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
	
	public ChildInChildbirth getChildrenByID(long mid) throws DBException {
		ChildInChildbirth ret = null;
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		List<ChildInChildbirth> childList = null;
		try {
			conn = ds.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM childreninchildbirth WHERE childID=?");
			pstring.setLong(1, mid);
			results = pstring.executeQuery();
			/* May update with loader instead */
			childList = childLoader.loadChildrenList(results);
			if (childList.size() > 0) {
				ret = childList.get(0);
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
	public boolean add(Childbirth cb, long loggedInMID, long parentMID) throws FormValidationException, DBException {
		return addReturnGeneratedId(cb, loggedInMID, parentMID) >= 0;
	}
	
	@Override
	public boolean update(Childbirth cb) throws DBException, FormValidationException {
		boolean retval = false;
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			validator.validate(cb);
		} catch (FormValidationException e1) {
			throw new DBException(new SQLException(e1.getMessage()));
		}
		int results;
		try {
			conn = ds.getConnection();
			pstring = childLoader.loadParameters(conn, pstring, cb, false);
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
	public long addReturnGeneratedId(Childbirth cb, long loggedInMID, long parentMID) throws DBException {
		if(factory == null) {
			factory = DAOFactory.getProductionInstance();
		}
		AddPatientAction addpatient = new AddPatientAction(factory, loggedInMID);
		PatientDAO patientDAO = new PatientDAO(factory);
		PatientBean parent = patientDAO.getPatient(parentMID);
		ArrayList<ChildInChildbirth> patient = cb.getChildbirthchildren();
		String idList = "";
		for(int i = 0; i < patient.size(); i++){
			long mid = -1; long id = -1;
			PatientBean p = new PatientBean();
			p.setFirstName("Baby");
			p.setLastName(parent.getLastName());
			p.setEmail(parent.getEmail());
			p.setMotherMID(parentMID + "");
			p.setGenderStr(patient.get(i).getGender());
			try {
				mid = addpatient.addPatient(p, loggedInMID);
			} catch (FormValidationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ITrustException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(mid > 0){
				patient.get(i).setMid(mid);
			}
			id = addReturnGeneratedId(patient.get(i));
			if(id > 0){
				idList += id + ",";
			}
		}
		cb.setChildrenIDs(idList);
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			validator.validate(cb);
		} catch (FormValidationException e1) {
			throw new DBException(new SQLException(e1.getMessage()));
		}
		long generatedId = -1;
		try {
			conn = ds.getConnection();
			pstring = childLoader.loadParameters(conn, pstring, cb, true);
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
		addPreviousPreg(cb, parentMID);
		return generatedId;
	}
	
	public void addPreviousPreg(Childbirth cb, long parentMID) throws DBException{
		PreviousPregnancyInfo prevInfo = new PreviousPregnancyInfo();
		prevInfo.setPatientMID(parentMID);
		prevInfo.setYear(cb.getDate().getYear());
		int value = 1; String method = cb.getMethod();
		if(method.equals("Vaginal Delivery Vacuum Assist")) value = 2;
		if(method.equals("Vaginal Delivery Forceps Assist")) value = 3;
		if(method.equals("Caesarean Section")) value = 4;
		if(method.equals("Miscarriage")) value = 5;
		prevInfo.setDeliveryType(value);
		prevInfo.setHoursInLabor(cb.getHoursInLabor());
		prevInfo.setAmtChildren(cb.getChildbirthchildren().size());
		ObstetricsOfficeVisitController obov = new ObstetricsOfficeVisitController();
		List<ObstetricsOfficeVisit> list = obov.getObstetricsVisitForPatient(parentMID + "");
		if (list != null && !list.isEmpty()) {
			double weightgained = 0;
			if(list.size() > 1){
				weightgained = list.get(list.size()).getWeight() - list.get(0).getWeight();
			}
			if(weightgained < 0) weightgained = 0;
			prevInfo.setWeightGained((int) weightgained);
			prevInfo.setWeeksPreg(list.get(0).getNumWeeks());
		} else {
			prevInfo.setWeightGained(0);
			prevInfo.setWeeksPreg(0);
		}
		ObstetricsInitializationController oi = new ObstetricsInitializationController();
		oi.add(prevInfo);
	}
	
	public long addReturnGeneratedId(ChildInChildbirth cb) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			validator.validate(cb);
		} catch (FormValidationException e1) {
			throw new DBException(new SQLException(e1.getMessage()));
		}
		long generatedId = -1;
		try {
			conn = ds.getConnection();
			pstring = childLoader.loadParameters(conn, pstring, cb, true);
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
	public List<Childbirth> getChildbirthsForPatient(Long patientMID) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		if (ValidationFormat.NPMID.getRegex().matcher(Long.toString(patientMID)).matches()) {
			try {
				conn = ds.getConnection();
				pstring = conn.prepareStatement("SELECT * FROM childbirths WHERE patientMID=?");
				pstring.setLong(1, patientMID);
				results = pstring.executeQuery();
				List<Childbirth> visitList = childLoader.loadList(results);
				for(int i = 0; i < visitList.size(); i++){
					if(visitList.get(i).getChildrenIDs() != null && !(visitList.get(i).getChildrenIDs().isEmpty())){
						String[] ids = visitList.get(i).getChildrenIDs().split(",");
						//load ultrasounds into arraylist.
						ArrayList<ChildInChildbirth> children = new ArrayList<ChildInChildbirth>();
						for(int j = 0; j < ids.length; j++){
							children.add(getChildrenByID(Long.parseLong(ids[j])));
						}
						visitList.get(i).setChildbirthchildren(children);
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

	@Override
	public boolean add(Childbirth addObj) throws FormValidationException, DBException {
		throw new IllegalStateException("should not use");
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
