package edu.ncsu.csc216.lab.fsm.validator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.lab.fsm.exception.InvalidFSMTransitionException;

/**
 * Tests the CourseNameValidator class
 * @author Jason King
 *
 */
public class CourseNameValidator_StatePatternTest {

	/** Keeps track of the CourseNameValidator object */
	private Validatable validator;
	
	/**
	 * Before each test case executes,
	 * initializes the validator object to a new CourseNameValidator
	 */
	@Before
	public void setUp()
	{
		validator = new CourseNameValidator_StatePattern();
	}
	
	/**
	 * Tests the isValid method
	 * finite state machine
	 */
	@Test
	public void testIsValid()
	{	
		// STATE_INITIAL
		// Transition: Letter
		try
		{
			assertTrue(validator.isValid("C116"));
		} catch(InvalidFSMTransitionException e)
		{
			fail("STATE_INITIAL, letter: A valid course name was provided.");
		}
		
		// STATE_INITIAL
		// Transition: Digit
		try
		{
			validator.isValid("116");
			fail("STATE_INITIAL, digit: Course name cannot start with a digit.");
		} catch(InvalidFSMTransitionException e)
		{
			assertEquals("Course name must start with a letter.", e.getMessage());
		}
		
		// STATE_INITIAL
		// Transition: Symbol
		try
		{
			validator.isValid("!CSC116");
			fail("STATE_INITIAL, symbol: Course name can only contain letters and digits.");
		} catch(InvalidFSMTransitionException e)
		{
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
		
		
		
		
		
		//TODO create the rest of the tests for the isValid() method
		//STATE_L
		//Transition: Letter
		try {
			assertTrue(validator.isValid("CS215"));
		} catch(InvalidFSMTransitionException e) {
			fail("STATE_L, Letter: A valid course name was provided");
		}
		
		//STATE_L
		//Transition: Symbol
		try {
			validator.isValid("C!215");
			fail("STATE_L, symbol: Course name can only contain letters and digits.");
		} catch(InvalidFSMTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
		
		//STATE_LL
		//Transition: Letter
		try {
			assertTrue(validator.isValid("CSC216"));
		} catch(InvalidFSMTransitionException e) {
			fail("STATE_LL, Letter: A valid course name was provided.");
		}
		
		//STATE_LL
		//Transition: Symbol
		try {
			validator.isValid("CS!215");
			fail("STATE_LL, symbol: Course name can only contain letters and digits.");
		} catch(InvalidFSMTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
		
		//STATE_LLL
		///Transition: Letter
		try {
			validator.isValid("CSCS216");
			fail("STATE_LLL, letter: Course name can only contain 3 letters max.");
		} catch(InvalidFSMTransitionException e) {
			assertEquals("Course name cannot start with more than 3 letters.", e.getMessage());
		}
		
		//STATE_LLL
		//Transition: Symbol
		try {
			validator.isValid("CSC!215");
			fail("STATE_LL, symbol: Course name can only contain letters and digits.");
		} catch(InvalidFSMTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
		
		//STATE_D
		//Transition: Letter
		try {
			validator.isValid("CS2C15");
			fail("STATE_LL, letter: Course name must contain 3 consecutive numbers.");
		} catch(InvalidFSMTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());
		}
		
		//STATE_D
		//Transition: Symbol
		try {
			validator.isValid("CSC2!15");
			fail("STATE_LL, symbol: Course name can only contain letters and digits.");
		} catch(InvalidFSMTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
		
		//STATE_DD
		//Transition: Letter
		try {
			validator.isValid("CS21C5");
			fail("STATE_LL, letter: Course name must contain 3 consecutive numbers.");
		} catch(InvalidFSMTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());
		}
		
		//STATE_DD
		//Transition: Symbol
		try {
			validator.isValid("CSC21!5");
			fail("STATE_LL, symbol: Course name can only contain letters and digits.");
		} catch(InvalidFSMTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
		
		//STATE_DDD
		//Transition: Letter
		try {
			assertTrue(validator.isValid("CSC216L"));
		}catch (InvalidFSMTransitionException e) {
			fail("STATE_DDD, Letter: A valid course name was provided.");
		}
		
		//STATE_DDD
		//Transition: Digit
		try {
			validator.isValid("CSC2161");
			fail("STATE_DDD, Digit: Course name can only contain 3 digits.");
		} catch(InvalidFSMTransitionException e) {
			assertEquals("Course name can only have 3 digits.", e.getMessage());
		}
		
		//STATE_SUFFIX
		//Transition: Letter
		try {
			validator.isValid("CSC216LL");
			fail("STATE_Suffix, Letter: Course name can only have a one letter suffix");
		} catch(InvalidFSMTransitionException e) {
			assertEquals("Course name can only have a 1 letter suffix.", e.getMessage());
		}
		
		//STATE_SUFFIX
		//Transition: Digit
		try {
			validator.isValid("CSC216L2");
			fail("STATE_SUFFIX, Digit: Course name cannot contain digits after the suffix.");
		} catch(InvalidFSMTransitionException e) {
			assertEquals("Course name cannot contain digits after the suffix.", e.getMessage());
		}
		
		// Test valid course names
		try{
			assertTrue(validator.isValid("CSC216"));
			
			//TODO Create additional valid path tests here
			assertTrue(validator.isValid("CS123"));
			assertTrue(validator.isValid("C112"));
			assertTrue(validator.isValid("CSC216L"));
		} catch(InvalidFSMTransitionException e)
		{
			fail("Valid course names were provided.");
		}
	}

}
