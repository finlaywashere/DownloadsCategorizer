package downloadsCategorizer.daemon;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class DaemonUtilsTest {

	@Test
	public void testCategorize() {
		List<String> patterns = new ArrayList<String>();
		patterns.add("*.txt Text");
		patterns.add("*.jpg Images");
		File folder = DaemonUtils.categorizeFile("test.jpg", patterns);
		if(!folder.getName().equals("Images"))
			fail();
	}

}
