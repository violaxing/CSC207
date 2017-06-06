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
import javax.swing.JTextArea;

public class LogF extends LogButton implements ActionListener {	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Revert")){
			
			if (TimeLog.logtime.contains(timeComboBox.getSelectedItem())){
				try {
					Search.revertAllChanges(timeComboBox.getItemAt(timeComboBox.getSelectedIndex()));
					logTextArea.removeAll();
					logTextArea.setText(Log.logToString());
					timeComboBox.removeAllItems();
					for (Timestamp t : TimeLog.logtime) {
				        timeComboBox.addItem(t);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			comboBox.removeAllItems();
			for (File f : Database.photoDatabase.keySet()) {
				comboBox.addItem(f);
			}
			
		} else if (cmd.equals("Exit Log")){
			
			logFrame.setVisible(false);
			
		}
	}
}