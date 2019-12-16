package downloadsCategorizer.common;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConfigurationManager {
	public static final File DOWNLOADS_FOLDER = new File(System.getProperty("user.home"),"Downloads/");
	private static final File CONFIGURATION_FILE = new File(DOWNLOADS_FOLDER,".dcconfig");
	private static final File INDEX_FILE = new File(DOWNLOADS_FOLDER,".dcindex");
	
	public static List<String> loadConfiguration() throws Exception{
		List<String> patterns = new ArrayList<String>();
		Scanner in = new Scanner(CONFIGURATION_FILE);
		while(in.hasNextLine()) {
			String s = in.nextLine();
			if(s.trim().isEmpty())
				continue;
			patterns.add(s);
		}
		in.close();
		return patterns;
	}
	public static Map<String,List<File>> loadIndexes() throws Exception{
		Map<String,List<File>> returnValue = new HashMap<String,List<File>>();
		List<File> currentList = null;
		String pattern = null;
		Scanner in = new Scanner(INDEX_FILE);
		while(in.hasNextLine()) {
			String line = in.nextLine();
		   /*If line is blank
		      Go to next iteration
		   If currentList is null
		      Assign pattern to the current line
		      Assign currentList to a new list of Files
		   Else if this line starts with a space
		      Add a new file with the path of the current line in the index file to the currentList
		   Else 
		      Put currentList into the returnValue map with a key of pattern
		      Assign null to pattern
		      Assign null to currentList
		*/
		}
		in.close();
		return returnValue;

	}
}
