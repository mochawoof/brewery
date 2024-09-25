import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import org.fife.ui.rtextarea.*;
import org.fife.ui.rsyntaxtextarea.*;

import java.util.function.IntConsumer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    
    private String getMimeType(String filePath) {
        String type = null;
        try {
            type = Files.probeContentType(Paths.get(filePath));
        } catch (Exception e) {
            System.err.println("Mime type probe failed for " + filePath);
            e.printStackTrace();
        }
        if (type == null) {
            type = "text/plain";
        }
        return type;
    }
    
    private void addFileTab(String filePath) {
        RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(getMimeType(filePath));
        textArea.setCodeFoldingEnabled(true);
        
        RTextScrollPane pane = new RTextScrollPane(textArea);
        pane.putClientProperty("JTabbedPane.tabClosable", true);
        pane.putClientProperty("JTabbedPane.tabCloseCallback", (IntConsumer) tabIndex -> {
            
        });
        
        tabs.addTab(filePath, pane);
    }
    
    private JTabbedPane tabs;
    
    public App() {
        setTitle("Brewery");
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setTheme("com.formdev.flatlaf.FlatLightLaf");
        setIconImage(Resources.getAsImage("images/icon_128.png"));
        
        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu projectMenu = new JMenu("Project");
        menuBar.add(projectMenu);
        
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        
        // Tabs
        tabs = new JTabbedPane();
        add(tabs, BorderLayout.CENTER);
        
        addFileTab("Main.java");
        addFileTab("Frame.java");

        setVisible(true);
    }
}