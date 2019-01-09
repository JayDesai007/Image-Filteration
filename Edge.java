import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class Edge {
    static int width;
    static int height;
    static public BufferedImage getImage(BufferedImage image){
        Kernel kernel = new Kernel(3, 3,
                new float[]{-1,-1,-1,-1,8,-1,-1,-1,-1});
        BufferedImageOp op = new ConvolveOp(kernel);
        image = op.filter(image, null);
        return image;
    }
}
