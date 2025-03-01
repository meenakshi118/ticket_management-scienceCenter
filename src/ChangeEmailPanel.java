package TMS;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChangeEmailPanel extends JPanel implements ActionListener {
    private JComboBox<String> usernameComboBox;
    private JTextField newEmailField;
    private JButton changeEmailButton;
    private ConnectionClass database;

    public ChangeEmailPanel() {
        database = new ConnectionClass(); // Initialize your database connection

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username selection
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Select Username:"), gbc);

        gbc.gridx = 1;
        usernameComboBox = new JComboBox<>();
        loadUsernames();
        add(usernameComboBox, gbc);

        // New email label and field
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("New Email:"), gbc);

        gbc.gridx = 1;
        newEmailField = new JTextField(20);
        add(newEmailField, gbc);

        // Change email button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        changeEmailButton = new JButton("Change Email");
        changeEmailButton.addActionListener(this);
        add(changeEmailButton, gbc);

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
        String selectedUsername = (String) usernameComboBox.getSelectedItem();
        String newEmail = newEmailField.getText();

        // Validate input
        if (selectedUsername == null || newEmail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a username and enter a new email.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Call method to change email in the database (you need to implement this method in ConnectionClass)
            boolean success = database.changeUserEmail(selectedUsername, newEmail);
            if (success) {
                JOptionPane.showMessageDialog(this, "Email changed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Optionally, clear fields after successful change
                newEmailField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to change email. Please check your details and try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

