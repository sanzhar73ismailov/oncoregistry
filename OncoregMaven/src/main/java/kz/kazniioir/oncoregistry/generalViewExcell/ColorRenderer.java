package kz.kazniioir.oncoregistry.generalViewExcell;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class ColorRenderer extends DefaultTableCellRenderer {

    Color backgroundColor = getBackground();
    JTable table;

    public ColorRenderer(JTable table) {
        this.table = table;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(
                table, obj, isSelected, hasFocus, row, column);
        //System.out.println();
        if (isSelected) {
            // cell.setBackground(Color.green);
        } else {
            System.out.println("v: " + cell.getClass());
            /*
             if (row % 2 == 0) {
             cell.setBackground(Color.cyan);
             } else {
             cell.setBackground(Color.lightGray);
             }
             * */
        }
        return cell;
    }
    
    
}
