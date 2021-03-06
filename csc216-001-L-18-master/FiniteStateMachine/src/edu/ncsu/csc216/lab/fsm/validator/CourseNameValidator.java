package edu.ncsu.csc216.lab.fsm.validator;

import edu.ncsu.csc216.lab.fsm.exception.InvalidFSMTransitionException;

/**
 * Finite State Machine for checking whether
 * a Course Name is valid
 * 
 * @author Jason King
 */
public class CourseNameValidator implements Validatable {

	/** Initial state before input is examined */
	private final int STATE_INITIAL = 0;
	
	/** State at which one letter has been identified */
	private final int STATE_L = 1;
	
	/** State at which two letters have been identified */
	private final int STATE_LL = 2;
	
	/** State at which three letters have been identified */
	private final int STATE_LLL = 3;
	
	/** State at which one digit has been identified */
	private final int STATE_D = 4;
	
	/** State at which two digits have been identified */
	private final int STATE_DD = 5;
	
	/** State at which three digits have been identified */
	private final int STATE_DDD = 6;
	
	/** State at which a suffix letter has been identified */
	private final int STATE_SUFFIX = 7;
	
	/** The state variable keeps track of the current FSM state we are in */
	private int state;
	
	/** This variable tracks whether the input string is in a valid final/end state */
	private boolean validEndState;
	
	/**
	 * Returns true if the course name is valid, based on
	 * a string matching Finite State Machine.
	 * 
	 * The course name must match the following format:
	 *      (1-3 letters)(3 digits)(optionally, a 1 letter suffix)
	 *      
	 * @param courseName the name of the course
	 * @return true if the course name is valid, or false if the course name is invalid
	 * @throws InvalidFSMTransitionException when the FSM attempts an invalid transition
	 */
	public boolean isValid(String courseName) throws InvalidFSMTransitionException
	{
		// Set the state field to be the initial FSM state
		state = STATE_INITIAL;
		
		// Create a variable to track the current character index
		int charIndex = 0;
		
		// Variable to keep track of the current input character being examined
		char c;
		
		// Boolean variable to keep track of whether the course name is valid or not
		validEndState = false;
		
		// Iterate through the ID, examining one character at a time
		while(charIndex < courseName.length())
		{
			// Set the current character being examined
			c = courseName.charAt(charIndex);
			
			// Throw an exception if the string contains non alphanumeric characters
			if(!Character.isLetter(c) && !Character.isDigit(c))
			{
				throw new InvalidFSMTransitionException("Course name can only contain letters and digits.");
			}
			
			// Use a switch statement for the current state
			switch(state)
			{
				case STATE_INITIAL:
					if(Character.isLetter(c))
					{
						state = STATE_L;
					}
					else if(Character.isDigit(c))
					{
						throw new InvalidFSMTransitionException("Course name must start with a letter.");
					}
					break;
				
				case STATE_L:
					if(Character.isLetter(c))
					{
						state = STATE_LL;
					}
					else if (Character.isDigit(c))
					{
						state = STATE_D;
					}
					break;
					
				case STATE_LL:
					if(Character.isLetter(c))
					{
						state = STATE_LLL;
					}
					else if (Character.isDigit(c))
					{
						state = STATE_D;
					}
					break;
					
				case STATE_LLL:
					if(Character.isLetter(c))
					{
						throw new InvalidFSMTransitionException("Course name cannot start with more than 3 letters.");
					}
					else if (Character.isDigit(c))
					{
						state = STATE_D;
					}
					break;
					
				case STATE_D:
					if(Character.isLetter(c))
					{
						throw new InvalidFSMTransitionException("Course name must have 3 digits.");
					}
					else if (Character.isDigit(c))
					{
						state = STATE_DD;
					}
					break;
					
				case STATE_DD:
					if(Character.isLetter(c))
					{
						throw new InvalidFSMTransitionException("Course name must have 3 digits.");
					}
					else if (Character.isDigit(c))
					{
						state = STATE_DDD;
						validEndState = true;
					}
					break;
					
				case STATE_DDD:
					if(Character.isLetter(c))
					{
						state = STATE_SUFFIX;
						validEndState = true;
					}
					else if (Character.isDigit(c))
					{
						throw new InvalidFSMTransitionException("Course name can only have 3 digits.");
					}
					break;
					
				case STATE_SUFFIX:
					if(Character.isLetter(c))
					{
						throw new InvalidFSMTransitionException("Course name can only have a 1 letter suffix.");
					}
					else if (Character.isDigit(c))
					{
						throw new InvalidFSMTransitionException("Course name cannot contain digits after the suffix.");
					}
					break;
			}
			
			charIndex++;

		}
		
		return validEndState;	
	}

}
