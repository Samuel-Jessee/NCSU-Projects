/**
 * 
 */
package edu.ncsu.csc216.course_manager.exceptions;

/**
 * @author Gene
 *
 */
public class StudentAlreadyEnrolledException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public StudentAlreadyEnrolledException() {
		super("Student is already enrolled in the Course.");
	}
	
}
