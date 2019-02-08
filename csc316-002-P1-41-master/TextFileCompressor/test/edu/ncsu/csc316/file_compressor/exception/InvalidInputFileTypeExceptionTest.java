/**
 * 
 */
package edu.ncsu.csc316.file_compressor.exception;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests InvalidInputFileTypeException
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
public class InvalidInputFileTypeExceptionTest {

	/** error message */
	public static final String ERROR_MESSAGE = "Incorrect file type.";

	/**
	 * Test method for
	 * {@link edu.ncsu.csc316.file_compressor.exception.InvalidInputFileTypeException#InvalidInputFileTypeException()}
	 * .
	 */
	@Test
	public void testInvalidInputFileTypeException() {
		try {
			throw new InvalidInputFileTypeException();
		} catch (InvalidInputFileTypeException e) {
			assertEquals(ERROR_MESSAGE, e.getMessage());
		}
	}

}