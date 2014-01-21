package com.supremefist.klwc;

import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PatientListSelectionHandler implements ListSelectionListener {
    AcupunctureViewer control = null;
    
    public PatientListSelectionHandler(AcupunctureViewer control) {
        this.control = control;
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
        Patient selectedPatient = (Patient) control.getPatientListModel().get(lsm.getMinSelectionIndex());
        control.setSelectedPatient(selectedPatient);
    }

}
