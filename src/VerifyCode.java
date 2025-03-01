
package TMS;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class VerifyCode extends JFrame implements ActionListener {
    private JTextField usernameField, codeField;
    private JButton verifyButton;
    private Map<String, String> passwordResetTokens;

    public VerifyCode(Map<String, String> passwordResetTokens) {
        this.passwordResetTokens = passwordResetTokens;

        setTitle("Verify Code");
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

        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameField, gbc);

        // Verification code field
        JLabel codeLabel = new JLabel("Verification Code:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(codeLabel, gbc);

        codeField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(codeField, gbc);

        // Verify button
        verifyButton = new JButton("Verify");
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(verifyButton, gbc);
        verifyButton.addActionListener(this);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String code = codeField.getText();

        if (passwordResetTokens.containsKey(username) && passwordResetTokens.get(username).equals(code)) {
            new ResetPassword(username).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid verification code", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


