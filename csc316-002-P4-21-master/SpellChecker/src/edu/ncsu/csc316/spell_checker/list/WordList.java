/**
 * 
 */
package edu.ncsu.csc316.spell_checker.list;

/**
 * The WordList interface for the ArrayBasedList
 * 
 * @author Selena Turner (snturner)
 * @author Samuel Jessee (sijessee)
 */
public interface WordList {
	/**
	 * Adds the given value to the list.
	 * 
	 * @param word
	 *            to add
	 */
	public void add(String word);

	/**
	 * Adds the given word to the specified index.
	 * 
	 * @param word
	 *            The word to add.
	 * @param index
	 *            The index to add to.
	 */
	public void add(String word, int index);

	/**
	 * Adds the given word (as a string) to the list in increasing alphabetical
	 * order (case insensitive)
	 * 
	 * @param word
	 *            to add
	 */
	public void addSorted(String word);

	/**
	 * Gets the word at the given index
	 * 
	 * @param index
	 *            in the list to get from
	 * @return element at the index
	 */
	public String get(int index);

	/**
	 * Gets the size of the list
	 * 
	 * @return size of the list
	 */
	public int size();
}
