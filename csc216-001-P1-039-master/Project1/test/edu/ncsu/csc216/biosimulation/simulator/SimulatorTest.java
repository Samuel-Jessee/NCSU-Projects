/**
 * 
 */
package edu.ncsu.csc216.biosimulation.simulator;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests Simulator class.
 * 
 * @author SamuelJessee
 *
 */
public class SimulatorTest {

	/** default initial counts for each Species as ints */
	private static final int[] COUNTS = { 10, 250, 400 };

	/** default initial counts for each Species as Strings */
	private static final String[] S_COUNTS = { "10", "250", "400" };

	/** default parameters for each Species as doubles */
	private static final double[][] PARMS = { { 0.00068, 0.23 }, { 0.165, 0.0006 }, { 0.000002, 0.1, 0.0006, 0.0003 } };

	/** default parameters for each Species as Strings */
	private static final String[][] S_PARMS = { { "0.00068", "0.23" }, { "0.165", "0.0006" },
			{ "0.000002", "0.1", "0.0006", "0.0003" } };

	/** prey count */
	double x = 250;
	/** predator count */
	double y = 10;
	/** predator death rate */
	double c = 0.23;
	/** predator birth rate */
	double p = 0.00068;
	/** population change */
	double changePred = (-1 * c * y) + (p * x * y);
	/** predator projected population */
	double projectedPred = y + changePred;
	/** prey birth rate */
	double a = 0.165;
	/** prey death rate */
	double b = 0.0006;
	/** population change */
	double changePrey = (a * x) - (b * x * y);
	/** prey projected population */
	double projectedPrey = x + changePrey;
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
	double changeScav = (-1 * e * z) + (f * x * y * z) + (g * x * z) + (h * y * z) - (i * z * z);
	/** svavenger projected population */
	double projectedScav = z + changeScav;

	/** Simulator object */
	Simulator sim;

	/**
	 * Constructs Simulator object with default values.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		sim = new Simulator(S_COUNTS, S_PARMS);
	}

	/**
	 * Sets sim to null to ensure nothing carries over between tests.
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		sim = null;
	}

	/**
	 * Test method for Simulator(String[], String[][]).
	 */
	@Test
	public void testSimulator() {
		assertTrue(COUNTS[0] == sim.getPopulations()[0]);
		assertTrue(COUNTS[1] == sim.getPopulations()[1]);
		assertTrue(COUNTS[2] == sim.getPopulations()[2]);
	}

	/**
	 * Test method for getDefaultParameters().
	 */
	@Test
	public void testGetDefaultParameters() {
		for (int t = 0; t < 2; t++) {
			assertTrue(PARMS[0][t] == Simulator.getDefaultParameters()[0][t]);
		}
		for (int j = 0; j < 2; j++) {
			assertTrue(PARMS[1][j] == Simulator.getDefaultParameters()[1][j]);
		}
		for (int k = 0; k < 4; k++) {
			assertTrue(PARMS[2][k] == Simulator.getDefaultParameters()[2][k]);
		}
	}

	/**
	 * Test method for getDefaultInitialCounts().
	 */
	@Test
	public void testGetDefaultInitialCounts() {
		assertTrue(COUNTS[0] == Simulator.getDefaultInitialCounts()[0]);
		assertTrue(COUNTS[1] == Simulator.getDefaultInitialCounts()[1]);
		assertTrue(COUNTS[2] == Simulator.getDefaultInitialCounts()[2]);
	}

	/**
	 * Test method for getPopulations().
	 */
	@Test
	public void testGetPopulations() {
		assertTrue(COUNTS[0] == sim.getPopulations()[0]);
		assertTrue(COUNTS[1] == sim.getPopulations()[1]);
		assertTrue(COUNTS[2] == sim.getPopulations()[2]);
	}

	/**
	 * Test method for step().
	 */
	@Test
	public void testStep() {
		sim.step();
		assertTrue((int) projectedPred + 1 == sim.getPopulations()[0]);
		assertTrue((int) projectedPrey + 1 == sim.getPopulations()[1]);
		assertTrue((int) projectedScav + 1 == sim.getPopulations()[2]);
	}
}