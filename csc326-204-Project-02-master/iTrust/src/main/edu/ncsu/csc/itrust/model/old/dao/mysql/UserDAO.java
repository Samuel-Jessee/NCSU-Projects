/**
 * 
 */
package edu.ncsu.csc.itrust.model.old.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;

/**
 * Abstract parent class for user DAO classes. Implements common methods.
 * 
 * @author Samuel Jessee
 *
 */
public abstract class UserDAO {

	/**
	 * Returns the name for a given MID
	 * 
	 * @param mid
	 *            the MID of the user
	 * @param factory
	 *            factory to connect with database
	 * @param role
	 *            the role of the user in the system
	 * @return a String representing the name of the user
	 * @throws ITrustException
	 * @throws DBException
	 */
	public String getName(long mid, DAOFactory factory, String role) throws ITrustException, DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement stmt = conn
						.prepareStatement("SELECT firstName, lastName FROM " + role + " WHERE MID=?");) {
			stmt.setLong(1, mid);
			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				String result = results.getString("firstName") + " " + results.getString("lastName");
				results.close();
				return result;
			} else {
				results.close();
				throw new ITrustException("User does not exist");
			}
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

}
