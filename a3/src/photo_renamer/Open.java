package photo_renamer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Open extends PhotoRenamer implements ActionListener {
	/** The window the button is in. */
	private JFrame directoryFrame;
	/** The label for the full path to the chosen directory. */
	private JLabel directoryLabel;
	/** The area to use to display the nested directory contents. */
	private JTextArea textArea;
	
	public Open(JFrame dirFrame, JLabel dirLabel, JTextArea textArea){
		this.directoryFrame = dirFrame;
		this.directoryLabel = dirLabel;
		this.textArea = textArea;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Open Image")){
			File file1 = (File)comboBox.getSelectedItem();
			// Add image file to JFrame.
			BufferedImage image = null;
	        try
	        {
	          image = ImageIO.read(file1);
	        }
	        catch (Exception e1)
	        {
	          e1.printStackTrace();
	          System.exit(1);
	        }
	        ImageIcon imageIcon = new ImageIcon(image);
	        JLabel imageLabel = new JLabel(null, imageIcon, JLabel.CENTER);
	        imagePanel.removeAll();
	        imagePanel.add(imageLabel);
	        directoryFrame.pack();
		}
		
	}
}
