package edu.ncsu.csc216.flix_2.customer;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * tests the CustomerDB class
 * 
 * @author MatthewLilly
 * @author SamuelJessee
 * 
 */
public class CustomerDBTest {
	/**
	 * tests the CustomerDB constructor
	 */
	@Test
	public void testCustomerDB() {
		CustomerDB c = new CustomerDB();
		assertEquals("", c.listAccounts());
	}

	/**
	 * tests the verifyCustomer method
	 */
	@Test
	public void testVerifyCustomer() {
		CustomerDB c = new CustomerDB();
		c.addNewCustomer("id", "pw", 2);
		assertEquals("id\n", c.listAccounts());
		assertEquals("id", c.verifyCustomer("id", "pw").getId());
		assertTrue(c.verifyCustomer("id", "pw").verifyPassword("pw"));
	}

	/**
	 * tests the cancelAccount method
	 */
	@Test
	public void testCancelAccount() {
		CustomerDB c = new CustomerDB();
		c.addNewCustomer("id1", "pw1", 2);
		c.addNewCustomer("id2", "pw2", 2);
		c.cancelAccount("id1");
		assertEquals("id2\n", c.listAccounts());
	}

}