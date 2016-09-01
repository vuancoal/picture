package library;

public class MIO {
	private static MIO ins;

	private MIO() {
	}

	void test() {
	}

	synchronized static MIO getIns() {
		return (ins == null) ? (ins = new MIO()) : ins;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new RuntimeException("not support clone");
	}

	public void print(String format, Object... args) {
		System.out.print(String.format(format, args));
	}

	public void print(Object obj) {
		System.out.print(obj);
	}

	public void println(String format, Object... args) {
		System.out.println(String.format(format, args));
	}

	public void println(Object obj) {
		System.out.println(obj);
	}

	public void debug(String format, Object... args) {
		System.out.print(String.format(format, args));
	}

	public void debugln(String format, Object... args) {
		System.out.println(String.format(format, args));
	}

	public void err(String format, Object... args) {
		System.err.print(String.format(format, args));
	}

	public void errln(String format, Object... args) {
		System.err.println(String.format(format, args));
	}

	public static void main(String... args) {
		getIns().test();
	}
}
