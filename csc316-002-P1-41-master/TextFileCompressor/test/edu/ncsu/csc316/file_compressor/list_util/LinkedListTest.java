/**
 * 
 */
package edu.ncsu.csc316.file_compressor.list_util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests LinkedList
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
public class LinkedListTest {

	/**
	 * Test method for
	 * {@link edu.ncsu.csc316.file_compressor.list_util.LinkedList#LinkedList()}
	 * .
	 */
	@Test
	public void testLinkedList() {
		LinkedList list = new LinkedList();
		assertTrue(list.size() == 0);
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc316.file_compressor.list_util.LinkedList#hasNext()}.
	 */
	@Test
	public void testHasNext() {
		LinkedList list = new LinkedList();
		assertTrue(list.size() == 0);
		assertFalse(list.hasNext());
		list.addWord("a");
		assertTrue(list.hasNext());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc316.file_compressor.list_util.LinkedList#next()}.
	 */
	@Test
	public void testNext() {
		LinkedList list = new LinkedList();
		assertTrue(list.size() == 0);
		assertFalse(list.hasNext());
		list.addWord("a");
		list.addWord("b");
		list.resetIterator();
		assertEquals("b", list.next());
		assertEquals("a", list.next());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc316.file_compressor.list_util.LinkedList#addWord(java.lang.String)}
	 * .
	 */
	@Test
	public void testAddWord() {
		LinkedList list = new LinkedList();
		assertTrue(list.size() == 0);
		assertEquals("a", list.addWord("a"));
		assertEquals("1", list.addWord("a"));
		assertEquals("b", list.addWord("b"));
		assertEquals("2", list.addWord("a"));
		assertEquals("1", list.addWord("a"));
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc316.file_compressor.list_util.LinkedList#retrieve(int)}
	 * .
	 */
	@Test
	public void testRetrieve() {
		LinkedList list = new LinkedList();
		list.addWord("a");
		list.addWord("b");
		list.addWord("c");
		list.resetIterator();
		assertEquals("a", list.retrieve(3));
		assertEquals("b", list.retrieve(3));
		assertEquals("c", list.retrieve(3));
		assertEquals("c", list.retrieve(1));
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc316.file_compressor.list_util.LinkedList#isEmpty()}.
	 */
	@Test
	public void testIsEmpty() {
		LinkedList list = new LinkedList();
		assertTrue(list.size() == 0);
		assertTrue(list.isEmpty());
		list.addWord("a");
		assertFalse(list.isEmpty());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc316.file_compressor.list_util.LinkedList#lookAtItemN(int)}
	 * .
	 */
	@Test
	public void testLookAtItemN() {
		LinkedList list = new LinkedList();
		list.addWord("a");
		list.addWord("b");
		list.addWord("c");
		assertEquals("c", list.lookAtItemN(0));
		assertEquals("b", list.lookAtItemN(1));
		assertEquals("a", list.lookAtItemN(2));
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc316.file_compressor.list_util.LinkedList#remove(int)}.
	 */
	@Test
	public void testRemove() {
		LinkedList list = new LinkedList();
		list.addWord("a");
		list.addWord("b");
		list.addWord("c");
		assertEquals("c", list.remove(0));
		assertEquals("b", list.lookAtItemN(0));
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc316.file_compressor.list_util.LinkedList#size()}.
	 */
	@Test
	public void testSize() {
		LinkedList list = new LinkedList();
		assertTrue(list.size() == 0);
		list.addWord("a");
		assertTrue(list.size() == 1);
		list.addWord("b");
		assertTrue(list.size() == 2);
		list.addWord("c");
		assertTrue(list.size() == 3);
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc316.file_compressor.list_util.LinkedList#search(java.lang.String)}
	 * .
	 */
	@Test
	public void testSearch() {
		LinkedList list = new LinkedList();
		list.addWord("a");
		list.addWord("b");
		list.addWord("c");
		assertTrue(list.search("x") == -1);
		assertTrue(list.search("c") == 0);
		assertTrue(list.search("b") == 1);
		assertTrue(list.search("a") == 2);

	}

}