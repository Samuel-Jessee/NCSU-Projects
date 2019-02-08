/**
 * 
 */
package edu.ncsu.csc316.spell_checker.counter;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The counter tests.
 * 
 * @author Selena Turner (snturner)
 * @author Samuel Jessee (sijessee)
 */
public class CounterTest {

	/**
	 * Set totals to test values.
	 */
	@Before
	public void setUp() {
		Counter.setTotalWordsChecked(2);
		Counter.setTotalMisspelled(1);
		Counter.setTotalProbes(10);
		Counter.setTotalLookups(5);
	}

	/**
	 * Set totals to 0 after tests.
	 */
	@After
	public void tearDown() {
		Counter.setTotalWordsChecked(0);
		Counter.setTotalMisspelled(0);
		Counter.setTotalProbes(0);
		Counter.setTotalLookups(0);
	}

	/**
	 * Test decrement methods.
	 */
	@Test
	public void testDecrement() {
		assertTrue(Counter.totalWordsChecked() == 2);
		assertTrue(Counter.totalMisspelled() == 1);
		assertTrue(Counter.totalProbes() == 10);
		assertTrue(Counter.totalLookups() == 5);

		Counter.decrementWordsChecked();
		Counter.decrementMisspelled();
		Counter.decrementProbes();
		Counter.decrementLookups();

		assertTrue(Counter.totalWordsChecked() == 1);
		assertTrue(Counter.totalMisspelled() == 0);
		assertTrue(Counter.totalProbes() == 9);
		assertTrue(Counter.totalLookups() == 4);
	}

	/**
	 * Test increment methods.
	 */
	@Test
	public void testIncrement() {
		assertTrue(Counter.totalWordsChecked() == 2);
		assertTrue(Counter.totalMisspelled() == 1);
		assertTrue(Counter.totalProbes() == 10);
		assertTrue(Counter.totalLookups() == 5);

		Counter.incrementWordsChecked();
		Counter.incrementMisspelled();
		Counter.incrementProbes();
		Counter.incrementLookups();

		assertTrue(Counter.totalWordsChecked() == 3);
		assertTrue(Counter.totalMisspelled() == 2);
		assertTrue(Counter.totalProbes() == 11);
		assertTrue(Counter.totalLookups() == 6);
	}

	/**
	 * Test lookupProbes and wordProbes.
	 */
	@Test
	public void testAverages() {
		assertTrue(Counter.totalWordsChecked() == 2);
		assertTrue(Counter.totalMisspelled() == 1);
		assertTrue(Counter.totalProbes() == 10);
		assertTrue(Counter.totalLookups() == 5);

		assertTrue(Counter.lookupProbes() == 2);
		assertTrue(Counter.wordProbes() == 5);

		assertTrue(Counter.totalWordsChecked() == 2);
		assertTrue(Counter.totalMisspelled() == 1);
		assertTrue(Counter.totalProbes() == 10);
		assertTrue(Counter.totalLookups() == 5);
	}
}
