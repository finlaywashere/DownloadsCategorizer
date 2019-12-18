package downloadsCategorizer.daemon;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import downloadsCategorizer.common.ConfigurationManager;

public class Daemon {

	/**
	 * Starts the daemon
	 * @param args the program arguments
	 */
	public static void main(String[] args) {
		// Load the configuration
		List<String> patterns = null;
		try {
			patterns = ConfigurationManager.loadConfiguration();
		} catch (Exception e) {
			// Throw a fit
			e.printStackTrace();
			System.err.println("Failed to load configuration!");
			System.err.println("Make sure that you have your .dcconfig in your Downloads folder!");
			System.exit(1);
		}
		// If there are no arguments passed, then do the easter egg (so that anyone actually using this can disable the blessed_image)
		if(args.length == 0)
			DaemonUtils.easterEgg(patterns);
		// Load index file
		Map<String, List<File>> indexes = null;
		try {
			indexes = ConfigurationManager.loadIndexes();
		} catch (Exception e) {
			indexes = new HashMap<String,List<File>>();
		}
		// Loop forever
		while (true) {
			// Get the new files in the Downloads folder
			List<File> newFiles = DaemonUtils.findNewFiles();
			// If there are more than 0 new files
			if (newFiles.size() > 0) {
				// Log how many files have been found
				System.out.println("Found " + newFiles.size() + " new files in "
						+ ConfigurationManager.DOWNLOADS_FOLDER.getPath() + "!");
				// For each file in the new files list
				for (File f : newFiles) {
					// Assign folder to the output of categorizeFile for this file
					File folder = DaemonUtils.categorizeFile(f.getName(), patterns);
					// Log where the file has been categorized in to
					System.out.println("File "+f.getName()+" goes in to "+(folder == null ? "nowhere" : folder.getPath()));
					File newFile = f;
					// If the folder is not null
					if (folder != null) {
						// If the folder doesn't exist, then make it
						if(!folder.exists())
							folder.mkdirs();
						newFile = new File(folder, f.getName());
						// Log that the file is being moved
						System.out.println("Moving file "+f.getName()+" to "+folder.getPath()+"!");
						// Move this file to folder
						try {
							Files.move(f.toPath(), newFile.toPath(), StandardCopyOption.ATOMIC_MOVE);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					// For every number between 0 and the length of the filename as i
					for (int i = 0; i < f.getName().length(); i++) {
						// Add the file to the list in the index map with key of the substring of
						// filename between 0 and i
						String key = f.getName().substring(0, i);
						List<File> files = indexes.get(key);
						if (files == null)
							files = new ArrayList<File>();
						if(files.contains(newFile))
							continue;
						files.add(newFile);
						indexes.put(key, files);
					}
				}
				// Save indexes
				try {
					ConfigurationManager.saveIndexes(indexes);
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("Failed to save indexes!");
				}
			}
			// Sleep for 5 seconds
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
