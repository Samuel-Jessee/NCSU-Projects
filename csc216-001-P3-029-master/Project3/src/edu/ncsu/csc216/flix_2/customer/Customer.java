package edu.ncsu.csc216.flix_2.customer;

import edu.ncsu.csc216.flix_2.inventory.Movie;
import edu.ncsu.csc216.flix_2.list_util.MultiPurposeList;

/**
 * A customer for the Movie Rental system
 * 
 * @author MatthewLilly
 * @author SamuelJessee
 * 
 */
public class Customer {
	/** The customer's username **/
	private String id;

	/** The customer's password **/
	private String password;

	/** The maximum allowable number of rental movie DVDs at home **/
	private int maxAtHome;

	/** The number of rental movie DVDs currently at home **/
	private int nowAtHome;

	/** Movies that the customer now has at home. Adds to end of list **/
	private MultiPurposeList<Movie> atHomeQueue;

	/** Movies in the customer's reserve queue. Adds to end of list **/
	private MultiPurposeList<Movie> reserveQueue;

	/**
	 * The constructor for a customer. If maxHome is negative, it is set to 0
	 * 
	 * @param id
	 *            the customer's id
	 * @param pw
	 *            the customer's password
	 * @param maxHome
	 *            the max number of movies allowed at home
	 * @throws IllegalArgumentException
	 *             if the first two arguments are null, or of length 0 after
	 *             trimming
	 */
	public Customer(String id, String pw, int maxHome) {
		if (id == null || pw == null) {
			throw new IllegalArgumentException();
		}

		if (id.trim().equals("") || pw.trim().equals("")) {
			throw new IllegalArgumentException();
		}

		this.id = id.trim().toLowerCase();
		this.password = pw.trim();

		if (maxHome < 0) {
			maxAtHome = 0;
		} else {
			maxAtHome = maxHome;
		}
		atHomeQueue = new MultiPurposeList<Movie>();
		reserveQueue = new MultiPurposeList<Movie>();
	}

	/**
	 * Returns true if the provided password matches this users password
	 * 
	 * @param pw
	 *            the password to be checked
	 * @return true if the passwords match
	 */
	public boolean verifyPassword(String pw) {
		if (pw != null) {
			pw = pw.trim();
		} else {
			throw new IllegalArgumentException();
		}

		return password.trim().equals(pw.trim());
	}

	/**
	 * Returns the users id
	 * 
	 * @return the id of the user
	 */
	public String getId() {
		return id;
	}

	/**
	 * Compares two customers lexicographically and ignores case for list
	 * ordering
	 * 
	 * @param c
	 *            the customer to be compared to
	 * @return <0 if other customer comes first, 0 if same customer, >0 if this
	 *         one first
	 */
	public int compareToByName(Customer c) {
		String c1 = this.getId().toLowerCase();
		String c2 = c.getId().toLowerCase();

		int n;
		for (int i = 0; i < c1.length() && i < c2.length(); i++) {
			n = c1.charAt(i) - c2.charAt(i);
			if (n != 0) {
				return n;
			}
		}
		if (c1.length() > c2.length()) {
			return 1;
		} else if (c1.length() < c2.length()) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * Adjusts the both of the customer's queues when the customer logs in to be
	 * up to date with the current state of the inventory
	 */
	public void login() {
		checkOut();
	}

	/**
	 * Returns a string of movies in the reserve queue in order. Successive
	 * movies are separated by newlines
	 * 
	 * @return a string of movies representing the reserve queue
	 */
	public String traverseReserveQueue() {

		if (reserveQueue.isEmpty()) {
			return "";
		} else {
			String s = "";
			reserveQueue.resetIterator();

			for (int i = 0; i < reserveQueue.size(); i++) {
				s += (reserveQueue.next().getName() + "\n");
			}
			return s;
		}
	}

	/**
	 * Returns a string of movies in the home queue in order. Successive movies
	 * are separated by newlines
	 * 
	 * @return a string of movies representing the home queue
	 */
	public String traverseAtHomeQueue() {
		atHomeQueue.resetIterator();

		if (atHomeQueue.isEmpty()) {
			return "";
		} else {

			String s = "";

			while (atHomeQueue.hasNext()) {
				s += (atHomeQueue.next().getName() + "\n");
			}
			return s;
		}
	}

	/**
	 * Closes this account and returns all movies at home to the inventory
	 */
	public void closeAccount() {
		// actual closing of account is done in customerDB
		while(!atHomeQueue.isEmpty()) {
			atHomeQueue.remove(0).backToInventory();
			nowAtHome--;
		}
	}

	/**
	 * Removes the movie in the given position from the queue of movies at home
	 * and returns it to the inventory
	 * 
	 * @param idx
	 *            the index of the movie to be returned
	 * @throws IllegalArgumentException
	 *             if the position is out of bounds
	 */
	public void returnDVD(int idx) {
		if (idx < 0 || idx > atHomeQueue.size() - 1) {
			throw new IllegalArgumentException();
		}
		
		atHomeQueue.remove(idx).backToInventory();
		nowAtHome--;
		checkOut();
		
	}

	/**
	 * Moves the movie in the given position ahead one position in the reserve
	 * queue
	 * 
	 * @param idx
	 *            the index of the movie to be moved
	 * @throws IllegalArgumentException
	 *             if the position is out of bounds. Not thrown if 0
	 */
	public void moveAheadOneInReserves(int idx) {
		if (idx < 0 || idx > reserveQueue.size() - 1) {
			throw new IllegalArgumentException();
		}

		reserveQueue.moveAheadOne(idx);

		checkOut();
	}

	/**
	 * Removes the movie in the given position from the reserve queue.
	 * 
	 * @param idx
	 *            the index of the movie to be removed
	 * @throws IllegalArgumentException
	 *             if the position is out of bounds
	 */
	public void unReserve(int idx) {
		if (idx < 0)
		{
			throw new IllegalArgumentException("No movie selected.");
		}
		
		if (idx > reserveQueue.size() - 1) 
		{
         throw new IllegalArgumentException("No movie selected.");
      }
		reserveQueue.remove(idx);
	}

	/**
	 * Places the provided movie at the end of the reserve queue
	 * 
	 * @param movie
	 *            the movie to be reserved
	 * @throws IllegalArgumentException
	 *             if movie is null
	 */
	public void reserve(Movie movie) {
		if (movie == null) {
			throw new IllegalArgumentException("Movie not specified.");
		} else {
			reserveQueue.addToRear(movie);
		}
		checkOut();
	}

	/**
	 * updates all queues
	 */
	private void checkOut() {
		atHomeQueue.resetIterator();
		reserveQueue.resetIterator();

		Movie temp = null;
		if (nowAtHome < maxAtHome && !reserveQueue.isEmpty()) {
			int n = 0;
			while (reserveQueue.hasNext() && nowAtHome < maxAtHome) {
				temp = reserveQueue.next();
				if (temp.isAvailable()) {
					temp.removeOneCopyFromInventory();
					reserveQueue.remove(n);
					atHomeQueue.addToRear(temp);
					nowAtHome++;
					return;
				}
				n++;
			}
		}
	}
}