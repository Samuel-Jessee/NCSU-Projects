package edu.ncsu.csc216.lab.fsm.exception;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class InvalidFSMTransitionExceptionTest {

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Tests the construction of the InvalidFSMTransitionException
	 */
	@Test
	public void InvalidFSMTransitionExcpetion() {
		//Test default Constructor
		try {
			if(true)
				throw new InvalidFSMTransitionException();
		} catch(InvalidFSMTransitionException e) {
			assertEquals("Invalid FSM Transition.", e.getMessage());
		}
		
		//Tests secondary Constructor
		try {
			if(true)
				throw new InvalidFSMTransitionException("Failed");
		} catch(InvalidFSMTransitionException e) {
			assertEquals("Failed", e.getMessage());
		}
	}

}
