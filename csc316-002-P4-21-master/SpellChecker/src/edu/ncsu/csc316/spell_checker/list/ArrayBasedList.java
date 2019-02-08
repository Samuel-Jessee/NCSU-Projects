/**
 * 
 */
package edu.ncsu.csc316.spell_checker.list;

import java.util.Arrays;

/**
 * The ArrayBasedList for the SpellChecker
 * 
 * @author Selena Turner (snturner)
 * @author Samuel Jessee (sijessee)
 */
public class ArrayBasedList implements WordList {
	/** The array the list uses */
	private String[] list;
	/** The size of the list */
	private int size;
	/** The capacity of the list */
	private int capacity = 25144;

	/**
	 * A parameterless constructor for the ArrayList
	 */
	public ArrayBasedList() {
		this(10);
	}

	/**
	 * A secondary constructor with a capacity parameter which will be used to
	 * manually set the length of the array.
	 * 
	 * @param capacity
	 *            of the base array
	 */
	public ArrayBasedList(int capacity) {
		list = new String[capacity];
		size = 0;
	}

	@Override
	public void add(String word) {
		if (word == null)
			throw new IllegalArgumentException();
		resize();
		list[this.size] = word;
		this.size++;

	}

	/**
	 * Adds a word to a specific index in a list and shifts everything over to
	 * accommodate.
	 * 
	 * @param word
	 *            The word being added.
	 * @param index
	 *            The index to add at.
	 */
	@Override
	public void add(String word, int index) {
		resize();
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		} else if (word == null) {
			throw new NullPointerException();
		} else {
			for (int i = size; i > index; i--) {
				list[i] = list[i - 1];
			}
		}
		list[index] = word;
		size++;
	}

	/**
	 * Adds an item to the array list while maintaining alphabetical order.
	 * 
	 * @param word
	 *            The word to add.
	 */
	@Override
	public void addSorted(String word) {
		if (word == null) {
			throw new IllegalArgumentException();
		}
		resize();
		if (size == 0) {
			add(word);
		} else {
			for (int i = 0; i < size; i++) {
				if (word.compareToIgnoreCase(list[i]) < 0) {
					add(word, i);
					break;
				} else if (i == size - 1 && word.compareToIgnoreCase(list[i]) >= 0) {
					add(word);
					break;
				}
			}
		}
	}

	@Override
	public String get(int index) {
		if (index >= capacity || index < 0)
			throw new IndexOutOfBoundsException();
		return list[index];
	}

	@Override
	public int size() {
		return this.size;
	}

	/**
	 * Must return a string representation of the list in the format:
	 * "WordList[word1, word2, word3]".
	 * 
	 * @return the string of words
	 */
	public String toString() {
		String s = "WordList[";
		for (int i = 0; i < size(); i++) {
			if (i < size() - 1) {
				s += get(i) + ", ";
			} else if (i == size() - 1) {
				s += get(i);
			}
		}
		s += "]";
		return s;
	}

	/**
	 * Will resize the array the the list contains in order to allow more
	 * elements to be added to the list even when the size is larger than the
	 * array's length.
	 */
	private void resize() {
		if (this.size + 1 == list.length) {
			String[] oldList = list;
			int newCap = list.length * 2 + 1;
			list = (String[]) Arrays.copyOf(oldList, newCap);
		}
	}
}
