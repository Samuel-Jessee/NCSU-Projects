package edu.ncsu.csc.itrust.exception;

/**
 * This exception is thrown to indicate an error occurring when adding patient
 * fitness information.
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
public class AddFitnessException extends Exception {

	/** Unique identifier for the exception */
	private static final long serialVersionUID = 6746505526582373463L;

	/** The error message for the exception */
	private String message;

	/**
	 * Constructor initializing the error message string
	 * 
	 * @param string
	 *            The error message string
	 */
	public AddFitnessException(String string) {
		message = string;
	}

	/**
	 * Returns the exception's error message
	 * 
	 * @return The error message from the exception
	 */
	public String getMessage() {
		return message;
	}
}
