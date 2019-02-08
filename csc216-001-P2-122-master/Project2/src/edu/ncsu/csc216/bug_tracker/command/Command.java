/**
 *
 */
package edu.ncsu.csc216.bug_tracker.command;

/** 
 * The Command class creates objects that encapsulate 
 * user actions (or transitions) that cause the state 
 * of a TrackedBug to update.
 * @author Matthew Gibson 
 */
public class Command {
	/** String for the resolution FIXED */
	public static final String R_FIXED = "Fixed";
	/** String for the resolution DUPLICATE */
	public static final String R_DUPLICATE = "Duplicate";
	/** String for the resolution WONTFIX */
	public static final String R_WONTFIX = "WontFix";
	/** String for the resolution WORKSFORME */
	public static final String R_WORKSFORME = "WorksForMe";
	private CommandValue command;
	private String developerId;
	private Resolution resolution;
	private String note;
	/**
	 * Constructor for the Command object
	 * @param value The Command value from the enum
	 * @param developerId The specific id for a developer using the command class
	 * @param resolution The Resolution value from the enum
	 * @param note A note from the developer
	 */
	public Command(CommandValue value, String developerId, Resolution resolution, String note) {
		if(value == null) {
			throw new IllegalArgumentException();
		}
		if(value == CommandValue.RESOLVED && resolution == null ) {
			throw new IllegalArgumentException();
		}
		if(value == CommandValue.POSSESSION && developerId == null) {
			throw new IllegalArgumentException();
		}
		if(value == CommandValue.POSSESSION && developerId.equals("")) {
			throw new IllegalArgumentException();
		}
		if(note != null && value == CommandValue.POSSESSION && note.equals("")) {
			throw new IllegalArgumentException();
		}
		this.command = value;
		this.developerId = developerId;
		this.resolution = resolution;
		this.note = note;
	}
	/**
	 * Returns a Command objects CommandValue
	 * @return CommandValue a Command objects CommandValue
	 */
	public CommandValue getCommand(){
		return command;
	}
	/**
	 * Returns the Developers ID
	 * @return the developerId
	 */
	public String getDeveloperId() {
		return developerId;
	}
	/**
	 * Returns a Command objects Resolution
	 * @return resolution the Command objects Resolution
	 */
	public Resolution getResolution(){
		return resolution;
	}
	/**
	 * Returns the note on the Command
	 * @return the note 
	 */
	public String getNote() {
		return note;
	}

	/**
	 * The acceptable values for a Command
	 * @author Matthew Gibson
	 */
    public static enum CommandValue { VOTE, POSSESSION, RESOLVED, VERIFIED, REOPEN, CONFIRM }
    /**
     * The acceptable values for a Resolution
     * @author Matthew Gibson
     */
    public static enum Resolution { FIXED, DUPLICATE, WONTFIX, WORKSFORME }


}
