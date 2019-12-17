package downloadsCategorizer.daemon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import downloadsCategorizer.common.ConfigurationManager;
import downloadsCategorizer.common.Utils;

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
			String pattern = Utils.createRegexFromGlob(s.split(" ")[0]);
			// Check if the filename matches pattern
			if (filename.matches(pattern))
				// Return the second element of the current pattern split by spaces
				return new File(ConfigurationManager.DOWNLOADS_FOLDER, s.split(" ")[1]);
		}
		// Return null because no pattern was found
		return null;
	}

	/**
	 * HAHAHA I'm soo funny and original
	 * 
	 * @param patterns
	 */
	public static void easterEgg(List<String> patterns) {
		for (String s : patterns) {
			String pattern = s.split(" ")[0].toLowerCase();
			String folder = s.split(" ")[1];
			if(new File(new File(ConfigurationManager.DOWNLOADS_FOLDER,folder),"easter_egg.jpg").exists())
				continue;
			if (pattern.contains("jpg") || pattern.contains("png") || pattern.contains("gif") || pattern.contains("svg")
					|| folder.toLowerCase().contains("images")) {
				// Do the easter egg
				try {
					URL url = new URL(
							"https://upload.wikimedia.org/wikipedia/commons/thumb/2/21/Danny_DeVito_by_Gage_Skidmore.jpg/1200px-Danny_DeVito_by_Gage_Skidmore.jpg");
					BufferedImage image = ImageIO.read(url);
					ImageIO.write(image, "jpg",
							new File(new File(ConfigurationManager.DOWNLOADS_FOLDER, folder), "easter_egg.jpg"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
