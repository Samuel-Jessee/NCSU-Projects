package edu.ncsu.csc316.familytree.util;

import edu.ncsu.csc316.familytree.person.Person;

/**
 * Creates a Tree of nodes which can each have an arbitrary number of children.
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
public class Tree {

	/** preOrder traversal of tree */
	private List<Person> preTrav;

	/** postOrder traversal of tree */
	private List<Person> postTrav;

	/** root node of tree */
	private TreeNode<Person> root;

	/**
	 * Builds a Tree using a preOrder and postOrder traversal.
	 * 
	 * @param pre
	 *            preOrder traversal
	 * @param post
	 *            postOrder traversal
	 */
	public Tree(List<Person> pre, List<Person> post) {
		preTrav = pre;
		postTrav = post;
		root = buildTree(preTrav.size(), 0, 0);
	}

	/**
	 * BuildTree builds the root node of a subtree and then builds the root
	 * node's children by recursively calling itself.
	 * 
	 * @param size
	 *            number of nodes in the subtree to be built
	 * @param prestart
	 *            position in preTrav where the preOrder traversal of this
	 *            subtree begins
	 * @param poststart
	 *            position in postTrav where the postOrder traversal of this
	 *            subtree begins
	 * @return root node of the new subtree
	 */
	private TreeNode<Person> buildTree(int size, int prestart, int poststart) {
		TreeNode<Person> root = new TreeNode<Person>(preTrav.get(prestart));
		if (size <= 1) {
			return root;
		}
		Person newChild;
		Person parent = root.getData();
		while (!postTrav.get(poststart).equalsPerson(parent)) {
			prestart++;
			newChild = preTrav.get(prestart);
			size = postTrav.indexOf(newChild) + 1 - poststart;
			root.addChild(buildTree(size, prestart, poststart));
			prestart += (size - 1);
			poststart = postTrav.indexOf(newChild) + 1;
		}
		return root;
	}

	/**
	 * Returns the root node of the Tree.
	 * 
	 * @return root node
	 */
	public TreeNode<Person> getRoot() {
		return root;
	}

	/**
	 * Searches for a Person in the Tree and returns the node of the Person if
	 * found.
	 * 
	 * @param name
	 *            Person to search for
	 * @return node of the Person
	 */
	public TreeNode<Person> getNode(String name) {
		TreeNode<Person> temp = null;
		temp = search(root, name);
		return temp;
	}

	/**
	 * Helper method for getNode(). Search checks a node to see if it matches
	 * the given name, and either returns the current node, or recursively calls
	 * itself for each of the node's children. If none of the node's children
	 * return a node, search returns null.
	 * 
	 * @param node
	 *            node to search
	 * @param name
	 *            name to search for
	 * @return node matching the name
	 */
	private TreeNode<Person> search(TreeNode<Person> node, String name) {
		if (node.getData().getFullName().equals(name)) {
			return node;
		} else {
			TreeNode<Person> temp = null;
			int size = node.numberOfChildren();
			for (int i = 0; i < size; i++) {
				temp = search(node.getChild(i), name);
				if (temp != null) {
					return temp;
				}
			}
			return null;
		}
	}

}