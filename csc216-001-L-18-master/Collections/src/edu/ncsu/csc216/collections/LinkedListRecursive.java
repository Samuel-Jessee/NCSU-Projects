/**
 * 
 */
package edu.ncsu.csc216.collections;

/**
 * Creates a LinkedList and allows modifications to the list using Recursion
 * @author JeffColeman, GeneTodd, SamuelJessee
 *@param <E> the generic type of the list
 */
public class LinkedListRecursive<E> {
	
	/** The size of the list */
	private int size;
	/** Node that points to the front of the list */
	private ListNode front;
	

	/**
	 * Method used to get the size of the list
	 * @return size the size of the list
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Checks if the list is empty
	 * @return true if the list is empty
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Method used to get the data at a certain index. Calls the private get method from the ListNode class and 
	 * returns the result
	 * @param i the index of the list to get the data
	 * @return the data from the index
	 */
	public E get(int i) {
		if(i < 0 || i >= size()) {
			throw new IndexOutOfBoundsException();
		}
		
		return front.get(i);
	}

	/**
	 * Method used to add an element to the list. Throws a NullPointerException if the element is null. Creates a new
	 * ListNode at the front if the list is empty.  Adds to the back of the list otherwise by calling the private
	 * add method in ListNode
	 * @param element the element to add to the list
	 * @return true if the element is added
	 */
	public boolean add(E element) {
		if(element == null) {
			throw new NullPointerException();
		}
		if(size == 0) {
			front = new ListNode(element);
			size++;
		}
		else {
			front.add(element);
		}

		return true;
	}

	/**
	 * Method used to remove the item at the specified index in the list.  Throws an IndexOutOfBoundsException if
	 * the index is less than 0 or greater than or equal to the size. Makes a call to the private remove method of 
	 * ListNode if the index is not 0.
	 * @param i the index of the list
	 * @return the data of the removed element
	 */
	public E remove(int i) {
		if(i < 0 || i >= size()) {
			throw new IndexOutOfBoundsException();
		}
		else if(i == 0) {
			E temp = front.data;
			front = front.next;
			size--;
			return temp;
		}
		else {
			return front.remove(i);
		}
		
	}

	/**
	 * Sets the data at a node at index i to the specified element
	 * @param i the index to set the new data in
	 * @param element the element to put in the Node
	 * @return the data that was replaced
	 */
	public E set(int i, E element) {
		if(i < 0 || i >= size()) {
			throw new IndexOutOfBoundsException();
		}
		else if(element == null) {
			throw new NullPointerException();
		}
		return front.set(i, element);
		
		
	}

	/**
	 * Method that adds an element at a specified index.  Throws an IndexOutOfBoundsException if the index is less than 0 or greater
	 * than the size of the list. Throws a NullPointerException if the element is null. Adds the element to the front of the list if index is 0, otherwise
	 * calls the private add method from ListNode to add the element.
	 * @param i the index dictating where to add the element
	 * @param element the element to add to the list
	 */
	public void add(int i, E element) {
		if(i < 0 || i > size()) {
			throw new IndexOutOfBoundsException();
		}
		else if(element == null) {
			throw new NullPointerException();
		}
		if(i == 0) {
			ListNode temp = new ListNode(element, front);
			front = temp;
			size++;
		}
		else {
			front.add(i, element);
		}
		
	}
	
	/**
	 * Creates a node for the list to store data and a pointer to the next node in the list
	 * @author JeffColeman, GeneTodd, SamuelJessee
	 *
	 */
	private class ListNode {
		
		/** The data stored in the node */
		public E data;
		/** The next node in the list */
		public ListNode next;
		
		/**
		 * Constructs the ListNode with only data being based in and a null next)
		 * @param data the data to put in the node
		 */
		public ListNode(E data) {
			this(data, null);
		}
		
		/**
		 * Constructs a ListNode with the passed in data and a pointer for the next node in the list
		 * @param data the data put in the node
		 * @param next the next node in the list
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
		
		/**
		 * Removes a node from the list based on its index.  Uses recursion to get to the spot just before the index. Then it sets 
		 * the data to a temporary field for returning and changes next to the node following the next node (next.next).
		 * @param i the index of the node to remove
		 * @return temp the data removed from the list
		 */
		public E remove(int i) {
			if(i == 1) {
				E temp = next.data;
				next = next.next;
				size--;
				return temp;
			}
			else {
				return next.remove(i - 1);
			}
			
		}
		
		/**
		 * Method used to get the data at a specific index.
		 * @param i index where the data is stored
		 * @return data the data of the node at the index
		 */
		public E get(int i) {
			if(i == 0) {
				return data;
			}
			else {
				return next.get(i - 1);
			}
			
		}

		/**
		 * Method used to add an element at the specified index. 
		 * @param i the index of where the element is to be added in the list
		 * @param element the element to add to the list
		 */
		public void add(int i, E element) {
			if(i == 1) {
				next = new ListNode(element, next);
				size++;
			}
			else {
				next.add(i - 1, element);
			}
			
		}

		/**
		 * Method used to add an element to the end of the list. When there is no next node, a new node with the element
		 * as its data is created and size is incremented appropriately
		 * @param element the element to add to the list
		 */
		private void add(E element) {
			if(next == null) {
				next = new ListNode(element);
				size++;
			}
			else {
				next.add(element);
			}
		}
		
		/**
		 * Sets the data at the index to the element and returns the data that was overwritten
		 * @param i the index of the list to set the new element to
		 * @param element the element to put in the node
		 * @return temp the data that was in the node before it was changed to element
		 */
		private E set(int i, E element) {
			if(i == 0) {
				E temp = data;
				data = element;
				return temp;
			}
			else {
				return next.set(i - 1, element);
			}
		}
	}
	
	

}
