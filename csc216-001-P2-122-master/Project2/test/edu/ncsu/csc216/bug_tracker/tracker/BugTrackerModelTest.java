/**
 * 
 */
package edu.ncsu.csc216.bug_tracker.tracker;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.bug_tracker.command.Command;
import edu.ncsu.csc216.bug_tracker.command.Command.CommandValue;

/**
 * This class tests the BugTrackerModel class.
 * @author Matthew Gibson
 */
public class BugTrackerModelTest {
	private BugTrackerModel model;
	private static final String RANDOM_STRING = "random";
	private static final String RANDOM_STRING1 = "random1";
	private static final String RANDOM_STRING2 = "random2";
	private static final String RANDOM_STRING3 = "random3";
	/** Sets up variables for testing*/
	@Before
	public void setUp(){
		model = BugTrackerModel.getInstance();
		model.addBugToList(RANDOM_STRING, RANDOM_STRING1);
		model.addBugToList(RANDOM_STRING2, RANDOM_STRING3);
		model.addBugToList(RANDOM_STRING3, RANDOM_STRING2);
	}
	/** Tears down variables for next test*/
	@After
	public void tearDown(){
		model = null;
	}
	/**
	 * Tests the LoadBugsFromFile method
	 */
	@Test
	public void testLoadBugsFromFile() {
		try{
			model.loadBugsFromFile("filedoesnotexist.xml");
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(model.equals(model));
		}
	}
	/**
	 * Tests the GetBugListAsArray method
	 */
	@Test
	public void testGetBugListAsArray() {
		BugList bugs = new BugList();
		bugs.addBug(RANDOM_STRING, RANDOM_STRING1);
		bugs.addBug(RANDOM_STRING2, RANDOM_STRING3);
		bugs.addBug(RANDOM_STRING3, RANDOM_STRING2);
		Object[][] bugArray = model.getBugListAsArray();
		for(int i = 0; i < bugs.getBugs().size(); i++){
			assertEquals(bugArray[i][0], bugs.getBugs().get(i).getBugId());
			assertEquals(bugArray[i][1], bugs.getBugs().get(i).getState().getStateName());
			assertEquals(bugArray[i][2], bugs.getBugs().get(i).getSummary());
		}
		
	}
	/**
	 * Tests the GetBugListByOwnerAsArray method
	 */
	@Test
	public void testGetBugListByOwnerAsArray() {
		try {
			model.getBugListByOwnerAsArray(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(model.getBugById(0).getSummary(), RANDOM_STRING);
		}
		Command confirm = new Command(CommandValue.CONFIRM, null, null, null);
		Command owner = new Command(CommandValue.POSSESSION, "owner", null, null);
		model.executeCommand(0, confirm);
		model.executeCommand(0, owner);
		assertEquals(model.getBugListByOwnerAsArray("owner")[0][0], 0);
	}
	/**
	 * Tests the GetBugById method
	 */
	@Test
	public void testGetBugById() {
		assertEquals(model.getBugById(0).getSummary(), RANDOM_STRING);
		assertEquals(model.getBugById(1).getSummary(), RANDOM_STRING2);
	}
	/**
	 * Tests the ExecuteCommand method
	 */
	@Test
	public void testExecuteCommand() {
		Command vote = new Command(CommandValue.VOTE, null, null, null);
		model.executeCommand(0, vote);
		assertEquals(model.getBugById(0).getVotes(), 2);
	}
	/**
	 * Tests the DeleteBugById method
	 */
	@Test
	public void testDeleteBugById() {
		assertEquals(model.getBugById(0).getSummary(), RANDOM_STRING);
		assertEquals(model.getBugById(1).getSummary(), RANDOM_STRING2);
		model.deleteBugById(1);
		assertTrue(model.getBugById(1) == null);
	}
}
