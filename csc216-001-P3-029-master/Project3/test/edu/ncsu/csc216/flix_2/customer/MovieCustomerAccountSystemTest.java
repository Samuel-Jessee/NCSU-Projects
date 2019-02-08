package edu.ncsu.csc216.flix_2.customer;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.flix_2.rental_system.DVDRentalSystem;

/**
 * Tests MovieCustomerAccountSystem
 * 
 * @author SamuelJessee
 * @author MatthewLilly
 *
 */
public class MovieCustomerAccountSystemTest {

	/** account system for testing */
	private MovieCustomerAccountSystem system;

	/** rental system */
	private DVDRentalSystem rental;

	/**
	 * Sets up system for testing
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		rental = new DVDRentalSystem("moviesTest");
		system = new MovieCustomerAccountSystem(rental);
	}

	/**
	 * Clears fields between tests
	 */
	@After
	public void tearDown() {
		system = null;
		rental = null;
	}

	/**
	 * tests the MovieCustomerAccountSystem constructor
	 */
	@Test
	public void testMovieCustomerAccountSystem() {
		assertEquals("", system.listAccounts());
		assertFalse(system.isAdminLoggedIn());
		assertFalse(system.isCustomerLoggedIn());
	}

	/**
	 * tests the login method
	 */
	@Test
	public void testLogin() {
		system.login("admin", "admin");
		system.addNewCustomer("id", "pw", 1);
		assertTrue(system.isAdminLoggedIn());
		assertFalse(system.isCustomerLoggedIn());
		try {
			system.login("id", "pw");
			fail("state exception expected");
		} catch (IllegalStateException e) {
			assertTrue(system.isAdminLoggedIn());
			assertFalse(system.isCustomerLoggedIn());
		}
		system.logout();
		assertFalse(system.isAdminLoggedIn());
		assertFalse(system.isCustomerLoggedIn());
		system.login("id", "pw");
		assertFalse(system.isAdminLoggedIn());
		assertTrue(system.isCustomerLoggedIn());
		try {
			system.login("admin", "admin");
			fail("state exception expected");
		} catch (IllegalStateException e) {
			assertFalse(system.isAdminLoggedIn());
			assertTrue(system.isCustomerLoggedIn());
		}
	}

	/**
	 * tests the logout method
	 */
	@Test
	public void testLogout() {
		system.login("admin", "admin");
		system.addNewCustomer("id", "pw", 1);
		assertTrue(system.isAdminLoggedIn());
		assertFalse(system.isCustomerLoggedIn());
		system.logout();
		assertFalse(system.isAdminLoggedIn());
		assertFalse(system.isCustomerLoggedIn());
		system.login("id", "pw");
		assertFalse(system.isAdminLoggedIn());
		assertTrue(system.isCustomerLoggedIn());
		system.logout();
		assertFalse(system.isAdminLoggedIn());
		assertFalse(system.isCustomerLoggedIn());
	}

	/**
	 * tests the addNewCustomer method
	 */
	@Test
	public void testAddNewCustomer() {
		try {
			system.addNewCustomer("id", "pw", 1);
			system.addNewCustomer("a", "pw", 1);
			fail("should have thrown state exception because admin isn't logged in");
		} catch (IllegalStateException e) {
			assertFalse(system.isAdminLoggedIn());
		}
		system.login("admin", "admin");
		system.addNewCustomer("c", "pw", 1);
		system.addNewCustomer("d", "pw", 1);
		assertEquals("c\nd\n", system.listAccounts());
	}

	/**
	 * tests the cancelAccount method
	 */
	@Test
	public void testCancelAccount() {
		system.login("admin", "admin");
		system.addNewCustomer("id", "pw", 1);
		system.addNewCustomer("a", "pw", 1);
		assertEquals("a\nid\n", system.listAccounts());
		system.cancelAccount("id");
		assertEquals("a\n", system.listAccounts());
		try {
			system.cancelAccount("id");
			fail("illegal arg exception expected");
		} catch (IllegalArgumentException e) {
			assertEquals("a\n", system.listAccounts());
		}
		system.logout();
		try {
			system.cancelAccount("a");
			fail("illegal state exception expected");
		} catch (IllegalStateException e) {
			assertEquals("a\n", system.listAccounts());
		}
		system.login("admin", "admin");
		system.cancelAccount("a");
		assertEquals("", system.listAccounts());
	}

}
