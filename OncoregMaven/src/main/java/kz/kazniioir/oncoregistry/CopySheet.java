package kz.kazniioir.oncoregistry;

import kz.kazniioir.oncoregistry.OncoException;
import java.io.*;
import java.util.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;

public class CopySheet {

    private File file;
    HSSFWorkbook workbook;
    HSSFWorkbook workbookTarget;

    public CopySheet(File file) {
        this.file = file;
    }

    public void copySheet() throws IOException, OncoException {
        System.out.println("   <<< copy started of file = " + file.getName());
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(file);
            String nameOfNewFile = file.getParent() + "\\" + file.getName().substring(0, (file.getName().length() - 4)) + "_np.xls";
            System.out.println("new name of file  = " + nameOfNewFile);
            File newFile = new File(nameOfNewFile);
            //FileInputStream fisOfNewFile = new FileInputStream(newFile);

            workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
            workbookTarget = new HSSFWorkbook();

            if (1 == 0) {
                return;
            }

            int numberOfSheets = workbook.getNumberOfSheets();

            // copy all sheets
            for (int sheetNum = 0; sheetNum < numberOfSheets; sheetNum++) {
                HSSFSheet sheetSource = workbook.getSheetAt(sheetNum);
                int totalRows = sheetSource.getPhysicalNumberOfRows();
                String nameOfSheet = sheetSource.getSheetName();
                HSSFSheet sheetCloned = workbookTarget.createSheet(nameOfSheet);

                // filling List of Rows
                List<HSSFRow> rows = new ArrayList<>();
                for (int i = 0; i < totalRows; i++) {
                    HSSFRow row = sheetSource.getRow(i);
                    if (row != null) {
                        rows.add(row);
                    }
                }

                for (int i = 0; i < rows.size(); i++) {
                    HSSFRow row = rows.get(i);
                    HSSFRow hSSFRowCr = sheetCloned.createRow(i);
                    //setting height of row
                    if (row != null) {
                        hSSFRowCr.setHeight(row.getHeight());
                    }

                    //setting width of column
                    if (i == 0) {
                        for (int cellNum = 0; cellNum < 1; cellNum++) {
                            sheetCloned.setColumnWidth(cellNum, sheetSource.getColumnWidth(cellNum));
                            //sheetCloned.setDefaultColumnStyle(cellNum, sheetSource.getColumnStyle(cellNum));
                        }
                    }

                    // copy of rows to the new sheet
                    copyByCell(row, hSSFRowCr, i);
                }
            }

            fos = new FileOutputStream(newFile);
            workbookTarget.write(fos);
            System.out.println("copy of file = " + file.getName() + "..... succefully finished >>>");
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    private void copyByCell(HSSFRow row, HSSFRow hSSFRowCr, int rowCurrentNum) throws OncoException {

        for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
            try {
                HSSFCell cell = (HSSFCell) row.getCell(cellNum);
                HSSFCell cellCreated = hSSFRowCr.createCell(cellNum);
                // if (cell != null) {
                // закоментировал, так как потом проблема с форматами чисел появляются
//                    CellStyle newStyle = workbookTarget.createCellStyle();
//                    newStyle.cloneStyleFrom(cell.getCellStyle());
//                    cellCreated.setCellStyle(newStyle);
                //  }
                if (cell == null) {
                    cellCreated.setCellType(Cell.CELL_TYPE_BLANK);
                } else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    cellCreated.setCellType(Cell.CELL_TYPE_BLANK);
                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    cellCreated.setCellValue(cell.getStringCellValue());
                } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    cellCreated.setCellValue(cell.getNumericCellValue());
                } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                    cellCreated.setCellValue(cell.getCellFormula());
                } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                    cellCreated.setCellValue(cell.getBooleanCellValue());
                } else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
                    cellCreated.setCellValue(cell.getErrorCellValue());
                } else {
                    throw new OncoException("Неизвестный тип у ячейки!");
                }
            } catch (NullPointerException ex) {
                System.out.println("--------------" + "row: " + (rowCurrentNum + 1) + ", cell: " + ((char) (cellNum + 1 + 64)));
            }
        }
        // System.out.println("");
    }
}
