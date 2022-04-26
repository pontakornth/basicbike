package basicbike.gui;

import basicbike.dao.DaoFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainGui extends JFrame {

    private final DaoFactory daoFactory;
    private final FilterPanel filterPanel;
    private final ResultTable resultTable;

    private static class FilterPanel extends JPanel {
        public final JLabel modelInputLabel;
        public final JTextField modelInput;
        public final JButton searchButton;

        public FilterPanel() {
            setLayout(new FlowLayout());
            modelInputLabel = new JLabel("Model: ");
            modelInput = new JTextField(25);
            searchButton = new JButton("Search");
            add(modelInputLabel);
            add(modelInput);
            add(searchButton);
        }

    }

    private static class ResultTable extends JPanel {
        public final JTable table;
        public ResultTable() {
            // TODO: Make it actually work.
            String[] columnName = new String[]{"Bike ID", "Model", "Type", "Size", "Status", "Action"};
            // TODO: Use data from database
            String[][] data = new String[][]{
                    {"A1234", "LA Mountain Kun", "Mountain", "Available", "Rent"},
                    {"A1234", "LA Mountain Kun", "Mountain", "Available", "Rent"},
                    {"A1234", "LA Mountain Kun", "Mountain", "Available", "Rent"},
                    {"A1234", "LA Mountain Kun", "Mountain", "Available", "Rent"},
            };
            table = new JTable();
            DefaultTableModel model = new DefaultTableModel(data, columnName);
            table.setModel(model);
            table.getColumn("Model").setPreferredWidth(200);
//            table.setFillsViewportHeight(true);
            table.setCellEditor(null);
            setLayout(new BorderLayout());
            add(new JScrollPane(table), BorderLayout.SOUTH);
        }
    }

    public MainGui(DaoFactory daoFactory) {
        setLayout(new BorderLayout());
        this.daoFactory = daoFactory;
        this.filterPanel = new FilterPanel();
        this.resultTable = new ResultTable();
        add(filterPanel, BorderLayout.NORTH);
        add(resultTable, BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                // Close connection with database when the application closes.
                daoFactory.closeConnection();
            }
        });
        pack();
    }

    public void start() {
        setVisible(true);
    }
}
