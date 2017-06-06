package photo_renamer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import java.awt.*;
import java.util.List;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

import java.io.IOException;

import photo_renamer.FileType;

/**
 * The listener for the button to choose a directory. This is where most of the
 * work is done.
 */
public class User extends PhotoRenamer implements ActionListener {

	/** The window the button is in. */
	private JFrame directoryFrame;
	/** The label for the full path to the chosen directory. */
	private JLabel directoryLabel;
	/** The file chooser to use when the user clicks. */
	private JFileChooser fileChooser;
	/** The area to use to display the nested directory contents. */
	private JTextArea textArea;

	/**
	 * An action listener for window dirFrame, displaying a file path on
	 * dirLabel, using fileChooser to choose a file.
	 *
	 * @param dirFrame
	 *            the main window
	 * @param dirLabel
	 *            the label for the directory path
	 * @param fileChooser
	 *            the file chooser to use
	 */
	public User(JFrame dirFrame, JLabel dirLabel, JTextArea textArea, JFileChooser fileChooser) {
		this.directoryFrame = dirFrame;
		this.directoryLabel = dirLabel;
		this.textArea = textArea;
		this.fileChooser = fileChooser;
	}

	/**
	 * Handle the user clicking on the open button.
	 *
	 * @param e
	 *            the event object
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int returnVal = fileChooser.showOpenDialog(directoryFrame.getContentPane());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if (file.exists()) {				

//				directoryFrame.pack();
//		        directoryFrame.setLocationRelativeTo(null);
//		        directoryFrame.setVisible(true);
				
				directoryLabel.setText("Selected Directory " + file.getAbsolutePath());
				

				// Make the root.
				FileNode fileTree = new FileNode(file, null, FileType.DIRECTORY);
				Database.buildTree(file, fileTree);
				  

				// Build the string representation and put it into the text
				// area.
				try {
					Database.buildImageList(fileTree);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				comboBox.removeAllItems();
				for (File f : Database.photoDatabase.keySet()) {
			        comboBox.addItem(f);
				}
				tagComboBox.removeAllItems();
				for (String tag : TagDatabase.tagDatabase.keySet()) {
			        tagComboBox.addItem(tag);
				}
				
				// Display a temporary message while the directory is
				// traversed.
				Database.buildTagMap();
				if (Database.testtagDatabase.equals(TagDatabase.tagDatabase)){
					dispArea.setText("Input new tags in the left window. Tags begin with @");
				}
				else{
					dispArea.setText("Error with tag map, may because imporpor exit last time. "
							+ "Fixed error by changing tag map to the latest version.");
					TagDatabase.tagDatabase.clear();
					TagDatabase.tagDatabase.putAll(Database.testtagDatabase);
				}
				
			}
		}
	}
}