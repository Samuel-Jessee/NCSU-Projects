package edu.ncsu.csc216.flix_2.customer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import edu.ncsu.csc216.flix_2.inventory.Movie;

/**
 * tests the Customer class
 * 
 * @author MatthewLilly
 * @author SamuelJessee
 * 
 */
public class CustomerTest {
	/**
	 * tests the Customer constructor
	 */
	@Test
	public void testCustomer() {
		Customer c = new Customer("id", "pw", 2);
		assertEquals("id", c.getId());
		assertTrue(c.verifyPassword("pw"));

		c = new Customer("id2", "pw2", -2);
		assertEquals("id2", c.getId());
		assertTrue(c.verifyPassword("pw2"));
	}

	/**
	 * tests the verifyPassword method
	 */
	@Test
	public void testVerifyPassword() {
		// also tests if given password doesn't match for conditional coverage
		Customer c = new Customer("id", "pw", 2);
		assertFalse(c.verifyPassword("pq"));
		assertTrue(c.verifyPassword("pw"));
	}

	/**
	 * tests the compareToByName method
	 */
	@Test
	public void testCompareToByName() {
		Customer c = new Customer("aa", "pw", 2);
		Customer c2 = new Customer("aa", "pw", 2);
		assertTrue(c.compareToByName(c2) == 0);

		c = new Customer("bb", "pw", 2);
		assertTrue(c.compareToByName(c2) > 0); // might be the other way around

		c = new Customer("aaa", "pw", 2);
		assertTrue(c.compareToByName(c2) > 0); // longer names come second?

		c2 = new Customer("aba", "pw", 2);
		assertTrue(c.compareToByName(c2) < 0);
	}

	/**
	 * tests the login method
	 */
	@Test
	public void testLogin() {
		Customer c = new Customer("id", "pw", 2);
		Movie m1 = new Movie("1 movie1");
		Movie m2 = new Movie("1 movie2");
		Movie m3 = new Movie("1 movie3");
		Movie m4 = new Movie("1 movie4");
		Movie m5 = new Movie("1 movie5");

		assertEquals("movie1", m1.getName());
		assertEquals("", c.traverseAtHomeQueue());
		assertEquals("", c.traverseReserveQueue());
		c.reserve(m1);
		c.reserve(m2);
		c.reserve(m3);
		c.reserve(m4);
		c.reserve(m5);
		assertEquals("movie1\nmovie2\n", c.traverseAtHomeQueue());
		c.login();
		assertEquals("movie1\nmovie2\n", c.traverseAtHomeQueue());

		// maybe some more later
	}

	/**
	 * tests the traverseReserveQueue method
	 */
	@Test
	public void testTraverseReserveQueue() {
		Customer c = new Customer("id", "pw", 2);
		c.reserve(new Movie("1 movie1"));
		c.reserve(new Movie("1 movie2"));
		c.reserve(new Movie("1 movie3"));
		c.reserve(new Movie("1 movie4"));
		c.reserve(new Movie("1 movie5"));
		c.reserve(new Movie("1 movie6"));
		assertEquals("movie3\nmovie4\nmovie5\nmovie6\n", c.traverseReserveQueue());
		c.login();
		assertEquals("movie3\nmovie4\nmovie5\nmovie6\n", c.traverseReserveQueue());
	}

	/**
	 * tests the returnDVD method
	 */
	@Test
	public void testReturnDVD() {

		Customer c = new Customer("id", "pw", 2);
		Movie movie1 = new Movie("0 movie1");
		Movie movie2 = new Movie("1 movie2");
		assertFalse(movie1.isAvailable());
		assertTrue(movie2.isAvailable());
		c.reserve(movie1);
		assertEquals("", c.traverseAtHomeQueue());
		assertEquals("movie1\n", c.traverseReserveQueue());
		c.reserve(movie2);
		assertEquals("movie2\n", c.traverseAtHomeQueue());

		assertFalse(movie1.isAvailable());
		assertFalse(movie2.isAvailable());

		c.returnDVD(0);
		assertTrue(movie2.isAvailable());

	}

	/**
	 * tests the moveAheadOneInReserves method
	 */
	@Test
	public void testMoveAheadOneInReserves() {
		Customer c = new Customer("id", "pw", 2);
		assertEquals("", c.traverseReserveQueue());
		c.reserve(new Movie("1 movie1"));
		c.reserve(new Movie("1 movie2"));
		c.reserve(new Movie("1 movie3"));
		c.reserve(new Movie("1 movie4"));
		c.moveAheadOneInReserves(1);
		assertEquals("movie4\nmovie3\n", c.traverseReserveQueue());
		c.login();
		assertEquals("movie1\nmovie2\n", c.traverseAtHomeQueue());
	}

	/**
	 * tests the unReserve method
	 */
	@Test
	public void testUnReserve() {
		Customer c = new Customer("id", "pw", 1);
		c.reserve(new Movie("1 movie1"));
		c.reserve(new Movie("1 movie3"));
		c.reserve(new Movie("1 movie4"));
		c.reserve(new Movie("1 movie5"));
		c.unReserve(0);
		assertEquals("movie4\nmovie5\n", c.traverseReserveQueue());
	}
}