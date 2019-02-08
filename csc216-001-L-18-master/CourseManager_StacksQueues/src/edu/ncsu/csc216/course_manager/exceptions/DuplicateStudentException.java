/**
 * 
 */
package edu.ncsu.csc216.course_manager.exceptions;

/**
 * @author Gene
 *
 */
public class DuplicateStudentException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DuplicateStudentException() {
		super("Student already exists in the system.");
	}
	
}
