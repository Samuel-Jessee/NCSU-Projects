/**
 * 
 */
package edu.ncsu.csc316.spell_checker.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.ncsu.csc316.spell_checker.list.ArrayBasedList;
import edu.ncsu.csc316.spell_checker.list.WordList;
import edu.ncsu.csc316.spell_checker.manager.SpellCheckerManager;

/**
 * The User Interface for the spell checker
 * 
 * @author Selena Turner (snturner)
 * @author Samuel Jessee (sijessee)
 */
public class SpellCheckerUI extends JFrame implements ActionListener {

	/** The dictionary file selected from the File Chooser */
	private String dictionary;
	/** The text file selected from the File Chooser */
	private String textFile;
	/** The container */
	private Container c;
	/** The spell checker manager */
	private SpellCheckerManager s;
	/** The Word List that holds the input text */
	private WordList list;
	/** The spellcheck panel */
	private JPanel spellPane;
	/** The button for spellcheck */
	private JButton spellCheck;

	/**
	 * Default serial id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates the UI
	 */
	public SpellCheckerUI() {
		dictionary = getFileName();
		textFile = getFileName();

		c = getContentPane();
		setSize(405, 50);
		setTitle("Spell Checker");
		c.setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		list = new ArrayBasedList();
		try {
			s = new SpellCheckerManager(dictionary);
			list = s.getInputText(textFile);
		} catch (IllegalArgumentException e1) {
			JOptionPane.showMessageDialog(this, e1.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} catch (NullPointerException e2) {
			JOptionPane.showMessageDialog(this, "Empty file.", "Message", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

		spellPane = new JPanel();
		spellCheck = new JButton("Check the Spelling");
		spellCheck.addActionListener(this);

		spellCheck.setMaximumSize(new Dimension(400, 30));

		spellPane.setMaximumSize(new Dimension(900, 520));
		spellPane.setBorder(BorderFactory.createLineBorder(Color.RED));
		spellPane.add(spellCheck, BorderLayout.NORTH);

		c.add(spellCheck);
		setVisible(true);
	}

	/**
	 * Starts the program by creating the GUI
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		new SpellCheckerUI();

	}

	/**
	 * Returns a file name generated
	 * 
	 * @return the file name selected
	 */
	private String getFileName() {
		// Open JFileChoose to current working directory
		JFileChooser fc = new JFileChooser("./");
		int returnVal = fc.showOpenDialog(this);
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			throw new IllegalStateException();
		}
		File gameFile = fc.getSelectedFile();
		return gameFile.getAbsolutePath();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == spellCheck) {
			try {
				System.out.println(s.spellCheck(list));
			} catch (IllegalArgumentException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
