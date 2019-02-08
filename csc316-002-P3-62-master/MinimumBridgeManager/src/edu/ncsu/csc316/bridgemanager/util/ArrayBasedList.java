package edu.ncsu.csc316.bridgemanager.util;

import java.util.Arrays;

/**
 * An ArrayList to store different type elements into a list
 * 
 * @author Devin Janus (dwjanus)
 * @author Theodore Reger (tlreger)
 * @author Samuel Jessee (sijessee)
 *
 * @param <E>
 *            The type to use.
 */
public class ArrayBasedList<E> implements List<E> {
	/** list to store elements */
	private E[] list;
	/** How many elements are store in the list */
	private int size;
	/** Default length of the list */
	private final static int DEFAULT_CAPACITY = 10;

	/**
	 * Constructor of ArrayList with a capacity for use to input
	 * 
	 * @param capacity
	 *            the length for the list
	 */
	@SuppressWarnings("unchecked")
	public ArrayBasedList(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException();
		}
		this.list = (E[]) (new Object[capacity]);
		this.size = 0;
	}

	/**
	 * Default constructor of the ArrayList with 10 capacity
	 */
	@SuppressWarnings("unchecked")
	public ArrayBasedList() {
		this.list = (E[]) (new Object[DEFAULT_CAPACITY]);
		this.size = 0;
	}

	/**
	 * Return the element of the index
	 * 
	 * @param index
	 *            the index of the list
	 * @return the element at given index
	 */
	public E get(int index) {
		if (index < 0 || index > (list.length - 1)) {
			throw new IndexOutOfBoundsException();
		}
		return list[index];
	}

	/**
	 * Return the old element at chosen index, and set new element at chosen
	 * index
	 * 
	 * @param index
	 *            The index want to set
	 * @param element
	 *            New element want to set
	 * @return old Old element of given index
	 */
	public E set(int index, E element) {
		E old = list[index];
		if (element == null) {
			throw new NullPointerException();
		}
		if (old == null) {
			this.size++;
		}
		list[index] = element;
		return old;
	}

	/**
	 * Add new element at chosen index
	 * 
	 * @param index
	 *            The chosen index
	 * @param element
	 *            New element want to add
	 */
	public void add(int index, E element) {
		ensureCapacity(index);
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		} else if (element == null) {
			throw new NullPointerException();
		} else {// {1,2,3,4,5,6,null}
			for (int i = size; i > index; i--) {
				list[i] = list[i - 1];
			}
		}

		list[index] = element;
		size++;
	}

	/**
	 * Alternate add method that adds the value to the end of the list.
	 * 
	 * @param value
	 *            The value to add.
	 */
	@Override
	public void add(E value) {
		ensureCapacity(size + 1);
		if (value == null) {
			throw new NullPointerException();
		}
		list[size] = value;
		size++;
	}

	/**
	 * Remove the element at the chosen index
	 * 
	 * @param index
	 *            the index of the element
	 * @return the element
	 */
	public E remove(int index) {
		if (size == 0) {
			throw new IndexOutOfBoundsException();
		} else if (index > size) {
			throw new IndexOutOfBoundsException();
		}
		E a = list[index];
		for (int i = index; i < size; i++) {
			list[i] = list[i + 1];
		}
		list[size] = null;
		size--;
		return a;
	}

	/**
	 * Return the size of the ArrayList
	 * 
	 * @return size how many elements in the list
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Ensure the list has enough capacity for new elements, if not, create a
	 * new list and copy all old elements into new list
	 * 
	 * @param capacity
	 *            New capacity for the list
	 */
	private void ensureCapacity(int capacity) {
		if (capacity > list.length) {
			int newCapacity = list.length * 2 + 1;
			if (capacity > newCapacity) {
				newCapacity = capacity;
			}
			list = Arrays.copyOf(list, newCapacity);
		}

	}

	/**
	 * Determines if the list is empty.
	 * 
	 * @return True if the list is empty.
	 */
	public boolean isEmpty() {
		return (this.size() == 0);
	}

	/**
	 * Searches for an element and returns its index if found. If not found,
	 * returns -1.
	 * 
	 * @param element
	 *            The element to search for.
	 * @return The index of the element, or -1 if not found.
	 */
	public int contains(E element) {
		int i = 0;
		while (i < this.size) {
			if (list[i].equals(element)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	/**
	 * Returns a String representation of the ArrayBasedList.
	 * 
	 * @return A String describing the contents of the list.
	 */
	public String toString() {
		String out = "[";
		for (int i = 0; i < this.size; i++) {
			out += this.list[i].toString();
			if ((i + 1) < this.size) {
				out += ", ";
			}
		}
		out += "]";
		return out;
	}

}