package edu.ncsu.csc316.familytree.util;

/**
 * Interface that defines the methods a List must implement.
 * 
 * @author Samuel Jessee (sijessee)
 *
 * @param <E>
 *            type of object to use
 */
public interface List<E> {

	/**
	 * Returns the object at the given index.
	 * 
	 * @param index
	 *            index of the object to return
	 * @return the object at the given index
	 */
	public E get(int index);

	/**
	 * Adds an object to the end of the List.
	 * 
	 * @param value
	 *            the object to add
	 */
	public void add(E value);

	/**
	 * Returns the size of the List.
	 * 
	 * @return the size of the List
	 */
	public int size();

	/**
	 * Takes a value and searches for it in the List. Returns the index of the
	 * first occurrence of that value in the list, or -1 if the value is not
	 * found.
	 * 
	 * @param value
	 *            value to search for
	 * @return index of value
	 */
	public int indexOf(E value);
}