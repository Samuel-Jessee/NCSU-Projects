/**
 * 
 */
package edu.ncsu.csc216.bug_tracker.tracker;

import edu.ncsu.csc216.bug_tracker.bug.TrackedBug;
import edu.ncsu.csc216.bug_tracker.command.Command;
import edu.ncsu.csc216.bug_tracker.xml.BugIOException;
import edu.ncsu.csc216.bug_tracker.xml.BugReader;
import edu.ncsu.csc216.bug_tracker.xml.BugWriter;

/** 
 * This class pertains to the Singleton instance of the BugTrackerModel and
 * different methods on interacting with it.
 * @author SamuelJessee
 */
public class BugTrackerModel {
	/** Number of columns when creating bug arrays */
	private static final int NUMBER_OF_COLUMNS_IN_ARRAY = 3;
	/** Singleton Instance of this class*/
	private static BugTrackerModel model;	
	/**
	 * The Buglist containing bugs of a specific program
	 */
	private BugList list;
	/**
	 * The Constructor for the BugTrackerModel
	 */
	private BugTrackerModel() {
		createNewBugList();
	}
	/**
	 * Returns the singleton instance of the BugTrackerModel
	 * @return the singleton BugTrackerModel
	 */
	public static BugTrackerModel getInstance() {
		if(model == null) {
			model = new BugTrackerModel();
		}
		return model;
	}
	/**
	 * Saves a BugList to a XML format file
	 * @param file the file in which the BugList should be saved
	 */
	public void saveBugsToFile(String file){
		BugWriter bugs = new BugWriter(file);
		for(int i = 0; i < list.getBugs().size() - 1; i++) {
			bugs.addItem(list.getBugs().get(i).getXMLBug());
		}
	}
	/**
	 * Loads a list of bugs from a XML format file
	 * @param file Name of the XML formal file which contains BugList information
	 * @throws BugIOException 
	 */
	public void loadBugsFromFile(String file) {
		try{
			BugReader bugs = new BugReader(file);
			list.addXMLBugs(bugs.getBugs());
		} catch (BugIOException e) {
			throw new IllegalArgumentException();
		}
	}
	/**
	 * Creates the BugList to be used in this singleton instance
	 */
	public void createNewBugList() {
		list = new BugList();
	}
	/**
	 * Returns the Buglist as an array including its id, summary, and owner
	 * @return Object[][] The array of the Buglist including certain fields
	 */
	public Object[][] getBugListAsArray(){
		Object[][] bugArray = new Object[list.getBugs().size()][NUMBER_OF_COLUMNS_IN_ARRAY];
		for(int i = 0; i < list.getBugs().size(); i++){
			bugArray[i][0] = list.getBugs().get(i).getBugId();
			bugArray[i][1] = list.getBugs().get(i).getState().getStateName();
			bugArray[i][2] = list.getBugs().get(i).getSummary();
		}
		return bugArray;
	}
	/**
	 * Returns the BugList of bugs from a specific owner as an array 
	 * @param owner owner of the bugs
	 * @return Object[][] Array of the Bugs
	 */
	public Object[][] getBugListByOwnerAsArray(String owner){
		if(owner == null){
			throw new IllegalArgumentException();
		}
		Object[][] bugArray = new Object[list.getBugsByOwner(owner).size()][NUMBER_OF_COLUMNS_IN_ARRAY];
		for(int i = 0; i < list.getBugsByOwner(owner).size(); i++){
			bugArray[i][0] = list.getBugsByOwner(owner).get(i).getBugId();
			bugArray[i][1] = list.getBugsByOwner(owner).get(i).getState().getStateName();
			bugArray[i][2] = list.getBugsByOwner(owner).get(i).getSummary();
		}
		return bugArray;
	}
	/**
	 * Returns a bug by its ID
	 * @param id ID of the bug to be returned
	 * @return TrackedBug The Bug in the BugList
	 */
	public TrackedBug getBugById(int id){
		return list.getBugById(id);
	}
	/**
	 * Executes a given command on a bug by its ID
	 * @param id ID of the bug to be commanded
	 * @param command Command to be used on the bug
	 */
	public void executeCommand(int id, Command command){
		list.executeCommand(id, command);
	}
	/**
	 * Deletes a bug from the BugList
	 * @param id ID of the bug to be deleted
	 */
	public void deleteBugById(int id){
		list.deleteBugById(id);
	}
	/**
	 * Adds a bug to the BugList
	 * @param summary Summary of the bug
	 * @param userId Reporter of the bug
	 */
	public void addBugToList(String summary, String userId){
		list.addBug(summary, userId);
	}
}
