package svk.lubsar.j2dgl.fileio;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import svk.lubsar.j2dgl.utils.Constants;
/**
* Utility class with static methods that load and save Strings and arrays of Strings from/to files.
* 
* @see #loadText(String)
* @see #loadTextAsArray(String)
* @see #saveToFile(String, String)
* @see #saveToFile(String[], String, boolean)
*/
public class TextPort {
	/**
	 * Loads textfile on given path as class resource (see documentation).
	 * @param path name of resource
	 * @return resource as buffered image
	 * @throws IOException if error occurs while loading the resource
	 */
	public static String loadText(String path) {
		File f = new File(path);
		return loadText(f);
	}
	
	public static String loadText(String path, String separator) {
		File f = new File(path);
		return loadText(f, separator);
	}
	
	public static String loadText(File file) {
		return loadText(file, Constants.LINE_SEPARATOR);
	}
	
	public static String loadText(File file, String separator) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			StringBuilder ret = new StringBuilder();
			String line = "";
			
			do {
				line = reader.readLine();
				ret.append(line);
				ret.append(separator);
			} while (line != null);
			
			reader.close();
			
			return ret.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String[] loadTextAsArray(String path) {
		File f = new File(path);
		return loadTextAsArray(f);
	}
	
	public static String[] loadTextAsArray(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			List<String> ret = new ArrayList<String>();
			String line = "";
			
			do {
				line = reader.readLine();
					ret.add(line);
			} while (line != null);
				
			reader.close();
			
			return (String[]) ret.toArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		return null;
	}
		
	public static void saveToFile(String string, File file) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			writer.write(string);
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveToFile(String string, String path) {
		saveToFile(string, new File(path));
	}
	
	public static void saveToFile(String[] strings, File file, boolean stringPerLine) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			for(String s : strings) {
				writer.write(s);
				if(stringPerLine)
					writer.newLine();
			}
			
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveToFile(String[] strings, String path, boolean stringPerLine) {
		File f = new File(path);
		saveToFile(strings, f, stringPerLine);
	}
}
