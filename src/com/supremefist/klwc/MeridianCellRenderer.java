package com.supremefist.klwc;

import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class MeridianCellRenderer extends JLabel implements ListCellRenderer {

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        
        Meridian m = (Meridian)value;
        setText(m.name);
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
