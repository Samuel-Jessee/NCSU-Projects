package edu.ncsu.csc316.bridgemanager.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.ncsu.csc316.bridgemanager.manager.BridgeManager;

/**
 * constructs the GUI
 * 
 * @author Devin Janus (dwjanus)
 * @author Theodore Reger (tlreger)
 * @author Samuel Jessee (sijessee)
 * @param s
 *            file name for use in constructing the movie inventory
 * @throws FileNotFoundException
 *             if the file cannot be found
 */
public class BridgeManagerGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	/** Title for top of GUI. */
	private static final String APP_TITLE = "Bridge Builder";
	/** Execute button */
	private JButton btnExecute;
	/** browse button */
	private JButton btnBrowse;
	/** label for the file name */
	private JLabel lblFileName;
	/** label for the output */
	private JTextArea txtOutput;
	/** file chooser */
	private JFileChooser fileChooser;
	/** return value for fileChooser */
	int returnVal;
	/** file name: */
	private JTextField txtFileName;
	/** main window of the gui */
	private JPanel mainPanel;
	/** panel for file selector */
	private JPanel fileSelectPanel;
	/** output panel */
	private JPanel outputPanel;

	// The GUI will create the compressor manager and pass it a filepath
	private BridgeManager bridgeManager;
	private String filePath;
	private String processOutput;

	/**
	 * The GUI to be used with the file compression manager class allows user to
	 * select or input a file name. compresses/decompresses automatically
	 * 
	 * @author Devin Janus
	 * @throws FileNotFoundException
	 */
	public BridgeManagerGUI() throws FileNotFoundException {
		super();

		this.bridgeManager = new BridgeManager();
		this.processOutput = "";

		// general GUI setup stuff
		setSize(350, 600);
		setLocation(50, 50);
		setTitle(APP_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// the panel that contains the entire gui
		mainPanel = new JPanel(new BorderLayout());

		// The panel for the file chooser
		fileSelectPanel = new JPanel(new FlowLayout()); // this may change
		fileSelectPanel.setPreferredSize(new Dimension(350, 80));
		lblFileName = new JLabel("File Name: ");
		txtFileName = new JTextField(18);

		fileChooser = new JFileChooser();
		btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(this);
		btnExecute = new JButton("Find Minimum Bridges");
		btnExecute.addActionListener(this);

		fileSelectPanel.add(lblFileName);
		fileSelectPanel.add(txtFileName);
		fileSelectPanel.add(btnBrowse);
		fileSelectPanel.add(btnExecute);

		// the panel that holds the output text
		outputPanel = new JPanel(new FlowLayout());
		txtOutput = new JTextArea(processOutput);
		txtOutput.setPreferredSize(new Dimension(315, 470));
		txtOutput.setLineWrap(true);
		outputPanel.add(txtOutput);
		outputPanel.setBorder(new EtchedBorder());

		mainPanel.add(outputPanel, BorderLayout.CENTER);
		mainPanel.add(fileSelectPanel, BorderLayout.NORTH);

		// add panel to the container
		Container c = getContentPane();
		c.add(mainPanel);

		// Now you can see it!
		setVisible(true);
	}

	/**
	 * if the browse button is selected... pulls up file chooser
	 * 
	 * if the execute button is selected... compresses/decompresses chosen file
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnBrowse) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
			fileChooser.setFileFilter(filter);
			returnVal = fileChooser.showOpenDialog(null); // makes fileChooser
															// visible
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				try {
					filePath = fileChooser.getSelectedFile().getAbsolutePath();
					// display file name in text field
					txtFileName.setText(filePath);
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		}

		if (e.getSource() == btnExecute) {
			try {
				// compress file and set text output area to say the status of
				// the compression
				filePath = txtFileName.getText();
				bridgeManager = new BridgeManager(filePath);
				// maybe find a nice way to output this in a window?
				String s1 = bridgeManager.getHeap(bridgeManager.getBridges());
				String s2 = bridgeManager.getAdjacencyList(bridgeManager.getBridges());
				String s3 = bridgeManager.getMinimumSpanningBridges(bridgeManager.getBridges());
				this.processOutput = s1 + "\n\n" + s2 + "\n\n" + s3 + "\n";
				txtOutput.setText(processOutput);

				txtOutput.revalidate();
				txtOutput.repaint();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Starts the GUI for the FileCompressor application.
	 * 
	 * @param args
	 *            command line arguments (none passed)
	 * @throws FileNotFoundException
	 *             if file cannot be found
	 */
	public static void main(String[] args) throws FileNotFoundException {
		new BridgeManagerGUI();
	}

}