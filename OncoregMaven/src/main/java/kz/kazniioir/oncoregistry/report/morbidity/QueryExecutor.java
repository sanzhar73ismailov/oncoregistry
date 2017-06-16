package kz.kazniioir.oncoregistry.report.morbidity;

import kz.kazniioir.oncoregistry.main.TestReport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import kz.kazniioir.oncoregistry.db.Action;
import kz.kazniioir.oncoregistry.db.CreateTables;
import kz.kazniioir.oncoregistry.db.DatabaseConnectionPool;

public class QueryExecutor {

    private String query;
    private Set<String> columnNames;
    private Set<String> rowNames;
    List<RowTempObject> rowTempObjectList;
    String tempVarForCheckingOfNewRowForTable = "";
    private int baseOfDivision;

    public QueryExecutor(String query, int baseOfDivision) {
        this.query = query;
        this.baseOfDivision = baseOfDivision;
        columnNames = new LinkedHashSet<>();
        // rowNames = new LinkedHashSet<>();
        rowTempObjectList = new ArrayList<>(200);
    }

    private boolean isNecessaryCreateNewRowTempObject(String rowName) {
        if (!tempVarForCheckingOfNewRowForTable.equals(rowName)) {
            tempVarForCheckingOfNewRowForTable = rowName;
            return true;
        }
        return false;
    }

    public synchronized List<ReportRow> executeQuery() {
        List<ReportRow> listReportRow = new ArrayList<>(200);
        System.out.println("\texecuteQuery started <<<<");
        DatabaseConnectionPool databaseConnectionPool = null;
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {

            databaseConnectionPool = DatabaseConnectionPool.getInstance();
            con = databaseConnectionPool.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(this.query);
            fillingTempListArray(rs);
            String rowNamePrev = "";
            ReportRow repRow = null;
            int rowNum = 0;

            for (RowTempObject rowTempObj : rowTempObjectList) {
                if (isNecessaryCreateNewRowTempObject(rowTempObj.rowName)) {
                    repRow = new ReportRow();
                    repRow.setRowName(rowTempObj.rowName);
                    repRow.setNumber(rowNum++);
                    listReportRow.add(repRow);
                    // System.out.println("repRow = " + repRow);
                }
                IndicatorPart indicatorPart = new IndicatorPart(this.baseOfDivision);
                indicatorPart.setValueAbsolute(rowTempObj.morbAbs);
                indicatorPart.setValuePopulation(rowTempObj.population);
                indicatorPart.setValueOn100_000PopByFormula();
                columnNames.add(rowTempObj.colName);
                repRow.add(indicatorPart);

            }

//            int k1 = 0;
//            for (ReportRow reportRow : listReportRow) {
//                System.out.println(k1++ +")reportRow = " + reportRow);
//            }



        } catch (Exception ex) {
            Logger.getLogger(QueryExecutor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (databaseConnectionPool != null) {
                databaseConnectionPool.freeConnection(con);
            }
        }
        return listReportRow;
    }

    public Set<String> getColumnNames() {
        return columnNames;
    }

    public static void main(String[] args) {
        CreateTables ct = new CreateTables();
        ct.executeSql(StringQueries.queryMorbT1, Action.SELECT);
    }

    private void fillingTempListArray(ResultSet rs) throws SQLException {
        while (rs.next()) {
            RowTempObject row = new RowTempObject();
            row.rowName = rs.getString("row_name");
            row.colName = rs.getString("col_name");
            row.morbAbs = rs.getInt("morbidity_abs");
            row.population = rs.getInt("population");
            rowTempObjectList.add(row);
        }
    }

    class RowTempObject {

        String rowName;
        String colName;
        int morbAbs;
        int population;

        @Override
        public String toString() {
            return "RowTempObject{" + "rowName=" + rowName + ", colName=" + colName + ", morbAbs=" + morbAbs + ", population=" + population + '}';
        }
    }
}
