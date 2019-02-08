package edu.ncsu.csc316.bridgemanager.util;

/**
 * The UpTree class actually stores multiple upTrees. When a new node is
 * created, that node starts as its own upTree until the union function is
 * performed on it.
 * 
 * @author Theodore Reger (tlreger)
 * @author Samuel Jessee (sijessee)
 * @author Devin Janus (dwjanus)
 *
 */
public class UpTree {

	private static final int NO_CHILDREN = -1;

	/**
	 * Array list representing the uptree. Every single uptree is contained in
	 * this array list.
	 */
	private UpTreeNode[] tree;
	private int vertexCount;

	/**
	 * Creates empty uptree
	 * 
	 * @param size
	 *            The size of the UpTree
	 */
	public UpTree(int size) {
		tree = new UpTreeNode[size];
		vertexCount = 0;
	}

	/**
	 * Returns the data of the passed vertex to the calling method.
	 * 
	 * @param vertex
	 *            The position of the node whose data is to be retrieved.
	 * @return The data of the node, indicating parent vertex or number of nodes
	 *         in its uptree.
	 */
	public int getData(int vertex) {
		return tree[vertex].data;
	}

	/**
	 * Returns the size.
	 * 
	 * @return The size
	 */
	public int getSize() {
		return vertexCount;
	}

	/**
	 * Adds given vertex to the uptree with no parent
	 * 
	 * @param vertex
	 *            The vertex to be added to the upTree
	 */
	public void makeSet(int vertex) {

		// Vertex is added into the position of the uptree matching its value.
		tree[vertex] = new UpTreeNode(vertex);
		vertexCount++;
	}

	/**
	 * Finds and returns the root vertex of the passed vertex
	 * 
	 * @param v
	 *            The vertex to be found in the upTree
	 * @return The vertex of the root node of the uptree
	 */
	public int find(int v) {
		int p = tree[v].data;
		// if p is >= 0, then the current vertex is a child. Find the parent.
		if (p >= 0) {
			return find(tree[p].vertex);
		} else {
			return v;
		}
	}

	/**
	 * Makes the data, or parent, of the lower vertex equal to the position of
	 * the higher vertex.
	 * 
	 * @param u
	 *            One of two vertices whose upTrees are being joined
	 * @param v
	 *            The second vertices
	 * @return The positive size of the produced upTree, including the head
	 *         node.
	 */
	public int union(int u, int v) {
		int upTreeSize;
		// vertex u becomes parent node if greater than or equal to v
		if (u >= v) {
			tree[u].data += tree[v].data;
			tree[v].data = u;
			upTreeSize = getData(u);
		} else { // vertex v becomes parent otherwise
			tree[v].data += tree[u].data;
			tree[u].data = v;
			upTreeSize = getData(v);
		}
		// Get the (positive) size of the produced uptree to the calling method
		return upTreeSize * NO_CHILDREN;
	}

	/**
	 * An object that stores a vertex, and data which is either the position of
	 * the parent or, if the node is a root, the (negative) number of nodes in
	 * the root's upTree.
	 * 
	 * @author Theodore Reger (tlreger)
	 *
	 */
	private class UpTreeNode {
		/**
		 * Data represents the parent, or if root, the (negative) number of
		 * nodes in the tree
		 */
		private int data;

		/** The vertex that this node represents */
		private int vertex;

		/**
		 * Creates a new node of the upTree without any children
		 * 
		 * @param v
		 *            The vertex being stored in the node
		 */
		private UpTreeNode(int vertex) {
			this.vertex = vertex;
			data = NO_CHILDREN;
		}
	}

}