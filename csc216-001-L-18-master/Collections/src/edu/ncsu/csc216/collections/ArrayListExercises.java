package edu.ncsu.csc216.collections;

import java.util.ArrayList;

/**
 * Solutions to Exercise for the Java Collections Framework Lecture.
 * @author SarahHeckman
 */
public class ArrayListExercises {

	/**
	 * Removes any words ending in "s" from the given list.  The 
	 * list parameter is modified.
	 * @param list the list to remove plurals from
	 */
	public void removePlurals(ArrayList<String> list) {
		for (int i = 0; i < list.size(); i++) {
			String word = list.get(i);
			if (word.endsWith("s")) {
				list.remove(i);
				i--;
			}
		}
	}

	/**
	 * Returns the average of the integers in the given list.
	 * @param list a list of integers to average
	 * @return the average of the integers in the given list
	 */
	public double calculateAverage(ArrayList<Integer> list) {
		double sum = 0;
		for (int i = 0; i < list.size(); i++) {
			sum += list.get(i);
		}
		return sum / list.size();
	}
	
	/**
	 * Mystery ArrayList method.
	 * @param list a list of integers.
	 */
	public void mystery1(ArrayList<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			list.remove(i);
		}
	}
	
	/**
	 * Mystery ArrayList method.
	 * @param list a list of integers.
	 */
	public void mystery2(ArrayList<Integer> list) {
		int size = list.size();
		for (int i = 0; i < size; i++) {
			list.add(i, 42);
		}
	}
	
	/**
	 * Mystery ArrayList method. Same as mystery 2, but without
	 * the size variable.
	 * @param list a list of integers.
	 */
	public void mystery3(ArrayList<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			list.add(i, 42);
		}
	}

}
