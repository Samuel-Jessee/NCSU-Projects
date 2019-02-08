package edu.ncsu.csc216.flix_2.inventory;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests Movie
 * 
 * @author SamuelJessee
 * @author MatthewLilly
 *
 */
public class MovieTest {
	/**
	 * tests the Movie constructor
	 */
	@Test
	public void testMovie() {
		Movie movie1 = new Movie("1 movie1");
		assertTrue(movie1.getName().equals("movie1"));
		assertTrue(movie1.isAvailable());

		Movie movie2 = new Movie("-1 movie2");
		assertTrue(movie2.getName().equals("movie2"));
		assertFalse(movie2.isAvailable());
	}

	/**
	 * tests the getDisplayName method
	 */
	@Test
	public void testGetDisplayName() {
		Movie movie1 = new Movie("1 movie1");
		assertTrue(movie1.getDisplayName().equals("movie1"));

		Movie movie2 = new Movie("-1 movie2");
		assertTrue(movie2.getDisplayName().equals("movie2 (currently unavailable)"));
	}

	/**
	 * tests the compareToByName method
	 */
	@Test
	public void testCompareToByName() {
		Movie movie1 = new Movie("1 an movie1");
		Movie movie2 = new Movie("1 an movie1");

		assertTrue(movie1.compareToByName(movie2) == 0);

		movie2 = new Movie("1 the movie12");
		assertTrue(movie1.compareToByName(movie2) < 0);

		movie2 = new Movie("1 a movie");
		assertTrue(movie1.compareToByName(movie2) > 0);

		movie2 = new Movie("1 a movie2");
		assertTrue(movie1.compareToByName(movie2) < 0);
	}

	/**
	 * tests the backToInventory method
	 */
	@Test
	public void testBackToInventory() {
		Movie movie = new Movie("0 a movie");
		assertFalse(movie.isAvailable());
		movie.backToInventory();
		assertTrue(movie.isAvailable());
	}

	/**
	 * tests the removeOneCopyFromInventory method
	 */
	@Test
	public void testRemoveOneCopyFromInventory() {
		Movie movie = new Movie("1 a movie");
		assertTrue(movie.isAvailable());
		movie.removeOneCopyFromInventory();
		assertFalse(movie.isAvailable());

		movie = new Movie("0 a movie");
		assertFalse(movie.isAvailable());

		try {
			movie.removeOneCopyFromInventory();
			fail();
		} catch (IllegalStateException e) {
			assertFalse(movie.isAvailable());
		}
	}
}
