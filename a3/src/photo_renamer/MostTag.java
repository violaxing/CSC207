package photo_renamer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MostTag extends PhotoRenamer implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Most Common Tag")){
			dispArea.setText(Search.searchMostCommonTag());
		} else if (cmd.equals("Most Similarly Tagged")){
			dispArea.setText(Search.mostSimilarlyPhotos());
		} else if (cmd.equals("Most Tags")){
			dispArea.setText(Search.fileWithMostTags());
		}
	}
}
