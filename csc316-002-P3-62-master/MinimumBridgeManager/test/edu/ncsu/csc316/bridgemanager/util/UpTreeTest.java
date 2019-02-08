package edu.ncsu.csc316.bridgemanager.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the UpTree class.
 * 
 * @author Theodore Reger (tlreger)
 * @author Devin Janus (dwjanus)
 * @author Samuel Jessee (sijessee)
 *
 */
public class UpTreeTest {

	/**
	 * Tests the UpTree constructor.
	 */
	@Test
	public void testUpTree() {
		UpTree u = new UpTree(0);
		assertEquals(0, u.getSize());

		u = new UpTree(4);
		assertEquals(0, u.getSize());
	}

	/**
	 * Tests makeSet.
	 */
	@Test
	public void testMakeSet() {
		UpTree u = new UpTree(4);

		for (int i = 0; i < 4; i++) {
			u.makeSet(i);
		}
		assertEquals(4, u.getSize());
	}

	/**
	 * Tests find.
	 */
	@Test
	public void testFind() {
		UpTree u = new UpTree(4);
		for (int i = 0; i < 4; i++) {
			u.makeSet(i);
		}
		u.union(0, 1);
		u.union(1, 2);
		u.union(2, 3);
		assertEquals(3, u.find(0));
		assertEquals(3, u.find(1));
		assertEquals(3, u.find(2));
	}

	/**
	 * Tests union.
	 */
	@Test
	public void testUnion() {
		UpTree u = new UpTree(4);
		for (int i = 0; i < 4; i++) {
			u.makeSet(i);
		}
		u.union(0, 1);
		assertEquals(1, u.getData(0));
		u.union(1, 2);
		assertEquals(2, u.getData(1));
		u.union(2, 3);
		assertEquals(3, u.getData(2));
	}

}