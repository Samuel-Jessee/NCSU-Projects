/**
 * 
 */
package edu.ncsu.csc216.biosimulation.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests Prey class.
 * 
 * @author SamuelJessee
 *
 */
public class PreyTest {

	/** default initial population count */
	private static final int COUNT = 250;

	/** default initial population birth rate */
	private static final double BIRTH_RATE = 0.165;

	/** default initial population death rate */
	private static final double DEATH_RATE = 0.0006;

	/** Prey object */
	private Prey prey;

	/** Predator object */
	private Species pred;

	/** prey count */
	double x = 250;
	/** prey birth rate */
	double a = 0.165;
	/** prey death rate */
	double b = 0.0006;
	/** predator count */
	double y = 10;
	/** population change */
	double change = (a * x) - (b * x * y);

	/**
	 * Constructs new Predator and Prey objects using default initial values.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		prey = new Prey(COUNT, BIRTH_RATE, DEATH_RATE);
		pred = new Predator(10, 0.00068, 0.23);
	}

	/**
	 * Sets Prey p and Predator s to null, ensuring nothing is carried over
	 * between tests.
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		prey = null;
		pred = null;
	}

	/**
	 * Test method for getProjectedPopulation().
	 */
	@Test
	public void testGetProjectedPopulation() {
		prey.registerPredator(pred);
		assertTrue(COUNT + change == prey.getProjectedPopulation());
	}

	/**
	 * Test method for registerPredator(Species).
	 */
	@Test
	public void testRegisterPredator() {
		prey.registerPredator(pred);
		assertEquals(pred, prey.getPredator());
	}

	/**
	 * Test method for registerPrey(Species).
	 */
	@Test
	public void testRegisterPrey() {
		prey.registerPrey(prey);
		assertEquals(prey, prey.getPrey());
	}

	/**
	 * Test method for getPredator().
	 */
	@Test
	public void testGetPredator() {
		prey.registerPredator(pred);
		assertEquals(pred, prey.getPredator());
	}

	/**
	 * Test method for getPrey().
	 */
	@Test
	public void testGetPrey() {
		prey.registerPrey(prey);
		assertEquals(prey, prey.getPrey());
	}

	/**
	 * Test method for Prey(int, double, double).
	 */
	@Test
	public void testPrey() {
		assertTrue(COUNT == prey.getCount());
		assertTrue(BIRTH_RATE == prey.getBirthRate());
		assertTrue(DEATH_RATE == prey.getDeathRate());

		// negative count
		try {
			prey = new Prey(-1, BIRTH_RATE, DEATH_RATE);
			fail("An IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertTrue(COUNT == prey.getCount());
		}

		// too high count
		prey = new Prey(501, BIRTH_RATE, DEATH_RATE);
		assertTrue(500 == prey.getCount());

		// negative rate
		try {
			prey = new Prey(COUNT, -1, DEATH_RATE);
			fail("An IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertTrue(BIRTH_RATE == prey.getBirthRate());
		}
		try {
			prey = new Prey(COUNT, BIRTH_RATE, -1);
			fail("An IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertTrue(DEATH_RATE == prey.getDeathRate());
		}

		// too high rate
		try {
			prey = new Prey(COUNT, 1.01, DEATH_RATE);
			fail("An IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertTrue(BIRTH_RATE == prey.getBirthRate());
		}
		try {
			prey = new Prey(COUNT, BIRTH_RATE, 1.01);
			fail("An IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertTrue(DEATH_RATE == prey.getDeathRate());
		}
	}

	/**
	 * Test method for getDefaultParameters().
	 */
	@Test
	public void testGetDefaultParameters() {
		double[] defaults = Prey.getDefaultParameters();
		assertTrue(2 == defaults.length);
		assertTrue(BIRTH_RATE == defaults[0]);
		assertTrue(DEATH_RATE == defaults[1]);
	}
}