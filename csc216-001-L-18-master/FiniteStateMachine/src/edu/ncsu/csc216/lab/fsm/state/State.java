package edu.ncsu.csc216.lab.fsm.state;

import edu.ncsu.csc216.lab.fsm.exception.InvalidFSMTransitionException;

public interface State {
	
	//Three possible input values
	public void onNumber() throws InvalidFSMTransitionException;
	public void onLetter() throws InvalidFSMTransitionException;
	
	/**
	 * Default exception throw for all input that is not a number or a letter
	 * @throws InvalidFSMTransitionException 
	 */
	public default void onOther() throws InvalidFSMTransitionException {
		throw new InvalidFSMTransitionException("Course name can only contain letters and digits.");
	}
}
