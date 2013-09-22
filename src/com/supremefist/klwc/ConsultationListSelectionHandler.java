package com.supremefist.klwc;

import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ConsultationListSelectionHandler implements ListSelectionListener {
    AcupunctureViewer control = null;
    
    public ConsultationListSelectionHandler(AcupunctureViewer control) {
        this.control = control;
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
        int selectedIndex = lsm.getMinSelectionIndex();
        if (selectedIndex >= 0) {
            control.setSelectedConsultation((Consultation) control.getConsultationListModel().get(selectedIndex));
        }
        
    }

}
