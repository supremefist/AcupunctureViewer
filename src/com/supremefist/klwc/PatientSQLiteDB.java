package com.supremefist.klwc;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PatientSQLiteDB {
    private File dir = null;
    
    public PatientSQLiteDB(File newDirName) {
        dir = newDirName;
    }
    
    public void read() {
        Connection adminConnection = null;
        Connection acupunctureConnection = null;
        Connection layoutConnection = null;
        
        File administrationFilename = new File(dir, "Administration.sqlite");
        File acupunctureFilename = new File(dir, "Acupuncture.sqlite");
        File layoutFilename = new File(dir, "Layout.sqlite");
        
        try {
          Class.forName("org.sqlite.JDBC");
          adminConnection = DriverManager.getConnection("jdbc:sqlite:" + administrationFilename.getAbsolutePath());
          acupunctureConnection = DriverManager.getConnection("jdbc:sqlite:" + acupunctureFilename.getAbsolutePath());
          layoutConnection = DriverManager.getConnection("jdbc:sqlite:" + layoutFilename.getAbsolutePath());
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
        
        System.out.println("Opened database successfully");
        
        try {
            Statement statement = adminConnection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            
//            statement.executeUpdate("drop table if exists person");
//            statement.executeUpdate("create table person (id integer, name string)");
//            statement.executeUpdate("insert into person values(1, 'leo')");
//            statement.executeUpdate("insert into person values(2, 'yui')");
            
            ResultSet rs = statement.executeQuery("SELECT * FROM sqlite_master WHERE type='table';");
            //ResultSet rs = statement.executeQuery("SELECT * FROM dbname.sqlite_master WHERE type='table';");
            while(rs.next())
            {
              // read the result set
              System.out.println("name = " + rs.getString("name"));
            }
        }
        catch(SQLException e)
        {
          // if the error message is "out of memory", 
          // it probably means no database file is found
          System.err.println(e.getMessage());
        }
        finally
        {
          try
          {
            if(adminConnection != null) {
                adminConnection.close();
            }
          }
          catch(SQLException e)
          {
            // connection close failed.
            System.err.println(e);
          }
        }
    }
}
