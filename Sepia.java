import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Sepia {
    static int width;
    static int height;
    static public BufferedImage getImage(BufferedImage image){
        try {
            width = image.getWidth();
            height = image.getHeight();
            int tr,tg,tb;
            for(int i=0; i<height; i++){
                for(int j=0; j<width; j++){
                    Color c = new Color(image.getRGB(j, i));

                    tr = (int)((c.getRed() * 0.393)+(c.getGreen() * 0.769)+(c.getBlue() *0.189));
                    tg = (int)((c.getRed() * 0.349)+(c.getGreen() * 0.686)+(c.getBlue() *0.168));
                    tb = (int)((c.getRed() * 0.272)+(c.getGreen() * 0.534)+(c.getBlue() *0.131));

                    if(tr>255){ tr=255; }
                    if(tb>255){ tb=255; }
                    if(tg>255){ tg=255; }
                    Color newColor = new Color(tr,tg,tb);
                    image.setRGB(j,i,newColor.getRGB());
                }
            }
        } catch (Exception e) {}
        return image;
    }
}
