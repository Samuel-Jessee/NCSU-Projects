package edu.ncsu.csc216.flix_2.inventory;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests MovieDB
 * 
 * @author SamuelJessee
 * @author MatthewLilly
 *
 */
public class MovieDBTest {
	/**
	 * tests the MovieDB constructor
	 */
	@Test
	public void testMovieDB() {
		MovieDB movieDB = new MovieDB("moviesTest");
		assertEquals("Frozen", movieDB.findItemAt(0).getName());
		assertEquals("Gravity", movieDB.findItemAt(1).getName());
		assertEquals("Spectre", movieDB.findItemAt(2).getName());
		assertEquals("Warcraft", movieDB.findItemAt(3).getName());

	}

	/**
	 * tests the findItemAt method
	 */
	@Test
	public void testFindItemAt() {
		MovieDB movieDB = new MovieDB("moviesTest");
		assertEquals("Frozen", movieDB.findItemAt(0).getName());
		assertEquals("Gravity", movieDB.findItemAt(1).getName());
		assertEquals("Spectre", movieDB.findItemAt(2).getName());
		assertEquals("Warcraft", movieDB.findItemAt(3).getName());

		try {
			movieDB.findItemAt(4);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Frozen", movieDB.findItemAt(0).getName());
			assertEquals("Gravity", movieDB.findItemAt(1).getName());
			assertEquals("Spectre", movieDB.findItemAt(2).getName());
			assertEquals("Warcraft", movieDB.findItemAt(3).getName());
		}
	}

	/**
	 * tests the traverse method
	 */
	@Test
	public void testTraverse() {
		// this one has to check for alphabetical order
		MovieDB movieDB = new MovieDB("moviesTest");
		assertEquals("Frozen\nGravity\nSpectre (currently unavailable)\n" + "Warcraft (currently unavailable)\n",
				movieDB.traverse());

		// just to make sure the list is not in alphabetical order
		assertEquals("Frozen", movieDB.findItemAt(0).getName());
		assertEquals("Gravity", movieDB.findItemAt(1).getName());
		assertEquals("Spectre", movieDB.findItemAt(2).getName());
		assertEquals("Warcraft", movieDB.findItemAt(3).getName());
	}
}
