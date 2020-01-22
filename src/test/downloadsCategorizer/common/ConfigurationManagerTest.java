package downloadsCategorizer.common;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class ConfigurationManagerTest {

	private File folder;
	public ConfigurationManagerTest(){
		try {
			folder = File.createTempFile("tmp","tmp").getParentFile();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		ConfigurationManager.DOWNLOADS_FOLDER = folder;
		ConfigurationManager.init();
	}
	@Test
	public void testLoadConfiguration() {
		PrintWriter out = null;
		try {
			ConfigurationManager.CONFIGURATION_FILE.delete();
			ConfigurationManager.CONFIGURATION_FILE.createNewFile();
			out = new PrintWriter(new FileWriter(ConfigurationManager.CONFIGURATION_FILE,true));
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
		out.println("*.txt Text");
		out.println("*.jpg Images");
		out.close();
		List<String> config = null;
		try {
			config = ConfigurationManager.loadConfiguration();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		if(!config.get(0).equals("*.txt Text") || !config.get(1).equals("*.jpg Images"))
			fail();
	}
	@Test
	public void testLoadIndexes() {
		PrintWriter out = null;
		try {
			ConfigurationManager.INDEX_FILE.delete();
			ConfigurationManager.INDEX_FILE.createNewFile();
			out = new PrintWriter(new FileWriter(ConfigurationManager.INDEX_FILE,true));
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
		out.println("test");
		out.println(" test2");
		out.println("END");
		out.close();
		Map<String,List<File>> indexes = null;
		try {
			indexes = ConfigurationManager.loadIndexes();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		if(!indexes.get("test").get(0).getName().equals("test2"))
			fail();
	}
	@Test
	public void testSaveIndexes() {
		Map<String,List<File>> indexes = new HashMap<String,List<File>>();
		List<File> list = new ArrayList<File>();
		list.add(new File("test.jpg"));
		indexes.put("test", list);
		try {
			ConfigurationManager.saveIndexes(indexes);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		Map<String, List<File>> newIndexes = null;
		try {
			newIndexes = ConfigurationManager.loadIndexes();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		if(!newIndexes.get("test").get(0).getName().equals("test.jpg"))
			fail();
	}
}
