import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import org.fife.ui.rsyntaxtextarea.*;
import javax.swing.tree.*;
import java.io.File;

class Main {
    private static JFrame f;
    private static DefaultMutableTreeNode ftreeroot;
    private static File openfolder;
    
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
        if (UIManager.getLookAndFeel().getSupportsWindowDecorations()) {
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
        f.setSize(800, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setIconImage(Res.getAsImage("res/icon64.png"));
        
        update();
        
        JMenuBar menubar = new JMenuBar();
        f.setJMenuBar(menubar);
        
        JMenu filem = new JMenu("File");
        filem.setMnemonic(KeyEvent.VK_F);
        menubar.add(filem);
        
        JMenu compilem = new JMenu("Compile");
        compilem.setMnemonic(KeyEvent.VK_C);
        menubar.add(compilem);
        
        JMenu toolsm = new JMenu("Tools");
        toolsm.setMnemonic(KeyEvent.VK_T);
        menubar.add(toolsm);
            JMenuItem settingsi = new JMenuItem("Settings...");
            settingsi.setMnemonic(KeyEvent.VK_S);
            settingsi.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int o = Settings.show(f);
                    if (o == Settings.OK || o == Settings.RESET) {
                        update();
                    }
                }
            });
            toolsm.add(settingsi);
        
        JMenu helpm = new JMenu("Help");
        helpm.setMnemonic(KeyEvent.VK_H);
        menubar.add(helpm);
        
        // Components
        JSplitPane filesplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        filesplit.setDividerLocation(150);
        f.add(filesplit, BorderLayout.CENTER);
            ftreeroot = new DefaultMutableTreeNode("");
            JTree ftree = new JTree(ftreeroot);
            filesplit.setLeftComponent(ftree);
        
        JSplitPane termsplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        termsplit.setDividerLocation(300);
        filesplit.setBottomComponent(termsplit);
            RSyntaxTextArea textarea = new RSyntaxTextArea();
            termsplit.setTopComponent(new JScrollPane(textarea));
            
            RSyntaxTextArea termtextarea = new RSyntaxTextArea();
            termtextarea.setBackground(Color.BLACK);
            termtextarea.setForeground(Color.WHITE);
            termtextarea.setCurrentLineHighlightColor(Color.BLACK);
            termtextarea.setEditable(false);
            termsplit.setBottomComponent(new JScrollPane(termtextarea));
        
        f.setVisible(true);
    }
}