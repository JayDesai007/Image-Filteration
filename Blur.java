import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

public class Blur {
    static int width;
    static int height;
    static public BufferedImage getImage(BufferedImage image){
        try {
            width = image.getWidth();
            height = image.getHeight();
            int r,g,b;
            File file = new File("temp" + ".jpg");
            try {
                ImageIO.write(image, "jpg", file);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            BufferedImage tempImage = ImageIO.read(new File("temp.jpg"));
            file.delete();
            int number = (height*width)/100000;
            int n = 0;
            while(((n+1)*(n+1))<=number){ n++; }
            System.out.print(n);
            Color c;
            for(int i=0; i<height-n; i++){
                for(int j=0; j<width-n; j++){
                    r=g=b=0;
                    for(int ii=0;ii<n;ii++){
                        for(int jj=0;jj<n;jj++) {
                            c = new Color(tempImage.getRGB(j+jj,i+ii));
                            r += c.getRed();
                            g += c.getGreen();
                            b += c.getBlue();
                        }
                    }
                    Color newColor = new Color(r/(n*n),g/(n*n),b/(n*n));
                    image.setRGB(j,i,newColor.getRGB());
                }
            }
        } catch (Exception e) {}
        return image;
    }
}
