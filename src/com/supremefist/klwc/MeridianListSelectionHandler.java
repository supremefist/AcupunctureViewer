package com.supremefist.klwc;

import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MeridianListSelectionHandler implements ListSelectionListener {
    AcupunctureViewer control = null;
    
    public MeridianListSelectionHandler(AcupunctureViewer control) {
        this.control = control;
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
        int selectedIndex = lsm.getMinSelectionIndex();
        if (selectedIndex >= 0) {
            control.setSelectedMeridian((Meridian) control.getMeridianListModel().get(selectedIndex));
        }
        
    }

}
