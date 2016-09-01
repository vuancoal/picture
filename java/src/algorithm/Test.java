package algorithm;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.System.out;

public class Test {
	public Test() {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
		try {
			writer.write("hello");
			writer.flush();
//			writer.close(); // also call flush
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String... args) {
		new Test();
	}
}
