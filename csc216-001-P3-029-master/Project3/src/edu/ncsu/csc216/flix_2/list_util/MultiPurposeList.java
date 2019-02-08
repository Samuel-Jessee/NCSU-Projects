package edu.ncsu.csc216.flix_2.list_util;

/**
 * A generic list class that supports the underlying list operations for the
 * movie inventory and queues.
 * 
 * @author MatthewLilly
 * @author SamuelJessee
 * 
 * @param <T>
 *            object type to use in the list
 */
public class MultiPurposeList<T> {

	/** points to the front of the list */
	private Node head;

	/** traverses through the list elements */
	private Node iterator;

	/** size of the list */
	private int size;

	/**
	 * Constructs the empty list
	 */
	public MultiPurposeList() {
		head = new Node(null, null);
		size = 0;
		iterator = null;
	}

	/**
	 * Resets the iterator to point to the first element in the list. ALWAYS
	 * CALL THIS BEFORE USING ITERATOR IN OTHER METHODS
	 */
	public void resetIterator() {
		iterator = head;
	}

	/**
	 * Returns true if the iterator is pointing to an element
	 * 
	 * @return true if list contains another element
	 */
	public boolean hasNext() {
		return iterator != null;
	}

	/**
	 * Returns the element the iterator is pointing to and moves iterator to
	 * point to the next element in the list. If the iterator is null, returns
	 * null. Always call hasNext() first.
	 * 
	 * @return the next element in the list, null if iterator is null
	 */
	public T next() {
		if (hasNext()) {
			T temp = iterator.data;
			iterator = iterator.next;
			return temp;
		} else {
			return null;
		}
	}

	/**
	 * Adds an element at idx. If idx < 0, add to the front. If idx >= size, add
	 * to the end
	 * 
	 * @param idx
	 *            the index to be added at
	 * @param element
	 *            the element to be added
	 */
	public void addItem(int idx, T element) {
		resetIterator();
		if (isEmpty()) {
			size++;
			head = new Node(element, null);
			iterator = head;
		} else if (idx <= 0) {
			size++;
			Node temp = head;
			head = new Node(element, temp);
		} else if (idx > 0 && idx < size()) {
			size++;
			for (int i = 0; i < idx - 1; i++) {
				next();
			}
			Node temp = iterator.next;
			iterator.next = new Node(element, temp);
		} else if (idx >= size()) {
			addToRear(element);
		}
	}

	/**
	 * Returns true if the list is empty
	 * 
	 * @return true if the list is empty
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns the element at the given position. Returns null if idx >= size or
	 * idx < 0
	 * 
	 * @param idx
	 *            the index to be looked at
	 * @return the element at idx
	 */
	public T lookAtItemN(int idx) {
		resetIterator();
		if (idx < 0 || idx >= size) {
			return null;
		}

		for (int i = 0; i < idx; i++) {
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
	public void addToRear(T element) {
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
	 * Removes and returns the element at the given position, or null if idx is
	 * out of range
	 * 
	 * @param idx
	 *            the index to remove the element at
	 * @return the element that was removed, or null if idx > size
	 */
	public T remove(int idx) {
		resetIterator();
		if (idx == 0) {
			size--;
			T temp = head.data;
			head = head.next;
			return temp;
		} else if (idx < size() - 1 && idx > 0) {
			size--;
			for (int i = 0; i < idx - 1; i++) {
				next();
			}
			T temp = iterator.next.data;
			iterator.next = iterator.next.next;
			return temp;
		} else if (idx == size() - 1) {
			size--;
			for (int i = 0; i < idx - 1; i++) {
				next();
			}
			T temp = iterator.next.data;
			iterator.next = null;
			return temp;
		} else {
			return null;
		}
	}

	/**
	 * Moves the element at the given position ahead one spot in the list. Does
	 * nothing if the element is already at the front or if idx is out of range
	 * 
	 * @param idx
	 *            the index of the element to be moved up
	 */
	public void moveAheadOne(int idx) {
		resetIterator();
		if (idx == 1) {
			Node temp1 = head;
			Node temp2 = head.next;
			Node restOfList = head.next.next;
			head = temp2;
			head.next = temp1;
			head.next.next = restOfList;
		} else if (idx > 1 && idx < size()) {
			for (int i = 0; i < idx - 2; i++) {
				next();
			}
			Node temp1 = iterator.next;
			Node temp2 = iterator.next.next;
			Node restOfList = iterator.next.next.next;
			iterator.next = temp2;
			iterator.next.next = temp1;
			iterator.next.next.next = restOfList;
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
	 * A node of the MultiPurposeList iterator
	 * 
	 * @author MatthewLilly
	 * @author SamuelJessee
	 * 
	 */
	private class Node {
		/** The data held at this node **/
		public T data;

		/** The next node in the iterator **/
		public Node next;

		/** The constructor, takes an element and the next in the list **/
		public Node(T data, Node next) {
			this.data = data;
			this.next = next;
		}
	}
}