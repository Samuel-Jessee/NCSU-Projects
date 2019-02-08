/**
 * 
 */
package edu.ncsu.csc216.biosimulation.domain;

/**
 * Implements the behavior of the scavenger species for the simulation,
 * including calculating the projected population according to the last bullet
 * of [UC4, S1].
 * 
 * @author SamuelJessee
 *
 */
public class Scavenger extends Species {

	/** max Scavenger count */
	public static final int MAX_SCAVENGER = 500;

	/** min Scavenger count */
	public static final int MIN_SCAVENGER = 4;

	/** default initial values */
	private static final double[] DEFAULTS = { 0.000002, 0.1, 0.0006, 0.0003 };

	/** birth rate from prey carcasses */
	private double preyCarcassBirthRate;

	/** birth rate from predator carcasses */
	private double predatorCarcassBirthRate;

	/** Scavenger population limiting factor */
	private static final double LIMITING_FACTOR = 0.001;

	/** Prey object */
	private Species preyCarcass;

	/** Predator object */
	private Species predatorCarcass;

	/**
	 * Constructs a Scavenger with the given initial population and birth/death
	 * rates.
	 * 
	 * @param count
	 *            Initial Scavenger population (forced into range between 4 and
	 *            500 if needed)
	 * @param birthKill
	 *            Population growth rate from scavenging corpses of prey that
	 *            die from predation
	 * @param deathRate
	 *            Death rate of scavengers
	 * @param birthPrey
	 *            Population growth rate from scavenging corpses of prey that
	 *            die naturally
	 * @param birthPredator
	 *            Population growth rate from scavenging corpses of predators
	 */
	public Scavenger(int count, double birthKill, double deathRate, double birthPrey, double birthPredator) {
		super(count, birthKill, deathRate);
		if (birthPrey < 0 || birthPredator < 0 || birthPrey > 1 || birthPredator > 1) {
			throw new IllegalArgumentException();
		} else {
			this.preyCarcassBirthRate = birthPrey;
			this.predatorCarcassBirthRate = birthPredator;
			this.setCount(putCountInRange(count));
		}
	}

	/**
	 * Returns an array containing the default values.
	 * 
	 * @return default values
	 */
	public static double[] getDefaultParameters() {
		return DEFAULTS;
	}

	/**
	 * If the received count is outside of the Scavenger population range,
	 * returns the maximum or minimum count. If count is inside range, it
	 * returns the parameter.
	 * 
	 * @param count
	 *            population count
	 * @return count that is in population range
	 */
	private double putCountInRange(double count) {
		if (count > MAX_SCAVENGER) {
			return MAX_SCAVENGER;
		} else if (count < MIN_SCAVENGER) {
			return MIN_SCAVENGER;
		} else {
			return count;
		}
	}

	/**
	 * Calculates what the population will be in the next step of the
	 * simulation.
	 * 
	 * @return the projected population
	 */
	@Override
	public double getProjectedPopulation() {
		/** scavenger death rate set to a local variable */
		double e = this.getDeathRate();
		/** scavenger count set to a local variable */
		double z = this.getCount();
		/** scavenger birth rate from kills set to a local variable */
		double f = this.getBirthRate();
		/** prey count set to a local variable */
		double x = preyCarcass.getCount();
		/** predator count set to a local variable */
		double y = predatorCarcass.getCount();
		/** scavenger birth rate from prey carcasses */
		double g = preyCarcassBirthRate;
		/** scavenger birth rate from predator carcasses */
		double h = predatorCarcassBirthRate;
		/** scavenger limiting factor */
		double i = LIMITING_FACTOR;
		/** calculated population change */
		double change = (-1 * e * z) + (f * x * y * z) + (g * x * z) + (h * y * z) - (i * z * z);
		/** projected population */
		double projectedPopulation = this.getCount() + change;
		projectedPopulation = putCountInRange(projectedPopulation);
		return projectedPopulation;
	}

	/**
	 * Sets a Species to predatorCarcass, specifying a Predator to use when
	 * calculating Scavenger population.
	 * 
	 * @param predator
	 *            Predator to assign to predatorCarcass
	 */
	@Override
	public void registerPredator(Species predator) {
		this.predatorCarcass = predator;
	}

	/**
	 * Sets a Species to preyCarcass, specifying a Prey to use when calculating
	 * Scavenger population.
	 * 
	 * @param prey
	 *            Prey to assign to preyCarcass
	 */
	@Override
	public void registerPrey(Species prey) {
		this.preyCarcass = prey;
	}

	/**
	 * Returns the Predator assigned to predatorCarcass.
	 * 
	 * @return registered Predator
	 */
	@Override
	protected Species getPredator() {
		return this.predatorCarcass;
	}

	/**
	 * Returns the Prey assigned to preyCarcass.
	 * 
	 * @return registered Prey
	 */
	@Override
	protected Species getPrey() {
		return this.preyCarcass;
	}
}