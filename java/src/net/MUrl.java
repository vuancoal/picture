package net;

import java.io.*;
import java.net.URL;
import java.util.Collection;

public class MUrl implements Config {
	public MUrl() {
		try {
			URL url = new URL(String.format("http://%s/index.html", HOST1));
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

			String fileName = new File("").getAbsolutePath() + "/src/data/index.html";
			File file = new File(fileName);
			if (!file.exists()) file.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

			String line;
			while ((line = reader.readLine()) != null) {
				writer.write(line);
			}

			reader.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String... args) {
		new MUrl();
	}
}
