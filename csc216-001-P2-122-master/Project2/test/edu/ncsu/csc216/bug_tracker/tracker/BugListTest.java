/**
 * 
 */
package edu.ncsu.csc216.bug_tracker.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.bug_tracker.command.Command;
import edu.ncsu.csc216.bug_tracker.command.Command.CommandValue;
import static org.junit.Assert.*;

/**
 * Tests the BugList class.
 * @author Matthew Gibson
 */
public class BugListTest {
	/** Lists used for testing */
	private BugList list;
	private static final String RANDOM_STRING = "random";
	private static final String RANDOM_STRING1 = "random1";
	private static final String RANDOM_STRING2 = "random2";
	private static final String RANDOM_STRING3 = "random3";
	/**
	 * Sets up variables for testing the BugList methods
	 */
	@Before
	public void setUp() {
		list = new BugList();
		list.addBug(RANDOM_STRING, RANDOM_STRING1);
		list.addBug(RANDOM_STRING2, RANDOM_STRING3);
		
	}
	/**
	 * Resets variables to null for next test method
	 */
	@After
	public void tearDown() {
		list = null;
	}
	/**
	 * Tests the addBug method
	 */
	@Test
	public void testAddBug(){
		try{
			list.getBugs().get(2);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(list.getBugs().get(0).getSummary(), RANDOM_STRING );
		}
		assertEquals(list.addBug(RANDOM_STRING, RANDOM_STRING1), 2);
		assertEquals(list.getBugs().get(0).getSummary(), RANDOM_STRING );
		try{
			list.addBug(null , RANDOM_STRING1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(list.getBugs().size(), 3);
		}
		try{
			list.addBug( RANDOM_STRING , null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(list.getBugs().size(), 3);
		}
	}
	/**
	 * Tests the DeleteBugById method
	 */
	@Test
	public void testDeleteBugById(){
		assertEquals(list.addBug(RANDOM_STRING, RANDOM_STRING1), 2);
		assertEquals(list.getBugs().size(), 3);
		list.deleteBugById(2);
		assertEquals(list.getBugs().size(), 2);
		list.deleteBugById(43);
		assertEquals(list.getBugs().size(), 2);
	}
	/**
	 * Tests the ExecuteCommand method
	 */
	@Test
	public void testExecuteCommand(){
		Command vote = new Command(CommandValue.VOTE, null, null, null);
		list.executeCommand(0, vote);
		assertEquals(list.getBugById(0).getVotes(), 2);
	}
	/**
	 * Tests the GetBugById method
	 */
	@Test
	public void testGetBugById(){
		assertEquals(list.getBugById(0).getSummary(), RANDOM_STRING);
		assertEquals(list.getBugById(1).getSummary(), RANDOM_STRING2);
		list.deleteBugById(1);
		assertEquals(list.getBugById(1), null);
	}
	/**
	 * Tests the GetBugsByOwner method
	 */
	@Test
	public void testGetBugsByOwner(){
		try{
			list.getBugsByOwner(null);
		} catch (IllegalArgumentException e){
			assertEquals(list.getBugs().size(), 2);
		}
		Command confirm = new Command(CommandValue.CONFIRM, null, null, null);
		Command owner = new Command(CommandValue.POSSESSION, "owner", null, null);
		list.executeCommand(0, confirm);
		list.executeCommand(0, owner);
		assertEquals(list.getBugsByOwner("owner").get(0).getBugId(), 0);
	}
	
}
