package edu.ncsu.csc216.lab.fsm.course;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.lab.fsm.course.Course;

/**
 * Tests the Course object
 * @author Jason King
 *
 */
public class CourseTest {

	/**
	 * Tests the Course.setName method
	 */
	@Test
	public void testSetName()
	{
		// Test valid Course
		try
		{
			Course c = new Course("CSC216",5,100);
			assertEquals("CSC216", c.getName());
			assertEquals(5, c.getCredits());
			assertEquals(100, c.getCapacity());
		} catch(IllegalArgumentException e)
		{
			fail("No exception should have been thrown while constructing a valid course object.");
		}
		
		// Test valid Course
		try
		{
			Course c = new Course("CSC216L",5,100);
			assertEquals("CSC216L", c.getName());
			assertEquals(5, c.getCredits());
			assertEquals(100, c.getCapacity());
		} catch(IllegalArgumentException e)
		{
			fail("No exception should have been thrown while constructing a valid course object.");
		}
		
		// Test empty Course name
		try
		{
			Course c = new Course("",5,100);
			fail("An exception should have been thrown due to an invalid course name.");
		} catch(IllegalArgumentException e)
		{
			assertEquals("Course name cannot be empty or null.", e.getMessage());
		}
		
		// Test null Course name
		try
		{
			Course c = new Course(null,5,100);
			fail("An exception should have been thrown due to an invalid course name.");
		} catch(IllegalArgumentException e)
		{
			assertEquals("Course name cannot be empty or null.", e.getMessage());
		}
		
		// Test invalid Course name
		try
		{
			Course c = new Course("216CSC",5,100);
			fail("An exception should have been thrown due to an invalid course name.");
		} catch(IllegalArgumentException e)
		{
			assertEquals("Course name must start with a letter.", e.getMessage());
		}
		
		// Test incomplete Course name
		try
		{
			Course c = new Course("CSC",5,100);
			fail("An exception should have been thrown due to an invalid course name.");
		} catch(IllegalArgumentException e)
		{			assertEquals("Invalid FSM Transition.", e.getMessage());

		}
		
		// Test invalid Course credits
		try
		{
			Course c = new Course("CSC216",-5,100);
			fail("An exception should have been thrown due to an invalid course credits.");
		} catch(IllegalArgumentException e)
		{
			assertEquals("The credit hours cannot be less than or equal to zero.", e.getMessage());
		}
		
		// Test invalid Course capacity
		try
		{
			Course c = new Course("CSC216",5,0);
			fail("An exception should have been thrown due to an invalid course capacity.");
		} catch(IllegalArgumentException e)
		{
			assertEquals("Course capacity cannot be less than or equal to zero.", e.getMessage());
		}
	}

}
