import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Flip2 {
    static int width;
    static int height;
    static public BufferedImage getImage(BufferedImage image){
        try {
            width = image.getWidth();
            height = image.getHeight();
            int r=0,g=0,b=0;
            File file = new File("temp" + ".jpg");
            try {
                ImageIO.write(image, "jpg", file);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            BufferedImage tempImage = ImageIO.read(new File("temp.jpg"));
            for(int i=0; i<height; i++){
                for(int j=0; j<width; j++){
                    Color c = new Color(tempImage.getRGB(j,height-1-i));
                    r = c.getRed();
                    g = c.getGreen();
                    b = c.getBlue();
                    Color newColor = new Color(r,g,b);
                    image.setRGB(j,i,newColor.getRGB());
                }
            }
        } catch (Exception e) {}
        return image;
    }
}
