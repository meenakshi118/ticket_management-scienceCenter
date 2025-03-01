package TMS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.border.LineBorder;

public class RemoveGalleryPanel extends JPanel {
    private JComboBox<String> galleryComboBox;
    private JButton removeButton;
    private ConnectionClass database;

    public RemoveGalleryPanel() {
        database = new ConnectionClass();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel label = new JLabel("Select Gallery to Remove:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(label, gbc);

        galleryComboBox = new JComboBox<>();
        loadGalleries();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(galleryComboBox, gbc);

        removeButton = new JButton("UPDATE");
        removeButton.addActionListener(new RemoveGalleryActionListener());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(removeButton, gbc);
        
        setOpaque(false);
        setPreferredSize(new Dimension(400, 200)); // Set preferred size for the panel
        setBorder(new LineBorder(Color.BLACK, 2)); // Add a black border
        
    }

    private class RemoveGalleryActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedGallery = (String) galleryComboBox.getSelectedItem();
            if (selectedGallery != null) {
                int choice = JOptionPane.showConfirmDialog(RemoveGalleryPanel.this,
                        "Are you sure to remove the gallery?", "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (choice == JOptionPane.YES_OPTION) {
                    removeGallery(selectedGallery);
                }
            }
        }
    }

    public void loadGalleries() {
        try {
            galleryComboBox.removeAllItems(); // Clear existing items
            String query = "SELECT name FROM gallery";
            PreparedStatement pst = database.con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                galleryComboBox.addItem(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void removeGallery(String galleryName) {
        try {
            // Start transaction
            database.con.setAutoCommit(false);

            // Get gallery ID
            String getGalleryIdQuery = "SELECT id FROM gallery WHERE name = ?";
            PreparedStatement getGalleryIdStmt = database.con.prepareStatement(getGalleryIdQuery);
            getGalleryIdStmt.setString(1, galleryName);
            ResultSet galleryRs = getGalleryIdStmt.executeQuery();

            if (galleryRs.next()) {
                int galleryId = galleryRs.getInt("id");

                // Delete gallery prices
                String deleteGalleryPricesQuery = "DELETE FROM gallery_prices WHERE gallery_id = ?";
                PreparedStatement deleteGalleryPricesStmt = database.con.prepareStatement(deleteGalleryPricesQuery);
                deleteGalleryPricesStmt.setInt(1, galleryId);
                deleteGalleryPricesStmt.executeUpdate();

                // Delete gallery
                String deleteGalleryQuery = "DELETE FROM gallery WHERE id = ?";
                PreparedStatement deleteGalleryStmt = database.con.prepareStatement(deleteGalleryQuery);
                deleteGalleryStmt.setInt(1, galleryId);
                deleteGalleryStmt.executeUpdate();

                // Commit transaction
                database.con.commit();

                // Remove the gallery from the combo box
                galleryComboBox.removeItem(galleryName);

                JOptionPane.showMessageDialog(RemoveGalleryPanel.this, "Gallery and its prices removed successfully.");
            } else {
                JOptionPane.showMessageDialog(RemoveGalleryPanel.this, "Gallery not found.");
            }
        } catch (SQLException e) {
            try {
                database.con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                database.con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
