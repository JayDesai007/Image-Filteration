import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class App {
    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(laf.getName())) {
                    try {
                        UIManager.setLookAndFeel(laf.getClassName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        try{
            UIManager.setInstalledLookAndFeels(UIManager.getInstalledLookAndFeels());
        }
        catch (UnsupportedOperationException e){
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Main().mainFrame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    });
    }
}
