package edu.ncsu.csc316.file_compressor.exception;

/**
 * Checked exception for receiving an incorrect file type.
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
public class InvalidInputFileTypeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Exception for incorrect file types.
	 */
	public InvalidInputFileTypeException() {
		super("Incorrect file type.");
	}

}