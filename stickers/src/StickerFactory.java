import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class StickerFactory {

	public void create(InputStream inputStream, String fileName) throws Exception {

		BufferedImage image = ImageIO.read(inputStream);

		int width = image.getWidth();
		int height = image.getHeight();
		int newHeight = height + 200;
		BufferedImage newImage = new BufferedImage(width, newHeight, BufferedImage.TRANSLUCENT);

		Graphics2D graphics = (Graphics2D) newImage.getGraphics();
		graphics.drawImage(image, 0, 0, null);

		var font = new Font(Font.SANS_SERIF, Font.BOLD, 64);
		graphics.setColor(Color.YELLOW);
		graphics.setFont(font);

		graphics.drawString("TOPZERA", 100, newHeight - 100);

		ImageIO.write(newImage, "png", new File("output/" + fileName));

	}

}