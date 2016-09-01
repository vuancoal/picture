package algorithm;

import static java.lang.System.out;

import java.util.Arrays;

public class RegularExpression {
	private void start() {
		String pattern = "\\banh\\b", string = "anhyeuem";
		out.println(Arrays.toString(string.split(pattern)));
		// for (String elem : string.split(pattern))
		// out.println(elem);
		// Pattern ptn = Pattern.compile(pattern);
		// Matcher matcher = ptn.matcher(string);
		// out.println(matcher.find());
	}

	public static void main(String... args) {
		new RegularExpression().start();
	}

	public RegularExpression() {
	}
}
