/**
 * 
 */
package edu.ncsu.csc216.course_manager.courses;

import java.util.ArrayList;

import edu.ncsu.csc216.course_manager.users.Student;
import edu.ncsu.csc216.course_manager.users.User;

/**
 * Creates, stores, and manipulates Course objects, which have a name, credits,
 * capacity, and a list of enrolled students.
 * 
 * @author SamuelJessee
 *
 */
public class Course implements Enrollable {

	/** Name of the Course */
	private String name;

	/** Credits of the Course */
	private int credits;

	/** Student capacity of the Course */
	private int capacity;

	/** Minimum number of credits */
	public static final int MIN_HOURS = 1;

	/** Maximum number of credits */
	public static final int MAX_HOURS = 4;

	/** Students enrolled in the course */
	private ArrayList<User> enrolledStudents;

	/**
	 * Creates a Course with the given name and credit hours.
	 * 
	 * @param name
	 *            course name
	 * @param credits
	 *            course credit hours
	 * @param capacity
	 *            course capacity
	 */
	public Course(String name, int credits, int capacity) {
		super();
		enrolledStudents = new ArrayList<User>();
		setName(name);
		setCredits(credits);
		setCapacity(capacity);
	}

	/**
	 * Returns the Course name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name for a Course.
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		if (name == null || name.length() == 0) {
			throw new IllegalArgumentException();
		}
		this.name = name;
	}

	/**
	 * Returns the credits of the Course.
	 * 
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the credits for a Course.
	 * 
	 * @param credits
	 *            the credits to set
	 */
	public void setCredits(int credits) {
		if (credits < MIN_HOURS || credits > MAX_HOURS) {
			throw new IllegalArgumentException();
		}
		this.credits = credits;
	}

	/**
	 * Returns the Course student capacity.
	 * 
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Sets the student capacity for a Course.
	 * 
	 * @param capacity
	 *            the capacity to set
	 */
	public void setCapacity(int capacity) {
		if (capacity <= 0 || capacity < enrolledStudents.size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}

	/**
	 * Generates hashCode for a Course.
	 * 
	 * @return Course hashCode
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Compares the parameter to a Course and returns true if they are the same.
	 * 
	 * @param obj
	 *            Course object to compare
	 * @return true if two courses are the same
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		Course other = (Course) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * Generates a String of the Course name, credits, and capacity.
	 * 
	 * @return String containing Course information
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name + "," + credits + "," + capacity;
	}

	/**
	 * Returns the enrolled students as an array.
	 * 
	 * @return enrolled students
	 */
	public Student[] getEnrolledStudents() {
		Student[] s = new Student[enrolledStudents.size()];
		return enrolledStudents.toArray(s);
	}

	/**
	 * Returns true if there is capacity to add a user to the course and the
	 * user is not already enrolled.
	 * 
	 * @param user
	 *            User to add to the course
	 * @return true if there is capacity
	 */
	public boolean canEnroll(User user) {
		if (enrolledStudents.size() < capacity) {
			if (user instanceof Student) {
				Student s = (Student) user;
				for (int i = 0; i < enrolledStudents.size(); i++) {
					if (enrolledStudents.get(i).equals(s)) {
						return false;
					}
				}
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * Enroll the user in the course if there is room.
	 * 
	 * @param user
	 *            user to enroll
	 * @return true if user is enrolled.
	 */
	public boolean enroll(User user) {
		return canEnroll(user) && enrolledStudents.add(user);
	}

	/**
	 * Drops the student from the course.
	 * 
	 * @param user
	 *            student to drop
	 * @return true if the student is dropped
	 */
	public boolean drop(User user) {
		return enrolledStudents.remove(user);
	}

}
