/**
 * 
 */
package edu.ncsu.csc216.biosimulation.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests Predator class.
 * 
 * @author SamuelJessee
 *
 */
public class PredatorTest {

	/** default initial population count */
	private static final int COUNT = 10;

	/** default initial population birth rate */
	private static final double BIRTH_RATE = 0.00068;

	/** default initial population death rate */
	private static final double DEATH_RATE = 0.23;

	/** Predator object */
	private Predator pred;

	/** Prey object */
	private Species prey;

	/** prey count */
	double x = 250;
	/** predator count */
	double y = 10;
	/** predator death rate */
	double c = 0.23;
	/** predator birth rate */
	double p = 0.00068;
	/** population change */
	double change = (-1 * c * y) + (p * x * y);

	/**
	 * Constructs new Predator and Prey objects using default initial values.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		pred = new Predator(COUNT, BIRTH_RATE, DEATH_RATE);
		prey = new Prey(250, 0.165, 0.0006);
	}

	/**
	 * Sets Predator p and Prey s to null, ensuring nothing is carried over
	 * between tests.
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		pred = null;
		prey = null;
	}

	/**
	 * Test method for getProjectedPopulation().
	 */
	@Test
	public void testGetProjectedPopulation() {
		pred.registerPrey(prey);
		assertTrue(COUNT + change == pred.getProjectedPopulation());
	}

	/**
	 * Test method for registerPredator(Species).
	 */
	@Test
	public void testRegisterPredator() {
		pred.registerPredator(pred);
		assertEquals(pred, pred.getPredator());
	}

	/**
	 * Test method for registerPrey(Species).
	 */
	@Test
	public void testRegisterPrey() {
		pred.registerPrey(prey);
		assertEquals(prey, pred.getPrey());
	}

	/**
	 * Test method for getPredator().
	 */
	@Test
	public void testGetPredator() {
		pred.registerPredator(pred);
		assertEquals(pred, pred.getPredator());
	}

	/**
	 * Test method for getPrey().
	 */
	@Test
	public void testGetPrey() {
		pred.registerPrey(prey);
		assertEquals(prey, pred.getPrey());
	}

	/**
	 * Test method for Predator(int, double, double).
	 */
	@Test
	public void testPredator() {
		assertTrue(COUNT == pred.getCount());
		assertTrue(BIRTH_RATE == pred.getBirthRate());
		assertTrue(DEATH_RATE == pred.getDeathRate());

		// negative count
		try {
			pred = new Predator(-1, BIRTH_RATE, DEATH_RATE);
			fail("An IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertTrue(COUNT == pred.getCount());
		}

		// too high count
		pred = new Predator(301, BIRTH_RATE, DEATH_RATE);
		assertTrue(300 == pred.getCount());

		// negative rate
		try {
			pred = new Predator(COUNT, -1, DEATH_RATE);
			fail("An IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertTrue(BIRTH_RATE == pred.getBirthRate());
		}
		try {
			pred = new Predator(COUNT, BIRTH_RATE, -1);
			fail("An IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertTrue(DEATH_RATE == pred.getDeathRate());
		}

		// too high rate
		try {
			pred = new Predator(COUNT, 1.01, DEATH_RATE);
			fail("An IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertTrue(BIRTH_RATE == pred.getBirthRate());
		}
		try {
			pred = new Predator(COUNT, BIRTH_RATE, 1.01);
			fail("An IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertTrue(DEATH_RATE == pred.getDeathRate());
		}
	}

	/**
	 * Test method for getDefaultParameters().
	 */
	@Test
	public void testGetDefaultParameters() {
		double[] defaults = Predator.getDefaultParameters();
		assertTrue(2 == defaults.length);
		assertTrue(BIRTH_RATE == defaults[0]);
		assertTrue(DEATH_RATE == defaults[1]);
	}
}