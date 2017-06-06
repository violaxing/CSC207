package photo_renamer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.sql.Timestamp;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;



/* 	In this java project, we use two design pattern, Singleton Pattern and Observer
 * Pattern.
 * 
 *	 For Singleton Pattern, log in Log class and logtime in TimeLog class is Singleton
 *Pattern. These two classes both create an object that while making sure that 
 *only single object gets created. This class provides a way to access its only object 
 *which can be accessed directly without need to instantiate the object of the class.
 * 
 * 
 * 	For Observer Pattern, photo object is the subject, and photo database,tag database,
 * logDatabase and logtime are observer. When photo object ad tag or remove tag, it will 
 * notify these databases and change databases. This one-to-many relationship between 
 * photo and four databases are Observer Pattern.
 * 
 * 
 * 
 */

/**
 * Create and show a directory explorer, which displays the contents of a
 * directory.
 */
public class PhotoRenamer {

	public static JFrame directoryFrame;
	public static JLabel directoryLabel;
	public static JLabel directoryLabelBottom;
	public static JFileChooser fileChooser;
	public static JTextArea textArea;
	public static JTextArea dispArea;
	public static JButton openButton;
	public static JButton applyButton;
	public static JButton MCTagButton;
	public static JButton MSTagButton;
	public static JButton MostTagButton;
	public static JButton openImageButton;
	public static JButton exitButton;
	public static JButton logButton;
	public static JRadioButton addTagButton;
	public static JRadioButton remTagButton;
	public static JRadioButton remAllTagButton;
	public static JComboBox<File> comboBox;
	public static JComboBox<String> tagComboBox;
	public static JPanel imagePanel;
	public static JPanel eastPanel;

