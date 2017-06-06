
package photo_renamer;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;



public class ExitButton extends PhotoRenamer implements ActionListener {	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String cmd = e.getActionCommand();
		if (cmd.equals("Exit")){
			try {
				Log.saveLogData();
			} catch (ClassNotFoundException | IOException | InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}		
			try {
				TimeLog.saveLogTimeData();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Database.savePhotoDatabase();
			TagDatabase.savetagDatabase();
			System.exit(0);
		}
	}
}