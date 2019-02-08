package edu.ncsu.csc216.flix_2.customer;

/**
 * A database of Customers that provides the list operations required to support
 * the single-customer operations described in CustomerAccountManager. The upper
 * limit of the number of customers in the database is 20.
 * 
 * @author MatthewLilly
 * @author SamuelJessee
 * 
 */
public class CustomerDB {
	/** The maximum number of customers the system can support **/
	public static final int MAX_SIZE = 20;

	/** The number of customers currently in the system **/
	private int size;

	/** The database of customers currently in the system **/
	private Customer[] list;

	/**
	 * Constructs the customer database
	 */
	public CustomerDB() {
		size = 0;
		list = new Customer[MAX_SIZE];
	}

	/**
	 * Returns the customer in the list to match the provided id and pw
	 * 
	 * @param id
	 *            the id of the customer to verify
	 * @param pw
	 *            the password of the customer to verify
	 * @return the customer to match the provided id and pw
	 * @throws IllegalArgumentException
	 *             if id/pw are null, pw is wrong, or customer doesn't exist
	 */
	public Customer verifyCustomer(String id, String pw) {
		if (id == null || pw == null) {
			throw new IllegalArgumentException("The account doesn't exist.");
		}

		int n = findMatchingAccount(id);

		if (n == -1) {
			throw new IllegalArgumentException("The account doesn't exist.");
		} else {
			Customer temp = list[n];
			if (temp.verifyPassword(pw)) {
				return temp;
			} else {
				throw new IllegalArgumentException("The account doesn't exist.");
			}
		}
	}

	/**
	 * Used only for testing. Returns a string of ids of customers in the list
	 * in the order the customers appear in the list. Successive ids are
	 * separated by newlines
	 * 
	 * @return the string representing the customers in the list
	 */
	public String listAccounts() {
		String s = "";

		for (int i = 0; i < size; i++) {
			s += list[i].getId() + "\n";
		}
		return s;
	}

	/**
	 * Add a new customer to the list
	 * 
	 * @param id
	 *            customer username
	 * @param pw
	 *            customer password
	 * @param maxHome
	 *            max movies allowed at home
	 * @throws IllegalArgumentException
	 *             if id/pw has whitespace or are empty, or if customer with
	 *             same id already exists
	 * @throws IllegalStateException
	 *             if database is full
	 */
	public void addNewCustomer(String id, String pw, int maxHome) {
		if (id.equals("") || pw.equals("") || id.contains(" ") || pw.contains(" ")) {
			throw new IllegalArgumentException("Username and password must have"
			      + " non-whitespace characters.");
		}

		Customer temp = new Customer(id, pw, maxHome);

		if (!isNewCustomer(temp.getId())) {
			throw new IllegalArgumentException("Customer already has an account.");
		} else if (size == MAX_SIZE) {
			throw new IllegalStateException();
		} else {
			insert(temp);
		}
	}

	/**
	 * Removes the customer with the given name from the list and returns the
	 * movies they have at home
	 * 
	 * @param id
	 *            the id of the customer to be deleted
	 * @throws IllegalArgumentException
	 *             if the customer doesn't exist
	 */
	public void cancelAccount(String id) {
		int n = findMatchingAccount(id);

		if (n == -1) {
			throw new IllegalArgumentException();
		} else {
			list[n].closeAccount();
			for (int i = n; i < size - 1; i++) {
				list[i] = list[i + 1];
			}
			size--;
		}
	}

	/**
	 * Iterates through the customer list and checks for a customer with the
	 * same id. Returns true if the new id is not being used.
	 * 
	 * @param id
	 *            new id
	 * @return true if id is not already a customer
	 */
	private boolean isNewCustomer(String id) {
		return findMatchingAccount(id) == -1;
	}

	/**
	 * Inserts the new customer into the list in alphabetical order.
	 * 
	 * @param c
	 *            customer to add
	 */
	private void insert(Customer c) {
		if (size == 0) {
			list[0] = c;
			size++;
		} else {
			int index = 0;
			String temp1 = c.getId().toLowerCase();
			String temp2;
			for (int i = 0; i < size; i++) {
				temp2 = list[i].getId().toLowerCase();
				int n = compare(temp1, temp2);
				if (n < 0) {
					index = i;
					break;
				} else if (n == 0) {
					throw new IllegalArgumentException();
				} else if (n > 0 && i == size - 1) {
					index = size;
				}
			}

			add(c, index);
		}
	}

	/**
	 * Shifts the customer array to the right and puts the new customer in its
	 * spot
	 * 
	 * @param c
	 *            customer to insert
	 * @param index
	 *            place to insert at
	 */
	private void add(Customer c, int index) {
		if (index == size) {
			list[size] = c;
			size++;
		} else {
			for (int i = size; i > index; i--) {
				list[i] = list[i - 1];
			}
			list[index] = c;
			size++;
		}
	}

	/**
	 * Compares c to a customer in the list and determines which comes first in
	 * alphabetical order
	 * 
	 * @param temp1
	 *            customer to be inserted
	 * @param temp2
	 *            customer in the list being compared to
	 * @return negative number if c should come before the customer already in
	 *         the list
	 */
	private int compare(String temp1, String temp2) {
		int n = 0;
		for (int i = 0; i < temp1.length() && i < temp2.length(); i++) {
			n = temp1.charAt(i) - temp2.charAt(i);
			if (n != 0) {
				return n;
			}
		}
		if (temp1.length() > temp2.length()) {
			n = 1;
		} else if (temp1.length() < temp2.length()) {
			n = -1;
		}
		return n;
	}

	/**
	 * Iterates through customer list and returns the index of the customer with
	 * the same id, or -1 if does not exist.
	 * 
	 * @param id
	 *            customer id to find
	 * @return index of id if found, or -1 if not
	 */
	private int findMatchingAccount(String id) {
		for (int i = 0; i < size; i++) {
			if (list[i].getId().equalsIgnoreCase(id)) {
				return i;
			}
		}
		return -1;
	}

}