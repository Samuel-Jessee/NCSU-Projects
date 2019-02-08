package edu.ncsu.csc316.familytree.person;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Person class. There are no specific tests for testing the Person
 * constructor, because the constructor is indirectly tested through the other
 * method tests.
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
public class PersonTest {

	/** holds a Person object used in tests */
	private Person p1;

	/** holds a Person object used in tests */
	private Person p2;

	/** holds a Person object used in tests */
	private Person p3;

	/**
	 * Creates three Person objects for use in tests.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		p1 = new Person(" Samuel ", " Jessee ", " m ");
		p2 = new Person("Samuel", "Jessee", "M");
		p3 = new Person("Caleb", "Jessee", "M");
	}

	/**
	 * Sets Person objects to null, ensuring nothing carries over between tests.
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		p1 = null;
		p2 = null;
		p3 = null;
	}

	/**
	 * Test method for setFirstName().
	 */
	@Test
	public void testSetFirstName() {
		p1.setFirstName(" Caleb   ");
		assertEquals("Caleb", p1.getFirstName());
		assertFalse(p1.equalsPerson(p2));
		assertTrue(p1.equalsPerson(p3));
		assertEquals("Caleb Jessee", p1.getFullName());
	}

	/**
	 * Test method for getFirstName().
	 */
	@Test
	public void testGetFirstName() {
		assertEquals("Samuel", p1.getFirstName());
		assertEquals("Samuel", p2.getFirstName());
		assertEquals("Caleb", p3.getFirstName());
	}

	/**
	 * Test method for setLastName().
	 */
	@Test
	public void testSetLastName() {
		p1.setLastName("  jessee");
		assertEquals("jessee", p1.getLastName());
		assertFalse(p1.equalsPerson(p2));
		assertEquals("Samuel jessee", p1.getFullName());
	}

	/**
	 * Test method for getLastName().
	 */
	@Test
	public void testGetLastName() {
		assertEquals("Jessee", p1.getLastName());
		assertEquals("Jessee", p2.getLastName());
		assertEquals("Jessee", p3.getLastName());
	}

	/**
	 * Test method for setGender().
	 */
	@Test
	public void testSetGender() {
		p1.setGender("  f ");
		assertEquals("F", p1.getGender());
	}

	/**
	 * Test method for getGender().
	 */
	@Test
	public void testGetGender() {
		assertEquals("M", p1.getGender());
		assertEquals("M", p2.getGender());
		assertEquals("M", p3.getGender());
	}

	/**
	 * Test method for getFullName().
	 */
	@Test
	public void testGetFullName() {
		assertEquals("Samuel Jessee", p1.getFullName());
		assertEquals("Samuel Jessee", p2.getFullName());
		assertEquals("Caleb Jessee", p3.getFullName());
	}

	/**
	 * Test method for equals().
	 */
	@Test
	public void testEqualsPerson() {
		assertTrue(p1.equalsPerson(p2));
		assertTrue(p2.equalsPerson(p1));
		assertFalse(p3.equalsPerson(p1));
		assertFalse(p2.equalsPerson(p3));
	}

}