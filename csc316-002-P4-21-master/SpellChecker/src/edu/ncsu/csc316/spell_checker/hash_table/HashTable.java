/**
 * 
 */
package edu.ncsu.csc316.spell_checker.hash_table;

/**
 * The HashTable for the spell checker
 * 
 * @author Selena Turner (snturner)
 * @author Samuel Jessee (sijessee)
 */
public class HashTable {
	/** Number for the hash code */
	private int primeNumber = 7;
	/** The ArrayBasedList */
	private String[] dictionary;
	// Check to see if we should use an ArrayBasedList or a String array
	private int size = 25144;

	/**
	 * Initializes the HashTable
	 */
	public HashTable() {
		dictionary = new String[size];
	}

	/**
	 * Inserts the word from the dictionary file
	 * 
	 * @param word
	 *            the word to insert
	 */
	public void insert(String word) {
		int hNum = hashCode(word);
		int i = (hNum) % (dictionary.length);
		if (i < 0) {
			i = i * (-1);
		}
		int j = i;
		while (i < size) {
			if (get(i) == null) {
				dictionary[i] = word;
				break;
			}
			i++;
		}

		if (i == size) {
			i = 0;
			while (i < j) {
				if (get(i) == null) {
					dictionary[i] = word;
					break;
				}
				i++;
			}
		}

	}

	/**
	 * Looks up the word in the HashTable dictionary
	 * 
	 * @param word
	 *            the word to look up in the HashTable dictionary
	 * @return the word, if it is NOT found in the dictionary
	 */
	public String lookup(String word) {
		int hNum = 0;
		hNum = hashCode(word);
		int i = (hNum) % (dictionary.length);
		if (i < 0) {
			i = i * (-1);
		}
		int j = i;
		// Goes through from i to the SIZE
		while (i < size) {
			if (get(i) != null) {
				if (get(i).equals(word)) {
					// No misspellings
					return null;
				} else {
					i++;
				}
			} else {
				i++;
			}
		}
		if (i == size) {
			i = 0;
			while (i < j) {
				if (get(i) != null) {
					if (get(i).equals(word)) {
						// If there are no mispellings
						return null;
					} else {
						i++;
					}
				} else {
					i++;
				}
			}
		}
		// If the word is potentially misspelled
		return word;
	}

	/**
	 * Generates a "random" number based on the word entered.
	 * 
	 * @param word
	 *            the entered word
	 * @return a hash coded number
	 */
	public int hashCode(String word) {
		int p = primeNumber;
		int c = 1;
		for (int i = word.length() - 1; i > 0; i--) {
			c = word.charAt(i) + (c * p);
		}
		return c;
	}

	/**
	 * Gets the word at the index given
	 * 
	 * @param index
	 *            the index
	 * @return the word at the index
	 */
	public String get(int index) {
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException();
		return dictionary[index];
	}

}
