package edu.ncsu.csc316.familytree.manager;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc316.familytree.person.Person;
import edu.ncsu.csc316.familytree.util.LinkedList;
import edu.ncsu.csc316.familytree.util.List;
import edu.ncsu.csc316.familytree.util.Tree;

/**
 * Tests FamilyTreeManager class.
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
public class FamilyTreeManagerTest {

	/** manager used for testing */
	FamilyTreeManager manager;

	/** holds a preOrder traversal */
	List<Person> pre;

	/** holds a postOrder traversal */
	List<Person> post;

	/** holds a preOrder traversal created by manager class */
	List<Person> preM;

	/** holds a postOrder traversal created by manager class */
	List<Person> postM;

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

	/** tree created for testing */
	Tree tree;

	/** another tree */
	Tree tree2;

	/**
	 * Creates lists an trees for use in testing.
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

		LinkedList<Person> pre2a = new LinkedList<Person>();
		pre2a.add(pc);
		pre2a.add(cj);
		pre2a.add(sj);
		pre2a.add(rj);
		pre2a.add(mj);
		pre2a.add(aj);
		pre2a.add(tf);
		pre2a.add(wf);
		pre2a.add(lf);
		pre2a.add(mf);
		pre2a.add(bf);

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
		manager = new FamilyTreeManager("cox-pre.txt", "cox-post.txt");
		preM = manager.pre;
		postM = manager.post;
		tree = new Tree(pre, post);
		tree2 = new Tree(preM, postM);
	}

	/**
	 * Clears lists and trees.
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		manager = null;
		pre = null;
		post = null;
		preM = null;
		postM = null;
		tree = null;
		tree2 = null;
	}

	/**
	 * Test method for buildTraversals().
	 */
	@Test
	public void testBuildTraversals() {
		assertTrue(pre.size() == 11);
		assertTrue(post.size() == 11);
		assertTrue(preM.size() == 11);
		assertTrue(postM.size() == 11);
		for (int i = 0; i < 11; i++) {
			assertTrue(pre.get(i).equalsPerson(preM.get(i)));
			assertTrue(post.get(i).equalsPerson(postM.get(i)));
		}
	}

	/**
	 * Test method for getLevelOrder().
	 */
	@Test
	public void testGetLevelOrder() {
		List<Person> level = new LinkedList<Person>();
		level.add(pc);
		level.add(cj);
		level.add(tf);
		level.add(sj);
		level.add(rj);
		level.add(mj);
		level.add(aj);
		level.add(wf);
		level.add(lf);
		level.add(mf);
		level.add(bf);
		List<Person> level2 = manager.getLevelOrder();
		assertTrue(level.size() == 11);
		assertTrue(level2.size() == 11);
		for (int i = 0; i < 11; i++) {
			assertTrue(level.get(i).equalsPerson(level2.get(i)));
		}
	}

	/**
	 * Test method for getRelationship().
	 */
	@Test
	public void testGetRelationship() {
		String expected = "S J is C J's son";
		String actual = manager.getRelationship("S J", "C J");
		assertEquals(expected, actual);

		expected = "R J is C J's daughter";
		actual = manager.getRelationship("R J", "C J");
		assertEquals(expected, actual);

		expected = "C J is R J's mother";
		actual = manager.getRelationship("C J", "R J");
		assertEquals(expected, actual);

		expected = "P C is S J's grandmother";
		actual = manager.getRelationship("P C", "S J");
		assertEquals(expected, actual);

		expected = "S J is P C's grandson";
		actual = manager.getRelationship("S J", "P C");
		assertEquals(expected, actual);

		expected = "R J is P C's granddaughter";
		actual = manager.getRelationship("R J", "P C");
		assertEquals(expected, actual);

		expected = "T F is C J's sister";
		actual = manager.getRelationship("T F", "C J");
		assertEquals(expected, actual);

		expected = "S J is R J's brother";
		actual = manager.getRelationship("S J", "R J");
		assertEquals(expected, actual);

		expected = "S J is T F's nephew";
		actual = manager.getRelationship("S J", "T F");
		assertEquals(expected, actual);

		expected = "R J is T F's niece";
		actual = manager.getRelationship("R J", "T F");
		assertEquals(expected, actual);

		expected = "T F is R J's aunt";
		actual = manager.getRelationship("T F", "R J");
		assertEquals(expected, actual);
	}

}