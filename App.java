import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import org.fife.ui.rtextarea.*;
import org.fife.ui.rsyntaxtextarea.*;

import java.util.function.IntConsumer;

import java.io.*;

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
        //pane.putClientProperty("JTabbedPane.tabClosable", true);
        pane.putClientProperty("JTabbedPane.tabCloseCallback", (IntConsumer) tabIndex -> {
            
        });
        
        tabs.addTab(filePath, Resources.getAsImageIcon("images/code.png"), pane);
    }
    
    private App thisApp;
    private JTabbedPane tabs;
    
    public App() {
        thisApp = this;
        
        setTitle("Brewery");
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setTheme("com.formdev.flatlaf.FlatLightLaf");
        setIconImage(Resources.getAsImage("images/icon_128.png"));
        
        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu folderMenu = new JMenu("Folder");
        menuBar.add(folderMenu);
        
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        
        menuBar.add(Box.createGlue());
        JButton helpButton = new JButton(Resources.getAsImageIcon("images/help.png"));
        helpButton.setBorderPainted(false);
        
        helpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(thisApp, "Brewery " + Main.VERSION + "\nPlease visit https://github.com/mochawoof/brewery/issues to get help with Brewery.", "Brewery Help", JOptionPane.INFORMATION_MESSAGE, Resources.getAsImageIcon("images/icon_128.png"));
            }
        });
        
        menuBar.add(helpButton);
        
        // Tabs
        UIManager.put("TabbedPane.tabAlignment", "leading");
        tabs = new JTabbedPane(JTabbedPane.LEFT);
        add(tabs, BorderLayout.CENTER);
        
        addFileTab("Main.java");
        addFileTab("Frame.java");

        setVisible(true);
    }
}