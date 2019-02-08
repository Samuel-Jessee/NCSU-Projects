/**
 * 
 */
package edu.ncsu.csc316.spell_checker.list;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The ArrayBasedList test
 * 
 * @author Selena Turner (snturner)
 * @author Samuel Jessee (sijessee)
 *
 */
public class ArrayBasedListTest {
	/** The word list */
	private WordList list;
	/** A out of bounds number */
	private int bigNum = 39000;

	/**
	 * Sets up a list for testing.
	 * 
	 * @throws exception
	 */
	@Before
	public void setUp() throws Exception {
		list = new ArrayBasedList();
	}

	/**
	 * Clears the list after tests.
	 */
	@After
	public void tearDown() {
		list = null;
	}

	/**
	 * Tests ArrayBasedList constructor without arguments.
	 */
	@Test
	public void testArrayBasedList() {
		assertTrue(list.size() == 0);
	}

	/**
	 * Tests ArrayBasedList constructor with capacity parameter.
	 */
	@Test
	public void testArrayBasedListCapacity() {
		list = new ArrayBasedList(10);
		assertTrue(list.size() == 0);
	}

	/**
	 * Tests add method that adds to end of the list.
	 */
	@Test
	public void testAddAtEnd() {
		assertTrue(list.size() == 0);
		list.add("cat");
		assertTrue(list.size() == 1);
		assertEquals("cat", list.get(0));
		list.add("dog");
		assertTrue(list.size() == 2);
		assertEquals("cat", list.get(0));
		assertEquals("dog", list.get(1));
		list.add("turtle");
		assertTrue(list.size() == 3);
		assertEquals("cat", list.get(0));
		assertEquals("dog", list.get(1));
		assertEquals("turtle", list.get(2));
	}

	/**
	 * Tests addSorted()
	 */
	@Test
	public void testAddAtIndex() {
		list.add("dog");
		list.add("cat");
		list.add("caT");
		list.add("CAT");
		list.add("zebra");
		list.add("ant");
		assertEquals("dog", list.get(0));
		assertEquals("cat", list.get(1));
		assertEquals("caT", list.get(2));
		assertEquals("CAT", list.get(3));
		assertEquals("zebra", list.get(4));
		assertEquals("ant", list.get(5));
		list.add("turtle", 3);
		assertEquals("dog", list.get(0));
		assertEquals("cat", list.get(1));
		assertEquals("CAT", list.get(4));
		assertEquals("turtle", list.get(3));
		assertEquals("CAT", list.get(4));
		list.add("monkey", 5);
		assertTrue(list.size() == 8);
		assertEquals("dog", list.get(0));
		assertEquals("cat", list.get(1));
		assertEquals("caT", list.get(2));
		assertEquals("turtle", list.get(3));
		assertEquals("CAT", list.get(4));
		assertEquals("monkey", list.get(5));
		list.add("fish", 2);
		assertTrue(list.size() == 9);
		assertEquals("dog", list.get(0));
		assertEquals("cat", list.get(1));
		assertEquals("fish", list.get(2));
		assertEquals("caT", list.get(3));
		assertEquals("turtle", list.get(4));
		assertEquals("CAT", list.get(5));
		assertEquals("monkey", list.get(6));
		list.add("bird", 1);
		assertTrue(list.size() == 10);
		assertEquals("dog", list.get(0));
		assertEquals("bird", list.get(1));
		assertEquals("cat", list.get(2));
		assertEquals("fish", list.get(3));
		assertEquals("caT", list.get(4));
		assertEquals("turtle", list.get(5));
		assertEquals("CAT", list.get(6));
		assertEquals("monkey", list.get(7));
		list.add("lion", 0);
		assertTrue(list.size() == 11);
		assertEquals("lion", list.get(0));
		assertEquals("dog", list.get(1));
		assertEquals("bird", list.get(2));
		assertEquals("cat", list.get(3));
		assertEquals("fish", list.get(4));
		assertEquals("caT", list.get(5));
		assertEquals("turtle", list.get(6));
		assertEquals("CAT", list.get(7));
		assertEquals("monkey", list.get(8));
	}

	/**
	 * Tests addSorted()
	 */
	@Test
	public void testAddSorted() {

		// add to empty list
		list.addSorted("dog");
		assertTrue(list.size() == 1);
		assertEquals("dog", list.get(0));

		// add word that needs to add to the beginning
		list.addSorted("cat");
		assertTrue(list.size() == 2);
		assertEquals("cat", list.get(0));
		assertEquals("dog", list.get(1));

		// add word that adds to the end
		list.addSorted("zebra");
		assertTrue(list.size() == 3);
		assertEquals("cat", list.get(0));
		assertEquals("dog", list.get(1));
		assertEquals("zebra", list.get(2));

		// add word that goes at 1
		list.addSorted("cow");
		assertTrue(list.size() == 4);
		assertEquals("cat", list.get(0));
		assertEquals("cow", list.get(1));
		assertEquals("dog", list.get(2));
		assertEquals("zebra", list.get(3));

		// add word that goes second to last
		list.addSorted("turtle");
		assertTrue(list.size() == 5);
		assertEquals("cat", list.get(0));
		assertEquals("cow", list.get(1));
		assertEquals("dog", list.get(2));
		assertEquals("turtle", list.get(3));
		assertEquals("zebra", list.get(4));

		try {
			list.get(bigNum);
		} catch (IndexOutOfBoundsException e) {
			assertEquals(e.getMessage(), e.getMessage());
		}

		try {
			list.addSorted(null);
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), e.getMessage());
		}

		try {
			list.add(null);
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), e.getMessage());
		}

		try {
			list.add("no", bigNum);
		} catch (IndexOutOfBoundsException e) {
			assertEquals(e.getMessage(), e.getMessage());
		}

		try {
			list.add(null, 0);
		} catch (NullPointerException e) {
			assertEquals(e.getMessage(), e.getMessage());
		}
	}

	/**
	 * Tests toString()
	 */
	@Test
	public void testToString() {
		list.addSorted("what");
		list.addSorted("uhh");
		assertEquals("WordList[uhh, what]", list.toString());
	}
}
