package TMS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.border.LineBorder;

public class AddVisitorPanel extends JPanel {
    private JTextField visitorNameField;
    private Map<Integer, JTextField> galleryPriceFields;
    private JButton saveButton;
    private ConnectionClass database;

    public AddVisitorPanel() {
        database = new ConnectionClass();
        galleryPriceFields = new HashMap<>();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel visitorNameLabel = new JLabel("Visitor Name:");
        add(visitorNameLabel, gbc);

        gbc.gridx = 1;
        visitorNameField = new JTextField(20);
        add(visitorNameField, gbc);

        // Add price fields for each gallery
        try {
            ResultSet rs = database.st.executeQuery("SELECT id, name FROM gallery");
            while (rs.next()) {
                int galleryId = rs.getInt("id");
                String galleryName = rs.getString("name");

                gbc.gridx = 0;
                gbc.gridy++;
                add(new JLabel("Price for " + galleryName + ":"), gbc);

                gbc.gridx = 1;
                JTextField priceField = new JTextField(10);
                add(priceField, gbc);

                galleryPriceFields.put(galleryId, priceField);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        gbc.gridx = 1;
        gbc.gridy++;
        saveButton = new JButton("Save");
        add(saveButton, gbc);

        setOpaque(false);
        setPreferredSize(new Dimension(400, 300)); // Set preferred size for the panel
        setBorder(new LineBorder(Color.BLACK, 2)); // Add a black border

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String visitorName = visitorNameField.getText();
                Map<Integer, Double> galleryPrices = new HashMap<>();

                if (!visitorName.isEmpty()) {
                    try {
                        for (Map.Entry<Integer, JTextField> entry : galleryPriceFields.entrySet()) {
                            int galleryId = entry.getKey();
                            String priceText = entry.getValue().getText();
                            if (!priceText.isEmpty()) {
                                galleryPrices.put(galleryId, Double.parseDouble(priceText));
                            }
                        }

                        if (!galleryPrices.isEmpty()) {
                            int visitorTypeId = database.addVisitorType(visitorName);
                            database.addVisitorPricesForGalleries(visitorTypeId, galleryPrices);

                            JOptionPane.showMessageDialog(null, "Visitor added successfully!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Price fields cannot be empty");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid price format");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Visitor name cannot be empty");
                }
            }
        });
    }
}
