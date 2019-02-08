package edu.ncsu.csc216.course_manager.utils;


import java.util.NoSuchElementException;

/**
 * LinkedQueue class that uses a LinkedList to implement a queue
 * @author JeffColeman, GeneTodd, SamuelJessee
 * @param <E> the generic parameter for the LinkedQueue
 */

public class LinkedQueue<E> implements Queue<E> {

	/** LinkedList that is used to store the elements of the queue */
	LinkedList<E> list;
	
	/**
	 * Returns size of queue
	 * @return The number of elements in the queue
	 */
	public int size() {
		return list.size();
	}
	
	/**
	 * Checks if object is in the queue
	 * @return true if the object is in the queue
	 */
	public boolean contains(E obj) {
		for(int i = 0; i < list.size(); i++) {
			if(obj.equals(list.get(i)))
				return true;
		}
		return false;
	}
	
	/**
	 * Constructor that initializes list to a new empty LinkedList
	 */
	public LinkedQueue() {
		list = new LinkedList<E>();
	}

	/**
	 * Adds an element to the end of the queue using the add method from LinkedList
	 * @param element the element to add to the queue
	 */
	@Override
	public void enqueue(E element) {
		list.add(list.size(), element);
	}

	/**
	 * Removes the element at the front of the queue using the remove method from LinkedList.
	 * Throws a NoSuchElementException if the queue is empty.
	 * @return the element removed from the queue
	 */
	@Override
	public E dequeue() {
		if(this.isEmpty())
			throw new NoSuchElementException();
		return list.remove(0);
	}

	/**
	 * Checks if the queue is empty
	 * @return true if the queue is empty
	 */
	@Override
	public boolean isEmpty() {
		if(list.size() == 0) {
			return true;
		}
		return false;
	}

}
