package mybeans;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Iterator;

public class DataSheetTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private int columnCount = 3;
    private int rowCount = 1;
    private DataSheet dataSheet = null;
    String[] columnNames = {"Date", "X Value", "Y Value"};
    public DataSheet getDataSheet() {
        return dataSheet;
    }
    public DataSheetTableModel(){
        dataSheet = new DataSheet();
        rowCount = dataSheet.size();
    }
    public void setDataSheet(DataSheet dataSheet) {
        this.dataSheet = dataSheet;
        rowCount = this.dataSheet.size();
        fireDataSheetChange();
    }
    @Override
    public int getColumnCount() {
        return columnCount;
    }
    @Override
    public int getRowCount() {
        return rowCount;
    }
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex >= 0;
    }
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        try {
            double d;
            if (dataSheet != null) {
                if (columnIndex == 0) {
                    dataSheet.getDataItem(rowIndex).setDate((String) value);
                } else if (columnIndex == 1) {
                    d = Double.parseDouble((String) value);
                    dataSheet.getDataItem(rowIndex).setX(d);
                } else if (columnIndex == 2) {
                    d = Double.parseDouble((String) value);
                    dataSheet.getDataItem(rowIndex).setY(d);
                }
            }
            fireDataSheetChange();
        } catch (Exception ex) {}
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    // TODO Auto-generated method stub
        if (dataSheet != null) {
            if (columnIndex == 0)
                return dataSheet.getDataItem(rowIndex).getDate();
            else if (columnIndex == 1)
                return dataSheet.getDataItem(rowIndex).getX();
            else if (columnIndex == 2)
                return dataSheet.getDataItem(rowIndex).getY();
        }
        return null;
    }
    public void setRowCount(int rowCount) {
        if (rowCount > 0)
            this.rowCount = rowCount;
    }
    // список слухачів
    private ArrayList<DataSheetChangeListener> listenerList = new ArrayList<>();
    // один універсальний об’єкт-подія
    private DataSheetChangeEvent event = new DataSheetChangeEvent(this);
    // метод, що приєднує слухача події
    public void addDataSheetChangeListener(DataSheetChangeListener l) {
        listenerList.add(l);
    }
    // метод, що від’єднує слухача події
    public void removeDataSheetChangeListener(DataSheetChangeListener l) {
        listenerList.remove(l);
    }
    // метод, що оповіщає зареєстрованих слухачів про подію
    protected void fireDataSheetChange() {
        Iterator<DataSheetChangeListener> i = listenerList.iterator();
        while ( i.hasNext() )
            (i.next()).dataChanged(event);
    }
}