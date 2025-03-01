package TMS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import java.util.List;
import javax.swing.border.LineBorder;

public class UpdatePricePanel extends JPanel implements ActionListener {
    private JLabel galleryLabel;
    private JComboBox<String> galleryComboBox;
    private JLabel visitorTypeLabel;
    private JList<String> visitorTypeList;
    private JTextField priceField;
    private JButton updateButton;
    private DefaultListModel<String> visitorTypeModel;
    private ConnectionClass database;

    public UpdatePricePanel() {
        database = new ConnectionClass();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        galleryLabel = new JLabel("Select Gallery:");
        add(galleryLabel, gbc);

        gbc.gridx++;
        galleryComboBox = new JComboBox<>();
        database.setGalleryComboBox(galleryComboBox);
        galleryComboBox.addActionListener(e -> handleGallerySelection());
        add(galleryComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        visitorTypeLabel = new JLabel("Select Visitor Types:");
        add(visitorTypeLabel, gbc);

        gbc.gridx++;
        visitorTypeModel = new DefaultListModel<>();
        visitorTypeList = new JList<>(visitorTypeModel);
        visitorTypeList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        visitorTypeList.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(visitorTypeList);
        listScrollPane.setPreferredSize(new Dimension(200, 100));
        add(listScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel priceLabel = new JLabel("New Price:");
        add(priceLabel, gbc);

        gbc.gridx++;
        priceField = new JTextField(10);
        add(priceField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        updateButton = new JButton("Update Price");
        updateButton.addActionListener(this);
        add(updateButton, gbc);

        handleGallerySelection(); // Initial call to set the state based on the default selection
    
    
        setOpaque(false);
        setPreferredSize(new Dimension(400, 200)); // Set preferred size for the panel
        setBorder(new LineBorder(Color.BLACK, 2)); // Add a black border
        
         
    }

    private void handleGallerySelection() {
        String selectedGallery = (String) galleryComboBox.getSelectedItem();
        if (selectedGallery == null) {
            return;
        }
        visitorTypeLabel.setVisible(true);
        visitorTypeList.setVisible(true);
        
        // Pass the DefaultListModel to loadVisitorTypes
        database.loadVisitorTypes(selectedGallery, visitorTypeModel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateButton) {
            String selectedGallery = (String) galleryComboBox.getSelectedItem();
            if (selectedGallery == null) {
                JOptionPane.showMessageDialog(this, "Please select a gallery.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<String> selectedVisitorTypes = visitorTypeList.getSelectedValuesList();
            if (selectedVisitorTypes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select at least one visitor type.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String priceStr = priceField.getText().trim();
            if (priceStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a price.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid price format. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            database.updatePrices(selectedGallery, selectedVisitorTypes, price);
            JOptionPane.showMessageDialog(this, "Prices updated successfully.");
            priceField.setText("");
        }
    }
}
