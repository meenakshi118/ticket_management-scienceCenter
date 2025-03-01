package TMS;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.border.LineBorder;

public class GalleryAddPanel extends JPanel {
    private JTextField galleryNameField;
    private JButton addButton,addVisitorButton;;
    private ConnectionClass database;
    private JPanel visitorPricesPanel;
    private Map<String, JTextField> visitorPriceFields;
   

    
        
   

    public GalleryAddPanel() {
        database = new ConnectionClass();
        RemoveGalleryPanel rgp = new  RemoveGalleryPanel();
        database = new ConnectionClass();
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel galleryNameLabel = new JLabel("Gallery Name:");
        galleryNameField = new JTextField(50);
        addButton = new JButton("Add Gallery");
        
        
        visitorPricesPanel = new JPanel();
        visitorPricesPanel.setLayout(new BoxLayout(visitorPricesPanel, BoxLayout.Y_AXIS));
        visitorPriceFields = new HashMap<>();

        visitorPricesPanel.setOpaque(false);
        loadVisitor();

        setOpaque(false);
        add(galleryNameLabel);
        add(galleryNameField);
        add(visitorPricesPanel);
        add(addButton);
        setPreferredSize(new Dimension(400, 400)); // Set preferred size for the panel
        setBorder(new LineBorder(Color.BLACK, 2)); // Add a black border

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String galleryName = galleryNameField.getText();
                if (!galleryName.isEmpty()) {
                    Map<String, Double> visitorPrices = new HashMap<>();
                    for (String visitorType : visitorPriceFields.keySet()) {
                        String priceText = visitorPriceFields.get(visitorType).getText();
                        try {
                            double price = Double.parseDouble(priceText);
                            visitorPrices.put(visitorType, price);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid price for " + visitorType);
                            return;
                        }
                    }
                    database.addGallery(galleryName, visitorPrices);
                    // Reload the galleries in RemoveGalleryPanel
                    rgp.loadGalleries();

                    JOptionPane.showMessageDialog(null, "Gallery added successfully!");
                    galleryNameField.setText("");
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Gallery name cannot be empty!");
                }
            }
        });
        
        
    
    }

    private void loadVisitor() {
        try {
            
            ResultSet rs = database.st.executeQuery("SELECT type_name FROM visitor_types");

            while (rs.next()) {
                String visitorType = rs.getString("type_name");
                JLabel visitorTypeLabel = new JLabel(visitorType + " Price:");
                JTextField priceField = new JTextField(5);
                visitorPricesPanel.add(visitorTypeLabel);
                visitorPricesPanel.add(priceField);
                visitorPriceFields.put(visitorType, priceField);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
       
}
