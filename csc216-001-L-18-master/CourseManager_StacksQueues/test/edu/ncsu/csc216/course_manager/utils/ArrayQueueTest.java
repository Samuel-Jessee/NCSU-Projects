package edu.ncsu.csc216.course_manager.utils;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for the ArrauQueue class
 * @author JeffColeman, GeneTodd, SamuelJesse
 *
 */
public class ArrayQueueTest {
	
	/** Creates an ArrayQueue reference for use in testing */
	ArrayQueue<Integer> list;

	/**
	 * Initializes the list to a new empty ArrayQueue
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		list = new ArrayQueue<Integer>();
	}

	/**
	 * Tests the enqueue method of the ArrayQueue
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
	 * Tests the dequeue method of ArrayQueue
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
	 * Tests the isEmpty method of the ArrayQueue
	 */
	@Test
	public void testIsEmpty() {
		assertTrue(list.isEmpty());
		list.enqueue(1);
		assertFalse(list.isEmpty());
	}

}
