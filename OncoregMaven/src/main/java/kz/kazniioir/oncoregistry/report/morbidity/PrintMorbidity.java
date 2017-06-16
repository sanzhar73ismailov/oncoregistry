package kz.kazniioir.oncoregistry.report.morbidity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class PrintMorbidity implements PrintAble {

    private ReportTable table;
    List<ReportRow> listReportRows;
    private String reportFolder = "report\\";
    private String nameOfSecColumn;

    public PrintMorbidity() {
        this(null);
        
    }

    public PrintMorbidity(ReportTable table) {
        this.table = table;
        listReportRows = table.getListReportRows();
        if(table.getName().startsWith("Забол")){
            nameOfSecColumn = "заб";
        }else if(table.getName().startsWith("Смерт")){
         nameOfSecColumn = "смерт";
        }      
    }

    @Override
    public void printTableToConsole() {
        System.out.println(table.getNameWithNumber());
        System.out.println("строки: " + table.getRowGeneralName());

        for (ReportRow listReportRows : table.getListReportRows()) {
            for (IndicatorPart indicatorPart : listReportRows.getListIndicatorParts()) {
                System.out.print(indicatorPart + "\t");
            }
            System.out.println();
        }
    }

    @Override
    public void printTableToWordFile() {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph paragraph = doc.createParagraph();

        XWPFRun run = paragraph.createRun();
        run.setText(table.getNameWithNumber());
        run.setFontSize(12);
        run.addBreak();

        XWPFTable tableWord = doc.createTable();
        XWPFTableRow tableOneRowOne = tableWord.getRow(0);
        tableOneRowOne.getCell(0).setText(table.getRowGeneralName());
        //creating head

        String [] colNames = table.getColNames().toArray(new String [0]);


        for (int i = 0; i < colNames.length; i++) {
            tableOneRowOne.addNewTableCell().setText(colNames[i]);
            tableOneRowOne.addNewTableCell();
        }

        XWPFTableRow secRowOfHead = tableWord.createRow();
        secRowOfHead.getCell(0).setText("");
        for (int i = 0; i < colNames.length; i++) {
            int col = i * 2;
            secRowOfHead.getCell(col + 1).setText("абс");
            secRowOfHead.getCell(col + 2).setText(nameOfSecColumn);
        }

        for (int i = 0; i < listReportRows.size(); i++) {

            XWPFTableRow rowOne = tableWord.createRow();
            ReportRow reportRow = listReportRows.get(i);
            int j = 0;
            for (IndicatorPart tableCell : reportRow.getListIndicatorParts()) {
                int col = j * 2;
                if (j == 0) {
                    rowOne.getCell(0).setText(reportRow.getRowName());
                }
                j++;
                XWPFTableCell cell1 = rowOne.getCell(col + 1);
                cell1.setText(tableCell.getValueAbsolute() + "");
//                    cellParagraph = cell1.getParagraphs().get(0);
//                    XWPFRun cellRun = cellParagraph.createRun();
//                    cellRun.setFontSize(10);
//                    cellRun.setText(tableCell.getAbsValue() + "");
                XWPFTableCell cell2 = rowOne.getCell(col + 2);
                cell2.setText(String.format("%.1f", roundResult(tableCell.getValueOn100_000Pop(), 1)));
            }
        }
        try {
            //String fileNameToPrinted = getShortVarianletable.getBaseNameForReportFileWithouExtension();
            //String fileNameToPrinted = reportFolder + table.getShortVariantBaseNameForReportFileWithouExtension() + ".docx";
            String fileNameToPrinted = reportFolder + table.getSuperShortVariantBaseNameForReportFileWithouExtension()+ ".docx";
            doc.write(new FileOutputStream(new File(fileNameToPrinted)));
            System.out.println("file printed, name: " + fileNameToPrinted);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PrintMorbidity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PrintMorbidity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void printTableToExcelFile() {
        String fileNameToPrinted = reportFolder + table.getSuperShortVariantBaseNameForReportFileWithouExtension() + ".xlsx";
        org.apache.poi.ss.usermodel.Workbook wb = new XSSFWorkbook();
        org.apache.poi.ss.usermodel.Sheet sheet = wb.createSheet("sheet");
        try {
            FileOutputStream fos = new FileOutputStream(new File(fileNameToPrinted));

            List<String> colNames = new ArrayList<>(table.getColNames());


            Row rowForColname = sheet.createRow(0);
            for (int i = 0; i < colNames.size(); i++) {
                int colNumber = i * 2 + 1;
                Cell cell = rowForColname.createCell(colNumber);
                cell.setCellValue(colNames.get(i) + " abs");
                cell = rowForColname.createCell(colNumber + 1);
                cell.setCellValue(colNames.get(i) + " " + nameOfSecColumn);
            }


            for (int i = 1; i < listReportRows.size() + 1; i++) {
                ReportRow parsedRow = listReportRows.get(i - 1);
                Row row = sheet.createRow(i);
                Cell cell = row.createCell(0);
                cell.setCellValue(parsedRow.getRowName());

                for (int j = 0; j < parsedRow.size(); j++) {
                    int colNumber = j * 2 + 1;
                    IndicatorPart indPart = parsedRow.get(j);
                    cell = row.createCell(colNumber);
                    cell.setCellValue(indPart.getValueAbsolute());
                    cell = row.createCell(colNumber + 1);
                    //cell.setCellValue(String.format("%.2f",indPart.getValueOn100_000Pop()));
                    cell.setCellValue(roundResult(indPart.getValueOn100_000Pop(), 1));
                }
            }
            wb.write(fos);
            System.out.println("\tfile printed, name: " + fileNameToPrinted);
        } catch (Exception ex) {
            Logger.getLogger(PrintMorbidity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ReportTable getTable() {
        return table;
    }

    public void setTable(ReportTable table) {
        this.table = table;
    }
    
     public double roundResult(double d, int precise) {
        double newDouble = new BigDecimal(d).setScale(precise, RoundingMode.HALF_UP).doubleValue();
        return newDouble;
    }
}
