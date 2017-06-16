package kz.kazniioir.oncoregistry.formPopul;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import kz.kazniioir.oncoregistry.OncoException;
import kz.kazniioir.oncoregistry.OutputFile;
import kz.kazniioir.oncoregistry.ParseDefault;
import kz.kazniioir.oncoregistry.RowOnco;
import kz.kazniioir.oncoregistry.db.DbConnection;
import kz.kazniioir.oncoregistry.form1000.PrMorb1000;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public abstract class ParsePopulAbstr extends ParseDefault {

    protected Workbook workbook;
    protected File file;
    protected int firstRow;
    protected int lastRow;
    protected int firstColumn = 0;
    protected int lastColumn = 9;
    protected Map<String, Integer> mapAges = fillAgeCode();

    public ParsePopulAbstr(File file, int year) throws IOException, InvalidFormatException {
        this.file = file;
        workbook = WorkbookFactory.create(this.file);
        this.year = year;
    }

    @Override
    public void normalizeLocalizations() throws OncoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    @Override
    public void validateParse() throws OncoException {
//        int sum85HigherCalculate = sum85HigherCalculate();
//        int sum70HigherCalculate = sum70HigherCalculate();
        for (RowOnco rowOnco : listOfParsedRows) {
            PopulationTransfer item = (PopulationTransfer) rowOnco;

            if (item.getValueAllSexBoth() != (item.getValueAllSexFeMale() + item.getValueAllSexMale())) {
                throw new OncoException("сумма мужчин и женщин не совпадает");
            }
            if (item.getValueCitySexBoth() != (item.getValueCitySexFeMale() + item.getValueCitySexMale())) {
                throw new OncoException("сумма мужчин и женщин не совпадает");
            }
            if (item.getValueVillageSexBoth() != (item.getValueVillageSexFeMale() + item.getValueVillageSexMale())) {
                throw new OncoException("сумма мужчин и женщин не совпадает");
            }
            if (item.getValueAllSexBoth() != (item.getValueCitySexBoth() + item.getValueVillageSexBoth())) {
                throw new OncoException("сумма всех и сумма в городе и селе не совпадает");
            }
            if (item.getValueAllSexMale() != (item.getValueCitySexMale() + item.getValueVillageSexMale())) {
                throw new OncoException("сумма всех мужиков и сумма  мужиков в городе и селе не совпадает");
            }

            if (item.getValueAllSexFeMale() != (item.getValueCitySexFeMale() + item.getValueVillageSexFeMale())) {
                throw new OncoException("сумма всех женщин и сумма  мужиков в городе и селе не совпадает");
            }


        }
        for (int region = 1; region < 18; region++) {
            List<RowOnco> listRegion = new ArrayList<>();
            for (RowOnco rowOnco : listOfParsedRows) {
                PopulationTransfer item = (PopulationTransfer) rowOnco;
                if (item.getRegion() == region) {
                    listRegion.add(rowOnco);
                }
            }
            System.out.print("region=" + region);
            System.out.print(", listRegion.size()=" + listRegion.size());
            PopulationTransfer item = (PopulationTransfer) listRegion.get(0);
            System.out.println(", Region=" + item.getRegion());
            if (listRegion.size() > 0) {
                CheckPopulRows checkPopulRows = new CheckPopulRows(listRegion);
                checkPopulRows.checkValueSums(CheckPopulRows.CalVariant.enum70);
                if (year < 2008 || year == 2010) {
                    checkPopulRows.checkValueSums(CheckPopulRows.CalVariant.enum85);
                }
            }
        }

    }

    public void validateSmall() throws OncoException {
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
        Connection conn = null;
        Statement stmt = null;
        Object value;
        System.out.println("   !!! writing to DB file " + getFileName());
        try {
            conn = DbConnection.getConnection();
            //STEP 4: Execute a query
            System.out.println("Insert Rows in given table");
            stmt = conn.createStatement();
            for (int i = 0, rowCurrent = 0; i < listOfParsedRows.size(); i++) {
                PopulationTransfer parsedRow = (PopulationTransfer) listOfParsedRows.get(i);
                String insertRow = "";
                int sex = 0;
                int regionType = 0;
                for (int j = 0; j < 9; j++) {
                    switch (j) {
                        case 0:
                            sex = 3;
                            regionType = 3;
                            break;
                        case 1:
                            sex = 1;
                            regionType = 3;
                            break;
                        case 2:
                            sex = 2;
                            regionType = 3;
                            break;
                        case 3:
                            sex = 3;
                            regionType = 1;
                            break;
                        case 4:
                            sex = 1;
                            regionType = 1;
                            break;
                        case 5:
                            sex = 2;
                            regionType = 1;
                            break;
                        case 6:
                            sex = 3;
                            regionType = 2;
                            break;
                        case 7:
                            sex = 1;
                            regionType = 2;
                            break;
                        case 8:
                            sex = 2;
                            regionType = 2;
                            break;
                    }
                    // to insert only regionTypes all type (id=3)
                    if (regionType != 3) {
                        continue;
                    }

                    insertRow = String.format(""
                            + "INSERT INTO population "
                            + "(id,  year, region_id, sex_id, age_id, region_type_id,   value) "
                            + "VALUE (null, '%s', '%s', '%s', '%s' , '%s' , '%s' )",
                            parsedRow.getYear(),
                            parsedRow.getRegion(),
                            sex,
                            parsedRow.getAgeGroup(),
                            regionType,
                            parsedRow.getValueByIndex(j));

                    // System.out.println(j + ") Workin with insert: " + insertRow);
                    //System.out.println("stmt.execute(insertRow):" + stmt.executeUpdate(insertRow));
                    stmt.addBatch(insertRow);

                }

            }
            int[] resInt = stmt.executeBatch();
            int sum = 0;
            for (int j : resInt) {
                sum += j;
            }
            System.out.print("were inserted " + sum + " rows");

            System.out.println(". Insertion finished ...");
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try

        System.out.println(
                "Goodbye!");
    }

    @Override
    public void writeOutputDBSimpleTable() {
        Connection conn = null;
        Statement stmt = null;
        System.out.println("   !!! writing to DB file " + getFileName());
        try {
            conn = DbConnection.getConnection();
            //STEP 4: Execute a query
            System.out.println("Insert Rows in given table");
            stmt = conn.createStatement();
            for (int i = 0, rowCurrent = 0; i < listOfParsedRows.size(); i++) {
                PopulationTransfer parsedRow = (PopulationTransfer) listOfParsedRows.get(i);
                String insertRow = "";


                insertRow = String.format(""
                        + "INSERT INTO populationsimple ("
                        + " id, year, region_id, age_id, "
                        + " value_all_sex_both,"
                        + " value_all_sex_male,"
                        + " value_all_sex_female,"
                        + " value_city_sex_both,"
                        + " value_city_sex_male,"
                        + " value_city_sex_female,"
                        + " value_village_sex_both,"
                        + " value_village_sex_male,"
                        + " value_village_sex_female )"
                        + " VALUE (null, '%s', '%s', '%s', '%s' , '%s' , '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                        parsedRow.getYear(),
                        parsedRow.getRegion(),
                        parsedRow.getAgeGroup(),
                        parsedRow.getValueAllSexBoth(),
                        parsedRow.getValueAllSexMale(),
                        parsedRow.getValueAllSexFeMale(),
                        parsedRow.getValueCitySexBoth(),
                        parsedRow.getValueCitySexMale(),
                        parsedRow.getValueCitySexFeMale(),
                        parsedRow.getValueVillageSexBoth(),
                        parsedRow.getValueVillageSexMale(),
                        parsedRow.getValueVillageSexFeMale());
                // System.out.println("insertRow = " + insertRow);

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
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try

        System.out.println(
                "Goodbye!");
    }

    @Override
    public int getNumberOfRowsCouldBeParsed() throws OncoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getFileName() {
        return file.getName();
    }

    protected Map<String, Integer> fillAgeCode() {
        Map<String, Integer> map = new HashMap<>();
        map.put("все возраста", 10);
        map.put("все", 10);
        map.put("бар", 10);
        map.put("0-4", 20);
        map.put("5-9", 30);
        map.put("10-14", 40);
        map.put("15-17", 50);
        map.put("18-19", 60);
        map.put("15-19", 70);
        map.put("20-24", 80);
        map.put("25-29", 90);
        map.put("15-29", 100);
        map.put("30-34", 110);
        map.put("35-39", 120);
        map.put("40-44", 130);
        map.put("45-49", 140);
        map.put("50-54", 150);
        map.put("55-59", 160);
        map.put("60-64", 170);
        map.put("65-69", 180);
        map.put("70-74", 190);
        map.put("75-79", 200);
        map.put("80-84", 210);
        map.put("70 +", 220);
        map.put("70+", 220);
        map.put("70&+", 220);
        map.put("85 +", 230);
        map.put("85+", 230);
        map.put("85&+", 230);
        return map;
    }

    protected int getAgeCode(Object value) {
        if (!mapAges.containsKey(value.toString().toLowerCase())) {
            return 0;
        }
        return this.mapAges.get(value.toString().toLowerCase());
    }

    protected boolean containsAgeKey(Object value) {
        return mapAges.containsKey(value.toString().toLowerCase());
    }

    @Override
    public boolean isRowWithData(Row row) {
        int orientirColumn = 0;
        int orientirRow;

        if (row == null) {
            return false;
        }


        if (row.getCell(orientirColumn) == null) {
            return false;
        }

        if (row.getCell(orientirColumn).getCellType() != Cell.CELL_TYPE_STRING) {
            return false;
        }

        if (!this.containsAgeKey(row.getCell(orientirColumn).getStringCellValue().toLowerCase())) {
            return false;
        }
        return true;
    }

    protected void setValue(int columnNumber, Cell cell, PopulationTransfer populationTransfer) throws OncoException {
        int cellType = cell.getCellType();
        Object value = "";
        if (cellType == Cell.CELL_TYPE_BLANK) {
            //value = "BLANK";
        } else if (cellType == Cell.CELL_TYPE_STRING) {
            value = cell.getStringCellValue().trim();
        } else if (cellType == Cell.CELL_TYPE_NUMERIC) {
            value = cell.getNumericCellValue();
        } else if (cellType == Cell.CELL_TYPE_FORMULA) {
            value = cell.getNumericCellValue();
        } else {
            try {
                throw new kz.kazniioir.oncoregistry.OncoException("unknown type");


            } catch (OncoException ex) {
                Logger.getLogger(ParsePopulManySheets.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        switch (columnNumber) {
            case 0:
                populationTransfer.setAgeGroup(getAgeCode(value));
                break;
            case 1:
                populationTransfer.setValueAllSexBoth((int) (double) value);
                break;
            case 2:
                populationTransfer.setValueAllSexMale((int) (double) value);
                break;
            case 3:
                populationTransfer.setValueAllSexFeMale((int) (double) value);
                break;
            case 4:
                populationTransfer.setValueCitySexBoth((int) (double) value);
                break;
            case 5:
                populationTransfer.setValueCitySexMale((int) (double) value);
                break;
            case 6:
                populationTransfer.setValueCitySexFeMale((int) (double) value);
                break;
            case 7:
                populationTransfer.setValueVillageSexBoth((int) (double) value);
                break;
            case 8:
                populationTransfer.setValueVillageSexMale((int) (double) value);
                break;
            case 9:
                populationTransfer.setValueVillageSexFeMale((int) (double) value);
                break;
        }


    }
}
