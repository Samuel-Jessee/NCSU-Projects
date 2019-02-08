package edu.ncsu.csc216.course_manager.utils;

/**
 * LinkedStack class that is used to create and modify a Stack.  Uses a LinkedList with 
 * methods that allow only manipulation allowed on a stack
 * @author JeffColeman, GeneTodd, SamuelJessee
 *
 * @param <E> generic parameter type for the Stack
 */

public class LinkedStack<E> implements Stack<E> {
	
	/**LinkedList that is used to store the elements of the stack */
	LinkedList<E> list;

	/**
	 * Constructor that initializes list to a new empty linked list
	 */
	public LinkedStack() {
		list = new LinkedList<E>();
	}

	/**
	 * Adds a new element to the top of the stack by using the add function of a linked list
	 * @param element the element to add to the stack
	 */
	@Override
	public void push(E element) {
		list.add(list.size(), element);
	}

	/**
	 * Removes the element at the top of the stack by using the remove function of a linked list
	 * @return the element being removed from the stack
	 */
	@Override
	public E pop() {
		return list.remove(list.size() - 1);
	}

	/**
	 * Allows the viewing of the element at the top of the stack without removing the element
	 * @return the element at the top of the stack
	 */
	@Override
	public E peek() {
		return list.get(list.size() - 1);
	}

	/**
	 * Checks to see if the stack is empty
	 * @return true if the stack is empty
	 */
	@Override
	public boolean isEmpty() {
		if(list.size() == 0) {
			return true;
		}
		return false;
	}

}
