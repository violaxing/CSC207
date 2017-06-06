package photo_renamer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;




/*
* TagDatabase is one of observers of photo object. When photo object add tag or remove tag, 
* it will notify TagDatabase and other these databases and change databases. 
* This one-to-many relationship between photo and four databases are Observer Pattern.
*/





public class TagDatabase implements Serializable{
	public static HashMap<String, Integer> tagDatabase = new HashMap<String, Integer>();
	
	private static final long serialVersionUID = 3950536640070526829L;
	/**
	 * Serialization of tagDatabase.
	 * 
	 */
	
	public static void savetagDatabase(){
		try {
			FileOutputStream fileOut = new FileOutputStream("tagData");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(tagDatabase);
			out.close();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deserialization of tagDatabase.
	 * 
	 */
	public static void readtagDatabase(){
		try {
			FileInputStream fileIn = new FileInputStream("tagData");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			try {
				tagDatabase=(HashMap<String, Integer>) in.readObject();
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
	
	
	
	

	
	
}
