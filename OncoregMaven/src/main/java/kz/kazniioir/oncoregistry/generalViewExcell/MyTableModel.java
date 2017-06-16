package kz.kazniioir.oncoregistry.generalViewExcell;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {

    private Vector<Vector<Object>> data;

    public MyTableModel(Vector<Vector<Object>> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return data.get(0).size() + 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return rowIndex;
        }
        return data.get(rowIndex).get(columnIndex - 1);
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "";
        }
        char ch = (char) (64 + column);
        return (column - 1) + " " + ch + ""; //To change body of generated methods, choose Tools | Templates.
    }

    public static void main(String[] args) {
        System.out.println((int) 'A');
    }
}
