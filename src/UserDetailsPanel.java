

package TMS;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class UserDetailsPanel extends JPanel {
   
    private JTable userTable;
    private DefaultTableModel tableModel;
    private ConnectionClass database;

    public UserDetailsPanel() {
        
        database = new ConnectionClass();
        setLayout(new BorderLayout());

        // Table model to hold user details
        tableModel = new DefaultTableModel(new Object[]{"Username", "Password", "Email"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };

        // User table
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load user details from database
        loadUserDetails();
        
        setOpaque(false);
    }

    public void loadUserDetails() {
        try {
            // Start transaction
            database.con.setAutoCommit(false);

            ConnectionClass obj = new ConnectionClass();
            String query = "SELECT username, password, email FROM login WHERE username != 'admin'";
            ResultSet rs = obj.st.executeQuery(query);

            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                tableModel.addRow(new Object[]{username, password, email});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


