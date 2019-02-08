package edu.ncsu.csc316.bridgemanager.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.ncsu.csc316.bridgemanager.bridge.Bridge;
import edu.ncsu.csc316.bridgemanager.util.AdjacencyList;
import edu.ncsu.csc316.bridgemanager.util.ArrayBasedList;
import edu.ncsu.csc316.bridgemanager.util.Heap;
import edu.ncsu.csc316.bridgemanager.util.List;
import edu.ncsu.csc316.bridgemanager.util.UpTree;

/**
 * Creates and manages a heap of bridges, an AdjacencyList, and a minimum
 * spanning tree.
 * 
 * @author Theodore Reger (tlreger)
 * @author Samuel Jessee (sijessee)
 * @author Devin Janus (dwjanus)
 *
 */
public class BridgeManager {

	/** File name. */
	private String fileName;

	/** List of Bridges. */
	private List<Bridge> bridges;

	/** The AdjacencyList of bridges. */
	private AdjacencyList bridgeAL;

	/** The heap of bridges. */
	private Heap<Double, Bridge> heap;

	/** the UpTree built by Kruskals algorithm */
	private UpTree upTree;
	/** The string representation of the uptree built. */
	private ArrayBasedList<Bridge> mstList;

	/**
	 * Constructs a BridgeManager object.
	 */
	public BridgeManager() {
		this.fileName = "";
		this.bridges = new ArrayBasedList<Bridge>();
		this.bridgeAL = new AdjacencyList();
		this.heap = new Heap<Double, Bridge>();
		this.mstList = new ArrayBasedList<Bridge>();
	}

	/**
	 * Constructs a BridgeManager with a given file.
	 * 
	 * @param fn
	 *            The file name.
	 * @throws FileNotFoundException
	 */
	public BridgeManager(String fn) throws FileNotFoundException {
		this.setFileName(fn);
		this.bridges = new ArrayBasedList<Bridge>();
		this.bridgeAL = new AdjacencyList();
		this.heap = new Heap<Double, Bridge>();
		build();
		this.mstList = new ArrayBasedList<Bridge>();
	}

	/**
	 * Reads the given file and creates a list of Bridges.
	 * 
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("resource")
	private void build() throws FileNotFoundException {
		try {
			Scanner file = new Scanner(new File(this.fileName));
			while (file.hasNextLine()) {
				int island1 = file.nextInt();
				int island2 = file.nextInt();
				double c = file.nextDouble();

				Bridge b = new Bridge(island1, island2, c);
				bridges.add(b);
			}
		} catch (FileNotFoundException e) {
			throw e;
		}
	}

	/**
	 * Creates a heap from the list of bridges.
	 * 
	 * @param bridges
	 *            The list of bridges.
	 * @return String representation of the heap.
	 */
	public String getHeap(List<Bridge> bridges) {
		heap = new Heap<Double, Bridge>();
		for (int i = 0; i < bridges.size(); i++) {
			heap.insert(bridges.get(i).getCost(), bridges.get(i));
		}
		return heap.toString();
	}

	/**
	 * Creates an AdjacencyList from the list of bridges.
	 * 
	 * @param bridges
	 *            List of bridges.
	 * @return String representation of the AdjacencyList.
	 */
	public String getAdjacencyList(List<Bridge> bridges) {
		bridgeAL = new AdjacencyList();
		for (int i = 0; i < bridges.size(); i++) {
			bridgeAL.insertEdge(bridges.get(i));
		}
		return bridgeAL.toString();
	}

	/**
	 * Creates an MSTFinder object, which finds the MST, then returns the String
	 * representation of the MST.
	 * 
	 * @param bridges
	 *            List of bridges.
	 * @return String representation of the MST.
	 */
	public String getMinimumSpanningBridges(List<Bridge> bridges) {

		if (this.bridgeAL == null) {
			getAdjacencyList(bridges);
		}
		this.mstList = mstFinder(bridges);

		return "List" + mstList.toString();
	}

