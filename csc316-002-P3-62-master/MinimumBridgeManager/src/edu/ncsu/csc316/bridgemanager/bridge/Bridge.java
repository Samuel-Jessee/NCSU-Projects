package edu.ncsu.csc316.bridgemanager.bridge;

import edu.ncsu.csc316.bridgemanager.util.ArrayBasedList;

/**
 * Creates a Bridge object that stores the islands it connects to, the cost to
 * build the bridge, and the next bridge in the AdjacencyList.
 * 
 * @author Samuel Jessee (sijessee)
 * @author Devin Janus (dwjanus)
 * @author Theodore Reger (tlreger)
 *
 */
public class Bridge implements Comparable<Bridge> {

	/** First island the bridge connects to. */
	private int island1;

	/** Second island the bridge connects to. */
	private int island2;

	/** Cost to build the bridge. */
	private double cost;

	/** Next bridge in AdjacencyList. */
	private Bridge next;

	/**
	 * Constructs the Bridge object.
	 * 
	 * @param island1
	 *            First island the bridge connects to.
	 * @param island2
	 *            Second island the bridge connects to.
	 * @param cost
	 *            The cost to build the bridge.
	 */
	public Bridge(int island1, int island2, double cost) {
		setIsland1(island1);
		setIsland2(island2);
		setCost(cost);
		this.next = null;
	}

	/**
	 * Constructs a duplicate Bridge from a Bridge.
	 * 
	 * @param b
	 *            Bridge to recreate.
	 */
	public Bridge(Bridge b) {
		setIsland1(b.getIsland1());
		setIsland2(b.getIsland2());
		setCost(b.getCost());
		setNext(b.getNext());
	}

	/**
	 * Get the first island that the Bridge connects to.
	 * 
	 * @return the island1
	 */
	public int getIsland1() {
		return island1;
	}

	/**
	 * Set the first island that the Bridge connects to.
	 * 
	 * @param island1
	 *            the island1 to set
	 */
	private void setIsland1(int island1) {
		this.island1 = island1;
	}

	/**
	 * Get the second island that the Bridge connects to.
	 * 
	 * @return the island2
	 */
	public int getIsland2() {
		return island2;
	}

	/**
	 * Set the second island that the Bridge connects to.
	 * 
	 * @param island2
	 *            the island2 to set
	 */
	private void setIsland2(int island2) {
		this.island2 = island2;
	}

	/**
	 * Get the cost to build the Bridge.
	 * 
	 * @return the cost
	 */
	public double getCost() {
		return this.cost;
	}

	/**
	 * Set the cost to build the Bridge.
	 * 
	 * @param cost
	 *            the cost to set
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * Get the next Bridge in the AdjacencyList.
	 * 
	 * @return the next
	 */
	public Bridge getNext() {
		return this.next;
	}

	/**
	 * Set the next Bridge in the AdjacencyList.
	 * 
	 * @param next
	 *            the next to set
	 */
	public void setNext(Bridge next) {
		this.next = next;
	}

	/**
	 * Adds the next Bridge in the AdjacencyList. If the current Bridge already
	 * has a next element, addAdjacent recursively calls itself for the next
	 * Bridge in the AdjacencyList.
	 * 
	 * @param b
	 *            The Bridge to add.
	 */
	public void addAdjacent(Bridge b) {
		Bridge bridge = new Bridge(b);
		bridge.setNext(null);
		if (this.getNext() == null) {
			this.setNext(bridge);
		} else if (this.getNext().compareTo(bridge) >= 0) {
			bridge.setNext(this.getNext());
			this.setNext(bridge);
		} else {
			this.getNext().addAdjacent(bridge);
		}
	}

	/**
	 * Sorts the neighbors and current, first by island1, then by island2, then
	 * by cost.
	 * 
	 * @param b
	 *            First Bridge in adjacencyList.
	 * @return The new head of the adjacencyList.
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

	/**
	 * Returns a String representation of the Bridge.
	 * 
	 * @return A String describing the Bridge.
	 */
	public String toString() {
		return ("Bridge[island1=" + getIsland1() + ", island2=" + getIsland2() + ", cost=" + getCost() + "]");
	}

	/**
	 * Compares another Bridge with this one, and returns -1 if this Bridge goes
	 * first in sorted order, 1 if the other Bridge goes first, or 0 if they are
	 * exactly the same.
	 * 
	 * @param other
	 *            The other Bridge to compare to.
	 * @return An int telling which Bridge comes first.
	 */
	@Override
	public int compareTo(Bridge other) {
		if (this.island1 == other.getIsland1()) {
			if (this.island2 == other.getIsland2()) {
				if (this.cost == other.getCost()) {
					return 0;
				} else if (this.cost < other.getCost()) {
					return -1;
				} else {
					return 1;
				}
			} else if (this.island2 < other.getIsland2()) {
				return -1;
			} else {
				return 1;
			}
		} else if (this.island1 < other.getIsland1()) {
			return -1;
		} else {
			return 1;
		}
	}

}