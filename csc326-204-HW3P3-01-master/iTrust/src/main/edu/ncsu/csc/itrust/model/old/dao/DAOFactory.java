package edu.ncsu.csc.itrust.model.old.dao;

import java.sql.Connection;
import java.sql.SQLException;

import edu.ncsu.csc.itrust.model.old.dao.mysql.AccessDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AllergyDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ApptDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ApptRequestDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ApptTypeDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.BillingDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.DrugInteractionDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.DrugReactionOverrideCodesDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.FakeEmailDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.FamilyDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.HospitalsDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.MessageDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.NDCodesDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.RemoteMonitoringDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ReportRequestDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ReviewsDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ZipCodeDAO;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * The central mediator for all Database Access Objects. The production instance
 * uses the database connection pool provided by Tomcat (so use the production
 * instance when doing stuff from JSPs in the "real code"). Both the production
 * and the test instance parses the context.xml file to get the JDBC connection.
 * 
 * Also, @see {@link EvilDAOFactory} and @see {@link TestDAOFactory}.
 * 
 * Any DAO that is added to the system should be added in this class, in the
 * same way that all other DAOs are.
 * 
 * 
 * 
 */
public class DAOFactory {
	private static DAOFactory productionInstance = null;
	/**
	 * 
	 * @return A production instance of the DAOFactory, to be used in deployment
	 *         (by Tomcat).
	 */
	public static DAOFactory getProductionInstance() {
		if (productionInstance == null) {
			productionInstance = new DAOFactory();
		}
		return productionInstance;
	}

	private IConnectionDriver driver;

	/**
	 * Protected constructor. Call getProductionInstance to get an instance of
	 * the DAOFactory
	 */
	protected DAOFactory() {
		this.driver = new ProductionConnectionDriver();
	}

	/**
	 * 
	 * @return this DAOFactory's AccessDAO
	 */
	public AccessDAO getAccessDAO() {
		return new AccessDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's AllergyDAO
	 */
	public AllergyDAO getAllergyDAO() {
		return new AllergyDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's ApptDAO
	 */
	public ApptDAO getApptDAO() {
		return new ApptDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's ApptRequestDAO
	 */
	public ApptRequestDAO getApptRequestDAO() {
		return new ApptRequestDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's ApptTypeDAO
	 */
	public ApptTypeDAO getApptTypeDAO() {
		return new ApptTypeDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's AuthDAO
	 */
	public AuthDAO getAuthDAO() {
		return new AuthDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's BillingDAO
	 */
	public BillingDAO getBillingDAO() {
		return new BillingDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's Connection
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		return driver.getConnection();
	}

	/**
	 * 
	 * @return this DAOFactory's DrugInteractionDAO
	 */
	public DrugInteractionDAO getDrugInteractionDAO() {
		return new DrugInteractionDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's FakeEmailDAO
	 */
	public FakeEmailDAO getFakeEmailDAO() {
		return new FakeEmailDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's FamilyDAO
	 */
	public FamilyDAO getFamilyDAO() {
		return new FamilyDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's HospitalsDAO
	 */
	public HospitalsDAO getHospitalsDAO() {
		return new HospitalsDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's MessageDAO
	 */
	public MessageDAO getMessageDAO() {
		return new MessageDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's OfficeVisitDAO
	 */
	// public OfficeVisitDAO getOfficeVisitDAO() {
	// return new OfficeVisitDAO(this);
	// }

	/**
	 * 
	 * @return this DAOFactory's NDCodesDAO
	 */
	public NDCodesDAO getNDCodesDAO() {
		return new NDCodesDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's DrugReactionOverrideCodesDAO
	 */
	public DrugReactionOverrideCodesDAO getORCodesDAO() {
		return new DrugReactionOverrideCodesDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's PatientDAO
	 */
	public PatientDAO getPatientDAO() {
		return new PatientDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's PersonnelDAO
	 */
	public PersonnelDAO getPersonnelDAO() {
		return new PersonnelDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's RemoteMonitoringDAO
	 */
	public RemoteMonitoringDAO getRemoteMonitoringDAO() {
		return new RemoteMonitoringDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's ReportRequestDAO
	 */
	public ReportRequestDAO getReportRequestDAO() {
		return new ReportRequestDAO(this);
	}

	/**
	 * Gets the DAO for reviews with the DB table reviews.
	 * 
	 * @return this DAOFactory's ReviewsDAO
	 */
	public ReviewsDAO getReviewsDAO() {
		return new ReviewsDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's TransactionDAO
	 */
	public TransactionDAO getTransactionDAO() {
		return new TransactionDAO(this);
	}

	/**
	 *
	 * @return this DAOFactory's ZipCodeDAO
	 */
	public ZipCodeDAO getZipCodeDAO() {
		return new ZipCodeDAO(this);
	}
}
