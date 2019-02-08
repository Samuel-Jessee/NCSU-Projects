/**
 * 
 */
package edu.ncsu.csc216.bug_tracker.bug;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.bug_tracker.command.Command;
import edu.ncsu.csc216.bug_tracker.command.Command.CommandValue;
import edu.ncsu.csc216.bug_tracker.command.Command.Resolution;
import edu.ncsu.csc216.bug_tracker.xml.Bug;
import edu.ncsu.csc216.bug_tracker.xml.NoteList;

/**
 * Creates TrackedBug objects, either from scratch, or from a Bug object, and
 * stores their fields and states. Each state determines which methods are
 * available currently.
 * 
 * @author SamuelJessee
 * @author MatthewGibson
 *
 */
public class TrackedBug {

	/** Int value that represents a specific bug */
	private int bugId;

	/** State which a certain bug is in */
	private BugState state;

	/** String that contains details on the bug */
	private String summary;

	/** The id of the person who reported the bug */
	private String reporter;

	/** The owner of the bug */
	private String owner;

	/** This is the number of votes this bugs has for it to be fixed */
	private int votes;

	/** Whether the bug is confirmed or not */
	private boolean confirmed;

	/** An array of string that contains notes on the bug */
	private ArrayList<String> notes;

	/**
	 * A state which means the bug has not been replicated by a system developer
	 * or tester.
	 */
	private final BugState unconfirmedState;

	/**
	 * Bugs in this state have no owners and have either received enough votes
	 * showing interest from other developers or have been replicated and
	 * confirmed.
	 */
	private final BugState newState;

	/**
	 * In this state, the bug has an owner that works on the bug and applies a
	 * resolution to it.
	 */
	private final BugState assignedState;

	/** This state is the place where it tests the fixes to the bugs. */
	private final BugState resolvedState;

	/** In this state, bugs are confirmed, but not yet fixed. */
	private final BugState reopenState;

	/**
	 * The final state. In this state, a bug can be reopened if the fix didn't
	 * work
	 */
	private final BugState closedState;

	/** The name for the Unconfirmed state */
	public static final String UNCONFIRMED_NAME = "Unconfirmed";

	/** The name for the New state */
	public static final String NEW_NAME = "New";

	/** The name for the Assigned state */
	public static final String ASSIGNED_NAME = "Assigned";

	/** The name for the Resolved state */
	public static final String RESOLVED_NAME = "Resolved";

	/** The name for the Reopen state */
	public static final String REOPEN_NAME = "Reopen";

	/** The name for the state */
	public static final String CLOSED_NAME = "Closed";

	/** The number of votes needed to change a bug from unconfirmed to new */
	public static final int VOTE_THRESHOLD = 3;

	/** The counter holding the next bugId that can be used */
	private static int counter = 0;

	/** Resolution Enumerator */
	private Resolution resolution;

	/**
	 * Constructor for a bug from two strings
	 * 
	 * @param summary
	 *            summary of the bug
	 * @param userId
	 *            Id of the reporter
	 */
	public TrackedBug(String summary, String userId) {
		if (summary == null || userId == null || userId.equals("") || summary.equals("")) {
			throw new IllegalArgumentException();
		}
		this.reporter = userId;
		this.summary = summary;
		this.bugId = counter;
		incrementCounter();
		this.votes = 1;
		this.notes = new ArrayList<String>();
		this.unconfirmedState = new UnconfirmedState();
		this.newState = new NewState();
		this.assignedState = new AssignedState();
		this.resolvedState = new ResolvedState();
		this.reopenState = new ReopenState();
		this.closedState = new ClosedState();
		setState(UNCONFIRMED_NAME);
	}

