package com.supremefist.klwc;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    private Map<String, AcupuncturePoint> points;
    private List<Meridian> meridians;
    
    public AcupunctureViewer() {
        view = new View(this);
        db = null;
        
        meridians = new ArrayList<Meridian>();
        meridians.add(new Meridian("Gall Bladder"));
    }
    
    public void start() {
        view.initUI();
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
        points = db.getAcupuncturePoints();
        List<Patient> patients = db.getPatients();
        
        view.setPatientList(patients);
        view.setMeridianList(meridians);
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

    public void setSelectedMeridian(Meridian meridian) {
        // TODO Auto-generated method stub
        
    }

    public DefaultListModel getMeridianListModel() {
        // TODO Auto-generated method stub
        return view.getMeridianListModel();
    }
}    



