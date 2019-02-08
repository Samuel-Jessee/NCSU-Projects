package edu.ncsu.csc316.familytree.util;

import edu.ncsu.csc316.familytree.person.Person;

/**
 * A generic list class that supports various list operations.
 * 
 * @author Samuel Jessee (sijessee)
 * 
 * @param <E>
 *            object type to use in the list
 */
public class LinkedList<E> implements List<E> {

	/** points to the front of the list */
	private Node head;

	/** traverses through the list elements */
	private Node iterator;

	/** size of the list */
	private int size;

	/**
	 * Constructs the empty list
	 */
	public LinkedList() {
		head = new Node(null, null);
		size = 0;
		iterator = null;
	}

	/**
	 * Resets the iterator to point to the first element in the list. ALWAYS
	 * CALL THIS BEFORE USING ITERATOR IN OTHER METHODS
	 */
	private void resetIterator() {
		iterator = head;
	}

	/**
	 * Returns true if the iterator is pointing to an element
	 * 
	 * @return true if list contains another element
	 */
	private boolean hasNext() {
		return iterator != null;
	}

	/**
	 * Returns the element the iterator is pointing to and moves iterator to
	 * point to the next element in the list. If the iterator is null, returns
	 * null. Always call hasNext() first.
	 * 
	 * @return the next element in the list, null if iterator is null
	 */
	private E next() {
		if (hasNext()) {
			E temp = iterator.data;
			iterator = iterator.next;
			return temp;
		} else {
			return null;
		}
	}

	/**
	 * Returns true if the list is empty
	 * 
	 * @return true if the list is empty
	 */
	private boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns the element at the given position. Returns null if index >= size
	 * or index < 0
	 * 
	 * @param index
	 *            the index to be looked at
	 * @return the element at index
	 */
	public E get(int index) {
		resetIterator();
		if (index < 0 || index >= size) {
			return null;
		}

		for (int i = 0; i < index; i++) {
			next();
		}
		return iterator.data;
	}

	/**
	 * Adds an element to the rear of the list
	 * 
	 * @param element
	 *            the element to be added
	 */
	public void add(E element) {
		resetIterator();
		if (isEmpty()) {
			size++;
			head = new Node(element, null);
			return;
		} else {
			for (int i = 0; i < size - 1; i++) {
				next();
			}
			size++;
			if (hasNext()) {
				iterator.next = new Node(element, null);
			} else {
				iterator = new Node(element, null);
			}
		}
	}

	/**
	 * returns the number of elements in the list
	 * 
	 * @return the number of elements in the list
	 */
	public int size() {
		return size;
	}

	/**
	 * Searches the list for the given object, and returns the index of the
	 * first occurrence of the object if found.
	 * 
	 * @param value
	 *            object to search for
	 * @return index of the object
	 */
	public int indexOf(E value) {
		if (isEmpty()) {
			return -1;
		}
		for (int i = 0; i < size(); i++) {
			if (((Person) get(i)).equalsPerson((Person) value)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Removes and returns the first element in the list.
	 * 
	 * @return the element that was removed
	 */
	public E dequeue() {
		resetIterator();
		size--;
		E temp = head.data;
		head = head.next;
		return temp;
	}

	/**
	 * Adds item to end of queue.
	 * 
	 * @param element
	 *            element to add
	 */
	public void enqueue(E element) {
		add(element);
	}

	/**
	 * A node of the LinkedList iterator
	 * 
	 * @author Samuel Jessee (sijessee)
	 * 
	 */
	private class Node {
		/** The data held at this node **/
		public E data;

		/** The next node in the iterator **/
		public Node next;

		/** The constructor, takes an element and the next in the list **/
		public Node(E data, Node next) {
			this.data = data;
			this.next = next;
		}
	}

}