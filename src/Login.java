package TMS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Login extends JFrame implements ActionListener, ItemListener {
    private JLabel l1, l2, l3, l4;
    private JButton b1, b3;
    private JPasswordField pf;
    private JTextField t1;
    private JPanel upperPanel,mainPanel, loginPanel;
    private JCheckBox showPasswordCheckBox;
    private Map<String, String> passwordResetTokens;

    public Login() {
        setTitle("Login Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the frame
        setUndecorated(true); // Remove window decorations

        
        // Upper panel for logo
        upperPanel = new JPanel(new GridBagLayout());
        upperPanel.setBackground(Color.WHITE);
        upperPanel.setPreferredSize(new Dimension(800, 150));

        // Load the image
        ImageIcon img = new ImageIcon(getClass().getResource("/images/logo.jpg"));
        Image i1 = img.getImage().getScaledInstance(800, 100, Image.SCALE_SMOOTH);
        ImageIcon img2 = new ImageIcon(i1);
        JLabel imageLabel = new JLabel(img2);
        upperPanel.add(imageLabel);
        add(upperPanel, BorderLayout.NORTH);

        
        // Main panel for the background image
       mainPanel = new JPanel(new GridBagLayout()) {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/Login1.png"));
        Image img = backgroundImage.getImage();
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }
};
        
        // Transparent panel for the login form
        loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username label and icon
        t1 = new JTextField();
        t1.setPreferredSize(new Dimension(100, 35)); // Set preferred size for username text field
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        loginPanel.add(t1, gbc);

        l1 = new JLabel();
        ImageIcon usernameIcon = new ImageIcon(getClass().getResource("/images/username.png"));
        Image usernameImg = usernameIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        l1.setIcon(new ImageIcon(usernameImg));
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(l1, gbc);

        // Password label and icon
        pf = new JPasswordField();
        pf.setPreferredSize(new Dimension(100, 35)); // Set preferred size for username text field
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        loginPanel.add(pf, gbc);

        l2 = new JLabel();
        ImageIcon passwordIcon = new ImageIcon(getClass().getResource("/images/password.png"));
        Image passwordImg = passwordIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        l2.setIcon(new ImageIcon(passwordImg));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        loginPanel.add(l2, gbc);

        // Show Password checkbox
        showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setBackground(Color.WHITE);
        showPasswordCheckBox.setOpaque(false);
        showPasswordCheckBox.addItemListener(this); // Add item listener to handle state change
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        loginPanel.add(showPasswordCheckBox, gbc);

        // Login button
        b1 = new JButton("Login");
        b1.setPreferredSize(new Dimension(35, 35));
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        gbc.gridx = 2;
        gbc.gridy = 3;
        loginPanel.add(b1, gbc);
        b1.addActionListener(this);

        // Exit button
        b3 = new JButton("Exit");
        b3.setPreferredSize(new Dimension(35, 35));
        b3.setBackground(Color.BLACK);
        b3.setForeground(Color.WHITE);
        gbc.gridx = 3;
        gbc.gridy = 3;
        loginPanel.add(b3, gbc);
        b3.addActionListener(this);

        // Password reset link (like hyperlink on websites)
        JLabel resetLabel = new JLabel("Forgot Password?");
        resetLabel.setForeground(Color.BLUE.darker());
        resetLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.ipady = 20;
        loginPanel.add(resetLabel, gbc);
        resetLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                handlePasswordReset();
            }
        });
        // Constraints for main panel
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.gridx = 1;
        mainGbc.gridy = 0;
        mainGbc.anchor = GridBagConstraints.CENTER;
        mainGbc.weightx = 1.0;
        mainGbc.weighty = 1.0;
        mainGbc.insets = new Insets(0, 300, 0, 0); // Add left inset to move the panel to the left
        
        // Add login panel to the main panel with constraints
        mainGbc.gridx = 500; // Set the x position to 2 to move the login panel to the right
        mainGbc.gridy = 0; // Set the y position to 0 to center it vertically
        mainPanel.add(loginPanel, mainGbc);
        
        add(mainPanel, BorderLayout.CENTER);
        passwordResetTokens = new HashMap<>();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            // Handle login action here
            String username = t1.getText();
            String password = new String(pf.getPassword());
            try {
                ConnectionClass obj = new ConnectionClass();
                String q = "SELECT * FROM login WHERE username = '" + username + "' AND password = '" + password + "'";
                ResultSet rs = obj.st.executeQuery(q);

                if (rs.next()) {
                    String role = rs.getString("username");
                    if (role.equals("admin")) {
                        new HomePage(username).setVisible(true); // Open admin home page
                       dispose(); // Close the login window
                    } else {
                        new UserPage(role).setVisible(true); // Open user home page
                        dispose(); // Close the login window
                    }
                    dispose(); // Close the login window
                } else {
                    JOptionPane.showMessageDialog(this, "You have entered the wrong username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == b3) {
            // Handle exit action
            System.exit(0);
        }
    }

    private void handlePasswordReset() {
        String username = JOptionPane.showInputDialog(this, "Enter Username to reset password:");
        if (username != null && !username.isEmpty()) {
            // Retrieve user email from database
            try {
                ConnectionClass obj = new ConnectionClass();
                String q = "SELECT email FROM login WHERE username = '" + username + "'";
                ResultSet rs = obj.st.executeQuery(q);

                if (rs.next()) {
                    String email = rs.getString("email");
                    String resetToken = generatePasswordResetToken(username);
                    passwordResetTokens.put(username, resetToken);
                    sendPasswordResetEmail(username, email, resetToken);
                    JOptionPane.showMessageDialog(this, "Password reset email sent to admin.");
                    new VerifyCode(passwordResetTokens).setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Username not found.", "Password Reset", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Username cannot be empty.", "Password Reset", JOptionPane.WARNING_MESSAGE);
        }
    }

    private String generatePasswordResetToken(String username) {
        Random random = new Random();
        int token = 100000 + random.nextInt(900000);
        return String.valueOf(token);
    }

    private void sendPasswordResetEmail(String username, String email, String resetToken) {
        // Replace with your admin email address and SMTP server details
        String adminEmail = "almoramanaskhand@gmail.com"; // Admin email address
        String smtpHost = "smtp.gmail.com"; // SMTP server host
        String smtpPort = "465"; // SMTP server port
        String adminEmailPassword = "Almora@12345"; // Admin email password

        // Setup properties for sending email via SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        // Create session with authenticator for admin email
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(adminEmail, adminEmailPassword);
            }
        });

        session.setDebug(true);

        try {
            // Create MimeMessage object
            Message message = new MimeMessage(session);

            // Set From: header field of the header
            message.setFrom(new InternetAddress(adminEmail));

            // Set To: header field of the header
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

            // Set Subject: header field
            message.setSubject("Password Reset Request for Username: " + username);

            // Set the actual message
            message.setText("A password reset request was received for the username: " + username + ".\n\n" +
                    "Verification Code: " + resetToken + "\n\n" +
                    "If you did not request this password reset, please ignore this email.");

            // Send message
            Transport.send(message);

            System.out.println("Sent password reset email successfully.");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }


    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == showPasswordCheckBox) {
            if (showPasswordCheckBox.isSelected()) {
                pf.setEchoChar((char) 0);
            } else {
                pf.setEchoChar('*');
            }
        }
    }

    public static void main(String[] args) {
        new Login();
    }

    private class ImagePanel extends JPanel {
        private Image image;

        public ImagePanel(String imagePath) {
            try {
                image = new ImageIcon(getClass().getResource(imagePath)).getImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}