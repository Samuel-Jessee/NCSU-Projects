/**
 * 
 */
package edu.ncsu.csc216.course_manager.exceptions;

/**
 * @author Gene
 *
 */
public class DuplicateCourseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DuplicateCourseException() {
		super("Course already exists in the system.");
	}
	
}
