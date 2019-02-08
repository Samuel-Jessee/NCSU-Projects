/**
 * 
 */
package edu.ncsu.csc216.bug_tracker.command;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.bug_tracker.command.Command.CommandValue;
import edu.ncsu.csc216.bug_tracker.command.Command.Resolution;

/**
 * This class tests the Command class.
 * @author Matthew Gibson
 */
public class CommandTest {
	/**
	 * Command variables used in the tests
	 */
	private Command command;
	private Command command1;
	private Command command2;
	private Command command3;
	/**
	 * Sets up the Command variable for testing
	 */
	@Before
	public void setUp(){
		command = new Command(CommandValue.VOTE, "Owner", Resolution.FIXED, "note");
	}
	/**
	 * Cleans up the Command variables after testing
	 */
	@After
	public void tearDown(){
		command = null;
		command1 = null;
		command2 = null;
		command3 = null;
	}
	/**
	 * Tests the Command constructor
	 */
	@Test
	public void testCommand(){
		
		try{
			command1 = new Command(null, "Owner", Resolution.FIXED, "note");
			fail();
		} catch(IllegalArgumentException e) {
			assertTrue(command1 == null);
		}
		try{
			command2 = new Command(CommandValue.POSSESSION, "Owner", null, "");
			fail();
		} catch(IllegalArgumentException e){
			assertTrue(command2 == null);
		}
		try{
			command2 = new Command(CommandValue.POSSESSION, "Owner", null, "note");
		} catch(IllegalArgumentException e){
			fail();
		}
		try{
			command2 = new Command(CommandValue.RESOLVED, "Owner", Resolution.FIXED, "note");
		} catch(IllegalArgumentException e){
			fail();
		}
		try{
			command3 = new Command(CommandValue.RESOLVED, null, null, "");
		} catch(IllegalArgumentException e) {
			assertTrue(command3 == null);
		}
		try{
			command3 = new Command(CommandValue.POSSESSION, null, Resolution.FIXED, "");
			fail();
		} catch(IllegalArgumentException e) {
			assertTrue(command3 == null);
		}
	}
	/**
	 * Tests the getCommand method
	 */
	@Test
	public void testGetCommand(){
		assertTrue(command.getCommand() == CommandValue.VOTE);
		command1 = new Command(CommandValue.CONFIRM, "Owner", Resolution.FIXED, "note");
		assertTrue(command1.getCommand() == CommandValue.CONFIRM);
	}
	/**
	 * Tests the getDeveloperId method
	 */
	@Test
	public void testGetDeveloperId(){
		assertTrue(command.getDeveloperId().equals("Owner"));
		command1 = new Command(CommandValue.CONFIRM, "Owner234", Resolution.FIXED, "note");
		assertTrue(command1.getDeveloperId().equals("Owner234"));
	}
	/**
	 * Tests the getResolution method
	 */
	@Test
	public void testGetResolution(){
		assertTrue(command.getResolution() == Resolution.FIXED);
		command1 = new Command(CommandValue.CONFIRM, "Owner", Resolution.WONTFIX, "note");
		assertTrue(command1.getResolution() == Resolution.WONTFIX);
	}
	/**
	 * Tests the getNote method
	 */
	@Test
	public void testGetNote(){
		assertTrue(command.getNote().equals("note"));
		command1 = new Command(CommandValue.CONFIRM, "Owner", Resolution.FIXED, "letter");
		assertTrue(command1.getNote().equals("letter"));
	}
	
}
