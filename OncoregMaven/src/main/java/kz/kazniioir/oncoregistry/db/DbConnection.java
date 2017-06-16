package kz.kazniioir.oncoregistry.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnection {

    public final static String DRIVER = "com.mysql.jdbc.Driver";
    //public final static String HOST = "192.168.10.80";
    public final static String HOST = "localhost";
    
    public final static String PORT = "3306";
    public final static String USER = "root";
    public final static String PASSWORD = "elsieltc";
    public final static String DB_NAME = "oncoregistry";

    public static Connection getConnection() {
        return getConnection(DB_NAME);
    }
    public static Connection getConnection(String host,String port, String dbName, String user, String password) {
         Connection connection = null;

        try {
            Class.forName(DRIVER).newInstance();
            connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/",
                    host, port) + dbName, user, password);

            if (!connection.isClosed()) {
                //System.out.println("Successfully connected to " + "MySQL server using TCP/IP...");
            }

        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnection(String dbName) {
        return getConnection(HOST, PORT, dbName, USER, PASSWORD);
    }

    public static void main2(String[] args) {
        //ResourceBundle rb = ResourceBundle.getBundle("")
   //     Properties p = new Properties();
     //   File f = new File("hibernate.properties");
       // System.out.println("f = " + f.getAbsolutePath());
        //p.load(new FileInputStream("hibernate.properties"));
        
        System.out.println(System.getProperties().getProperty("hibernate.dialect"));
        
    }
    public static void main(String[] args) {
        Connection con = getConnection();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select * from localization");
//            rs = st.executeQuery("select * from table1");
            while (rs.next()) {
                System.out.println("" + rs.getString(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }
}
