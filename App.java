import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import org.fife.ui.rtextarea.*;
import org.fife.ui.rsyntaxtextarea.*;

import java.util.function.IntConsumer;

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
    
    private void addFileTab(String filePath) {
        RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
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
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setTheme("com.formdev.flatlaf.FlatLightLaf");
        setIconImage(Resources.getAsImage("images/icon_128.png"));
        
        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
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