package edu.ncsu.csc216.flix_2.customer;

import edu.ncsu.csc216.flix_2.rental_system.RentalManager;

/**
 * The system that manages both customers and movies in the Movie Rental system.
 * Represents the customer part of the overall system.
 * 
 * @author MatthewLilly
 * @author SamuelJessee
 * 
 */
public class MovieCustomerAccountSystem implements CustomerAccountManager {
	/** A boolean flag for if the admin is logged in **/
	private boolean adminLoggedIn;

	/** A boolean flag for if a customer is logged in **/
	private boolean customerLoggedIn;

	/** Database of customers in the system **/
	private CustomerDB customerList;

	/** the rental inventory associated with the system **/
	private RentalManager inventorySystem;

	/** The admin username and password **/
	private static final String ADMIN = "admin";

	/**
	 * Constructs the MovieCustomerAccountSystem
	 * 
	 * @param manager
	 *            the rental manager to be included with the system
	 */
	public MovieCustomerAccountSystem(RentalManager manager) {
		inventorySystem = manager;
		adminLoggedIn = false;
		customerLoggedIn = false;
		customerList = new CustomerDB();
	}

	/**
	 * Logs a user into the system.
	 * 
	 * @param username
	 *            id/username of the user
	 * @param password
	 *            user's password
	 * @throws IllegalStateException
	 *             if a customer or the administrator is already logged in
	 * @throws IllegalArgumentException
	 *             if the customer account does not exist
	 */
	@Override
	public void login(String username, String password) {
		if (isCustomerLoggedIn() || isAdminLoggedIn()) {
			throw new IllegalStateException("Current customer or admin must first log out.");
		} else if (username.equals(ADMIN) && password.equals(ADMIN)) {
			adminLoggedIn = true;
		} else {
			Customer temp = customerList.verifyCustomer(username, password);
			inventorySystem.setCustomer(temp);
			customerLoggedIn = true;
		}
	}

	/**
	 * Logs the current customer or administrator out of the system.
	 */
	@Override
	public void logout() {
		inventorySystem.setCustomer(null);
		adminLoggedIn = false;
		customerLoggedIn = false;

	}

	/**
	 * Is an administrator logged into the system?
	 * 
	 * @return true if yes, false if no
	 */
	@Override
	public boolean isAdminLoggedIn() {
		return adminLoggedIn;
	}

	/**
	 * Is a customer logged into the system?
	 * 
	 * @return true if yes, false if no
	 */
	@Override
	public boolean isCustomerLoggedIn() {
		return customerLoggedIn;
	}

	/**
	 * Add a new customer to the customer database. The administrator must be
	 * logged in.
	 * 
	 * @param id
	 *            id/email for new customer
	 * @param password
	 *            new customer's password
	 * @param num
	 *            number associated with this customer
	 * @throws IllegalStateException
	 *             if the database is full or the administrator is not logged in
	 * @throws IllegalArgumentException
	 *             if customer with given id is already in the database
	 */
	@Override
	public void addNewCustomer(String id, String password, int num) {
		if (isAdminLoggedIn()) {
			customerList.addNewCustomer(id, password, num);
		} else {
			throw new IllegalStateException("Access denied.");
		}
	}

	/**
	 * Cancel a customer account.
	 * 
	 * @param id
	 *            id/username of the customer to cancel
	 * @throws IllegalStateException
	 *             if the administrator is not logged in
	 * @throws IllegalArgumentException
	 *             if no matching account is found
	 */
	@Override
	public void cancelAccount(String id) {
		if (isAdminLoggedIn()) {
			customerList.cancelAccount(id);
		} else {
			throw new IllegalStateException("Access denied.");
		}
	}

	/**
	 * List all customer accounts.
	 * 
	 * @return string of customer usernames separated by newlines
	 */
	@Override
	public String listAccounts() {
		String s = customerList.listAccounts();
		return s;
	}

}