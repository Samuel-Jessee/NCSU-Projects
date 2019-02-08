package edu.ncsu.csc216.collections;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * The LinkedList class creates a LinkedList that can be iterated through using the internal ListIterator<E>
 * class. It extends AbstractSequentialList<E> to make use of the get, add, remove, and set methods
 * @author JeffColeman, GeneTodd, SamuelJessee
 * 
 * @param <E> generic object parameter for LinkedList
 */
public class LinkedList<E> extends AbstractSequentialList<E> {
	
	/** Node to keep track of the front of the list */
	private ListNode front;
	/** Node to keep track of the back of the list */
	private ListNode back;
	/** Size of the list */
	private int size;
	
	/** Empty List Constructor */
	public LinkedList() {
		front = new ListNode(null);
		back = new ListNode(null);
		front.next = back;
		back.prev = front;
		size = 0;
	}

	/**
	 * Creates a listIterator to iterate through the list. Returns the elements returned by the ListIterator
	 * 
	 * @param idx the index of the list to start the iterator at
	 */
	@Override
	public ListIterator<E> listIterator(int idx) {
		if (idx < 0 || idx > size()) {
			throw new IndexOutOfBoundsException();
		}
		LinkedListIterator li = new LinkedListIterator(idx);		
		return li;
	}

	/** 
	 * Method to get the size of the LinkedList
	 * @return Returns number of data in list 
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Class to create a ListNode for adding data to the LinkedList
	 * @author JeffColeman, GeneTodd, SamuelJessee
	 *
	 */
	private class ListNode {
		
		/** Information stored in the node */
		public E data;
		/** Location of previous node */
		public ListNode prev;
		/** Location of next node */
		public ListNode next;
		
		/** Constructor with no adjacent nodes */
		public ListNode(E data) {
			this(data, null, null);
		}
		
		/** Constructor with adjacent nodes */
		public ListNode(E data, ListNode prev, ListNode next) {
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
	}
	
	/**
	 * The LinkedListIterator class thats implements ListIterator. This is used to iterate through the LinkedList
	 * and provides methods to make changes to the list.
	 * @author JeffColeman, GeneTodd, SamuelJessee
	 *
	 */
	private class LinkedListIterator implements ListIterator<E> {
		
		/** The node following the iterator */
		public ListNode next;
		/** The node preceding the iterator */
		public ListNode prev;
		/** Stores the next node for returning after changing the list */
		public ListNode last;
		/**The index for the node following the iterator's position */
		public int nextIndex;
		/** The index for the node preceding the iterator's position */
		public int prevIndex;
		
		/** Constructs a listIterator at index 0 if no index is provided */
		@SuppressWarnings("unused")
		public LinkedListIterator() {
			this(0);
		}
		
		/**
		 * Constructs the linkedListIterator for the given index. Initializes the nodes based on what is around
		 * the index
		 * @param index the index to start the iterator
		 */
		public LinkedListIterator(int index) {
			last = null;
			next = front.next;
			nextIndex = 0;
			prev = front;
			prevIndex = -1;
			
			while(nextIndex < index) {
				next = next.next;
				nextIndex++;
				prev = prev.next;
				prevIndex++;
			}
		}
		
		

		/**
		 * Adds an element to the list and shifts the previous and next nodes for the iterator to compensate for
		 * this addition.
		 * 
		 * @param data the data to be added to the new ListNode
		 */
		@Override
		public void add(E data) {
			if(data == null) {
				throw new IllegalArgumentException();
			}
			if(size() == 0) {
				next = new ListNode(data);
			}
			ListNode node = new ListNode(data, prev, next);
			prev.next = node;
			next.prev = node;
			nextIndex++;
			prevIndex++;
			prev = node;
			last = null;
			size++;
		}

		/**
		 * Checks that the iterator has a node following it
		 * 
		 * @return true if there is a node following the iterator
		 */
		@Override
		public boolean hasNext() {
			return next != null;
		}

		/**
		 * Checks that the iterator has a node preceding it
		 * 
		 * @return true if there is a node preceding the iterator
		 */
		@Override
		public boolean hasPrevious() {
			return prev != null;
		}

		/**
		 * Moves the iterator forward one position and adjusts the previous and next nodes based on the 
		 * new position
		 * 
		 * @return last.data the data in the node that the iterator moved through
		 */
		@Override
		public E next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			last = next;
			next = next.next;
			nextIndex++;
			prev = prev.next;
			prevIndex++;
			return last.data;
		}

		/**
		 * Used to get the index of the node following the iterator
		 * 
		 * @return nextIndex the index of the next node
		 */
		@Override
		public int nextIndex() {
			return nextIndex;
		}

		/**
		 * Moves the iterator backwards one position and adjusts the previous and next nodes based on the
		 * new position
		 * 
		 * @return last.data the data in the node the iterator moved through to reach its new position
		 */
		@Override
		public E previous() {
			if(!hasPrevious()) {
				throw new NoSuchElementException();
			}
			last = prev;
			next = next.prev;
			nextIndex--;
			prev = prev.prev;
			prevIndex--;
			return last.data;
		}

		/**
		 * Used to get the index of the node preceding the iterator's position
		 * 
		 * @return prevIndex the index of the node preceding the iterator
		 */
		@Override
		public int previousIndex() {
			return prevIndex;
		}

		/**
		 * Removes a node from the list. Resets the previous and next nodes for the iterator to the new nodes
		 * surrounding its position. Decrements the size by 1 if a node is removed.
		 */
		@Override
		public void remove() {
			if(last == null) {
				throw new NoSuchElementException();
			}
			if(size() == 0 || nextIndex > size()) {
				throw new IndexOutOfBoundsException();
			}
			last.prev.next = last.next;
			last.next.prev = last.prev;
			last = null;
			size--;
		}

		/**
		 * Sets the data for the node following the iterator to the passed in data
		 * 
		 * @param data the data to set the data in the node to
		 */
		@Override
		public void set(E data) {
			if(data == null) {
				throw new IllegalArgumentException();
			}
			if(last == null) {
				throw new NoSuchElementException();
			}
			if(size() == 0 || nextIndex > size()) {
				throw new IndexOutOfBoundsException();
			}
			last.data = data;
		}
		
	}
	
}
