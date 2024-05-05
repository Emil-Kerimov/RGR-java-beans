package myApplication;

import mybeans.Data;
import mybeans.DataSheet;
import mybeans.DataSheetGraph;
import mybeans.DataSheetTable;
import xml.DataSheetSaveToXML;

import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.event.*;

public class Test extends JFrame {
    private final JFileChooser fileChooser = new JFileChooser();
    private JPanel contentPane;
    private DataSheet dataSheet;
    private DataSheetGraph dataSheetGraph;
    private DataSheetTable dataSheetTable;
    private JButton readButton;
    private JButton saveButton;
    private JButton clearButton;
    private JButton exitButton;

    public Test() {
        setContentPane(contentPane);
        dataSheet = new DataSheet();
        dataSheet.addDataItem(new Data());
        dataSheetGraph.setDataSheet(dataSheet);
        dataSheetTable.getTableModel().setDataSheet(dataSheet);
        fileChooser.setCurrentDirectory(new java.io.File("."));



        exitButton.addActionListener(e -> {
            dispose();
        });

        clearButton.addActionListener(e -> {
            dataSheet = new DataSheet();
            dataSheet.addDataItem(new Data());
            dataSheetTable.getTableModel().setDataSheet(dataSheet);
            dataSheetTable.revalidate();
            dataSheetGraph.setDataSheet(dataSheet);
        });

        saveButton.addActionListener(e -> {
            if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(null)) {
                String fileName = fileChooser.getSelectedFile().getPath();
                try {
                    DataSheetSaveToXML.marshalDataToXML(fileName,dataSheet);
                    JOptionPane.showMessageDialog(null,
                            "File " + fileName.trim() + " saved!", "Результати збережені",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (JAXBException jaxbException) {
                    JOptionPane.showMessageDialog(null,
                            "File " + fileName.trim() + " not saved", "Результати не збережені",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        readButton.addActionListener(e -> {
            if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
                String fileName = fileChooser.getSelectedFile().getPath();
                try {
                    dataSheet = DataSheetSaveToXML.unmarshalOutXMLs(fileName);
                } catch (JAXBException jaxbException) {
                    jaxbException.printStackTrace();
                }
                dataSheetTable.getTableModel().setDataSheet(dataSheet);
                dataSheetTable.revalidate();
                dataSheetTable.revalidate();
                dataSheetGraph.setDataSheet(dataSheet);
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        dataSheetTable.getTableModel().addDataSheetChangeListener(e -> {
            dataSheetGraph.revalidate();
            dataSheetGraph.repaint();
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Test frame = new Test();
                frame.pack();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
