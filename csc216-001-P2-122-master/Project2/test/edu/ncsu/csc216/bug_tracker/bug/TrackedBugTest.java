/**
 * 
 */
package edu.ncsu.csc216.bug_tracker.bug;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.bug_tracker.command.Command;
import edu.ncsu.csc216.bug_tracker.command.Command.Resolution;
import edu.ncsu.csc216.bug_tracker.xml.Bug;
import edu.ncsu.csc216.bug_tracker.xml.NoteList;

/**
 * Tests the TrackedBug class
 * 
 * @author MatthewGibson
 * @author SamuelJessee
 */
public class TrackedBugTest {
	/** Bugs used for testing */
	private TrackedBug bug1;
	private TrackedBug bug2;
	private Bug xmlBug;
	private TrackedBug bug5;
	private NoteList notes;
	private ArrayList<String> notes2;
	private ArrayList<String> notes3;
	private Command confirm;
	private Command vote;
	private Command possession;
	private Command resolved1;
	private Command resolved2;
	private Command verified;
	private Command reopen;
	private static final String SUMMARY1 = "this is a bug";
	private static final String REPORTER1 = "krog the orc";
	private static final String SUMMARY2 = "this is another bug";
	private static final String REPORTER2 = "krogg the camel";
	private static final String VALID_NOTE_STRING = "confirm note\n------\npossession note\n------\n";

	/**
	 * Sets up a bug for testing
	 */
	@Before
	public void setUp() {
		TrackedBug.setCounter(0);
		bug1 = new TrackedBug(SUMMARY1, REPORTER1);
		bug2 = new TrackedBug(SUMMARY2, REPORTER2);
		confirm = new Command(Command.CommandValue.CONFIRM, null, null, "confirm note");
		vote = new Command(Command.CommandValue.VOTE, null, null, "vote note");
		possession = new Command(Command.CommandValue.POSSESSION, REPORTER1, null, "possession note");
		resolved1 = new Command(Command.CommandValue.RESOLVED, null, Command.Resolution.FIXED, "resolved fixed note");
		resolved2 = new Command(Command.CommandValue.RESOLVED, null, Command.Resolution.DUPLICATE,
				"resolved duplicate note");
		verified = new Command(Command.CommandValue.VERIFIED, null, null, "verified note");
		reopen = new Command(Command.CommandValue.REOPEN, null, null, "reopen note");
		xmlBug = new Bug();
		xmlBug.setConfirmed(true);
		xmlBug.setId(3);
		notes = new NoteList();
		notes2 = new ArrayList<String>();
		notes3 = new ArrayList<String>();
		notes3.add("confirm note");
		notes3.add("possession note");
		xmlBug.setNoteList(notes);
		xmlBug.setOwner("Bob");
		xmlBug.setReporter("Joe");
		xmlBug.setResolution(Command.R_FIXED);
		xmlBug.setState(TrackedBug.RESOLVED_NAME);
		xmlBug.setSummary("a bug");
		xmlBug.setVotes(1);
	}

	/**
	 * Cleans up bug for next test
	 */
	@After
	public void tearDown() {
		bug1 = null;
		bug2 = null;
		confirm = null;
		vote = null;
		possession = null;
		resolved1 = null;
		resolved2 = null;
		verified = null;
		reopen = null;
		xmlBug = null;
		notes = null;
		bug5 = null;
		notes2 = null;
		notes3 = null;
		TrackedBug.setCounter(0);
	}

	/**
	 * Tests the Constructor from two Strings
	 */
	@Test
	public void testTrackedBug() {
		assertEquals(SUMMARY1, bug1.getSummary());
		assertEquals(REPORTER1, bug1.getReporter());
		assertEquals(SUMMARY2, bug2.getSummary());
		assertEquals(REPORTER2, bug2.getReporter());
		assertEquals(TrackedBug.UNCONFIRMED_NAME, bug1.getState().getStateName());
		assertEquals(TrackedBug.UNCONFIRMED_NAME, bug2.getState().getStateName());
		assertEquals(bug1.getBugId(), 0);
		assertEquals(bug2.getBugId(), 1);
	}

