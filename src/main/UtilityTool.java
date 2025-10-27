package main;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
public class UtilityTool {
    public static BufferedImage scaleImage(BufferedImage originalImage, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return scaledImage;
    }
}
