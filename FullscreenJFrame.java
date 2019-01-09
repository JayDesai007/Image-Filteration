import java.awt.event.*;
import javax.swing.*;

public class FullscreenJFrame extends JFrame {
    private JPanel contentPane = new JPanel();
    private JButton fullscreenButton = new JButton("Fullscreen Mode");
    private boolean Am_I_In_FullScreen = false;
    private int PrevX, PrevY, PrevWidth, PrevHeight;

    public static void main(String[] args) {
        FullscreenJFrame frame = new FullscreenJFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setVisible(true);
    }

    public FullscreenJFrame() {
        super("My FullscreenJFrame");

        setContentPane(contentPane);

        // From Here starts the trick
        FullScreenEffect effect = new FullScreenEffect();

        fullscreenButton.addActionListener(effect);

        contentPane.add(fullscreenButton);
        fullscreenButton.setVisible(true);
    }

    private class FullScreenEffect implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (Am_I_In_FullScreen == false) {
                PrevX = getX();
                PrevY = getY();
                PrevWidth = getWidth();
                PrevHeight = getHeight();

                // Destroys the whole JFrame but keeps organized every Component
                // Needed if you want to use Undecorated JFrame dispose() is the
                // reason that this trick doesn't work with videos.
                dispose();
                setUndecorated(true);

                setBounds(0, 0, getToolkit().getScreenSize().width,
                        getToolkit().getScreenSize().height);
                setVisible(true);
                Am_I_In_FullScreen = true;
            } else {
                setVisible(true);
                setBounds(PrevX, PrevY, PrevWidth, PrevHeight);
                dispose();
                setUndecorated(false);
                setVisible(true);
                Am_I_In_FullScreen = false;
            }
        }
    }
}