package edu.ncsu.csc316.bridgemanager.manager;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import edu.ncsu.csc316.bridgemanager.bridge.Bridge;
import edu.ncsu.csc316.bridgemanager.util.AdjacencyList;
import edu.ncsu.csc316.bridgemanager.util.ArrayBasedList;
import edu.ncsu.csc316.bridgemanager.util.Heap;
import edu.ncsu.csc316.bridgemanager.util.List;

/**
 * Class used for testing the core manager of the program, BridgeManager
 * 
 * @author Theodore Reger (tlreger)
 * @author Samuel Jessee (sijessee)
 * @author Devin Janus (dwjanus)
 * 
 */
public class BridgeManagerTest {

	/**
	 * Tests constructor.
	 */
	@Test
	public void testBridgeManager() {
		BridgeManager m = new BridgeManager();

		assertEquals("", m.getFileName());
	}

	/**
	 * Tests the heap and getHeap.
	 */
	@Test
	public void testGetHeap() throws FileNotFoundException {
		BridgeManager m = new BridgeManager("four_isl.txt");

		List<Bridge> bridges = new ArrayBasedList<Bridge>();
		Bridge bridge1 = new Bridge(2, 0, 7.0);
		Bridge bridge2 = new Bridge(3, 2, 12.0);
		Bridge bridge3 = new Bridge(0, 3, 14.0);
		Bridge bridge4 = new Bridge(1, 0, 5.0);
		Bridge bridge5 = new Bridge(3, 1, 10.0);
		Bridge bridge6 = new Bridge(1, 2, 6.0);
		bridges.add(bridge1);
		bridges.add(bridge2);
		bridges.add(bridge3);
		bridges.add(bridge4);
		bridges.add(bridge5);
		bridges.add(bridge6);

		Heap<Double, Bridge> h = new Heap<Double, Bridge>();
		for (int i = 0; i < 6; i++) {
			h.insert(bridges.get(i).getCost(), bridges.get(i));
		}
		for (int i = 0; i < 6; i++) {
			assertEquals(h.toString(), m.getHeap(bridges).toString());
		}

	}

	/**
	 * Tests the adjacency list builder.
	 */
	@Test
	public void testGetAdjacencyList() throws FileNotFoundException {
		BridgeManager m = new BridgeManager("four_isl.txt");

		List<Bridge> bridges = new ArrayBasedList<Bridge>();
		Bridge bridge1 = new Bridge(2, 0, 7.0);
		Bridge bridge2 = new Bridge(3, 2, 12.0);
		Bridge bridge3 = new Bridge(0, 3, 14.0);
		Bridge bridge4 = new Bridge(1, 0, 5.0);
		Bridge bridge5 = new Bridge(3, 1, 10.0);
		Bridge bridge6 = new Bridge(1, 2, 6.0);
		bridges.add(bridge1);
		bridges.add(bridge2);
		bridges.add(bridge3);
		bridges.add(bridge4);
		bridges.add(bridge5);
		bridges.add(bridge6);

		AdjacencyList l = new AdjacencyList();
		for (int i = 0; i < 6; i++) {
			l.insertEdge(bridges.get(i));
		}
		for (int i = 0; i < 6; i++) {
			assertEquals(l.toString(), m.getAdjacencyList(bridges).toString());
		}
	}

	/**
	 * Tests the MST builder.
	 */
	@Test
	public void testGetMinimumSpanningBridges() {
		// Set up the bridges for a four vertex, regular graph
		List<Bridge> bridges = new ArrayBasedList<Bridge>();
		Bridge bridge1 = new Bridge(2, 0, 7.0);
		Bridge bridge2 = new Bridge(3, 2, 12.0);
		Bridge bridge3 = new Bridge(0, 3, 14.0);
		Bridge bridge4 = new Bridge(1, 0, 5.0);
		Bridge bridge5 = new Bridge(3, 1, 10.0);
		Bridge bridge6 = new Bridge(1, 2, 6.0);
		bridges.add(bridge1);
		bridges.add(bridge2);
		bridges.add(bridge3);
		bridges.add(bridge4);
		bridges.add(bridge5);
		bridges.add(bridge6);

		ArrayBasedList<Bridge> mst = new ArrayBasedList<Bridge>();
		mst.add(bridge4);
		mst.add(bridge6);
		mst.add(bridge5);
		assertEquals(3, mst.size());

		BridgeManager m = new BridgeManager();
		ArrayBasedList<Bridge> result = m.mstFinder(bridges);
		assertEquals(3, result.size());
		for (int i = 0; i < 3; i++) {
			assertEquals(mst.get(i).getIsland1(), result.get(i).getIsland1());
			assertEquals(mst.get(i).getIsland2(), result.get(i).getIsland2());
		}

		BridgeManager a = new BridgeManager();
		assertEquals("List" + result.toString(), a.getMinimumSpanningBridges(bridges));

		// Set up for a four vertex, incomplete graph
		bridges = new ArrayBasedList<Bridge>();
		bridges.add(bridge1);
		bridges.add(bridge3);
		bridges.add(bridge4);
		bridges.add(bridge6);

		mst = new ArrayBasedList<Bridge>();
		mst.add(bridge3);
		mst.add(bridge4);
		mst.add(bridge6);

		m = new BridgeManager();
		result = m.mstFinder(bridges);
		for (int i = 0; i < 3; i++) {
			assertEquals(mst.get(i).getIsland1(), result.get(i).getIsland1());
			assertEquals(mst.get(i).getIsland2(), result.get(i).getIsland2());
		}
	}

	/**
	 * Tests the fileName
	 */
	@Test
	public void testGetFileName() throws FileNotFoundException {
		BridgeManager m = new BridgeManager("four_isl.txt");
		assertEquals("four_isl.txt", m.getFileName());
	}

	/**
	 * Makes sure the list of bridges works properly
	 */
	@Test
	public void testGetBridges() throws FileNotFoundException {
		BridgeManager m = new BridgeManager("four_isl.txt");

		// Set up the bridges for a four vertex, regular graph
		List<Bridge> bridges = new ArrayBasedList<Bridge>();
		Bridge bridge1 = new Bridge(2, 0, 7.0);
		Bridge bridge2 = new Bridge(3, 2, 12.0);
		Bridge bridge3 = new Bridge(0, 3, 14.0);
		Bridge bridge4 = new Bridge(1, 0, 5.0);
		Bridge bridge5 = new Bridge(3, 1, 10.0);
		Bridge bridge6 = new Bridge(1, 2, 6.0);
		bridges.add(bridge1);
		bridges.add(bridge2);
		bridges.add(bridge3);
		bridges.add(bridge4);
		bridges.add(bridge5);
		bridges.add(bridge6);

		for (int i = 0; i < 6; i++) {
			assertEquals(bridges.get(i).getIsland1(), m.getBridges().get(i).getIsland1());
			assertEquals(bridges.get(i).getIsland2(), m.getBridges().get(i).getIsland2());
		}
	}

}