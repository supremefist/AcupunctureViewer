package com.supremefist.klwc;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class PatientCellRenderer extends JLabel implements ListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        
        Patient p = (Patient)value;
        setText(p.getName());
        setOpaque(true);
        
        if (isSelected) {
            setBorder(BorderFactory.createLineBorder(Color.black));
        }
        else {
            setBorder(null);
        }
        
        return this;
    }

}
