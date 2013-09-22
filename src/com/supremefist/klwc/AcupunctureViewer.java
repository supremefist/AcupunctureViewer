package com.supremefist.klwc;

import java.io.File;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
        view.initUI();
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

    public void selectDataDir(File selectedDir) {
        db = new PatientSQLiteDB(selectedDir);
        if (!db.ready()) {
            view.showMessage("All data not found in specified directory!");
            return;
        }
    
        db.connect();
        List<Patient> patients = db.getPatients();
        
        view.setPatientList(patients);
    
    }
}    



