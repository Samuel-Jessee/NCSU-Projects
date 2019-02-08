package edu.ncsu.csc216.lab.fsm.validator;

import edu.ncsu.csc216.lab.fsm.exception.InvalidFSMTransitionException;
import edu.ncsu.csc216.lab.fsm.state.State;

public class CourseNameValidator_StatePattern implements Validatable {

	/** Initial state before input is examined */
	private final State STATE_INITIAL = new Initial();
	
	/** State at which one letter has been identified */
	private final State STATE_L = new L();
	
	/** State at which two letters have been identified */
	private final State STATE_LL = new LL();
	
	/** State at which three letters have been identified */
	private final State STATE_LLL = new LLL();
	
	/** State at which one digit has been identified */
	private final State STATE_D = new D();
	
	/** State at which two digits have been identified */
	private final State STATE_DD = new DD();
	
	/** State at which three digits have been identified */
	private final State STATE_DDD = new DDD();
	
	/** State at which a suffix letter has been identified */
	private final State STATE_SUFFIX = new Suffix();
	
	/** The state variable keeps track of the current FSM state we are in */
	private State state = STATE_INITIAL;
	
	@Override
	public boolean isValid(String input) throws InvalidFSMTransitionException {
		try {
			int i = 0;
			char ch;
			while(i < input.length()) {
				ch = input.charAt(i);
				System.out.println(ch);
				if(Character.isAlphabetic(ch))
					state.onLetter();
				else if(Character.isDigit(ch))
					state.onNumber();
				else
					state.onOther();
				i++;
			}
		} catch (InvalidFSMTransitionException e) {
			state = STATE_INITIAL;
			throw new InvalidFSMTransitionException(e.getMessage());
		}
		state = STATE_INITIAL;
		return true;
	}
	
	/**
	 * 
	 * Method used when the FSM is in its initial state. This throws and exception if it tries
	 * to transition out of Initial with a digit and changes the state to L if it starts with a letter
	 */
	private class Initial implements State{

		@Override
		public void onNumber() throws InvalidFSMTransitionException {
			throw new InvalidFSMTransitionException("Course name must start with a letter.");
		}

		@Override
		public void onLetter() {
			state = STATE_L;
		}
	}
	
	/**
	 * Method is called if the current state of the FSM is L. It changes the state to either
	 * D or LL based on if a digit or letter is read next.
	 */
	private class L implements State{

		@Override
		public void onNumber() {
			state = STATE_D;
		}

		@Override
		public void onLetter() {
			state = STATE_LL;
		}
	}
	
	/**
	 * Method is called if the current state of the FSM is LL. It changes the state to either
	 * D or LLL based on if a digit or letter is read next.
	 */
	private class LL implements State{

		@Override
		public void onNumber() {
			state = STATE_D;
		}

		@Override
		public void onLetter() {
			state = STATE_LLL; 
		}
	}
	
	/**
	 * Method is called if the current state of the FSM is LLL. It changes the state D if the
	 * next input is a digit. Throws an exception with custom message if next input is a letter
	 */
	private class LLL implements State{

		@Override
		public void onNumber() {
			state = STATE_D;
		}

		@Override
		public void onLetter() throws InvalidFSMTransitionException {
			throw new InvalidFSMTransitionException("Course name cannot start with more than 3 letters.");
		}
	}
	
	/**
	 * Method is called if the current state of the FSM is D. It changes the state to DD if next
	 * input is a digit. Throws an exception if next input is letter
	 */
	private class D implements State{

		@Override
		public void onNumber() {
			state = STATE_DD;
		}

		@Override
		public void onLetter() throws InvalidFSMTransitionException {
			throw new InvalidFSMTransitionException("Course name must have 3 digits.");
		}
	}
	
	/**
	 * Method is called if the current state of the FSM is DD. It changes the state to DDD if next
	 * input is a digit. Throws an exception if next input is letter
	 */
	private class DD implements State{

		@Override
		public void onNumber() {
			state = STATE_DDD;
		}

		@Override
		public void onLetter() throws InvalidFSMTransitionException {
			throw new InvalidFSMTransitionException("Course name must have 3 digits.");
		}
	}
	
	/**
	 * Method is called if the current state of the FSM is DDD. It changes the state to Suffix if next
	 * input is a letter. Throws an exception if next input is a digit.
	 */
	private class DDD implements State{

		@Override
		public void onNumber() throws InvalidFSMTransitionException {
			throw new InvalidFSMTransitionException("Course name can only have 3 digits.");
		}

		@Override
		public void onLetter() {
			state = STATE_SUFFIX;
		}
	}
	
	/**
	 * Method is called if the current state of the FSM is Suffix. Throws an exception if there is another
	 * input following the suffix
	 */
	private class Suffix implements State{

		@Override
		public void onNumber() throws InvalidFSMTransitionException {
			throw new InvalidFSMTransitionException("Course name cannot contain digits after the suffix.");
		}

		@Override
		public void onLetter() throws InvalidFSMTransitionException {
			throw new InvalidFSMTransitionException("Course name can only have a 1 letter suffix.");
		}
	}
}
