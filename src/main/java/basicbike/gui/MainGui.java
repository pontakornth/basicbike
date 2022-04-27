package basicbike.gui;

import basicbike.dao.DaoFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainGui extends JFrame {

    private final DaoFactory daoFactory;
    private final FilterPanel filterPanel;
    private final ResultTable resultTable;
    private final ActionPanel actionPanel;

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
            String[] columnName = new String[]{"Bike ID", "Model", "Type", "Size", "Status"};
            // TODO: Use data from database
            Object[][] data = new Object[][]{
                    {"A1234", "LA Mountain Kun", "Mountain", 20, "Available"},
                    {"A1234", "LA Mountain Kun", "Mountain", 20, "Available"},
                    {"A1234", "LA Mountain Kun", "Mountain", 20, "Available"},
                    {"A1234", "LA Mountain Kun", "Mountain", 20, "Available"},
            };
            table = new JTable();
            DefaultTableModel model = new DefaultTableModel(data, columnName);
            table.setModel(model);
            table.getColumn("Model").setPreferredWidth(200);
            table.setFillsViewportHeight(true);
            // The table is not editable because it is outside of scope.
            table.setDefaultEditor(Object.class, null);
            setLayout(new BorderLayout());
            add(new JScrollPane(table), BorderLayout.SOUTH);
        }
    }

    private static class ActionPanel extends JPanel {
        private final JButton rentButton;

        public ActionPanel() {
            // TODO: Add button action
            rentButton = new JButton("Rent");
            rentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO: Add checking if the bike is already rented.
                    JPanel panel = new JPanel(new GridLayout(3,2));
                    JLabel idLabel = new JLabel("National ID or Passport");
                    JTextField idInput = new JTextField();
                    idInput.setColumns(20);
                    JLabel timeLabel = new JLabel("Time");
                    JTextField timeInput = new JTextField();
                    timeInput.setColumns(20);
                    panel.add(idLabel);
                    panel.add(idInput);
                    panel.add(timeLabel);
                    panel.add(timeInput);
                    int result = JOptionPane.showConfirmDialog(null, panel, "Rent",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                }
            });
            setLayout(new FlowLayout());
            add(rentButton);
        }
    }

    public MainGui(DaoFactory daoFactory) {
        setLayout(new BorderLayout());
        this.daoFactory = daoFactory;
        this.filterPanel = new FilterPanel();
        this.resultTable = new ResultTable();
        this.actionPanel = new ActionPanel();
        add(filterPanel, BorderLayout.NORTH);
        add(resultTable, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);
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
