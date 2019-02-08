/**
 * 
 */
package edu.ncsu.csc216.collections;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author SarahHeckman, JeffColeman, GeneTodd, SamuelJesse
 *
 */
public class ArrayListTest {
	
	private ArrayList<String> list;

	/**
	 * Setup method to create a new ArrayList with String objects
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		list = new ArrayList<String>();
	}

	/**
	 * Tests that a ArrayList is constructed correctly.  A ArrayList of
	 * any generic type should be not null and empty, which implies a size of 0.
	 * Test method for {@link edu.ncsu.csc216.collections.ArrayList#ArrayList()}.
	 */
	@Test
	public void testArrayList() {
		//Test that the list field is created correctly.
		assertEquals(0, list.size());
	}

	/**
	 * Tests adding elements to an empty ArrayList.  Then tests adding elements to the 
	 * front, middle, and back of a ArrayList.  The size and contents should be checked
	 * after each insertion.  Additionally, the bounds of the list should be checked and null
	 * elements should not be added to the list.  Finally, test that the ArrayList with an
	 * initial capacity of 10 grows when an 11th element is added.
	 * Test method for {@link edu.ncsu.csc216.collections.ArrayList#add(int, java.lang.Object)}.
	 */
	@Test
	public void testAddIntE() {
		//Attempt to add an element to index 1 in an empty list.
		//Check that the element was not added.
		try {
			list.add(1, "apple");
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, list.size());
		}
		
		//Add element to empty list
		list.add(0, "apple");
		assertEquals(1, list.size());
		assertEquals("apple", list.get(0));
		
		//Add element to the front of a list
		list.add(0, "front");
		assertEquals("front", list.get(0));
		
		//Add element to the middle of a list
		list.add(1, "middle");
		assertEquals("middle", list.get(1));
		
		//Add element to the back of a list
		list.add("last");
		assertEquals("last", list.get(3));
		
		//Attempt to add a null element.  Check that the element
		//was not added.
		try {
			list.add(null);
			fail("Accepted Null");
		} catch (NullPointerException e) {
			assertEquals(4, list.size());
		}
		
