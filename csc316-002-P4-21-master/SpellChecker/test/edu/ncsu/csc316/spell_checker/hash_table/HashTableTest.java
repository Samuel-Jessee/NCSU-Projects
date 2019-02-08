/**
 * 
 */
package edu.ncsu.csc316.spell_checker.hash_table;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * The HashTable test
 * 
 * @author Selena Turner (snturner)
 * @author Samuel Jessee (sijessee)
 *
 */
public class HashTableTest {
	/** The hash table */
	private HashTable d;
	/** A big number */
	private int bigNum = 39000;

	/**
	 * Tests hashCode()
	 */
	@Test
	public void testHashCode() {
		d = new HashTable();
		int h = d.hashCode("never");
		assertEquals(47379, h);
	}

	/**
	 * Tests insert() and lookup()
	 */
	@Test
	public void testInsertAndLookup() {
		d = new HashTable();
		d.insert("Hey");
		String s = d.lookup("Hey");
		assertEquals(null, s);
		d = new HashTable();
		d.insert("apple");
		d.insert("boy");
		d.insert("cat");
		d.insert("dog");
		d.insert("elephant");
		d.insert("frog");
		d.insert("goat");
		d.insert("happy");
		d.insert("igloo");
		d.insert("just");
		d.insert("keep");
		d.insert("loud");
		d.insert("monkey");
		d.insert("nap");
		d.insert("out");
		d.insert("pineapple");
		d.insert("quarter");
		d.insert("red");
		d.insert("squeak");
		d.insert("turn");
		d.insert("umbrella");
		d.insert("very");
		d.insert("water");
		d.insert("xenophone");
		d.insert("yellow");
		d.insert("zoo");
		s = d.lookup("quartr");
		assertEquals("quartr", s);
		s = d.lookup("out");
		assertEquals(null, s);
		s = d.lookup("monkay");
		try {
			d.get(bigNum);
		} catch (IndexOutOfBoundsException e) {
			assertEquals(e.getMessage(), e.getMessage());
		}
		assertEquals("monkay", s);
	}
}
