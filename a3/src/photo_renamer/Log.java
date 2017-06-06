package photo_renamer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;




/*
* LogDatabase is one of observers of photo object. When photo object add tag or remove tag, 
* it will notify LogDatabase and other these databases and change databases. 
* This one-to-many relationship between photo and four databases are Observer Pattern.
*

*Log class is also a Singleton Pattern. The class creates an object that 
*while making sure that only single object log gets created. This class provides a way to access its only object 
*which can be accessed directly without need to instantiate the object of the class.
*
*/


/**
 * LinkedHashMaps called log that keep track of all the time stamps
 * as Keys and a LogValue object as its values.
 */
public class Log implements Serializable{
	public static HashMap<Timestamp, LogValue> log = new HashMap<Timestamp, LogValue>();
	private static final long serialVersionUID = 3950536640070526829L;

	/**
	 * Add the time stamp as a Key and the LogValue object as a Value to log.
	 * And add the time stamp to logtime.
	 * 
	 * @param timestamp
	 *            		the timestamp when the edit happens.
	 * @param original
	 *            		the Photo object that the edit is implemented on.
	 * @param edit
	 *            		the type of edit that is being implemented.
	 * @param tags
	 *            		the tags affected by this edit.
	 */
	public static void editTag(Timestamp t, Photo original, String edit, String tags){
		LogValue logvalue = new LogValue(original,edit,tags);
		log.put(t, logvalue);	
		TimeLog.logtime.add(t);
	}

	
	/**
	 * Serialization of log. 
	 * @throws IOException 
	 * 
	 */
	public static void saveLogData() throws FileNotFoundException, IOException, ClassNotFoundException,InterruptedException{

		FileOutputStream fileOut = new FileOutputStream("logData");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(log);
		out.close();
		fileOut.close();
		
		
	}

	/**
	 * Deserialization of log.
	 * @throws ClassNotFoundException 
	 * 
	 */
	public static void readLogData() throws FileNotFoundException, IOException, ClassNotFoundException, InterruptedException{
		
		FileInputStream fileIn = new FileInputStream("logData");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		
		log=(HashMap<Timestamp, LogValue>)  in.readObject();
		
		in.close();
		fileIn.close();
		
	}

	
	/**
	 * Return a String version of log.
	 * 
	 * @return a String representation of log.
	 * 
	 */
	public static String logToString(){
		String s = "";
		for(Timestamp t : TimeLog.logtime){
			s = s + t.toString() + " " +log.get(t).photo.filename+" " +
		log.get(t).action + " " + log.get(t).tags + "\n";
		}
		return s;
	}
}