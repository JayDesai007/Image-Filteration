import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class Sharpening {
    static int width;
    static int height;
    static public BufferedImage getImage(BufferedImage image){
        Kernel kernel = new Kernel(3, 3,
                new float[]{
                        0, -1,  0,
                        -1,  5, -1,
                        0 ,-1,  0});
        BufferedImageOp op = new ConvolveOp(kernel);
        image = op.filter(image, null);
        return image;
    }
}
