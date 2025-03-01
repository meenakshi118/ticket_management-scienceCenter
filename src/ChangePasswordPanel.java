package TMS;
      
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.border.LineBorder;

public class ChangePasswordPanel extends JPanel implements ActionListener {
    private JPasswordField existingPasswordField, newPasswordField, confirmPasswordField;
    private JButton changePasswordButton;
    private String username;
    private JComboBox<String> usernameComboBox;
    private ConnectionClass database;

    public ChangePasswordPanel() {
        database = new ConnectionClass(); // Initialize your database connection

        
        setLayout(new GridBagLayout());
        setBackground(new Color(230, 230, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Username selection
        JLabel usernameLabel = new JLabel("Select Username:");
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        usernameComboBox = new JComboBox<>();
        loadUsernames();
        add(usernameComboBox, gbc);


        // Existing password field
        JLabel existingPasswordLabel = new JLabel("Existing Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(existingPasswordLabel, gbc);

        existingPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(existingPasswordField, gbc);

        // New password field
        JLabel newPasswordLabel = new JLabel("New Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(newPasswordLabel, gbc);

        newPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(newPasswordField, gbc);

        // Confirm password field
        JLabel confirmPasswordLabel = new JLabel("Confirm New Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(confirmPasswordLabel, gbc);

        confirmPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(confirmPasswordField, gbc);

        // Change password button
        changePasswordButton = new JButton("Change Password");
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(changePasswordButton, gbc);
        changePasswordButton.addActionListener(this);
        
        setOpaque(false);
        setPreferredSize(new Dimension(400, 200)); // Set preferred size for the panel
        setBorder(new LineBorder(Color.BLACK, 2)); // Add a black border
        
    }

    private void loadUsernames() {
        ArrayList<String> usernames = database.getUsernames();
        for (String username : usernames) {
            usernameComboBox.addItem(username);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String username = (String) usernameComboBox.getSelectedItem();
        String existingPassword = new String(existingPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "New passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            ConnectionClass obj = new ConnectionClass();
            String q = "SELECT password FROM login WHERE username = '" + username + "'";
            ResultSet rs = obj.st.executeQuery(q);

            if (rs.next()) {
                String currentPassword = rs.getString("password");
                if (!currentPassword.equals(existingPassword)) {
                    JOptionPane.showMessageDialog(this, "Existing password is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                q = "UPDATE login SET password = '" + newPassword + "' WHERE username = '" + username + "'";
                obj.st.executeUpdate(q);
                JOptionPane.showMessageDialog(this, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Username not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

