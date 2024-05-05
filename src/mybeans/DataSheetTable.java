package mybeans;

import javax.swing.*;

public class DataSheetTable{
    private JButton addButton;
    private JButton delButton;
    private JScrollPane scrollPane;
    private JTable table;
    private JPanel rootPane;
    private DataSheetTableModel tableModel;

    private void createUIComponents() {

        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        scrollPane.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        tableModel = new DataSheetTableModel();
        table = new JTable();
        table.setModel(tableModel);

        addButton = new JButton();
        addButton.addActionListener(e -> {
            tableModel.setRowCount(tableModel.getRowCount() + 1);
            tableModel.getDataSheet().addDataItem(new Data());
            table.revalidate();
            tableModel.fireDataSheetChange();
        });

        delButton = new JButton();
        delButton.addActionListener(e -> {
            if (tableModel.getRowCount() > 1) {
                tableModel.setRowCount(tableModel.getRowCount() - 1);
                tableModel.getDataSheet().removeDataItem(tableModel.getDataSheet().size() - 1);
                table.revalidate();
            }
            tableModel.fireDataSheetChange();
        });

    }

    public DataSheetTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DataSheetTableModel tableModel) {
        this.tableModel = tableModel;
        table.revalidate();
    }

    public void revalidate() {
        if (table != null) table.revalidate();
    }

}