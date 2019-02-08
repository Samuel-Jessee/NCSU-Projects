package edu.ncsu.csc216.course_manager.utils;



import java.util.NoSuchElementException;

/**
 * ArrayQueue class that uses an array to implement a queue
 * @author JeffColeman, GeneTodd, SamuelJessee
 * @param <E> the generic type parameter of ArrayQueue
 */

public class ArrayQueue<E> implements Queue<E> {

	/** ArrayList used to store the elements of the queue */
	ArrayList<E> list;
	
	/**
	 * Constructor that initializes list to a new ArrayList
	 */
	public ArrayQueue() {
		list = new ArrayList<E>();
	}

	/**
	 * Adds an element to the end of the queue
	 * @param element the element to add to the queue
	 */
	@Override
	public void enqueue(E element) {
		list.add(list.size(), element);
	}

	/**
	 * Removes the element at the front of the queue. Throws a NoSuchElementException if the queue
	 * is empty.
	 * @return the element that is removed from the queue
	 */
	@Override
	public E dequeue() {
		if(this.isEmpty())
			throw new NoSuchElementException();
		return list.remove(0);
	}

	/**
	 * Checks to see if the queue is empty
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
