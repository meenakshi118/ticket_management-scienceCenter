package TMS;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.List;
import java.util.Map;

public class ConnectionClass {
    
    Connection con;
    Statement st;
    private JComboBox<String> galleryComboBox;
    private JList<String> visitorTypeList;
    private DefaultListModel<String> visitorTypeModel;

    public ConnectionClass() 
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ams", "root", "bias@123");
            st = con.createStatement();
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
     public static void main (String []args)
  {
    new ConnectionClass();
  }
    
//for booking ticket

public void insertBooking(String name, int numberOfTickets, String phoneNumber,  String emailAddress,
                           String vrCharge, String photographyCharge, String videographyCharge,
                           String parkingCharge2W, String parkingCharge4W, String childAge,
                           String totalFee, String bookingDate, String bookingTime, String visitDate,
                           String visitorTypeIds, String galleryIds) {
    // Convert string values to double, defaulting to 0.0 if conversion fails
    double vrChargeDouble = convertToDouble(vrCharge);
    double photographyChargeDouble = convertToDouble(photographyCharge);
    double videographyChargeDouble = convertToDouble(videographyCharge);
    double parkingCharge2WDouble = convertToDouble(parkingCharge2W);
    double parkingCharge4WDouble = convertToDouble(parkingCharge4W);
    double totalFeeDouble = convertToDouble(totalFee);

    // Save the ticket booking in the database
    try {
        String query = "INSERT INTO booking (visitor_name, number_of_tickets,phone_no, total_fee, payment_method, photograph_charges, videography_charges, parking_charge_2w, parking_charge_4w, visit_date, booking_date, phone_number, email_address, booking_time, cancel, visitor_type_ids, gallery_ids,double ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, name);
        pstmt.setInt(2, numberOfTickets);
        pstmt.setString(2, phoneNumber);
        pstmt.setDouble(5, totalFeeDouble);
        pstmt.setString(6, "Cash"); // Assuming payment_method is Cash
        pstmt.setDouble(7, photographyChargeDouble);
        pstmt.setDouble(8, videographyChargeDouble);
        pstmt.setDouble(9, parkingCharge2WDouble);
        pstmt.setDouble(10, parkingCharge4WDouble);
        pstmt.setDate(11, Date.valueOf(visitDate));
        pstmt.setDate(12, Date.valueOf(bookingDate));
        pstmt.setString(13, phoneNumber);
        pstmt.setString(14, emailAddress);
        pstmt.setString(15, bookingTime); // Assuming bookingTime is a String
        pstmt.setBoolean(16, false); // Assuming cancel is false
        pstmt.setString(17, visitorTypeIds);
        pstmt.setString(18, galleryIds);

        pstmt.executeUpdate();

        
    } catch (SQLException e) {
        e.printStackTrace();
          
    }
}

private double convertToDouble(String value) {
    try {
        return Double.parseDouble(value);
    } catch (NumberFormatException e) {
        return 0.0;
    }
}


//for addUserPanel
  public void saveUser(String username, String password, String email) {
    try {
        String query = "INSERT INTO login (username, password, email) VALUES (?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, username);
        pst.setString(2, password);
        pst.setString(3, email);

        pst.executeUpdate(); // Use executeUpdate() without the query parameter

        pst.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

  
  //for ChangeUserEmailPanel
  public ArrayList<String> getUsernames() {
        ArrayList<String> usernames = new ArrayList<>();
        try {
            String query = "SELECT username FROM login";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                usernames.add(resultSet.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usernames;
    }

    public boolean changeUserEmail(String username, String newEmail) {
        try {
            String query = "UPDATE login SET email = ? WHERE username = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, newEmail);
            statement.setString(2, username);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // update gallery price
    
     public void setGalleryComboBox(JComboBox<String> comboBox) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM gallery");

            while (rs.next()) {
                comboBox.addItem(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  public void loadVisitorTypes(String galleryName, DefaultListModel<String> listModel) {
    try {
        listModel.clear();

        String query = "SELECT type_name " +
                       "FROM visitor_types vt " +
                       "JOIN gallery_prices gp ON vt.id = gp.visitor_type_id " +
                       "JOIN gallery g ON gp.gallery_id = g.id " +
                       "WHERE g.name = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, galleryName);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            listModel.addElement(rs.getString("type_name"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}



    public void updatePrices(String galleryName, List<String> visitorTypes, double price) {
        try {
            // Get gallery ID
            String getGalleryIdQuery = "SELECT id FROM gallery WHERE name = ?";
            PreparedStatement getGalleryIdStmt = con.prepareStatement(getGalleryIdQuery);
            getGalleryIdStmt.setString(1, galleryName);
            ResultSet galleryRs = getGalleryIdStmt.executeQuery();

            if (galleryRs.next()) {
                int galleryId = galleryRs.getInt("id");

                // Update prices for each visitor type
                String updatePriceQuery = "UPDATE gallery_prices SET fee = ? WHERE gallery_id = ? AND visitor_type_id = ?";
                PreparedStatement updatePriceStmt = con.prepareStatement(updatePriceQuery);

                for (String visitorType : visitorTypes) {
                    // Get visitor type ID
                    String getVisitorTypeIdQuery = "SELECT id FROM visitor_types WHERE type_name = ?";
                    PreparedStatement getVisitorTypeIdStmt = con.prepareStatement(getVisitorTypeIdQuery);
                    getVisitorTypeIdStmt.setString(1, visitorType);
                    ResultSet visitorTypeRs = getVisitorTypeIdStmt.executeQuery();

                    if (visitorTypeRs.next()) {
                        int visitorTypeId = visitorTypeRs.getInt("id");
                        updatePriceStmt.setDouble(1, price);
                        updatePriceStmt.setInt(2, galleryId);
                        updatePriceStmt.setInt(3, visitorTypeId);
                        updatePriceStmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//for add gallery
    
    
     
     
    public void addGallery(String galleryName, Map<String, Double> visitorPrices) {
        try {
            // Insert new gallery
            String insertGalleryQuery = "INSERT INTO gallery (name) VALUES (?)";
            PreparedStatement insertGalleryStmt = con.prepareStatement(insertGalleryQuery, Statement.RETURN_GENERATED_KEYS);
            insertGalleryStmt.setString(1, galleryName);
            insertGalleryStmt.executeUpdate();

            // Get the generated gallery id
            ResultSet generatedKeys = insertGalleryStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int galleryId = generatedKeys.getInt(1);

                // Insert prices for each visitor type
                String selectVisitorTypeIdQuery = "SELECT id FROM visitor_types WHERE type_name = ?";
                PreparedStatement selectVisitorTypeIdStmt = con.prepareStatement(selectVisitorTypeIdQuery);

                String insertGalleryPricesQuery = "INSERT INTO gallery_prices (gallery_id, visitor_type_id, fee) VALUES (?, ?, ?)";
                PreparedStatement insertGalleryPricesStmt = con.prepareStatement(insertGalleryPricesQuery);

                for (Map.Entry<String, Double> entry : visitorPrices.entrySet()) {
                    String visitorType = entry.getKey();
                    double price = entry.getValue();

                    selectVisitorTypeIdStmt.setString(1, visitorType);
                    ResultSet visitorTypeRs = selectVisitorTypeIdStmt.executeQuery();
                    if (visitorTypeRs.next()) {
                        int visitorTypeId = visitorTypeRs.getInt("id");
                        insertGalleryPricesStmt.setInt(1, galleryId);
                        insertGalleryPricesStmt.setInt(2, visitorTypeId);
                        insertGalleryPricesStmt.setDouble(3, price);
                        insertGalleryPricesStmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    
    
      public int addVisitorType(String visitorName) throws SQLException {
        String query = "INSERT INTO visitor_types (type_name) VALUES (?)";
        PreparedStatement pstmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, visitorName);
        pstmt.executeUpdate();

        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            throw new SQLException("Failed to add visitor type");
        }
    }

    public void addVisitorPricesForGalleries(int visitorTypeId, Map<Integer, Double> galleryPrices) throws SQLException {
        String query = "INSERT INTO gallery_prices (gallery_id, visitor_type_id, fee) VALUES (?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(query);

        for (Map.Entry<Integer, Double> entry : galleryPrices.entrySet()) {
            pstmt.setInt(1, entry.getKey());
            pstmt.setInt(2, visitorTypeId);
            pstmt.setDouble(3, entry.getValue());
            pstmt.executeUpdate();
        }
    }
    
    public void saveVisitor(String visitorName, Map<String, Double> galleryPrices) throws SQLException {
        

        // SQL to insert the visitor
        String insertVisitorSQL = "INSERT INTO visitor (name) VALUES (?)";
        try (PreparedStatement visitorStmt = con.prepareStatement(insertVisitorSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            visitorStmt.setString(1, visitorName);
            visitorStmt.executeUpdate();
            
            // Get the generated visitor ID
            int visitorId;
            try (ResultSet generatedKeys = visitorStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    visitorId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating visitor failed, no ID obtained.");
                }
            }

            // SQL to insert gallery prices for the visitor
            String insertPriceSQL = "INSERT INTO visitor_gallery_prices (visitor_id, gallery_name, price) VALUES (?, ?, ?)";
            try (PreparedStatement priceStmt = con.prepareStatement(insertPriceSQL)) {
                for (Map.Entry<String, Double> entry : galleryPrices.entrySet()) {
                    priceStmt.setInt(1, visitorId);
                    priceStmt.setString(2, entry.getKey());
                    priceStmt.setDouble(3, entry.getValue());
                    priceStmt.addBatch();
                }
                priceStmt.executeBatch();
            }
        }
    }
    
     public void addGallery(String galleryName) throws SQLException {
        // Ensure that the connection is established
       

        // SQL to insert a new gallery
        String insertGallerySQL = "INSERT INTO gallery (name) VALUES (?)";
        try (PreparedStatement galleryStmt = con.prepareStatement(insertGallerySQL)) {
            galleryStmt.setString(1, galleryName);
            galleryStmt.executeUpdate();
        }
    }
      public void removeGallery(String galleryName) {
       

            String deleteGallerySQL = "DELETE FROM gallery WHERE name = ?";
            try (PreparedStatement galleryStmt = con.prepareStatement(deleteGallerySQL)) {
                galleryStmt.setString(1, galleryName);
                int rowsAffected = galleryStmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("No gallery found with the name: " + galleryName);
                }
            }
         catch (SQLException e) {
            e.printStackTrace(); // Log the exception details
            throw new RuntimeException("Error removing gallery: " + e.getMessage(), e);
        }
    }
    
    //for CancellationTicket
     public boolean cancelBooking(int bookingId) {
        try {
            // Check if the booking exists and is not already canceled
            String checkQuery = "SELECT * FROM bookings WHERE booking_id = ? AND cancel = 0";
            PreparedStatement checkStatement = con.prepareStatement(checkQuery);
            checkStatement.setInt(1, bookingId);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                // Update the cancel status to true (1)
                String updateQuery = "UPDATE bookings SET cancel = 1 WHERE booking_id = ?";
                PreparedStatement updateStatement = con.prepareStatement(updateQuery);
                updateStatement.setInt(1, bookingId);
                int rowsUpdated = updateStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    // Fetch booking details
                    String fetchQuery = "SELECT * FROM bookings WHERE booking_id = ?";
                    PreparedStatement fetchStatement = con.prepareStatement(fetchQuery);
                    fetchStatement.setInt(1, bookingId);
                    ResultSet bookingResultSet = fetchStatement.executeQuery();

                    if (bookingResultSet.next()) {
                        // Insert into cancellations table
                        String insertQuery = "INSERT INTO cancellations (booking_id, visitor_name, visitor_type, " +
                                "number_of_tickets, general_entry_fee, taramandal_fee, total_fee, payment_method, " +
                                "photograph_charges, videography_charges, parking_charges, visit_date, booking_date, " +
                                "phone_number, email_address, cancel_time) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";

                        PreparedStatement insertStatement = con.prepareStatement(insertQuery);
                        insertStatement.setInt(1, bookingResultSet.getInt("booking_id"));
                        insertStatement.setString(2, bookingResultSet.getString("visitor_name"));
                        insertStatement.setString(3, bookingResultSet.getString("visitor_type"));
                        insertStatement.setInt(4, bookingResultSet.getInt("number_of_tickets"));
                        insertStatement.setDouble(5, bookingResultSet.getDouble("general_entry_fee"));
                        insertStatement.setDouble(6, bookingResultSet.getDouble("taramandal_fee"));
                        insertStatement.setDouble(7, bookingResultSet.getDouble("total_fee"));
                        insertStatement.setString(8, bookingResultSet.getString("payment_method"));
                        insertStatement.setDouble(9, bookingResultSet.getDouble("photograph_charges"));
                        insertStatement.setDouble(10, bookingResultSet.getDouble("videography_charges"));
                        insertStatement.setDouble(11, bookingResultSet.getDouble("parking_charges"));
                        insertStatement.setString(12, bookingResultSet.getString("visit_date"));
                        insertStatement.setString(13, bookingResultSet.getString("booking_date"));
                        insertStatement.setString(14, bookingResultSet.getString("phone_number"));
                        insertStatement.setString(15, bookingResultSet.getString("email_address"));

                        insertStatement.executeUpdate();
                        insertStatement.close();
                    }
                    return true; // Cancellation successful
                }
            }

            resultSet.close();
            checkStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Cancellation failed
    }

    
}


