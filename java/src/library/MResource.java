package library;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

public class MResource {
	private static MResource ins;

	private MResource() {
	}

	void test() {
	}

	synchronized static MResource getIns() {
		return (ins == null) ? (ins = new MResource()) : ins;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new RuntimeException("not support clone");
	}

	public final String LINE_SEPARATOR = System.getProperty("line.separator");
	public final String USER_HOME_DIR = System.getProperty("user.home");
	public final String FILE_SEPARATOR = File.separator;
	public final Locale JP = new Locale("ja", "JP");
	public final Locale US = new Locale("en", "US");
	public final Locale VN = new Locale("vi", "VN");

	/**
	 * @param stringMap map integer with string key in resource
	 * @param musicMap  map integer with music key in resource
	 * @param imgMap    map integer with image key in resource
	 * @param pathName  full pathname of R.java file
	 * @param pkgName   package that contains R.java file
	 * @return map that contains ID and key of string value
	 */
	public void gen_r_file(Map<Integer, String> stringMap, Map<Integer, String> musicMap, Map<Integer, String> imgMap,
	                       String pathName, String pkgName) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(pathName));
			bw.write("package " + pkgName + ";\n\n");
			bw.write("public class R{\n");

			// string
			bw.write("\tpublic interface string {\n");
			if (stringMap != null) {
				for (Integer key : stringMap.keySet()) {
					bw.write(String.format("\t\tint %s = %d;\n", stringMap.get(key), key));
				}
			}
			bw.write("\t}\n\n");

			// music
			bw.write("\tpublic interface music {\n");
			if (musicMap != null) {
				for (Integer key : musicMap.keySet()) {
					bw.write(String.format("\t\tint %s = %d;\n", musicMap.get(key), key));
				}
			}
			bw.write("\t}\n\n");

			// img
			bw.write("\tpublic interface img {\n");
			if (imgMap != null) {
				for (Integer key : imgMap.keySet()) {
					bw.write(String.format("\t\tint %s = %d;\n", imgMap.get(key), key));
				}
			}
			bw.write("\t}\n");

			bw.write("}\n");
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param baseName directory from src folder (eg., com.project.res)
	 */
	public ResourceBundle get_bundle(String baseName, Locale locale) {
		ResourceBundle bundle = null;
		try {
			bundle = ResourceBundle.getBundle(baseName, locale);
		} catch (MissingResourceException e) {
			e.printStackTrace();
			JOptionPane.showConfirmDialog(null, "not found " + baseName, "MissingResourceException",
					JOptionPane.CLOSED_OPTION);
		} catch (NullPointerException e) {
			e.printStackTrace();
			JOptionPane.showConfirmDialog(null, "null base name", "NullPointerException", JOptionPane.CLOSED_OPTION);
		}

		return bundle;
	}

	/**
	 * @return full url for value of key in this bundle
	 */
	public URL get_url(String key, ResourceBundle bundle) {
		if (bundle == null)
			throw new RuntimeException("bundle is null");
		String value = bundle.getString(key);
		return getClass().getClassLoader().getResource(value);
	}

	public static void main(String... args) {
		getIns().test();
	}
}
