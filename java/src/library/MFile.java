package library;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import utils.MGson;

public class MFile {
	private static MFile ins;

	private MFile() {
	}

	void test() {
	}

	synchronized static MFile getIns() {
		return (ins == null) ? (ins = new MFile()) : ins;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new RuntimeException("not support clone");
	}

	public static final int BUF_SIZE = 1 << 13;

	/**
	 * find extension of the filename
	 */
	public String get_extension(String file_name) {
		if (file_name == null)
			return null;
		int ind = file_name.lastIndexOf(".");
		return file_name.substring(ind + 1, file_name.length());
	}

	/**
	 * get file name of the file
	 *
	 * @param url url of file
	 * @return filename of the file
	 */
	public String get_filename(URL url) {
		if (url == null)
			return null;
		return new File(url.getFile()).getName();
	}

	/**
	 * read data from file via InputStream class
	 *
	 * @param file read file
	 * @return byte array of data in read file
	 */
	public byte[] read_file(File file) {
		int length = (int) file.length(), offset = 0, numRead = 0;
		byte[] bytes = new byte[(int) file.length()];
		try {
			if (length > Integer.MAX_VALUE)
				throw new RuntimeException("file too large");
			InputStream is = new FileInputStream(file);
			while (offset < length && (numRead = is.read(bytes, offset, length)) >= 0) {
				offset += numRead;
			}
			is.close();
			if (offset < length) {
				throw new RuntimeException("can't read file completely");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytes;
	}

	/**
	 * read file and return gson object
	 *
	 * @param file     the file that store object to read
	 * @param classOfT the class type of object stored in the file
	 * @return object to read
	 * @throws IOException
	 */
	public <T> T read_gson_obj_from_file(File file, Class<T> classOfT) throws IOException {
		T obj = null;
		BufferedReader br;
		br = new BufferedReader(new FileReader(file));
		obj = MGson.getIns().gson.fromJson(br, classOfT);
		if (obj == null)
			throw new RuntimeException("can not retrieve object from " + file.getPath());
		System.out.println("read successfully object from " + file.getPath());
		br.close();
		return obj;
	}

	/**
	 * @param file the destination file that store object
	 * @param obj  the object to read that stored in the file
	 * @return the object to read
	 * @throws IOException
	 * @throws throw       MRuntimeException when can not retrieve object from the file
	 */
	public void write_obj_to_disk(File file, Object obj) {
		if (obj == null)
			throw new RuntimeException("obj null");
		// Type type = new TypeToken<BoardModel>() {}.getType();
		String data = MGson.getIns().gson.toJson(obj);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(data);
			System.out.println("mlib: wrote successfully object to file " + file.getPath());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write_to_disk(File file, String data) {
		if (data == null)
			throw new RuntimeException("data null");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(data);
			System.out.println("mlib: wrote successfully data to file " + file.getPath());
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * download file from internet and store it into file_name
	 *
	 * @param file_name     name of the store file
	 * @param download_link link to download file
	 */
	public void download(String file_name, String download_link) {
		File file = new File(file_name);
		try {
			URL url = new URL(download_link);
			BufferedInputStream reader = new BufferedInputStream(url.openStream());
			FileOutputStream writer = new FileOutputStream(file);

			byte[] buf = new byte[BUF_SIZE];
			int num;
			while ((num = reader.read(buf, 0, BUF_SIZE)) != -1) {
				writer.write(buf, 0, num);
			}
			reader.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int size_of_download_file(String download_link) throws IOException {
		URL url = new URL(download_link);
		return url.openConnection().getContentLength();
	}

	public static void main(String... args) {
		getIns().test();
	}
}
