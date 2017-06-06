package photo_renamer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;





/*
* TimeLog is one of observers of photo object. When photo object add tag or remove tag, 
* it will notify TimeLog and other these databases and change databases. 
* This one-to-many relationship between photo and four databases are Observer Pattern.
*
*
*TimeLog class is also a Singleton Pattern. The class creates an object that 
*while making sure that only single object logtime gets created. This class provides a 
*way to access its only object which can be accessed directly without need to 
*instantiate the object of the class.
*/

/**
*And an ArrayList called logtime that records all the time stamps.
*/

public class TimeLog implements Serializable{
	
	public static ArrayList<Timestamp> logtime = new ArrayList<Timestamp>();
	private static final long serialVersionUID = 3950536640070526829L;
	
	
	/**
	 * Serialization of logtime. 
	 * 
	 */
	public static void saveLogTimeData() throws FileNotFoundException, IOException{
		
		FileOutputStream fileOut = new FileOutputStream("logTimeData");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(logtime);
		out.close();
		fileOut.close();		
	
	}

	/**
	 * Deserialization of logtime. 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	public static void readLogTimeData() throws IOException,FileNotFoundException, ClassNotFoundException{
	
		FileInputStream fileIn = new FileInputStream("logTimeData");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		
		logtime=(ArrayList<Timestamp>)  in.readObject();
		
		in.close();	
		fileIn.close();
		
	}


}
