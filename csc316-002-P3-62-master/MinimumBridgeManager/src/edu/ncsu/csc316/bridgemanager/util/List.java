package edu.ncsu.csc316.bridgemanager.util;

/**
 * Interface defining the methods for a list.
 * 
 * @author Samuel Jessee (sijessee)
 *
 * @param <E>
 *            Type to use for the list.
 */
public interface List<E> {

	/**
	 * Adds the value to the list.
	 * 
	 * @param value
	 *            The value to add.
	 */
	public void add(E value);

	/**
	 * Returns the size of the List.
	 * 
	 * @return The size.
	 */
	public int size();

	/**
	 * Returns the element at the given index in the List.
	 * 
	 * @param index
	 *            Index of the element to retrieve.
	 * @return The element from the List.
	 */
	public E get(int index);
}