package kz.kazniioir.oncoregistry;

import kz.kazniioir.oncoregistry.ServiceMorbidity1000;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class FileSystem {

    private File file;
    private Workbook workBook;
    private String sheetName;
    private Sheet sheet;
    private int lastRow;
    Service service;

    public FileSystem(Service service) throws IOException, OncoException, InvalidFormatException {
        this.service = service;
        this.file = service.getSourceFileForParsing();
        this.sheetName = service.getSheetNameOfSourceFile();
        this.lastRow = service.getLastRow();
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
        workBook = WorkbookFactory.create(file);
        sheet = workBook.getSheet(sheetName);
        if(sheet == null){
            throw new OncoException("в файле " + file.getName() +" нет листа с именем " + sheetName );
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Workbook getWorkBook() {
        return workBook;
    }

    public void setWorkBook(Workbook workBook) {
        this.workBook = workBook;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public int getLastRow() {
        return lastRow;
    }

    public void setLastRow(int lastRow) {
        this.lastRow = lastRow;
    }

    public int getNumberOfColumns(Sheet sheet) {
        int rows = sheet.getPhysicalNumberOfRows();
        int cols = 0;
        org.apache.poi.ss.usermodel.Row row;

        int tmp;
        // This trick ensures that we get the data properly even if it doesn't start from first few rows
        for (int i = 0; i < 10 || i < rows; i++) {
            row = sheet.getRow(i);
            if (row != null) {
                tmp = row.getLastCellNum();
                //System.out.println("row.getLastCellNum()="+row.getLastCellNum());
                // System.out.println(i + ", cols = " + tmp);
                if (tmp > cols) {
                    cols = tmp;
                }
            }
        }
        return cols;
    }
}
