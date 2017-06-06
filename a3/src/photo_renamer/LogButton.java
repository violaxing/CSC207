package photo_renamer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class LogButton extends PhotoRenamer implements ActionListener {	
	
	
	public static JComboBox<Timestamp> timeComboBox;
	public static JPanel westLogPanel;
	public static JButton logExitButton;
	public static JButton revertButton;
	public static JFrame logFrame;
	public static JPanel topLogPanel;
	public static JTextArea logTextArea;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Log")){
			logFrame = new JFrame("Log");
			
			logTextArea = new JTextArea(30, 50);
			logTextArea.setText(Log.logToString());
			logTextArea.setEditable(false);
			JScrollPane dispScrollPane = new JScrollPane(logTextArea);
			
			// Top Panel.
			topLogPanel = new JPanel();
			topLogPanel.add(dispScrollPane, BorderLayout.CENTER);
			
			
			// Combo Box for time stamps.
			timeComboBox = new JComboBox<Timestamp>();
			timeComboBox.setEditable(false);
			
			// Adds the Timestamp objects in the log to the timeComboBox.
			timeComboBox.removeAllItems();
			for (Timestamp t : TimeLog.logtime) {
		        timeComboBox.addItem(t);
			}
					
			
			// West Panel.
			westLogPanel = new JPanel();
			westLogPanel.add(timeComboBox, BorderLayout.CENTER);
			
			// Exit Button
			logExitButton = new JButton("Exit Log");
			ActionListener logExitButtonListener = new LogF();
			logExitButton.addActionListener(logExitButtonListener);
			
			// Revert Button.
			revertButton = new JButton("Revert");
			ActionListener revertButtonListener = new LogF();
			revertButton.addActionListener(revertButtonListener);
			
			// Button Panel.
			JPanel logButtonPanel = new JPanel();
			logButtonPanel.add(logExitButton);
			logButtonPanel.add(revertButton);
			
			// Put it all together.
			Container c = logFrame.getContentPane();
			c.add(westLogPanel, BorderLayout.WEST);
			c.add(topLogPanel, BorderLayout.EAST);
			c.add(logButtonPanel, BorderLayout.SOUTH);
			logFrame.pack();
			logFrame.setVisible(true);;
		} 
	}
}