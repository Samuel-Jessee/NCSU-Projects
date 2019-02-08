package edu.ncsu.csc216.course_manager.users;

import edu.ncsu.csc216.course_manager.courses.Course;

/**
 * A Student User in the Course Management system.  A student has a list of
 * classes they are enrolled in.
 * 
 * @author SarahHeckman
 */
public class Student extends User {
	
	private int maxCredits;
	/** Default MAX credits a student can register for */
	public static final int MAX_CREDITS = 18;
	
	/**
	 * Creates a new user with the following information.  If any of the inputs are
	 * null or an empty string an IllegalArgumentException is thrown.  If the email 
	 * does not contain one @ and one '.' after the @, an IllegalArgumentException 
	 * is thrown.  If any other error occurs when creating a User, an IllegalArgumentException
	 * is thrown.
	 * @param firstName user's first name
	 * @param lastName users' last name
	 * @param id user's id
	 * @param email user's email
	 * @param password user's plaintext password
	 */
	public Student(String firstName, String lastName, String id, String email,
			String password) {
		this(firstName, lastName, id, email, password, MAX_CREDITS);
	}
	
	public Student(String fname, String lname, String id) {
		this(fname, lname, id, id+"@ncsu.edu", id, MAX_CREDITS);
	}

	/**
	 * Creates a new user with the following information.  If any of the inputs are
	 * null or an empty string an IllegalArgumentException is thrown.  If the email 
	 * does not contain one @ and one '.' after the @, an IllegalArgumentException 
	 * is thrown.  If any other error occurs when creating a User, an IllegalArgumentException
	 * is thrown.
	 * @param firstName user's first name
	 * @param lastName users' last name
	 * @param id user's id
	 * @param email user's email
	 * @param password user's plaintext password
	 * @param maxCredits user's max credit they can register for
	 */
	public Student(String firstName, String lastName, String id, String email,
			String password, int maxCredits) {
		super(firstName, lastName, id, email, password);
		setMaxCredits(maxCredits);
	}

	/**
	 * Returns the max credits a student can take.
	 * @return the max credits a student can take
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Sets the max credits a student can take.  Max credits must be 
	 * positive and cannot exceed MAX_CREDITS.
	 * @param maxCredits number of max credits
	 */
	public void setMaxCredits(int maxCredits) {
		if (maxCredits < 0 || maxCredits > MAX_CREDITS) {
			throw new IllegalArgumentException();
		}
		this.maxCredits = maxCredits;
	}
	
	/**
	 * Returns true if the student can add the course to their schedule.
	 * @param c Course to check
	 * @return true if the course can be added
	 */
	@Override
	public boolean canAddCourse(Course c) {
		return false;
	}

	/**
	 * Adds a course to the user.  Returns false if the course cannot be 
	 * added because the student has too many credits or if the student
	 * is already enrolled.
	 * @param c Course to add
	 * @return true if the course is added
	 */
	@Override
	public boolean addCourse(Course c) {
		return false;
	}

	/**
	 * Removes a course from the user.  Returns false if the course cannot 
	 * be removed.
	 * @param c Course to remove
	 * @return true if the course is removed
	 */
	@Override
	public boolean removeCourse(Course c) {
		return false;
	}

	/**
	 * Returns a list of courses for the user.
	 * @return user's courses
	 */
	@Override
	public Course[] getCourses() {
		return null;
	}

	
}
