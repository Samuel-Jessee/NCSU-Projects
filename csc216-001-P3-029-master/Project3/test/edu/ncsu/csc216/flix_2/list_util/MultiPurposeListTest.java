package edu.ncsu.csc216.flix_2.list_util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests MultiPurposeList
 * 
 * @author SamuelJessee
 * @author MatthewLilly
 *
 */
public class MultiPurposeListTest {

	/**
	 * tests the MultiPurposeList constructor
	 */
	@Test
	public void testMultiPurposeList() {
		MultiPurposeList<String> list = new MultiPurposeList<String>();
		assertEquals(0, list.size());
	}

	/**
	 * tests the hasNext method
	 */
	@Test
	public void testHasNext() {
		MultiPurposeList<String> list = new MultiPurposeList<String>();
		list.addToRear("1");
		list.addToRear("2");
		assertTrue(list.hasNext());
		list.next();
		assertTrue(list.hasNext());
		list.next();
		assertFalse(list.hasNext());
	}

	/**
	 * tests the addItem method
	 */
	@Test
	public void testAddItem() {
		MultiPurposeList<String> list = new MultiPurposeList<String>();
		list.addItem(2, "0");
		list.addItem(5, "1");
		list.addItem(-1, "2");
		list.addItem(2, "3");

		assertEquals("2", list.lookAtItemN(0));
		assertEquals("0", list.lookAtItemN(1));
		assertEquals("3", list.lookAtItemN(2));
		assertEquals("1", list.lookAtItemN(3));

	}

	/**
	 * tests the isEmpty method
	 */
	@Test
	public void testIsEmpty() {
		MultiPurposeList<String> list = new MultiPurposeList<String>();
		assertTrue(list.isEmpty());

		list.addToRear("");
		assertFalse(list.isEmpty());
	}

	/**
	 * tests the lookAtItemN method
	 */
	@Test
	public void testLookAtItemN() {
		MultiPurposeList<String> list = new MultiPurposeList<String>();
		list.addToRear("");
		assertEquals("", list.lookAtItemN(0));
	}

	/**
	 * tests the remove method
	 */
	@Test
	public void testRemove() {
		MultiPurposeList<String> list = new MultiPurposeList<String>();
		list.addToRear("");
		assertFalse(list.isEmpty());
		list.remove(0);
		assertTrue(list.isEmpty());

		list = new MultiPurposeList<String>();

		list.addToRear("");
		list.addToRear("");
		assertFalse(list.isEmpty());
		list.remove(0);

		assertEquals(1, list.size());

		MultiPurposeList<String> list2 = new MultiPurposeList<String>();

		list2.addToRear("");
		list2.addToRear("");
		list2.addToRear("");
		list2.addToRear("");
		assertFalse(list.isEmpty());
		list2.remove(2);
		list2.remove(2);
		assertEquals(2, list2.size());
	}

	/**
	 * tests the moveAheadOne method
	 */
	@Test
	public void testMoveAheadOne() {
		MultiPurposeList<String> list = new MultiPurposeList<String>();
		list.addToRear("0");
		list.addToRear("1");

		list.moveAheadOne(1);
		assertEquals("1", list.lookAtItemN(0));
		list.addToRear("2");
		list.moveAheadOne(2);
		assertEquals("2", list.lookAtItemN(1));
	}

	/**
	 * tests the size method
	 */
	@Test
	public void testSize() {
		MultiPurposeList<String> list = new MultiPurposeList<String>();
		assertEquals(0, list.size());

		list.addToRear("");
		assertEquals(1, list.size());

		list.addItem(0, "");
		assertEquals(2, list.size());

		list.remove(0);
		assertEquals(1, list.size());
	}

	/**
	 * tests addToRear method
	 */
	@Test
	public void testAddToRear() {
		MultiPurposeList<String> list = new MultiPurposeList<String>();
		list.addToRear("0");
		assertEquals("0", list.lookAtItemN(0));

		list.addToRear("1");
		assertEquals("1", list.lookAtItemN(1));
	}
}
