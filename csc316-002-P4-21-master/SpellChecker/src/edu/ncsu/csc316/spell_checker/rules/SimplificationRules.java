/**
 * 
 */
package edu.ncsu.csc316.spell_checker.rules;

/**
 * The rules for spell checking with the dictionary
 * 
 * @author Selena Turner (snturner)
 * @author Samuel Jessee (sijessee)
 */
public class SimplificationRules {

	// unneeded constructor
	/*
	 * public SimplificationRules() {
	 * 
	 * }
	 */

	/**
	 * Removes capitalization of first index
	 * 
	 * @param word
	 *            the word
	 * @return the word lowercased
	 */
	public String removeCapitalization(String word) {
		String newWord = word.substring(0, 1);
		newWord = newWord.toLowerCase();
		newWord += word.substring(1, word.length());
		return newWord;
	}

	/**
	 * Removes possession
	 * 
	 * @param word
	 *            the word
	 * @return the word without possession
	 */
	public String removePossession(String word) {
		word = word.substring(0, word.length() - 2);
		return word;
	}

	/**
	 * Removes past tense
	 * 
	 * @param word
	 *            the word
	 * @return the word without past tense
	 */
	public String removePastTense(String word) {
		if (word.endsWith("ed")) {
			word = word.substring(0, word.length() - 2);
		} else if (word.endsWith("d")) {
			word = word.substring(0, word.length() - 1);
		}
		return word;
	}

	/**
	 * Removes "ly"
	 * 
	 * @param word
	 *            the word
	 * @return the word without "ly"
	 */
	public String removeAdverb(String word) {
		word = word.substring(0, word.length() - 2);
		return word;
	}

	/**
	 * Removes the plural "s" or "es"
	 * 
	 * @param word
	 *            thw word
	 * @return the word without "es" or "s"
	 */
	public String removePlural(String word) {
		if (word.endsWith("es")) {
			word = word.substring(0, word.length() - 2);
		} else if (word.endsWith("s")) {
			word = word.substring(0, word.length() - 1);
		}
		return word;
	}

	/**
	 * Removes the verb "ing"
	 * 
	 * @param word
	 *            the word
	 * @return the word without "ing"
	 */
	public String removeVerb(String word) {
		word = word.substring(0, word.length() - 3);
		return word;
	}

	/**
	 * Removes the title "r" or "er"
	 * 
	 * @param word
	 *            the word
	 * @return the word without "r" or "er"
	 */
	public String removeTitle(String word) {
		if (word.endsWith("er")) {
			word = word.substring(0, word.length() - 2);
		} else if (word.endsWith("r")) {
			word = word.substring(0, word.length() - 1);
		}
		return word;
	}
}
