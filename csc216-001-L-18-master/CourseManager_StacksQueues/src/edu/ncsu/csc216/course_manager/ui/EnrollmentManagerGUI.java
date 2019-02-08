package edu.ncsu.csc216.course_manager.ui;

import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EnrollmentManagerGUI extends JFrame {
	
	/** ID number used for object serialization. */
	private static final long serialVersionUID = 1L;
	/** Title for top of GUI. */
	private static final String APP_TITLE = "Enrollment Manager";
	/** Constant to identify InitPanel for CardLayout. */
	private static final String INIT_PANEL = "InitPanel";
	private JPanel panel;
	private final InitPanel initPanel = new InitPanel();
	
	/**
	 * Constructs the GUI
	 */
	public EnrollmentManagerGUI() {
		super();
		
		//Set up general GUI info
		setSize(700, 500);
		setLocation(50, 50);
		setTitle(APP_TITLE);
		
		panel = new JPanel();
		panel.add(initPanel, INIT_PANEL);
		
		Container c = getContentPane();
	
		
	}
	
	private class InitPanel extends JPanel {
		
		private JLabel studentFirst;
		private JLabel studentLast;
		private JLabel studentID;
		private JLabel course;
		
		private JTextField txtStudentFirst;
		private JTextField txtStudentLast;
		private JTextField txtStudentID;
		private JTextField txtCourse;
		
		
		
		
		
	}
	
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
