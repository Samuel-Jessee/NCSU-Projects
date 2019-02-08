/**
 * 
 */
package edu.ncsu.csc216.course_manager.users;

import java.util.ArrayList;

import edu.ncsu.csc216.course_manager.courses.Course;

/**
 * Constructs Student objects and stores Student information including the
 * fields inherited from the User class, plus maxCredits and courses.
 * 
 * @author SamuelJessee
 *
 */
public class Student extends User {

	/** ArrayList of Course objects */
	private ArrayList<Course> courses;

	/** Maximum amount of credits that can be enrolled in */
	private int maxCredits;

	/** Maximum amount of credits that can be enrolled in */
	public static final int MAX_CREDITS = 18;

	/**
	 * Constructs a Student from the inherited constructor from User.
	 * 
	 * @param firstName
	 *            Student first name
	 * @param lastName
	 *            Student last name
	 * @param id
	 *            Student id
	 * @param email
	 *            Student email
	 * @param password
	 *            Student password
	 */
	public Student(String firstName, String lastName, String id, String email, String password) {
		this(firstName, lastName, id, email, password, MAX_CREDITS);
	}

	/**
	 * Constructs a Student object and initializes maxCredits and courses.
	 * 
	 * @param firstName
	 *            Student first name
	 * @param lastName
	 *            Student last name
	 * @param id
	 *            Student id
	 * @param email
	 *            Student email
	 * @param password
	 *            Student password
	 * @param maxCredits
	 *            Student's max credits
	 */
	public Student(String firstName, String lastName, String id, String email, String password, int maxCredits) {
		super(firstName, lastName, id, email, password);
		courses = new ArrayList<Course>();
		setMaxCredits(maxCredits);
	}

	/**
	 * Checks if the Student can add a particular Course. Returns true if the
	 * credits for the new Course added to the Student's current enrolled
	 * credits doesn't exceed the Student's maxCredits, and the Student isn't
	 * already enrolled in the Course.
	 * 
	 * @param c
	 *            Course to be checked
	 * @return true if Student can enroll in the Course
	 * @see edu.ncsu.csc216.course_manager.users.User#canAddCourse(edu.ncsu.csc216.
	 *      course_manager.courses.Course)
	 */
	@Override
	public boolean canAddCourse(Course c) {
		if (getCurrentCredits() + c.getCredits() <= maxCredits) {
			for (int i = 0; i < courses.size(); i++) {
				Course n = courses.get(i);
				if (n.equals(c)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Attempts to add a Course. Returns true if the Course was added
	 * successfully.
	 * 
	 * @param c
	 *            Course to add
	 * @return true if Course was added
	 * @see edu.ncsu.csc216.course_manager.users.User#addCourse(edu.ncsu.csc216.
	 *      course_manager.courses.Course)
	 */
	@Override
	public boolean addCourse(Course c) {
		if (canAddCourse(c)) {
			courses.add(c);
			return true;
		}
		return false;
	}

	/**
	 * Attempts to remove a Course from Student. Returns true if the Course is
	 * removed from the courses list.
	 * 
	 * @param c
	 *            Course to be removed
	 * @return true if Course is successfully removed
	 * @see edu.ncsu.csc216.course_manager.users.User#removeCourse(edu.ncsu.csc216.
	 *      course_manager.courses.Course)
	 */
	@Override
	public boolean removeCourse(Course c) {
		for (int i = 0; i < courses.size(); i++) {
			Course n = courses.get(i);
			if (n.equals(c)) {
				courses.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the list of Courses as an array.
	 * 
	 * @return Courses in an array
	 * @see edu.ncsu.csc216.course_manager.users.User#getCourses()
	 */
	@Override
	public Course[] getCourses() {
		Course[] s = new Course[courses.size()];
		return courses.toArray(s);
	}

	/**
	 * Returns the max number of credits that the Student can enroll in.
	 * 
	 * @return the maxCredits
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Sets the max number of credits the Student can enroll in. If parameter is
	 * less than zero, or greater than MAX_CREDITS, an IllegalArgumentException
	 * is thrown. If the new maxCredits is less than the total credits a Student
	 * is enrolled in, an IllegalArgumentException is thrown.
	 * 
	 * @param maxCredits
	 *            the maxCredits to set
	 * @throws IllegalArgumentException
	 *             if maxCredits is less than zero, greater than MAX_CREDITS, or
	 *             less than currently enrolled credits
	 */
	public void setMaxCredits(int maxCredits) {
		if (maxCredits < 0 || maxCredits < getCurrentCredits() || maxCredits > MAX_CREDITS) {
			throw new IllegalArgumentException();
		}
		this.maxCredits = maxCredits;
	}

	/**
	 * Returns the sum of all credits for enrolled Courses.
	 * 
	 * @return the credits sum
	 */
	public int getCurrentCredits() {

		/** Sum of credits for enrolled Courses */
		int sum = 0;

		for (int i = 0; i < courses.size(); i++) {
			Course c = courses.get(i);
			sum += c.getCredits();
		}
		return sum;
	}

	/**
	 * Generates hashCode for Student object.
	 * 
	 * @return hashCode for Student
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((courses == null) ? 0 : courses.hashCode());
		result = prime * result + maxCredits;
		return result;
	}

	/**
	 * Checks if the parameter is equal to the Student object.
	 * 
	 * @param obj
	 *            Student object to compare
	 * @return true if Students are the same
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (courses == null) {
			if (other.courses != null)
				return false;
		} else if (!courses.equals(other.courses))
			return false;
		if (maxCredits != other.maxCredits)
			return false;
		return true;
	}

	/**
	 * Returns User's toString() with maxCredits and a comma separated list of
	 * courses concatenated.
	 * 
	 * @return String containing all Student information
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < courses.size(); i++) {
			Course c = courses.get(i);
			s += "," + c.getName();
		}
		return super.toString() + "," + maxCredits + s;
	}

}
