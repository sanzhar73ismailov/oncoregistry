package kz.kazniioir.oncoregistry.generalViewExcell;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;
import javax.swing.CellRendererPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class MyTableFrame extends JFrame {

    JTable table;
    private Vector<Vector<Object>> data;
    private Vector<String> columnNames = new Vector<>();

    public MyTableFrame(Vector<Vector<Object>> data) {
        this.data = data;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 800);

        TableModel tableModel = new MyTableModel(data);
        table = new JTable(tableModel);
       
       // table.setDefaultRenderer(String.class, new ColorRenderer(tableModel));
//        TableColumn column = null;
//        TableCellRenderer rend = new  ColorRenderer(table);
//        for(int colN = 0; colN < table.getColumnCount(); colN++){
//             column = table.getColumnModel().getColumn(colN);
//            column.setCellRenderer(rend);
//        }
        
        this.add(new JScrollPane(table));
        this.setVisible(true);
        
    }
}
