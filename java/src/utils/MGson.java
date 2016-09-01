package utils;

import java.io.IOException;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class MGson {
	private static MGson ins;
	public Gson gson;

	private MGson() {
		Exclude ex = new Exclude();
		gson = new GsonBuilder()
				// add deserialization
				.addDeserializationExclusionStrategy(ex)
				// add serialization
				.addSerializationExclusionStrategy(ex)
				// create gson from builder
				.create();
	}

	public synchronized static MGson getIns() {
		return (ins == null) ? (ins = new MGson()) : ins;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new RuntimeException("not support clone");
	}

	class Exclude implements ExclusionStrategy {
		@Override
		public boolean shouldSkipClass(Class<?> arg0) {
			return false;
		}

		@Override
		public boolean shouldSkipField(FieldAttributes field) {
			SerializedName ns = field.getAnnotation(SerializedName.class);
			if (ns != null)
				return false;
			return true;
		}
	}

	class MyTypeAdapter<T> extends TypeAdapter<T> {
		@Override
		public T read(JsonReader reader) throws IOException {
			return null;
		}

		@Override
		public void write(JsonWriter writer, T obj) throws IOException {
			if (obj == null) {
				writer.nullValue();
				return;
			}
			writer.value(obj.toString());
		}
	}
}
