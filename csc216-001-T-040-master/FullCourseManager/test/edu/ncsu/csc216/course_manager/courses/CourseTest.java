package edu.ncsu.csc216.course_manager.courses;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.course_manager.courses.Course;
import edu.ncsu.csc216.course_manager.users.Faculty;

/**
 * Tests for Course.
 * 
 * @author SarahHeckman
 */
public class CourseTest {

	/** Faculty Object */
	private Faculty f1, f2;

	/**
	 * Tests the Course constructor.
	 */
	@Test
	public void testCourse() {
		// Test correct path
		Course c = new Course("CSC216", 3, 10);
		assertEquals("CSC216", c.getName());
		assertEquals(3, c.getCredits());
		assertEquals(10, c.getCapacity());

		// Test null name
		c = null;
		try {
			c = new Course(null, 3, 10);
			fail("A course with a null name should throw an IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			assertNull(c);
		} // try

		// Test empty string name
		c = null;
		try {
			c = new Course("", 3, 10);
			fail("A course with a null name should throw an IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			assertNull(c);
		} // try

		// Test credit min boundary
		c = null;
		try {
			c = new Course("CSC216", Course.MIN_HOURS - 1, 10);
			fail("A course with a null name should throw an IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			assertNull(c);
		} // try

		c = new Course("CSC216", Course.MIN_HOURS, 10);
		assertEquals("CSC216", c.getName());
		assertEquals(Course.MIN_HOURS, c.getCredits());
		assertEquals(10, c.getCapacity());

		// Test credit max boundary
		c = null;
		try {
			c = new Course("CSC216", Course.MAX_HOURS + 1, 10);
			fail("A course with a null name should throw an IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			assertNull(c);
		} // try

		c = new Course("CSC216", Course.MAX_HOURS, 10);
		assertEquals("CSC216", c.getName());
		assertEquals(Course.MAX_HOURS, c.getCredits());
		assertEquals(10, c.getCapacity());

		// Test negative capacity
		c = null;
		try {
			c = new Course("CSC216", 3, -1);
			fail("A negative capacity should throw an IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			assertNull(c);
		} // try
	}// testCourse

	/**
	 * Tests setName() after a valid course object is created.
	 */
	@Test
	public void testSetName() {
		// Test correct path
		Course c = new Course("CSC216", 3, 10);
		assertEquals("CSC216", c.getName());
		assertEquals(3, c.getCredits());
		assertEquals(10, c.getCapacity());

		// Set name to null
		try {
			c.setName(null);
			fail();
		} catch (IllegalArgumentException e) {
			// No changes
			assertEquals("CSC216", c.getName());
			assertEquals(3, c.getCredits());
			assertEquals(10, c.getCapacity());
		} // try

		// Set name to empty string
		try {
			c.setName("");
			fail();
		} catch (IllegalArgumentException e) {
			// No changes
			assertEquals("CSC216", c.getName());
			assertEquals(3, c.getCredits());
			assertEquals(10, c.getCapacity());
		} // try

		// Change name
		c.setName("CSC116");
		assertEquals("CSC116", c.getName());
		assertEquals(3, c.getCredits());
		assertEquals(10, c.getCapacity());
	}// testSetName

	/**
	 * Tests setCredits() after a valid course object is created.
	 */
	@Test
	public void testSetCredits() {
		// Test correct path
		Course c = new Course("CSC216", 3, 10);
		assertEquals("CSC216", c.getName());
		assertEquals(3, c.getCredits());
		assertEquals(10, c.getCapacity());

		// Set credits to MIN_HOURS - 1
		try {
			c.setCredits(Course.MIN_HOURS - 1);
			fail();
		} catch (IllegalArgumentException e) {
			// No changes
			assertEquals("CSC216", c.getName());
			assertEquals(3, c.getCredits());
			assertEquals(10, c.getCapacity());
		} // try

		// Set credits to MAX_HOURS + 1
		try {
			c.setCredits(Course.MAX_HOURS + 1);
			fail();
		} catch (IllegalArgumentException e) {
			// No changes
			assertEquals("CSC216", c.getName());
			assertEquals(3, c.getCredits());
			assertEquals(10, c.getCapacity());
		} // try

		// Change credits to MIN_HOURS
		c.setCredits(Course.MIN_HOURS);
		assertEquals("CSC216", c.getName());
		assertEquals(Course.MIN_HOURS, c.getCredits());
		assertEquals(10, c.getCapacity());

		// Change credits to MAX_HOURS
		c.setCredits(Course.MAX_HOURS);
		assertEquals("CSC216", c.getName());
		assertEquals(Course.MAX_HOURS, c.getCredits());
		assertEquals(10, c.getCapacity());
	}// testSetCredits

	/**
	 * Test setCapacity() after a valid course object is created.
	 */
	@Test
	public void testSetCapacity() {
		// Test correct path
		Course c = new Course("CSC216", 3, 10);
		assertEquals("CSC216", c.getName());
		assertEquals(3, c.getCredits());

		// Set capacity to 0
		try {
			c.setCapacity(0);
			fail();
		} catch (IllegalArgumentException e) {
			// No changes
			assertEquals("CSC216", c.getName());
			assertEquals(3, c.getCredits());
			assertEquals(10, c.getCapacity());
		} // try
	}// testSetCapacity

	/**
	 * Test hashCode and equals.
	 */
	@Test
	public void testEquals() {
		Course c1 = new Course("CSC216", 4, 10);
		Course c2 = new Course("CSC216", 4, 10);
		Course c3 = new Course("CSC216", 3, 10);
		Course c4 = new Course("CSC116", 4, 10);

		assertTrue(c1.equals(c2));
		assertTrue(c1.equals(c3));
		assertFalse(c1.equals(c4));
		assertTrue(c1.equals(c1));

		assertEquals(c1.hashCode(), c2.hashCode());
		assertEquals(c1.hashCode(), c3.hashCode());
		assertNotEquals(c1.hashCode(), c4.hashCode());
	}// testEquals

	/**
	 * Tests canAddFaculty().
	 */
	@Test
	public void testCanAddFaculty() {
		f1 = new Faculty("first", "last", "id", "email@ncsu.edu", "pw", 2);
		Course c = new Course("CSC216", 3, 10);

		// Tests on a Course without a Faculty.
		assertTrue(c.canAddFaculty());

		// Tests on a Course with a Faculty.
		c.addFaculty(f1);
		assertFalse(c.canAddFaculty());
	}// testCanAddFaculty

	/**
	 * Tests addFaculty().
	 */
	@Test
	public void testAddFaculty() {
		f1 = new Faculty("first", "last", "id", "email@ncsu.edu", "pw", 2);
		f2 = new Faculty("bob", "jones", "id", "email@ncsu.edu", "pw", 2);
		Course c = new Course("CSC216", 3, 10);

		// Add Faculty to an unassigned faculty.
		assertTrue(c.addFaculty(f1));
		assertEquals(f1, c.getFaculty());

		// Add Faculty to an assigned faculty.
		assertFalse(c.addFaculty(f2));
		assertEquals(f1, c.getFaculty());
	}// testAddFaculty

	/**
	 * Tests removeFaculty().
	 */
	@Test
	public void testRemoveFaculty() {
		f1 = new Faculty("first", "last", "id", "email@ncsu.edu", "pw", 2);
		Course c = new Course("CSC216", 3, 10);

		// Remove Faculty when faculty is already null. The faculty field should
		// continue to be null.
		c.removeFaculty();
		assertEquals(null, c.getFaculty());

		// Remove Faculty, setting faculty to null.
		c.addFaculty(f1);
		c.removeFaculty();
		assertEquals(null, c.getFaculty());
	}// testRemoveFaculty

	/**
	 * Tests getFaculty().
	 */
	@Test
	public void testGetFaculty() {
		f1 = new Faculty("first", "last", "id", "email@ncsu.edu", "pw", 2);
		Course c = new Course("CSC216", 3, 10);

		// faculty is null
		assertEquals(null, c.getFaculty());

		// faculty is assigned
		c.addFaculty(f1);
		assertEquals(f1, c.getFaculty());
	}// testGetFaculty

}// class