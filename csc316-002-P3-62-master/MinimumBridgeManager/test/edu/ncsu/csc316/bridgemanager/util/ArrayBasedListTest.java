package edu.ncsu.csc316.bridgemanager.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the ArrayBasedList class.
 * 
 * @author Theodore Reger (tlreger)
 * @author Devin Janus (dwjanus)
 * @author Samuel Jessee (sijessee)
 *
 */
public class ArrayBasedListTest {

	/** Array of Strings. */
	ArrayBasedList<String> stringArray;

	/** Array of ints. */
	ArrayBasedList<Integer> intArray;

	/**
	 * Tests the constructor.
	 */
	@Test
	public void testArrayBasedList() {
		stringArray = new ArrayBasedList<String>(15);
		assertEquals(stringArray.size(), 0);

		intArray = new ArrayBasedList<Integer>();
		assertEquals(intArray.size(), 0);

		// test add(int) - also uses contains, get and size
		boolean err = false;
		try {
			stringArray.add(2, "boat");
		} catch (IndexOutOfBoundsException e) {
			err = true;
		}
		assertTrue(err);
		stringArray.add(0, "boat");
		assertTrue(stringArray.contains("boat") > -1);
		assertEquals(stringArray.get(0), "boat");
		stringArray.add(1, "plane");
		assertTrue(stringArray.contains("plane") > 0);
		assertEquals(stringArray.get(1), "plane");
		stringArray.add(2, "train");
		assertTrue(stringArray.contains("train") > 0);
		assertEquals(stringArray.get(2), "train");
		assertEquals(stringArray.size(), 3);
		stringArray.add(2, "jeep");
		assertTrue(stringArray.contains("jeep") > 0);
		assertEquals(stringArray.get(2), "jeep");
		assertEquals(stringArray.size(), 4);
		assertEquals(stringArray.get(3), "train");

		intArray.add(0, (int) 12);
		assertTrue(intArray.contains(12) > -1);
		assertTrue(intArray.get(0) == 12);
		intArray.add(1, (int) 11);
		assertTrue(intArray.contains(11) > 0);
		assertTrue(intArray.get(1) == 11);
		intArray.add(2, (int) 10);
		assertTrue(intArray.contains(10) > 0);
		assertTrue(intArray.get(2) == 10);
		assertEquals(intArray.size(), 3);

		// test add
		String npe = null;
		err = false;
		try {
			stringArray.add(npe);
		} catch (NullPointerException e) {
			err = true;
		}
		assertTrue(err);

		stringArray.add("car");
		stringArray.add("bike");
		stringArray.add("sled");

		assertEquals(stringArray.size(), 7);
		assertEquals(stringArray.get(4), "car");
		assertEquals(stringArray.get(5), "bike");
		assertEquals(stringArray.get(6), "sled");

		String expectedString = "[boat, plane, jeep, train, car, bike, sled]";
		String expectedInt = "[12, 11, 10]";

		assertEquals(stringArray.toString(), expectedString);
		assertEquals(intArray.toString(), expectedInt);

		assertEquals(stringArray.remove(0), "boat");
		assertEquals(stringArray.remove(0), "plane");
		assertEquals(stringArray.remove(0), "jeep");
		assertEquals(stringArray.remove(0), "train");
		assertEquals(stringArray.remove(0), "car");
		assertEquals(stringArray.remove(0), "bike");
		assertEquals(stringArray.remove(0), "sled");
		assertEquals(stringArray.size(), 0);

		assertTrue(intArray.remove(2) == 10);
		assertTrue(intArray.remove(1) == 11);
		assertTrue(intArray.remove(0) == 12);
		assertEquals(intArray.size(), 0);
	}
}