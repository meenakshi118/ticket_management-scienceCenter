
package TMS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResetPassword extends JFrame implements ActionListener {
    private JPasswordField newPasswordField, confirmPasswordField;
    private JButton resetButton;
    private String username;

    public ResetPassword(String username) {
        this.username = username;

        setTitle("Reset Password");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the frame
        setUndecorated(true); // Remove window decorations
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(230, 230, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // New password field
        JLabel newPasswordLabel = new JLabel("New Password:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(newPasswordLabel, gbc);

        newPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(newPasswordField, gbc);

        // Confirm password field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(confirmPasswordLabel, gbc);

        confirmPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(confirmPasswordField, gbc);

        // Reset button
        resetButton = new JButton("Reset Password");
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(resetButton, gbc);
        resetButton.addActionListener(this);

        add(panel);
        setVisible(true);
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (newPassword.equals(confirmPassword)) {
            try {
                ConnectionClass obj = new ConnectionClass();
                String q = "UPDATE login SET password = '" + newPassword + "' WHERE username = '" + username + "'";
                obj.st.executeUpdate(q);
                JOptionPane.showMessageDialog(this, "Password reset successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                new Login().setVisible(true);
                this.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
