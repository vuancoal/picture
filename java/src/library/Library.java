package library;

public class Library {
	private static Library ins;

	public MAlgorithm alg = MAlgorithm.getIns();
	public MFile file = MFile.getIns();
	public MIO io = MIO.getIns();
	public MDevice dev = MDevice.getIns();
	public MImage img = MImage.getIns();
	public MResource res = MResource.getIns();
	public MObject obj = MObject.getIns();
	public MSystem sys = MSystem.getIns();

	private Library() {
	}

	public synchronized static Library getIns() {
		return (ins == null) ? (ins = new Library()) : ins;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new RuntimeException("not support clone");
	}

	void test() {
	}

	public static void main(String[] args) {
		getIns().test();
	}
}
