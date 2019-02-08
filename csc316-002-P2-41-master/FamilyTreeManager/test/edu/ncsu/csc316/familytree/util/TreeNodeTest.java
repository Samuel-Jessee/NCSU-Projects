package edu.ncsu.csc316.familytree.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc316.familytree.person.Person;

/**
 * Tests the TreeNode class. Some methods are tested alongside other methods and
 * do not have individual test cases.
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
public class TreeNodeTest {

	/** TreeNode containing a Person */
	TreeNode<Person> pc;

	/** TreeNode containing a Person */
	TreeNode<Person> cj;

	/** TreeNode containing a Person */
	TreeNode<Person> sj;

	/** TreeNode containing a Person */
	TreeNode<Person> rj;

	/** TreeNode containing a Person */
	TreeNode<Person> mj;

	/** TreeNode containing a Person */
	TreeNode<Person> aj;

	/**
	 * Creates several TreeNodes containing Person objects.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		pc = new TreeNode<Person>(new Person("P", "C", "F"));
		cj = new TreeNode<Person>(new Person("C", "J", "F"));
		sj = new TreeNode<Person>(new Person("S", "J", "M"));
		rj = new TreeNode<Person>(new Person("R", "J", "F"));
		mj = new TreeNode<Person>(new Person("M", "J", "M"));
		aj = new TreeNode<Person>(new Person("A", "J", "M"));
	}

	/**
	 * Clears all objects so nothing carries over between tests.
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		pc = null;
		cj = null;
		sj = null;
		rj = null;
		mj = null;
		aj = null;
	}

	/**
	 * Test method for addChild().
	 */
	@Test
	public void testAddChild() {
		cj.addChild(sj);
		cj.addChild(rj);
		cj.addChild(mj);
		cj.addChild(aj);
		String temp = cj.getChild(0).getData().getFullName();
		assertEquals("S J", temp);
		temp = cj.getChild(1).getData().getFullName();
		assertEquals("R J", temp);
		temp = cj.getChild(2).getData().getFullName();
		assertEquals("M J", temp);
		temp = cj.getChild(3).getData().getFullName();
		assertEquals("A J", temp);
		assertTrue(cj.getChild(4) == null);
	}

	/**
	 * Test method for setParent().
	 */
	@Test
	public void testSetParent() {
		assertTrue(cj.getParent() == null);
		cj.setParent(pc);
		String temp = cj.getParent().getData().getFullName();
		assertEquals("P C", temp);
	}

	/**
	 * Test method for traceToRoot().
	 */
	@Test
	public void testTraceToRoot() {
		cj.addChild(sj);
		cj.addChild(rj);
		cj.addChild(mj);
		cj.addChild(aj);
		cj.setParent(pc);
		List<Person> trace = sj.traceToRoot();
		assertEquals("S J", trace.get(0).getFullName());
		assertEquals("C J", trace.get(1).getFullName());
		assertEquals("P C", trace.get(2).getFullName());
		assertTrue(trace.get(3) == null);
	}

	/**
	 * Test method for numberOfChildren().
	 */
	@Test
	public void testNumberOfChildren() {
		cj.addChild(sj);
		cj.addChild(rj);
		cj.addChild(mj);
		cj.addChild(aj);
		assertTrue(cj.numberOfChildren() == 4);
	}

}