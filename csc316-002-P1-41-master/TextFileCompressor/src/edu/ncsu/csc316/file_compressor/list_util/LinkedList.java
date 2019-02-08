package edu.ncsu.csc316.file_compressor.list_util;

/**
 * A list class that supports the underlying list operations for the text
 * compressor.
 * 
 * @author Samuel Jessee (sijessee)
 * 
 */
public class LinkedList {

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
	public String next() {
		if (hasNext()) {
			String temp = iterator.data;
			iterator = iterator.next;
			return temp;
		} else {
			return null;
		}
	}

	/**
	 * Adds an item to the front of the list, removes duplicates, and returns
	 * the index of the word if it was already in the list. Otherwise returns
	 * the word
	 * 
	 * @param s
	 *            the element to be added
	 * @return index of the word, or the word itself
	 */
	public String addWord(String s) {
		resetIterator();
		if (isEmpty()) {
			size++;
			head = new Node(s, null);
			iterator = head;
			return s;
		}
		int n = search(s);
		if (n == -1) {
			size++;
			Node temp = head;
			head = new Node(s, temp);
			return s;
		} else if (n == 0) {
			s = "" + (n + 1);
			return s;
		} else {
			remove(n);
			size++;
			Node temp = head;
			head = new Node(s, temp);
			s = "" + (n + 1);
			return s;
		}
	}

	/**
	 * Retrieves the word at the given index, moves it to the front of the list,
	 * and returns the word
	 * 
	 * @param n
	 *            index to retrieve
	 * @return the word
	 */
	public String retrieve(int n) {
		if ((n - 1) == 0) {
			return head.data;
		} else {
			String s = remove(n - 1);
			size++;
			Node temp = head;
			head = new Node(s, temp);
			return s;
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
	public String lookAtItemN(int idx) {
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
	 * Removes and returns the element at the given position, or null if idx is
	 * out of range
	 * 
	 * @param idx
	 *            the index to remove the element at
	 * @return the element that was removed, or null if idx > size
	 */
	public String remove(int idx) {
		resetIterator();
		if (idx == 0) {
			size--;
			String temp = head.data;
			head = head.next;
			return temp;
		} else if (idx < size() - 1 && idx > 0) {
			size--;
			for (int i = 0; i < idx - 1; i++) {
				next();
			}
			String temp = iterator.next.data;
			iterator.next = iterator.next.next;
			return temp;
		} else if (idx == size() - 1) {
			size--;
			for (int i = 0; i < idx - 1; i++) {
				next();
			}
			String temp = iterator.next.data;
			iterator.next = null;
			return temp;
		} else {
			return null;
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
	 * searches the LinkedList for a word, and returns the index if found
	 * 
	 * @param s
	 *            String to search for
	 * @return index of word in list, or -1 if not found
	 */
	public int search(String s) {
		if (isEmpty()) {
			return -1;
		}
		for (int i = 0; i < size(); i++) {
			if (lookAtItemN(i).equals(s)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * A node of the LinkedList iterator
	 * 
	 * @author Samuel Jessee (sijessee)
	 * 
	 */
	private class Node {
		/** The data held at this node **/
		public String data;

		/** The next node in the iterator **/
		public Node next;

		/** The constructor, takes an element and the next in the list **/
		public Node(String data, Node next) {
			this.data = data;
			this.next = next;
		}
	}
}