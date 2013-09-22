package com.supremefist.klwc;

import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PatientListSelectionHandler implements ListSelectionListener {
    DefaultListModel model;
    
    public PatientListSelectionHandler(DefaultListModel model) {
        this.model = model;
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
        System.out.println("Selected " + model.get(lsm.getMinSelectionIndex()));
    }

}
