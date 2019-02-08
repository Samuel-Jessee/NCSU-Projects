/**
 * 
 */
package edu.ncsu.csc216.biosimulation.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests Scavenger class.
 * 
 * @author SamuelJessee
 *
 */
public class ScavengerTest {

	/** default initial population count */
	private static final int COUNT = 400;

	/** default initial population birth rate */
	private static final double BIRTH_KILL = 0.000002;

	/** default initial population death rate */
	private static final double DEATH_RATE = 0.1;

	/** default birth rate from Prey carcasses */
	private static final double BIRTH_PREY = 0.0006;

	/** default birth rate from Predator carcasses */
	private static final double BIRTH_PRED = 0.0003;

	/** Scavenger object */
	private Scavenger scav;

	/** Prey object */
	private Species prey;

	/** Predator object */
	private Species pred;

	/** prey count */
	double x = 250;
	/** predator count */
	double y = 10;
	/** scavenger count */
	double z = 400;
	/** scavenger death rate */
	double e = 0.1;
	/** scavenger birth rate from kills */
	double f = 0.000002;
	/** scavenger birth rate from prey */
	double g = 0.0006;
	/** scavenger birth rate from predators */
	double h = 0.0003;
	/** scavenger population limiting factor */
	double i = 0.001;
	/** population change */
	double change = (-1 * e * z) + (f * x * y * z) + (g * x * z) + (h * y * z) - (i * z * z);

	/**
	 * Constructs Scavenger, Predator, and Prey objects with default values.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		scav = new Scavenger(COUNT, BIRTH_KILL, DEATH_RATE, BIRTH_PREY, BIRTH_PRED);
		prey = new Prey(250, 0.165, 0.0006);
		pred = new Predator(10, 0.00068, 0.23);
	}

	/**
	 * Sets scav, pred, and prey to null, ensuring nothing is carried over
	 * between tests.
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		scav = null;
		prey = null;
		pred = null;
	}

	/**
	 * Test method for getProjectedPopulation().
	 */
	@Test
	public void testGetProjectedPopulation() {
		scav.registerPredator(pred);
		scav.registerPrey(prey);
		assertTrue(COUNT + change == scav.getProjectedPopulation());
	}

	/**
	 * Test method for registerPredator(Species).
	 */
	@Test
	public void testRegisterPredator() {
		scav.registerPredator(pred);
		assertEquals(pred, scav.getPredator());
	}

	/**
	 * Test method for registerPrey(Species).
	 */
	@Test
	public void testRegisterPrey() {
		scav.registerPrey(prey);
		assertEquals(prey, scav.getPrey());
	}

	/**
	 * Test method for getPredator().
	 */
	@Test
	public void testGetPredator() {
		scav.registerPredator(pred);
		assertEquals(pred, scav.getPredator());
	}

	/**
	 * Test method for getPrey().
	 */
	@Test
	public void testGetPrey() {
		scav.registerPrey(prey);
		assertEquals(prey, scav.getPrey());
	}

	/**
	 * Test method for Scavenger(int, double, double, double, double).
	 */
	@Test
	public void testScavenger() {
		assertTrue(COUNT == scav.getCount());
		assertTrue(BIRTH_KILL == scav.getBirthRate());
		assertTrue(DEATH_RATE == scav.getDeathRate());

		// negative count
		try {
			scav = new Scavenger(-1, BIRTH_KILL, DEATH_RATE, BIRTH_PREY, BIRTH_PRED);
			fail("An IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertTrue(COUNT == scav.getCount());
		}

		// too high count
		scav = new Scavenger(501, BIRTH_KILL, DEATH_RATE, BIRTH_PREY, BIRTH_PRED);
		assertTrue(500 == scav.getCount());

		// too low count
		scav = new Scavenger(3, BIRTH_KILL, DEATH_RATE, BIRTH_PREY, BIRTH_PRED);
		assertTrue(4 == scav.getCount());

		// negative rate
		try {
			scav = new Scavenger(COUNT, -1, DEATH_RATE, BIRTH_PREY, BIRTH_PRED);
			fail("An IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertTrue(BIRTH_KILL == scav.getBirthRate());
		}
		try {
			scav = new Scavenger(COUNT, BIRTH_KILL, -1, BIRTH_PREY, BIRTH_PRED);
			fail("An IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertTrue(DEATH_RATE == scav.getDeathRate());
		}

		// too high rate
		try {
			scav = new Scavenger(COUNT, 1.01, DEATH_RATE, BIRTH_PREY, BIRTH_PRED);
			fail("An IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertTrue(BIRTH_KILL == scav.getBirthRate());
		}
		try {
			scav = new Scavenger(COUNT, BIRTH_KILL, 1.01, BIRTH_PREY, BIRTH_PRED);
			fail("An IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertTrue(DEATH_RATE == scav.getDeathRate());
		}
	}

	/**
	 * Test method for getDefaultParameters().
	 */
	@Test
	public void testGetDefaultParameters() {
		double[] defaults = Scavenger.getDefaultParameters();
		assertTrue(4 == defaults.length);
		assertTrue(BIRTH_KILL == defaults[0]);
		assertTrue(DEATH_RATE == defaults[1]);
		assertTrue(BIRTH_PREY == defaults[2]);
		assertTrue(BIRTH_PRED == defaults[3]);
	}
}