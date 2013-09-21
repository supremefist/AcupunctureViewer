package com.supremefist.klwc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


public class View extends JFrame {
    private AcupunctureViewer control = null;
    private JFileChooser fc = null;
    
    public View(AcupunctureViewer newAcupunctureViewer) {
        control = newAcupunctureViewer;
        
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
        
        setTitle("KLWC Acupuncture Viewer");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        initUI();
    }
    
    private class LoadPatientActionListener implements ActionListener {
        private View view;

        public LoadPatientActionListener(View newView) {
            view = newView;
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            int returnVal = fc.showOpenDialog(view);
            
            if (returnVal == 0) {
                System.out.println("User wants to load: " + fc.getSelectedFile());
                control.loadDir(fc.getSelectedFile());
            }
        }
        
    }
    
    public final void initUI() {

        JMenuBar menubar = new JMenuBar();

        // File menu
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        
        // Select patient directory
        JMenuItem dMenuItem = new JMenuItem("Load...");
        dMenuItem.setMnemonic(KeyEvent.VK_S);
        dMenuItem.setToolTipText("Select patient directory");
        dMenuItem.addActionListener(new LoadPatientActionListener(this));

        file.add(dMenuItem);
        
        file.addSeparator();

        // Exit
        JMenuItem eMenuItem = new JMenuItem("Exit");
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        file.add(eMenuItem);

        menubar.add(file);
        
        // About menu
        JMenu help = new JMenu("Help");
        file.setMnemonic(KeyEvent.VK_H);

        JMenuItem aMenuItem = new JMenuItem("About");
        aMenuItem.setMnemonic(KeyEvent.VK_E);
        aMenuItem.setToolTipText("About AcupunctureViewer");
        aMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
            }
        });

        help.add(aMenuItem);

        menubar.add(help);

        setJMenuBar(menubar);
    }
}
