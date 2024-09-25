import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import org.fife.ui.rtextarea.*;
import org.fife.ui.rsyntaxtextarea.*;

class App extends JFrame {
    private void setTheme(String theme) {
        try {
            UIManager.setLookAndFeel(theme);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            System.err.println("Failed to set theme!");
            e.printStackTrace();
        }
    }
    
    private JTabbedPane tabs;
    
    public App() {
        setTitle("Brewery");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setTheme("com.formdev.flatlaf.FlatLightLaf");
        
        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        
        // Tabs
        tabs = new JTabbedPane();
        add(tabs, BorderLayout.CENTER);
        
        JLabel label = new JLabel("Tabbb");
        label.putClientProperty("JTabbedPane.tabClosable", true);
        
        tabs.addTab("Tab", label);

        setVisible(true);
    }
}