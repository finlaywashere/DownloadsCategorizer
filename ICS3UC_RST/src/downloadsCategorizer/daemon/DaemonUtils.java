package downloadsCategorizer.daemon;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import downloadsCategorizer.common.ConfigurationManager;

public class DaemonUtils {
	private static File[] lastFiles = new File[0];

	public static List<File> findNewFiles() {
		File DIRECTORY = ConfigurationManager.DOWNLOADS_FOLDER;
		// Declare a list of Files called newFiles
		List<File> newFiles = new ArrayList<File>();
		// Iterate over all the files in the Downloads folder
		for (File f : DIRECTORY.listFiles()) {
			if (f.isDirectory())
				continue;
			// Check if file is not in the lastFiles array
			if (!contains(f, lastFiles))
				// Add it to the newFiles list
				newFiles.add(f);
		}
		// Assign the current files in the Downloads folder to lastFiles
		lastFiles = DIRECTORY.listFiles();
		// Return the newFiles list
		return newFiles;
	}

	/**
	 * Checks if an array of FIles contains a given file
	 * 
	 * @param f     the file to check for in the array
	 * @param files the array to check the file against
	 * @return whether or not the file is in the array
	 */
	private static boolean contains(File f, File[] files) {
		for (File f1 : files) {
			if (f1.equals(f))
				return true;
		}
		return false;
	}

	public static File categorizeFile(String filename, List<String> patterns) {
		// Iterate over the patterns
		for (String s : patterns) {
			// Assign pattern to the first element of the current pattern split by spaces
			String pattern = s.split(" ")[0];
			// Check if the filename matches pattern
			if (s.matches(pattern))
				// Return the second element of the current pattern split by spaces
				return new File(s.split(" ")[1]);
		}
		// Return null because no pattern was found
		return null;
	}
}
