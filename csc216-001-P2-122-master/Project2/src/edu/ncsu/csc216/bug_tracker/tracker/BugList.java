/**
 * 
 */
package edu.ncsu.csc216.bug_tracker.tracker;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.bug_tracker.bug.TrackedBug;
import edu.ncsu.csc216.bug_tracker.command.Command;
import edu.ncsu.csc216.bug_tracker.xml.Bug;

/**
 * This Class holds the List of all bugs in the BugTracker Program.
 * @author Matthew Gibson
 * @author Samuel Jessee
 */
public class BugList {
	/** The ArrayList of Bugs to be handled */
	private ArrayList<TrackedBug> list;
	/**
	 * Constructor for a BugList
	 */
	public BugList() {
		TrackedBug.setCounter(0);
		list = new ArrayList<TrackedBug>();
	}
	/**
	 * Adds a bug to the list and returns its ID
	 * @param summary summary of the bug
	 * @param userId ID of the reporter of the bug
	 * @return int the id of the bug to add to the list
	 */
	public int addBug(String summary, String userId) {
		if(summary == null || userId == null) {
			throw new IllegalArgumentException();
		}
		TrackedBug bug = new TrackedBug(summary, userId);
		this.list.add(bug);
		return bug.getBugId();
	}
	/**
	 * Adds a list of bugs in XML format to the BugList 
	 * @param xMLList Bugs in XML format
	 */
	public void addXMLBugs(List<Bug> xMLList){
		TrackedBug bug;
		for(int i = 0; i < xMLList.size(); i++){
			bug = new TrackedBug(xMLList.get(i));
			this.list.add(bug);
		}
	}
	/**
	 * Returns the list of TrackedBugs
	 * @return the list of TrackedBugs
	 */
	public ArrayList<TrackedBug> getBugs(){
		return list;
	}
	/**
	 * Returns a List of Bugs only by a specific owner
	 * @param owner the owner of the bugs to be listed
	 * @return the list of Bugs by one owner
	 */
	public ArrayList<TrackedBug> getBugsByOwner(String owner){
		if(owner == null) {
			throw new IllegalArgumentException();
		}
		ArrayList<TrackedBug> ownerArray = new ArrayList<TrackedBug>();
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getOwner() != null && list.get(i).getOwner().equals(owner)) {
				ownerArray.add(list.get(i));
			}
		}
		return ownerArray;
	}
	/**
	 * Returns a specific bug from its ID
	 * @param id The ID of the bug to be returned
	 * @return TrackedBug A Bug
	 */
	public TrackedBug getBugById(int id){
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getBugId() == id){
				return list.get(i);
			}
		}
		return null;
	}
	/**
	 * Executes a command on a TrackedBug
	 * @param id ID of the bug
	 * @param command The specific command to be done
	 */
	public void executeCommand(int id, Command command) {
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getBugId() == id){
				list.get(i).update(command);
			}
		}
	}
	/**
	 * Deletes a specific bug by its ID
	 * @param id The ID of the bug to be deleted
	 */
	public void deleteBugById(int id){
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getBugId() == id) {
				list.remove(list.get(i));
			}
		}
	}
}
