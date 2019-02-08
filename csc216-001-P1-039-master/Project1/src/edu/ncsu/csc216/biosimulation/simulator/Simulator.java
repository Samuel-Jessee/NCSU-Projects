/**
 * 
 */
package edu.ncsu.csc216.biosimulation.simulator;

import edu.ncsu.csc216.biosimulation.domain.Predator;
import edu.ncsu.csc216.biosimulation.domain.Prey;
import edu.ncsu.csc216.biosimulation.domain.Scavenger;
import edu.ncsu.csc216.biosimulation.domain.Species;

/**
 * Oversees the simulation. This class creates the different Species from user
 * information [UC2, S1-2]. The class also steps through the simulation
 * according to [UC3, S1] and reports the current state of the simulation after
 * the final step [UC2, S5].
 * 
 * @author SamuelJessee
 *
 */
public class Simulator {

	/** default initial counts for each Species */
	private static final int[] DEFAULT_INITIAL_COUNTS = { 10, 250, 400 };

	/** Parameters to construct each Species */
	private double[][] speciesParameters = new double[3][];

	/** default parameters for each Species */
	private static final double[][] DEFAULT_PARAMETERS = { Predator.getDefaultParameters(), Prey.getDefaultParameters(),
			Scavenger.getDefaultParameters() };

	/** population counts for each Species */
	private int[] populations = new int[3];

	/** Prey of the simulation */
	private Species victim;

	/** Scavenger of the simulation */
	private Species scrounger;

	/** Predator of the simulation */
	private Species killer;

	/**
	 * Constructs the Simulator with an array of strings representing initial
	 * population counts, and a two dimensional array of strings representing
	 * birth and death rates.
	 * 
	 * @param counts
	 *            array of initial population counts
	 * @param parms
	 *            arrays containing birth and death rates
	 */
	public Simulator(String[] counts, String[][] parms) {
		this.readCounts(counts);
		this.validateCounts();
		this.speciesParameters[0] = this.readParms(parms[0]);
		this.speciesParameters[1] = this.readParms(parms[1]);
		this.speciesParameters[2] = this.readParms(parms[2]);
		this.validateParms(this.speciesParameters[0]);
		this.validateParms(this.speciesParameters[1]);
		this.validateParms(this.speciesParameters[2]);
		this.instantiateSpecies();
	}

	/**
	 * Returns a two-dimensional array of doubles, which is structured exactly
	 * like the two dimensional array of Strings that is the constructor
	 * parameter, except the elements are doubles instead of String equivalents.
	 * See Table 1(Default Value), [UC2, S1, S2], [UC5] from Part 1.
	 * 
	 * @return two-dimensional array containing default simulation values
	 */
	public static double[][] getDefaultParameters() {
		return DEFAULT_PARAMETERS;
	}

	/**
	 * Returns an array of three ints, which are the initial population count
	 * defaults. The first element corresponds to predator, the second to prey,
	 * and the third to scavenger. See Table 1(Default Value), [UC2, S1, S2],
	 * [UC5] from Part 1.
	 * 
	 * @return array of default initial population counts
	 */
	public static int[] getDefaultInitialCounts() {
		return DEFAULT_INITIAL_COUNTS;
	}

	/**
	 * Checks if any population counts are negative.
	 */
	private void validateCounts() {
		if (this.populations[0] < 0 || this.populations[1] < 0 || this.populations[2] < 0) {
			throw new IllegalArgumentException("Population counts cannot be negative.");
		}
	}

	/**
	 * Reads through the counts array and attempts to parse each value to an
	 * int, and assign to population. If counts contains a non-int element, an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param counts
	 *            array of population counts as Strings
	 */
	private void readCounts(String[] counts) {
		try {
			this.populations[0] = Integer.parseInt(counts[0]);
			this.populations[1] = Integer.parseInt(counts[1]);
			this.populations[2] = Integer.parseInt(counts[2]);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Initial population counts must be integers.");
		}
	}

	/**
	 * Reads through an array of rates and parses the rates to doubles. Any
	 * non-double element causes an IllegalArgumentException to be thrown.
	 * Returns an array of rates for a single species.
	 * 
	 * @param parms
	 *            array of rates as Strings
	 * @return array of rates for a single Species
	 */
	private double[] readParms(String[] parms) {
		/** array of rates for a single species */
		double[] rates = new double[parms.length];
		try {
			for (int i = 0; i < parms.length; i++) {
				rates[i] = Double.parseDouble(parms[i]);
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Birth/death rates must be numbers.");
		}
		return rates;
	}

	/**
	 * Goes through an array of rates for a single Species and checks that each
	 * rate is between 0 and 1.
	 * 
	 * @param parms
	 *            array of rates for a Species
	 */
	private void validateParms(double[] parms) {
		for (int i = 0; i < parms.length; i++) {
			if (parms[i] < 0 || parms[i] > 1) {
				throw new IllegalArgumentException("Birth/death rates must be between 0 and 1.");
			}
		}
	}

	/**
	 * Uses values from population and speciesParameters to instantiate the
	 * Species needed for the simulation.
	 */
	private void instantiateSpecies() {
		this.killer = new Predator(this.populations[0], this.speciesParameters[0][0], this.speciesParameters[0][1]);
		this.victim = new Prey(this.populations[1], this.speciesParameters[1][0], this.speciesParameters[1][1]);
		this.scrounger = new Scavenger(this.populations[2], this.speciesParameters[2][0], this.speciesParameters[2][1],
				this.speciesParameters[2][2], this.speciesParameters[2][3]);
		this.killer.registerPrey(victim);
		this.victim.registerPredator(killer);
		this.scrounger.registerPrey(victim);
		this.scrounger.registerPredator(killer);
	}

	/**
	 * Returns an array of 3 ints, which are the current populations of the
	 * species (predator, prey, scavenger in order) [UC3, S1], [UC2, S5].
	 * 
	 * @return array of current population counts
	 */
	public int[] getPopulations() {
		return this.populations;
	}

	/**
	 * Runs a single steps of the simulation [UC4]. Note that the count for each
	 * population is updated as a double rather than an int.
	 */
	public void step() {
		/** Predator projected population */
		double projectedPred = this.killer.getProjectedPopulation();
		/** Prey projected population */
		double projectedPrey = this.victim.getProjectedPopulation();
		/** Scavenger projected population */
		double projectedScav = this.scrounger.getProjectedPopulation();
		this.killer.setCount(projectedPred);
		this.victim.setCount(projectedPrey);
		this.scrounger.setCount(projectedScav);
		this.populations[0] = (int) projectedPred + 1;
		this.populations[1] = (int) projectedPrey + 1;
		this.populations[2] = (int) projectedScav + 1;
	}
}