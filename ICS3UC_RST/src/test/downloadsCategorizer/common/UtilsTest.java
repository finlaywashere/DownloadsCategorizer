package downloadsCategorizer.common;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilsTest {

	@Test
	public void testGlobToRegex() {
		String glob = "*.txt";
		String regex = Utils.createRegexFromGlob(glob);
		assertEquals("^.*\\.txt$", regex);
	}

}
