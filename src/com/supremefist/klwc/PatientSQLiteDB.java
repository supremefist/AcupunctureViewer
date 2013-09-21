package com.supremefist.klwc;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class PatientSQLiteDB {
    private File dir = null;
    
    public PatientSQLiteDB(File newDirName) {
        dir = newDirName;
    }
    
    public void read() {
        Connection c = null;
        File coreFilename = new File(dir, "base.sqlite");
        
        try {
          Class.forName("org.sqlite.JDBC");
          c = DriverManager.getConnection("jdbc:sqlite:" + coreFilename.getAbsolutePath());
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
}