	/**
	 * Tests the Constructor from a Bug
	 */
	@Test
	public void testTrackedBug2() {
		bug5 = new TrackedBug(xmlBug);
		assertTrue(bug5.isConfirmed());
		assertTrue(bug5.getBugId() == 3);
		assertEquals(Command.R_FIXED, xmlBug.getResolution());
		assertTrue(xmlBug.getNoteList().getNote().size() == bug5.getNotes().size());
		for (int i = 0; i < bug5.getNotes().size(); i++) {
			assertEquals(xmlBug.getNoteList().getNote().get(i), bug5.getNotes().get(i));
		}
		assertEquals("Bob", bug5.getOwner());
		assertEquals("Joe", bug5.getReporter());
		assertEquals(xmlBug.getResolution(), bug5.getResolutionString());
		assertEquals(TrackedBug.RESOLVED_NAME, bug5.getState().getStateName());
		assertEquals("a bug", bug5.getSummary());
		assertTrue(bug5.getVotes() == 1);

	}

	/**
	 * Tests incrementing the counter method
	 */
	@Test
	public void testIncrementCounter() {
		assertEquals(bug2.getBugId(), 1);
		TrackedBug bug3 = new TrackedBug(SUMMARY1, REPORTER1);
		assertEquals(bug3.getBugId(), 2);
	}

	/**
	 * Tests the getBug method
	 */
	@Test
	public void testGetBugId() {
		assertEquals(bug1.getBugId(), 0);
		assertEquals(bug2.getBugId(), 1);
	}

	/**
	 * Tests the getState method
	 */
	@Test
	public void testGetState() {
		assertEquals(TrackedBug.UNCONFIRMED_NAME, bug1.getState().getStateName());
		assertEquals(TrackedBug.UNCONFIRMED_NAME, bug2.getState().getStateName());
	}

	/**
	 * Tests the getResolution method
	 */
	@Test
	public void testGetResolution() {
		bug1.update(confirm);
		bug1.update(possession);
		bug1.update(resolved1);
		assertEquals(Resolution.FIXED, bug1.getResolution());
		assertTrue(null == bug2.getResolution());
	}

	/**
	 * Tests the getResolutionString method
	 */
	@Test
	public void testGetResolutionString() {
		bug1.update(confirm);
		bug1.update(possession);
		bug1.update(resolved1);
		assertEquals(Command.R_FIXED, bug1.getResolutionString());
		assertTrue(null == bug2.getResolutionString());
	}

	/**
	 * Tests the getOwner method
	 */
	@Test
	public void testGetOwner() {
		assertEquals(bug1.getOwner(), null);
		bug1.update(confirm);
		bug1.update(possession);
		assertEquals(REPORTER1, bug1.getOwner());
	}

	/**
	 * Tests the getSummary method
	 */
	@Test
	public void testGetSummary() {
		assertEquals(SUMMARY1, bug1.getSummary());
		assertEquals(SUMMARY2, bug2.getSummary());
	}

	/**
	 * Tests the getReporter method
	 */
	@Test
	public void testGetReporter() {
		assertEquals(REPORTER1, bug1.getReporter());
		assertEquals(REPORTER2, bug2.getReporter());
	}

	/**
	 * Tests the getNotes method
	 */
	@Test
	public void testGetNotes() {
		assertEquals(notes2, bug1.getNotes());
		bug1.update(confirm);
		bug1.update(possession);
		assertEquals(notes3, bug1.getNotes());

	}

	/**
	 * Tests the GetNotesString method
	 */
	@Test
	public void testGetNotesString() {
		bug1.update(confirm);
		bug1.update(possession);
		assertEquals(VALID_NOTE_STRING, bug1.getNotesString());
	}

	/**
	 * Tests the getVotes method
	 */
	@Test
	public void testGetVotes() {
		assertTrue(bug1.getVotes() == 1);
		assertTrue(bug2.getVotes() == 1);
	}

	/**
	 * Tests the isConfirmed method
	 */
	@Test
	public void testIsConfirmed() {
		assertTrue(!bug1.isConfirmed());
		bug1.update(confirm);
		assertTrue(bug1.isConfirmed());
	}

