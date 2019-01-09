import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AdjustColor {
    private static int width;
    private static int height;
    private static int r,g,b;
    private static BufferedImage image,tempImage,tImage;
    private static JSlider sliderR,sliderG,sliderB;
    public static JFrame f;
    public static int numberOfFilter = 0;
    public static void setImage(BufferedImage imag){
        image = imag;
    }
    public static void createControls(Main main,JFrame jFrame){
        class ColorListener implements ChangeListener {
            private int flag;
            public ColorListener(int flag){
                this.flag = flag;
            }
            @Override
            public void stateChanged(ChangeEvent e) {
                mixColors(flag,main,jFrame);
                setImage(image);
            }
        }
        f = new JFrame();
        File file = new File("temp" + ".jpg");
        try {
            ImageIO.write(image, "jpg", file);
            tImage = ImageIO.read(new File("temp.jpg"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        file.delete();
        try {
            for(int i=0; i<height; i++){
                for(int j=0; j<width; j++){
                    Color c = new Color(tImage.getRGB(j, i));
                    r+=c.getRed();
                    g+=c.getGreen();
                    b+=c.getBlue();
                }
            }
        } catch (Exception e) {}
        r=r/(width*height);
        g=g/(width*height);
        b=b/(width*height);
        sliderR = new JSlider(0,255,r);
        sliderG = new JSlider(0,255,g);
        sliderB = new JSlider(0,255,b);
        JPanel p = new JPanel();
        p.setBackground(Color.DARK_GRAY);
        JPanel redIcon = new JPanel() {
            public void paintComponent(Graphics g) {
                Image red = new ImageIcon("Red.png").getImage();
                g.drawImage(red,0,0,10, 10, this);
            }
        };
        JPanel greenIcon = new JPanel() {
            public void paintComponent(Graphics g) {
                Image green = new ImageIcon("Green.png").getImage();
                g.drawImage(green,0,0,10, 10, this);
            }
        };
        JPanel blueIcon = new JPanel() {
            public void paintComponent(Graphics g) {
                Image blue = new ImageIcon("Blue.png").getImage();
                g.drawImage(blue,0,0,10, 10, this);
            }
        };

        p.add(redIcon);
        p.add(sliderR);
        p.add(greenIcon);
        p.add(sliderG);
        p.add(blueIcon);
        p.add(sliderB);
        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");
        okButton.repaint(0,0,20,20);
        p.add(okButton);
        cancelButton.repaint(0,0,50,20);
        p.add(cancelButton);
        okButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                f.dispose();
                File file = new File(String.valueOf(numberOfFilter) + ".jpg");
                try {
                    ImageIO.write(image, "jpg", file);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }

            }
        });
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File file = new File(String.valueOf(--numberOfFilter) + ".jpg");
                try {
                    tempImage = ImageIO.read(file);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                image = main.loadImage(tempImage,jFrame);
                f.dispose();
            }
        });
        sliderR.addChangeListener(new ColorListener(0));
        sliderG.addChangeListener(new ColorListener(1));
        sliderB.addChangeListener(new ColorListener(2));
        f.add(p);
        f.setSize(240, 155);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setVisible(true);
        f.setResizable(false);
    }
    public static boolean isClose(){
        if(f.isVisible()){
            return true;
        }
        return false;
    }
    public static void mixColors(int flag,Main main,JFrame jFrame){
        int red = sliderR.getValue();
        int green = sliderG.getValue();
        int blue = sliderB.getValue();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color c = new Color(tImage.getRGB(j,i));
                int tempRed = red - r + c.getRed();
                int tempGreen = green - g + c.getGreen();
                int tempBlue = blue - b + c.getBlue();
                if ((tempRed >= 0) && (tempRed <= 255) && (tempGreen >= 0) && (tempGreen <= 255) && (tempBlue >= 0) && (tempBlue <= 255)) {
                    Color newColor = new Color(tempRed, tempGreen, tempBlue);
                    image.setRGB(j, i, newColor.getRGB());
                }
            }
        }
        image = main.loadImage(image,jFrame);
    }
    public static void processImage(BufferedImage imag,Main main,JFrame jFrame,int numberOffilter) {
        numberOfFilter = numberOffilter;
        image = imag;
        tempImage = imag;
        width = image.getWidth();
        height = image.getHeight();
        r=0;g=0;b=0;
        createControls(main,jFrame);
    }
    public static BufferedImage getImage(){
        return image;
    }
}
