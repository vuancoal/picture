package net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.out;

public class MServer implements Config {
	private ServerSocket serverSocket;

	public MServer() {
		try {
			serverSocket = new ServerSocket(PORT);
			serverSocket.setReuseAddress(true);
			for (int i = 0; i < MAX_NUMBER_OF_CLIENT; ++i) {
				System.out.println("Waiting for connection...");
				Socket socket = serverSocket.accept();
				Thread thread = new MThread(socket);
				thread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class MThread extends Thread {
		private Socket socket;
		BufferedReader reader;
		PrintWriter printer;

		public MThread(Socket socket) {
			this.socket = socket;
			System.out.println(String.format("New client at %s, %d has connected", socket.getInetAddress(), socket.getPort()));
		}

		@Override
		public void run() {
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				/** If you use writer.write(String), you must flush data by using writer.flush() method */
//				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				printer = new PrintWriter(socket.getOutputStream(), true);

				printer.println("Welcome to the Microsoft Activation");
				String line;
				while ((line = reader.readLine()) != null) {
					if (line.equals("bye")) break;
					System.out.println("Client: " + line);
					response(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					out.println("Try closing all stream");
					reader.close();
					printer.close();
					socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		void response(String line) throws Exception {
			String[] strings = line.split(" ");

			Class[] param = new Class[2];
			param[0] = Double.TYPE;
			param[1] = Double.TYPE;
			Class<?> cls = Class.forName(MServer.this.getClass().getPackage().getName() + ".MServer");

			String methodName = strings[0];
			double value = Double.parseDouble(strings[1]);
			double epsilon = Double.parseDouble(strings[2]);
			Method method = cls.getDeclaredMethod(methodName, param);
			Double res = (Double) method.invoke(cls.newInstance(), value, epsilon);
			printer.println(String.format("value of %s(%.2f) with the accuracy %f is %.2f", methodName, value, epsilon, res));
		}
	}

	double square(double val, final double epsilon) {
		if (val == 0.0) return val;
		if (val < 0.0) throw new RuntimeException("must be specify non-negative value");
		double x0, x1 = val / 2;
		do {
			x0 = x1;
			x1 = (x0 + val / x0) / 2;
		} while (Math.abs(x0 - x1) > epsilon);

		return x1;
	}

	public static void main(String... args) {
		new MServer();
	}
}
