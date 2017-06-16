package kz.kazniioir.oncoregistry.form2200;

import java.awt.geom.Arc2D;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import kz.kazniioir.oncoregistry.LocalizationSelected;
import kz.kazniioir.oncoregistry.OncoException;
import kz.kazniioir.oncoregistry.OutputFile;
import kz.kazniioir.oncoregistry.Parse;
import kz.kazniioir.oncoregistry.ParseDefault;
import kz.kazniioir.oncoregistry.RowOnco;
import kz.kazniioir.oncoregistry.db.DbConnection;
import kz.kazniioir.oncoregistry.form2100.PrMorb2100;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Parse2200 extends ParseDefault {

  
    Sheet sheet;
    private Map<String, Integer> localizationMap = getLocalizationMap();

    public Parse2200(File file) {
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
            Workbook workBook = WorkbookFactory.create(file);
            sheet = workBook.getSheetAt(0);
        } catch (Exception ex) {
            Logger.getLogger(Parse2200.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void parseFile() {
        int rows = sheet.getLastRowNum();
        int cols = 6;
        for (int r = 1; r < rows; r++) {
            Row row = sheet.getRow(r);
            if (!isRowWithData(row)) {
                continue;
            }

            PrMorb2200 prMorbidity = new PrMorb2200();

            int year = 2004;

            Cell cell = row.getCell(1, Row.CREATE_NULL_AS_BLANK);
            prMorbidity.setRegion((int) cell.getNumericCellValue());

            cell = row.getCell(3, Row.CREATE_NULL_AS_BLANK);
            prMorbidity.setYear((int) cell.getNumericCellValue());

            cell = row.getCell(4, Row.CREATE_NULL_AS_BLANK);
            prMorbidity.setCodeIcdNum(localizationMap.get(cell.getStringCellValue()));

            cell = row.getCell(5, Row.CREATE_NULL_AS_BLANK);
            if (cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                prMorbidity.setValue((int) cell.getNumericCellValue());
            }

            listOfParsedRows.add(prMorbidity);

            //System.out.println(prMorbidity);

            // System.out.println();
        }


    }

    @Override
    public void normalizeLocalizations() throws OncoException {
        System.out.println("size before: " + listOfParsedRows.size());
        int year = 2004;
        List<RowOnco> listOfParsedRowsNew = new ArrayList<>();

        List<PrMorb2200> listOfParsedRowsPrMorb2200 = new ArrayList<>();

        PrMorb2200 prMorbidityAll = null;
        PrMorb2200 prMorbidityOther = null;
        PrMorb2200 prMorbidityLympAndKrovet = null;

        int valueForOther = 0;


        for (int i = 0; i < listOfParsedRows.size(); i++) {
            // for (RowOnco rowOnco : listOfParsedRows) {
            PrMorb2200 prMorb = (PrMorb2200) listOfParsedRows.get(i);

            if (year != prMorb.getYear()) {
                year = prMorb.getYear();
                try {
                    prMorbidityOther = (PrMorb2200) prMorbidityAll.clone();
                    prMorbidityOther.setCodeIcdNum(LocalizationSelected.drugie);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Parse2200.class.getName()).log(Level.SEVERE, null, ex);
                }
                prMorbidityOther.setValue(prMorbidityAll.getValue() - valueForOther);
                listOfParsedRowsNew.add(prMorbidityOther);
                valueForOther = 0;
            }


            if (prMorb.getCodeIcdNum() == LocalizationSelected.vse) {
                prMorbidityAll = prMorb;
            } else {
                valueForOther += prMorb.getValue();
            }

            if (prMorb.getCodeIcdNum() == LocalizationSelected.limphomi) {
                prMorbidityLympAndKrovet = prMorb;
                prMorbidityLympAndKrovet.setCodeIcdNum(LocalizationSelected.limphat_krovet_tkani);
            }

            if (prMorb.getCodeIcdNum() == LocalizationSelected.leukemiya) {
                prMorbidityLympAndKrovet.setValue(prMorbidityLympAndKrovet.getValue() + prMorb.getValue());
            }


            if (i == (listOfParsedRows.size() - 1)) {

                try {
                    prMorbidityOther = (PrMorb2200) prMorbidityAll.clone();
                    prMorbidityOther.setCodeIcdNum(LocalizationSelected.drugie);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Parse2200.class.getName()).log(Level.SEVERE, null, ex);
                }
                prMorbidityOther.setValue(prMorbidityAll.getValue() - valueForOther);
                listOfParsedRowsNew.add(prMorbidityOther);
            }
        }

        listOfParsedRows.addAll(listOfParsedRowsNew);

        List<RowOnco> newListTemp = new ArrayList<>();

        for (int i = 0; i < listOfParsedRows.size(); i++) {
            PrMorb2200 prMorb = (PrMorb2200) listOfParsedRows.get(i);
            if (prMorb.getCodeIcdNum() != LocalizationSelected.leukemiya) {
                newListTemp.add(prMorb);
            }
        }

        for (RowOnco rowOnco : newListTemp) {
            listOfParsedRowsPrMorb2200.add((PrMorb2200) rowOnco);
        }

        Collections.sort(listOfParsedRowsPrMorb2200);

        listOfParsedRows.clear();

        for (PrMorb2200 rowOnco : listOfParsedRowsPrMorb2200) {
            listOfParsedRows.add(rowOnco);
        }

        for (RowOnco rowOnco : listOfParsedRows) {
            System.out.println("rowOnco = " + rowOnco);
        }

        System.out.println("size after: " + listOfParsedRows.size());
    }

    @Override
    public void validateParse() throws OncoException {
        List<PrMorb2200> listTocheck = new ArrayList<>();
        int year = 2004;
        for (int i = 0; i < listOfParsedRows.size(); i++) {
            PrMorb2200 prMorb = (PrMorb2200) listOfParsedRows.get(i);
            if (year != prMorb.getYear()) {
                validatePart(listTocheck);
                listTocheck = new ArrayList<>();
                year = prMorb.getYear();

            }
            listTocheck.add(prMorb);

            if (i == (listOfParsedRows.size() - 1)) {
                validatePart(listTocheck);
            }

        }
    }

    @Override
    public void writeOutputFile(OutputFile ouputFile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void writeOutputFileOlap(OutputFile ouputFile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void writeOutputDBOlap() {
      throw new UnsupportedOperationException("Olap table is not necessary!!!"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void writeOutputDBSimpleTable() {
         Connection conn = null;
        Statement stmt = null;
        Object value;
        System.out.println("   !!! writing to DB Simple table ");
        try {
            conn = DbConnection.getConnection();
            //STEP 4: Execute a query
            System.out.println("Insert Rows in given table");
            stmt = conn.createStatement();
            for (int i = 0, rowCurrent = 0; i < listOfParsedRows.size(); i++) {
                PrMorb2200 parsedRow = (PrMorb2200) listOfParsedRows.get(i);
                String insertRow = String.format(""
                        + " INSERT INTO morbiditysimple2200 ( "
                        + " id, "
                        + " region_id, "
                        + " year, "
                        + " localization_id, "
                        + " value) "
                        + " VALUE (null, %s, %s, %s, '%s');",
                        parsedRow.getRegion(),
                        parsedRow.getYear(),
                        parsedRow.getCodeIcdNum(),
                        parsedRow.getValue());

                // System.out.println(j + ") Workin with insert: " + insertRow);
                //System.out.println("stmt.execute(insertRow):" + stmt.executeUpdate(insertRow));
                stmt.addBatch(insertRow);
            }
            int[] resInt = stmt.executeBatch();
            int sum = 0;
            for (int j : resInt) {
                sum += j;
            }
            System.out.print("were inserted " + sum + " rows");

            System.out.println(". Insertion finished ...");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }

    @Override
    public int getNumberOfRowsCouldBeParsed() throws OncoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getFileName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isRowWithData(Row row) {
        Cell cell = row.getCell(4);
        return localizationMap.containsKey(cell.getStringCellValue());
    }

    private Map<String, Integer> getLocalizationMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("всего", 1);
        map.put("пищевода", 4);
        map.put("желудка", 5);
        map.put("ободочной кишки", 6);
        map.put("прямой кишки, ректосигм. соединения, ануса", 7);
        map.put("трахеи, бронхов, легкого", 11);
        map.put("меланома кожи", 13);
        map.put("молочной железы", 16);
        map.put("шейки матки", 17);
        map.put("предстательной железы", 21);
        map.put("злокачественные лимфомы", 41);
        map.put("лейкемии", 42);
        return map;
    }

    private void validatePart(List<PrMorb2200> listTocheck) throws OncoException {
        int vseVal = 0;
        int otherVal = 0;
        
        for (int i = 0; i < listTocheck.size(); i++) {
            PrMorb2200 prMorb2200 = listTocheck.get(i);

            if (prMorb2200.getCodeIcdNum() == LocalizationSelected.vse) {
                vseVal = prMorb2200.getValue();
            } else {
                otherVal += prMorb2200.getValue();
            }
        }

        if (vseVal != otherVal) {
            throw new OncoException("сумма не бьет: " + listTocheck.get(0) + "величины: " + vseVal + ", " + otherVal);
        }
    }
     class Morbidity2000Olap {

        int region;
        int year;
        int localization;
         int value;
    }
}
