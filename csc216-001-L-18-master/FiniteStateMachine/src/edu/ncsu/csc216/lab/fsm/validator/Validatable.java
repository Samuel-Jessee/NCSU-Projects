package edu.ncsu.csc216.lab.fsm.validator;

import edu.ncsu.csc216.lab.fsm.exception.InvalidFSMTransitionException;

/**
 * Validator interface:
 * Any validator object must implement this interface
 * to have an isValid() method
 * @author Jason King
 *
 */
public interface Validatable {

	/**
	 * Returns true if the given input String is valid.
	 * Returns false if the given input String is invalid.
	 * @return true if the input String is valid
	 * @throws InvalidFSMTransitionException if an attempted transition is illegal
	 */
	boolean isValid(String input) throws InvalidFSMTransitionException;
	
}
