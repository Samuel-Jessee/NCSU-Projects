package edu.ncsu.csc316.familytree.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the LinkedList class. Only public methods are tested, since private
 * methods are tested indirectly during public method testing.
 * 
 * @author Samuel Jessee (sijessee)
 * 
 *
 */
public class LinkedListTest {

	/** holds a list for testing */
	private LinkedList<String> list;

	/**
	 * Creates a list and adds several objects to it.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		list = new LinkedList<String>();
		list.add("one");
		list.add("two");
		list.add("three");
	}

	/**
	 * Clears the list so nothing carries between tests.
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		list = null;
	}

	/**
	 * Test method for get().
	 */
	@Test
	public void testGet() {
		assertEquals("two", list.get(1));
		assertEquals("three", list.get(2));
		assertTrue(list.get(3) == null);
	}

	/**
	 * Test method for add().
	 */
	@Test
	public void testAdd() {
		list.add("four");
		assertEquals("four", list.get(3));
	}

	/**
	 * Test method for size().
	 */
	@Test
	public void testSize() {
		assertTrue(list.size() == 3);
		list.add("four");
		assertTrue(list.size() == 4);
	}

}