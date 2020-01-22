package downloadsCategorizer.common;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConfigurationManager {
	public static File DOWNLOADS_FOLDER = new File(System.getProperty("user.home"), "Downloads/");
	public static File CONFIGURATION_FILE;
	public static File INDEX_FILE;
	public static void init() {
		CONFIGURATION_FILE = new File(DOWNLOADS_FOLDER, ".dcconfig");
		INDEX_FILE = new File(DOWNLOADS_FOLDER, ".dcindex");
	}

	/**
	 * Loads the daemon configuration from the .dcconfig file
	 * @return the daemon configuration
	 * @throws Exception
	 */
	public static List<String> loadConfiguration() throws Exception {
		List<String> patterns = new ArrayList<String>();
		Scanner in = new Scanner(CONFIGURATION_FILE);
		// For each line in the configuration file
		while (in.hasNextLine()) {
			String s = in.nextLine();
			if (s.trim().isEmpty())
				continue;
			// Add the line to the patterns list
			patterns.add(s);
		}
		in.close();
		return patterns;
	}
	/**
	 * Loads the indexes from the .dcindex file
	 * @return the indexes
	 * @throws Exception
	 */
	public static Map<String, List<File>> loadIndexes() throws Exception {
		// Declare a map of string,list of files called returnValue, equal to a blank map
		Map<String, List<File>> returnValue = new HashMap<String, List<File>>();
		if(!INDEX_FILE.exists())
			return returnValue;
		// Declare a list of files called currentList, equal to null
		List<File> currentList = null;
		// Declare a variable called pattern, equal to null
		String pattern = null;
		Scanner in = new Scanner(INDEX_FILE);
		// For each line in the index file
		while (in.hasNextLine()) {
			String line = in.nextLine();
			// If line is blank
			if (line.isEmpty())
				// Go to next iteration
				continue;

			// If currentList is null
			if (currentList == null) {
				// Assign pattern to the current line
				pattern = line;
				// Assign currentList to a new list of Files
				currentList = new ArrayList<File>();
				// Else if this line starts with a space
			} else if (line.startsWith(" ")) {
				// Add a new file with the path of the current line in the index file to the
				// current list
				currentList.add(new File(line.trim()));
				// Else
			} else {
				// Put currentList into the returnValue map with a key of pattern
				returnValue.put(pattern, currentList);
				// Assign null to pattern
				pattern = null;
				// Assign null to currentList
				currentList = null;
			}
		}
		in.close();
		return returnValue;
	}
	/**
	 * Saves the indexes to the .dcindex file
	 * @param indexes map
	 * @throws Exception
	 */
	public static void saveIndexes(Map<String,List<File>> indexes) throws Exception{
		// Delete and recreate the file so it is blank
		INDEX_FILE.delete();
		INDEX_FILE.createNewFile();
		// Create a print writer to write to the index file that is set to append to the file
		PrintWriter out = new PrintWriter(new FileWriter(INDEX_FILE,true));
		
		// Iterate over all of the patterns in the indexes set
		for(String s : indexes.keySet()) {
			// Write the name of the pattern out to the file
			out.println(s);
			// Iterate through all the files for this pattern
			for(File f : indexes.get(s)) {
				// Print all the file paths for the files in this pattern to the file
				out.println(" "+f.getPath());
			}
			// Print out END so that the program will know where the end of this pattern is
			out.println("END");
		}
		
		// Close the writer so the changes are saved
		out.close();
	}
}
