package edu.ncsu.csc216.lab.fsm.exception;

@SuppressWarnings("serial")
public class InvalidFSMTransitionException extends Exception{
	
	private String message;

	/**
	 * Default Constructor
	 */
	public InvalidFSMTransitionException() {
		message = "Invalid FSM Transition.";
	}
	
	/**
	 * Throws custom exception message
	 * @param message Message to throw
	 */
	public InvalidFSMTransitionException(String message) {
		this.message = message;
	}
	
	/**
	 * Returns message
	 */
	public String getMessage() {
		return message;
	}
	

}
