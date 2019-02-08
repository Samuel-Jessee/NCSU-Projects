/**
 * 
 */
package edu.ncsu.csc216.biosimulation.domain;

/**
 * Abstract parent class of the Species hierarchy. This class contains the
 * common state and behavior for all species. The abstract behaviors are
 * registering and getting predator and prey and in getting the projected
 * population one step hence.
 * 
 * @author SamuelJessee
 *
 */
public abstract class Species implements Organism {

	/** the size of the population */
	private double count;

	/** the death rate of the population */
	private double deathRate;

	/** the birth rate of the population */
	private double birthRate;

	/**
	 * Constructs a Species with the given initial population and birth/death
	 * rates.
	 * 
	 * @param count
	 *            initial population count
	 * @param birthRate
	 *            birth rate
	 * @param deathRate
	 *            death rate
	 */
	public Species(int count, double birthRate, double deathRate) {
		if (count < 0 || birthRate < 0 || deathRate < 0 || birthRate > 1 || deathRate > 1) {
			throw new IllegalArgumentException();
		} else {
			this.count = count;
			this.birthRate = birthRate;
			this.deathRate = deathRate;
		}
	}

	/**
	 * A death rate, which may take into account deaths caused by other species.
	 * The death rate is a percentage of the current population/100.
	 * 
	 * @return the death rate
	 */
	@Override
	public double getDeathRate() {
		return deathRate;
	}

	/**
	 * A rate of births, which may be due to other species via increased food
	 * supply. The birth rate is a percentage of the current population/100.
	 * 
	 * @return the rate of births
	 */
	@Override
	public double getBirthRate() {
		return birthRate;
	}

	/**
	 * The current population count. This is maintained as a double to avoid
	 * serious roundoff errors with successive computations.
	 * 
	 * @return the number of organisms of this species
	 */
	@Override
	public double getCount() {
		return count;
	}

	/**
	 * Sets the population count to a particular value.
	 * 
	 * @param count
	 *            the new value for the population
	 */
	@Override
	public void setCount(double count) {
		if (count < 0) {
			throw new IllegalArgumentException();
		} else {
			this.count = count;
		}
	}

	/**
	 * Computes the population count for the subsequent round.
	 * 
	 * @return the projected population count.
	 */
	@Override
	public abstract double getProjectedPopulation();

	/**
	 * Specifies the predator for this simulation and required for the Scavenger
	 * and Prey to calculate their projected populations [UC2, S1].
	 * 
	 * @param predator
	 *            Predator for the simulation
	 */
	public abstract void registerPredator(Species predator);

	/**
	 * Specifies the prey for this simulation and required for the Scavenger and
	 * Predator to calculate their projected populations [UC2, S1].
	 * 
	 * @param prey
	 *            Prey for the simulation
	 */
	public abstract void registerPrey(Species prey);

	/**
	 * Returns the predator for this simulation. Protected method, not to be
	 * used outside the package. This is used for JUnit tests.
	 * 
	 * @return the Predator of the simulation
	 */
	protected abstract Species getPredator();

	/**
	 * Prey counterpart to getPredator.
	 * 
	 * @return the Prey of the simulation
	 */
	protected abstract Species getPrey();
}