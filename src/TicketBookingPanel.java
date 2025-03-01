package TMS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.LineBorder;

public class TicketBookingPanel extends JPanel implements ActionListener {
    private CardLayout cardLayout;
    private JPanel mainPanel, buttonPanel, bookingPanel, cancelPanel;

    public TicketBookingPanel(String username) {
        setLayout(new BorderLayout());

        // Main Panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Welcome Panel
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel welcomeLabel = new JLabel("WELCOME!");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 24));
        welcomePanel.add(welcomeLabel, gbc);

        gbc.gridy = 1;
        JLabel chooseOptionLabel = new JLabel("CHOOSE AN OPTION");
        chooseOptionLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        welcomePanel.add(chooseOptionLabel, gbc);

        gbc.gridy = 2;
        buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton bookTicketButton = new JButton("Book Ticket");
        JButton cancelTicketButton = new JButton("Ticket Cancellation");

        bookTicketButton.addActionListener(this);
        cancelTicketButton.addActionListener(this);

        buttonPanel.add(bookTicketButton);
        buttonPanel.add(cancelTicketButton);
        buttonPanel.setOpaque(false);
        welcomePanel.add(buttonPanel, gbc);
        welcomePanel.setOpaque(false);

        mainPanel.add(welcomePanel, "welcome");

        // Booking and Cancellation Panels
        
        bookingPanel = new BackgroundPanel("/images/pink.jpg", new bookingPanel(username));
        cancelPanel = new BackgroundPanel("/images/pink.jpg", new cancellationPanel(username));

        // Load the image
        ImageIcon img = new ImageIcon(getClass().getResource("/images/close.png"));
        Image i1 = img.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon img2 = new ImageIcon(i1);
        
        
        // Create a JLabel with a close icon for the booking panel
        JLabel bookingCloseButton = new JLabel(img2);
        bookingCloseButton.addMouseListener(new MouseAdapter() {
         @Override
            public void mouseClicked(MouseEvent e) {
                 cardLayout.show(mainPanel, "welcome"); }});
         bookingPanel.add(bookingCloseButton, BorderLayout.NORTH);
         

        // Create a JLabel with a close icon for the cancel panel
        JLabel cancelCloseButton = new JLabel(img2);
        cancelCloseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "welcome"); }});
         cancelPanel.add(cancelCloseButton, BorderLayout.NORTH);

        
        mainPanel.add(bookingPanel, "booking");
        mainPanel.add(cancelPanel, "cancellation");
        mainPanel.setOpaque(false);

        
        add(mainPanel, BorderLayout.CENTER);

        cardLayout.show(mainPanel, "welcome");
        setOpaque(false);
        
                
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Book Ticket":
                cardLayout.show(mainPanel, "booking");
                break;
            case "Ticket Cancellation":
                cardLayout.show(mainPanel, "cancellation");
                break;
            default:
                break;
        }
    }
}
 