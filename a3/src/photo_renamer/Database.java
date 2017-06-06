package photo_renamer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.activation.MimetypesFileTypeMap;

import photo_renamer.FileNode;
import photo_renamer.FileType;
import photo_renamer.Photo;





/*
* PhotoDatabase is one of observers of photo object. When photo object add tag or remove tag, 
* it will notify photoDatabase and other these databases and change databases. 
* This one-to-many relationship between photo and four databases are Observer Pattern.
*/

/**
 * HashMaps called photoDatabase that keep track of all the files
 * as Keys and photo objects as its values. 
 * A HashMap called testtagDatabase created by the photoDatabase that compare 
 * with the tagDatabase to ensure tagDatabase is correct.
 */
public class Database implements Serializable{
	public static HashMap<File,Photo> photoDatabase = new HashMap<File,Photo>();
	public static HashMap<String, Integer> testtagDatabase = new HashMap<String, Integer>();
	private static final long serialVersionUID = 3950536640070526829L;
	
	
	

	/**
	 * Return true if file is an image file and false otherwise.
	 * 
	 * @return True if file is an image file.
	 */
	public static boolean isImage(File file){
		boolean b = true;
        String mimetype= new MimetypesFileTypeMap().getContentType(file);
        String type = mimetype.split("/")[0];
        if(type.equals("image")){
            b = true;
        }
        else {
            b = false;
        }
        return b;
	}

	/**
	 * Build the tree of nodes rooted at file in the file system; note curr is
	 * the FileNode corresponding to file, so this only adds nodes for children
	 * of file to the tree. Precondition: file represents a directory.
	 * 
	 * @param file
	 *            the file or directory we are building
	 * @param curr
	 *            the node representing file
	 */
	public static void buildTree(File file, FileNode curr) {
		File[] filelist = file.listFiles();
		for (File files : filelist){
			if (files.isDirectory()){
				FileNode newcurr = new FileNode(files, curr, FileType.DIRECTORY);
				buildTree(files, newcurr);
				curr.addChild(files.getName(), newcurr);
			}
			else if (isImage(files)){
				FileNode newcurr = new FileNode(files, curr, FileType.FILE);
				curr.addChild(files.getName(), newcurr);
			}
		}
	}

	/**
	 * Build the photoDatabase.
	 * 
	 * @param fileNode
	 *            		the fileNode we are adding to photoDatabase. 
	 */
	public static void buildImageList(FileNode fileNode)throws InterruptedException, IOException, FileNotFoundException{
		if (!fileNode.isDirectory()){
			   if (!photoDatabase.containsKey(fileNode.getfile())){
				   Photo newphoto = new Photo(fileNode.getfile());
				   photoDatabase.put(fileNode.getfile(),newphoto);
			   } 
		   }
		for (FileNode children : fileNode.getChildren()){
		   buildImageList(children);
		   }	
	}

	/**
	 * Return all the file names in photoDatabase followed by the size of
	 * photoDatabase.
	 * 
	 * @return all the file names in photoDatabase and the size of photoDatabase. 
	 */
	public static String showDatabase(){
		String s = "";
		List<File> database = new ArrayList<File>();
		for (File file : photoDatabase.keySet()){
			database.add(file);
			s = s + file.getName();
		}
		return s;
	}

	/**
	 * Serialization of photoDatabase.
	 * 
	 */
	public static void savePhotoDatabase(){
		try {
			FileOutputStream fileOut = new FileOutputStream("photoData");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(photoDatabase);
			out.close();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deserialization of photoDatabase.
	 * 
	 */
	public static void readPhotoDatabase(){
		try {
			FileInputStream fileIn = new FileInputStream("photoData");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			try {
				photoDatabase=(HashMap<File,Photo>) in.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			in.close();
			fileIn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	
	/**
	 * Rename files and change photo in photoDatabase
	 * 
	 * @param file
	 * 	        the file that will be renamed
	 * @param photo
	 * 			the Photo object that will be changed
	 */
	
	public static void renameFile(File file, Photo photo){
		photoDatabase.remove(file);
		file.renameTo(new File(photo.name));
		File fi = new File(photo.name);
		photoDatabase.put(fi, photo);
		photo.file = fi;
	}
	
	/**
	 * Building tagDatabase which is a HashMap with the name of the tag as its keys
	 * and the number of Photo objects with that tag as its values.
	 * 
	 */	
	
	public static void buildTagMap(){
		for (Photo photo : Database.photoDatabase.values()){
			for (String tag : photo.tags){
				if (testtagDatabase.containsKey(tag) ){
					Integer value = testtagDatabase.get(tag) + 1;
					testtagDatabase.replace(tag, testtagDatabase.get(tag), value);
				}
				else{
					testtagDatabase.put(tag, 1);
				}
			}
		}
	}
}