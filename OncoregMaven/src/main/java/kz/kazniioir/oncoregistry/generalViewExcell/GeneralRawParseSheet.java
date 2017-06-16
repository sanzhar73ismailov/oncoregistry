package kz.kazniioir.oncoregistry.generalViewExcell;

import java.util.Vector;
import kz.kazniioir.oncoregistry.OncoException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class GeneralRawParseSheet {

    private Sheet sheet;
    private Vector<Vector<Object>> data = new Vector<>();
    private int rows;
    private int columns;
    private MyTableFrame myTableFrame;

    public GeneralRawParseSheet(Sheet sheet, int columns, int rows) throws OncoException {
        this.sheet = sheet;
        this.columns = columns;
        this.rows = rows;
        fullfillData();
    }

    public void showFrame() {
        myTableFrame = new MyTableFrame(data);

    }

    public Sheet getSheet() {
        return sheet;
    }

    public Vector<Vector<Object>> getData() {
        return data;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    private void fullfillData() throws OncoException {
        for (int rowNum = 0; rowNum < rows; rowNum++) {
            Row rowCurrent = sheet.getRow(rowNum);
            Vector<Object> vector = new Vector<>();

            for (int colNum = 0; colNum < columns; colNum++) {
                Object value = null;
                if (rowCurrent == null) {
                    value = "ROW_N";
                } else {
                    Cell cell = rowCurrent.getCell(colNum);
                    if (cell == null) {
                        //value = "NULL";
                        value = "";
                    } else {
                        if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                            value = "";
                        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            //value = "<" + cell.getStringCellValue().trim() + ">";
                            value = "" + cell.getStringCellValue().trim() + "";
                        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            value = cell.getNumericCellValue();
                        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                            value = cell.getBooleanCellValue();
                        } else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
                            value = cell.getErrorCellValue();
                        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                            value = cell.getCellFormula();
                        } else {
                            throw new OncoException("Unknown type of cell, adress: row "
                                    + rowNum + ", column " + colNum
                                    + ", value=" + cell.getStringCellValue());
                        }
                    }
                    if (value == null) {
                        throw new OncoException("value  не должно быть null");
                    }
                }
                vector.add(value);
            }
            data.add(vector);
        }
    }
}
