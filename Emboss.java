import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Emboss {
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
                    Color c1 = new Color(tempImage.getRGB(j,i));
                    Color c2 = new Color(tempImage.getRGB(j+1,i+1));
                    r = Math.min(Math.abs(c1.getRed()-c2.getRed()+128),255);
                    g = Math.min(Math.abs(c1.getGreen()-c2.getGreen()+128),255);
                    b = Math.min(Math.abs(c1.getBlue()-c2.getBlue()+128),255);
                    Color newColor = new Color(r,g,b);
                    image.setRGB(j,i,newColor.getRGB());
                }
            }
        } catch (Exception e) {}
        return image;
    }
}