		//Attempt to add at index -1.  Check that the element was not
		//added.
		try {
			list.add(-1, "Illegal");
			fail("Illegal Argument");
		} catch (IndexOutOfBoundsException e){
			assertEquals(4, list.size());
		}
		
		
		//Attempt to add at index 5 (since there are 4 elements in the list).
		//Check that the element was not added.
		try {
			list.add(5, "Illegal");
			fail("Illegal Argument");
		} catch (IndexOutOfBoundsException e){
			assertEquals(4, list.size());
		}
		
		
		//Test adding an 11th element to an ArrayList with an initial 
		//capacity of 10.
		for(int i = 4; i < 10; i++) {
			list.add(i, "Initial cap");
		}
		assertEquals(10, list.size());
		list.add(10, "11th element");
		assertEquals("11th element", list.get(10));
		assertEquals(11, list.size());		
	}

	/**
	 * Tests that elements are correctly removed from the front, middle, and back of an 
	 * ArrayList.  Removing the last element should leave an empty list.  The bounds are
	 * checked for the appropriate exceptions.
	 * Test method for {@link edu.ncsu.csc216.collections.ArrayList#remove(int)}.
	 */
	@Test
	public void testRemoveInt() {
		//Attempt to remove an element from an empty list
		try {
			list.remove(0);
			fail("Can't remove from empty list");
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, list.size());
		}
		
		//Add 4 elements to the list and test that the contents are correct.
		//Reuse code from your testAddIntE.
		list.add(0, "orange");
		list.add(1, "banana");
		list.add(2, "apple");
		list.add(3, "kiwi");
		assertEquals(4, list.size());
		assertEquals("orange", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("apple", list.get(2));
		assertEquals("kiwi", list.get(3));
		
		//Test that IndexOutOfBoundsException is thrown when remove() is passed
		//a negative index.  Make sure the list is unchanged.
		try {
			list.remove(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
		}
		
		//Test that IndexOutOfBoundsException is thrown when remove() is passed
		//an index > size() - 1.  Make sure the list is unchanged.
		try {
			list.remove(list.size());
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
		}
		
		//Remove middle element.  Test that the removed element is correct and
		//that the remaining list is correct after the removal.
		String s1 = list.remove(1);
		assertEquals(s1, "banana");
		assertEquals(3, list.size());
		assertEquals("orange", list.get(0));
		assertEquals("apple", list.get(1));
		assertEquals("kiwi", list.get(2));
		
		//Remove first element
		String s2 = list.remove(0);
		assertEquals(s2, "orange");
		assertEquals(2, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("kiwi", list.get(1));
		
		//Remove last element
		String s3 = list.remove(1);
		assertEquals(s3, "kiwi");
		assertEquals(1, list.size());
		assertEquals("apple", list.get(0));
		
		//Remove only element
		String s4 = list.remove(0);
		assertEquals(s4, "apple");
		assertEquals(0, list.size());
	}

	/**
	 * Tests setting an element in an empty list, the bounds of the list when
	 * using the set() method, and setting an element at the front, middle, and back
	 * of the list.  The set() method is also passed null.
	 * Test method for {@link edu.ncsu.csc216.collections.ArrayList#set(int, java.lang.Object)}.
	 */
	@Test
	public void testSetIntE() {
		//Attempt to set a value in an empty list
		try {
			list.set(0, "empty");
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, list.size());
		}
		
		
		//Add 4 elements to the list and test that the contents are correct.
		//Reuse code from your testAddIntE.
		list.add(0, "orange");
		list.add(1, "banana");
		list.add(2, "apple");
		list.add(3, "kiwi");
		assertEquals(4, list.size());
		assertEquals("orange", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("apple", list.get(2));
		assertEquals("kiwi", list.get(3));
		
		//Test that IndexOutOfBoundsException is thrown when set() is passed
		//a negative index.  Make sure the list is unchanged.
		try {
			list.set(-1, "lower");
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
			assertEquals("orange", list.get(0));
			assertEquals("banana", list.get(1));
			assertEquals("apple", list.get(2));
			assertEquals("kiwi", list.get(3));
		}
		
		//Test that IndexOutOfBoundsException is thrown when set() is passed
		//an index > size() - 1.  Make sure the list is unchanged.
		try {
			list.set(list.size(), "upper");
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
			assertEquals("orange", list.get(0));
			assertEquals("banana", list.get(1));
			assertEquals("apple", list.get(2));
			assertEquals("kiwi", list.get(3));
		}
		
		//Set middle element.  Test that the element is modified correctly, set() returns the
		//right value, and that the rest of the list is correct.
		String s1 = list.set(1, "strawberry");
		assertEquals(s1, "banana");
		assertEquals(4, list.size());
		assertEquals("orange", list.get(0));
		assertEquals("strawberry", list.get(1));
		assertEquals("apple", list.get(2));
		assertEquals("kiwi", list.get(3));
		
		//Set first element
		String s2 = list.set(0, "first");
		assertEquals(s2, "orange");
		assertEquals(4, list.size());
		assertEquals("first", list.get(0));
		assertEquals("strawberry", list.get(1));
		assertEquals("apple", list.get(2));
		assertEquals("kiwi", list.get(3));
		
		//Set last element
		String s3 = list.set(list.size() - 1, "last");
		assertEquals(s3, "kiwi");
		assertEquals(4, list.size());
		assertEquals("first", list.get(0));
		assertEquals("strawberry", list.get(1));
		assertEquals("apple", list.get(2));
		assertEquals("last", list.get(3));
		
		//Attempt to set an element to null.  Check that the element
		//was not modified.
		try {
			list.set(1, null);
			fail();
		} catch (NullPointerException e) {
			assertEquals("strawberry", list.get(1));
			assertEquals(4, list.size());
		}

	}

	/**
	 * Main get() functionality is tested in the other test methods.  This method will
	 * focus on testing the exceptions associated with bounds.
	 * Test method for {@link edu.ncsu.csc216.collections.ArrayList#get(int)}.
	 */
	@Test
	public void testGetInt() {
		//Add 4 elements to the list and test that the contents are correct.
		//Reuse code from your testAddIntE.
		list.add(0, "orange");
		list.add(1, "banana");
		list.add(2, "apple");
		list.add(3, "kiwi");
		assertEquals(4, list.size());
		assertEquals("orange", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("apple", list.get(2));
		assertEquals("kiwi", list.get(3));
		
		//Test that IndexOutOfBoundsException is thrown when get() is passed
		//a negative index.  Make sure the list is unchanged.
		try {
			@SuppressWarnings("unused")
			String s1 = list.get(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
			assertEquals("orange", list.get(0));
			assertEquals("banana", list.get(1));
			assertEquals("apple", list.get(2));
			assertEquals("kiwi", list.get(3));
		}
		
		//Test that IndexOutOfBoundsException is thrown when get() is passed
		//an index > size() - 1.  Make sure the list is unchanged.
		try {
			@SuppressWarnings("unused")
			String s2 = list.get(5);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
			assertEquals("orange", list.get(0));
			assertEquals("banana", list.get(1));
			assertEquals("apple", list.get(2));
			assertEquals("kiwi", list.get(3));
		}
	}
	
	/**
	 * Tests that the ArrayList class can add objects of type Integer and make the correct
	 * manipulations on the array (add, remove, set)
	 */
	@Test
	public void testIntegerArrayList() {
		ArrayList<Integer> intArray = new ArrayList<Integer>();
		
		//Tests that 5 numbers can be added to the list
		for(int i = 0; i < 5; i++)
			intArray.add(i, i);
		assertEquals(5, intArray.size());
		for(int i = 0; i < 5; i++)
			assertEquals(i, (int)intArray.get(i));
		
		//Tests removing the middle number (index 2)
		intArray.remove(2);
		assertEquals(4, intArray.size());
		assertEquals(0, (int) intArray.get(0));
		assertEquals(1, (int) intArray.get(1));
		assertEquals(3, (int) intArray.get(2));
		assertEquals(4, (int) intArray.get(3));
		
		//Tests adding 2 more numbers
		intArray.add(5);
		intArray.add(6);
		assertEquals(6, intArray.size());
		assertEquals(0, (int) intArray.get(0));
		assertEquals(1, (int) intArray.get(1));
		assertEquals(3, (int) intArray.get(2));
		assertEquals(4, (int) intArray.get(3));
		assertEquals(5, (int) intArray.get(4));
		assertEquals(6, (int) intArray.get(5));
		
		//Tests removing the first number
		intArray.remove(0);
		assertEquals(5, intArray.size());
		assertEquals(1, (int) intArray.get(0));
		assertEquals(3, (int) intArray.get(1));
		assertEquals(4, (int) intArray.get(2));
		assertEquals(5, (int) intArray.get(3));
		assertEquals(6, (int) intArray.get(4));
		
		intArray.add(7);
		assertEquals(7, (int) intArray.get(intArray.size() - 1));
		//Tests removing the last number
		intArray.remove(intArray.size() - 1);
		assertEquals(5, intArray.size());
		assertEquals(1, (int) intArray.get(0));
		assertEquals(3, (int) intArray.get(1));
		assertEquals(4, (int) intArray.get(2));
		assertEquals(5, (int) intArray.get(3));
		assertEquals(6, (int) intArray.get(4));
		
		intArray.add(7);
		//Tests removing the second number
		intArray.remove(1);
		assertEquals(5, intArray.size());
		assertEquals(1, (int) intArray.get(0));
		assertEquals(4, (int) intArray.get(1));
		assertEquals(5, (int) intArray.get(2));
		assertEquals(6, (int) intArray.get(3));
		assertEquals(7, (int) intArray.get(4));
		
		//Tests removing the remaining numbers
		for(int i = intArray.size() - 1; i >= 0; i--) {
			intArray.remove(i);
		}
		assertEquals(0, intArray.size());
		
	}

}
