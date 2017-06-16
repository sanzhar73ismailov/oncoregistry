package kz.kazniioir.oncoregistry.report.morbidity;

public class MorbidityReport {

    private ReportTable table;
    public static final int COEF_MORBIDITY = 100_000;
    
    private String query;
    private PrintAble printerObj;

    public enum ColumnName {

        YEAR, REGION, SEX
    };

    public MorbidityReport(int tableNo, String tableName, String query, String rowGeneralName, int baseOfDevision) {

        this.query = query;
        table = new ReportTable(tableNo, tableName, this.query, rowGeneralName, baseOfDevision);
        printerObj = new PrintMorbidity(table);
    }

    public void printTableToConsole() {
        printerObj.printTableToConsole();

    }

    public void printTableToWordFile() {
        System.out.println("\tPrinting WORD file....<<<");
        printerObj.printTableToWordFile();
        System.out.println("\t>>>Finish Printing WORD file....");
    }
    
     public void printTableToExcelFile() {
        System.out.println("\tPrinting EXCEL file....<<<");
        printerObj.printTableToExcelFile();
        System.out.println("\t>>>Finish Printing EXCEL file....");
    }

    public ReportTable getTable() {
        return table;
    }

  

    public void setQuery(String query) {
        this.query = query;
    }

    public static String[] getColumnNames(ColumnName colname) {
        switch (colname) {
            case YEAR:
                String[] colNames = new String[20];
                for (int i = 0; i < 10; i++) {
                    colNames[i * 2] = 2004 + (i) + " abs";
                    colNames[i * 2 + 1] = 2004 + i + " заб";
                }
                return colNames;
            case REGION:
                throw new UnsupportedOperationException();
        }

        return null;
    }

    public static void main(String[] args) {
        String[] arr = getColumnNames(ColumnName.YEAR);
        for (String string : arr) {
            System.out.println("string = " + string);
        }
    }
}
