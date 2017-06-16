package kz.kazniioir.oncoregistry.form1000;

import kz.kazniioir.oncoregistry.OncoException;
import kz.kazniioir.oncoregistry.FileSystem;
import kz.kazniioir.oncoregistry.OutputFile;
import kz.kazniioir.oncoregistry.RowOnco;
import kz.kazniioir.oncoregistry.db.DbConnection;
import kz.kazniioir.oncoregistry.LocalizationMap;
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
import kz.kazniioir.oncoregistry.formAgencyStat.ParseMortality;
import kz.kazniioir.oncoregistry.formAgencyStat.PrMortality;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ParseForm07 extends ParseDefault {

    private FileSystem fileSystem;
    private int region;
    int rows;
    int cols;
    Sheet sheet;
    RowOnco classParse;
    int numberOfRowsCouldBeParsed;
    int finishParsing = 0;

    public ParseForm07(int year, int region, FileSystem fileSystem, RowOnco classParse) throws OncoException {
        this.year = year;
        this.region = region;
        this.fileSystem = fileSystem;
        this.classParse = classParse;
        initialWork();
    }

    private void initialWork() throws OncoException {
        sheet = fileSystem.getSheet();
        listOfParsedRows = new ArrayList<RowOnco>();
        rows = sheet.getPhysicalNumberOfRows(); // No of rows
        cols = fileSystem.getNumberOfColumns(sheet); // No of columns
        numberOfRowsCouldBeParsed = defineNumberOfRowsCouldBeParsed();
        System.out.println("<<<<<  Работа с файлом: " + getFileName());
        System.out.println("rows всего = " + rows
                + ", cols = " + cols
                + ", rows для парсинга = " + numberOfRowsCouldBeParsed);
    }

    @Override
    public synchronized void parseFile() {
        Row row;
        Cell cell;
        if (classParse instanceof PrMorb1000) {
            PrMorb1000 primaryMorbidityPrevious = null;
            PrMorb1000 primaryMorbidityBothSex = null;
            try {

                for (int r = 0; r < rows; r++) {
                    row = sheet.getRow(r);

                    if (row != null) {
                        if (!isRowWithData(row)) {
                            continue;
                        }
                        PrMorb1000 primaryMorbidity = null;

                        if (year == 2004 || year == 2005) {
                            primaryMorbidity = new PrMorb1000(true);
                        } else {
                            primaryMorbidity = new PrMorb1000(false);
                        }
                        primaryMorbidity.setYear(year);
                        primaryMorbidity.setRegion(region);
                        primaryMorbidity.setFileName(fileSystem.getFile().getName());

                        for (int columnNumber = 0; columnNumber < cols; columnNumber++) {
                            cell = row.getCell(columnNumber, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);
                            primaryMorbidity.setValueOfMorbidity(columnNumber, cell, primaryMorbidityPrevious);
                        }
                        listOfParsedRows.add(primaryMorbidity);

                        //System.out.println("1) prev=" + primaryMorbidityPrevious);
                        //  System.out.println("2) cur=" + primaryMorbidity);
//                        if(primaryMorbidity.getLocalization().contains("член")){
//                         //   System.out.println("debug");
//                        }





                        if (primaryMorbidity.getSex() == 1) {
                            primaryMorbidityBothSex = (PrMorb1000) primaryMorbidity.clone();
                            primaryMorbidityBothSex.setSex(3);
//                             System.out.println("3>>>>) both=" + primaryMorbidityBothSex);
//                            System.out.println("");
                        } else if (primaryMorbidity.getSex() == 2 && (primaryMorbidity.getCodeIcdNum() == primaryMorbidityPrevious.getCodeIcdNum())) {

                            primaryMorbidityBothSex.addAllAgeValues(primaryMorbidity);
                            listOfParsedRows.add(primaryMorbidityBothSex);
//                            System.out.println("3) both=" + primaryMorbidityBothSex);
//                            System.out.println("");
                            primaryMorbidityBothSex = null;
                        }
                        primaryMorbidityPrevious = primaryMorbidity;

                    }
//                    System.out.println("");
                }



                finishParsing = 1;
//                notifyAll();
//                for (RowOnco primaryMorbidity : listOfParsedRows) {
//                    System.out.println("primaryMorbidity = " + primaryMorbidity);
//                }
            } catch (Exception ioe) {
                ioe.printStackTrace();
            }
        }
    }

    @Override
    public void writeOutputFile(OutputFile ouputFile) {
        System.out.println("  <<< Записываем простой файл " + ouputFile.getFileName());

        HSSFSheet sheet = ouputFile.getWorkBook().createSheet("form07");
        try {
            for (int i = 0; i < listOfParsedRows.size(); i++) {
                RowOnco parsedRow = listOfParsedRows.get(i);
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(i);
                for (int col = 0; col < parsedRow.getNumberOfColumns(); col++) {
//                    System.out.println("col = " + col);
                    Cell cell = row.createCell(col);
                    Object value = parsedRow.getValueByIndex(col);
                    if (value instanceof String) {
                        cell.setCellValue(value.toString());
                    } else if (value instanceof Number) {
                        cell.setCellValue((double) (Integer) value);
                    } else {
                        throw new OncoException("value=" + value + ", unknow type: " + value.getClass());
                    }
                }
            }

            ouputFile.write();
            System.out.println("Файл " + ouputFile.getFileName() + " успешно записан >>>>");
        } catch (Exception ex) {
            Logger.getLogger(ParseForm07.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void writeOutputFileOlap(OutputFile ouputFile) {
        System.out.println("  <<< Записываем OLAP файл " + ouputFile.getFileName());
        HSSFSheet sheet = ouputFile.getWorkBook().createSheet("form07");
        Object value;
        try {
            for (int i = 0, rowCurrent = 0; i < listOfParsedRows.size(); i++) {
                PrMorb1000 parsedRow = (PrMorb1000) listOfParsedRows.get(i);
                for (int j = 0; j < 23; j++) {
                    org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowCurrent++);
                    int colNum = 0;
                    //localization
                    colNum = 0;
                    Cell cell = row.createCell(colNum);
                    value = parsedRow.getLocalization();
                    cell.setCellValue(value.toString());

                    //codeICD
                    colNum = 1;
                    cell = row.createCell(colNum);
                    value = parsedRow.getCodeICD();
                    cell.setCellValue(value == null ? "" : value.toString());


                    //codeICDNum
                    colNum = 2;
                    cell = row.createCell(colNum);
                    value = parsedRow.getCodeIcdNum();
                    cell.setCellValue(value.toString());

                    //year;
                    colNum = 3;
                    cell = row.createCell(colNum);
                    value = parsedRow.getYear();
                    cell.setCellValue((int) value);

                    //region;
                    colNum = 4;
                    cell = row.createCell(colNum);
                    value = parsedRow.getRegion();
                    cell.setCellValue((int) value);

                    //sex;
                    colNum = 5;
                    cell = row.createCell(colNum);
                    value = parsedRow.getSex();
                    cell.setCellValue((int) value);

                    //fileName;
                    colNum = 6;
                    cell = row.createCell(colNum);
                    value = parsedRow.getFileName();
                    cell.setCellValue(value.toString());

                    //ageDiapozon;
                    colNum = 7;
                    cell = row.createCell(colNum);
                    cell.setCellValue((int) ((j + 1) * 10));

                    //morbidity value;
                    colNum = 8;
                    cell = row.createCell(colNum);
                    value = parsedRow.getValueByIndex(j + 7);
                    cell.setCellValue((int) value);

                }
            }

            ouputFile.write();
            System.out.println("Файл OLAP " + ouputFile.getFileName() + " успешно записан >>>>");


        } catch (Exception ex) {
            Logger.getLogger(ParseForm07.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void validateParse() throws OncoException {
        System.out.println("Валидируем данные!");

        if (classParse instanceof PrMorb1000) {

            if (listOfParsedRows.size() != numberOfRowsCouldBeParsed + 30) {
                throw new OncoException("количество строк и количество объектов не совпадает: "
                        + listOfParsedRows.size()
                        + ", против "
                        + numberOfRowsCouldBeParsed + 30);
            }


            for (RowOnco row : listOfParsedRows) {
                PrMorb1000 pMorbidity = (PrMorb1000) row;

                if (pMorbidity.getSumOfAllAges() != pMorbidity.getAgeall()) {
                    throw new OncoException("сумма не бьет у строки " + pMorbidity.getCodeICD()
                            + ", в файле сумма - " + pMorbidity.getAgeall() + ", а по рассчетам - " + pMorbidity.getSumOfAllAges());
                }

                if (pMorbidity.getSumOfAllAgesWithAge70Plus() != pMorbidity.getAgeall()) {
                    throw new OncoException("сумма не бьет для 70 плюс у строки " + pMorbidity.getCodeICD()
                            + ", в файле сумма - " + pMorbidity.getAgeall() + ", а по рассчетам - " + pMorbidity.getSumOfAllAgesWithAge70Plus());
                }

                if (pMorbidity.getYear() != year) {
                    throw new OncoException("год у строки неправильный" + pMorbidity);
                }

                if (pMorbidity.getSex() != 1 && pMorbidity.getSex() != 2 && pMorbidity.getSex() != 3) {
                    throw new OncoException("пол у строки неправильный" + pMorbidity);
                }

                if (pMorbidity.getCodeIcdNum() == 0) {
                    throw new OncoException("численный код локализации у строки неправильный: " + pMorbidity.getCodeIcdNum() + " подробно: " + pMorbidity);
                }
                if (pMorbidity.getRegion() != region) {
                    throw new OncoException("код региона у строки неправильный" + pMorbidity);
                }

//                if (!pMorbidity.containsCode()) {
//                    throw new OncoException("шифр заболевания у строки неправильный" + pMorbidity);
//                }

                if (pMorbidity.getCodeIcdNum() == 0
                        || pMorbidity.getCodeIcdNum() < -1
                        || (pMorbidity.getCodeIcdNum() > 36
                        && pMorbidity.getCodeIcdNum() != LocalizationMap.CODE_INT_ALL_LOC)) {
                    throw new OncoException("численный шифр заболевания у строки неправильный" + pMorbidity);
                }


            }
            System.out.println("Валидация прошла успешно!");
        }
    }

    @Override
    public int getNumberOfRowsCouldBeParsed() throws OncoException {
        return numberOfRowsCouldBeParsed;
    }

    private int defineNumberOfRowsCouldBeParsed() throws OncoException {
        int numberOfRows = 0;
        int countParsed = 0;

        for (int r = 0; r < rows; r++) {
            Row row = sheet.getRow(r);
            if (row != null) {
                Cell cell = row.getCell(2);
                if (cell != null) {
                    if (cell.getCellType() != Cell.CELL_TYPE_BLANK && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        numberOfRows = (int) cell.getNumericCellValue();
                        countParsed++;
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
    public String getFileName() {
        return fileSystem.getFile().getName();
    }

    @Override
    public boolean isRowWithData(Row row) {
        if (row == null) {
            return false;
        }

        if (row.getCell(1) == null) {
            return false;
        }

        if (row.getCell(1).getCellType() != Cell.CELL_TYPE_STRING) {
            return false;
        }

        if (!row.getCell(1).getStringCellValue().trim().equals("м")
                && !row.getCell(1).getStringCellValue().trim().equals("ж")) {
            return false;
        }
        return true;
    }

    @Override
    public void writeOutputDBOlap() {
        Connection conn = null;
        Statement stmt = null;
        Object value;
        System.out.println("   !!! writing to OLAP DB file " + getFileName());
        try {
            conn = DbConnection.getConnection();
            //STEP 4: Execute a query
            System.out.println("Insert Rows in given table");
            stmt = conn.createStatement();
            for (int i = 0, rowCurrent = 0; i < listOfParsedRows.size(); i++) {
                PrMorb1000 parsedRow = (PrMorb1000) listOfParsedRows.get(i);
                String insertRow = "";
                for (int j = 0; j < 23; j++) {
                    insertRow = String.format(""
                            + "INSERT INTO morbidityolap "
                            + "(id,  region_id,  year,  localization_id,  sex_id,  age_id,   filename,  morbidity) "
                            + "VALUE (null, '%s', '%s', '%s', '%s' , '%s' , '%s' , '%s')",
                            parsedRow.getRegion(),
                            parsedRow.getYear(),
                            parsedRow.getCodeIcdNum(),
                            parsedRow.getSex(),
                            (j + 1) * 10,
                            parsedRow.getFileName(),
                            parsedRow.getValueByIndex(j + 7));

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
                PrMorb1000 parsedRow = (PrMorb1000) listOfParsedRows.get(i);
                String insertRow = String.format(""
                        + "INSERT INTO morbiditysimple "
                        + "(id,   region_id,   year,   localization_id,   sex_id,   filename,   "
                        + " ageall,   age0_4,   age5_9,   age10_14,   age15_17, age18_19,  age15_19, "
                        + " age20_24,   age25_29,   age15_29,   age30_34,   age35_39,   "
                        + " age40_44,   age45_49,   age50_54,   age55_59,   age60_64,   "
                        + " age65_69,   age70_74,   age75_79,   age80_84, age70,  age85) "
                        + " VALUE (null, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', "
                        + "'%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', "
                        + "'%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                        parsedRow.getRegion(),
                        parsedRow.getYear(),
                        parsedRow.getCodeIcdNum(),
                        parsedRow.getSex(),
                        parsedRow.getFileName(),
                        parsedRow.getAgeall(),
                        parsedRow.getAge0_4(),
                        parsedRow.getAge5_9(),
                        parsedRow.getAge10_14(),
                        parsedRow.getAge15_17(),
                        parsedRow.getAge18_19(),
                        parsedRow.getAge15_19(),
                        parsedRow.getAge20_24(),
                        parsedRow.getAge25_29(),
                        parsedRow.getAge15_29(),
                        parsedRow.getAge30_34(),
                        parsedRow.getAge35_39(),
                        parsedRow.getAge40_44(),
                        parsedRow.getAge45_49(),
                        parsedRow.getAge50_54(),
                        parsedRow.getAge55_59(),
                        parsedRow.getAge60_64(),
                        parsedRow.getAge65_69(),
                        parsedRow.getAge70_74(),
                        parsedRow.getAge75_79(),
                        parsedRow.getAge80_84(),
                        parsedRow.getAge70Plus(),
                        parsedRow.getAge85Plus());

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
        System.out.println("Goodbye!");
    }

    @Override
    public void normalizeLocalizations() throws OncoException {
        List<List<PrMorb1000>> listPrMortalityByLocalAndsex = new ArrayList<>();
        List<List<PrMorb1000>> listPrMortalityByLocalAndsexAfterWork = new ArrayList<>();

        List<PrMorb1000> listOfParsedRowsCasted = new ArrayList<>();

        // to cast and add another sex and both sex variants
        for (RowOnco rowOnco : listOfParsedRows) {
            PrMorb1000 prMorb1000Casted = (PrMorb1000) rowOnco;
            listOfParsedRowsCasted.add(prMorb1000Casted);

            if (LocalizationSelected.isSexSpecificLocalization(prMorb1000Casted.getCodeIcdNum())) {
                PrMorb1000 anotherSexPrMorb1000 = null;
                PrMorb1000 bothSexPrMorb1000 = null;
                try {
                    anotherSexPrMorb1000 = prMorb1000Casted.clone();
                    bothSexPrMorb1000 = prMorb1000Casted.clone();
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(ParseForm07.class.getName()).log(Level.SEVERE, null, ex);
                }
                anotherSexPrMorb1000.setSex(prMorb1000Casted.getSex() == 1 ? 2 : 1);
                anotherSexPrMorb1000.cleareAges();
                bothSexPrMorb1000.setSex(3);
                listOfParsedRowsCasted.add(anotherSexPrMorb1000);
                listOfParsedRowsCasted.add(bothSexPrMorb1000);
            }
        }
        //

        Collections.sort(listOfParsedRowsCasted);

        int sex = 1;
        List<PrMorb1000> listGroup = new ArrayList<>();

        int count = 0;
        for (PrMorb1000 rowOnco : listOfParsedRowsCasted) {
            // System.out.println("rowOnco = " + rowOnco);
            //if(1==1) continue;
            count++;
            if (sex != rowOnco.getSex()) {
                listPrMortalityByLocalAndsex.add(listGroup);
                listGroup = new ArrayList<>();
                listGroup.add(rowOnco);
                sex = rowOnco.getSex();
            } else {
                listGroup.add(rowOnco);
                if (count == listOfParsedRowsCasted.size()) {
                    listPrMortalityByLocalAndsex.add(listGroup);
                }
            }
        }
        System.out.println("" + listPrMortalityByLocalAndsex.size());

        for (List<PrMorb1000> listGroup1 : listPrMortalityByLocalAndsex) {
            listPrMortalityByLocalAndsexAfterWork.add(getNeededListGroup(listGroup1));
        }

        this.listOfParsedRows.clear();

        for (List<PrMorb1000> listGroup1 : listPrMortalityByLocalAndsexAfterWork) {
            //System.out.println("<<<<<<<<<<<<< Next group");
            for (PrMorb1000 prMortality : listGroup1) {
                //  System.out.println("prMortality = " + prMortality);
                this.listOfParsedRows.add(prMortality);
            }
        }
    }

    private List<PrMorb1000> getNeededListGroup(List<PrMorb1000> list) {
        List<PrMorb1000> listSelected = new ArrayList<>();

        PrMorb1000 otherPrMor = null;

        for (PrMorb1000 prMorb : list) {
            if (prMorb.getCodeIcdNum() == LocalizationSelected.drugie) {
                otherPrMor = prMorb;
                break;
            }
        }

        for (PrMorb1000 prMorb : list) {
            // System.out.println("prMortality = " + prMortality);
            if (LocalizationSelected.isSelectedLocalization(prMorb.getCodeIcdNum())
                    && prMorb.getCodeIcdNum() != LocalizationSelected.drugie) {
                listSelected.add(prMorb);
            } else {
                if (prMorb.getCodeIcdNum() < 29) {
                    otherPrMor.updateAges(prMorb);
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

    private void check(List<PrMorb1000> listOther) throws OncoException {
        PrMorb1000 allLocal = null;

        int ageallOther = 0;
        int age0_4Other = 0;
        int age5_9Other = 0;
        int age10_14Other = 0;
        int age15_19Other = 0;
        int age20_24Other = 0;
        int age25_29Other = 0;
        int age30_34Other = 0;
        int age35_39Other = 0;
        int age40_44Other = 0;
        int age45_49Other = 0;
        int age50_54Other = 0;
        int age55_59Other = 0;
        int age60_64Other = 0;
        int age65_69Other = 0;
        int age70PlusOther = 0;

        int age15_17Other = 0;
        int age18_19Other = 0;
        int age70_74Other = 0;
        int age75_79Other = 0;
        int age80_84Other = 0;
        int age85PlusOther = 0;
        int age15_29Other = 0;

        for (PrMorb1000 prMortality : listOther) {
            if (prMortality.getCodeIcdNum() == LocalizationSelected.vse) {
                allLocal = prMortality;
                continue;
            }
            ageallOther += prMortality.getAgeall();
            age0_4Other += prMortality.getAge0_4();
            age5_9Other += prMortality.getAge5_9();
            age10_14Other += prMortality.getAge10_14();
            age15_19Other += prMortality.getAge15_19();
            age20_24Other += prMortality.getAge20_24();
            age25_29Other += prMortality.getAge25_29();
            age30_34Other += prMortality.getAge30_34();
            age35_39Other += prMortality.getAge35_39();
            age40_44Other += prMortality.getAge40_44();
            age45_49Other += prMortality.getAge45_49();
            age50_54Other += prMortality.getAge50_54();
            age55_59Other += prMortality.getAge55_59();
            age60_64Other += prMortality.getAge60_64();
            age65_69Other += prMortality.getAge65_69();
            age70PlusOther += prMortality.getAge70Plus();

            age15_17Other += prMortality.getAge15_17();
            age18_19Other += prMortality.getAge18_19();
            age70_74Other += prMortality.getAge70_74();
            age75_79Other += prMortality.getAge75_79();
            age80_84Other += prMortality.getAge80_84();
            age85PlusOther += prMortality.getAge85Plus();
            age15_29Other += prMortality.getAge15_29();
        }

        if (ageallOther != allLocal.getAgeall()) {
            throw new OncoException("ageall не сходится!");
        }
        if (age0_4Other != allLocal.getAge0_4()) {
            throw new OncoException("age0_4Other не сходится!");
        }
        if (age5_9Other != allLocal.getAge5_9()) {
            throw new OncoException("age5_9Other не сходится!");
        }
        if (age10_14Other != allLocal.getAge10_14()) {
            throw new OncoException("age10_14Other не сходится!");
        }
        if (age15_19Other != allLocal.getAge15_19()) {
            throw new OncoException("age15_19Other не сходится!");
        }
        if (age20_24Other != allLocal.getAge20_24()) {
            throw new OncoException("age20_24Other не сходится!");
        }
        if (age25_29Other != allLocal.getAge25_29()) {
            throw new OncoException("age25_29Other не сходится!");
        }
        if (age30_34Other != allLocal.getAge30_34()) {
            throw new OncoException("age30_34Other не сходится!");
        }
        if (age35_39Other != allLocal.getAge35_39()) {
            throw new OncoException("age35_39Other не сходится!");
        }
        if (age40_44Other != allLocal.getAge40_44()) {
            throw new OncoException("age40_44Other не сходится!");
        }
        if (age45_49Other != allLocal.getAge45_49()) {
            throw new OncoException("age45_49Other не сходится!");
        }
        if (age50_54Other != allLocal.getAge50_54()) {
            throw new OncoException("age50_54Other не сходится!");
        }
        if (age55_59Other != allLocal.getAge55_59()) {
            throw new OncoException("age55_59Other не сходится!");
        }
        if (age60_64Other != allLocal.getAge60_64()) {
            throw new OncoException("age60_64Other не сходится!");
        }
        if (age65_69Other != allLocal.getAge65_69()) {
            throw new OncoException("age65_69Other не сходится!");
        }
        if (age70PlusOther != allLocal.getAge70Plus()) {
            throw new OncoException("age70PlusOther не сходится!");
        }

        if (age15_17Other != allLocal.getAge15_17()) {
            throw new OncoException("age15_17Other не сходится!");
        }
        if (age18_19Other != allLocal.getAge18_19()) {
            throw new OncoException("age18_19Other не сходится!");
        }
        if (age75_79Other != allLocal.getAge75_79()) {
            throw new OncoException("age75_79Other не сходится!");
        }
        if (age80_84Other != allLocal.getAge80_84()) {
            throw new OncoException("age80_84Other не сходится!");
        }
        if (age85PlusOther != allLocal.getAge85Plus()) {
            throw new OncoException("age85PlusOther не сходится!");
        }
        if (age15_29Other != allLocal.getAge15_29()) {
            throw new OncoException("age15_29Other не сходится!");
        }






    }
}
