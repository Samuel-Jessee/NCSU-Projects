/**
 * 
 */
package edu.ncsu.csc216.collections;

import java.util.AbstractList;

/**
 * Class that creates a linked list and allows modifications to the list
 * @author JeffColeman, GeneTodd, SamuelJessee
 */

public class LinkedListAL<E> extends AbstractList<E> {
	
	/** node that stores the first node in the list */
	private Node<E> front;
	/** size of the list */
	private int size;
	
	/** Default constructor for the list. Initializes front to null and size to 0 */
	public LinkedListAL() {
		front = null;
		size = 0;
	}

	/**
	 * Get method to get the data at a specified index in the linked list
	 * @param index index to retrieve data from
	 * @return temp.data data that is returned from the list
	 */
	@Override
	public E get(int index) {
		Node<E> temp = front;
		if(index >= size() || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		for(int i = 0; i < index; i++) {
			temp = temp.next;
		}
		return temp.data;
	}

	/**
	 * Set method used to set the data at a specified index to the specified element
	 * @param index index of the list to set the new element
	 * @param element element to set the data in the list to
	 * @return removes the data that was previously at the index and changed to the new element
	 */
	@Override
	public E set(int index, E element) {
		if(element == null) {
			throw new NullPointerException();
		}
		if(index >= size() || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		Node<E> temp = front;
		E removed = null;
		for(int i = 0; i < index; i++) {
			temp = temp.next;
		}
		removed = temp.data;
		temp.data = element;
		return removed;
	}

	/**
	 * Add method that adds an element at the specified index. Updates the next references to keep the 
	 * list linked in the correct order
	 * @param index the index of where the element is to be added
	 * @param element the element to add to the list
	 */
	@Override
	public void add(int index, E element) {
		if(element == null) {
			throw new NullPointerException();
		}
		if(index > size() || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		Node<E> temp = front;
		Node<E> newNode = new Node<E>(element);
		if(index == 0) {
			if(size() == 0) {
				front = new Node<E>(element);
				size++;
				return;
			} else {
				newNode.next = front;
				front = newNode;
				size++;
				return;
			}
			
		}
		for(int i = 0; i < index - 1; i++) {
			temp = temp.next;
		}
		if(temp.next != null) {
			newNode.next = temp.next;
		}
		temp.next = newNode;
		size++;
	}

	/**
	 * Method used to remove an element from the list at the specified index. Updates the .next of the
	 * Node in front of the index to reference the Node after the index. This eliminates the Node at the 
	 * index from the list.
	 * @param index the index of the element to remove
	 * @return removed.data the value that was removed from the list
	 */
	@Override
	public E remove(int index) {
		
		if(index >= size() || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		Node<E> temp = front;
		Node<E> removed = null;
		if(index == 0) {
			removed = front;
			front = temp.next;
			size--;
			return removed.data;
		}
		for(int i = 0; i < index - 1; i++) {
			temp = temp.next;
		}
		removed = temp.next;
		if(index == size() - 1) {
			temp.next = null;
			size--;
			return removed.data;
		}
		temp.next = temp.next.next;
		size--;
		return removed.data;

	}

	/**
	 * Used to get the size of the list
	 * @return size the size of the list
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * The Node class that is used to construct a node with the data that will be added to
	 * the list
	 * @author JeffColeman, GeneTodd, SamuelJessee
	 */
	@SuppressWarnings("hiding")
	private class Node<E> {
		/** the data that will be stored in the node */
		private E data;
		private Node<E> next;
		
		/**
		 * Constructs the node when only data is provided. Sets the local data field to what is 
		 * passed in.
		 * @param data the element that is to be stored in the Node
		 */
		public Node(E data) {
			this.data = data;
		}
		
		/**
		 * Constructs a Node using data and a known next Node.
		 * @param data the element that is to be stored in the Node
		 * @param next the Node the that created Node will reference with .next
		 */
		@SuppressWarnings("unused")
		public Node(E data, Node<E> next) {
			this(data);
			this.next = next;	
		}
		
	}

}
