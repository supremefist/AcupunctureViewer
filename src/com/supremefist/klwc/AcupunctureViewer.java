package com.supremefist.klwc;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class AcupunctureViewer {
    /*
     * Controller class for Acupuncture Viewer.
     */
    
    private View view;
    private PatientSQLiteDB db;
    
    public AcupunctureViewer() {
        view = new View(this);
        db = null;
    }
    
    public void start() {
        view.setVisible(true);
    }
    
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AcupunctureViewer app = new AcupunctureViewer();
                app.start();
            }
        });
    }

    public void loadDir(File selectedDir) {
        db = new PatientSQLiteDB(selectedDir);
        db.read();
    }
}    



