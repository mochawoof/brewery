import javax.swing.*;
import java.awt.event.*;

class Main {
    private static JFrame f;
    
    public static void update() {
        try {
            if (Settings.get("Theme").equals("System")) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } else {
                for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
                    if (Settings.get("Theme").equals(laf.getName())) {
                        UIManager.setLookAndFeel(laf.getClassName());
                    }
                }
            }
            SwingUtilities.updateComponentTreeUI(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        f.dispose();
        if (Settings.get("Standard_Window_Decoration").equals("Off") && UIManager.getLookAndFeel().getSupportsWindowDecorations()) {
            f.setUndecorated(true);
            f.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        } else {
            f.setUndecorated(false);
            f.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        }
        f.setVisible(true);
    }
    public static void main(String[] args) {
        f = new JFrame("Brewery");
        f.setSize(800, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setIconImage(Res.getAsImage("res/icon64.png"));
        
        update();
        
        JMenuBar menubar = new JMenuBar();
        f.setJMenuBar(menubar);
        
        JMenu filem = new JMenu("File");
        menubar.add(filem);
        
        JMenu compilem = new JMenu("Compile");
        menubar.add(compilem);
        
        JMenu toolsm = new JMenu("Tools");
        menubar.add(toolsm);
            JMenuItem settingsi = new JMenuItem("Settings...");
            settingsi.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Settings.show(f);
                }
            });
            toolsm.add(settingsi);
        
        JMenu helpm = new JMenu("Help");
        menubar.add(helpm);
        
        f.setVisible(true);
    }
}