package edu.ncsu.csc316.bridgemanager.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc316.bridgemanager.bridge.Bridge;

/**
 * Test for the adjacency list structure. bridge list is based off of example in
 * ts-provided design spec,
 * 
 * @author Devin Janus (dwjanus)
 * @author Samuel Jessee (sijessee)
 *
 */
public class AdjacencyListTest {

	/** Bridge object used in tests. */
	Bridge b1;

	/** Bridge object used in tests. */
	Bridge b2;

	/** Bridge object used in tests. */
	Bridge b3;

	/** Bridge object used in tests. */
	Bridge b4;

	/** Bridge object used in tests. */
	Bridge b5;

	/** Bridge object used in tests. */
	Bridge b6;

	/** AdjacencyList used in testing. */
	AdjacencyList al1;

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
		al1 = new AdjacencyList();
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
		al1 = null;
	}

	/**
	 * Tests the constructor.
	 */
	@Test
	public void testAdjacencyList() {

		al1.insertEdge(b1);
		// 0 = b1
		// _
		// 2 = b1
		assertEquals(0, al1.getEdges().get(0).compareTo(b1));
		assertEquals(0, al1.getEdges().get(2).compareTo(b1));
		assertTrue(al1.getEdges().get(0).getNext() == null);
		assertTrue(al1.getEdges().get(2).getNext() == null);

		al1.insertEdge(b2);
		// 0 = b1
		// _
		// 2 = b1->b2
		// 3 = b2
		assertEquals(0, al1.getEdges().get(3).compareTo(b2));
		assertEquals(0, al1.getEdges().get(2).getNext().compareTo(b2));
		assertTrue(al1.getEdges().get(3).getNext() == null);
		assertTrue(al1.getEdges().get(2).getNext().getNext() == null);
		assertTrue(al1.getEdges().get(0).getNext() == null);

		al1.insertEdge(b3);
		// 0 = b1->b3 ==> 0 = b3->b1
		// _
		// 2 = b1->b2
		// 3 = b2->b3 ==> 3 = b3->b2
		assertEquals(0, al1.getEdges().get(0).compareTo(b3));
		assertEquals(0, al1.getEdges().get(3).compareTo(b3));
		assertTrue(al1.getEdges().get(3).getNext().getNext() == null);
		assertTrue(al1.getEdges().get(2).getNext().getNext() == null);
		assertTrue(al1.getEdges().get(0).getNext().getNext() == null);

		al1.insertEdge(b4);
		// 0 = b3->b1->b4 ==> 0 = b3->b4->b1
		// 1 = b4
		// 2 = b1->b2
		// 3 = b3->b2
		assertEquals(0, al1.getEdges().get(0).getNext().compareTo(b4));
		assertEquals(0, al1.getEdges().get(1).compareTo(b4));
		assertTrue(al1.getEdges().get(3).getNext().getNext() == null);
		assertTrue(al1.getEdges().get(2).getNext().getNext() == null);
		assertTrue(al1.getEdges().get(1).getNext() == null);
		assertTrue(al1.getEdges().get(0).getNext().getNext().getNext() == null);

		al1.insertEdge(b5);
		// 0 = b3->b4->b1
		// 1 = b4->b5
		// 2 = b1->b2
		// 3 = b3->b2->b5 ==> 3 = b3->b5->b2
		assertEquals(0, al1.getEdges().get(1).getNext().compareTo(b5));
		assertEquals(0, al1.getEdges().get(3).getNext().compareTo(b5));
		assertTrue(al1.getEdges().get(3).getNext().getNext().getNext() == null);
		assertTrue(al1.getEdges().get(2).getNext().getNext() == null);
		assertTrue(al1.getEdges().get(1).getNext().getNext() == null);
		assertTrue(al1.getEdges().get(0).getNext().getNext().getNext() == null);

		al1.insertEdge(b6);
		// 0 = b3->b4->b1
		// 1 = b4->b5->b6 ==> 1 = b4->b6->b5
		// 2 = b1->b2->b6 ==> 1 = b6->b1->b2
		// 3 = b3->b5->b2
		assertEquals(0, al1.getEdges().get(1).getNext().compareTo(b6));
		assertEquals(0, al1.getEdges().get(2).compareTo(b6));
		assertTrue(al1.getEdges().get(3).getNext().getNext().getNext() == null);
		assertTrue(al1.getEdges().get(2).getNext().getNext().getNext() == null);
		assertTrue(al1.getEdges().get(1).getNext().getNext().getNext() == null);
		assertTrue(al1.getEdges().get(0).getNext().getNext().getNext() == null);

		assertEquals(4, al1.getEdges().size());

		String expected = "AdjacencyList[\n" + "   Island 0: -> Bridge[island1=0, island2=3, cost=14.0] -> "
				+ "Bridge[island1=1, island2=0, cost=5.0] -> " + "Bridge[island1=2, island2=0, cost=7.0]\n"
				+ "   Island 1: -> Bridge[island1=1, island2=0, cost=5.0] -> "
				+ "Bridge[island1=1, island2=2, cost=6.0] -> " + "Bridge[island1=3, island2=1, cost=10.0]\n"
				+ "   Island 2: -> Bridge[island1=1, island2=2, cost=6.0] -> "
				+ "Bridge[island1=2, island2=0, cost=7.0] -> " + "Bridge[island1=3, island2=2, cost=12.0]\n"
				+ "   Island 3: -> Bridge[island1=0, island2=3, cost=14.0] -> "
				+ "Bridge[island1=3, island2=1, cost=10.0] -> " + "Bridge[island1=3, island2=2, cost=12.0]\n]";
		assertEquals(expected, al1.toString());
	}
}