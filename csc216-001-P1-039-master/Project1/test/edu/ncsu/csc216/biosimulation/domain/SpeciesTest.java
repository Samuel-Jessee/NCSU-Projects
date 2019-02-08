/**
 * 
 */
package edu.ncsu.csc216.biosimulation.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests Species abstract class.
 * 
 * @author SamuelJessee
 *
 */
public class SpeciesTest {

	/** default initial population count */
	private static final int COUNT = 10;

	/** default initial population birth rate */
	private static final double BIRTH_RATE = 0.00068;

	/** default initial population death rate */
	private static final double DEATH_RATE = 0.23;

	/** Species object */
	private Species s;

	/**
	 * Constructs a new Species object using default initial values.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		s = new Predator(COUNT, BIRTH_RATE, DEATH_RATE);
	}

	/**
	 * Sets Species object s to null, ensuring that nothing is carried over
	 * between tests.
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		s = null;
	}

	/**
	 * Test method for getDeathRate().
	 */
	@Test
	public void testGetDeathRate() {
		assertTrue(DEATH_RATE == s.getDeathRate());
	}

	/**
	 * Test method for getBirthRate().
	 */
	@Test
	public void testGetBirthRate() {
		assertTrue(BIRTH_RATE == s.getBirthRate());
	}

	/**
	 * Test method for getCount().
	 */
	@Test
	public void testGetCount() {
		assertTrue(COUNT == s.getCount());
	}

	/**
	 * Test method for setCount(double).
	 */
	@Test
	public void testSetCount() {
		s.setCount(52.1);
		assertTrue(52.1 == s.getCount());
		try {
			s.setCount(-1);
			fail("An IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertTrue(52.1 == s.getCount());
		}
	}
}