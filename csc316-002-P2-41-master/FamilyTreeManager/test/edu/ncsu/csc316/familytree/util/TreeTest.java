package edu.ncsu.csc316.familytree.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc316.familytree.person.Person;

/**
 * Tests the Tree class.
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
public class TreeTest {

	/** holds a preOrder traversal */
	List<Person> pre;

	/** holds a postOrder traversal */
	List<Person> post;

	/** a Person in the tree */
	Person pc = new Person("P", "C", "F");

	/** a Person in the tree */
	Person tf = new Person("T", "F", "F");

	/** a Person in the tree */
	Person cj = new Person("C", "J", "F");

	/** a Person in the tree */
	Person wf = new Person("W", "F", "M");

	/** a Person in the tree */
	Person lf = new Person("L", "F", "F");

	/** a Person in the tree */
	Person mf = new Person("M", "F", "F");

	/** a Person in the tree */
	Person bf = new Person("B", "F", "M");

	/** a Person in the tree */
	Person sj = new Person("S", "J", "M");

	/** a Person in the tree */
	Person rj = new Person("R", "J", "F");

	/** a Person in the tree */
	Person mj = new Person("M", "J", "M");

	/** a Person in the tree */
	Person aj = new Person("A", "J", "M");

	/** tree used for testing */
	Tree tree;

	/**
	 * Sets up a Tree for testing.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		pre = new LinkedList<Person>();
		pre.add(pc);
		pre.add(cj);
		pre.add(sj);
		pre.add(rj);
		pre.add(mj);
		pre.add(aj);
		pre.add(tf);
		pre.add(wf);
		pre.add(lf);
		pre.add(mf);
		pre.add(bf);
		post = new LinkedList<Person>();
		post.add(sj);
		post.add(rj);
		post.add(mj);
		post.add(aj);
		post.add(cj);
		post.add(wf);
		post.add(lf);
		post.add(mf);
		post.add(bf);
		post.add(tf);
		post.add(pc);
		tree = new Tree(pre, post);
	}

	/**
	 * Clears both traversals and the tree.
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		pre = null;
		post = null;
		tree = null;
	}

	/**
	 * Test method for Tree().
	 */
	@Test
	public void testTree() {
		TreeNode<Person> root1;
		TreeNode<Person> root2;
		TreeNode<Person> root3;
		TreeNode<Person> temp;

		// pc's children
		root1 = tree.getRoot();
		assertTrue(root1.getParent() == null);
		assertEquals("P C", root1.getData().getFullName());
		assertTrue(root1.numberOfChildren() == 2);
		root2 = root1.getChild(0);
		root3 = root1.getChild(1);
		assertEquals("C J", root2.getData().getFullName());
		assertEquals("P C", root2.getParent().getData().getFullName());
		assertEquals("T F", root3.getData().getFullName());
		assertEquals("P C", root3.getParent().getData().getFullName());

		// cj's children
		assertTrue(root2.numberOfChildren() == 4);
		temp = root2.getChild(0);
		assertEquals("S J", temp.getData().getFullName());
		assertEquals("C J", temp.getParent().getData().getFullName());
		assertTrue(temp.numberOfChildren() == 0);
		temp = root2.getChild(1);
		assertEquals("R J", temp.getData().getFullName());
		assertEquals("C J", temp.getParent().getData().getFullName());
		assertTrue(temp.numberOfChildren() == 0);
		temp = root2.getChild(2);
		assertEquals("M J", temp.getData().getFullName());
		assertEquals("C J", temp.getParent().getData().getFullName());
		assertTrue(temp.numberOfChildren() == 0);
		temp = root2.getChild(3);
		assertEquals("A J", temp.getData().getFullName());
		assertEquals("C J", temp.getParent().getData().getFullName());
		assertTrue(temp.numberOfChildren() == 0);

		// tf's children
		assertTrue(root3.numberOfChildren() == 4);
		temp = root3.getChild(0);
		assertEquals("W F", temp.getData().getFullName());
		assertEquals("T F", temp.getParent().getData().getFullName());
		assertTrue(temp.numberOfChildren() == 0);
		temp = root3.getChild(1);
		assertEquals("L F", temp.getData().getFullName());
		assertEquals("T F", temp.getParent().getData().getFullName());
		assertTrue(temp.numberOfChildren() == 0);
		temp = root3.getChild(2);
		assertEquals("M F", temp.getData().getFullName());
		assertEquals("T F", temp.getParent().getData().getFullName());
		assertTrue(temp.numberOfChildren() == 0);
		temp = root3.getChild(3);
		assertEquals("B F", temp.getData().getFullName());
		assertEquals("T F", temp.getParent().getData().getFullName());
		assertTrue(temp.numberOfChildren() == 0);

	}

	/**
	 * Test method for getRoot().
	 */
	@Test
	public void testGetRoot() {
		TreeNode<Person> root = tree.getRoot();
		assertEquals("P C", root.getData().getFullName());
	}

	/**
	 * Test method for getNode().
	 */
	@Test
	public void testGetNode() {
		TreeNode<Person> node = tree.getNode("S J");
		assertFalse(node == null);
		assertEquals("S J", node.getData().getFullName());
		node = tree.getNode("P C");
		assertEquals("P C", node.getData().getFullName());
		node = tree.getNode("T F");
		assertEquals("T F", node.getData().getFullName());
		node = tree.getNode("s b");
		assertTrue(node == null);
	}

}