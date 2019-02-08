package edu.ncsu.csc316.familytree.util;

/**
 * TreeNode creates a node within a Tree. A TreeNode stores an object, and also
 * knows its parent, and stores a List of child nodes. Has methods to alter and
 * return its fields, and also has a method that traces its parents to the root
 * node, and returns the complete path as a List of objects.
 * 
 * @author Samuel Jessee (sijessee)
 * @param <E>
 *            the type of object to use
 *
 */
public class TreeNode<E> {

	/** this TreeNode's parent node */
	private TreeNode<E> parent;

	/** List of child nodes */
	private List<TreeNode<E>> children;

	/** object at this TreeNode */
	private E data;

	/** mark used for determining relationships */
	private boolean mark;

	/**
	 * Constructs a TreeNode with an object, and an empty list of child nodes.
	 * 
	 * @param data
	 *            object in this node
	 */
	public TreeNode(E data) {
		this.data = data;
		children = new LinkedList<TreeNode<E>>();
	}

	/**
	 * Adds a node to the current node's list of child nodes. Also updates the
	 * child to store this node as its parent.
	 * 
	 * @param child
	 *            child node to add
	 */
	public void addChild(TreeNode<E> child) {
		child.setParent(this);
		children.add(child);
	}

	/**
	 * Returns the object stored in this node.
	 * 
	 * @return object at this node
	 */
	public E getData() {
		return data;
	}

	/**
	 * Sets the parent of this TreeNode.
	 * 
	 * @param parent
	 *            parent of the TreeNode
	 */
	public void setParent(TreeNode<E> parent) {
		this.parent = parent;
	}

	/**
	 * Returns this node's parent node.
	 * 
	 * @return parent node
	 */
	public TreeNode<E> getParent() {
		return parent;
	}

	/**
	 * Traces upward in the tree from the current node to the root node. Returns
	 * a list of every object, including this one, contained in the nodes
	 * leading to the root node.
	 * 
	 * @return list of objects
	 */
	public List<E> traceToRoot() {
		List<E> treePath = new LinkedList<E>();
		TreeNode<E> current = this;
		treePath.add(current.getData());
		while ((current = current.getParent()) != null) {
			treePath.add(current.getData());
		}
		return treePath;
	}

	/**
	 * Returns the child node at the given index, or null if index is out of
	 * range.
	 * 
	 * @param index
	 *            index of child node
	 * @return child node
	 */
	public TreeNode<E> getChild(int index) {
		return children.get(index);
	}

	/**
	 * Returns the number of children this node has.
	 * 
	 * @return number of children
	 */
	public int numberOfChildren() {
		return children.size();
	}

	/**
	 * Marks a node as an ancestor.
	 */
	public void mark() {
		mark = true;
	}

	/**
	 * Marks all ancesters of this node.
	 */
	public void markAncestors() {
		TreeNode<E> current = this;
		current.mark();
		while ((current = current.getParent()) != null) {
			current.mark();
		}
	}

	/**
	 * Removes the mark on this node.
	 */
	public void unmark() {
		mark = false;
	}

	/**
	 * Clears all marks from ancestors of this node.
	 */
	public void clearMarks() {
		TreeNode<E> current = this;
		current.unmark();
		while ((current = current.getParent()) != null) {
			current.unmark();
		}
	}

	/**
	 * Returns true if node is marked.
	 * 
	 * @return true if marked
	 */
	public boolean isMarked() {
		return mark;
	}

}