	/**
	 * Uses Kruskal's algorithm to find the MST.
	 * 
	 * @param bridges
	 *            List of Bridges.
	 * @return The MST as an ArrayBasedList.
	 */
	public ArrayBasedList<Bridge> mstFinder(List<Bridge> bridges) {

		if (this.heap.size() == 0) {
			getHeap(bridges);
		}

		this.upTree = new UpTree(heap.size());
		// ---- Implement Kruskals Algorithm HERE -----//
		// 1) components <- |bridges| (each component is a vertex by itself)
		// int components = bridges.size();

		// 2) for each bridge b, makeSet(b)

		// This is a list of used vertices. We do not want to add duplicate
		// vertices to the uptree.
		ArrayBasedList<Integer> vertices = new ArrayBasedList<Integer>();
		// Stores the island1 value being checked
		int vertex1 = -1;
		// Stores the island2 value being checked
		int vertex2 = -1;

		// This for loop goes through every bridge in bridges list
		for (int i = 0; i < bridges.size(); i++) {
			// This tells whether the given island1 matches a vertex in the list
			// of used vertices.
			boolean matches1Flag = false;
			// This tells whether the given island2 matches a vertex in the list
			// of used vertices.
			boolean matches2Flag = false;

			vertex1 = bridges.get(i).getIsland1();
			vertex2 = bridges.get(i).getIsland2();
			// This for loop goes through every vertex in the list of used
			// vertices
			for (int j = 0; j < vertices.size(); j++) {

				// If the current island1 matches a used vertex, we can't use it
				// again.
				if ((vertex1) == vertices.get(j)) {
					matches1Flag = true;
				}
				// If the current island2 matches a used vertex, we can't use it
				// again.
				if ((vertex2) == vertices.get(j)) {
					matches2Flag = true;
				}
				// Ends the loop if island1 and 2 both match a used vertex
				if (matches1Flag && matches2Flag) {
					break;
				}
			}
			// Makes sure the current island1 doesn't match and that it has been
			// initialized.
			if (!matches1Flag && vertex1 != -1) {
				// Adds island1 to the used vertices list
				vertices.add(vertex1);
			}
			// Makes sure the current island2 doesn't match and that it has been
			// initialized.
			if (!matches2Flag && vertex2 != -1) {
				// Adds island2 to the used vertices list
				vertices.add(vertex2);
			}
		}

		// upTree's size will be equivalent to the number of vertices in the
		// graph.
		upTree = new UpTree(vertices.size());
		// Adds every vertex in the graph into the uptree.
		for (int i = 0; i < vertices.size(); i++) {
			upTree.makeSet(vertices.get(i));
		}

		// 3) process edges
		// sizeOfMST is the number of vertices in the most recently created /
		// unioned upTree.
		int sizeOfMST = 0;
		// minSpanTree is a list of bridges representing the minimum spanning
		// tree.
		// To string for the min span tree will come from this list.
		ArrayBasedList<Bridge> minSpanTree = new ArrayBasedList<Bridge>();
		// Will continue to loop until the number of vertices in the uptree is
		// equal to
		// The number of vertices in the graph
		while (sizeOfMST < upTree.getSize()) {
			Bridge b = heap.deleteMin().getVal();
			int u = upTree.find(b.getIsland1());
			int v = upTree.find(b.getIsland2());
			if (u != v) {
				sizeOfMST = upTree.union(u, v);
				minSpanTree.add(b);
			}
		}
		for (int i = 0; i < minSpanTree.size(); i++) {
			for (int j = i + 1; j < minSpanTree.size(); j++) {
				if (minSpanTree.get(j).getIsland1() < minSpanTree.get(i).getIsland1()) {
					Bridge temp = minSpanTree.get(i);
					minSpanTree.set(i, minSpanTree.get(j));
					minSpanTree.set(j, temp);
				} else if (minSpanTree.get(j).getIsland1() == minSpanTree.get(i).getIsland1()) {
					if (minSpanTree.get(j).getIsland2() < minSpanTree.get(i).getIsland2()) {
						Bridge temp = minSpanTree.get(i);
						minSpanTree.set(i, minSpanTree.get(j));
						minSpanTree.set(j, temp);
					} else if ((minSpanTree.get(j).getIsland2() == minSpanTree.get(i).getIsland2())
							&& (minSpanTree.get(j).getCost() < minSpanTree.get(i).getCost())) {
						Bridge temp = minSpanTree.get(i);
						minSpanTree.set(i, minSpanTree.get(j));
						minSpanTree.set(j, temp);
					}
				}
			}
		}
		return minSpanTree;
	}

	/**
	 * Returns the file name.
	 * 
	 * @return File name.
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the file name.
	 * 
	 * @param fileName
	 *            The fileName to set.
	 */
	private void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Gets the list of Bridges.
	 * 
	 * @return List of Bridges.
	 */
	public List<Bridge> getBridges() {
		return this.bridges;
	}

}