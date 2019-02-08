package edu.ncsu.csc316.bridgemanager.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc316.bridgemanager.bridge.Bridge;

/**
 * Tests the Heap class.
 * 
 * @author Theodore Reger (tlreger)
 * @author Devin Janus (dwjanus)
 * @author Samuel Jessee (sijessee)
 *
 */
public class HeapTest {

	/** Bridge used in tests. */
	Bridge b1;

	/** Bridge used in tests. */
	Bridge b2;

	/** Bridge used in tests. */
	Bridge b3;

	/** Bridge used in tests. */
	Bridge b4;

	/** Bridge used in tests. */
	Bridge b5;

	/** Bridge used in tests. */
	Bridge b6;

	/** Heap used in tests. */
	Heap<Double, Bridge> h;

	/**
	 * Creates objects used for testing.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		b1 = new Bridge(2, 0, 7.0);
		b2 = new Bridge(3, 2, 12.0);
		b3 = new Bridge(0, 3, 14.0);
		b4 = new Bridge(1, 0, 5.0);
		b5 = new Bridge(3, 1, 10.0);
		b6 = new Bridge(1, 2, 6.0);
	}

	/**
	 * Clears objects after testing.
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		b1 = null;
		b2 = null;
		b3 = null;
		b4 = null;
		b5 = null;
		b6 = null;
		h = null;
	}

	/**
	 * Tests Heap constructor.
	 */
	@Test
	public void testHeap() {
		h = new Heap<Double, Bridge>();
		assertTrue(h.isEmpty());
	}

	/**
	 * Tests Heap constructor that has K and V parameters.
	 */
	@Test
	public void testHeapKV() {
		// create new heap with first entry (type: bridge)
		h = new Heap<Double, Bridge>(b1.getCost(), b1);

		// test getters and verify entry
		assertFalse(h.isEmpty());
		assertTrue(h.size() == 1);
		assertEquals(0, h.parent(0));
		assertFalse(h.hasLeft(0));
		assertFalse(h.hasRight(0));
		// check root
		assertTrue(h.min().getVal().compareTo(b1) == 0);

		// add next bridge and repeat...
		h.insert(b2.getCost(), b2);
		assertTrue(h.size() == 2);
		assertEquals(0, h.parent(1));
		assertFalse(h.hasLeft(1));
		assertFalse(h.hasRight(1));
		assertTrue(h.hasLeft(0));
		assertFalse(h.hasRight(0));

		h.insert(b3.getCost(), b3);
		assertTrue(h.size() == 3);
		assertEquals(0, h.parent(2));
		assertFalse(h.hasLeft(2));
		assertFalse(h.hasRight(2));
		assertFalse(h.hasLeft(1));
		assertFalse(h.hasRight(1));
		assertTrue(h.hasLeft(0));
		assertTrue(h.hasRight(0));
		// check root
		assertTrue(h.min().getVal().compareTo(b1) == 0);
		// check children of root
		assertTrue(h.get(h.left(0)).getVal().compareTo(b2) == 0);
		assertTrue(h.get(h.right(0)).getVal().compareTo(b3) == 0);

		h.insert(b4.getCost(), b4);
		assertTrue(h.size() == 4);
		assertEquals(1, h.parent(3));
		assertFalse(h.hasLeft(3));
		assertFalse(h.hasRight(3));
		assertFalse(h.hasLeft(2));
		assertFalse(h.hasRight(2));
		assertTrue(h.hasLeft(1));
		assertFalse(h.hasRight(1));
		assertTrue(h.hasLeft(0));
		assertTrue(h.hasRight(0));
		// check root
		assertTrue(h.min().getVal().compareTo(b4) == 0);
		// check children of root
		assertTrue(h.get(h.left(0)).getVal().compareTo(b1) == 0);
		assertTrue(h.get(h.right(0)).getVal().compareTo(b3) == 0);
		// and rest of heap
		assertTrue(h.get(h.left(1)).getVal().compareTo(b2) == 0);

		h.insert(b5.getCost(), b5);
		assertTrue(h.size() == 5);
		assertEquals(1, h.parent(4));
		assertFalse(h.hasLeft(4));
		assertFalse(h.hasRight(4));
		assertFalse(h.hasLeft(3));
		assertFalse(h.hasRight(3));
		assertFalse(h.hasLeft(2));
		assertFalse(h.hasRight(2));
		assertTrue(h.hasLeft(1));
		assertTrue(h.hasRight(1));
		assertTrue(h.hasLeft(0));
		assertTrue(h.hasRight(0));
		// check root
		assertTrue(h.min().getVal().compareTo(b4) == 0);
		// check children of root
		assertTrue(h.get(h.left(0)).getVal().compareTo(b1) == 0);
		assertTrue(h.get(h.right(0)).getVal().compareTo(b3) == 0);
		// and rest of heap
		assertTrue(h.get(h.left(1)).getVal().compareTo(b2) == 0);
		assertTrue(h.get(h.right(1)).getVal().compareTo(b5) == 0);

		h.insert(b6.getCost(), b6);
		assertTrue(h.size() == 6);
		assertEquals(2, h.parent(5));
		assertFalse(h.hasLeft(5));
		assertFalse(h.hasRight(5));
		assertFalse(h.hasLeft(4));
		assertFalse(h.hasRight(4));
		assertFalse(h.hasLeft(3));
		assertFalse(h.hasRight(3));
		assertTrue(h.hasLeft(2));
		assertFalse(h.hasRight(2));
		assertTrue(h.hasLeft(1));
		assertTrue(h.hasRight(1));
		assertTrue(h.hasLeft(0));
		assertTrue(h.hasRight(0));
		// check root
		assertTrue(h.min().getVal().compareTo(b4) == 0);
		// check children of root
		assertTrue(h.get(h.left(0)).getVal().compareTo(b1) == 0);
		assertTrue(h.get(h.right(0)).getVal().compareTo(b6) == 0);
		// and rest of heap
		assertTrue(h.get(h.left(1)).getVal().compareTo(b2) == 0);
		assertTrue(h.get(h.right(1)).getVal().compareTo(b5) == 0);
		assertTrue(h.get(h.left(2)).getVal().compareTo(b3) == 0);

		// ---Lets check the toString() method---//
		String expected = "Heap[Bridge[island1=1, island2=0, cost=5.0], " + "Bridge[island1=2, island2=0, cost=7.0], "
				+ "Bridge[island1=1, island2=2, cost=6.0], " + "Bridge[island1=3, island2=2, cost=12.0], "
				+ "Bridge[island1=3, island2=1, cost=10.0], " + "Bridge[island1=0, island2=3, cost=14.0]]";
		assertEquals(expected, h.toString());

		// ---Now that all have been entered and verified, lets deleteMin the
		// whole heap---//
		assertTrue(h.deleteMin().getVal().compareTo(b4) == 0);
		assertEquals(5, h.size());

		assertTrue(h.deleteMin().getVal().compareTo(b6) == 0);
		assertEquals(4, h.size());

		assertTrue(h.deleteMin().getVal().compareTo(b1) == 0);
		assertEquals(3, h.size());

		assertTrue(h.deleteMin().getVal().compareTo(b5) == 0);
		assertEquals(2, h.size());

		assertTrue(h.deleteMin().getVal().compareTo(b2) == 0);
		assertEquals(1, h.size());

		assertTrue(h.deleteMin().getVal().compareTo(b3) == 0);
		assertEquals(0, h.size());
	}
}