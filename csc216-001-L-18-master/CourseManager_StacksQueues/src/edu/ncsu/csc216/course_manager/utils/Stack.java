package edu.ncsu.csc216.course_manager.utils;
/**
 * The Stack interface that gives the template for a class to create a stack
 * @author JeffColeman, GeneTodd, SamuelJessee
 *
 * @param <E>
 */

public interface Stack<E> {
	/**
	 * See ArrayStack and LinkedStack for implementation
	 * @param element the element to push to the stack
	 */
	public void push(E element);
	/**
	 * See ArrayStack and LinkedStack for implementation
	 * @return the element popped off of the stack
	 */
	public E pop();
	/**
	 * See ArrayStack and LinkedStack for implementation
	 * @return the element that is at the top of the stack
	 */
	public E peek();
	/**
	 * See ArrayStack and LinkedStack for implementation
	 * @return true if stack is empty
	 */
	public boolean isEmpty();
}
