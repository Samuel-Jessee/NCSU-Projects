/**
 * 
 */
package edu.ncsu.csc316.spell_checker.manager;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc316.spell_checker.list.ArrayBasedList;
import edu.ncsu.csc316.spell_checker.list.WordList;

/**
 * The SpellCheckerManager test
 * 
 * @author Selena Turner (snturner)
 * @author Samuel Jessee (sijessee)
 *
 */
public class SpellCheckerManagerTest {
	/** The manager */
	private SpellCheckerManager s;

	/**
	 * Tests SpellCheckerManager()
	 */
	@Test
	public void testSpellCheckerManager() {
		// Tests String dictionary file name constructor
		s = new SpellCheckerManager("testDictionary.txt");
		String l = s.getHashTable().lookup("10th");
		assertEquals(null, l);
		l = s.getHashTable().lookup("no");
		assertEquals(null, l);
		// Tests WordList dictionary constructor
		WordList dict = new ArrayBasedList();
		dict.add("fairy");
		dict.add("never");
		dict.add("water");
		dict.add("cat");
		s = new SpellCheckerManager(dict);
		l = s.getHashTable().lookup("never");
		assertEquals(null, l);
		WordList w = new ArrayBasedList();
		w.add("naturedly");
		w.add("creatures");
		w.add("closing");
		w.add("closed");
		w.add("blades");
		w.add("added");
		w.add("thing");
		w.add("nor");
		w.add("ladder");
		w.add("Teachederingsly's");
		w.add("Baker's");
		w.add("a");
		w.add("quarter");
		w.add("red");
		w.add("squeak");
		w.add("turn");
		w.add("umbrella");
		w.add("this");
		w.add("veryly");
		w.add("water");
		w.add("abouioehly");
		w.add("abouioehly");
		w.add("natur");
		w.add("yellow");
		w.add("naturesed");
		w.add("hashhing");
		w.add("abetting");
		w.add("hashhes");
		w.add("hashher");
		w.add("hashhs");
		w.add("snoder");
		w.add("thudd");
		w.add("analyses");
		w.add("yellowssssssssssssss");
		w.add("zoo");
		s = new SpellCheckerManager("testDictionary.txt");
		WordList n = s.spellCheck(w);
		l = s.getHashTable().lookup("asjdak");
		assertEquals("asjdak", l);
		assertEquals("WordList[abouioehly, hashher, hashhes, hashhing, hashhs, natur, snoder]", n.toString());
		WordList list = s.getInputText("smallText.txt");
		n = s.spellCheck(list);
		assertEquals(0, n.size()); // Should all be correct words
		try {
			list = s.getInputText("emptyText.txt");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), e.getMessage());
		}
		try {
			s = new SpellCheckerManager("notReal.txt");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), e.getMessage());
		}

		try {
			s.getInputText("non.txt");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), e.getMessage());
		}
	}
}
