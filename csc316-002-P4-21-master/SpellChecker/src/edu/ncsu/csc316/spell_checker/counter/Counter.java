/**
 * 
 */
package edu.ncsu.csc316.spell_checker.counter;

/**
 * Keeps the counters for the spell checker
 * 
 * @author Selena Turner (snturner)
 * @author Samuel Jessee (sijessee)
 */
public class Counter {
	/** The number of words in the text to be spell-checked. */
	private static int totalWordsChecked;

	/** The number of misspelled words in the text. */
	private static int totalMisspelled;

	/** The total number of probes during the checking phase. */
	private static int totalProbes;

	/** The total number of lookups during the checking phase. */
	private static int totalLookups;

	/**
	 * Sets the totalWordsChecked
	 * 
	 * @param x
	 *            the number to set
	 */
	public static void setTotalWordsChecked(int x) {
		totalWordsChecked = x;
	}

	/**
	 * Sets the probes
	 * 
	 * @param x
	 *            the number to set
	 */
	public static void setTotalProbes(int x) {
		totalProbes = x;
	}

	/**
	 * Sets the lookups
	 * 
	 * @param x
	 *            the number to set
	 */
	public static void setTotalLookups(int x) {
		totalLookups = x;
	}

	/**
	 * Sets the number of misspelled words found.
	 * 
	 * @param x
	 *            the number to set
	 */
	public static void setTotalMisspelled(int x) {
		totalMisspelled = x;
	}

	/**
	 * Increments the counter of probes
	 */
	public static void incrementProbes() {
		totalProbes++;
	}

	/**
	 * Increments the counter of lookups
	 */
	public static void incrementLookups() {
		totalLookups++;
	}

	/**
	 * Increments the counter of words checked
	 */
	public static void incrementWordsChecked() {
		totalWordsChecked++;
	}

	/**
	 * Increments the number of misspelled words found.
	 */
	public static void incrementMisspelled() {
		totalMisspelled++;
	}

	/**
	 * Decrements the number of misspelled words found.
	 */
	public static void decrementMisspelled() {
		totalMisspelled--;
	}

	/**
	 * Decrements the counter of words checked
	 */
	public static void decrementWordsChecked() {
		totalWordsChecked--;
	}

	/**
	 * Decrements the counter of words checked
	 */
	public static void decrementLookups() {
		totalLookups--;
	}

	/**
	 * Decrements the counter of words checked
	 */
	public static void decrementProbes() {
		totalProbes--;
	}

	/**
	 * Returns the number of words in the text to be spell-checked.
	 * 
	 * @return totalWordsChecked
	 */
	public static int totalWordsChecked() {
		return totalWordsChecked;
	}

	/**
	 * Returns the number of misspelled words in the text.
	 * 
	 * @return totalMisspelled
	 */
	public static int totalMisspelled() {
		return totalMisspelled;
	}

	/**
	 * Returns the total number of probes during the checking phase.
	 * 
	 * @return totalProbes
	 */
	public static int totalProbes() {
		return totalProbes;
	}

	/**
	 * Returns the total number of lookups during the checking phase.
	 * 
	 * @return totalLookups
	 */
	public static int totalLookups() {
		return totalLookups;
	}

	/**
	 * The average number of probes per word (of the original text file)
	 * checked.
	 * 
	 * @return probes per word
	 */
	public static int wordProbes() {
		return totalProbes / totalWordsChecked;
	}

	/**
	 * The average number of probes per lookup operation (note that a single
	 * word may require multiple lookup operations).
	 * 
	 * @return probes per lookup
	 */
	public static int lookupProbes() {
		return totalProbes / totalLookups;
	}
}
