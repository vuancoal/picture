package algorithm;

import com.sun.org.apache.xpath.internal.operations.Div;
import com.sun.org.apache.xpath.internal.operations.Mult;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static java.lang.System.*;

public class Calculator {
	final int PLUS_CHAR = '+', MINUS_CHAR = '-', MULTY_CHAR = '*', DIV_CHAR = '/';
	final int L_BRACKET_CHAR = '(', R_BRACKET_CHAR = ')';
	final int DOT_CHAR = '.';

	enum Type {PLUS_TYPE, MINUS_TYPE, MULTY_TYPE, DIV_TYPE, L_BRACKET_TYPE, R_BRACKET_TYPE, NUMBER_TYPE}

	int cha;
	BufferedReader reader;
	List<Element> tokens;

	void start() throws Exception {
		out.println("expression: " + toString());
	}

	@Override
	public String toString() {
		String res = "";
		for (Element element : tokens) {
			switch (element.type) {
				case PLUS_TYPE:
				case MINUS_TYPE:
				case MULTY_TYPE:
				case DIV_TYPE:
				case L_BRACKET_TYPE:
				case R_BRACKET_TYPE:
					res += element.name + " ";
					break;
				case NUMBER_TYPE:
					res += element.value + " ";
					break;
			}
		}
		return res;
	}

	void skip() throws Exception {
		if (Character.isWhitespace(cha))
			while (Character.isWhitespace((cha = reader.read()))) ;
	}

	int nextInt() throws Exception {
		skip();
		if (!Character.isDigit(cha)) throw new RuntimeException("c must be a digit");
		int num = cha - '0';
		while ((cha = reader.read()) != -1 && Character.isDigit(cha)) {
			num = 10 * num + cha - '0';
		}
		skip();
		return num;
	}

	void nextChar() throws Exception {
		while ((cha = reader.read()) != -1 && Character.isWhitespace(cha)) ;
	}

	void init() throws Exception {
		nextChar();
		for (; cha != -1; ) {
			if (Character.isWhitespace(cha))
				nextChar();
			switch (cha) {
				case PLUS_CHAR:
					tokens.add(new Element(Type.PLUS_TYPE, (char) PLUS_CHAR));
					nextChar();
					continue;
				case MINUS_CHAR:
					tokens.add(new Element(Type.MINUS_TYPE, (char) MINUS_CHAR));
					nextChar();
					continue;
				case MULTY_CHAR:
					tokens.add(new Element(Type.MULTY_TYPE, (char) MULTY_CHAR));
					nextChar();
					continue;
				case DIV_CHAR:
					tokens.add(new Element(Type.DIV_TYPE, (char) DIV_CHAR));
					nextChar();
					continue;
				case L_BRACKET_CHAR:
					tokens.add(new Element(Type.L_BRACKET_TYPE, (char) L_BRACKET_CHAR));
					nextChar();
					continue;
				case R_BRACKET_CHAR:
					tokens.add(new Element(Type.R_BRACKET_TYPE, (char) R_BRACKET_CHAR));
					nextChar();
					continue;
			}

			int num = nextInt();
			tokens.add(new Element(Type.NUMBER_TYPE, num));
		}
	}

	class Element {
		Type type;
		int value;
		char name;

		Element(Type type, int value) {
			this.type = type;
			this.value = value;
		}

		Element(Type type, char name) {
			this.type = type;
			this.name = name;
		}
	}

	public Calculator() throws Exception {
		String fileName = new File("").getAbsolutePath() + "/src/data/calculator.txt";
		reader = new BufferedReader(new FileReader(fileName));
		tokens = new ArrayList<>();
		init();
		start();
		reader.close();
	}

	public static void main(String... args) {
		try {
			new Calculator();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
