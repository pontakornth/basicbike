package basicbike.gui;

import basicbike.dao.DaoFactory;
import basicbike.domain.BikeRental;
import basicbike.model.Bike;
import basicbike.model.BikeItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainGui extends JFrame {

    private final DaoFactory daoFactory;
    private final BikeRental bikeRental;
    private final FilterPanel filterPanel;
    private final ResultTable resultTable;
    private final ActionPanel actionPanel;
    private List<BikeItem> items;

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

        public ResultTable(List<BikeItem> bikeItems) {
            // TODO: Make it actually work.
            String[] columnName = new String[]{"Bike ID", "Model", "Type", "Size", "Status"};
            // TODO Extract method
            Object[][] data = new Object[bikeItems.size()][5];
            for (int i = 0; i < bikeItems.size(); i++ ) {
                BikeItem bikeItem = bikeItems.get(i);
                Object[] row = new Object[5];
                Bike bike = bikeItem.getBike();
                row[0] = bikeItem.getBikeItemId();
                row[1] = bike.getModel();
                row[2] = bike.getType();
                row[3] = bike.getSize();
                String renterId = bikeItem.getRenterId();
                // Maintenance is outside the scope.
                if (renterId == null || renterId.isEmpty()) {
                    row[4] = "Available";
                } else {
                    row[4] = "Rented";
                }
                data[i] = row;
            }
//            Object[][] data = new Object[][]{
//                    {"A1234", "LA Mountain Kun", "Mountain", 20, "Available"},
//                    {"A1234", "LA Mountain Kun", "Mountain", 20, "Available"},
//                    {"A1234", "LA Mountain Kun", "Mountain", 20, "Available"},
//                    {"A1234", "LA Mountain Kun", "Mountain", 20, "Available"},
//            };
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

        public ActionPanel(ActionListener rentAction) {
            // TODO: Add button action
            rentButton = new JButton("Rent");
            rentButton.addActionListener(rentAction);
            setLayout(new FlowLayout());
            add(rentButton);
        }
    }

    private ActionListener getRentAction() {
        return e -> {
            // TODO: Add checking if the bike is already rented.
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            // The constructor automatically set the current time.
            Date currentDate = new Date();

                /*
                 Note:
                 Java does not have built-in date picker.
                 If I add it, it would require another dependency.
                 I wish to simplify current application.
                 */
            JPanel panel = new JPanel(new GridLayout(0, 2));
            JLabel idLabel = new JLabel("National ID or Passport");
            JTextField idInput = new JTextField();
            idInput.setColumns(20);
            JLabel timeLabel = new JLabel("Time");
            JTextField timeInput = new JTextField();
            timeInput.setColumns(20);
            timeInput.setText(timeFormat.format(currentDate));
            JLabel dateLabel = new JLabel("Date");
            JTextField dateInput = new JTextField();
            dateInput.setColumns(20);
            dateInput.setText(dateFormat.format(currentDate));


            panel.add(idLabel);
            panel.add(idInput);
            panel.add(dateLabel);
            panel.add(dateInput);
            panel.add(timeLabel);
            panel.add(timeInput);
            int result = JOptionPane.showConfirmDialog(null, panel, "Rent",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                if (resultTable.table.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "You didn't select any bike.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }

    public MainGui(DaoFactory daoFactory) {
        setLayout(new BorderLayout());
        this.daoFactory = daoFactory;
        this.bikeRental = new BikeRental(daoFactory.getBikeDao(), daoFactory.getBikeItemDao());
        items = this.bikeRental.getAllBikeItems();
        this.filterPanel = new FilterPanel();
        this.resultTable = new ResultTable(items);
        this.actionPanel = new ActionPanel(getRentAction());
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
        // Load data from database
        setVisible(true);
    }
}
