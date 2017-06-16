package kz.kazniioir.oncoregistry.db;

import java.sql.Connection;
import java.sql.ResultSet;

public class ConnectionPoolTest {

    public static void main(String[] argv) {
        String dburl = "jdbc:mysql://localhost:3306/oncoregistry";
        String driver = "com.mysql.jdbc.Driver";
        String sUser = "root";
        String sPwd = "elsieltc";
        java.sql.PreparedStatement pstmt;
        String select_sql = "select id, name from region";
        ResultSet rs;
        DatabaseConnectionPool dbConnectionPool;
        // create database connection pool
        try {
            dbConnectionPool = DatabaseConnectionPool.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Connection c1, c2, c3, c4, c5, c6;
        c1 = dbConnectionPool.getConnection();
        c2 = dbConnectionPool.getConnection();
        c3 = dbConnectionPool.getConnection();
        c5 = dbConnectionPool.getConnection();
        c5 = dbConnectionPool.getConnection();
        dbConnectionPool.freeConnection(c5);
        c6 = dbConnectionPool.getConnection();
        try {
            pstmt = c6.prepareStatement(select_sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("output value =>" + rs.getString(1) + "  "
                        + rs.getString(2));
            }
            rs.close();
            pstmt.close();
            c1.setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        // return first connection to the pool
        dbConnectionPool.freeConnection(c1);
        // release resources
        dbConnectionPool.destroy();
    }
}
