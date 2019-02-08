/**
 * 
 */
package edu.ncsu.csc216.biosimulation.domain;

/**
 * Implements the behavior of the predator species for the simulation, including
 * calculating the projected population according to the first bullet of [UC4,
 * S1].
 * 
 * @author SamuelJessee
 *
 */
public class Predator extends Species {

	/** max Predator count */
	public static final int MAX_PREDATOR = 300;

	/** min Predator count */
	public static final int MIN_PREDATOR = 0;

	/** default initial values */
	private static final double[] DEFAULTS = { 0.00068, 0.23 };

	/** Prey object */
	private Species food;

	/**
	 * Constructs a Predator with the given initial population and birth/death
	 * rates.
	 * 
	 * @param count
	 *            population count
	 * @param birthRate
	 *            population birth rate
	 * @param deathRate
	 *            population death rate
	 */
	public Predator(int count, double birthRate, double deathRate) {
		super(count, birthRate, deathRate);
		if (this.getCount() > MAX_PREDATOR) {
			this.setCount(MAX_PREDATOR);
		} else if (this.getCount() < MIN_PREDATOR) {
			this.setCount(MIN_PREDATOR);
		}
	}

	/**
	 * Returns array of default values.
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
		/** predator death rate set to a local variable */
		double c = this.getDeathRate();
		/** predator count set to a local variable */
		double y = this.getCount();
		/** predator birth rate set to a local variable */
		double p = this.getBirthRate();
		/** prey count set to a local variable */
		double x = food.getCount();
		/** calculated population change */
		double change = (-1 * c * y) + (p * x * y);
		/** projected population */
		double projectedPopulation = this.getCount() + change;
		if (projectedPopulation > MAX_PREDATOR) {
			return MAX_PREDATOR;
		} else if (projectedPopulation < MIN_PREDATOR) {
			return MIN_PREDATOR;
		} else {
			return projectedPopulation;
		}
	}

	/**
	 * Because the Predator can't register with itself, this method does
	 * nothing.
	 */
	@Override
	public void registerPredator(Species predator) {
		// Predator does not need to register with itself, so this method does
		// nothing.
	}

	/**
	 * Sets a Species object to the food field, specifying a Prey to be used
	 * when calculating Predator population.
	 * 
	 * @param prey
	 *            Species to be assigned to food
	 */
	@Override
	public void registerPrey(Species prey) {
		this.food = prey;
	}

	/**
	 * This method makes the Predator return itself.
	 * 
	 * @return this Predator object
	 */
	@Override
	protected Species getPredator() {
		return this;
	}

	/**
	 * Returns the Prey assigned to food.
	 * 
	 * @return registered Prey
	 */
	@Override
	protected Species getPrey() {
		return this.food;
	}
}