	/**
	 * Constructor for a bug from a Bug
	 * 
	 * @param bug
	 *            the bug to be added to the list
	 */
	public TrackedBug(Bug bug) {
		if (bug.getReporter() == null) {
			throw new IllegalArgumentException();
		} else if (bug.getReporter().equals("")) {
			throw new IllegalArgumentException();
		} else {
			this.reporter = bug.getReporter();
		}
		if (bug.getSummary() == null) {
			throw new IllegalArgumentException();
		} else if (bug.getSummary().equals("")) {
			throw new IllegalArgumentException();
		} else {
			this.summary = bug.getSummary();
		}
		if (bug.getId() >= 0) {
			this.bugId = bug.getId();
		} else {
			throw new IllegalArgumentException();
		}
		if (bug.getOwner() != null) {
			this.owner = bug.getOwner();
		}
		if (bug.getVotes() > 0) {
			this.votes = bug.getVotes();
		} else {
			throw new IllegalArgumentException();
		}
		this.confirmed = bug.isConfirmed();
		this.notes = new ArrayList<String>();
		if (bug.getNoteList() != null) {
			for (int i = 0; i < bug.getNoteList().getNote().size(); i++) {
				this.notes.add(bug.getNoteList().getNote().get(i));
			}
		}
		if (bug.getResolution() != null) {
			this.setResolution(bug.getResolution());
		}
		this.unconfirmedState = new UnconfirmedState();
		this.newState = new NewState();
		this.assignedState = new AssignedState();
		this.resolvedState = new ResolvedState();
		this.reopenState = new ReopenState();
		this.closedState = new ClosedState();

		if (bug.getState() == null) {
			throw new IllegalArgumentException();
		} else if (bug.getState().equals(UNCONFIRMED_NAME) || bug.getState().equals(NEW_NAME)
				|| bug.getState().equals(ASSIGNED_NAME) || bug.getState().equals(RESOLVED_NAME)
				|| bug.getState().equals(REOPEN_NAME) || bug.getState().equals(CLOSED_NAME)) {
			setState(bug.getState());
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Increments counter by 1.
	 */
	public static void incrementCounter() {
		counter++;
	}

	/**
	 * Returns the Bug's Id.
	 * 
	 * @return bugId
	 */
	public int getBugId() {
		return bugId;
	}

	/**
	 * Returns the Bug's state.
	 * 
	 * @return Bug state
	 */
	public BugState getState() {
		return state;
	}

	/**
	 * Sets the bug's state
	 * 
	 * @param stateName
	 */
	private void setState(String stateName) {
		switch (stateName) {
		case UNCONFIRMED_NAME:
			this.state = unconfirmedState;
			break;
		case NEW_NAME:
			this.state = newState;
			break;
		case ASSIGNED_NAME:
			this.state = assignedState;
			break;
		case RESOLVED_NAME:
			this.state = resolvedState;
			break;
		case REOPEN_NAME:
			this.state = reopenState;
			break;
		case CLOSED_NAME:
			this.state = closedState;
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Returns the Bug's resolution.
	 * 
	 * @return resolution
	 */
	public Resolution getResolution() {
		return resolution;
	}

	/**
	 * Returns the resolution as a String.
	 * 
	 * @return resolution as a String
	 */
	public String getResolutionString() {

		if (resolution == null) {
			return null;
		}
		if (resolution.equals(Resolution.FIXED)) {
			return Command.R_FIXED;
		} else if (resolution.equals(Resolution.DUPLICATE)) {
			return Command.R_DUPLICATE;
		} else if (resolution.equals(Resolution.WONTFIX)) {
			return Command.R_WONTFIX;
		} else if (resolution.equals(Resolution.WORKSFORME)) {
			return Command.R_WORKSFORME;
		} else {
			return null;
		}
	}

	/**
	 * Sets the bug's resolution
	 * 
	 * @param resolutionName
	 */
	private void setResolution(String resolutionName) {
		switch (resolutionName) {
		case Command.R_FIXED:
			this.resolution = Resolution.FIXED;
			break;
		case Command.R_DUPLICATE:
			this.resolution = Resolution.DUPLICATE;
			break;
		case Command.R_WONTFIX:
			this.resolution = Resolution.WONTFIX;
			break;
		case Command.R_WORKSFORME:
			this.resolution = Resolution.WORKSFORME;
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Returns the bug's assigned programmer
	 * 
	 * @return bug owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Returns the summary of the bug
	 * 
	 * @return bug summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Returns the reporter of the bug
	 * 
	 * @return bug reporter
	 */
	public String getReporter() {
		return reporter;
	}

	/**
	 * Returns the ArrayList of notes
	 * 
	 * @return notes
	 */
	public ArrayList<String> getNotes() {
		return notes;
	}

	/**
	 * Returns notes as a String
	 * 
	 * @return notes as String
	 */
	public String getNotesString() {
		String s = "";
		for (int i = 0; i < this.notes.size(); i++) {
			s += this.notes.get(i) + "\n" + "------" + "\n";
		}
		return s;
	}

	/**
	 * Returns the current number of votes
	 * 
	 * @return number of votes
	 */
	public int getVotes() {
		return votes;
	}

	/**
	 * Returns true if the bug has been confirmed
	 * 
	 * @return confirmation status
	 */
	public boolean isConfirmed() {
		return confirmed;
	}

	/**
	 * Receives a Command and updates bug information and state according to the
	 * Command given. Not every Command is applicable in all states
	 * 
	 * @param command
	 *            Command given
	 */
	public void update(Command command) {
		this.state.updateState(command);
	}

	/**
	 * Returns the TrackedBug as an XML Bug object
	 * 
	 * @return XML Bug
	 */
	public Bug getXMLBug() {
		Bug bug = new Bug();
		NoteList xmlNotes = new NoteList();
		bug.setConfirmed(this.confirmed);
		bug.setId(this.bugId);
		List<String> n;
		if (this.notes != null) {
			n = this.getNotes();
			xmlNotes.note = n;
			bug.setNoteList(xmlNotes);
		}
		if (owner != null) {
			bug.setOwner(this.owner);
		}
		bug.setReporter(this.reporter);
		if (resolution != null) {
			switch (resolution) {
			case FIXED:
				bug.setResolution(Command.R_FIXED);
				break;
			case DUPLICATE:
				bug.setResolution(Command.R_DUPLICATE);
				break;
			case WONTFIX:
				bug.setResolution(Command.R_WONTFIX);
				break;
			case WORKSFORME:
				bug.setResolution(Command.R_WORKSFORME);
				break;
			default:
				throw new IllegalArgumentException();
			}
		}
		bug.setState(this.state.getStateName());
		bug.setSummary(this.summary);
		bug.setVotes(this.votes);
		return bug;
	}

	/**
	 * Sets counter to an int value
	 * 
	 * @param a
	 *            int to set
	 */
	public static void setCounter(int a) {
		counter = a;
	}

	/**
	 * State in which a bug has not been confirmed and has not received enough
	 * votes to transition.
	 * 
	 * @author SamuelJessee
	 *
	 */
	private class UnconfirmedState implements BugState {

		/**
		 * Returns the current state as a String
		 * 
		 * @return state name
		 */
		@Override
		public String getStateName() {
			return UNCONFIRMED_NAME;
		}

		/**
		 * Updates the current state with a given command.
		 * 
		 * @param command
		 *            command given
		 */
		@Override
		public void updateState(Command command) {
			CommandValue c = command.getCommand();
			switch (c) {
			case VOTE:
				if (votes >= VOTE_THRESHOLD) {
					votes++;
					if (owner == null) {
						setState(NEW_NAME);
					} else if (owner != null) {
						setState(ASSIGNED_NAME);
					}
				}
				if (votes < VOTE_THRESHOLD) {
					votes++;
					if (votes == VOTE_THRESHOLD) {
						if (owner == null) {
							setState(NEW_NAME);
						} else if (owner != null) {
							setState(ASSIGNED_NAME);
						}
					}
				}
				if (command.getNote() != null && !command.getNote().equals("")) {
					notes.add(command.getNote());
				}
				break;
			case CONFIRM:
				confirmed = true;
				if (owner == null) {
					setState(NEW_NAME);
				} else if (owner != null) {
					setState(ASSIGNED_NAME);
				}
				if (command.getNote() != null && !command.getNote().equals("")) {
					notes.add(command.getNote());
				}
				break;
			default:
				throw new UnsupportedOperationException();
			}
		}
	}

	/**
	 * State in which bug has either been confirmed or has received enough
	 * votes, but does not has an assigned owner.
	 * 
	 * @author SamuelJessee
	 *
	 */
	private class NewState implements BugState {

		/**
		 * Returns the current state as a String.
		 * 
		 * @return state name
		 */
		@Override
		public String getStateName() {
			return NEW_NAME;
		}

		/**
		 * Updates the current state with the given command
		 * 
		 * @param command
		 *            command given
		 */
		@Override
		public void updateState(Command command) {
			CommandValue c = command.getCommand();
			switch (c) {
			case POSSESSION:
				owner = command.getDeveloperId();
				setState(ASSIGNED_NAME);
				if (command.getNote() != null && !command.getNote().equals("")) {
					notes.add(command.getNote());
				}
				break;
			default:
				throw new UnsupportedOperationException();
			}
		}
	}

	/**
	 * State in which a bug has been assigned an owner
	 * 
	 * @author SamuelJessee
	 *
	 */
	private class AssignedState implements BugState {

		/**
		 * Returns the current state name
		 * 
		 * @return state name
		 */
		@Override
		public String getStateName() {
			return ASSIGNED_NAME;
		}

		/**
		 * Updates the current state with the given command
		 * 
		 * @param command
		 *            command given
		 */
		@Override
		public void updateState(Command command) {
			CommandValue c = command.getCommand();
			switch (c) {
			case RESOLVED:
				if (command.getResolution().equals(Resolution.FIXED)) {
					setResolution(Command.R_FIXED);
					setState(RESOLVED_NAME);
				} else if (command.getResolution().equals(Resolution.WONTFIX)) {
					setResolution(Command.R_WONTFIX);
					setState(CLOSED_NAME);
				} else if (command.getResolution().equals(Resolution.WORKSFORME)) {
					setResolution(Command.R_WORKSFORME);
					setState(CLOSED_NAME);
				} else if (command.getResolution().equals(Resolution.DUPLICATE)) {
					setResolution(Command.R_DUPLICATE);
					setState(CLOSED_NAME);
				}
				if (command.getNote() != null && !command.getNote().equals("")) {
					notes.add(command.getNote());
				}
				break;
			default:
				throw new UnsupportedOperationException();
			}
		}
	}

	/**
	 * State in which a bug has been resolved
	 * 
	 * @author SamuelJessee
	 *
	 */
	private class ResolvedState implements BugState {

		/**
		 * Returns the current state name
		 * 
		 * @return state name
		 */
		@Override
		public String getStateName() {
			return RESOLVED_NAME;
		}

		/**
		 * Updates the current state with the given command
		 * 
		 * @param command
		 *            given command
		 */
		@Override
		public void updateState(Command command) {
			CommandValue c = command.getCommand();
			switch (c) {
			case VERIFIED:
				setState(CLOSED_NAME);
				if (command.getNote() != null && !command.getNote().equals("")) {
					notes.add(command.getNote());
				}
				break;
			case REOPEN:
				resolution = null;
				if (confirmed) {
					setState(REOPEN_NAME);
				} else if (!confirmed) {
					setState(UNCONFIRMED_NAME);
				}
				if (command.getNote() != null && !command.getNote().equals("")) {
					notes.add(command.getNote());
				}
				break;
			default:
				throw new UnsupportedOperationException();
			}
		}
	}

	/**
	 * State in which a bug has been closed
	 * 
	 * @author SamuelJessee
	 *
	 */
	private class ClosedState implements BugState {

		/**
		 * Returns the current state name
		 * 
		 * @return state name
		 */
		@Override
		public String getStateName() {
			return CLOSED_NAME;
		}

		/**
		 * Updates the current state with the given command
		 * 
		 * @param command
		 *            given command
		 */
		@Override
		public void updateState(Command command) {
			CommandValue c = command.getCommand();
			switch (c) {
			case REOPEN:
				resolution = null;
				if (confirmed) {
					setState(REOPEN_NAME);
				} else if (!confirmed) {
					setState(UNCONFIRMED_NAME);
				}
				if (command.getNote() != null && !command.getNote().equals("")) {
					notes.add(command.getNote());
				}
				break;
			default:
				throw new UnsupportedOperationException();
			}
		}
	}

	/**
	 * State in which bug has been reopened
	 * 
	 * @author SamuelJessee
	 *
	 */
	private class ReopenState implements BugState {

		/**
		 * Returns the current state name
		 * 
		 * @return state name
		 */
		@Override
		public String getStateName() {
			return REOPEN_NAME;
		}

		/**
		 * Updates the current state with the given command
		 * 
		 * @param command
		 *            the given command
		 */
		@Override
		public void updateState(Command command) {
			CommandValue c = command.getCommand();
			switch (c) {
			case POSSESSION:
				owner = command.getDeveloperId();
				setState(ASSIGNED_NAME);
				if (command.getNote() != null && !command.getNote().equals("")) {
					notes.add(command.getNote());
				}
				break;
			case RESOLVED:
				if (command.getResolution().equals(Resolution.FIXED)) {
					setResolution(Command.R_FIXED);
					setState(RESOLVED_NAME);
				} else if (command.getResolution().equals(Resolution.WONTFIX)) {
					setResolution(Command.R_WONTFIX);
					setState(CLOSED_NAME);
				} else if (command.getResolution().equals(Resolution.WORKSFORME)) {
					setResolution(Command.R_WORKSFORME);
					setState(CLOSED_NAME);
				} else if (command.getResolution().equals(Resolution.DUPLICATE)) {
					setResolution(Command.R_DUPLICATE);
					setState(CLOSED_NAME);
				}
				if (command.getNote() != null && !command.getNote().equals("")) {
					notes.add(command.getNote());
				}
				break;
			default:
				throw new UnsupportedOperationException();
			}
		}
	}

}