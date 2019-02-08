package edu.ncsu.csc216.course_manager.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the ArrayStack class
 * @author JeffColeman, GeneTodd, SamuelJesse
 *
 */
public class ArrayStackTest {
	
	/** The list the is being created as a Stack */
	ArrayStack<Integer> list;

	/**
	 * Initializes the list as a new empty Stack for use in the testing of ArrayStack
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		list = new ArrayStack<Integer>();
	}
	
	/**
	 * Tests the pop method of the ArrayStack
	 */
	@Test
	public void testPop() {
		//Pop an empty stack
		try{
			list.pop();
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertTrue(list.isEmpty());
		}
		
		//Single Element pop
		list.push(1);
		list.push(2);
		list.push(3);
		list.push(4);
		list.push(5);
		
		assertEquals(5, (int)list.pop());
		
		//Multiple elements pop
		for(int i = 4; i > 1; i--)
			assertEquals(i, (int)list.pop());
		
		//Last element pop
		assertEquals(1, (int)list.pop());
		assertTrue(list.isEmpty());
	}
	
	/**
	 * Tests the Push method of the ArrayStack
	 */
	@Test
	public void testPush() {
		//Single Element push
		list.push(1);
		assertEquals(1, (int)list.peek());
		
		//Multiple element push
		for(int i = 2; i < 5; i++) {
			list.push(i);
			assertEquals(i, (int)list.peek());
		}
	}

	/**
	 * Tests the isEmpty method of the ArrayStack
	 */
	@Test
	public void testIsEmpty() {
		assertTrue(list.isEmpty());
		list.push(1);
		assertFalse(list.isEmpty());
	}
}
