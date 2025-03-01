package TMS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.border.LineBorder;

class AddUserPanel extends JPanel implements ActionListener {
    private JTextField usernameField, emailField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private ConnectionClass database;
   

    public AddUserPanel() {
        UserDetailsPanel udp = new UserDetailsPanel();
        database = new ConnectionClass();
        
        
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(20);
        add(usernameField, gbc);

        // Password label and field
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passwordLabel = new JLabel("Password:");
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        add(passwordField, gbc);

        // Email label and field
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel emailLabel = new JLabel("Email:");
        add(emailLabel, gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        add(emailField, gbc);

        // Register button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        add(registerButton, gbc);
        
        setOpaque(false);
        setPreferredSize(new Dimension(400, 200)); // Set preferred size for the panel
        setBorder(new LineBorder(Color.BLACK, 2)); // Add a black border
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String email = emailField.getText();

        // Validate input
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Registration Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (isUsernameDuplicate(username)) {
                JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different username.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Insert user into database
                database.saveUser(username, password, email);

                // Refresh user details table
             
                

                JOptionPane.showMessageDialog(this, "User added successfully.");
                usernameField.setText("");
                emailField.setText("");
                passwordField.setText("");
                
                
            }
        }
    }

    private boolean isUsernameDuplicate(String username) {
        try {
            String query = "SELECT COUNT(*) FROM login WHERE username = ?";
            PreparedStatement pst = database.con.prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
