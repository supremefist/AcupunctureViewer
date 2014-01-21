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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PatientSQLiteDB {
    private File dir = null;

    Connection adminConnection = null;
    Connection acupunctureConnection = null;
    Connection layoutConnection = null;

    File administrationFilename;
    File acupunctureFilename;
    File layoutFilename;

    private Map<String, AcupuncturePoint> points;

    public List<SidedAcupuncturePoint> buildPointsFromString(
            String pointsString, String channel) {
        List<SidedAcupuncturePoint> points = new ArrayList<SidedAcupuncturePoint>();

        String[] parts = pointsString.split("-");

        for (String p : parts) {
            String[] components = p.split("\\*");
            
            if (components.length == 2) {
                try {
                    SidedAcupuncturePoint newPoint = new SidedAcupuncturePoint(this.points.get(channel
                            + components[0]), Integer.parseInt(components[1])); 
                    points.add(newPoint);
                }
                catch (NumberFormatException n) {
                    System.out.println("Couldn't parse: " + p);
                }
            }

        }

        return points;
    }

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

        if (!administrationFilename.exists() || !acupunctureFilename.exists()
                || !layoutFilename.exists()) {
            return false;
        } else {
            return true;
        }
    }

    public Map<String, AcupuncturePoint> getAcupuncturePoints() {
        Map<String, AcupuncturePoint> points = new HashMap<String, AcupuncturePoint>();

        try {
            Statement acupunctureStatement = acupunctureConnection
                    .createStatement();
            acupunctureStatement.setQueryTimeout(30); // set timeout to 30 sec.

            // List all patients in database
            ResultSet rs = acupunctureStatement
                    .executeQuery("select * from pointslocationvirtueel_content;");

            while (rs.next()) {
                // read the result set
                String name = rs.getString("c10name");
                String englishName = rs.getString("c9englishname");
                String location = rs.getString("c11location");
                String function = rs.getString("c13func");
                String needlingMethod = rs.getString("c14needling_method");
                String indication = rs.getString("c12indication");
                String contraIndication = rs.getString("c15contraindication");
                String application = rs.getString("c16localapp");
                String miscellaneous = rs.getString("c18misc");
                String commonName = rs.getString("c24common_name");
                int number = rs.getInt("c1pointnum");
                AcupuncturePoint p = new AcupuncturePoint(name, englishName,
                        location, function, needlingMethod, indication,
                        contraIndication, application, miscellaneous,
                        commonName, number);

                points.put(commonName, p);
            }

        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (acupunctureConnection != null) {
                    acupunctureConnection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }

        this.points = points;

        return points;
    }

    @SuppressWarnings("unchecked")
    public List<Patient> getPatients() {
        List<Patient> patients = new ArrayList<Patient>();

        try {
            Statement adminStatement = adminConnection.createStatement();
            adminStatement.setQueryTimeout(30); // set timeout to 30 sec.

            // List all patients in database
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
                Date dateOfRegistration = new Date(
                        rs.getLong("Inschrijfdatum") * 1000L);
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

            // Populate patients with consultations
            for (Patient p : patients) {
                List<Consultation> consultations = new ArrayList<Consultation>();
                adminStatement = adminConnection.createStatement();
                rs = adminStatement
                        .executeQuery("select * from Consulten where PatientNummer = "
                                + p.getId() + ";");

                while (rs.next()) {
                    // For each consultation for patient with PatientNumber
                    // p.getId()
                    int id = rs.getInt("ConsultID");
                    Date dateOfConsultation = new Date(
                            rs.getLong("ConsultDatum") * 1000L);
                    String history = rs.getString("Anamnese");

                    List<SidedAcupuncturePoint> points = new ArrayList<SidedAcupuncturePoint>();
                    
                    points.addAll(buildPointsFromString(
                            rs.getString("Blaas7L"), "GB"));
                    
                    Collections.sort(points);
                    
                    Consultation c = new Consultation(id, dateOfConsultation,
                            history, points);

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
