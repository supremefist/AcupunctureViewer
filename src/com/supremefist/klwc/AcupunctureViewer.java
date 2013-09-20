package com.supremefist.klwc;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class AcupunctureViewer {
    /*
     * Controller class for Acupuncture Viewer.
     */
    
    private View view;
    
    public AcupunctureViewer() {
        view = new View();
    }
    
    public void start() {
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
}    



