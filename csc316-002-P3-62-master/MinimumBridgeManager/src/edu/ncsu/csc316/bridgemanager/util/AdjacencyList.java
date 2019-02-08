package edu.ncsu.csc316.bridgemanager.util;

import edu.ncsu.csc316.bridgemanager.bridge.Bridge;

/**
 * Creates an AdjacencyList that keeps a full list of Bridges connected to each
 * individual island.
 * 
 * @author Theodore Reger (tlreger)
 * @author Devin Janus (dwjanus)
 * @author Samuel Jessee (sijessee)
 *
 */
public class AdjacencyList {

	/** the index will correspond with the vertex/island */
	private ArrayBasedList<Bridge> edges;

	/**
	 * Constructs AdjacencyList.
	 */
	public AdjacencyList() {
		this.setEdges(new ArrayBasedList<Bridge>(5000));
	}

	/**
	 * Constructs an AdjacencyList from a list of Bridges.
	 * 
	 * @param e
	 *            List of Bridges.
	 */
	public AdjacencyList(ArrayBasedList<Bridge> e) {
		this.setEdges(e);
	}

	/**
	 * Returns the list of edges.
	 * 
	 * @return List of edges.
	 */
	public ArrayBasedList<Bridge> getEdges() {
		return edges;
	}

	/**
	 * Sets the list of edges.
	 * 
	 * @param edges
	 *            The list to set.
	 */
	private void setEdges(ArrayBasedList<Bridge> edges) {
		this.edges = edges;
	}

	/**
	 * Inserts a Bridge into the AdjacencyList.
	 * 
	 * @param b
	 *            The Bridge to add.
	 */
	public void insertEdge(Bridge b) {

		// check if the first island is in the list yet,
		// if it is not -> set the equivalent index to that bridge
		// else -> add the bridge to the existing island's neighbors
		Bridge b1 = new Bridge(b.getIsland1(), b.getIsland2(), b.getCost());
		Bridge b2 = new Bridge(b.getIsland1(), b.getIsland2(), b.getCost());

		if (edges.get(b.getIsland1()) == null)
			edges.set(b.getIsland1(), b1);
		else {
			edges.get(b.getIsland1()).addAdjacent(b1);
			edges.set(b.getIsland1(), sortAdjacent(edges.get(b.getIsland1())));
		}

		// do the same for the second island
		if (edges.get(b.getIsland2()) == null)
			edges.set(b.getIsland2(), b2);
		else {
			edges.get(b.getIsland2()).addAdjacent(b2);
			edges.set(b.getIsland2(), sortAdjacent(edges.get(b.getIsland2())));
		}
	}

	/**
	 * Returns a String representing the AdjacencyList.
	 * 
	 * @return A String describing the AdjacencyList.
	 */
	public String toString() {
		String out = "AdjacencyList[\n";
		for (int i = 0; i < edges.size(); i++) {
			out += "   Island " + i + ": -> ";

			Bridge n = edges.get(i);
			while (n != null) {
				out += n.toString();
				if (n.getNext() != null)
					out += " -> ";
				n = n.getNext();
			}
			out += "\n";
		}
		out += "]";

		return out;
	}

	/**
	 * Sorts the AdjacencyList, first by island1, then by island2, then by cost.
	 * 
	 * @param b
	 *            First Bridge in AdjacencyList.
	 * @return The new head of the AdjacencyList.
	 */
	public Bridge sortAdjacent(Bridge b) {

		ArrayBasedList<Bridge> adjacent = new ArrayBasedList<Bridge>();

		// for each next in b, add to d arraylist
		while (b != null) {
			Bridge d = new Bridge(b.getIsland1(), b.getIsland2(), b.getCost());
			adjacent.add(d);
			b = b.getNext();
		}

		// sort the arrayList by finding and removing the lowest bridge in
		// adjacent
		// and then adding all other bridges into that bridges adjacency
		// --> bridge adds its adjacent bridges in order after the first;
		Bridge d1 = new Bridge(adjacent.get(0).getIsland1(), adjacent.get(0).getIsland2(), adjacent.get(0).getCost());
		int removeIndex = 0;
		for (int i = 1; i < adjacent.size(); i++) {
			if (adjacent.get(i).compareTo(d1) < 0) {
				d1 = new Bridge(adjacent.get(i));
				removeIndex = i;
			}
		}

		// make d1 the first element and set its nexts accordingly
		d1 = adjacent.remove(removeIndex);
		// d1 = new Bridge(adjacent.remove(removeIndex).getIsland1(),
		// adjacent.remove(removeIndex).getIsland2(),
		// adjacent.remove(removeIndex).getCost());
		while (adjacent.size() > 0) {
			Bridge d2 = new Bridge(adjacent.remove(0));
			d1.addAdjacent(d2);
		}

		return d1;
	}

}