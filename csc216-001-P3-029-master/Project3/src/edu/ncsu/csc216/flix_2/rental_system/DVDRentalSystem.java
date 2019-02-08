package edu.ncsu.csc216.flix_2.rental_system;

import edu.ncsu.csc216.flix_2.customer.Customer;
import edu.ncsu.csc216.flix_2.inventory.MovieDB;

/**
 * Controls renting movies in the Movie Rental system. Represents the inventory
 * part of the overall system in the context of a single customer.
 * 
 * @author MatthewLilly
 * @author SamuelJessee
 * 
 */
public class DVDRentalSystem implements RentalManager {
	/** The current customer in the system **/
	private Customer currentCustomer;

	/** The database of movies in the system **/
	private MovieDB inventory;

	/**
	 * Constructs DVDRentalSystem with an inventory of movies from the specified
	 * file
	 * 
	 * @param filename
	 *            file containing inventory
	 */
	public DVDRentalSystem(String filename) {
		inventory = new MovieDB(filename);
	}

	/**
	 * Traverse all items in the inventory.
	 * 
	 * @return the string representing the items in the inventory
	 */
	@Override
	public String showInventory() {

		return inventory.traverse();
	}

	/**
	 * Set the customer for the current context to a given value.
	 * 
	 * @param c
	 *            the new current customer
	 */
	@Override
	public void setCustomer(Customer c) {
		currentCustomer = c;
	}

	/**
	 * Reserve the selected item for the reserve queue.
	 * 
	 * @param position
	 *            position of the selected item in the inventory
	 * @throws IllegalStateException
	 *             if no customer is logged in
	 * @throws IllegalArgumentException
	 *             if position is out of bounds
	 */
	@Override
	public void addToCustomerQueue(int position) {
		if (currentCustomer == null) {
			throw new IllegalStateException("No customer is logged in.");
		}
		// bounds exception is thrown in moviedb
		currentCustomer.reserve(inventory.findItemAt(position));

	}

	/**
	 * Move the item in the given position up 1 in the reserve queue.
	 * 
	 * @param position
	 *            current position of item to move up one
	 * @throws IllegalStateException
	 *             if no customer is logged in
	 */
	@Override
	public void reserveMoveAheadOne(int position) {
		if (currentCustomer == null) {
			throw new IllegalStateException("No customer is logged in.");
		}

		currentCustomer.moveAheadOneInReserves(position);
	}

	/**
	 * Remove the item in the given position from the reserve queue.
	 * 
	 * @param position
	 *            position of the item in the queue
	 * @throws IllegalStateException
	 *             if no customer is logged in
	 * @throws IllegalArgumentException
	 *             if position is out of bounds
	 */
	@Override
	public void removeSelectedFromReserves(int position) {
		if (currentCustomer == null) {
			throw new IllegalStateException("No customer is logged in.");
		}

		currentCustomer.unReserve(position);
	}

	/**
	 * Traverse all items in the reserve queue.
	 * 
	 * @return string representation of items in the queue
	 * @throws IllegalStateException
	 *             if no customer is logged in
	 */
	@Override
	public String traverseReserveQueue() {
		if (currentCustomer == null) {
			throw new IllegalStateException("No customer is logged in.");
		}

		return currentCustomer.traverseReserveQueue();
	}

	/**
	 * Traverse all items in the reserve queue.
	 * 
	 * @return string representation of items at home
	 * @throws IllegalStateException
	 *             if no customer is logged in
	 */
	@Override
	public String traverseAtHomeQueue() {
		if (currentCustomer == null) {
			throw new IllegalStateException("No customer is logged in.");
		}

		return currentCustomer.traverseAtHomeQueue();
	}

	/**
	 * Return the selected item to the inventory.
	 * 
	 * @param position
	 *            location in the list of items at home of the item to return
	 * @throws IllegalStateException
	 *             if no customer is logged in
	 * @throws IllegalArgumentException
	 *             if position is out of bounds
	 */
	@Override
	public void returnItemToInventory(int position) {
		if (currentCustomer == null) {
			throw new IllegalStateException("No customer is logged in.");
		}

		currentCustomer.returnDVD(position);
	}
}
