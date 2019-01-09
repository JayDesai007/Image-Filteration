import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class Bright{
    static public BufferedImage getImage(BufferedImage image) {
        RescaleOp rescale = new RescaleOp(1.5f, 0f, null);
        image = rescale.filter(image, null);
        return image;
    }
}