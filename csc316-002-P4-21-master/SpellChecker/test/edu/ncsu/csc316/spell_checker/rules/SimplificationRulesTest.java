/**
 * 
 */
package edu.ncsu.csc316.spell_checker.rules;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * The SimplificationRules test
 * 
 * @author Selena Turner (snturner)
 * @author Samuel Jessee (sijessee)
 *
 */
public class SimplificationRulesTest {
	/**
	 * Tests SimplificationRules()
	 */
	@Test
	public void testSimplificationRules() {
		SimplificationRules rules = new SimplificationRules();
		String s = rules.removeTitle("happy");
		assertEquals("happy", s);
		s = rules.removePlural("non");
		assertEquals("non", s);
		s = rules.removePastTense("uhoh");
		assertEquals("uhoh", s);
	}
}
