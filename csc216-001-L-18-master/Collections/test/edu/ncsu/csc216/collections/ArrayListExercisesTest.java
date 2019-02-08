package edu.ncsu.csc216.collections;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class ArrayListExercisesTest {

	@Test
	public void testRemovePlurals() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("rebels");
		list.add("confused");
		list.add("cursed");
		list.add("aquatic");
		list.add("virtual");
		list.add("hellfire");
		list.add("spheres");
		list.add("falls");
		list.add("eat");
		list.add("cotton");
		
		ArrayListExercises ex = new ArrayListExercises();
		ex.removePlurals(list);
		assertEquals(7, list.size());
		assertEquals("confused", list.get(0));
		assertEquals("cursed", list.get(1));
		assertEquals("aquatic", list.get(2));
		assertEquals("virtual", list.get(3));
		assertEquals("hellfire", list.get(4));
		assertEquals("eat", list.get(5));
		assertEquals("cotton", list.get(6));
	}
	
	@Test
	public void testCalculateAverage() {
		int [] array = {32, 62, 22, 74, 64, 55, 90, 32, 12, 17};
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
		
		ArrayListExercises ex = new ArrayListExercises();
		assertEquals(46.0, ex.calculateAverage(list), 0.001);
	}
	
	@Test
	public void testMystery1() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i <= 10; i++) {
			list.add(10 * i);
		}
		
		ArrayListExercises ex = new ArrayListExercises();
		ex.mystery1(list);
		assertEquals(5, list.size());
		assertEquals(Integer.valueOf(20), list.get(0));
		assertEquals(Integer.valueOf(40), list.get(1));
		assertEquals(Integer.valueOf(60), list.get(2));
		assertEquals(Integer.valueOf(80), list.get(3));
		assertEquals(Integer.valueOf(100), list.get(4));
	}
	
	@Test
	public void testMystery2() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i <= 5; i++) {
			list.add(2 * i);
		}
		
		ArrayListExercises ex = new ArrayListExercises();
		ex.mystery2(list);
		assertEquals(10, list.size());
		int [] exp = {42, 42, 42, 42, 42, 2, 4, 6, 8, 10};
		for (int i = 0; i < list.size(); i++) {
			assertEquals(Integer.valueOf(exp[i]), list.get(i));
		}
	}
	
	//Infinite loop
//	@Test
//	public void testMystery3() {
//		ArrayList<Integer> list = new ArrayList<Integer>();
//		for (int i = 1; i <= 5; i++) {
//			list.add(2 * i);
//		}
//		
//		ArrayListExercises ex = new ArrayListExercises();
//		ex.mystery3(list);
//	}

}
