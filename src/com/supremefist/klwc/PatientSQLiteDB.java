package com.supremefist.klwc;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

public class PatientSQLiteDB {
    private File dir = null;
    
    Connection adminConnection = null;
    Connection acupunctureConnection = null;
    Connection layoutConnection = null;
    
    File administrationFilename;
    File acupunctureFilename;
    File layoutFilename;

    public PatientSQLiteDB(File newDirName) {
        dir = newDirName;
        
        administrationFilename = new File(dir, "Administration.sqlite");
        acupunctureFilename = new File(dir, "Acupuncture.sqlite");
        layoutFilename = new File(dir, "Layout.sqlite");
    }
    
    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            adminConnection = DriverManager.getConnection("jdbc:sqlite:"
                    + administrationFilename.getAbsolutePath());
            acupunctureConnection = DriverManager.getConnection("jdbc:sqlite:"
                    + acupunctureFilename.getAbsolutePath());
            layoutConnection = DriverManager.getConnection("jdbc:sqlite:"
                    + layoutFilename.getAbsolutePath());
            
            System.out.println("Opened database successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    
    public Boolean ready() {
        
        if (!administrationFilename.exists() || !acupunctureFilename.exists() || !layoutFilename.exists()) {
            return false;
        }
        else {
            return true;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Patient> getPatients() {
        List<Patient> patients = new ArrayList<Patient>();

        try {
            Statement adminStatement = adminConnection.createStatement();
            adminStatement.setQueryTimeout(30); // set timeout to 30 sec.

            ResultSet rs = adminStatement
                    .executeQuery("select * from Patients;");

            while (rs.next()) {
                // read the result set
                int id = rs.getInt("PatientID");
                String firstName = rs.getString("Voornaam");
                String surName = rs.getString("Achternaam");
                String street = rs.getString("Straat");
                String houseNumber = rs.getString("Huisnummer");
                String residence = rs.getString("Woonplaats");
                String country = rs.getString("Land");
                Date dateOfBirth = new Date(rs.getLong("Gebdatum") * 1000L);
                Date dateOfRegistration = new Date(rs.getLong("Inschrijfdatum") * 1000L);
                String phoneNumber = rs.getString("Telefoonnummer");
                String emailAddress = rs.getString("EMailadres");
                String postCode = rs.getString("Postcode");
                String sex = rs.getString("Geslacht");
                String bsn = rs.getString("BSN");

                Patient p = new Patient(id, firstName, surName, street,
                        houseNumber, residence, country, dateOfBirth,
                        dateOfRegistration, phoneNumber, emailAddress,
                        postCode, sex, bsn);
                
                patients.add(p);
            }
            
        
            for (Patient p: patients) {
                List<Consultation> consultations = new ArrayList<Consultation>();
                adminStatement = adminConnection.createStatement();
                rs = adminStatement
                        .executeQuery("select * from Consulten where PatientNummer = " + p.getId() + ";");

                while (rs.next()) {
                    // read the result set
                    int id = rs.getInt("ConsultID");
                    Date dateOfConsultation = new Date(rs.getLong("ConsultDatum") * 1000L);
                    String history = rs.getString("Anamnese");
                    
                    Consultation c = new Consultation(id, dateOfConsultation, history);
                    
                    consultations.add(c);
                    
                }
                
                Collections.sort(consultations);
                p.setConsultations(consultations);
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (adminConnection != null) {
                    adminConnection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
        
        Collections.sort(patients);
        
        return patients;
    }
}
