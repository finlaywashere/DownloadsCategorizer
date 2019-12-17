package downloadsCategorizer.common;

public class Utils {
	/**
	 * This code is from
	 * https://stackoverflow.com/questions/1247772/is-there-an-equivalent-of-java-util-regex-for-glob-type-patterns
	 * It does magic and converts glob patterns to the mysterious regexes
	 */
	public static String createRegexFromGlob(String glob) {
		String out = "^";
		for (int i = 0; i < glob.length(); ++i) {
			final char c = glob.charAt(i);
			switch (c) {
			case '*':
				out += ".*";
				break;
			case '?':
				out += '.';
				break;
			case '.':
				out += "\\.";
				break;
			case '\\':
				out += "\\\\";
				break;
			default:
				out += c;
			}
		}
		out += '$';
		return out;
	}
}
