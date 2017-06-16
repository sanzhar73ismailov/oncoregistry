package kz.kazniioir.oncoregistry.form1000;

import kz.kazniioir.oncoregistry.form2100.PrMorb2100;
import kz.kazniioir.oncoregistry.OncoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestMyTest {

    public static void main(String[] args) {
        checkVersionOffile();
    }
    
    
    static void checkVersionOffile(){
        File f2003 = new File("C:\\temp\\00_на удал\\excel2007.xls");
        File f2007 = new File("C:\\temp\\00_на удал\\excel2007.xlsx");
        File f111 = new File("S:\\DOCS\\КазНИИОиР_СекторБиостат\\Отдел_попул_регистра\\База\\7 форма\\2010\\35_2003\\01_астана_35_10.xls");
        File f112 = new File("S:\\DOCS\\КазНИИОиР_СекторБиостат\\Отдел_попул_регистра\\База\\7 форма\\2010\\00_test.xlsx");
        File f113 = new File("S:\\DOCS\\КазНИИОиР_СекторБиостат\\Отдел_попул_регистра\\База\\7 форма\\2010\\00_test111.xls");
        try {
            Workbook wb = WorkbookFactory.create(f111);
            //XSSFWorkbook wb2 = new XSSFWorkbook(new FileInputStream(f113));
            //HSSFWorkbook wb2013 = new HSSFWorkbook(new FileInputStream(f113));
            System.out.println("" + wb.getSheetName(0));
            //System.out.println("" + wb.getClass());
        } catch (Exception ex) {
            Logger.getLogger(TestMyTest.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    static String getFirstLetterBig(String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1, value.length());
    }

    public static void generateCode() {
        String[] arr = getAttrNames();
        if (1 == 1) {
            for (String string : arr) {
                System.out.println("public void check" + getFirstLetterBig(string) + "() throws OncoException {");
                System.out.printf("    int sumInTable = ((PrMorb2100) listOfParsedRows.get(0)).get%s();\n", getFirstLetterBig(string));
                System.out.println("    int sumCalc = 0;");
                System.out.println("    for (int i = 2; i < listOfParsedRows.size(); i++) {");
                System.out.println("        PrMorb2100 row = (PrMorb2100) listOfParsedRows.get(i);");
                System.out.printf("        sumCalc += row.get%s();\n}\n", getFirstLetterBig(string));

                System.out.println("    if (sumInTable != sumCalc) {");
                System.out.printf("        throw new OncoException(\"сумма не сходится для %s \" + sumInTable +\" \" + sumCalc);\n}}\n", string);

            }
        }
        if (1 == 1) {
            for (String string : arr) {
                System.out.println("parsedRow.get" + getFirstLetterBig(string) + "(),");
            }
        }
    }

    public static String[] getAttrNames() {
        return new String[]{
            "registeredInTheStartOfYear",
            "vzyatoNaUchetThisYear_WithBeforeDefinedDiagnosis",
            "vzyatoNaUchetThisYear_FirstTime",
            "from3Group_DetectedDuringProfAll",
            "from3Group_DetectedDuringProf01And02Stage",
            "from3Group_DiagnosoConfirmedMorfologically",
            "from3Group_HadStage1and2",
            "from3Group_HadStage3",
            "from3Group_HadStage4",
            "struckOffTheRegisterAll",
            "struckOffTheRegisterGoOut",
            "struckOffTheRegisterDiagNotConfirmed",
            "struckOffTheRegisterNoInfo",
            "struckOffTheRegisterWithDiagBazalioma",
            "struckOffTheRegisterDiedFromAnotherDisease",
            "registeredInTheEndOfYearAll",
            "registeredInTheEndOfYear5YearsAndMore"
        };
    }

    public static void main3(String[] args) {
        int res = JOptionPane.showConfirmDialog(null, "продолжить");
        System.out.println("res = " + res);

    }
    public static final String[] arr = {
        "ageall",
        "age0_4",
        "age5_9",
        "age10_14",
        "age15_17",
        "age18_19",
        "age20_24",
        "age25_29",
        "age15_29",
        "age30_34",
        "age35_39",
        "age40_44",
        "age45_49",
        "age50_54",
        "age55_59",
        "age60_64",
        "age65_69",
        "age70_74",
        "age75_79",
        "age80_84",
        "age85"
    };

    public static void main23(String[] args) {
        for (String string : arr) {
            //System.out.println("+ \" " + string + " INTEGER, \"");
            System.out.println("parsedRow.getA" + string.substring(1, string.length()) + "(),");
        }
    }

    public static void main2(String[] args) throws IOException, OncoException {
        final String generalPath = "S:\\DOCS\\КазНИИОиР_СекторБиостат\\Отдел_попул_регистра\\База\\7 форма\\2004\\00test.xls";
        final String generalPath2 = "S:\\DOCS\\КазНИИОиР_СекторБиостат\\Отдел_попул_регистра\\База\\7 форма\\2004\\00test222.xls";
        File file = new File(generalPath);
        FileInputStream fis = new FileInputStream(file);
        POIFSFileSystem fyleSystem = new POIFSFileSystem(fis);


        HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(file)));
        int totalRows = 200;

        HSSFSheet sheetSource = workbook.getSheet("форма");
        HSSFSheet sheetCloned = workbook.createSheet("форма1");
        List<HSSFRow> rows = new ArrayList<>();


        for (int i = 0; i < totalRows; i++) {
            HSSFRow row = sheetSource.getRow(i);
            if (row != null) {
                rows.add(row);
                Iterator cellIter = row.cellIterator();
                while (cellIter.hasNext()) {
                    HSSFCell cell = (HSSFCell) cellIter.next();
                    System.out.print(cell + ";");
                }
                System.out.println("");
            }
        }


        for (int i = 0; i < rows.size(); i++) {
            HSSFRow row = rows.get(i);
            HSSFRow hSSFRowCr = sheetCloned.createRow(i);
            if (row != null) {
                hSSFRowCr.setHeight(row.getHeight());
            }
            Iterator cellIter = row.cellIterator();


            for (int cellNum = 0; cellNum < 1; cellNum++) {
                sheetCloned.setColumnWidth(cellNum, sheetSource.getColumnWidth(cellNum));
                sheetCloned.setDefaultColumnStyle(cellNum, sheetSource.getColumnStyle(cellNum));
            }
            for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {



                // System.out.println("row: " + i + ", cell: " + cellNum);
                try {
                    HSSFCell cell = (HSSFCell) row.getCell(cellNum);
                    HSSFCell cellCreated = hSSFRowCr.createCell(cellNum);
                    if (cell != null) {
                        cellCreated.setCellStyle(cell.getCellStyle());
                    }
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
                    System.out.println("--------------" + "row: " + (i + 1) + ", cell: " + ((char) (cellNum + 1 + 64)));
                }
                //System.out.print(cell);
            }
            // System.out.println("");

        }

//        for (int i = 0; i < totalRecords - 1; i++) {
//            sheetCloned = workbook.cloneSheet(1);
//            sheetCloned.
//        }
//
//        //totalRecords = workbook.getNumberOfSheets();
//        System.out.println("totalRecords after = " + totalRecords);
//
        if (fis != null) {
            fis.close();
        }
        workbook.write(new FileOutputStream(new File(generalPath)));




//            for (int i = 1; i <= totalRecords; i++) {
//                workbook.setSheetName(i, "S" + i);
//            }

    }
}
