package edu.ncsu.csc316.bridgemanager.bridge;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Bridge class.
 * 
 * @author Samuel Jessee (sijessee)
 * @author Devin Janus (dwjanus)
 * @author Theodore Reger (tlreger)
 *
 */
public class BridgeTest {

	/** Bridge used in tests. */
	Bridge b1;

	/** Bridge used in tests. */
	Bridge b2;

	/** Bridge used in tests. */
	Bridge b3;

	/** Bridge used in tests. */
	Bridge b4;

	/**
	 * Constructs some Bridge objects for testing.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {

		b1 = new Bridge(2, 0, 7.0);
		b2 = new Bridge(3, 2, 12.0);
		b3 = new Bridge(0, 3, 14.0);
		b4 = new Bridge(1, 0, 5.0);
	}

	/**
	 * Clears the Bridges between tests.
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		b1 = null;
		b2 = null;
		b3 = null;
		b4 = null;
	}

	/**
	 * Tests the constructor. In testing the constructor, the getters and
	 * setters are also tested.
	 */
	@Test
	public void testBridge() {

		assertTrue(b1.getIsland1() == 2);
		assertTrue(b1.getIsland2() == 0);
		assertTrue(b1.getCost() == 7.0);

		assertTrue(b2.getIsland1() == 3);
		assertTrue(b2.getIsland2() == 2);
		assertTrue(b2.getCost() == 12.0);

		assertTrue(b3.getIsland1() == 0);
		assertTrue(b3.getIsland2() == 3);
		assertTrue(b3.getCost() == 14.0);

		assertTrue(b4.getIsland1() == 1);
		assertTrue(b4.getIsland2() == 0);
		assertTrue(b4.getCost() == 5.0);

		// test building a new bridge
		Bridge b5 = new Bridge(3, 1, 10.0);
		assertTrue(b5.getIsland1() == 3);
		assertTrue(b5.getIsland2() == 1);
		assertTrue(b5.getCost() == 10.0);

		assertTrue(b1.getNext() == null);
		assertTrue(b2.getNext() == null);
		assertTrue(b3.getNext() == null);
		assertTrue(b4.getNext() == null);
		assertTrue(b5.getNext() == null);

		// test setters
		b1.setCost(8.0);
		assertTrue(b1.getCost() == 8.0);

		b1.setNext(b2);
		b2.setNext(b1);
		b3.setNext(b2);
		b5.setNext(b4);

		assertEquals(b1.getNext(), b2);
		assertEquals(b2.getNext(), b1);
		assertEquals(b3.getNext(), b2);
		assertEquals(b5.getNext(), b4);

		// TODO Note: These test lines will no longer be applicable if the
		// if-else logic in the Bridge constructor is deleted.
		Bridge b6 = new Bridge(3, 1, 2.00);
		assertTrue(3 == b6.getIsland1());
		assertTrue(1 == b6.getIsland2());
		assertTrue(2.00 == b6.getCost());
		assertTrue(null == b6.getNext());
		b6 = null;
		b5 = null;
	}

	/**
	 * Tests the addAdjacent method, as well as the setNext method.
	 */
	@Test
	public void testAddAdjacentAndtoString() {

		// add neighbors
		b1.addAdjacent(b2);
		b1.addAdjacent(b3);
		assertEquals(0, b1.getNext().compareTo(b3));
		assertEquals(0, b1.getNext().getNext().compareTo(b2));
		assertTrue(null == b2.getNext());

		assertTrue(null == b4.getNext());
		b4.addAdjacent(b3);
		assertEquals(0, b4.getNext().compareTo(b3));

		// get the strings
		String expectedB1 = "Bridge[island1=2, island2=0, cost=7.0]";
		String expectedB2 = "Bridge[island1=3, island2=2, cost=12.0]";
		String expectedB3 = "Bridge[island1=0, island2=3, cost=14.0]";
		String expectedB4 = "Bridge[island1=1, island2=0, cost=5.0]";

		assertEquals(expectedB1, b1.toString());
		assertEquals(expectedB2, b2.toString());
		assertEquals(expectedB3, b3.toString());
		assertEquals(expectedB4, b4.toString());
	}
}