package com.supremefist.klwc;

import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ConsultationCellRenderer extends JLabel implements ListCellRenderer {

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        
        Consultation c = (Consultation)value;
        setText(formatter.format(c.getConsultationDate()));
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
