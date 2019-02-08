package edu.ncsu.csc216.lab.fsm.course;

import edu.ncsu.csc216.lab.fsm.exception.InvalidFSMTransitionException;
import edu.ncsu.csc216.lab.fsm.validator.CourseNameValidator;
import edu.ncsu.csc216.lab.fsm.validator.Validatable;

/**
 * The Course object has a name, number of credit hours, and capacity.
 * @author Jason King
 *
 */
public class Course {
	/** The name of the course */
	private String name;
	
	/** The number of credit hours */
	private int credits;
	
	/** The maximum number of students who can enroll in the course */
	private int capacity;
	
	/** The Validator for course objects */
	private Validatable validator;
	
	/**
	 * Sets an initial course name, credits, and capacity
	 * @param name the name of the course
	 * @param credits the credit hours of the course
	 * @param capacity the maximum number of students who can enroll in the course
	 */
	public Course(String name, int credits, int capacity)
	{
		validator = new CourseNameValidator();
		setName(name);
		setCredits(credits);
		setCapacity(capacity);
	}

	/**
	 * Return the course name
	 * @return the course name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the course name.
	 * The course name cannot be null or empty.
	 * The course name must contain one to three letters, followed by three digits, followed by an optional letter.
	 * @param name the name of the course
	 */
	public void setName(String name) {
		if(name == null || name.length() == 0)
		{
			throw new IllegalArgumentException("Course name cannot be empty or null.");
		}
		try
		{
			if(!validator.isValid(name))
			{
				throw new InvalidFSMTransitionException();
			}
		} catch(InvalidFSMTransitionException e)
		{
			throw new IllegalArgumentException(e.getMessage());
		}
		this.name = name;
	}

	/**
	 * Return the number of credit hours of the course
	 * @return the number of credit hours of the course
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the number of credit hours for the course.
	 * The credit hours cannot be less than or equal to zero
	 * @param credits the number of credit hours for the course
	 */
	public void setCredits(int credits) {
		if(credits <= 0)
		{
			throw new IllegalArgumentException("The credit hours cannot be less than or equal to zero.");
		}
		this.credits = credits;
	}

	/**
	 * Returns the capacity of the course
	 * @return the course capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Sets the capacity of the course.
	 * The capacity cannot be less than or equal to zero.
	 * @param capacity the maximum number of students who can enroll in the course
	 */
	public void setCapacity(int capacity) {
		if(capacity <= 0)
		{
			throw new IllegalArgumentException("Course capacity cannot be less than or equal to zero.");
		}
		this.capacity = capacity;
	}
	
	

}
