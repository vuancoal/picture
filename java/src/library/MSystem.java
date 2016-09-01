package library;

import java.awt.*;
import java.io.IOException;

public class MSystem {
	private static MSystem ins;

	private MSystem() {
	}

	void test() {
	}

	public synchronized static MSystem getIns() {
		return (ins == null) ? (ins = new MSystem()) : ins;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new RuntimeException("not suppot clone");
	}

	/**
	 * execute command of system
	 *
	 * @param command string like「/bin/echo hello」
	 */
	public void exec(String command) {
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * sleep for thread
	 */
	public void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * play beep sound of system
	 */
	public void beep() {
		Toolkit.getDefaultToolkit().beep();
	}

	public static void main(String... args) {
		getIns().test();
	}
}
