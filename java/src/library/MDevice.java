package library;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.DisplayMode;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.JFrame;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MDevice {
	private static MDevice ins;

	private MDevice() {
	}

	void test() {
	}

	synchronized static MDevice getIns() {
		return (ins == null) ? (ins = new MDevice()) : ins;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new RuntimeException("not support clone");
	}

	public void play_media(URL url) {
		new JFXPanel().setVisible(false);
		Media media = new Media(url.toString());
		MediaPlayer mp = new MediaPlayer(media);
		mp.play();
	}

	public GraphicsDevice[] get_screen_devices() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
	}

	public void set_cursor(JFrame frame, BufferedImage buf_img) {
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(buf_img, new Point(0, 0), "cursor");
		frame.setCursor(cursor);
	}

	public void move_mouse_to(int x, int y) {
		for (GraphicsDevice gd : get_screen_devices()) {
			for (GraphicsConfiguration config : gd.getConfigurations()) {
				Rectangle bound = config.getBounds();
				if (bound.contains(x, y)) {
					try {
						int cx = x - bound.x, cy = y - bound.y;
						Robot robot = new Robot(gd);
						robot.mouseMove(cx, cy);
					} catch (AWTException e) {
						e.printStackTrace();
					}
					return;
				}
			}
		}
	}

	public GraphicsDevice get_default_screen_device() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		return ge.getDefaultScreenDevice();
	}

	public void set_full_screen(DisplayMode dm, JFrame frame) {
		GraphicsDevice gd = get_default_screen_device();
		gd.setFullScreenWindow(frame);
		if (dm != null && gd.isDisplayChangeSupported()) {
			gd.setDisplayMode(dm);
		}
	}

	public static void main(String... args) {
		getIns().test();
	}
}
