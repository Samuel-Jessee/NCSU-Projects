package edu.ncsu.csc216.course_manager.utils;

/**
 * ArrayStack class that is used to create and modify a Stack.  Uses an ArrayList with 
 * methods that allow only manipulation allowed on a stack
 * @author JeffColeman, GeneTodd, SamuelJessee
 *
 * @param <E> generic parameter type for the Stack
 */

public class ArrayStack<E> implements Stack<E> {

	/** ArrayList the is used to store the elements of the stack */
	private ArrayList<E> list;
	
	/**
	 * Constructor that initializes list to an empty ArrayList
	 */
	public ArrayStack() {
		list = new ArrayList<E>();
	}

	/**
	 * The push method allows for an element to be added to the top of the stack by adding to 
	 * the end of the ArrayList
	 * @param element the element to add to the top of the stack
	 */
	@Override
	public void push(E element) {
		list.add(list.size(), element);
	}

	/**
	 * The pop method allows for an element to be removed from the top of the stack.
	 * @return the element that is being removed from the stack
	 */
	@Override
	public E pop() {
		return list.remove(list.size() - 1);
	}

	/**
	 * The peek method allows the viewing of the element at the top of the stack without removing
	 * it.
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
