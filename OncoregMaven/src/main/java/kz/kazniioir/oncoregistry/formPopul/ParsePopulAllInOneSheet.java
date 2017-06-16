package kz.kazniioir.oncoregistry.formPopul;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kz.kazniioir.oncoregistry.OncoException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ParsePopulAllInOneSheet extends ParsePopulAbstr {

    public ParsePopulAllInOneSheet(File file, int year) throws IOException, InvalidFormatException {
        super(file, year);
        if (year == 2008) {
            firstRow = 2;
            lastRow = 370;
        } else if (year == 2009 || (year > 2010)) {
            firstRow = 2;
            lastRow = 290;
        } else if (year == 2010) {
            firstRow = 2;
            lastRow = 426;
        }
    }

    @Override
    public void parseFile() {

//            if(!sheetName.startsWith("02")){
//                continue;
//            }
        Sheet sheet = workbook.getSheet("00");
        int region = 0;
        for (int rowNum = firstRow; rowNum < lastRow + 1; rowNum++) {
            if (rowNum == 290) {
                System.out.println("debug point");
            }
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }
            if (isRowIsStartNewRegion(row)) {
                try {
                    region = defineRegion(row);
                    continue;
                } catch (OncoException ex) {
                    Logger.getLogger(ParsePopulAllInOneSheet.class.getName()).log(Level.SEVERE, null, ex);
                    break;
                }
            }

            if (!isRowWithData(row)) {
                continue;
            }
            System.out.println("---" + row.getCell(0).getStringCellValue());

            PopulationTransfer rowOnco = null;
            rowOnco = new PopulationTransfer();
            rowOnco.setYear(year);
            rowOnco.setRegion(region);
            for (int colNum = firstColumn; colNum < lastColumn + 1; colNum++) {
                try {

                    Cell cell = row.getCell(colNum, Row.CREATE_NULL_AS_BLANK);
                    setValue(colNum, cell, rowOnco);
                } catch (OncoException ex) {
                    Logger.getLogger(ParsePopulManySheets.class.getName()).log(Level.SEVERE, null, ex);
                    break;
                }
            }
            listOfParsedRows.add(rowOnco);
        }

    }

    private boolean isRowIsStartNewRegion(Row row) {
        int orientirColumn = 0;
        if (row.getCell(orientirColumn).getCellType() == Cell.CELL_TYPE_STRING) {
            String value = row.getCell(orientirColumn).getStringCellValue().trim();
            if (value.contains("_")) {
                return true;
            }
        }
        return false;
    }

    private int defineRegion(Row row) throws OncoException {
        int orientirColumn = 0;
        int result = 0;
        if (row.getCell(orientirColumn).getCellType() == Cell.CELL_TYPE_STRING) {
            String value = row.getCell(orientirColumn).getStringCellValue().trim();
            if (value.contains("_")) {
                return Integer.parseInt(value.replace("_", ""));
            }
        }
        throw new OncoException("регион не определен: " + result);
    }

     @Override
    public void normalizeLocalizations() throws OncoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}
