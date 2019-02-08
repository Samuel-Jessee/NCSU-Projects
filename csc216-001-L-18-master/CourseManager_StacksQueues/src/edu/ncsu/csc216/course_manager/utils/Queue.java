package edu.ncsu.csc216.course_manager.utils;

/**
 * The Queue interface that gives the template for a class to create a queue
 * @author JeffColeman, GeneTodd, SamuelJessee
 * @param <E>
 */

public interface Queue<E> {
	/**
	 * See ArrayQueue and LinkedQueue for implementation
	 * @param element the element to add to the queue
	 */
	public void enqueue(E element);
	
	/**
	 * See ArrayQueue and LinkedQueue for implementation
	 * @return the element removed for the queue
	 */
	public E dequeue();
	
	/**
	 * Check ArrayQueue and LinkedQueue for implementation
	 * @return true if the queue is empty
	 */
	public boolean isEmpty();
}
