package kz.kazniioir.oncoregistry.report.morbidity;

import java.util.List;
import java.util.Set;

public class ReportTable {

    private String name;
    private int number;
    private String rowGeneralName;
    private String nameWithNumber;
    private Set<String> colNames;
    private List<ReportRow> listReportRows;
    private QueryExecutor queryExecutor;
    private String baseNameForReportFileWithouExtension;
    private String shortVariantBaseNameForReportFileWithouExtension;
    private String superShortVariantBaseNameForReportFileWithouExtension;
    

    public ReportTable(String query) {
        this(0, null, query, null, 100000);
    }

    public ReportTable(int number, String name, String query, String rowGeneralName,  int baseOfDivision) {
        this.number = number;
        this.name = name;
        this.rowGeneralName = rowGeneralName;
        queryExecutor = new QueryExecutor(query, baseOfDivision);
        listReportRows = queryExecutor.executeQuery();
        colNames = queryExecutor.getColumnNames();
        nameWithNumber = "Таблица " + this.number + ". " + this.name;
        String tableNoAsStr = number < 10 ? "0" + this.number : "" + this.number;
        baseNameForReportFileWithouExtension = "Таблица" + tableNoAsStr + " " + this.name;
        shortVariantBaseNameForReportFileWithouExtension = createShortVariantBaseNameForReportFileWithouExtension();
        superShortVariantBaseNameForReportFileWithouExtension = "Таблица" + tableNoAsStr + " " + this.name.split(" ")[0];
    }

    public String getBaseNameForReportFileWithouExtension() {
        return baseNameForReportFileWithouExtension;
    }
    
    public String createShortVariantBaseNameForReportFileWithouExtension() {
        String[] strArray = baseNameForReportFileWithouExtension.split(" ");
        StringBuilder sb = new StringBuilder(strArray[0]);

        for (int i = 1; i < strArray.length; i++) {
            if (strArray[i].length() >4 && !strArray[i].contains(")")) {
                sb.append("_" + strArray[i].substring(0, 4));
            } else {
                sb.append("_" + strArray[i]);
            }
            
        }
        return sb.toString();
    }

    public String getSuperShortVariantBaseNameForReportFileWithouExtension() {
        return superShortVariantBaseNameForReportFileWithouExtension;
    }
    
    
    

    public String getShortVariantBaseNameForReportFileWithouExtension() {
        return shortVariantBaseNameForReportFileWithouExtension;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getNameWithNumber() {
        return nameWithNumber;
    }

    public void setNameWithNumber(String nameWithNumber) {
        this.nameWithNumber = nameWithNumber;
    }

    public Set<String> getColNames() {
        return colNames;
    }

    public List<ReportRow> getListReportRows() {
        return listReportRows;
    }

    public void setListReportRows(List<ReportRow> listReportRows) {
        this.listReportRows = listReportRows;
    }

    public String getRowGeneralName() {
        return rowGeneralName;
    }

    public void setRowGeneralName(String rowGeneralName) {
        this.rowGeneralName = rowGeneralName;
    }

    public QueryExecutor getQueryExecutor() {
        return queryExecutor;
    }

    public void setQueryExecutor(QueryExecutor queryExecutor) {
        this.queryExecutor = queryExecutor;
    }
}
