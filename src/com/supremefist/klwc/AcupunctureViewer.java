package com.supremefist.klwc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class AcupunctureViewer {
    /*
     * Controller class for Acupuncture Viewer.
     */
    
    private View view;
    private PatientSQLiteDB db;
    
    private Map<String, AcupuncturePoint> points;
    private List<Meridian> meridians;
    
    private File selectedDir = null;
    
    public AcupunctureViewer() {
        view = new View(this);
        db = null;
        
        meridians = new ArrayList<Meridian>();
        meridians.add(new Meridian("Gall Bladder"));
        
        loadSettings();
    }
    
    public boolean loadSettings() {
        
        Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the    
            // XML file
            dom = db.parse("settings.xml");

            Element doc = dom.getDocumentElement();
            
            Node dirEle = doc.getElementsByTagName("dataDir").item(0);
            selectedDir = new File(dirEle.getTextContent());
            

            return true;

        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        return false;
    }
    
    public void saveSettings() {
        
        Document dom;
        Element e = null;

        // instance of a DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use factory to get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // create instance of DOM
            dom = db.newDocument();

            // create the root element
            Element rootEle = dom.createElement("settings");

            // create data elements and place them under root
            e = dom.createElement("dataDir");
            e.appendChild(dom.createTextNode(selectedDir.getAbsolutePath()));
            rootEle.appendChild(e);

            dom.appendChild(rootEle);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                // send DOM to file
                tr.transform(new DOMSource(dom), 
                                     new StreamResult(new FileOutputStream("settings.xml")));

            } catch (TransformerException te) {
                System.out.println(te.getMessage());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        } catch (ParserConfigurationException pce) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
        }
    }
    
    public void start() {
        view.initUI();
        
        if (selectedDir != null) {
            loadDb();
        }
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

    public void loadDb() {
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
    
    public void selectDataDir(File selectedDir) {
        
        this.selectedDir = selectedDir;
        
        loadDb();
        
        saveSettings();
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



