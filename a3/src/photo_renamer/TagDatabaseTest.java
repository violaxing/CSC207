package photo_renamer;

import static org.junit.Assert.*;


import java.io.File;
import java.io.IOException;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;





public class TagDatabaseTest {
	/** The directory in which the test directory structures will be created. */
	private final static String TEST_PATH = ".";

	/** The root of the first test. */
	private String TEST_DIR_1_NAME = TEST_PATH + "/" + "test_dir1";
	private String TEST_DIR_2_NAME = TEST_PATH + "/" + "test_dir2";


	/** The File for the first test directory. */
	private File test1File;



	
	HashMap<String, Integer> testMap = new HashMap<String, Integer>();

	
	/**
	 * Create the test image structures.
	 * @throws IOException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * 
	 * @throws Exception
	 * 
	 * JUnit test of the null tagMap.
	 */
	 
	
	@Test
	public void setUp() throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException {
		Database.photoDatabase.clear();
		TagDatabase.tagDatabase.clear();
		test1File = new File(TEST_DIR_1_NAME);
		String path = test1File.getAbsolutePath();
		test1File.mkdir(); // test_dir1
		new File(TEST_DIR_1_NAME + "/f1.jpg").createNewFile(); // test_dir1/f1
		new File(TEST_DIR_1_NAME + "/f2.jpg").createNewFile(); // test_dir1/f1
		new File(TEST_DIR_1_NAME + "/f3.jpg").createNewFile(); // test_dir1/f1
		new File(TEST_DIR_1_NAME + "/f4.jpg").createNewFile(); // test_dir1/f1
		
		
		FileNode root = new FileNode(test1File, null, FileType.DIRECTORY);
		Database.buildTree(test1File, root);
		try {
			Database.buildImageList(root);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(path);
		
	 
		 //JUnit test of the null tagMap.

		assertEquals(testMap, TagDatabase.tagDatabase);
		
		
		
		//JUnit test of legal addTag input.
		for(File file : Database.photoDatabase.keySet()){				
			if (file.getName().equals("f1.jpg")){
			Database.photoDatabase.get(file).addTag("111");
			}
		}

		
		
		assertEquals(testMap, TagDatabase.tagDatabase);
		
		//JUnit test of legal addTag input.
		
		for(File file : Database.photoDatabase.keySet()){
			if (file.getName().equals("f1.jpg")){
				Database.photoDatabase.get(file).addTag("@111");
				}
		}

		testMap.put("111", 1);
		assertEquals(testMap, TagDatabase.tagDatabase);
		
		
		//JUnit test of addTag input with more than one tag once
		for(File file : Database.photoDatabase.keySet()){
			if (file.getName().equals("f1.jpg")){				
				Database.photoDatabase.get(file).addTag("@111@222@333");
			}
		
		}
		
		testMap.put("222", 1);
		testMap.put("333", 1);

		assertEquals(testMap, TagDatabase.tagDatabase);
		
		//JUnit test of addTag input with  ""
		for(File file : Database.photoDatabase.keySet()){
			if (file.getName().equals("f1.jpg")){
				Database.photoDatabase.get(file).addTag("");
				}
		}
	
		assertEquals(testMap, TagDatabase.tagDatabase);
		
		
		//JUnit test of removeTag input with file does not contain that tag
		for(File file : Database.photoDatabase.keySet()){
			if (file.getName().equals("f2.jpg")){
				Database.photoDatabase.get(file).removeTag("@222");
				}
			}

		assertEquals(testMap, TagDatabase.tagDatabase);
		
		//JUnit test of removeTag input with illegal tag
		for(File file : Database.photoDatabase.keySet()){
			if (file.getName().equals("f1.jpg")){
				Database.photoDatabase.get(file).removeTag("222");
				}
			}

				assertEquals(testMap, TagDatabase.tagDatabase);
				
		//JUnit test of removeTag input with file contains that tag
		for(File file : Database.photoDatabase.keySet()){
			if (file.getName().equals("f1.jpg")){
				Database.photoDatabase.get(file).removeTag("@222");
				}
			}
		testMap.replace("222", 0);
		assertEquals(testMap, TagDatabase.tagDatabase);	
		
		//JUnit test of removeTag input with ""
		for(File file : Database.photoDatabase.keySet()){
			if (file.getName().equals("f1.jpg")){
				Database.photoDatabase.get(file).removeTag("");
				}
			}
		assertEquals(testMap, TagDatabase.tagDatabase);
		
		//JUnit test of removeAllTag 
		for(File file : Database.photoDatabase.keySet()){
			if (file.getName().equals("f1.jpg")){
				Database.photoDatabase.get(file).removeAllTag();
				}
			}
		testMap.replace("111", 0);
		testMap.replace("333", 0);

		assertEquals(testMap, TagDatabase.tagDatabase);	
		

		
		
	}
	
	
	

	
	
	
	
	

	
	
	@After
	public void tearDown() throws IOException {
		delete(test1File);
		Database.photoDatabase.clear();
		TagDatabase.tagDatabase.clear();
	}
	
	/**
	 * Delete f and its contents.
	 * 
	 * @param f
	 *            the file to delete.
	 */
	public static void delete(File f) {
		if (f.isDirectory()) {
			for (File c : f.listFiles())
				delete(c);
		}
	}
	

}
