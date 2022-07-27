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
		int newHeight = height + 50;
		BufferedImage newImage = new BufferedImage(width, newHeight, BufferedImage.TRANSLUCENT);

		Graphics2D graphics = (Graphics2D) newImage.getGraphics();
		graphics.drawImage(image, 0, 0, null);

		var font = new Font(Font.SERIF, Font.BOLD, 14);
		graphics.setColor(Color.YELLOW);
		graphics.setFont(font);

		graphics.drawString("Chique Show", 5, newHeight - 25);

		File outputFile = new File("./stickers/output/" + fileName + ".png");
		outputFile.mkdirs();
		ImageIO.write(newImage, "png", outputFile);

	}

}