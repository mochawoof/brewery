import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.event.*;

import org.fife.ui.rsyntaxtextarea.*;
import javax.swing.tree.*;
import java.io.File;

class Main {
    private static JFrame f;
    private static DefaultMutableTreeNode ftreeroot;
    private static JTree ftree;
    
    private static boolean loadfiles(DefaultMutableTreeNode folder, boolean top) {
        File file = (File) folder.getUserObject();
        folder.removeAllChildren();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File fl : files) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(fl);
                folder.add(node);
                
                if (fl.isDirectory() && top) {
                    loadfiles(node, false);
                }
            }
            
            if (files.length == 0) {
                return false;
            }
        } else {
            return false;
        }
        
        return true;
    }
    
    private static void loadfolder(File file) {
        if (file.isDirectory()) {
            ftreeroot.removeAllChildren();
            ftreeroot.setUserObject(file);
            boolean success = loadfiles(ftreeroot, true);
            
            if (success) {
                ftree.expandRow(0);
            } else {
                ftree.setRootVisible(false);
                ftree.setRootVisible(true);
            }
        }
    }
    
    public static void applysettings() {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        
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
        
        applysettings();
        
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
                        JOptionPane.showMessageDialog(f, "You must restart Brewery for some changes to take effect.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });
            toolsm.add(settingsi);
        
        JMenu helpm = new JMenu("Help");
        helpm.setMnemonic(KeyEvent.VK_H);
        menubar.add(helpm);
        
        // Components
        JSplitPane filesplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        filesplit.setDividerLocation(150);
        f.add(filesplit, BorderLayout.CENTER);
            ftreeroot = new DefaultMutableTreeNode();
            ftree = new JTree(ftreeroot);
                ftree.setCellRenderer(new DefaultTreeCellRenderer() {
                    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                        Object uo = ((DefaultMutableTreeNode) value).getUserObject();
                        Object v = value;
                        if (uo != null) {
                            v = ((File) uo).getName();
                        }
                        return super.getTreeCellRendererComponent(tree, v, sel, expanded, leaf, row, hasFocus);
                    }
                });
            filesplit.setLeftComponent(new JScrollPane(ftree));
        
        JSplitPane termsplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
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
        loadfolder(new File("."));
    }
}