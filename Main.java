import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;

import org.fife.ui.rsyntaxtextarea.*;

import java.util.HashMap;
import java.util.Enumeration;

import java.io.File;
import java.nio.file.*;

class Main {
    // Keep in mind that any variables here that are not set in the constructor will persist between restarts
    public static final String VERSION = "1";
    public static String openFolder = "./";
    
    public static JFrame frame;
    public static JTree fileTree;
    public static JSplitPane tpane;
    public static JSplitPane pane;
    public static JTabbedPane tabs;
    
    public static void main(String[] args) {
        Settings.init();
        
        // Load theme
        JFrame.setDefaultLookAndFeelDecorated(true);
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
        JMenuItem fOpen = new JMenuItem("Open...");
        fOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFolder();
            }
        });
        fm.add(fOpen);
        JMenuItem fRefresh = new JMenuItem("Refresh");
        fRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshFileTree();
            }
        });
        fm.add(fRefresh);
        
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
                int opt = JOptionPane.showConfirmDialog(frame, "Are you sure you want to reset all your settings? Brewery will restart afterwards.", "Confirm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
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
        
        // terminal pane
        tpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        frame.add(tpane, BorderLayout.CENTER);
        tpane.setDividerLocation(230);
        
        Terminal term = new Terminal();
        term.setText("Welcome to Brewery!");
        tpane.setBottomComponent(term);
        
        // pane
        pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        tpane.setTopComponent(pane);
        pane.setDividerLocation(150);
        
        UIManager.put("ToolBar.floatable", true);
        JToolBar toolBar = new JToolBar("Actions");
        toolBar.add(new JButton(Resources.getAsImageIcon("res/compile.png")));
        toolBar.add(new JButton(Resources.getAsImageIcon("res/run.png")));
        toolBar.add(new JButton(Resources.getAsImageIcon("res/build-jar.png")));
        frame.add(toolBar, BorderLayout.PAGE_END);
        
        UIManager.put("Tree.showDefaultIcons", true);
        refreshFileTree();
        
        tabs = new JTabbedPane();
        pane.setRightComponent(tabs);
        /*RSyntaxTextArea ta = new RSyntaxTextArea();
                
        // Load theme for RSyntaxTextArea
        if (Settings.get("Editor_Theme").equals("Dark")) {
            try {
                Theme.load(Resources.getAsStream("org/fife/ui/rsyntaxtextarea/themes/dark.xml")).apply(ta);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        ta.setCodeFoldingEnabled(true);
        pane.setRightComponent(new JScrollPane(ta));*/
        
        frame.setVisible(true);
    }
    
    private static void addTab(Path p) {        
        File f = new File(p.toUri());
        if (f.exists() && f.isFile() && f.canRead()) {
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
            
            tabs.addTab(Paths.get(openFolder).toAbsolutePath().relativize(p.toAbsolutePath()).toString(), new JScrollPane(ta));
        }
    }
    
    private static void openFolder() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int opt = chooser.showOpenDialog(frame);
        if (opt == JFileChooser.APPROVE_OPTION) {
            openFolder = chooser.getSelectedFile().getAbsolutePath();
            refreshFileTree();
        }
    }
    
    private static Path putTogetherPath(Object[] path) {
        Path p = Paths.get((String) path[0]);
        p = p.normalize();
        for (int i = 1; i < path.length; i++) {
            Object o = path[i];
            p = p.resolve((String) o);
        }
        return p;
    }
    
    private static void addToFileTree(File f, DefaultMutableTreeNode parent) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(f.getName());
        if (parent != null) {
            parent.add(node);
        } else {
            fileTree = null;
            node.setUserObject(f.getAbsolutePath());
            fileTree = new JTree(node);
            fileTree.setEditable(false);
            fileTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
            fileTree.addTreeSelectionListener(new TreeSelectionListener() {
                public void valueChanged(TreeSelectionEvent e) {
                    // Put file path back together
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();
                    Object[] path = node.getUserObjectPath();
                    addTab(putTogetherPath(path));
                }
            });
            pane.setLeftComponent(new JScrollPane(fileTree));
        }
        
        if (f.isDirectory() && f.canRead()) {
            for (File fs : f.listFiles()) {
                addToFileTree(fs, node);
            }
        }
    }
    
    private static void refreshFileTree() {
        fileTree = null;
        
        File openFolderf = new File(openFolder);
        if (openFolderf.exists() && openFolderf.isDirectory() && openFolderf.canRead()) {
            addToFileTree(openFolderf, null);
        }
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