package photo_renamer;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A representation of class Search.
 */
public class Search implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3950536640070526829L;
	/**
	 * Return the name of the Photo object with the most tags.
	 * 
	 * @return the name of the Photo object with the most tags.
	 */
	public static String fileWithMostTags(){
		if (Database.photoDatabase.isEmpty()){
			return "no files in database";
		}
		else{
			Photo mostTagPhoto = Database.photoDatabase.values().iterator().next();
			for (Photo photo : Database.photoDatabase.values()){
				if (mostTagPhoto.tags.size() < photo.tags.size()){
					mostTagPhoto = photo;
				}
			}
			return mostTagPhoto.name;
		}
	}

	/**
	 * Return the most common tag.
	 * 
	 * @return the most common tag.
	 */
	public static String searchMostCommonTag(){
		if (TagDatabase.tagDatabase.isEmpty()){
			return "no files in database";
		}
		else{
			Integer value = 0;
			ArrayList<String> tags = new ArrayList<String>();
			for (String tag : TagDatabase.tagDatabase.keySet()){
				if (tags.isEmpty()){
					value = 0;
				}
				else{
					value = TagDatabase.tagDatabase.get(tags.get(0));
				}
				if (value < TagDatabase.tagDatabase.get(tag)){
					tags.clear();
					tags.add(tag);
				}
				if (value == TagDatabase.tagDatabase.get(tag)){
					tags.add(tag);
				}
			}
			return tags.toString();
		}
	}

	/**
	 * Look in log for all the changes made to the Photo object p in p.timestamplist
	 * and construct a LinkedHashMap with the time stamps as the keys and a LogValue
	 * object as the values. (A log specific to the Photo object p).
	 * 
	 * @param p
	 * 			the Photo object whose log will be constructed.
	 */
	public static String mostSimilarlyPhotos(){
		if (TagDatabase.tagDatabase.isEmpty()){
			return "no files in database";
		}
		else{
			ArrayList<Photo>photos = new ArrayList<Photo>();
			Integer sametag = 0;
			for (Photo photo1 : Database.photoDatabase.values()){
				for (Photo photo2 : Database.photoDatabase.values()){
					if (photo1 != photo2){
						Integer value = 0;
						for (String tag :photo1.tags){	
							if (photo2.tags.contains(tag)){
								value = value + 1;
							}	
						}
						if (sametag <= value){
							sametag = value;
							photos.clear();
							photos.add(photo1);
							photos.add(photo2);
						}
					}
				}
			}
			return photos.get(0).file.getName() + " and " + photos.get(1).file.getName();
		}
	}
	
	/**
	 * Look in log for all the changes made to the Photo object p in p.timestamplist
	 * and construct a LinkedHashMap with the time stamps as the keys and a LogValue
	 * object as the values. (A log specific to the Photo object p).
	 * 
	 * @param p
	 * 			the Photo object whose log will be constructed.
	 */
	public static void revertAllChanges(Timestamp t) throws IOException{
		int index = TimeLog.logtime.indexOf(t);
		int i = TimeLog.logtime.size()-1;
		while(i >= index){
			if (Log.log.get(TimeLog.logtime.get(i)).action.equals("addTag")){
				Log.log.get(TimeLog.logtime.get(i)).photo.removeTag(Log.log.get(TimeLog.logtime.get(i)).tags);
				Database.renameFile(Log.log.get(TimeLog.logtime.get(i)).photo.file, Log.log.get(TimeLog.logtime.get(i)).photo);
			}
			else if (Log.log.get(TimeLog.logtime.get(i)).action.equals("removeTag")){
				Log.log.get(TimeLog.logtime.get(i)).photo.addTag(Log.log.get(TimeLog.logtime.get(i)).tags);
				Database.renameFile(Log.log.get(TimeLog.logtime.get(i)).photo.file, Log.log.get(TimeLog.logtime.get(i)).photo);
			}
			else if (Log.log.get(TimeLog.logtime.get(i)).action.equals("removeAllTag")){
				Log.log.get(TimeLog.logtime.get(i)).photo.addTag(Log.log.get(TimeLog.logtime.get(i)).tags);
				Database.renameFile(Log.log.get(TimeLog.logtime.get(i)).photo.file, Log.log.get(TimeLog.logtime.get(i)).photo);
			}
			i = i - 1;
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * Look in log for all the changes made to the Photo object p in p.timestamplist
	 * and construct a LinkedHashMap with the time stamps as the keys and a LogValue
	 * object as the values. (A log specific to the Photo object p).
	 * 
	 * @param p
	 * 			the Photo object whose log will be constructed.
	 */
	public static void revertOnePhotoChanges(Timestamp t, Photo photo) throws IOException{
		int index = photo.timestamplist.indexOf(t);
		List<Timestamp> revert = photo.timestamplist.subList(index, photo.timestamplist.size()-1);
		int i = revert.size();
		while(i >= 0){
			if (Log.log.get(revert.get(i)).action == "addTag"){
				Log.log.get(revert.get(i)).photo.removeTag(Log.log.get(revert.get(i)).tags);
				Database.renameFile(Log.log.get(TimeLog.logtime.get(i)).photo.file, Log.log.get(TimeLog.logtime.get(i)).photo);

			}
			if (Log.log.get(revert.get(i)).action == "removeTag"){
				Log.log.get(revert.get(i)).photo.addTag(Log.log.get(revert.get(i)).tags);
				Database.renameFile(Log.log.get(TimeLog.logtime.get(i)).photo.file, Log.log.get(TimeLog.logtime.get(i)).photo);

			}
			if (Log.log.get(revert.get(i)).action == "removeAllTag"){
				Log.log.get(revert.get(i)).photo.addTag(Log.log.get(revert.get(i)).tags);
				Database.renameFile(Log.log.get(TimeLog.logtime.get(i)).photo.file, Log.log.get(TimeLog.logtime.get(i)).photo);

			}
			i = i -1;
			
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
	
	