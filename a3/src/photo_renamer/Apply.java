
package photo_renamer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Apply extends PhotoRenamer implements ActionListener {
	/** The window the button is in. */
	private JFrame directoryFrame;
	/** The label for the full path to the chosen directory. */
	private JLabel directoryLabel;
	/** The area to use to display the nested directory contents. */
	private JTextArea textArea;
	
	public Apply(JFrame dirFrame, JLabel dirLabel, JTextArea textArea){
		this.directoryFrame = dirFrame;
		this.directoryLabel = dirLabel;
		this.textArea = textArea;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Apply")){
			if (addTagButton.isSelected()){		
				File newfile = comboBox.getItemAt(comboBox.getSelectedIndex());
				if (this.textArea.getText().isEmpty()){
					String tag = "@" + tagComboBox.getItemAt(tagComboBox.getSelectedIndex());
					Photo selectp = Database.photoDatabase.get(newfile);
					selectp.addTag(tag);
					Database.renameFile(newfile, selectp);
		
					dispArea.setText("Successfuly add tag " + tag + ". Now image name is " + selectp.name);
					comboBox.removeAllItems();
					for (File f : Database.photoDatabase.keySet()) {
						comboBox.addItem(f);
				      }
					
				}
				else {
					String tag = this.textArea.getText();
					Photo selectp = Database.photoDatabase.get(newfile);
					selectp.addTag(tag);
					Database.renameFile(newfile, selectp);
		
					dispArea.setText("Successfuly add tag " + tag + ". Now image name is " + selectp.name);
					comboBox.removeAllItems();
					for (File f : Database.photoDatabase.keySet()) {
						comboBox.addItem(f);
				      }
					tagComboBox.removeAllItems();
					for (String tags : TagDatabase.tagDatabase.keySet()) {
				        tagComboBox.addItem(tags);
					}
					textArea.removeAll();
				}
				
				
			}
			else if (remTagButton.isSelected()){
				File newfile = PhotoRenamer.comboBox.getItemAt(PhotoRenamer.comboBox.getSelectedIndex());
				if (this.textArea.getText().isEmpty()){
					String tag = "@" + tagComboBox.getItemAt(tagComboBox.getSelectedIndex());
					Photo selectp = Database.photoDatabase.get(newfile);
					selectp.removeTag(tag);
					Database.renameFile(newfile, selectp);
		
					dispArea.setText("Successfuly remove tag " + tag + ". Now image name is " + selectp.name);
					comboBox.removeAllItems();
					for (File f : Database.photoDatabase.keySet()) {
						comboBox.addItem(f);
				      }
					
				}
				else{
					String tag = this.textArea.getText();
					Photo selectp = Database.photoDatabase.get(newfile);
					selectp.removeTag(tag);
					Database.renameFile(newfile, selectp);
					dispArea.setText("Successfuly remove tag " + tag + ". Now image name is " + selectp.name);
					comboBox.removeAllItems();
					for (File f : Database.photoDatabase.keySet()) {
						comboBox.addItem(f);
				      }
					textArea.removeAll();
				}
				
			}
			else if (remAllTagButton.isSelected()){
				File newfile = PhotoRenamer.comboBox.getItemAt(PhotoRenamer.comboBox.getSelectedIndex());
				Photo selectp = Database.photoDatabase.get(newfile);
				selectp.removeAllTag();
				Database.renameFile(newfile, selectp);
				dispArea.setText("Successfuly remove all tag " + ". Now image name is "+ selectp.name);
				comboBox.removeAllItems();
				for (File f : Database.photoDatabase.keySet()) {
					comboBox.addItem(f);
			      }
				textArea.removeAll();
				
			}
		}
		
		
	}
}
