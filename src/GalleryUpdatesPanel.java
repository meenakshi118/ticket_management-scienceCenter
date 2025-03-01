package TMS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;

public class GalleryUpdatesPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardsPanel;
    private JPanel buttonPanel;
    private GalleryAddPanel addGalleryPanel;
    private RemoveGalleryPanel removeGalleryPanel;
    private UpdatePricePanel updatePricePanel;
    private AddVisitorPanel addVisitorPanel;

    public GalleryUpdatesPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Background image panel with transparency
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new GridBagLayout());

        // Make the background panel transparent
        backgroundPanel.setOpaque(false);

        

   
        // Button panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false); // Make button panel transparent

        JButton addGalleryButton = createStyledButton("Add Gallery");
        JButton removeGalleryButton = createStyledButton("Remove Gallery");
        JButton updatePriceButton = createStyledButton("Price Update");
        JButton addVisitorButton = createStyledButton("Add Visitor");
        

        buttonPanel.add(addGalleryButton);
        buttonPanel.add(removeGalleryButton);
        buttonPanel.add(updatePriceButton);
        buttonPanel.add(addVisitorButton);


        gbc.gridy = 1;
        backgroundPanel.add(buttonPanel, gbc);

        // Cards panel
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);
        cardsPanel.setOpaque(false); // Make cards panel transparent

        addGalleryPanel = new GalleryAddPanel();
        removeGalleryPanel = new RemoveGalleryPanel();
        updatePricePanel = new UpdatePricePanel();
        addVisitorPanel = new AddVisitorPanel();

        cardsPanel.add(new JPanel(), "Options");  // Empty panel for initial view
        cardsPanel.setOpaque(false);
        cardsPanel.add(addGalleryPanel, "AddGallery");
        cardsPanel.add(removeGalleryPanel, "RemoveGallery");
        cardsPanel.add(updatePricePanel, "PriceUpdate");
        cardsPanel.add(addVisitorPanel, "AddVisitor");

        gbc.gridy = 2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        backgroundPanel.add(cardsPanel, gbc);

        // Initially show the options (buttons) only
        cardLayout.show(cardsPanel, "Options");

        // Adding action listeners
        addGalleryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardsPanel, "AddGallery");
            }
        });

        removeGalleryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardsPanel, "RemoveGallery");
            }
        });

        updatePriceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardsPanel, "PriceUpdate");
            }
        });
        addVisitorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardsPanel, "AddVisitor");
            }
        });

        // Set the background panel
        setLayout(new BorderLayout());
        add(backgroundPanel, BorderLayout.CENTER);
        
        
        setOpaque(false);
        
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
        button.setForeground(Color.BLACK);
        //button.setBackground(new Color(0, 0, 0, 150)); // Semi-transparent background color
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 50));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
