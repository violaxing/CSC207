package photo_renamer;

import java.io.Serializable;

/**
 * An object that keeps track of each action performed on a Photo object
 * (i.e. addTad, removeTag) and the tags affected by that action.
 */
public class LogValue implements Serializable{
	public Photo photo;
	public String action;
	public String tags;

	/**
     * Creates a new LogValue object with Photo object photo, a String called action
     * and a String of existing tags called tags.
     * 
     * @param photo
     * 				the Photo object.
     * @param action
     * 				the action performed on the Photo object.
     * @param tags
     * 				the tags affected by action.
     */
	public LogValue(Photo photo, String action, String tags) {
		super();
		this.photo = photo;
		this.action = action;
		this.tags = tags;
	}
}