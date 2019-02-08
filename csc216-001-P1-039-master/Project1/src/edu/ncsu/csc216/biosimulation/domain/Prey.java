/**
 * 
 */
package edu.ncsu.csc216.biosimulation.domain;

/**
 * Implements the behavior of the prey species for the simulation, including
 * calculating the projected population according to the second bullet of [UC4,
 * S1].
 * 
 * @author SamuelJessee
 *
 */
public class Prey extends Species {

	/** max Prey count */
	public static final int MAX_PREY = 500;

	/** min Prey count */
	public static final int MIN_PREY = 0;

	/** default initial values */
	private static final double[] DEFAULTS = { 0.165, 0.0006 };

	/** Predator object */
	private Species killer;

	/**
	 * Constructs a Prey with the given initial population and birth/death
	 * rates.
	 * 
	 * @param count
	 *            population count
	 * @param birthRate
	 *            population birth rate
	 * @param deathRate
	 *            population death rate
	 */
	public Prey(int count, double birthRate, double deathRate) {
		super(count, birthRate, deathRate);
		if (this.getCount() > MAX_PREY) {
			this.setCount(MAX_PREY);
		} else if (this.getCount() < MIN_PREY) {
			this.setCount(MIN_PREY);
		}
	}

	/**
	 * Returns an array of default values.
	 * 
	 * @return default values
	 */
	public static double[] getDefaultParameters() {
		return DEFAULTS;
	}

	/**
	 * Calculates what the population will be in the next step of the
	 * simulation.
	 * 
	 * @return the projected population
	 */
	@Override
	public double getProjectedPopulation() {
		/** prey birth rate set to a local variable */
		double a = this.getBirthRate();
		/** prey count set to a local variable */
		double x = this.getCount();
		/** prey death rate set to a local variable */
		double b = this.getDeathRate();
		/** predator count set to a local variable */
		double y = killer.getCount();
		/** calculated population change */
		double change = (a * x) - (b * x * y);
		/** projected population */
		double projectedPopulation = this.getCount() + change;
		if (projectedPopulation > MAX_PREY) {
			return MAX_PREY;
		} else if (projectedPopulation < MIN_PREY) {
			return MIN_PREY;
		} else {
			return projectedPopulation;
		}
	}

	/**
	 * Sets a Species to killer, specifying the Predator to use when calculating
	 * Prey population.
	 * 
	 * @param predator
	 *            Predator to assign to killer
	 */
	@Override
	public void registerPredator(Species predator) {
		this.killer = predator;
	}

	/**
	 * Since Prey has no reason to register with itself, this method does
	 * nothing.
	 */
	@Override
	public void registerPrey(Species prey) {
		// Prey does not need to register with itself, so this method does
		// nothing.
	}

	/**
	 * Returns the Predator assigned to killer.
	 * 
	 * @return registered Predator
	 */
	@Override
	protected Species getPredator() {
		return this.killer;
	}

	/**
	 * This method makes Prey return itself.
	 * 
	 * @return this Prey object
	 */
	@Override
	protected Species getPrey() {
		return this;
	}
}