	/**
	 * Create and return the window for the directory explorer.
	 *
	 * @return the window for the directory explorer
	 */
	public static JFrame buildWindow() {
		directoryFrame = new JFrame("Photo Renamer");
		directoryFrame.setDefaultCloseOperation(directoryFrame.DO_NOTHING_ON_CLOSE);

		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		directoryLabel = new JLabel("Select a file: ");
		
		directoryLabelBottom = new JLabel("Select a tag: ");
		
		// Set up display text area.
		dispArea = new JTextArea(3, 50);
		dispArea.setEditable(false);
		
		// Put it on a scroll pane in case output is long.
		JScrollPane dispScrollPane = new JScrollPane(dispArea);

		// Set up the area for the directory contents.
		textArea = new JTextArea(3, 50);
		textArea.setEditable(true);

		// Put it in a scroll pane in case the input is long.
		JScrollPane scrollPane = new JScrollPane(textArea);

		// The directory choosing button.
		openButton = new JButton("Choose Directory");
		openButton.setVerticalTextPosition(AbstractButton.CENTER);
		openButton.setHorizontalTextPosition(AbstractButton.LEADING); // aka
																		// LEFT,
																		// for
																		// left-to-right
																		// locales
		openButton.setMnemonic(KeyEvent.VK_D);
		openButton.setActionCommand("disable");
		ActionListener buttonListener = new User(directoryFrame, directoryLabel, textArea,
				fileChooser);
		openButton.addActionListener(buttonListener);
		
		// Apply Button.
		applyButton = new JButton("Apply");
		ActionListener appButtonListener = new Apply(directoryFrame, directoryLabel, textArea);
		applyButton.addActionListener(appButtonListener);
		
		// Most Common Tag Button.
		MCTagButton = new JButton("Most Common Tag");
		ActionListener MCTagButtonListener = new MostTag();
		MCTagButton.addActionListener(MCTagButtonListener);
		
		// Most Similarly Tagged Button.
		MSTagButton = new JButton("Most Similarly Tagged");
		ActionListener MSTagButtonListener = new MostTag();
		MSTagButton.addActionListener(MSTagButtonListener);
		
		// Most Tags Button.
		MostTagButton = new JButton("Most Tags");
		ActionListener MostTagButtonListener = new MostTag();
		MostTagButton.addActionListener(MostTagButtonListener);
		
		// Open Image Button.
		openImageButton = new JButton("Open Image");
		ActionListener openButtonListener = new Open(directoryFrame, directoryLabel, textArea);
		openImageButton.addActionListener(openButtonListener);
		
		// Log Button.
		logButton = new JButton("Log");
		ActionListener logButtonListener = new LogButton();
		logButton.addActionListener(logButtonListener);
		
		// Exit Button
		exitButton = new JButton("Exit");
		ActionListener exitButtonListener = new ExitButton();
		exitButton.addActionListener(exitButtonListener);
		
		// Combo Box.
		comboBox = new JComboBox<File>();
		comboBox.setEditable(false);
		
		// Adds the files in the photoDatabase to the ComboBox.
		Database.readPhotoDatabase();
		for (File f : Database.photoDatabase.keySet()) {
	        comboBox.addItem(f);
		}
		
		// Tag Combo Box.
		tagComboBox = new JComboBox<String>();
		tagComboBox.setEditable(false);
		
		
		// Adds the files in the tagDatabase to the ComboBox.
		TagDatabase.readtagDatabase();
		for (String tag : TagDatabase.tagDatabase.keySet()) {
			 tagComboBox.addItem(tag);
		}

		// Radio Buttons.
		ButtonGroup radButtons = new ButtonGroup();
		addTagButton = new JRadioButton("Add Tag");
		remTagButton = new JRadioButton("Remove Tag");
		remAllTagButton = new JRadioButton("Remove All Tags");
		radButtons.add(addTagButton);
		radButtons.add(remTagButton);
		radButtons.add(remAllTagButton);
		
		// Button Panel.
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(openButton);
		buttonPanel.add(openImageButton);
		buttonPanel.add(MCTagButton);
		buttonPanel.add(MSTagButton);
		buttonPanel.add(MostTagButton);
		buttonPanel.add(logButton);
		buttonPanel.add(exitButton);	
		buttonPanel.add(applyButton);
		buttonPanel.add(addTagButton);
		buttonPanel.add(remTagButton);
		buttonPanel.add(remAllTagButton);
		
		// Top Panel.
		JPanel topPanel = new JPanel();
		topPanel.add(scrollPane, BorderLayout.CENTER);
		topPanel.add(dispScrollPane, BorderLayout.SOUTH);
		
		// Image Panel.
		imagePanel = new JPanel();
		imagePanel.setSize(500, 800);
		
		// Top East Panel.
		JPanel topEastPanel = new JPanel();
		topEastPanel.add(directoryLabel, BorderLayout.NORTH);
		topEastPanel.add(comboBox, BorderLayout.CENTER);
		
		// Bottom East Panel.
		JPanel eastPanel = new JPanel();
		eastPanel.add(directoryLabelBottom, BorderLayout.NORTH);
		eastPanel.add(tagComboBox, BorderLayout.CENTER);
		
		// Main East Panel.
		JPanel mainEastPanel = new JPanel();
		mainEastPanel.add(topEastPanel, BorderLayout.NORTH);
		mainEastPanel.add(eastPanel, BorderLayout.SOUTH);
		
		// Put it all together.
		Container c = directoryFrame.getContentPane();
		c.add(topPanel, BorderLayout.NORTH);
		c.add(buttonPanel, BorderLayout.SOUTH);
		c.add(imagePanel, BorderLayout.CENTER);
		c.add(mainEastPanel, BorderLayout.EAST);
		directoryFrame.pack();
		return directoryFrame;
	}

	/**
	 * Create and show a directory explorer, which displays the contents of a
	 * directory.
	 *
	 * @param argsv
	 *            the command-line arguments.
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException, IOException, InterruptedException {
		PhotoRenamer.buildWindow().setVisible(true);
		Database.readPhotoDatabase();
		TagDatabase.readtagDatabase();
		Log.readLogData();
		TimeLog.readLogTimeData();
		Database.buildTagMap();
		if (Database.testtagDatabase.equals(TagDatabase.tagDatabase)){
			dispArea.setText("Input new tags in the left area. Tags begin with @");
		}
		else{
			dispArea.setText("Error with tag map, may because imporpor exit last time."
					+ "Fixed error by changing tag map to the latest version.");
			TagDatabase.tagDatabase.clear();
			TagDatabase.tagDatabase.putAll(Database.testtagDatabase);			
		}
	}
}