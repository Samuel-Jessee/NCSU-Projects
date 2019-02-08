/**
 * 
 */
package edu.ncsu.csc316.spell_checker.manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.ncsu.csc316.spell_checker.counter.Counter;
import edu.ncsu.csc316.spell_checker.hash_table.HashTable;
import edu.ncsu.csc316.spell_checker.list.ArrayBasedList;
import edu.ncsu.csc316.spell_checker.list.WordList;
import edu.ncsu.csc316.spell_checker.rules.SimplificationRules;

/**
 * The manager for the spell checker
 * 
 * @author Selena Turner (snturner)
 * @author Samuel Jessee (sijessee)
 */
public class SpellCheckerManager {
	/** The hash table */
	private HashTable hashTable;

	/**
	 * Creates the dictionary by reading each word from the file given and
	 * adding them to the hash table
	 * 
	 * @param dictionaryFileName
	 *            the dictionary file
	 */
	public SpellCheckerManager(String dictionaryFileName) {
		hashTable = new HashTable();
		Scanner sc;
		try {
			sc = new Scanner(new FileInputStream(dictionaryFileName));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException();
		}
		WordList dictionary = new ArrayBasedList();
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			dictionary.add(line);
		}
		sc.close();
		for (int i = 0; i < dictionary.size(); i++) {
			hashTable.insert(dictionary.get(i).toString());
		}
	}

	/**
	 * Enters a WordList dictionary instead of a file name
	 * 
	 * @param dictionary
	 *            a dictionary to be added to the hash table
	 */
	public SpellCheckerManager(WordList dictionary) {
		hashTable = new HashTable();
		for (int i = 0; i < dictionary.size(); i++) {
			hashTable.insert(dictionary.get(i).toString());
		}
	}

	/**
	 * Returns the input file text for the GUI
	 * 
	 * @param inputFile
	 *            the input file text
	 * @return the input file in WordList form
	 */
	public WordList getInputText(String inputFile) {
		Scanner sc;
		try {
			sc = new Scanner(new FileInputStream(inputFile));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException();
		}
		WordList inputText = new ArrayBasedList();
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			Scanner s = new Scanner(line);
			while (s.hasNext()) {
				String word = s.next();
				inputText.add(word);
			}
			s.close();
		}
		sc.close();

		if (inputText.size() == 0) {
			throw new IllegalArgumentException("Text file should not be empty.");
		}
		return inputText;
	}

	/**
	 * Checks the spelling of the text
	 * 
	 * @param inputText
	 *            the text file to check
	 * @return the potentially misspelled words
	 */
	public WordList spellCheck(WordList inputText) {
		SimplificationRules rules = new SimplificationRules();
		WordList misspelledList = new ArrayBasedList();
		Counter.setTotalProbes(0);
		Counter.setTotalLookups(0);
		Counter.setTotalWordsChecked(inputText.size());
		Counter.setTotalMisspelled(0);
		for (int i = 0; i < inputText.size(); i++) {
			String originWord = inputText.get(i);
			String word = inputText.get(i);
			String found = hashTable.lookup(word);
			Counter.incrementProbes();
			if (found != null) {
				word = rules.removeCapitalization(word);
				Counter.incrementLookups();
				found = hashTable.lookup(word);
				Counter.incrementProbes();
				if (found != null) {
					if (word.endsWith("'s")) {
						word = rules.removePossession(word);
						Counter.incrementLookups();
						found = hashTable.lookup(word);
						Counter.incrementProbes();
					}
					while (found != null) {
						if (word.endsWith("ly")) {
							word = rules.removeAdverb(word);
							Counter.incrementLookups();
						} else if (word.endsWith("es")) {
							String temp = word;
							word = rules.removePlural(word);
							Counter.incrementLookups();
							found = hashTable.lookup(word.toString());
							Counter.incrementProbes();
							if (found != null && (!word.endsWith("er") && !word.endsWith("ed"))) {
								if (word.endsWith("r")) {
									String newTemp = word;
									newTemp = newTemp.concat("e");
									Counter.incrementLookups();
									found = hashTable.lookup(newTemp.toString());
									Counter.incrementProbes();
									if (found == null) {
										word = newTemp;
									}
								} else {
									word = temp.substring(0, temp.length() - 1);
									Counter.incrementLookups();
								}
							}
						} else if (word.endsWith("s")) {
							found = hashTable.lookup(word.toString());
							Counter.incrementProbes();
							if (found != null) {
								word = rules.removePlural(word);
								Counter.incrementLookups();
							}
						} else if (word.endsWith("ing")) {
							found = hashTable.lookup(word.toString());
							Counter.incrementProbes();
							if (found != null) {
								word = rules.removeVerb(word);
								Counter.incrementLookups();
							}
							String temp = word;
							if (found != null) {
								temp = temp.concat("e");
								Counter.incrementLookups();
								found = hashTable.lookup(temp.toString());
								Counter.incrementProbes();
								if (found == null) {
									word = temp;
								}
							}
						} else if (word.endsWith("er")) {
							String temp = word;
							word = rules.removeTitle(word);
							Counter.incrementLookups();
							found = hashTable.lookup(word.toString());
							Counter.incrementProbes();
							if (found != null && (!word.endsWith("ed") && !word.endsWith("d") && !word.endsWith("es")
									&& !word.endsWith("s"))) {
								word = temp.substring(0, temp.length() - 1);
								Counter.incrementLookups();
							}
						} else if (word.endsWith("r")) {
							found = hashTable.lookup(word.toString());
							Counter.incrementProbes();
							if (found != null) {
								word = rules.removeTitle(word);
								Counter.incrementLookups();
							}
						} else if (word.endsWith("ed")) {
							String temp = word;
							word = rules.removePastTense(word);
							Counter.incrementLookups();
							found = hashTable.lookup(word.toString());
							Counter.incrementProbes();
							if (found != null && (!word.endsWith("er") && !word.endsWith("es"))) {
								if (word.endsWith("r")) {
									String newTemp = word;
									newTemp = newTemp.concat("e");
									Counter.incrementLookups();
									found = hashTable.lookup(newTemp.toString());
									Counter.incrementProbes();
									if (found == null) {
										word = newTemp;
									}
								} else {
									word = temp.substring(0, temp.length() - 1);
									Counter.incrementLookups();
								}
							}
						} else if (word.endsWith("d")) {
							found = hashTable.lookup(word.toString());
							Counter.incrementProbes();
							if (found != null) {
								word = rules.removePastTense(word);
								Counter.incrementLookups();
							}
						} else {
							break;
						}
						found = hashTable.lookup(word.toString());
						Counter.incrementProbes();
					}
					if (found != null) {
						String check = "no";
						for (int j = 0; j < misspelledList.size(); j++) {
							if (misspelledList.get(j).equals(originWord)) {
								check = "yes";
							}
						}
						if (check.equals("no")) {
							Counter.incrementMisspelled();
							misspelledList.addSorted(originWord);
						}
					}
				}
			}
		}
//		System.out.println("Total Words Checked: " + Counter.totalWordsChecked());
//		System.out.println("Total Misspelled: " + Counter.totalMisspelled());
//		System.out.println("Total Probes: " + Counter.totalProbes());
//		System.out.println("Total Lookups: " + Counter.totalLookups());
//		System.out.println("Average probes per word: " + Counter.wordProbes());
//		System.out.println("Average probes per lookup operation: " + Counter.lookupProbes());
		return misspelledList;
	}

	/**
	 * Gets the hashTable (for student test)
	 * 
	 * @return the hashTable
	 */
	public HashTable getHashTable() {
		return hashTable;
	}
}
