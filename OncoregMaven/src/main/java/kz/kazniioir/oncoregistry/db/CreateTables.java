package kz.kazniioir.oncoregistry.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.hibernate.cfg.annotations.ResultsetMappingSecondPass;

public class CreateTables {

    
    private static boolean DEBUG = false;

    public void createTable(String sql) {
        executeSql(sql, Action.CREATE);
    }

    public void dropTable(String name) {
        executeSql("drop table " + name, Action.DROP);
    }

    public void selectRowsFromTable(String table, String columns, String condition) {
        String sql = String.format("select %s from %s where 1=1 %s ",
                columns, table, condition == null || condition.trim().isEmpty() ? "" : " and " + condition.trim());
        executeSql(sql, Action.SELECT);
    }

    public void insertRowIntoTable(String table, String columns, String values) {
        String sql = String.format("insert into %s (%s) values (%s) ", table, columns, values);
        executeSql(sql, Action.INSERT);
    }

    public void deleteRowsInTable(String table, String condition) {
        String sql = String.format("delete from %s where 1=1 %s ",
                table, condition == null || condition.trim().isEmpty() ? "" : " and " + condition.trim());
        executeSql(sql, Action.DELETE);
    }

    public void updateRowsInTable(String table, String updateSet, String condition) {
        String sql = String.format("update %s set %s where 1=1 %s ",
                table, updateSet, condition == null || condition.trim().isEmpty() ? "" : " and " + condition.trim());
        executeSql(sql, Action.UPDATE);
    }

    public void executeSql(String sql, Action action) {
        Connection conn = null;
        Statement stmt = null;
        int records = 0;
        String strForConsole = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnectionPool.getInstance().getConnection();
            switch (action) {
                case CREATE:
                    strForConsole = "Creating table in given database...";
                    break;
                case DROP:
                    strForConsole = "Droping table in given database...";
                    break;
                case SELECT:
                    strForConsole = "Selecting rows in given table...";
                    break;
                case INSERT:
                    strForConsole = "Inserting row in given table...";
                    break;
                case UPDATE:
                    strForConsole = "Updating row in given table...";
                    break;
                case DELETE:
                    strForConsole = "Deleting row in given table...";
                    break;
            }
            System.out.println("\n\t" + strForConsole);
            stmt = conn.createStatement();
            if (action == action.SELECT) {
                rs = stmt.executeQuery(sql);
                records = printSelect(rs);
            } else {
                records = stmt.executeUpdate(sql);
            }
            System.out.println("Задействовано " + records + " строк. Result positive :)");
            //System.out.println("Created table in given database...");
        } catch (SQLException se) {
            System.out.println("Result negative (:");
            printException(se);
        } catch (Exception e) {
            System.out.println("Result negative (:");
            printException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se) {
                printException(se);
            }
            try {
                DatabaseConnectionPool.getInstance().freeConnection(conn);
            } catch (SQLException se) {
                printException(se);
            }
        }
        System.out.println("Goodbye!");
    }

    void fillTable(String[] insertSql) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DatabaseConnectionPool.getInstance().getConnection();
            System.out.println("Insert Rows in given tabale");
            stmt = conn.createStatement();
            for (String string : insertSql) {
                System.out.println("Workin with insert: " + string);
                stmt.addBatch(string);
            }
            stmt.executeBatch();
            System.out.println("Insetion finished ...");
        } catch (SQLException e) {
            printException(e);
        } catch (Exception e) {
            printException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                printException(e);
            }
            try {
                DatabaseConnectionPool.getInstance().freeConnection(conn);
            } catch (SQLException e) {
                printException(e);
            }
        }
        System.out.println("Goodbye!");
    }

    private int printSelect(ResultSet rs) throws SQLException {
        int records = 0;
        int columnCount = rs.getMetaData().getColumnCount();
        for (int colNum = 1; colNum < columnCount + 1; colNum++) {
            if (colNum == 1) {
                System.out.print("\t");
            }
            System.out.print("" + rs.getMetaData().getColumnName(colNum) + "\t");
            if (colNum == columnCount) {
                System.out.println("");
            }
        }
        while (rs.next()) {
            records++;
            for (int colNum = 1; colNum < columnCount + 1; colNum++) {
                if (colNum == 1) {
                    System.out.print("row_" + records + "\t" + rs.getString(colNum) + "\t");
                } else {
                    System.out.print(rs.getString(colNum) + "\t");
                }
            }
            System.out.println("");
        }
        return records;
    }

    private void printException(Exception ex) {
        System.err.println(ex.getMessage());
        if (DEBUG) {
            ex.printStackTrace();
        }
    }

   
}
