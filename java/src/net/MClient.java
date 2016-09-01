package net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static java.lang.System.out;

public class MClient implements Config {
	private Socket socket;
	BufferedReader reader;
	PrintWriter printer;
	BufferedReader stdInput;

	public MClient() {
		try {
			socket = new Socket(HOST1, PORT);

			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			/** If you use writer.write(String), you must flush data by using writer.flush() method */
//			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			printer = new PrintWriter(socket.getOutputStream(), true);
			stdInput = new BufferedReader(new InputStreamReader(System.in));

			// request server
//			printer.println(REQUEST_SENTENCE);

			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println("Server: " + line);

				System.out.print("Ask some question: ");
				printer.println(stdInput.readLine());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.println("Try closing all stream");
				stdInput.close();
				reader.close();
				printer.close();
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String... args) {
		new MClient();
	}
}
