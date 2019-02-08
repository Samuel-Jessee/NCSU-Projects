/**
 * 
 */
package edu.ncsu.csc216.course_manager.users;

import java.util.ArrayList;

import edu.ncsu.csc216.course_manager.courses.Course;

/**
 * Constructs Faculty objects and stores Faculty information.
 * 
 * @author SamuelJessee
 *
 */
public class Faculty extends User {

	/** minimum number of Courses a Faculty can teach during a semester */
	private static final int MIN_COURSES = 1;

	/** maximum number of Courses a Faculty can teach during a semester */
	private static final int MAX_COURSES = 3;

	/** max number of Courses a Faculty teaches during a semester */
	private int maxCourses;

	/** ArrayList of Courses */
	private ArrayList<Course> courses;

	/**
	 * Constructs a Faculty with the given parameters.
	 * 
	 * @param firstName
	 *            Faculty's first name
	 * @param lastName
	 *            Faculty's last name
	 * @param id
	 *            Faculty's id
	 * @param email
	 *            Faculty's email
	 * @param password
	 *            Faculty's password
	 * @param maxCourses
	 *            number of Courses a Faculty teaches in a semester
	 */
	public Faculty(String firstName, String lastName, String id, String email, String password, int maxCourses) {
		super(firstName, lastName, id, email, password);
		setMaxCourses(maxCourses);
		courses = new ArrayList<Course>();
	}

	/**
	 * Sets the max number of Courses a Faculty teaches during a semester.
	 * 
	 * @param maxCourses
	 *            max number of Courses
	 */
	public void setMaxCourses(int maxCourses) {
		if (maxCourses < MIN_COURSES || maxCourses > MAX_COURSES) {
			throw new IllegalArgumentException();
		} else if (courses != null) {
			if (maxCourses < courses.size()) {
				throw new IllegalArgumentException();
			}
		}
		this.maxCourses = maxCourses;
	}

	/**
	 * Returns the max number of Courses a Faculty teaches during a semester.
	 * 
	 * @return the max number of Courses
	 */
	public int getMaxCourses() {
		return maxCourses;
	}

	/**
	 * Returns true if the faculty can add the course to their schedule.
	 * 
	 * @param c
	 *            Course to check
	 * @return true if the course can be added
	 */
	@Override
	public boolean canAddCourse(Course c) {
		return !(courses.size() >= maxCourses || courses.contains(c));
	}

	/**
	 * Adds a course to the user. Returns false if the course cannot be added
	 * because the faculty has too many courses or if the faculty is already
	 * teaching
	 * 
	 * @param c
	 *            Course to add
	 * @return true if the course is added
	 */
	@Override
	public boolean addCourse(Course c) {
		if (canAddCourse(c)) {
			return courses.add(c);
		}
		return false;
	}

	/**
	 * Removes a course from the user. Returns false if the course cannot be
	 * removed.
	 * 
	 * @param c
	 *            Course to remove
	 * @return true if the course is removed
	 */
	@Override
	public boolean removeCourse(Course c) {
		if (c == null)
			return false; // Avoid NPE
		return courses.remove(c);
	}

	/**
	 * Returns an array of Courses the faculty is teaching.
	 * 
	 * @return an array of Courses the faculty is teaching.
	 */
	@Override
	public Course[] getCourses() {
		Course[] cs = new Course[courses.size()];
		return courses.toArray(cs);
	}

	/**
	 * Generates hashCode.
	 * 
	 * @return hashCode
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((courses == null) ? 0 : courses.hashCode());
		result = prime * result + maxCourses;
		return result;
	}

	/**
	 * Compares Faculty Objects and returns true if they are the same.
	 * 
	 * @param obj
	 *            Object to compare
	 * @return true if the Objects are the same
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
		Faculty other = (Faculty) obj;
		if (courses == null) {
			if (other.courses != null)
				return false;
		} else if (!courses.equals(other.courses))
			return false;
		if (maxCourses != other.maxCourses)
			return false;
		return true;
	}

	/**
	 * Returns a String representation of a Faculty for output.
	 * 
	 * @return a String representation of a Faculty
	 */
	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < courses.size(); i++) {
			Course c = courses.get(i);
			s += "," + c.getName();
		}
		return super.toString() + "," + maxCourses + s;
	}

}
