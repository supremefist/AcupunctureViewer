package com.supremefist.klwc;

import java.io.File;
import java.util.List;

import javax.swing.DefaultListModel;
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

    public DefaultListModel getPatientListModel() {
        return view.getPatientListModel();
    }

    public void setSelectedPatient(Patient patient) {
        view.setConsultations(patient.getConsultations());
    }

    public DefaultListModel getConsultationListModel() {
        return view.getConsultationListModel();
    }

    public void setSelectedConsultation(Consultation consultation) {
        view.setSelectedConsultation(consultation);
    }
}    



