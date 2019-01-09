import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main extends JPanel {
    public JFileChooser fileChooser;
    public int numberOfFilter = 0;
    public int numberFilter = 0;
    public File file;
    public BufferedImage image = null;
    public Main() {
        this.fileChooser = new  JFileChooser(new File("F:\\"));
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        int w = getWidth();
        int h = getHeight();
        int iw = this.image.getWidth();
        int ih = this.image.getHeight();
        double xScale = (double)w/iw;
        double yScale = (double)h/ih;
        double scale = Math.min(xScale, yScale);
        int width = (int)(scale*iw);
        int height = (int)(scale*ih);
        int x = (w - width)/2;
        int y = (h - height)/2;
        g2.drawImage(this.image, x, y, width, height, this);
    }

    public void mainFrame() throws IOException {
        Main main = new Main();
        JFrame jFrame = new JFrame();
        jFrame.setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        jFrame.setLocationRelativeTo(null);
        jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadMenuItem = new JMenuItem("Load image");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(loadMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(exitMenuItem);

        loadMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if(main.fileChooser.showOpenDialog(main) == JFileChooser.APPROVE_OPTION){
                    main.file = main.fileChooser.getSelectedFile();
                    try {
                        main.loadImage(main.file,jFrame);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        main.numberOfFilter++;
                        main.numberFilter++;
                        main.image = ImageIO.read(new File(main.file.getAbsolutePath()));
                        File file = new File(String.valueOf(main.numberOfFilter)+".jpg");
                        ImageIO.write(main.image, "jpg", file);
                    }
                    catch (Exception exception){}
                }
            }
        });

        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    if(main.numberOfFilter==0)
                        throw new Exception();
                    JFrame parentFrame = new JFrame();

                    JFileChooser fileChooser = new JFileChooser(new File("F:\\"));
                    FileNameExtensionFilter bmpFilter = new FileNameExtensionFilter("BMP file", "bmp");
                    FileNameExtensionFilter jpegFilter = new FileNameExtensionFilter("JPEG file", "jpeg");
                    FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter("JPG file", "jpg");
                    FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("PNG file", "png");

                    fileChooser.setFileFilter(bmpFilter);
                    fileChooser.setFileFilter(jpegFilter);
                    fileChooser.setFileFilter(jpgFilter);
                    fileChooser.setFileFilter(pngFilter);

                    fileChooser.setDialogTitle("Specify a file to save");
                    int userSelection = fileChooser.showSaveDialog(parentFrame);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                    }
                    String Extension = fileChooser.getSelectedFile().getAbsolutePath();
                    String temp = "";
                    for(int i=0;i<Extension.length();i++){
                        if(Extension.charAt(Extension.length()-1-i)=='.'){
                            break;
                        }
                        temp = Extension.charAt(Extension.length()-1-i)+temp;
                    }
                    boolean flag = false;
                    if(temp.toLowerCase().equals("jpg")||temp.toLowerCase().equals("jpeg")||temp.toLowerCase().equals("png")||temp.toLowerCase().equals("bmp"))
                        flag = true;
                    String userExtension = fileChooser.getFileFilter().getDescription();
                    String userTemp = "";
                    for(int i=0;i<userExtension.length();i++) {
                        if (userExtension.charAt(i) == ' '){
                            userTemp = "."+userTemp;
                            break;
                        }
                        userTemp = userTemp+userExtension.charAt(i);
                    }
                    if(flag==false){
                        Extension = Extension + userTemp.toLowerCase();
                    }
                    ImageIO.write(main.image,"png",new File(Extension));
                }
                catch (Exception exception){
                    JOptionPane.showConfirmDialog(main, Constants.CANCEL_WARNING,"Warning",JOptionPane.CLOSED_OPTION);
                }
            }
        });

        JMenu filterMenu = new JMenu("Filter");

        JMenuItem grayScaleItem = new JMenuItem("Gray Scale");
        filterMenu.add(grayScaleItem);

        grayScaleItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    GrayScale grayScale = new GrayScale();
                    main.image = main.loadImage(GrayScale.getImage(main.image),jFrame);
                    main.numberOfFilter++;
                    main.numberFilter++;
                    File file = new File(String.valueOf(main.numberOfFilter) + ".jpg");
                    ImageIO.write(main.image, "jpg", file);
                }
                catch (Exception exception){
                    JOptionPane.showConfirmDialog(main, Constants.CANCEL_WARNING,"Warning",JOptionPane.CLOSED_OPTION);
                }
            }
        });

        JMenuItem sepiaItem = new JMenuItem("Sepia");
        filterMenu.add(sepiaItem);

        sepiaItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    Sepia sepia = new Sepia();
                    main.image = main.loadImage(sepia.getImage(main.image),jFrame);
                    main.numberOfFilter++;
                    main.numberFilter++;
                    File file = new File(String.valueOf(main.numberOfFilter) + ".jpg");
                    ImageIO.write(main.image, "jpg", file);
                }
                catch (Exception exception){
                    JOptionPane.showConfirmDialog(main, Constants.CANCEL_WARNING,"Warning",JOptionPane.CLOSED_OPTION);
                }
            }
        });

        JMenuItem negativeItem = new JMenuItem("Negative Image");
        filterMenu.add(negativeItem);

        negativeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    NegativeImage negativeImage = new NegativeImage();
                    main.image = main.loadImage(negativeImage.getImage(main.image),jFrame);
                    main.numberOfFilter++;
                    main.numberFilter++;
                    File file = new File(String.valueOf(main.numberOfFilter) + ".jpg");
                    ImageIO.write(main.image, "jpg", file);
                }
                catch (Exception exception){
                    JOptionPane.showConfirmDialog(main, Constants.CANCEL_WARNING,"Warning",JOptionPane.CLOSED_OPTION);
                }
            }
        });

        JMenuItem sharpeningItem = new JMenuItem("Sharp");
        filterMenu.add(sharpeningItem);

        sharpeningItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    Sharpening sharpening = new Sharpening();
                    main.image = main.loadImage(sharpening.getImage(main.image),jFrame);
                    main.numberOfFilter++;
                    main.numberFilter++;
                    File file = new File(String.valueOf(main.numberOfFilter) + ".jpg");
                    ImageIO.write(main.image, "jpg", file);
                }
                catch (Exception exception){
                    JOptionPane.showConfirmDialog(main, Constants.CANCEL_WARNING,"Warning",JOptionPane.CLOSED_OPTION);
                }
            }
        });

        JMenuItem extraSharpItem = new JMenuItem("Extra Sharp");
        filterMenu.add(extraSharpItem);

        extraSharpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    ExtraSharp extraSharp = new ExtraSharp();
                    main.image = main.loadImage(extraSharp.getImage(main.image),jFrame);
                    main.numberOfFilter++;
                    main.numberFilter++;
                    File file = new File(String.valueOf(main.numberOfFilter) + ".jpg");
                    ImageIO.write(main.image, "jpg", file);
                }
                catch (Exception exception){
                    JOptionPane.showConfirmDialog(main, Constants.CANCEL_WARNING,"Warning",JOptionPane.CLOSED_OPTION);
                }
            }
        });

        JMenuItem solarizeItem = new JMenuItem("Solarize");
        filterMenu.add(solarizeItem);

        solarizeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    Solarize solarize = new Solarize();
                    main.image = main.loadImage(solarize.getImage(main.image),jFrame);
                    main.numberOfFilter++;
                    main.numberFilter++;
                    File file = new File(String.valueOf(main.numberOfFilter) + ".jpg");
                    ImageIO.write(main.image, "jpg", file);
                }
                catch (Exception exception){
                    JOptionPane.showConfirmDialog(main, Constants.CANCEL_WARNING,"Warning",JOptionPane.CLOSED_OPTION);
                }
            }
        });

        JMenuItem embossItem = new JMenuItem("Emboss");
        filterMenu.add(embossItem);

        embossItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    Emboss emboss = new Emboss();
                    main.image = main.loadImage(emboss.getImage(main.image),jFrame);
                    main.numberOfFilter++;
                    main.numberFilter++;
                    File file = new File(String.valueOf(main.numberOfFilter) + ".jpg");
                    ImageIO.write(main.image, "jpg", file);
                }
                catch (Exception exception){
                    JOptionPane.showConfirmDialog(main, Constants.CANCEL_WARNING,"Warning",JOptionPane.CLOSED_OPTION);
                }
            }
        });

        JMenuItem blurItem = new JMenuItem("Blur");
        filterMenu.add(blurItem);

        blurItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    Blur blur = new Blur();
                    main.image = main.loadImage(blur.getImage(main.image),jFrame);
                    main.numberOfFilter++;
                    main.numberFilter++;
                    File file = new File(String.valueOf(main.numberOfFilter) + ".jpg");
                    ImageIO.write(main.image, "jpg", file);
                }
                catch (Exception exception){
                    JOptionPane.showConfirmDialog(main, Constants.CANCEL_WARNING,"Warning",JOptionPane.CLOSED_OPTION);
                }
            }
        });

        JMenuItem lightItem = new JMenuItem("Light");
        filterMenu.add(lightItem);

        lightItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    Light light = new Light();
                    main.image = main.loadImage(light.getImage(main.image),jFrame);
                    main.numberOfFilter++;
                    main.numberFilter++;
                    File file = new File(String.valueOf(main.numberOfFilter) + ".jpg");
                    ImageIO.write(main.image, "jpg", file);
                }
                catch (Exception exception){
                    JOptionPane.showConfirmDialog(main, Constants.CANCEL_WARNING,"Warning",JOptionPane.CLOSED_OPTION);
                }
            }
        });

        JMenuItem yrItem = new JMenuItem("YR");
        filterMenu.add(yrItem);

        yrItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    YellowRead yellowRead = new YellowRead();
                    main.image = main.loadImage(yellowRead.getImage(main.image),jFrame);
                    main.numberOfFilter++;
                    main.numberFilter++;
                    File file = new File(String.valueOf(main.numberOfFilter) + ".jpg");
                    ImageIO.write(main.image, "jpg", file);
                }
                catch (Exception exception){
                    JOptionPane.showConfirmDialog(main, Constants.CANCEL_WARNING,"Warning",JOptionPane.CLOSED_OPTION);
                }
            }
        });

        JMenuItem colorEdgeItem = new JMenuItem("Color Edge");
        filterMenu.add(colorEdgeItem);

        colorEdgeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    ColorEdge colorEdge = new ColorEdge();
                    main.image = main.loadImage(colorEdge.getImage(main.image),jFrame);
                    main.numberOfFilter++;
                    main.numberFilter++;
                    File file = new File(String.valueOf(main.numberOfFilter) + ".jpg");
                    ImageIO.write(main.image, "jpg", file);
                }
                catch (Exception exception){
                    JOptionPane.showConfirmDialog(main, Constants.CANCEL_WARNING,"Warning",JOptionPane.CLOSED_OPTION);
                }
            }
        });

        JMenuItem edgeItem = new JMenuItem("Edge");
        filterMenu.add(edgeItem);

        edgeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    Edge edge = new Edge();
                    main.image = main.loadImage(edge.getImage(main.image),jFrame);
                    main.numberOfFilter++;
                    main.numberFilter++;
                    File file = new File(String.valueOf(main.numberOfFilter) + ".jpg");
                    ImageIO.write(main.image, "jpg", file);
                }
                catch (Exception exception){
                    JOptionPane.showConfirmDialog(main, Constants.CANCEL_WARNING,"Warning",JOptionPane.CLOSED_OPTION);
                }
            }
        });

        JMenuItem brightItem = new JMenuItem("Bright");
        filterMenu.add(brightItem);

        brightItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    Bright bright = new Bright();
                    main.image = main.loadImage(bright.getImage(main.image),jFrame);
                    main.numberOfFilter++;
                    main.numberFilter++;
                    File file = new File(String.valueOf(main.numberOfFilter) + ".jpg");
                    ImageIO.write(main.image, "jpg", file);
                }
                catch (Exception exception){
                    JOptionPane.showConfirmDialog(main, Constants.CANCEL_WARNING,"Warning",JOptionPane.CLOSED_OPTION);
                }
            }
        });

        JMenu flipMenu = new JMenu("Flip");

        JMenuItem flipRLItem = new JMenuItem("Flip Right -> Left");
        flipMenu.add(flipRLItem);

        flipRLItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    Flip1 flip1 = new Flip1();
                    main.image = main.loadImage(flip1.getImage(main.image),jFrame);
                    main.numberOfFilter++;
                    main.numberFilter++;
                    File file = new File(String.valueOf(main.numberOfFilter) + ".jpg");
                    ImageIO.write(main.image, "jpg", file);
                }
                catch (Exception exception){
                    JOptionPane.showConfirmDialog(main, Constants.CANCEL_WARNING,"Warning",JOptionPane.CLOSED_OPTION);
                }
            }
        });

        JMenuItem flipTBItem = new JMenuItem("Flip Top -> Bottom");
        flipMenu.add(flipTBItem);

        flipTBItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    Flip2 flip2 = new Flip2();
                    main.image = main.loadImage(flip2.getImage(main.image),jFrame);
                    main.numberOfFilter++;
                    main.numberFilter++;
                    File file = new File(String.valueOf(main.numberOfFilter) + ".jpg");
                    ImageIO.write(main.image, "jpg", file);
                }
                catch (Exception exception){
                    JOptionPane.showConfirmDialog(main, Constants.CANCEL_WARNING,"Warning",JOptionPane.CLOSED_OPTION);
                }
            }
        });

        JMenu editMenu = new JMenu("Edit");

        JMenuItem resetItem = new JMenuItem("Reset");
        editMenu.add(resetItem);

        resetItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try{
                    main.numberOfFilter = 1;
                    File file = new File(String.valueOf(main.numberOfFilter) + ".jpg");
                    main.image = ImageIO.read(file);
                    main.image = main.loadImage(main.image,jFrame);
                }
                catch (Exception exception){
                    JOptionPane.showConfirmDialog(main, Constants.CANCEL_WARNING,"Warning",JOptionPane.CLOSED_OPTION);
                }
            }
        });

        JMenuItem undoItem = new JMenuItem("Undo");
        editMenu.add(undoItem);

        undoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    File file = new File(String.valueOf(--main.numberOfFilter) + ".jpg");
                    main.image = ImageIO.read(file);
                    main.image = main.loadImage(main.image,jFrame);

                } catch (Exception exception) {
                    JOptionPane.showConfirmDialog(main, Constants.CANCEL_WARNING,"Warning",JOptionPane.CLOSED_OPTION);
                }
            }
        });

        JMenuItem redoItem = new JMenuItem("Redo");
        editMenu.add(redoItem);

        redoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    File file = new File(String.valueOf(++main.numberOfFilter) + ".jpg");
                    main.image = ImageIO.read(file);
                    main.image = main.loadImage(main.image,jFrame);

                } catch (Exception exception) {
                    JOptionPane.showConfirmDialog(main, Constants.CANCEL_WARNING,"Warning",JOptionPane.CLOSED_OPTION);
                }
            }
        });

        JMenuItem adjustColorItem = new JMenuItem("Adjust Color");
        editMenu.add(adjustColorItem);

        adjustColorItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    AdjustColor adjustColor = new AdjustColor();
                    adjustColor.processImage(main.image,main,jFrame,main.numberOfFilter+1);
                    main.image = adjustColor.getImage();
                    main.numberOfFilter++;
                    main.numberFilter++;
                    File file = new File(String.valueOf(main.numberOfFilter) + ".jpg");
                    ImageIO.write(main.image, "jpg", file);
                } catch (Exception exception) {
                    JOptionPane.showConfirmDialog(main, Constants.CANCEL_WARNING,"Warning",JOptionPane.CLOSED_OPTION);
                }
            }
        });

        JMenu aboutMenu = new JMenu("About");
        JMenu helpMenu = new JMenu("Help");

        JMenuItem aboutItem = new JMenuItem("About");
        aboutMenu.add(aboutItem);

        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + "About.pdf");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JMenuItem helpItem = new JMenuItem("Help");
        helpMenu.add(helpItem);

        helpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + "Help.pdf");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(filterMenu);
        menuBar.add(flipMenu);
        menuBar.add(aboutMenu);
        menuBar.add(helpMenu);

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int action = JOptionPane.showConfirmDialog(main, Constants.EXIT_WARNING,"Warning",JOptionPane.YES_NO_OPTION);
                if(action == JOptionPane.OK_OPTION){
                    while(main.numberFilter!=0){
                        File file = new File(String.valueOf(main.numberFilter--) + ".jpg");
                        file.delete();
                    }
                    System.gc();
                    System.exit(0);
                }
            }
        });
        jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                int action = JOptionPane.showConfirmDialog(main, Constants.EXIT_WARNING,"Warning",JOptionPane.YES_NO_OPTION);
                if(action == JOptionPane.OK_OPTION){
                    while(main.numberFilter!=0){
                        File file = new File(String.valueOf(main.numberFilter--) + ".jpg");
                        file.delete();
                    }
                    System.gc();
                    System.exit(0);
                }
            }
        });
        jFrame.setJMenuBar(menuBar);
        jFrame.setVisible(true);
    }
    public void loadImage(File file,JFrame jFrame) throws IOException {
        this.image = ImageIO.read(this.file);
        Dimension dimension =jFrame.getSize();
        jFrame.setSize(dimension.width+1, dimension.height+1);
        jFrame.setSize(dimension.width, dimension.height);
        jFrame.add(this);
    }

    public BufferedImage loadImage(BufferedImage image,JFrame jFrame){
        this.image = image;
        Dimension dimension =jFrame.getSize();
        jFrame.setSize(dimension.width+1, dimension.height+1);
        jFrame.setSize(dimension.width, dimension.height);
        jFrame.add(this);
        return this.image;
    }
}