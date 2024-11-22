import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;

import org.fife.ui.rsyntaxtextarea.*;

import java.util.HashMap;
import java.util.Enumeration;

class Main {
    public static final String VERSION = "1";
    
    public static JFrame frame;
    public static void main(String[] args) {
        Settings.init();
        
        // Load theme
        try {
            UIManager.setLookAndFeel(Settings.get("Theme"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        frame = new JFrame("Brewery");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setIconImage(Resources.getAsImage("res/icon-48.png"));
        
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        JMenu fm = new JMenu("File");
        menuBar.add(fm);
        JMenuItem fOpen = new JMenuItem("Open");
        fm.add(fOpen);
        
        JMenu editm = new JMenu("Edit");
        menuBar.add(editm);
        JMenuItem eSettings = new JMenuItem("Settings...");
        eSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editSettings();
            }
        });
        editm.add(eSettings);
        JMenuItem rSettings = new JMenuItem("Reset Settings");
        rSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int opt = JOptionPane.showConfirmDialog(frame, "Are you sure you want to reset all your settings? Brewery will have to restart.", "Confirm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                if (opt == JOptionPane.OK_OPTION) {
                    Settings.reset();
                    restart();
                }
            }
        });
        editm.add(rSettings);
        
        JMenu hm = new JMenu("Help");
        menuBar.add(hm);
        JMenuItem hAbout = new JMenuItem("About");
        hAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame,
                    "Brewery v" + VERSION + "\n" +
                    "Copyright (c) Julian Herbert 2024",
                "About Brewery", JOptionPane.PLAIN_MESSAGE, Resources.getAsImageIcon("res/icon-128.png"));
            }
        });
        hm.add(hAbout);
        
        // pane
        JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        frame.add(pane, BorderLayout.CENTER);
        pane.setDividerLocation(150);
        
        UIManager.put("ToolBar.floatable", true);
        JToolBar toolBar = new JToolBar("Actions");
        toolBar.add(new JButton(Resources.getAsImageIcon("res/compile.png")));
        toolBar.add(new JButton(Resources.getAsImageIcon("res/run.png")));
        toolBar.add(new JButton(Resources.getAsImageIcon("res/build-jar.png")));
        frame.add(toolBar, BorderLayout.PAGE_END);
        
        UIManager.put("Tree.showDefaultIcons", true);
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("C:\\Users\\4804095191\\");
        root.add(new DefaultMutableTreeNode("fag.txt"));
        
        JTree fileTree = new JTree(root);
        pane.setLeftComponent(new JScrollPane(fileTree));
        
        RSyntaxTextArea ta = new RSyntaxTextArea();
                
        // Load theme for RSyntaxTextArea
        if (Settings.get("Editor_Theme").equals("Dark")) {
            try {
                Theme.load(Resources.getAsStream("org/fife/ui/rsyntaxtextarea/themes/dark.xml")).apply(ta);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        ta.setCodeFoldingEnabled(true);
        pane.setRightComponent(new JScrollPane(ta));
        
        frame.setVisible(true);
    }
    
    private static void restart() {
        frame.dispose();
        main(new String[] {});
    }
    
    private static void editSettings() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));
        
        Enumeration en = Settings.props.propertyNames();
        
        HashMap<String, JComboBox> boxes = new HashMap<String, JComboBox>();
        
        while (en.hasMoreElements()) {
            String key = (String) en.nextElement();
            
            String[] defaultVals = Settings.defaults.get(key);
            
            // Make sure default values exist
            if (defaultVals != null) {
                panel.add(new JLabel(key.replace("_", " ")));
                            
                JComboBox comboBox = new JComboBox(defaultVals);
                comboBox.setEditable(true);
                comboBox.setSelectedItem(Settings.get(key));
                
                panel.add(comboBox);
                boxes.put(key, comboBox);
            }
        }
        
        int opt = JOptionPane.showConfirmDialog(frame, new JScrollPane(panel), "Settings", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (opt == JOptionPane.OK_OPTION) {
            for (HashMap.Entry<String, JComboBox> e : boxes.entrySet()) {
                Settings.set(e.getKey().replace(" ", "_"), (String) e.getValue().getSelectedItem());
            }
            
            int restartOpt = JOptionPane.showConfirmDialog(frame, "Brewery must restart to apply your changes. Restart now?", "Confirm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (restartOpt == JOptionPane.OK_OPTION) {
                restart();
            }
        }
    }
}