package downloadsCategorizer.common;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

class ConfigurationManagerTest {

	private File folder;
	{
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
	void test() {
		fail("Didn't do this yet");
	}

}
