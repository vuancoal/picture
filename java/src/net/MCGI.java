package net;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import static java.lang.System.*;

public class MCGI {
	public MCGI(String first, String last) {
		try {
			String first_name = URLEncoder.encode(first, "UTF-8");
			String last_name = URLEncoder.encode(last, "UTF-8");
			URL url = new URL("http://www.tutorialspoint.com/cgi-bin/hello_get.cgi");
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);

			PrintWriter printer = new PrintWriter(connection.getOutputStream());
			String form = String.format("first_name=%s&last_name=%s", first_name, last_name);
			printer.println(form);

			String line, fileName;
			fileName = new File("").getAbsolutePath() + "/data/hello.html";
			File file = new File(fileName);
			file.createNewFile();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			while ((line = reader.readLine()) != null) {
				out.println(line);
				writer.write(line);
			}

			reader.close();
			writer.close();
			printer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new MCGI(args[0], args[1]);
	}
}
