package kz.kazniioir.oncoregistry.formPopul;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import kz.kazniioir.oncoregistry.OncoException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ParsePopulManySheets extends ParsePopulAbstr {

    private String[] sheetNames;

    public ParsePopulManySheets(File file, int year) throws IOException, InvalidFormatException {
        super(file, year);
        //mapAges = super.fillAgeCode();
        if (year == 2004 || year == 2005) {
            sheetNames = getSheetNames();
        } else {
            sheetNames = getSheetNamesOnlyNumbers();
        }
        if (year == 2004) {
            firstRow = 6;
            lastRow = 31;
        } else if (year == 2005) {
            firstRow = 5;
            lastRow = 30;
        } else if (year == 2006) {
            firstRow = 5;
            lastRow = 34;
        }  else if (year == 2007) {
            firstRow = 4;
            lastRow = 27;
        }else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void parseFile() {
        for (String sheetName : sheetNames) {
//            if(!sheetName.startsWith("02")){
//                continue;
//            }
            Sheet sheet = workbook.getSheet(sheetName);
            System.out.println("sheet " + sheetName + ": " + sheet);
            for (int rowNum = firstRow; rowNum < lastRow + 1; rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                if (!isRowWithData(row)) {
                    continue;
                }
                System.out.println("---" + row.getCell(0).getStringCellValue());

                PopulationTransfer rowOnco = null;
                rowOnco = new PopulationTransfer();
                rowOnco.setYear(year);
                rowOnco.setRegion(Integer.parseInt(sheetName.substring(0, 2)));
                for (int colNum = firstColumn; colNum < lastColumn + 1; colNum++) {
                    try {

                        Cell cell = row.getCell(colNum, Row.CREATE_NULL_AS_BLANK);


                        setValue(colNum, cell, rowOnco);

                    } catch (OncoException ex) {
                        Logger.getLogger(ParsePopulManySheets.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                listOfParsedRows.add(rowOnco);
            }
        }
    }

   

    private String[] getSheetNames() {
        return new String[]{
            "01_астана",
            "02_алматы",
            "03_акмола",
            "04_актюбинск",
            "05_алматин",
            "06_атырау",
            "07_вко",
            "08_жамбыл",
            "09_зко",
            "10_караганда",
            "11_костанай",
            "12_кызылорда",
            "13_мангистау",
            "14_павлодар",
            "15_ско",
            "16_юко",
            "17_РК"
        };
    }

    private static String[] getSheetNamesOnlyNumbers() {
        String[] retStr = new String[17];
        for (int i = 1; i < retStr.length + 1; i++) {
            String string = "";
            if (i < 10) {
                string += "0";
            }
            string += i;
            retStr[i - 1] = string;
        }
        return retStr;
    }

   
}
