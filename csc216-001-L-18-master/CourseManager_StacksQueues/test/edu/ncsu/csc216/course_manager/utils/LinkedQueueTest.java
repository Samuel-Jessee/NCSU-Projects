package edu.ncsu.csc216.course_manager.utils;

import static org.junit.Assert.*;


import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the LinkedQueue class
 * @author JeffColeman, GeneTodd, SamuelJesse
 *
 */

public class LinkedQueueTest {
	
	/** Creates a LinkedQueue reference to use in testing */
	LinkedQueue<Integer> list;

	/**
	 * Initializes the list to a new empty LinkedQueue
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		list = new LinkedQueue<Integer>();
	}

	/**
	 * Tests the enqueue method of LinkedQueue
	 */
	@Test
	public void testEnqueue() {
		//Single Element
		list.enqueue(1);
		assertEquals(1, (int)list.dequeue());
		
		//Multiple elements && Last element
		list.enqueue(1);
		list.enqueue(2);
		list.enqueue(3);
		
		for(int i = 1; i <= 3; i++)
			assertEquals(i, (int)list.dequeue());
		assertTrue(list.isEmpty());
	}
	
	/**
	 * Tests the dequeue method of LinkedQueue
	 */
	@Test
	public void testDequeue() {
		try{
			list.dequeue();
			fail();
		} catch (NoSuchElementException e) {
			assertTrue(list.isEmpty());
		}
	}
	
	/**
	 * Tests the isEmpty method of LinkedQueue
	 */
	@Test
	public void testIsEmpty() {
		assertTrue(list.isEmpty());
		list.enqueue(1);
		assertFalse(list.isEmpty());
	}

}