	/**
	 * Tests the update method
	 */
	@Test
	public void testUpdate() {

		String u = TrackedBug.UNCONFIRMED_NAME;
		String n = TrackedBug.NEW_NAME;
		String a = TrackedBug.ASSIGNED_NAME;
		String rs = TrackedBug.RESOLVED_NAME;
		String ro = TrackedBug.REOPEN_NAME;
		String c = TrackedBug.CLOSED_NAME;

		// unconfirmed to new with 3 votes
		assertEquals(u, bug1.getState().getStateName());
		bug1.update(vote);
		assertEquals(u, bug1.getState().getStateName());
		bug1.update(vote);
		assertEquals(n, bug1.getState().getStateName());

		// new to assigned
		bug1.update(possession);
		assertEquals(a, bug1.getState().getStateName());

		// assigned to resolved
		bug1.update(resolved1);
		assertEquals(rs, bug1.getState().getStateName());

		// resolved to unconfirmed
		bug1.update(reopen);
		assertEquals(u, bug1.getState().getStateName());

		// unconfirmed to assigned with vote
		bug1.update(vote);
		assertEquals(a, bug1.getState().getStateName());

		// assigned to closed
		bug1.update(resolved2);
		assertEquals(c, bug1.getState().getStateName());

		// closed to unconfirmed
		bug1.update(reopen);
		assertEquals(u, bug1.getState().getStateName());

		// unconfirmed to assigned through confirmation
		bug1.update(confirm);
		assertEquals(a, bug1.getState().getStateName());

		// assigned -> resolved -> closed
		bug1.update(resolved1);
		bug1.update(verified);
		assertEquals(c, bug1.getState().getStateName());

		// closed to reopen
		bug1.update(reopen);
		assertEquals(ro, bug1.getState().getStateName());

		// reopen to closed
		bug1.update(resolved2);
		assertEquals(c, bug1.getState().getStateName());

		// closed -> reopen -> resolved
		bug1.update(reopen);
		bug1.update(resolved1);
		assertEquals(rs, bug1.getState().getStateName());

		// resolved to reopen
		bug1.update(reopen);
		assertEquals(ro, bug1.getState().getStateName());

		// reopen to assigned
		bug1.update(possession);
		assertEquals(a, bug1.getState().getStateName());

		// assertTrue(bug1.isConfirmed() == false);
		// assertEquals(TrackedBug.UNCONFIRMED_NAME,
		// bug1.getState().getStateName());
		// bug1.update(confirm);
		// assertTrue(bug1.isConfirmed() == true);
		// assertTrue(bug1.getOwner() == null);
		// assertEquals(TrackedBug.NEW_NAME, bug1.getState().getStateName());
		// bug1.update(possession);
		// assertEquals(REPORTER1, bug1.getOwner());
		// assertTrue(bug1.isConfirmed() == true);
		// assertEquals(TrackedBug.ASSIGNED_NAME,
		// bug1.getState().getStateName());
	}

	/**
	 * Tests the getXMLBug method
	 */
	@Test
	public void testGetXMLBug() {
		xmlBug = bug1.getXMLBug();
		assertTrue(!xmlBug.isConfirmed());
		assertTrue(xmlBug.getId() == 0);
		assertTrue(null == xmlBug.getResolution());
		assertTrue(xmlBug.getNoteList().getNote().size() == bug1.getNotes().size());
		for (int i = 0; i < bug1.getNotes().size(); i++) {
			assertEquals(bug1.getNotes().get(i), xmlBug.getNoteList().getNote().get(i));
		}
		assertTrue(null == xmlBug.getOwner());
		assertEquals(REPORTER1, xmlBug.getReporter());
		assertEquals(bug1.getResolutionString(), xmlBug.getResolution());
		assertEquals(TrackedBug.UNCONFIRMED_NAME, xmlBug.getState());
		assertEquals(SUMMARY1, xmlBug.getSummary());
		assertTrue(xmlBug.getVotes() == 1);
	}

	/**
	 * Tests the setCounter method
	 */
	@Test
	public void testSetCounter() {
		assertEquals(bug2.getBugId(), 1);
		TrackedBug.setCounter(3028);
		TrackedBug bug3 = new TrackedBug(SUMMARY1, REPORTER1);
		assertEquals(bug3.getBugId(), 3028);
	}
}
