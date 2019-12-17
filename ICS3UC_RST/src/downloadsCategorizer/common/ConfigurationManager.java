package downloadsCategorizer.common;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConfigurationManager {
	public static final File DOWNLOADS_FOLDER = new File(System.getProperty("user.home"), "Downloads/");
	private static final File CONFIGURATION_FILE = new File(DOWNLOADS_FOLDER, ".dcconfig");
	private static final File INDEX_FILE = new File(DOWNLOADS_FOLDER, ".dcindex");

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

	public static Map<String, List<File>> loadIndexes() throws Exception {
		// Declare a map of string,list of files called returnValue, equal to a blank map
		Map<String, List<File>> returnValue = new HashMap<String, List<File>>();
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
}
