package kz.kazniioir.oncoregistry.form2100;

import kz.kazniioir.oncoregistry.db.DbConnection;
import kz.kazniioir.oncoregistry.form1000.PrMorb1000;
import kz.kazniioir.oncoregistry.FileSystem;
import kz.kazniioir.oncoregistry.LocalizationMap;
import kz.kazniioir.oncoregistry.OncoException;
import kz.kazniioir.oncoregistry.OutputFile;
import kz.kazniioir.oncoregistry.Parse;
import kz.kazniioir.oncoregistry.RowOnco;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import kz.kazniioir.oncoregistry.LocalizationSelected;
import kz.kazniioir.oncoregistry.ParseDefault;
import kz.kazniioir.oncoregistry.form1000.ParseForm07;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class Parse2100 extends ParseDefault {

    private FileSystem fileSystem;
    int rows;
    int cols;
    Sheet sheet;
    RowOnco classParse;
    int numberOfRowsCouldBeParsed;
    protected int region;

    public Parse2100(int year, int region, FileSystem fileSystem, RowOnco classParse) throws OncoException {
        this.year = year;
        this.region = region;
        this.fileSystem = fileSystem;
        this.classParse = classParse;
        initialWork();
    }

    private void initialWork() throws OncoException {
        sheet = fileSystem.getSheet();
        listOfParsedRows = new ArrayList<RowOnco>();
        //rows = sheet.getPhysicalNumberOfRows(); // No of rows
        rows = 38; // No of rows
        cols = fileSystem.getNumberOfColumns(sheet); // No of columns
        //cols = 17; // No of columns
        numberOfRowsCouldBeParsed = defineNumberOfRowsCouldBeParsed();
        System.out.println("<<<<<  Работа с файлом: " + getFileName());
        System.out.println("rows всего = " + rows
                + ", cols = " + cols
                + ", rows для парсинга = " + numberOfRowsCouldBeParsed);
    }

    public void setListOfParsedRows(List<RowOnco> listOfParsedRows) {
        this.listOfParsedRows = listOfParsedRows;
    }

    private int defineNumberOfRowsCouldBeParsed() throws OncoException {
        int numberOfRows = 0;
        int countParsed = 0;
        int columnForOrientation;

        if (year == 2004 || year == 2005) {
            columnForOrientation = 4;
        } else {
            columnForOrientation = 1;
        }

        for (int r = 0; r < rows; r++) {
            if (year == 2004 || year == 2005) {
                if (countParsed == 29) {
                    break;
                }
            } else {
                if (countParsed == 30) {
                    break;
                }
            }
            org.apache.poi.ss.usermodel.Row row = sheet.getRow(r);
            if (row != null) {
                Cell cell = row.getCell(columnForOrientation);
                if (cell != null) {
                    if (cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            numberOfRows = (int) cell.getNumericCellValue();
                            countParsed++;
                        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            try {
                                numberOfRows = Integer.parseInt(cell.getStringCellValue());
                                countParsed++;
                            } catch (NumberFormatException ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    }
                }
            }
        }
        if (countParsed == 0) {
            throw new OncoException("что то не так, количество парсированных не работает!");
        }
        return numberOfRows;
    }

    @Override
    public void parseFile() {
        org.apache.poi.ss.usermodel.Row row = null;
        Cell cell = null;
        boolean finishIterate = false;
        if (classParse instanceof PrMorb2100) {
            PrMorb2100 primaryMorbidityPrevious = null;
            try {
                OUT:
                for (int r = 0; r < rows; r++) {
                    row = sheet.getRow(r);

                    if (row != null) {
                        if (!isRowWithData(row)) {
                            continue;
                        }
                        PrMorb2100 primaryMorbidity = null;
                        primaryMorbidity = new PrMorb2100();
                        primaryMorbidity.setYear(year);
                        primaryMorbidity.setRegion(region);
                        primaryMorbidity.setFileName(fileSystem.getFile().getName());

                        if (year == 2004 || year == 2005) {
                            primaryMorbidity.setRegisteredInTheStartOfYear(-1);
                            primaryMorbidity.setVzyatoNaUchetThisYear_WithBeforeDefinedDiagnosis(-1);
                            primaryMorbidity.setFrom3Group_DetectedDuringProf01And02Stage(-1);
                            primaryMorbidity.setStruckOffTheRegisterAll(-1);
                            primaryMorbidity.setStruckOffTheRegisterGoOut(-1);
                            primaryMorbidity.setStruckOffTheRegisterDiagNotConfirmed(-1);
                            primaryMorbidity.setStruckOffTheRegisterNoInfo(-1);
                            primaryMorbidity.setStruckOffTheRegisterWithDiagBazalioma(-1);
                            primaryMorbidity.setStruckOffTheRegisterDiedFromAnotherDisease(-1);
                        }

                        for (int columnNumber = 0; columnNumber < cols; columnNumber++) {
                            cell = row.getCell(columnNumber, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);
                            if (year == 2004 || year == 2005) {

                                setValueOld(columnNumber, cell, primaryMorbidity);
                            } else {
                                setValueNew(columnNumber, cell, primaryMorbidity);
                            }
                        }
                        listOfParsedRows.add(primaryMorbidity);
                    }
//                    System.out.println("");
                }
//                for (RowOnco primaryMorbidity : listOfParsedRows) {
//                    System.out.println("" + primaryMorbidity);
//                }
            } catch (Exception ioe) {
                if (cell != null) {
                    System.out.println("column: " + cell.getColumnIndex() + "row: " + cell.getRowIndex());
                }
                ioe.printStackTrace();
            }
        }
    }

    @Override
    public boolean isRowWithData(Row row) {

        int orientirColumn;
        int orientirRow;

        if (year == 2004 || year == 2005) {
            orientirColumn = 5;
            orientirRow = 37;
        } else {
            orientirColumn = 2;
            orientirRow = 35;
        }
        if (row == null) {
            return false;
        }

        if (row.getRowNum() != orientirRow) {
            if (row.getCell(orientirColumn) == null) {
                return false;
            }

            if (row.getCell(orientirColumn).getCellType() != Cell.CELL_TYPE_STRING) {
                return false;
            }

            if (!row.getCell(orientirColumn).getStringCellValue().startsWith("С")) {
                return false;
            }

        }
        return true;
    }

    @Override
    public void validateParse() throws OncoException {
        if (year == 2004 || year == 2005) {
            if (listOfParsedRows.size() != 29) {
                throw new OncoException("количество строк для 2004 и 2005 годов неправильное, должно быть 29, а на самом деле: " + listOfParsedRows.size());
            }
        } else {
            if (listOfParsedRows.size() != 30) {
                throw new OncoException("количество строк для 2006-2013 годов неправильное, должно быть 30, а на самом деле: " + listOfParsedRows.size());
            }
        }
        if (numberOfRowsCouldBeParsed != listOfParsedRows.size()) {
            throw new OncoException("количество строк неправильное, должно быть "
                    + numberOfRowsCouldBeParsed
                    + ", а фактически "
                    + listOfParsedRows.size());
        }
        checkVzyatoNaUchetThisYear_FirstTime();
        checkFrom3Group_DetectedDuringProfAll();
        checkFrom3Group_DiagnosoConfirmedMorfologically();
        checkFrom3Group_HadStage1and2();
        checkFrom3Group_HadStage3();
        checkFrom3Group_HadStage4();
        checkRegisteredInTheEndOfYearAll();
        checkRegisteredInTheEndOfYear5YearsAndMore();

        if (year > 2005) {
            checkRegisteredInTheStartOfYear();
            checkVzyatoNaUchetThisYear_WithBeforeDefinedDiagnosis();
            checkFrom3Group_DetectedDuringProf01And02Stage();
            checkStruckOffTheRegisterAll();
            checkStruckOffTheRegisterGoOut();
            checkStruckOffTheRegisterDiagNotConfirmed();
            checkStruckOffTheRegisterNoInfo();
            checkStruckOffTheRegisterWithDiagBazalioma();
            checkStruckOffTheRegisterDiedFromAnotherDisease();
        }
        for (RowOnco row : listOfParsedRows) {
            PrMorb2100 pMorbidity = (PrMorb2100) row;
            if (pMorbidity.getCodeIcdNum() == 0
                    || pMorbidity.getCodeIcdNum() < -1) {
                throw new OncoException("численный шифр заболевания у строки неправильный: " + pMorbidity.getCodeIcdNum());
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
        Connection conn = null;
        Statement stmt = null;
        Object value;
        System.out.println("   !!! writing to DB Olap file " + getFileName());
        try {
            conn = DbConnection.getConnection();
            System.out.println("Insert Rows in given table");
            stmt = conn.createStatement();
            for (int i = 0; i < listOfParsedRows.size(); i++) {
                PrMorb2100 parsedRow = (PrMorb2100) listOfParsedRows.get(i);
                if (parsedRow.isYounger14Years()) {
                    continue;
                }
                Morbidity2000Olap morbidity2000Olap = null;
                int stages = 5;
                for (int stageVariant = 1; stageVariant <= stages; stageVariant++) {
                    morbidity2000Olap = new Morbidity2000Olap();
                    morbidity2000Olap.region = parsedRow.getRegion();
                    morbidity2000Olap.year = parsedRow.getYear();
                    morbidity2000Olap.localization = parsedRow.getCodeIcdNum();
                    morbidity2000Olap.stage = stageVariant;
                    switch (stageVariant) {
                        case 1:
                            morbidity2000Olap.value = parsedRow.getFrom3Group_HadStage1and2();
                            break;
                        case 2:
                            morbidity2000Olap.value = parsedRow.getFrom3Group_HadStage3();
                            break;
                        case 3:
                            morbidity2000Olap.value = parsedRow.getFrom3Group_HadStage4();
                            break;
                        case 4:
                            morbidity2000Olap.value = parsedRow.getVzyatoNaUchetThisYear_FirstTime()
                                    - (parsedRow.getFrom3Group_HadStage1and2()
                                    + parsedRow.getFrom3Group_HadStage3()
                                    + parsedRow.getFrom3Group_HadStage4());
                            break;
                        case 5:
                            morbidity2000Olap.value = parsedRow.getVzyatoNaUchetThisYear_FirstTime();
                            break;
                        default:
                            throw new UnsupportedOperationException("непонятный вариант стадии: " + stageVariant);
                    }

                    String insertRow = String.format(""
                            + " INSERT INTO morbidityolap2100 ( "
                            + " id, "
                            + " region_id, "
                            + " year, "
                            + " localization_id, "
                            + " stage_id, "
                            + " value)"
                            + " VALUE (null, %s, %s, %s, %s, %s);",
                            morbidity2000Olap.region,
                            morbidity2000Olap.year,
                            morbidity2000Olap.localization,
                            morbidity2000Olap.stage,
                            morbidity2000Olap.value);

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
    public void writeOutputDBSimpleTable() {
        Connection conn = null;
        Statement stmt = null;
        Object value;
        System.out.println("   !!! writing to DB Simple file " + getFileName());
        try {
            conn = DbConnection.getConnection();
            //STEP 4: Execute a query
            System.out.println("Insert Rows in given table");
            stmt = conn.createStatement();
            for (int i = 0, rowCurrent = 0; i < listOfParsedRows.size(); i++) {
                PrMorb2100 parsedRow = (PrMorb2100) listOfParsedRows.get(i);
                String insertRow = String.format(""
                        + " INSERT INTO morbiditysimple2100 ( "
                        + " id, "
                        + " region_id, "
                        + " year, "
                        + " localization_id, "
                        + " filename, "
                        + " younger14Years, "
                        + " registeredInTheStartOfYear, "
                        + " vzyatoNaUchetThisYear_WithBeforeDefinedDiagnosis, "
                        + " vzyatoNaUchetThisYear_FirstTime, "
                        + " from3Group_DetectedDuringProfAll, "
                        + " from3Group_DetectedDuringProf01And02Stage, "
                        + " from3Group_DiagnosoConfirmedMorfologically, "
                        + " from3Group_HadStage1and2, "
                        + " from3Group_HadStage3, "
                        + " from3Group_HadStage4, "
                        + " struckOffTheRegisterAll, "
                        + " struckOffTheRegisterGoOut, "
                        + " struckOffTheRegisterDiagNotConfirmed, "
                        + " struckOffTheRegisterNoInfo, "
                        + " struckOffTheRegisterWithDiagBazalioma, "
                        + " struckOffTheRegisterDiedFromAnotherDisease, "
                        + " registeredInTheEndOfYearAll, "
                        + " registeredInTheEndOfYear5YearsAndMore) "
                        + " VALUE (null, %s, %s, %s, '%s', %s, %s, %s, %s, %s, %s, %s, %s, "
                        + " %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);",
                        parsedRow.getRegion(),
                        parsedRow.getYear(),
                        parsedRow.getCodeIcdNum(),
                        parsedRow.getFileName(),
                        parsedRow.isYounger14Years(),
                        parsedRow.getRegisteredInTheStartOfYear(),
                        parsedRow.getVzyatoNaUchetThisYear_WithBeforeDefinedDiagnosis(),
                        parsedRow.getVzyatoNaUchetThisYear_FirstTime(),
                        parsedRow.getFrom3Group_DetectedDuringProfAll(),
                        parsedRow.getFrom3Group_DetectedDuringProf01And02Stage(),
                        parsedRow.getFrom3Group_DiagnosoConfirmedMorfologically(),
                        parsedRow.getFrom3Group_HadStage1and2(),
                        parsedRow.getFrom3Group_HadStage3(),
                        parsedRow.getFrom3Group_HadStage4(),
                        parsedRow.getStruckOffTheRegisterAll(),
                        parsedRow.getStruckOffTheRegisterGoOut(),
                        parsedRow.getStruckOffTheRegisterDiagNotConfirmed(),
                        parsedRow.getStruckOffTheRegisterNoInfo(),
                        parsedRow.getStruckOffTheRegisterWithDiagBazalioma(),
                        parsedRow.getStruckOffTheRegisterDiedFromAnotherDisease(),
                        parsedRow.getRegisteredInTheEndOfYearAll(),
                        parsedRow.getRegisteredInTheEndOfYear5YearsAndMore());

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
        return fileSystem.getFile().getName();
    }

    private void setValueNew(int columnNumber, Cell cell, PrMorb2100 primaryMorbidity) throws OncoException {
        int cellType = cell.getCellType();
        if (cellType == Cell.CELL_TYPE_BLANK) {
            //return;
        }
        switch (columnNumber) {
            case 0:
                if (cell.getStringCellValue().contains("до 14 лет")) {
                    primaryMorbidity.setYounger14Years(true);
                    primaryMorbidity.setLocalization("Злокачественные новообразования, в с е г о");
                } else {
                    primaryMorbidity.setLocalization(cell.getStringCellValue());
                }
                break;
            case 2:
                String codeValue = cell.getStringCellValue();
                primaryMorbidity.setCodeICD(codeValue);
                if (primaryMorbidity.getLocalizationMap().containsKey(codeValue)) {
                    primaryMorbidity.setCodeIcdNum(primaryMorbidity.getLocalizationMap().get(codeValue));
                } else {
                    throw new OncoException("нет такой локализации: " + codeValue);
                }
                break;
            case 3:
                primaryMorbidity.setRegisteredInTheStartOfYear((int) cell.getNumericCellValue());
                break;
            case 4:
                primaryMorbidity.setVzyatoNaUchetThisYear_WithBeforeDefinedDiagnosis((int) cell.getNumericCellValue());
                break;
            case 5:
                primaryMorbidity.setVzyatoNaUchetThisYear_FirstTime((int) cell.getNumericCellValue());
                break;
            case 6:
                primaryMorbidity.setFrom3Group_DetectedDuringProfAll((int) cell.getNumericCellValue());
                break;
            case 7:
                primaryMorbidity.setFrom3Group_DetectedDuringProf01And02Stage((int) cell.getNumericCellValue());
                break;
            case 8:
                primaryMorbidity.setFrom3Group_DiagnosoConfirmedMorfologically((int) cell.getNumericCellValue());
                break;
            case 9:
                primaryMorbidity.setFrom3Group_HadStage1and2((int) cell.getNumericCellValue());
                break;
            case 10:
                primaryMorbidity.setFrom3Group_HadStage3((int) cell.getNumericCellValue());
                break;
            case 11:
                primaryMorbidity.setFrom3Group_HadStage4((int) cell.getNumericCellValue());
                break;
            case 12:
                primaryMorbidity.setStruckOffTheRegisterAll((int) cell.getNumericCellValue());
                break;
            case 13:
                primaryMorbidity.setStruckOffTheRegisterGoOut((int) cell.getNumericCellValue());
                break;
            case 14:
                primaryMorbidity.setStruckOffTheRegisterDiagNotConfirmed((int) cell.getNumericCellValue());
                break;
            case 15:
                primaryMorbidity.setStruckOffTheRegisterNoInfo((int) cell.getNumericCellValue());
                break;
            case 16:
                primaryMorbidity.setStruckOffTheRegisterWithDiagBazalioma((int) cell.getNumericCellValue());
                break;
            case 17:
                primaryMorbidity.setStruckOffTheRegisterDiedFromAnotherDisease((int) cell.getNumericCellValue());
                break;
            case 18:
                primaryMorbidity.setRegisteredInTheEndOfYearAll((int) cell.getNumericCellValue());
                break;
            case 19:
                primaryMorbidity.setRegisteredInTheEndOfYear5YearsAndMore((int) cell.getNumericCellValue());
                break;
        }
    }

    private void setValueOld(int columnNumber, Cell cell, PrMorb2100 primaryMorbidity) throws OncoException {
        int cellType = cell.getCellType();
        if (cellType == Cell.CELL_TYPE_BLANK) {
            //return;
        }
        switch (columnNumber) {
            case 0:
                if (cell.getStringCellValue().contains("до 14 лет")) {
                    primaryMorbidity.setYounger14Years(true);
                    primaryMorbidity.setLocalization("Злокачественные новообразования, в с е г о");
                } else {
                    primaryMorbidity.setLocalization(cell.getStringCellValue().trim());
                }
                break;
            case 5:
                String codeValue = cell.getStringCellValue().trim();
                primaryMorbidity.setCodeICD(codeValue);
                if (primaryMorbidity.getLocalizationMap().containsKey(codeValue)) {
                    primaryMorbidity.setCodeIcdNum(primaryMorbidity.getLocalizationMap().get(codeValue));
                } else {
                    throw new OncoException("нет такой локализации: " + codeValue);
                }
                break;
            case 6:
                primaryMorbidity.setVzyatoNaUchetThisYear_FirstTime((int) cell.getNumericCellValue());
                break;
            case 10:
                primaryMorbidity.setFrom3Group_DetectedDuringProfAll((int) cell.getNumericCellValue());
                break;
            case 11:
                primaryMorbidity.setFrom3Group_DiagnosoConfirmedMorfologically((int) cell.getNumericCellValue());
                break;
            case 12:
                primaryMorbidity.setFrom3Group_HadStage1and2((int) cell.getNumericCellValue());
                break;
            case 13:
                primaryMorbidity.setFrom3Group_HadStage3((int) cell.getNumericCellValue());
                break;
            case 14:
                primaryMorbidity.setFrom3Group_HadStage4((int) cell.getNumericCellValue());
                break;
            case 15:
                primaryMorbidity.setRegisteredInTheEndOfYearAll((int) cell.getNumericCellValue());
                break;
            case 16:
                primaryMorbidity.setRegisteredInTheEndOfYear5YearsAndMore((int) cell.getNumericCellValue());
                break;
        }
    }

    public void checkRegisteredInTheStartOfYear() throws OncoException {
        int sumInTable = ((PrMorb2100) listOfParsedRows.get(0)).getRegisteredInTheStartOfYear();
        int sumCalc = 0;
        for (int i = 2; i < listOfParsedRows.size(); i++) {
            PrMorb2100 row = (PrMorb2100) listOfParsedRows.get(i);
            sumCalc += row.getRegisteredInTheStartOfYear();
        }
        if (sumInTable != sumCalc) {
            throw new OncoException("сумма не сходится для registeredInTheStartOfYear " + sumInTable + " " + sumCalc);
        }
    }

    public void checkVzyatoNaUchetThisYear_WithBeforeDefinedDiagnosis() throws OncoException {
        int sumInTable = ((PrMorb2100) listOfParsedRows.get(0)).getVzyatoNaUchetThisYear_WithBeforeDefinedDiagnosis();
        int sumCalc = 0;
        for (int i = 2; i < listOfParsedRows.size(); i++) {
            PrMorb2100 row = (PrMorb2100) listOfParsedRows.get(i);
            sumCalc += row.getVzyatoNaUchetThisYear_WithBeforeDefinedDiagnosis();
        }
        if (sumInTable != sumCalc) {
            throw new OncoException("сумма не сходится для vzyatoNaUchetThisYear_WithBeforeDefinedDiagnosis " + sumInTable + " " + sumCalc);
        }
    }

    public void checkVzyatoNaUchetThisYear_FirstTime() throws OncoException {
        int sumInTable = ((PrMorb2100) listOfParsedRows.get(0)).getVzyatoNaUchetThisYear_FirstTime();
        int sumCalc = 0;
        for (int i = 2; i < listOfParsedRows.size(); i++) {
            PrMorb2100 row = (PrMorb2100) listOfParsedRows.get(i);
            sumCalc += row.getVzyatoNaUchetThisYear_FirstTime();
        }
        if (sumInTable != sumCalc) {
            throw new OncoException("сумма не сходится для vzyatoNaUchetThisYear_FirstTime " + sumInTable + " " + sumCalc);
        }
    }

    public void checkFrom3Group_DetectedDuringProfAll() throws OncoException {
        int sumInTable = ((PrMorb2100) listOfParsedRows.get(0)).getFrom3Group_DetectedDuringProfAll();
        int sumCalc = 0;
        for (int i = 2; i < listOfParsedRows.size(); i++) {
            PrMorb2100 row = (PrMorb2100) listOfParsedRows.get(i);
            sumCalc += row.getFrom3Group_DetectedDuringProfAll();
        }
        if (sumInTable != sumCalc) {
            throw new OncoException("сумма не сходится для from3Group_DetectedDuringProfAll " + sumInTable + " " + sumCalc);
        }
    }

    public void checkFrom3Group_DetectedDuringProf01And02Stage() throws OncoException {
        int sumInTable = ((PrMorb2100) listOfParsedRows.get(0)).getFrom3Group_DetectedDuringProf01And02Stage();
        int sumCalc = 0;
        for (int i = 2; i < listOfParsedRows.size(); i++) {
            PrMorb2100 row = (PrMorb2100) listOfParsedRows.get(i);
            sumCalc += row.getFrom3Group_DetectedDuringProf01And02Stage();
        }
        if (sumInTable != sumCalc) {
            throw new OncoException("сумма не сходится для from3Group_DetectedDuringProf01And02Stage " + sumInTable + " " + sumCalc);
        }
    }

    public void checkFrom3Group_DiagnosoConfirmedMorfologically() throws OncoException {
        int sumInTable = ((PrMorb2100) listOfParsedRows.get(0)).getFrom3Group_DiagnosoConfirmedMorfologically();
        int sumCalc = 0;
        for (int i = 2; i < listOfParsedRows.size(); i++) {
            PrMorb2100 row = (PrMorb2100) listOfParsedRows.get(i);
            sumCalc += row.getFrom3Group_DiagnosoConfirmedMorfologically();
        }
        if (sumInTable != sumCalc) {
            throw new OncoException("сумма не сходится для from3Group_DiagnosoConfirmedMorfologically " + sumInTable + " " + sumCalc);
        }
    }

    public void checkFrom3Group_HadStage1and2() throws OncoException {
        int sumInTable = ((PrMorb2100) listOfParsedRows.get(0)).getFrom3Group_HadStage1and2();
        int sumCalc = 0;
        for (int i = 2; i < listOfParsedRows.size(); i++) {
            PrMorb2100 row = (PrMorb2100) listOfParsedRows.get(i);
            sumCalc += row.getFrom3Group_HadStage1and2();
        }
        if (sumInTable != sumCalc) {
            throw new OncoException("сумма не сходится для from3Group_HadStage1and2 " + sumInTable + " " + sumCalc);
        }
    }

    public void checkFrom3Group_HadStage3() throws OncoException {
        int sumInTable = ((PrMorb2100) listOfParsedRows.get(0)).getFrom3Group_HadStage3();
        int sumCalc = 0;
        for (int i = 2; i < listOfParsedRows.size(); i++) {
            PrMorb2100 row = (PrMorb2100) listOfParsedRows.get(i);
            sumCalc += row.getFrom3Group_HadStage3();
        }
        if (sumInTable != sumCalc) {
            throw new OncoException("сумма не сходится для from3Group_HadStage3 " + sumInTable + " " + sumCalc);
        }
    }

    public void checkFrom3Group_HadStage4() throws OncoException {
        int sumInTable = ((PrMorb2100) listOfParsedRows.get(0)).getFrom3Group_HadStage4();
        int sumCalc = 0;
        for (int i = 2; i < listOfParsedRows.size(); i++) {
            PrMorb2100 row = (PrMorb2100) listOfParsedRows.get(i);
            sumCalc += row.getFrom3Group_HadStage4();
        }
        if (sumInTable != sumCalc) {
            throw new OncoException("сумма не сходится для from3Group_HadStage4 " + sumInTable + " " + sumCalc);
        }
    }

    public void checkStruckOffTheRegisterAll() throws OncoException {
        int sumInTable = ((PrMorb2100) listOfParsedRows.get(0)).getStruckOffTheRegisterAll();
        int sumCalc = 0;
        for (int i = 2; i < listOfParsedRows.size(); i++) {
            PrMorb2100 row = (PrMorb2100) listOfParsedRows.get(i);
            sumCalc += row.getStruckOffTheRegisterAll();
        }
        if (sumInTable != sumCalc) {
            throw new OncoException("сумма не сходится для struckOffTheRegisterAll " + sumInTable + " " + sumCalc);
        }
    }

    public void checkStruckOffTheRegisterGoOut() throws OncoException {
        int sumInTable = ((PrMorb2100) listOfParsedRows.get(0)).getStruckOffTheRegisterGoOut();
        int sumCalc = 0;
        for (int i = 2; i < listOfParsedRows.size(); i++) {
            PrMorb2100 row = (PrMorb2100) listOfParsedRows.get(i);
            sumCalc += row.getStruckOffTheRegisterGoOut();
        }
        if (sumInTable != sumCalc) {
            throw new OncoException("сумма не сходится для struckOffTheRegisterGoOut " + sumInTable + " " + sumCalc);
        }
    }

    public void checkStruckOffTheRegisterDiagNotConfirmed() throws OncoException {
        int sumInTable = ((PrMorb2100) listOfParsedRows.get(0)).getStruckOffTheRegisterDiagNotConfirmed();
        int sumCalc = 0;
        for (int i = 2; i < listOfParsedRows.size(); i++) {
            PrMorb2100 row = (PrMorb2100) listOfParsedRows.get(i);
            sumCalc += row.getStruckOffTheRegisterDiagNotConfirmed();
        }
        if (sumInTable != sumCalc) {
            throw new OncoException("сумма не сходится для struckOffTheRegisterDiagNotConfirmed " + sumInTable + " " + sumCalc);
        }
    }

    public void checkStruckOffTheRegisterNoInfo() throws OncoException {
        int sumInTable = ((PrMorb2100) listOfParsedRows.get(0)).getStruckOffTheRegisterNoInfo();
        int sumCalc = 0;
        for (int i = 2; i < listOfParsedRows.size(); i++) {
            PrMorb2100 row = (PrMorb2100) listOfParsedRows.get(i);
            sumCalc += row.getStruckOffTheRegisterNoInfo();
        }
        if (sumInTable != sumCalc) {
            throw new OncoException("сумма не сходится для struckOffTheRegisterNoInfo " + sumInTable + " " + sumCalc);
        }
    }

    public void checkStruckOffTheRegisterWithDiagBazalioma() throws OncoException {
        int sumInTable = ((PrMorb2100) listOfParsedRows.get(0)).getStruckOffTheRegisterWithDiagBazalioma();
        int sumCalc = 0;
        for (int i = 2; i < listOfParsedRows.size(); i++) {
            PrMorb2100 row = (PrMorb2100) listOfParsedRows.get(i);
            sumCalc += row.getStruckOffTheRegisterWithDiagBazalioma();
        }
        if (sumInTable != sumCalc) {
            throw new OncoException("сумма не сходится для struckOffTheRegisterWithDiagBazalioma " + sumInTable + " " + sumCalc);
        }
    }

    public void checkStruckOffTheRegisterDiedFromAnotherDisease() throws OncoException {
        int sumInTable = ((PrMorb2100) listOfParsedRows.get(0)).getStruckOffTheRegisterDiedFromAnotherDisease();
        int sumCalc = 0;
        for (int i = 2; i < listOfParsedRows.size(); i++) {
            PrMorb2100 row = (PrMorb2100) listOfParsedRows.get(i);
            sumCalc += row.getStruckOffTheRegisterDiedFromAnotherDisease();
        }
        if (sumInTable != sumCalc) {
            throw new OncoException("сумма не сходится для struckOffTheRegisterDiedFromAnotherDisease " + sumInTable + " " + sumCalc);
        }
    }

    public void checkRegisteredInTheEndOfYearAll() throws OncoException {
        int sumInTable = ((PrMorb2100) listOfParsedRows.get(0)).getRegisteredInTheEndOfYearAll();
        int sumCalc = 0;
        for (int i = 2; i < listOfParsedRows.size(); i++) {
            PrMorb2100 row = (PrMorb2100) listOfParsedRows.get(i);
            sumCalc += row.getRegisteredInTheEndOfYearAll();
        }
        if (sumInTable != sumCalc) {
            throw new OncoException("сумма не сходится для registeredInTheEndOfYearAll " + sumInTable + " " + sumCalc);
        }
    }

    public void checkRegisteredInTheEndOfYear5YearsAndMore() throws OncoException {
        int sumInTable = ((PrMorb2100) listOfParsedRows.get(0)).getRegisteredInTheEndOfYear5YearsAndMore();
        int sumCalc = 0;
        for (int i = 2; i < listOfParsedRows.size(); i++) {
            PrMorb2100 row = (PrMorb2100) listOfParsedRows.get(i);
            sumCalc += row.getRegisteredInTheEndOfYear5YearsAndMore();
        }
        if (sumInTable != sumCalc) {
            throw new OncoException("сумма не сходится для registeredInTheEndOfYear5YearsAndMore " + sumInTable + " " + sumCalc);
        }
    }

    @Override
    public void normalizeLocalizations() throws OncoException {
        List<PrMorb2100> listOfParsedRowsCasted = new ArrayList<>();

        for (RowOnco rowOnco : listOfParsedRows) {
            PrMorb2100 prMorb1000Casted = (PrMorb2100) rowOnco;
            listOfParsedRowsCasted.add(prMorb1000Casted);
        }

        Collections.sort(listOfParsedRowsCasted);
        listOfParsedRowsCasted = getNeededListGroup(listOfParsedRowsCasted);
        this.listOfParsedRows.clear();

        for (PrMorb2100 prMorb2100 : listOfParsedRowsCasted) {
            listOfParsedRows.add(prMorb2100);
        }
 //       System.out.println(" &&&&&&&&&&&&&&&&&& После нормализации");
//        for (RowOnco primaryMorbidity : listOfParsedRows) {
//            System.out.println("" + primaryMorbidity);
//        }
    }

    private List<PrMorb2100> getNeededListGroup(List<PrMorb2100> list) {
        List<PrMorb2100> listSelected = new ArrayList<>();

        PrMorb2100 otherPrMor = null;
        PrMorb2100 lymphAndKrovetTkanei = null;

        int removedLimphomiIndex = 0;
        int removedLeukemiyaIndex = 0;
        int removedUnder14Years = 0;

        int i = 0;

        for (PrMorb2100 prMorb : list) {
            if (prMorb.getCodeIcdNum() == LocalizationSelected.drugie) {
                otherPrMor = prMorb;
            } else if (prMorb.getCodeIcdNum() == LocalizationSelected.limphomi) {
                try {
                    lymphAndKrovetTkanei = prMorb.clone();
                    lymphAndKrovetTkanei.setCodeIcdNum(LocalizationSelected.limphat_krovet_tkani);
                    removedLimphomiIndex = i;
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Parse2100.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (prMorb.getCodeIcdNum() == LocalizationSelected.leukemiya) {
                lymphAndKrovetTkanei.update(prMorb);
                removedLeukemiyaIndex = i;
            }

            if (prMorb.isYounger14Years()) {
                removedUnder14Years = i;
            }
            i++;
        }

        list.remove(removedLimphomiIndex);
        list.remove(removedLeukemiyaIndex);
        list.remove(removedUnder14Years);

        list.add(lymphAndKrovetTkanei);

        for (PrMorb2100 prMorb : list) {
            // System.out.println("prMortality = " + prMortality);
            if (LocalizationSelected.isSelectedLocalization(prMorb.getCodeIcdNum())
                    && prMorb.getCodeIcdNum() != LocalizationSelected.drugie) {
                listSelected.add(prMorb);
            } else {
                if (prMorb.getCodeIcdNum() < 29) {
                    otherPrMor.update(prMorb);
                }
            }
        }

        // limphat_krovet_tkani.updateAges(leukemia);
        // listSelected.add(limphat_krovet_tkani);
        listSelected.add(otherPrMor);
        /*
         try {
         check(listSelected);
         } catch (OncoException ex) {
         Logger.getLogger(ParseMortality.class.getName()).log(Level.SEVERE, null, ex);
         }
         */

        return listSelected;
    }

    class Morbidity2000Olap {

        int region;
        int year;
        int localization;
        int stage;
        int value;
    }
}
