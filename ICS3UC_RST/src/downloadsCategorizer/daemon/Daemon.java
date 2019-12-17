package downloadsCategorizer.daemon;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import downloadsCategorizer.common.ConfigurationManager;

public class Daemon {

	public static void main(String[] args) {
		// Load the configuration
		List<String> patterns = null;
		try {
			patterns = ConfigurationManager.loadConfiguration();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Failed to load configuration!");
			System.exit(1);
		}
		// Load index file
		Map<String, List<File>> indexes = null;
		try {
			indexes = ConfigurationManager.loadIndexes();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Failed to load indexes!");
			System.exit(1);
		}
		// Loop forever
		while (true) {
			// Get the new files in the Downloads folder
			List<File> newFiles = DaemonUtils.findNewFiles();
			// If there are more than 0 new files
			if (newFiles.size() > 0) {
				System.out.println("Found " + newFiles.size() + " new files in "
						+ ConfigurationManager.DOWNLOADS_FOLDER.getPath() + "!");
				// For each file in the new files list
				for (File f : newFiles) {
					// Assign folder to the output of categorizeFile for this file
					File folder = DaemonUtils.categorizeFile(f.getName(), patterns);
					System.out.println("File "+f.getName()+" goes in to "+(folder == null ? "nowhere" : folder.getPath()));
					// If the folder is not null
					if (folder != null) {
						File newFile = new File(folder, f.getName());
						System.out.println("Moving file "+f.getName()+" to "+folder.getPath()+"!");
						// Move this file to folder
						try {
							Files.move(f.toPath(), newFile.toPath(), StandardCopyOption.ATOMIC_MOVE);
							// For every number between 0 and the length of the filename as i
							for (int i = 0; i < f.getName().length(); i++) {
								// Add the file to the list in the index map with key of the substring of
								// filename between 0 and i
								String key = f.getName().substring(0, i);
								List<File> files = indexes.get(key);
								if (files == null)
									files = new ArrayList<File>();
								files.add(newFile);
								indexes.put(key, files);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
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
