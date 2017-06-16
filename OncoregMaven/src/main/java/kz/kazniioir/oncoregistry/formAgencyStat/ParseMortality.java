package kz.kazniioir.oncoregistry.formAgencyStat;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import kz.kazniioir.oncoregistry.LocalizationMap;
import kz.kazniioir.oncoregistry.LocalizationSelected;
import kz.kazniioir.oncoregistry.OncoException;
import kz.kazniioir.oncoregistry.OutputFile;
import kz.kazniioir.oncoregistry.RowOnco;
import kz.kazniioir.oncoregistry.db.DbConnection;
import kz.kazniioir.oncoregistry.form1000.PrMorb1000;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ParseMortality extends kz.kazniioir.oncoregistry.ParseDefault {

    protected Workbook workbook;
    protected File file;
    protected int firstRow;
    protected int lastRow;
    protected int firstColumn;
    protected int lastColumn;
    protected Map<String, Integer> mapAges = fillAgeCode();
    protected Map<String, Integer> mapLocalizations = fillLocalozationCode();

    public ParseMortality(File file, int year) throws IOException, InvalidFormatException {
        this.year = year;
        //this.region = region;
        if (file != null) {
            this.file = file;
            this.workbook = WorkbookFactory.create(file);
        }
    }

    public void normalizeLocalizations() throws OncoException {
        List<List<PrMortality>> listPrMortalityByLocalAndsex = new ArrayList<>();
        List<List<PrMortality>> listPrMortalityByLocalAndsexAfterWork = new ArrayList<>();

        List<PrMortality> listOfParsedRowsCasted = new ArrayList<>();
        for (RowOnco rowOnco : listOfParsedRows) {
            listOfParsedRowsCasted.add((PrMortality) rowOnco);
        }

        Collections.sort(listOfParsedRowsCasted);

        int sex = 1;
        List<PrMortality> listGroup = new ArrayList<>();

        int count = 0;
        for (PrMortality rowOnco : listOfParsedRowsCasted) {
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

        for (List<PrMortality> listGroup1 : listPrMortalityByLocalAndsex) {
            listPrMortalityByLocalAndsexAfterWork.add(getNeededListGroup(listGroup1));
        }

        this.listOfParsedRows.clear();
        
        for (List<PrMortality> listGroup1 : listPrMortalityByLocalAndsexAfterWork) {
            //System.out.println("<<<<<<<<<<<<< Next group");
            for (PrMortality prMortality : listGroup1) {
              //  System.out.println("prMortality = " + prMortality);
                this.listOfParsedRows.add(prMortality);
            }
        }
        
         //System.out.println("listOfParsedRows.size()=" + listOfParsedRows.size());
       // for (RowOnco prMortality : listOfParsedRows) {
          //  System.out.println("prMortality = " + prMortality);
       // }
    }

    private List<PrMortality> getNeededListGroup(List<PrMortality> list) {
        List<PrMortality> listSelected = new ArrayList<>();
        PrMortality limphat_krovet_tkani = null;;
        PrMortality leukemia = null;


        PrMortality otherPrMortality = new PrMortality();
        otherPrMortality.setYear(list.get(0).getYear());
        otherPrMortality.setRegion(list.get(0).getRegion());
        otherPrMortality.setSex(list.get(0).getSex());
        otherPrMortality.setLocalization("прочие злокачественные новообразования");
        otherPrMortality.setCodeICD(null);
        otherPrMortality.setCodeIcdNum(50);

        for (PrMortality prMortality : list) {
            // System.out.println("prMortality = " + prMortality);
            if (LocalizationSelected.isSelectedLocalization(prMortality.getCodeIcdNum())
                    && prMortality.getCodeIcdNum() != LocalizationSelected.limphat_krovet_tkani) {

                listSelected.add(prMortality);


            } else {
                if (prMortality.getCodeIcdNum() == LocalizationSelected.limphat_krovet_tkani) {
                    limphat_krovet_tkani = prMortality;
                } else if (prMortality.getCodeIcdNum() == LocalizationSelected.leukemiya) {
                    leukemia = prMortality;
                } else {
                    otherPrMortality.updateAges(prMortality);
                }

            }
        }

        limphat_krovet_tkani.updateAges(leukemia);
        listSelected.add(limphat_krovet_tkani);
        listSelected.add(otherPrMortality);
        try {
            check(listSelected);
        } catch (OncoException ex) {
            Logger.getLogger(ParseMortality.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listSelected;
    }

    private void check(List<PrMortality> listOther) throws OncoException {
        PrMortality allLocal = null;

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

        for (PrMortality prMortality : listOther) {
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
    }

    private Sheet getSheetNameFromStartNumber(int startNumber) throws OncoException {
        String startOfName = "";
        if (startNumber < 10) {
            startOfName += "0";
        }
        startOfName += startNumber;
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if (sheet.getSheetName().startsWith(startOfName)) {
                return sheet;
            }
        }
        throw new OncoException("No sheet that starts with " + startOfName);
    }

    private int getRowOfRegion(int startNumber, Sheet sheet) throws OncoException {
        String startOfName = "";
        if (startNumber < 10) {
            startOfName += "0";
        }
        startOfName += startNumber + "" + "_";
        int numberRows = sheet.getLastRowNum();
        for (int i = 0; i < numberRows; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(0);
            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                String val = cell.getStringCellValue();
                if (val.startsWith(startOfName)) {
                    System.out.println("reg = " + val);
                    return i;
                }
            }
        }

        return 0;
    }

    @Override
    public void parseFile() {
        if (year < 2011) {
            parseFileManySheets();
        } else {
            parseFileOneSheet();
        }

        //  System.out.println("listOfParsedRows.size = " + listOfParsedRows.size());

        for (RowOnco rowOnco : listOfParsedRows) {
            // System.out.println("rowOnco = " + rowOnco);
        }

    }

    private void parseFileManySheets() {
        try {
            for (int region = 1; region < 18; region++) {
                if (region != 17) {
                    // continue;
                }

                Sheet sheet = getSheetNameFromStartNumber(region);

                System.out.println("sheet = " + sheet.getSheetName());
                firstRow = 2;
                lastRow = 52;
                firstColumn = 1;
                lastColumn = 31;

                for (int colNum = firstColumn; colNum < lastColumn + 1; colNum++) {
                    PrMortality rowOnco = null;
                    int codeICDNum = 0;
                    String localizationStr = "";
                    String codeICDNumStr = "";
                    int sex = 0;

                    for (int rowNum = firstRow; rowNum < lastRow + 1; rowNum++) {

                        int age = 0;
                        Row row = sheet.getRow(rowNum);

                        Cell cell = row.getCell(colNum, Row.CREATE_NULL_AS_BLANK);

                        /* define localization */
                        if (rowNum == firstRow) {
                            Row rowOfHead = sheet.getRow(rowNum - 2);
                            Cell cellForLocalisationDefine = rowOfHead.getCell(colNum, Row.CREATE_NULL_AS_BLANK);
                            try {
                                codeICDNum = mapLocalizations.get(cellForLocalisationDefine.getStringCellValue());
                            } catch (NullPointerException ex) {
                                System.out.println("cellForLocalisationDefine.getStringCellValue()=" + cellForLocalisationDefine.getStringCellValue());
                                throw new NullPointerException(ex.getMessage());
                            }
                            localizationStr = cellForLocalisationDefine.getStringCellValue();

                            Row rowOfHeadWithCodeICD = sheet.getRow(rowNum - 1);
                            codeICDNumStr = rowOfHeadWithCodeICD.getCell(colNum, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
                        }
                        /*-- define localization */

                        /* define sex */
                        Cell cellForDefineSexAndAge = row.getCell(0, Row.CREATE_NULL_AS_BLANK);
                        String strforSexAndAge = cellForDefineSexAndAge.getStringCellValue();
                        if (strforSexAndAge.equalsIgnoreCase("Всего")) {
                            sex = 3;
                            rowOnco = new PrMortality();
                        } else if (strforSexAndAge.equalsIgnoreCase("Мужчины")) {
                            sex = 1;
                            rowOnco = new PrMortality();
                        } else if (strforSexAndAge.equalsIgnoreCase("Женщины")) {
                            sex = 2;
                            rowOnco = new PrMortality();
                        }
                        /*-- define sex */

                        /* define age */
                        if (mapAges.get(strforSexAndAge) != null) {
                            age = mapAges.get(strforSexAndAge);
                        }
                        /*-- define age */
                        Object val = null;
                        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            val = (int) cell.getNumericCellValue();
                        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            val = cell.getStringCellValue();
                        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                            val = (int) cell.getNumericCellValue();
                        } else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                            val = "БЛ";
                        } else {
                            throw new OncoException("Неизвестный тип ячейки, Unknown type of cell: " + cell.getCellType());
                        }

                        if (isRowWithData(row)) {
                            //System.out.println("val = " + val);
                            int valInt = 0;
                            try {
                                valInt = (int) val;
                            } catch (java.lang.ClassCastException ex) {
                                if (!val.toString().equals("БЛ")) {
                                    throw new OncoException(ex.getMessage() + " value: " + val + ", col: " + colNum + ", rowNum:" + rowNum);
                                }
                                //System.err.println(ex.getMessage() + (val)+" value: " + val + ", col: " + colNum + ", rowNum:" + rowNum);
                            }

                            switch (age) {
                                case 10:
                                    rowOnco.setAgeall(valInt);
                                    break;
                                case 20:
                                    rowOnco.setAge0_4(valInt);
                                    break;
                                case 30:
                                    rowOnco.setAge5_9(valInt);
                                    break;
                                case 40:
                                    rowOnco.setAge10_14(valInt);
                                    break;
                                case 70:
                                    rowOnco.setAge15_19(valInt);
                                    break;
                                case 80:
                                    rowOnco.setAge20_24(valInt);
                                    break;
                                case 90:
                                    rowOnco.setAge25_29(valInt);
                                    break;
                                case 110:
                                    rowOnco.setAge30_34(valInt);
                                    break;
                                case 120:
                                    rowOnco.setAge35_39(valInt);
                                    break;
                                case 130:
                                    rowOnco.setAge40_44(valInt);
                                    break;
                                case 140:
                                    rowOnco.setAge45_49(valInt);
                                    break;
                                case 150:
                                    rowOnco.setAge50_54(valInt);
                                    break;
                                case 160:
                                    rowOnco.setAge55_59(valInt);
                                    break;
                                case 170:
                                    rowOnco.setAge60_64(valInt);
                                    break;
                                case 180:
                                    rowOnco.setAge65_69(valInt);
                                    break;
                                case 220:
                                    rowOnco.setAge70Plus(valInt);
                                    break;
                                case -1:
                                    //rowOnco.setAgeAbs(valInt);
                                    throw new OncoException("Возраст не указан не должне быть!!! age: " + age);
                                // break;
                                default:
                                    throw new OncoException("Возраст отсутствующий!!! age: " + age);
                            }

                            if (age == 220) {
                                rowOnco.setCodeIcdNum(codeICDNum);
                                rowOnco.setCodeICD(codeICDNumStr);
                                rowOnco.setLocalization(localizationStr);
                                rowOnco.setSex(sex);
                                rowOnco.setRegion(region);
                                rowOnco.setYear(this.year);
                                rowOnco.setFileName(this.file.getName());
                                this.listOfParsedRows.add(rowOnco);
                            }
                        }
                    }

                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ParseMortality.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void parseFileOneSheet() {
        try {
            Sheet sheet = workbook.getSheet("00");
            for (int region = 1; region < 18; region++) {

                int rowStart = getRowOfRegion(region, sheet);

                System.out.println("rowStart = " + rowStart);

                if (1 == 1) {
                    // continue;
                }

                if (region != 17) {
                    // continue;
                }
                // System.out.println("sheet = " + sheet.getSheetName());
                firstRow = rowStart + 1;
                lastRow = firstRow + 52;
                firstColumn = 1;
                lastColumn = 31;

                for (int colNum = firstColumn; colNum < lastColumn + 1; colNum++) {
                    PrMortality rowOnco = null;
                    int codeICDNum = 0;
                    String localizationStr = "";
                    String codeICDNumStr = "";
                    int sex = 0;

                    for (int rowNum = firstRow; rowNum < lastRow + 1; rowNum++) {

                        int age = 0;
                        Row row = sheet.getRow(rowNum);

                        Cell cell = row.getCell(colNum, Row.CREATE_NULL_AS_BLANK);

                        /* define localization */
                        if (rowNum == firstRow) {
                            Row rowOfHead = sheet.getRow(0);
                            Cell cellForLocalisationDefine = rowOfHead.getCell(colNum, Row.CREATE_NULL_AS_BLANK);
                            try {
                                codeICDNum = mapLocalizations.get(cellForLocalisationDefine.getStringCellValue());
                            } catch (NullPointerException ex) {
                                System.out.println("cellForLocalisationDefine.getStringCellValue()=" + cellForLocalisationDefine.getStringCellValue());
                                throw new NullPointerException(ex.getMessage());
                            }
                            localizationStr = cellForLocalisationDefine.getStringCellValue();

                            Row rowOfHeadWithCodeICD = sheet.getRow(1);
                            codeICDNumStr = rowOfHeadWithCodeICD.getCell(colNum, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
                        }
                        /*-- define localization */

                        /* define sex */
                        Cell cellForDefineSexAndAge = row.getCell(0, Row.CREATE_NULL_AS_BLANK);
                        String strforSexAndAge = cellForDefineSexAndAge.getStringCellValue();
                        if (strforSexAndAge.equalsIgnoreCase("Всего")) {
                            sex = 3;
                            rowOnco = new PrMortality();
                        } else if (strforSexAndAge.equalsIgnoreCase("Мужчины")) {
                            sex = 1;
                            rowOnco = new PrMortality();
                        } else if (strforSexAndAge.equalsIgnoreCase("Женщины")) {
                            sex = 2;
                            rowOnco = new PrMortality();
                        }
                        /*-- define sex */

                        /* define age */
                        if (mapAges.get(strforSexAndAge) != null) {
                            age = mapAges.get(strforSexAndAge);
                        }
                        /*-- define age */
                        Object val = null;
                        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            val = (int) cell.getNumericCellValue();
                        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            val = cell.getStringCellValue();
                        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                            val = (int) cell.getNumericCellValue();
                        } else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                            val = "БЛ";
                        } else {
                            throw new OncoException("Неизвестный тип ячейки, Unknown type of cell: " + cell.getCellType());
                        }

                        if (isRowWithData(row)) {
                            //System.out.println("val = " + val);
                            int valInt = 0;
                            try {
                                valInt = (int) val;
                            } catch (java.lang.ClassCastException ex) {
                                if (!val.toString().equals("БЛ")) {
                                    throw new OncoException(ex.getMessage() + " value: " + val + ", col: " + colNum + ", rowNum:" + rowNum);
                                }
                                //System.err.println(ex.getMessage() + (val)+" value: " + val + ", col: " + colNum + ", rowNum:" + rowNum);
                            }

                            switch (age) {
                                case 10:
                                    rowOnco.setAgeall(valInt);
                                    break;
                                case 20:
                                    rowOnco.setAge0_4(valInt);
                                    break;
                                case 30:
                                    rowOnco.setAge5_9(valInt);
                                    break;
                                case 40:
                                    rowOnco.setAge10_14(valInt);
                                    break;
                                case 70:
                                    rowOnco.setAge15_19(valInt);
                                    break;
                                case 80:
                                    rowOnco.setAge20_24(valInt);
                                    break;
                                case 90:
                                    rowOnco.setAge25_29(valInt);
                                    break;
                                case 110:
                                    rowOnco.setAge30_34(valInt);
                                    break;
                                case 120:
                                    rowOnco.setAge35_39(valInt);
                                    break;
                                case 130:
                                    rowOnco.setAge40_44(valInt);
                                    break;
                                case 140:
                                    rowOnco.setAge45_49(valInt);
                                    break;
                                case 150:
                                    rowOnco.setAge50_54(valInt);
                                    break;
                                case 160:
                                    rowOnco.setAge55_59(valInt);
                                    break;
                                case 170:
                                    rowOnco.setAge60_64(valInt);
                                    break;
                                case 180:
                                    rowOnco.setAge65_69(valInt);
                                    break;
                                case 220:
                                    rowOnco.setAge70Plus(valInt);
                                    break;
                                case -1:
                                    //rowOnco.setAgeAbs(valInt);
                                    throw new OncoException("Возраст не указан не должне быть!!! age: " + age);
                                // break;
                                default:
                                    throw new OncoException("Возраст отсутствующий!!! age: " + age);
                            }

                            if (age == 220) {
                                rowOnco.setCodeIcdNum(codeICDNum);
                                rowOnco.setCodeICD(codeICDNumStr);
                                rowOnco.setLocalization(localizationStr);
                                rowOnco.setSex(sex);
                                rowOnco.setRegion(region);
                                rowOnco.setYear(this.year);
                                rowOnco.setFileName(this.file.getName());
                                this.listOfParsedRows.add(rowOnco);
                            }
                        }
                    }

                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ParseMortality.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private PrMortality getCommonSexRowOnco(PrMortality previousRowOnco, PrMortality thisrowOnco) {
        PrMortality commonSexRowOnco = new PrMortality();
        commonSexRowOnco.setCodeIcdNum(thisrowOnco.getCodeIcdNum());
        commonSexRowOnco.setSex(3);
        commonSexRowOnco.setRegion(thisrowOnco.getRegion());
        commonSexRowOnco.setYear(thisrowOnco.getYear());
        commonSexRowOnco.setFileName(this.file.getName());
        commonSexRowOnco.setAgeall(previousRowOnco.getAgeall() + thisrowOnco.getAgeall());
        commonSexRowOnco.setAge0_4(previousRowOnco.getAge0_4() + thisrowOnco.getAge0_4());
        commonSexRowOnco.setAge5_9(previousRowOnco.getAge5_9() + thisrowOnco.getAge5_9());
        commonSexRowOnco.setAge10_14(previousRowOnco.getAge10_14() + thisrowOnco.getAge10_14());
        commonSexRowOnco.setAge15_19(previousRowOnco.getAge15_19() + thisrowOnco.getAge15_19());
        commonSexRowOnco.setAge20_24(previousRowOnco.getAge20_24() + thisrowOnco.getAge20_24());
        commonSexRowOnco.setAge25_29(previousRowOnco.getAge25_29() + thisrowOnco.getAge25_29());
        commonSexRowOnco.setAge30_34(previousRowOnco.getAge30_34() + thisrowOnco.getAge30_34());
        commonSexRowOnco.setAge35_39(previousRowOnco.getAge35_39() + thisrowOnco.getAge35_39());
        commonSexRowOnco.setAge40_44(previousRowOnco.getAge40_44() + thisrowOnco.getAge40_44());
        commonSexRowOnco.setAge45_49(previousRowOnco.getAge45_49() + thisrowOnco.getAge45_49());
        commonSexRowOnco.setAge50_54(previousRowOnco.getAge50_54() + thisrowOnco.getAge50_54());
        commonSexRowOnco.setAge55_59(previousRowOnco.getAge55_59() + thisrowOnco.getAge55_59());
        commonSexRowOnco.setAge60_64(previousRowOnco.getAge60_64() + thisrowOnco.getAge60_64());
        commonSexRowOnco.setAge65_69(previousRowOnco.getAge65_69() + thisrowOnco.getAge65_69());
        commonSexRowOnco.setAge70Plus(previousRowOnco.getAge70Plus() + thisrowOnco.getAge70Plus());

        return commonSexRowOnco;
    }

    @Override
    public void validateParse() throws OncoException {
        System.out.println("\n \t <<<<<   Валидируем данные, за " + year + " год!");
        System.out.println("Количество строк в списке: " + listOfParsedRows.size());

        for (RowOnco row : listOfParsedRows) {
            //System.out.println("row = " + row);
            PrMortality pMorbidity = (PrMortality) row;

            if (pMorbidity.getSumOfAllAges() != pMorbidity.getAgeall()) {
                System.out.println("pMorbidity = " + pMorbidity);
                throw new OncoException("сумма не бьет у строки " + pMorbidity.getCodeICD()
                        + ", в файле сумма - " + pMorbidity.getAgeall() + ", а по рассчетам - " + pMorbidity.getSumOfAllAges());
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
            if (pMorbidity.getRegion() < 1 || pMorbidity.getRegion() > 17) {
                throw new OncoException("код региона у строки неправильный" + pMorbidity);
            }


            if (pMorbidity.getCodeIcdNum() == 0
                    || pMorbidity.getCodeIcdNum() < -1
                    || (pMorbidity.getCodeIcdNum() > 109
                    && pMorbidity.getCodeIcdNum() != LocalizationMap.CODE_INT_ALL_LOC)) {
                throw new OncoException("численный шифр заболевания у строки неправильный" + pMorbidity);
            }

            if (pMorbidity.getCodeICD() == null || pMorbidity.getCodeICD().isEmpty()) {
                throw new OncoException("строковый код CodeICD у строки null или пустой: " + pMorbidity.getCodeIcdNum() + " подробно: " + pMorbidity);
            }

            if (pMorbidity.getLocalization() == null || pMorbidity.getLocalization().isEmpty()) {
                throw new OncoException("строковое значение локализации у строки null или пустой: " + pMorbidity.getCodeIcdNum() + " подробно: " + pMorbidity);
            }


        }
        System.out.println("Валидация прошла успешно!");

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
        System.out.println("   !!! writing to OLAP DB file " + getFileName());
        try {
            conn = DbConnection.getConnection();
            System.out.println("Insert Rows in given table");
            stmt = conn.createStatement();
            List<Integer> listAge = getListAgeCode();
            for (int i = 0, rowCurrent = 0; i < listOfParsedRows.size(); i++) {
                PrMortality parsedRow = (PrMortality) listOfParsedRows.get(i);
                String insertRow = "";
                for (int j = 0; j < 16; j++) {
                    insertRow = String.format(""
                            + "INSERT INTO mortalityolap "
                            + "(id,  region_id,  year,  localization_id,  sex_id,  age_id,   filename,  mortality) "
                            + "VALUE (null, '%s', '%s', '%s', '%s' , '%s' , '%s' , '%s')",
                            parsedRow.getRegion(),
                            parsedRow.getYear(),
                            parsedRow.getCodeIcdNum(),
                            parsedRow.getSex(),
                            listAge.get(j),
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
        System.out.println("   !!! writing to DB Simple variant " + file.getName());
        try {
            conn = DbConnection.getConnection();
            System.out.println("Insert Rows in given table");
            stmt = conn.createStatement();
            for (int i = 0, rowCurrent = 0; i < listOfParsedRows.size(); i++) {
                PrMortality parsedRow = (PrMortality) listOfParsedRows.get(i);
                String insertRow = String.format(""
                        + "INSERT INTO mortalitysimple "
                        + "(id, region_id, year, localization_id, sex_id, filename, "
                        + " ageall, age0_4, age5_9, age10_14, age15_19, age20_24,"
                        + " age25_29, age30_34, age35_39, "
                        + " age40_44, age45_49, age50_54, age55_59, age60_64, "
                        + " age65_69, age70) "
                        + " VALUE (null, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', "
                        + "'%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', "
                        + "'%s', '%s')",
                        parsedRow.getRegion(),
                        parsedRow.getYear(),
                        parsedRow.getCodeIcdNum(),
                        parsedRow.getSex(),
                        parsedRow.getFileName(),
                        parsedRow.getAgeall(),
                        parsedRow.getAge0_4(),
                        parsedRow.getAge5_9(),
                        parsedRow.getAge10_14(),
                        parsedRow.getAge15_19(),
                        parsedRow.getAge20_24(),
                        parsedRow.getAge25_29(),
                        parsedRow.getAge30_34(),
                        parsedRow.getAge35_39(),
                        parsedRow.getAge40_44(),
                        parsedRow.getAge45_49(),
                        parsedRow.getAge50_54(),
                        parsedRow.getAge55_59(),
                        parsedRow.getAge60_64(),
                        parsedRow.getAge65_69(),
                        parsedRow.getAge70Plus());

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
    public int getNumberOfRowsCouldBeParsed() throws OncoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getFileName() {
        return this.file.getName();
    }

    @Override
    public boolean isRowWithData(Row row) {
        int orientirColumn = 0;
        if (row == null) {
            return false;
        }
        if (row.getCell(orientirColumn) == null) {
            return false;
        }
        if (row.getCell(orientirColumn).getCellType() != Cell.CELL_TYPE_STRING) {
            return false;
        }
        String value = row.getCell(orientirColumn).getStringCellValue();
        if (!Character.isDigit(value.charAt(0))
                //&& !value.startsWith("не ук.")
                && !value.startsWith("все возр.")) {
            return false;
        }
        return true;
    }

    protected Map<String, Integer> fillAgeCode() {
        Map<String, Integer> map = new HashMap<>();
        map.put("не ук.", -1);
        map.put("все возр.", 10);
        map.put("все", 10);
        map.put("0-4", 20);
        map.put("5-9", 30);
        map.put("10-14", 40);
        map.put("15-19", 70);
        map.put("20-24", 80);
        map.put("25-29", 90);
        map.put("30-34", 110);
        map.put("35-39", 120);
        map.put("40-44", 130);
        map.put("45-49", 140);
        map.put("50-54", 150);
        map.put("55-59", 160);
        map.put("60-64", 170);
        map.put("65-69", 180);
        map.put("70&+", 220);
        return map;
    }

    private List<Integer> getListAgeCode() {
        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.add(70);
        list.add(80);
        list.add(90);
        list.add(110);
        list.add(120);
        list.add(130);
        list.add(140);
        list.add(150);
        list.add(160);
        list.add(170);
        list.add(180);
        list.add(220);
        return list;
    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        ParseMortality pm1 = new ParseMortality(null, 1);

        //  Workbook wb = 
        for (int i = 1; i < 18; i++) {
        }
    }

    protected Map<String, Integer> fillLocalozationCode() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Всего", 1);
        map.put("губы, полости рта и глотки", 102);
        map.put("пищевода", 4);
        map.put("желудка", 5);
        map.put("тонкого кишечника", 103);
        map.put("ободочной кишки", 6);
        map.put("прямой кишки, ректосигм. соед.", 7);
        map.put("др. органов пищеварения", 104);
        map.put("гортани", 10);
        map.put("трахеи, бронхов и легких", 11);
        map.put("др. органов дыхания", 108);
        map.put("костей и суставных хрящей", 12);
        map.put("меланома кожи", 13);
        map.put("др.новообразования кожи", 14);
        map.put("соедин. и др. мягких тканей", 15);
        map.put("молочной железы", 16);
        map.put("шейки матки", 17);
        map.put("тела матки", 18);
        map.put("яичников", 19);
        map.put("др. неуточ. женских половых органов", 105);
        map.put("предстательной железы", 21);
        map.put("яичка", 22);
        map.put("др. мужских половых органов", 109);
        map.put("почки", 23);
        map.put("мочевого пузыря", 24);
        map.put("др. мочевых органов", 106);
        map.put("ЦНС", 26);
        map.put("щитовидной железы", 27);
        map.put("др. и неуточ. локализации", 107);
        map.put("лимфатической и кроветворной тканей", 28);
        map.put("лейкемии", 42);
        return map;
    }

    protected int getAgeCode(Object value) {
        if (!mapAges.containsKey(value.toString().toLowerCase())) {
            return 0;
        }
        return this.mapAges.get(value.toString().toLowerCase());
    }

    private void updateRowOncoAllLocalizations(PrMortality rowOncoAllLocalizations, PrMortality thisrowOnco) {
        rowOncoAllLocalizations.updateAgeall(thisrowOnco.getAgeall());
        rowOncoAllLocalizations.updateAge0_4(thisrowOnco.getAge0_4());
        rowOncoAllLocalizations.updateAge5_9(thisrowOnco.getAge5_9());
        rowOncoAllLocalizations.updateAge10_14(thisrowOnco.getAge10_14());
        rowOncoAllLocalizations.updateAge15_19(thisrowOnco.getAge15_19());
        rowOncoAllLocalizations.updateAge20_24(thisrowOnco.getAge20_24());
        rowOncoAllLocalizations.updateAge25_29(thisrowOnco.getAge25_29());
        rowOncoAllLocalizations.updateAge30_34(thisrowOnco.getAge30_34());
        rowOncoAllLocalizations.updateAge35_39(thisrowOnco.getAge35_39());
        rowOncoAllLocalizations.updateAge40_44(thisrowOnco.getAge40_44());
        rowOncoAllLocalizations.updateAge45_49(thisrowOnco.getAge45_49());
        rowOncoAllLocalizations.updateAge50_54(thisrowOnco.getAge50_54());
        rowOncoAllLocalizations.updateAge55_59(thisrowOnco.getAge55_59());
        rowOncoAllLocalizations.updateAge60_64(thisrowOnco.getAge60_64());
        rowOncoAllLocalizations.updateAge65_69(thisrowOnco.getAge65_69());
        rowOncoAllLocalizations.updateAge70Plus(thisrowOnco.getAge70Plus());
    }
}
