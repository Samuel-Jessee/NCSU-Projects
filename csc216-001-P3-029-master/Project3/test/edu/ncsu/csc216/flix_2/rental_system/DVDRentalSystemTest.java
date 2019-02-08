package edu.ncsu.csc216.flix_2.rental_system;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.flix_2.customer.Customer;
import edu.ncsu.csc216.flix_2.inventory.MovieDB;

/**
 * Tests DVDRentalSystem
 * 
 * @author SamuelJessee
 * @author MatthewLilly
 *
 */
public class DVDRentalSystemTest {

	/** display form of inventory */
	public static final String INVENTORY_DISPLAY = "Frozen\nGravity\nSpectre (currently unavailable)\nWarcraft (currently unavailable)\n";

	/** display form of inventory with one additional unavailable movie */
	public static final String INVENTORY_DISPLAY2 = "Frozen (currently unavailable)\nGravity\nSpectre (currently unavailable)\nWarcraft (currently unavailable)\n";

	/** list of movies */
	MovieDB movies;

	/** DVDRentalSystem object */
	DVDRentalSystem rental;

	/** customer object */
	Customer c;

	/** customer object */
	Customer c2;

	/** customer object */
	Customer c3;

	/**
	 * Sets up rental system for testing
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		movies = new MovieDB("moviesTest");
		rental = new DVDRentalSystem("moviesTest");
		c = new Customer("id", "pw", 1);
		c2 = new Customer("id", "pw", 1);
		c3 = new Customer("id", "pw", 1);
	}

	/**
	 * Clears fields between tests
	 */
	@After
	public void tearDown() {
		movies = null;
		rental = null;
		c = null;
		c2 = null;
		c3 = null;
	}

	/**
	 * tests the showInventory method
	 */
	@Test
	public void testShowInventory() {
		assertEquals(INVENTORY_DISPLAY, rental.showInventory());
		rental.setCustomer(c);
		rental.addToCustomerQueue(0);
		rental.setCustomer(c2);
		rental.addToCustomerQueue(0);
		rental.setCustomer(c3);
		rental.addToCustomerQueue(0);
		assertEquals(INVENTORY_DISPLAY2, rental.showInventory());
	}

	/**
	 * tests the setCustomer method
	 */
	@Test
	public void testSetCustomer() {
		try {
			rental.traverseAtHomeQueue();
			fail("without a customer logged in, should throw an exception, but does not");
		} catch (IllegalStateException e) {
			rental.setCustomer(c);
			assertEquals("", rental.traverseAtHomeQueue());
		}
	}

	/**
	 * tests the addCustomerToQueue method
	 */
	@Test
	public void testAddToCustomerQueue() {
		rental.setCustomer(c);
		rental.addToCustomerQueue(0);
		rental.addToCustomerQueue(1);
		assertEquals("Frozen\n", rental.traverseAtHomeQueue());
		assertEquals("Gravity\n", rental.traverseReserveQueue());
	}

	/**
	 * tests the reserveMoveAheadOne method
	 */
	@Test
	public void testReserveMoveAheadOne() {
		rental.setCustomer(c);
		rental.addToCustomerQueue(0);
		rental.addToCustomerQueue(1);
		rental.addToCustomerQueue(2);
		assertEquals("Frozen\n", rental.traverseAtHomeQueue());
		assertEquals("Gravity\nSpectre\n", rental.traverseReserveQueue());
		rental.reserveMoveAheadOne(0);
		assertEquals("Gravity\nSpectre\n", rental.traverseReserveQueue());
		rental.reserveMoveAheadOne(1);
		assertEquals("Spectre\nGravity\n", rental.traverseReserveQueue());
	}

	/**
	 * tests the removeSelectedFromReserves method
	 */
	@Test
	public void testRemoveSelectedFromReserves() {
		rental.setCustomer(c);
		rental.addToCustomerQueue(0);
		rental.addToCustomerQueue(1);
		rental.addToCustomerQueue(2);
		rental.removeSelectedFromReserves(0);
		assertEquals("Spectre\n", rental.traverseReserveQueue());
		try {
			rental.removeSelectedFromReserves(3);
			fail("should have thrown an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertEquals("Spectre\n", rental.traverseReserveQueue());
		}
	}

	/**
	 * tests the returnItemToInventory method
	 */
	@Test
	public void testReturnItemToInventory() {
		rental.setCustomer(c2);
		rental.addToCustomerQueue(0);
		rental.setCustomer(c3);
		rental.addToCustomerQueue(0);

		rental.setCustomer(c);
		rental.addToCustomerQueue(0);
		rental.addToCustomerQueue(1);
		rental.addToCustomerQueue(2);

		assertEquals(INVENTORY_DISPLAY2, rental.showInventory());
		assertEquals("Frozen\n", rental.traverseAtHomeQueue());
		assertEquals("Gravity\nSpectre\n", rental.traverseReserveQueue());
		rental.returnItemToInventory(0);

		assertEquals(INVENTORY_DISPLAY, rental.showInventory());
		assertEquals("Gravity\n", rental.traverseAtHomeQueue());
		assertEquals("Spectre\n", rental.traverseReserveQueue());

		try {
			rental.returnItemToInventory(3);
			fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e) {
			assertEquals("Gravity\n", rental.traverseAtHomeQueue());
		}
	}

}
