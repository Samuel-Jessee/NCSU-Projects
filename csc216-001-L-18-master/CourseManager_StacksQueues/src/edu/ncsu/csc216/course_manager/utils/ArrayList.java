package edu.ncsu.csc216.course_manager.utils;

import java.util.AbstractList;

/**
 * ArrayList class that takes a generic object and makes an array that contains the objects
 * and allows modifications to the array.
 * @author JeffColeman, GeneTodd,SamuelJessee
 * @param <E> the object type to be passed in
 */

public class ArrayList<E> extends AbstractList<E> {

	/** Array with generic object type to store objects */
	private E[] array;
	/** Size of the array */
	int size = 0;
	
	/**
	 * Constructs the array with a default size of 10
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		array = (E[])(new Object[10]);
	}
	
	/**
	 * Add method that allows an object to be added to the array at a specified index. The appropriate
	 * exception is thrown if the object is null or the index is not within the bounds of the
	 * array. The existing item in the array are shifted to the right and based on where the new
	 * object is added and the size is updated.
	 * @param index the index of where the object should be added
	 * @param e the object to add to the array
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void add(int index, E e) {
		if(e == null)
			throw new NullPointerException();
		if(index > size() || index < 0)
			throw new IndexOutOfBoundsException();
		if(size() == array.length) {
			E[] newArray = (E[])new Object[array.length * 2];
			for(int i = 0; i <size(); i++)
				newArray[i] = array[i];
			array = newArray;
		}
		for(int i = size(); i >= index + 1; i--)
			array[i] = array[i - 1];
		array[index] = e;
		size++;
	}

	/**
	 * Remove method that is used to remove an object at the specified index.  Everything in the array 
	 * after the removed index is shifted left and the size is adjusted
	 * @param index the index of the object to remove
	 */
	@Override
	public E remove(int index) {
		if(index >= size() || index < 0)
			throw new IndexOutOfBoundsException();
		E object = array[index];
		for(int i = index; i < size(); i++)
			array[i] = array[i+1];
		size--;
		return object;
	}

	/**
	 * Set method to set the object of the specified index to the object passed in.  This replaces the
	 * current object at that index.
	 * @param index the index of where the object is to be set
	 * @param element the object to be set
	 */
	@Override
	public E set(int index, E element) {
		if(element == null)
			throw new NullPointerException();
		if(index >= size() || index < 0)
			throw new IndexOutOfBoundsException();
		E object = array[index];
		array[index] = element;
		return object;
	}

	/**
	 * Used to get the object at a specified index of the array
	 * @return array[index] the object at the specified index of the array
	 * @param index the index to be retrieved
	 */
	@Override
	public E get(int index) {
		if(index >= size() || index < 0)
			throw new IndexOutOfBoundsException();
		return array[index];
	}

	/**
	 * Used to get the size of the array
	 * @return size the size of the array
	 */
	@Override
	public int size() {
		return size;
	}

}
