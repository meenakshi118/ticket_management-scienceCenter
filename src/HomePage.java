
package TMS;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HomePage extends JFrame
{
     private JLabel lblUsername;
    private JLabel lblDateTime;
    private JPanel sidePanel, mainPanel,upperPanel;
    private CardLayout cardLayout;
     private BackgroundPanel homePanel, ticketBookingPanel, changePasswordPanel, changeEmailPanel,
            galleryUpdatesPanel, userDetailsPanel, addUserPanel;
    
    
    
    public HomePage(String username)
    {       
        
        setTitle("Home Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set the frame to be full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        
        //upper panel
        upperPanel = new JPanel(new GridBagLayout());
        upperPanel.setBackground(Color.WHITE);
        upperPanel.setPreferredSize(new Dimension(800,150));

        // Load the image
        ImageIcon img = new ImageIcon(getClass().getResource("/images/logo.jpg"));
        Image i1 = img.getImage().getScaledInstance(800, 100, Image.SCALE_SMOOTH);
        ImageIcon img2 = new ImageIcon(i1);
        JLabel imageLabel = new JLabel(img2);
        upperPanel.add(imageLabel);
        
          // User info panel with GridBagLayout
        JPanel userInfoPanel = new JPanel(new GridBagLayout());
        userInfoPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        lblDateTime = new JLabel();
        lblDateTime.setFont(new Font("Serif", Font.BOLD, 18));
        updateDateTime(); // Initialize with the current date and time
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        userInfoPanel.add(lblDateTime, gbc);

        lblUsername = new JLabel("User: " + username);
        lblUsername.setFont(new Font("Serif", Font.BOLD, 18));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        userInfoPanel.add(lblUsername, gbc);
        
        // Side Panel
        sidePanel = new JPanel();
        sidePanel.setLayout(new GridBagLayout());
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color(138, 185, 241));
        sidePanel.setPreferredSize(new Dimension(250, getHeight()));

        // Add buttons to side panel
        addMenuButton("Ticket Booking","/images/ticket.png", sidePanel);
        addMenuButton("Ticket Report","/images/report.png", sidePanel);
        addMenuButton("Daily Summary", "/images/summary.png",sidePanel);
        addMenuButton("Sales Report","/images/report.png", sidePanel);
        addMenuButton("Query Report","/images/query.png", sidePanel);
        addMenuButton("Change Password","/images/changePw.png", sidePanel);
        addMenuButton("Change Email","/images/changeEmail.png", sidePanel);
        addMenuButton("Gallery Updates", "/images/gallery.png", sidePanel);
         addMenuButton("Add User", "/images/addUser.png", sidePanel);
        addMenuButton("User Details","/images/userDetail.png", sidePanel);
        addMenuButton("Logout","/images/logout.png", sidePanel);


       
       // Main Panel with Background Image
        mainPanel = new BackgroundPanel("/images/home2.JPG");
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);
        mainPanel.setBackground(new Color(230,230,250));
      
        
        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome to Manaskhand Science Centre, Almora - "+ username+" ! ");
        welcomeLabel.setFont(new Font("Serif", Font.ITALIC, 30)); // Adjust font and size here
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        
       // Welcome Panel
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        GridBagConstraints welcomeGbc = new GridBagConstraints();
        welcomeGbc.gridx = 0;
        welcomeGbc.gridy = 0;
        welcomeGbc.insets = new Insets(200, 40, 20, 40); // Add top padding to move the label downwards
        welcomePanel.add(welcomeLabel, welcomeGbc);
        welcomePanel.setOpaque(false); // Make the panel transparent

        
        
         // Initialize BackgroundPanels
        homePanel = new BackgroundPanel("/images/home2.JPG");
        ticketBookingPanel = new BackgroundPanel("/images/backt.JPG", new TicketBookingPanel(username));
        changePasswordPanel = new BackgroundPanel("/images/backpas.jpg", new ChangePasswordPanel());
        changeEmailPanel = new BackgroundPanel("/images/backemail.jpg", new ChangeEmailPanel());
        galleryUpdatesPanel = new BackgroundPanel("/images/backgall.jpg", new GalleryUpdatesPanel());
        userDetailsPanel = new BackgroundPanel("/images/backdetail.png", new UserDetailsPanel());
        addUserPanel = new BackgroundPanel("/images/back1.jpg", new AddUserPanel());

        // Add panels to mainPanel
        mainPanel.add(homePanel, "home");
        mainPanel.add(ticketBookingPanel, "ticketBooking");
        mainPanel.add(changePasswordPanel, "ChangePassword");
        mainPanel.add(changeEmailPanel, "ChangeEmail");
        mainPanel.add(galleryUpdatesPanel, "GalleryUpdates");
        mainPanel.add(userDetailsPanel, "UserDetails");
        mainPanel.add(addUserPanel, "AddUser");
        
        // Layout
        setLayout(new BorderLayout());
        add(upperPanel, BorderLayout.NORTH);
        add(sidePanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
        add(userInfoPanel, BorderLayout.SOUTH);

        cardLayout.show(mainPanel, "home"); // Show home image on startup
       

        homePanel.add(welcomePanel);
    
    // Start the date/time updater thread
        new Thread(() -> {
            while (true) {
                updateDateTime();
                try {
                    Thread.sleep(1000); // Update every second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void updateDateTime() {
        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        lblDateTime.setText("Date/Time: " + currentDateTime);
    }
   
  private void addMenuButton(String text, String iconPath, JPanel panel) {
        // Load and resize icon
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        if (icon == null) {
        System.err.println("Error loading icon: " + iconPath);
        icon = new ImageIcon(); // Default empty icon
    }

    // Resize icon
        Image scaledImage = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH); // Resize icon to 24x24
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Create button with text and icon
        JButton button = new JButton(text);
        button.setIcon(scaledIcon); // Set the resized icon to the button
        button.setHorizontalAlignment(SwingConstants.LEFT); // Align text and icon to the left
        button.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50)); // Set preferred size for button
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
         button.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
         
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(138, 185, 241));
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.addActionListener(new MenuButtonHandler());
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(10, 15))); // Add space between buttons
        
        
    }

    
    private class MenuButtonHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Ticket Booking":
                cardLayout.show(mainPanel, "ticketBooking");
                break;
            case "Ticket Report":
                cardLayout.show(mainPanel, "ticketBooking");
                break;
            case "Logout":
                System.exit(0); // Logout
                break;
            case "Daily Summary":
                cardLayout.show(mainPanel, "dailySummary");
                break;
            case "Sales Report":
                cardLayout.show(mainPanel, "ticketBooking");
                break;
            case "Query Report":
                cardLayout.show(mainPanel, "ticketBooking");
                break;
            case "Change Password":
                cardLayout.show(mainPanel, "ChangePassword");
                break;
            case "Change Email":
                cardLayout.show(mainPanel, "ChangeEmail");
                break;
            case "Gallery Updates":
                    cardLayout.show(mainPanel, "GalleryUpdates");
                    break;
            case "User Details":
                cardLayout.show(mainPanel, "UserDetails");
                break;    
            case "Add User":
                    cardLayout.show(mainPanel, "AddUser");
                    break;
            default:
                    break;
        }
    }
}

    
    
}
   

    
     
