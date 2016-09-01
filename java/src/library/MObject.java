package library;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import utils.MGson;

public class MObject {
	private static MObject ins;

	private MObject() {
	}

	void test() {
	}

	synchronized static MObject getIns() {
		return (ins == null) ? (ins = new MObject()) : ins;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new RuntimeException("not support clone");
	}

	/**
	 * @param obj the object that implemented Serializable. Notice that, all
	 *            reference type object inside this object must also implements
	 *            Serializable interface
	 * @return new independent object that created from this object
	 */
	public Object clone(Object obj) {
		Object newObject = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.close();

			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
			newObject = ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newObject;
	}

	/**
	 * convert obj to json string
	 */
	public String convert_to_json_string(Object obj) {
		if (obj == null)
			throw new RuntimeException("obj must be not null");
		return MGson.getIns().gson.toJson(obj);
	}

	/**
	 * convert json string to obj
	 */
	public <T> Object convert_to_obj(String json, Class<T> classOfT) {
		return MGson.getIns().gson.fromJson(json, classOfT);
	}

	public static void main(String... args) {
		getIns().test();
	}
}
