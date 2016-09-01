package algorithm;

import com.sun.javafx.sg.prism.NGWebView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.*;

public class ParseXML {
	public final int SPACE = ' ', EQUAL = '=', SLASH = '/', EOF = -1;
	public final int LEFT_BRACKET = '<';
	public final int RIGHT_BRACKET = '>';

	private BufferedReader reader;
	private Node root;

	class Node {
		private String tagName;
		private List<Node> children;
		private Map<String, String> attrs;

		public Node(String tagName) {
			this.tagName = tagName;
			children = new ArrayList<>();
			attrs = new HashMap<>();
		}

		public String getTagName() {
			return tagName;
		}

		public void setTagName(String tagName) {
			this.tagName = tagName;
		}

		public List<Node> getChildren() {
			return children;
		}

		public Map<String, String> getAttrs() {
			return attrs;
		}

		public void addNode(Node node) {
			children.add(node);
		}

		public void addAttribute(String key, String value) {
			attrs.put(key, value);
		}
	}

	void start() throws Exception {
	}

	String nextWord() throws Exception {
		int c;
		StringBuffer sb = new StringBuffer();
		while ((c = reader.read()) != EOF && c != SPACE && c != EQUAL && c != RIGHT_BRACKET) {
			if (c != LEFT_BRACKET)
				sb.append((char) c);
		}
		return sb.toString();
	}

	public ParseXML() throws Exception {
		String fileName = new File("").getAbsolutePath() + "/src/data/parse.xml";
		reader = new BufferedReader(new FileReader(fileName));
		root = new Node("root");
		start();
		reader.close();
	}

	public static void main(String... args) {
		try {
			new ParseXML();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
