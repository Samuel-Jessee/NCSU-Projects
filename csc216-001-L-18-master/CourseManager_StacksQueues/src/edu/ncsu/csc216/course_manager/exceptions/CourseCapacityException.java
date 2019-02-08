/**
 * 
 */
package edu.ncsu.csc216.course_manager.exceptions;

/**
 * @author Gene
 *
 */
public class CourseCapacityException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CourseCapacityException() {
		super("Course is at capacity.");
	}
	
}
