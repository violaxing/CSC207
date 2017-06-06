package photo_renamer;

import java.util.*;
import java.sql.Timestamp;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;




/*
* Photo is the subject, and photo database,tag database,
* logDatabase and logtime are observer. When photo object ad tag or remove tag, it will 
* notify these databases and change databases. This one-to-many relationship between 
* photo and four databases are Observer Pattern.
*/

/**
 * A representation of a Photo.
 */
public class Photo implements Serializable{
	public String name;
	public String filename;
	public List<String> tags;
	public List<Timestamp> timestamplist;
	public File file;
	public String last;
	private static final long serialVersionUID = 3950536640070526829L;

	/**
     * Creates a new Photo object with name name and a List of existing tags called
     * tags.
     * 
     * @param file
     * 				the Photo file.
     */
	public Photo(File file) {
		String nam = file.getName().substring(0,file.getName().length()-4);
 		List<String> lst = new ArrayList<>();
		String[] array = nam.split("@");
		this.tags = new ArrayList<>();
		for (int i = 0; i < array.length; i++){
			lst.add(array[i]);
			}
		this.filename = lst.get(0);
		this.name = lst.get(0);
		if (lst.size() == 1){
			this.tags = new ArrayList<>();
		}
		else{
			for (int i = 1; i < array.length; i++){
				this.tags.add(array[i]);
				if (TagDatabase.tagDatabase.containsKey(array[i])){
					Integer value = TagDatabase.tagDatabase.get(array[i]) + 1;
					TagDatabase.tagDatabase.replace(array[i], TagDatabase.tagDatabase.get(array[i]), value);
				}
				else{
					TagDatabase.tagDatabase.put(array[i], 1);
				}
			}
		}
		this.timestamplist = new ArrayList<Timestamp>();
		this.file = file;
		this.last = file.getName().substring(file.getName().length()-4,file.getName().length());
		Database.photoDatabase.put(this.file, this);
	}
	
	/**
	 * Add tag to the List this.tags if tag is not contained in this.tags.
	 * 
	 * @param tag
	 *            the tag that needs to be added to the Photo object.
	 */
	public void addTag(String tags){
		List<String> array = Arrays.asList(tags.split("@"));
		String str = "";
		for (String tag : array.subList(1, array.size())){
			if (!(this.tags.contains(tag))){
				str = str + "@" + tag;
				this.tags.add(tag);
				if (TagDatabase.tagDatabase.containsKey(tag)){
					Integer value = TagDatabase.tagDatabase.get(tag) + 1;
					TagDatabase.tagDatabase.replace(tag, TagDatabase.tagDatabase.get(tag), value);
				}
				else{
					TagDatabase.tagDatabase.put(tag, 1);
				}
			}
		}
		java.util.Date date = new java.util.Date();
		Timestamp t = new Timestamp(date.getTime());
		this.timestamplist.add(t);
	    Log.editTag(t, this, "addTag", str);
	    this.name = this.toString();	   
	}

	/**
	 * Remove tag from the List this.tags if tag is contained in this.tags.
	 * 
	 * @param tag
	 *            the tag that needs to be removed from the Photo object.
	 */
	public void removeTag(String tags){
		List<String> array = Arrays.asList(tags.split("@"));
		String str = "";
		for (String tag : array.subList(1, array.size())){
			if (this.tags.contains(tag)){
				this.tags.remove(tag);
				str = str + "@" + tag;
				if (TagDatabase.tagDatabase.containsKey(tag)){
					Integer value = TagDatabase.tagDatabase.get(tag) - 1;
					TagDatabase.tagDatabase.replace(tag, TagDatabase.tagDatabase.get(tag), value);
				}
			}	
		}
		java.util.Date date = new java.util.Date();	
		Timestamp t = new Timestamp(date.getTime()); 	
		this.timestamplist.add(t);
		Log.editTag(t, this, "removeTag", str);
		this.name = this.toString();
		Database.photoDatabase.replace(this.file, this);
		
	}

	/**
	 * Remove all tags from the List this.tags.
	 * 
	 */
	public void removeAllTag(){
		java.util.Date date = new java.util.Date();
		Timestamp t = new Timestamp(date.getTime());
		this.timestamplist.add(t);
		String strtag = "";
		for (String tag : this.tags){
			strtag = strtag + "@" + tag;
			if (TagDatabase.tagDatabase.containsKey(tag)){
				Integer value = TagDatabase.tagDatabase.get(tag) - 1;
				TagDatabase.tagDatabase.replace(tag, TagDatabase.tagDatabase.get(tag), value);
			}
		}
		Log.editTag(t, this, "removeAllTag", strtag);
		this.tags.clear();
		this.name = this.toString();
		Database.photoDatabase.replace(this.file, this);
		
	}

	/**
	 * Return a String representation of the new name.
	 * 
	 * @return	the name of the Photo object.
	 */
	@Override
    public String toString(){
		String photoName = this.file.getParent() +"/" + this.filename;
		for (int i = 0; i < this.tags.size(); i++) {
			photoName += "@" + this.tags.get(i);
		}
		return photoName  + this.last;
	}
}