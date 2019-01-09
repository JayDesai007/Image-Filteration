import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Solarize {
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
            for(int i=1; i<height-1; i++){
                for(int j=1; j<width-1; j++){
                    Color c = new Color(tempImage.getRGB(j,i));
                    if(c.getRed()<128)
                        r=225-c.getRed();
                    if(c.getGreen()<128)
                        g=225-c.getGreen();
                    if(c.getBlue()<128)
                        b=225-c.getBlue();
                    Color newColor = new Color(r,g,b);
                    image.setRGB(j,i,newColor.getRGB());
                }
            }
        } catch (Exception e) {}
        return image;
    }
}
