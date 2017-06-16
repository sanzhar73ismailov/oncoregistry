package kz.kazniioir.oncoregistry.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import kz.kazniioir.oncoregistry.OncoException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class WordRead {

    public static String filesDir = "S:\\DOCS\\ELSI_TECH\\02_Хромтау_ЦРБ\\tables_907\\";

    public static void main(String[] args) {

        String fileName = filesDir + "907_Перечень_форм.docx";
        List<Prikaz907Form> rows = readTable(fileName);
        System.out.println("rows = " + rows.size());
        for (Prikaz907Form prikaz907Form : rows) {
            System.out.println("prikaz907Form = " + prikaz907Form);
        }
        writeToExcelFile(rows);



    }

    public static void writeToExcelFile(List<Prikaz907Form> list) {
        Workbook wb = new XSSFWorkbook();
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(filesDir + "workbook.xlsx");
            Sheet sheet = wb.createSheet();

            Row row = sheet.createRow(0);
            int colNum = 0;
            Cell cell = row.createCell(colNum++, Cell.CELL_TYPE_STRING);
            cell.setCellValue("Сквозной номер");

            cell = row.createCell(colNum++, Cell.CELL_TYPE_STRING);
            cell.setCellValue("Внутрениий поряд номер");

            cell = row.createCell(colNum++, Cell.CELL_TYPE_STRING);
            cell.setCellValue("Номер формы");


            cell = row.createCell(colNum++, Cell.CELL_TYPE_STRING);
            cell.setCellValue("Наимен на русском");

            cell = row.createCell(colNum++, Cell.CELL_TYPE_STRING);
            cell.setCellValue("Наимен на казахском");

            cell = row.createCell(colNum++, Cell.CELL_TYPE_STRING);
            cell.setCellValue("Вид документа");

            cell = row.createCell(colNum++, Cell.CELL_TYPE_STRING);
            cell.setCellValue("Формат");

            cell = row.createCell(colNum++, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue("Кол-во страниц");

            cell = row.createCell(colNum++, Cell.CELL_TYPE_STRING);
            cell.setCellValue("Срок хранения");

            cell = row.createCell(colNum++, Cell.CELL_TYPE_STRING);
            cell.setCellValue("для каких организаций");


            for (int rowNum = 0; rowNum < list.size(); rowNum++) {
                Prikaz907Form item = list.get(rowNum);
                row = sheet.createRow(rowNum + 1);
                colNum = 0;
                cell = row.createCell(colNum++, Cell.CELL_TYPE_NUMERIC);
                cell.setCellValue(item.getGeneralId());

                cell = row.createCell(colNum++, Cell.CELL_TYPE_NUMERIC);
                cell.setCellValue(item.getId());

                cell = row.createCell(colNum++, Cell.CELL_TYPE_STRING);
                cell.setCellValue(item.getNumber());


                cell = row.createCell(colNum++, Cell.CELL_TYPE_STRING);
                cell.setCellValue(item.getNameRus());

                cell = row.createCell(colNum++, Cell.CELL_TYPE_STRING);
                cell.setCellValue(item.getNameKaz());

                cell = row.createCell(colNum++, Cell.CELL_TYPE_STRING);
                cell.setCellValue(item.getDocumentType());

                cell = row.createCell(colNum++, Cell.CELL_TYPE_STRING);
                cell.setCellValue(item.getFormat());

                cell = row.createCell(colNum++, Cell.CELL_TYPE_NUMERIC);
                if (item.getPages() > 0) {
                    cell.setCellValue(item.getPages());
                }

                cell = row.createCell(colNum++, Cell.CELL_TYPE_STRING);
                if (item.getStorageLife() == -1) {
                    cell.setCellValue("дмн");
                } else {
                    cell.setCellValue(item.getStorageLife());
                }

                cell = row.createCell(colNum++, Cell.CELL_TYPE_STRING);
                cell.setCellValue(item.getOrganizations());

            }



            wb.write(fileOut);
            fileOut.close();
        } catch (Exception ex) {
            Logger.getLogger(WordRead.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static List<Prikaz907Form> readTable(String fileName) {
        List<Prikaz907Form> retList = new ArrayList<>();
        File file = new File(fileName);
        if (file.exists()) {
            System.out.println("file " + file + " exists!");
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            XWPFDocument doc = new XWPFDocument(fis);
            List<XWPFTable> tables = doc.getTables();
            Prikaz907Form prikazRow = null;


            int generalCount = 0;


            XWPFTableCell cell = null;
            OUT:
            for (int tableNo = 0; tableNo < tables.size(); tableNo++) {

                String organizations = null;
                XWPFTable table = tables.get(tableNo);
                switch (tableNo) {
                    case 0:
                        organizations = "В СТАЦИОНАРЕ";
                        break;
                    case 1:
                        organizations = "В СТАЦИОНАРАХ И АМБУЛАТОРНО-ПОЛИКЛИНИЧЕСКИХ ОРГАНИЗАЦИЯХ";
                        break;
                    case 2:
                        organizations = "В АМБУЛАТОРНО- ПОЛИКЛИНИЧЕСКИХ ОРГАНИЗАЦИЯХ";
                        break;
                    case 3:
                        organizations = "ДРУГИХ ТИПОВ МЕДИЦИНСКИХ ОРГАНИЗАЦИЙ";
                        break;
                    case 4:
                        organizations = "ОРГАНИЗАЦИИ СУДЕБНО-МЕДИЦИНСКОЙ ЭКСПЕРТИЗЫ";
                        break;
                    case 5:
                        organizations = "ЛАБОРАТОРИЙ В СОСТАВЕ МЕДИЦИНСКИХ ОРГАНИЗАЦИЙ";
                        break;
                    case 6:
                        organizations = "ОРГАНИЗАЦИИ СЛУЖБЫ КРОВИ";
                        break;
                }
                if (tableNo == 7) {
                    break;
                }
                List<XWPFTableRow> rows = table.getRows();

                for (int rowNum = 0; rowNum < rows.size(); rowNum++) {

                    int id = 0;
                    String number = null;
                    String nameRus = null;
                    String nameKaz = null;
                    String documentType = null;
                    String format = null;

                    int pages = 0;
                    int storageLife = 0;
                    XWPFTableRow row = rows.get(rowNum);

                    if (rowNum < 2) {
                        continue;
                    }
                    try {

                        prikazRow = new Prikaz907Form();
                        List<XWPFTableCell> cells = row.getTableCells();

                        cell = cells.get(0);
                        Matcher matcher = Pattern.compile("\\d+").matcher(cell.getText());
                        if (matcher.find()) {
                            id = Integer.parseInt(matcher.group());
                        } else {
                            throw new OncoException("отсутствует id");
                        }



                        cell = cells.get(1);
                        SkobkaFind skobkaFind = new SkobkaFind(cell.getText());
                        nameKaz = skobkaFind.getKazVal();
                        nameRus = skobkaFind.getRusVal();

                        cell = cells.get(2);
                        number = cell.getText();

                        cell = cells.get(3);
                        format = cells.get(3).getText();

                        cell = cells.get(4);
                        documentType = cell.getText();
                        if (documentType.toLowerCase().startsWith("бланк")) {
                            //documentType = documentType.substring(0, documentType.indexOf(','));
                            documentType = "Бланк";
                        } else if (documentType.toLowerCase().startsWith("дәптер")) {
                            documentType = "Тетрадь";
                        } else if (documentType.toLowerCase().startsWith("журнал")) {
                            documentType = "Журнал";
                        } else if (documentType.startsWith("Кітап") || documentType.startsWith("Книга")) {
                            documentType = "Книга";
                        }



                        matcher = Pattern.compile("\\d+").matcher(cell.getText());
                        if (matcher.find()) {
                            pages = Integer.parseInt(matcher.group());
                        }

                        cell = cells.get(5);
                        matcher = Pattern.compile("\\d+").matcher(cell.getText());
                        if (matcher.find()) {
                            storageLife = Integer.parseInt(matcher.group());
                        } else {
                            if (cell.getText().contains("дмн")) {
                                storageLife = -1;
                            } else {
                                throw new OncoException("отсутствует срок хранения");
                            }
                        }

                        prikazRow.setId(id);
                        prikazRow.setNameKaz(nameKaz);
                        prikazRow.setNameRus(nameRus);
                        prikazRow.setNumber(number);
                        prikazRow.setFormat(format);
                        prikazRow.setDocumentType(documentType);
                        prikazRow.setPages(pages);
                        prikazRow.setStorageLife(storageLife);
                        prikazRow.setOrganizations(organizations);
                        prikazRow.setGeneralId(++generalCount);
                        retList.add(prikazRow);
                    } catch (Exception ex) {

                        String errorMess = "tableNo: " + tableNo + ", id: " + id + ", " + ex.getCause() + ": " + ex.getMessage() + ": " + rowNum + ", value of cell=" + cell.getText();
                        System.out.println(errorMess);
                        ex.printStackTrace();
                        int res = JOptionPane.showConfirmDialog(null, errorMess
                                + "\nпродолжить?");
                        if (res == JOptionPane.YES_OPTION) {
                            //return Collections.emptyList();
                        } else {
                            break OUT;
                        }

                    }
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return retList;
    }
}
