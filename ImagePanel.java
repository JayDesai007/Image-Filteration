import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImagePanel extends JPanel {
    private JLabel imageLabel;
    private ImageIcon transformedImageIcon;
    public ImagePanel(){
        this.imageLabel = new JLabel();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(Constants.IMAGE_LABEL_BORDER,Constants.IMAGE_LABEL_BORDER+100,Constants.IMAGE_LABEL_BORDER,Constants.IMAGE_LABEL_BORDER));
        add(imageLabel, BorderLayout.CENTER);
    }
    public void updateImage(final Image image){
        imageLabel.setIcon(new ImageIcon(scaleImage(image)));
    }
    private Image scaleImage(Image image){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return image.getScaledInstance(screenSize.width-215,screenSize.height-50,Image.SCALE_AREA_AVERAGING);
    }
    public void loadImage(File file){
        this.transformedImageIcon = new ImageIcon(file.getAbsolutePath());
        Image image = transformedImageIcon.getImage();
        updateImage(image);
    }
    public BufferedImage loadImage(BufferedImage image){
        updateImage(image);
        return image;
    }
}
