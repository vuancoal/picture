package library;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class MImage {
	private static MImage ins;

	private MImage() {
	}

	void test() {
		BufferedImage image = new BufferedImage(81, 81, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		g.fillRect(0, 0, 80, 80);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, 80, 80);
		g.drawLine(0, 0, 80, 80);
		g.drawLine(0, 80, 80, 0);

		String fileName = new File("").getAbsolutePath();
		File file = new File(fileName + "/src/data/rectangle.png");
		try {
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int convert_to_rgb(int r, int g, int b) {
		return (0xff000000) | (r << 16) | (g << 8) | (r);
	}

	public int convert_to_argb(int a, int r, int g, int b) {
		return (a << 24) | (r << 16) | (g << 8) | b;
	}

	/**
	 * alpha = 0 (transparent), alpha = 255 (opaque)
	 */
	public int get_alpha(int rgb) {
		return (rgb >> 24) & 0xff;
	}

	public int get_alpha(Color color) {
		if (color == null)
			throw new RuntimeException("color == null !");
		return color.getAlpha();
	}

	public int get_red(int rgb) {
		return (rgb >> 16) & 0xff;
	}

	public int get_red(Color color) {
		if (color == null)
			throw new RuntimeException("color == null !");
		return color.getRed();
	}

	public int get_green(int rgb) {
		return (rgb >> 8) & 0xff;
	}

	public int get_green(Color color) {
		if (color == null)
			throw new RuntimeException("color == null !");
		return color.getGreen();
	}

	public int get_blue(int rgb) {
		return rgb & 0xff;
	}

	public int get_blue(Color color) {
		if (color == null)
			throw new RuntimeException("color == null !");

		return color.getBlue();
	}

	public BufferedImage read_image(URL url) {
		// URL url = new URL("file:////Users/UserName/folder/img.gif");
		BufferedImage buf_img = null;
		try {
			buf_img = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buf_img;
	}

	public BufferedImage convert_image_to_buffered_image(Image source) {
		BufferedImage target = new BufferedImage(source.getWidth(null), source.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D gg = target.createGraphics();
		gg.drawImage(source, 0, 0, null);
		gg.dispose();
		return target;
	}

	public Image remove_background(final BufferedImage buf_img, final Color color) {
		if (buf_img == null)
			throw new RuntimeException("buf_img == null !");
		final ImageFilter filter = new RGBImageFilter() {
			final int opaque_bkg_color = color.getRGB() | 0xff000000;

			@Override
			public final int filterRGB(final int x, final int y, final int rgb) {
				if ((rgb | 0xff000000) == opaque_bkg_color) {
					return 0x00ffffff & rgb;
				} else {
					return rgb;
				}
			}
		};

		final ImageProducer ip = new FilteredImageSource(buf_img.getSource(), filter);
		return Toolkit.getDefaultToolkit().createImage(ip);
	}

	public Image read_image_via_toolkit(URL url) {
		// URL url = new URL("file:////Users/UserName/folder/img.gif");
		return Toolkit.getDefaultToolkit().createImage(url);
	}

	public Image read_image_via_imageicon(URL url) {
		// URL url = new URL("file:////Users/UserName/folder/img.gif");
		return new ImageIcon(url).getImage();
	}

	/**
	 * @param img       buffered image
	 * @param imgFormat as png, jpg, jpeg, gif...
	 * @param file      output image file
	 */
	public void write_image(BufferedImage img, String imgFormat, File file) {
		try {
			ImageIO.write(img, imgFormat, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String... args) {
		getIns().test();
	}

	public synchronized static MImage getIns() {
		return (ins == null) ? (ins = new MImage()) : ins;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new RuntimeException("not support clone");
	}
}
