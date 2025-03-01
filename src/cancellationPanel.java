
package TMS;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.sql.PreparedStatement;



public class cancellationPanel extends JPanel {


    private JTextField txtName,txtAddress, txtNumberOfTickets,txtTotalQuantity, txtPhoneNumber, txtEmailAddress;
    private JTextField txtVRCharge, txtBookingDate, txtVisitDate, txtPhotographyCharge, txtVideographyCharge, txtParkingCharge2W, txtParkingCharge4W;
    private JComboBox<String> cmbCountry,cmbConcessionTypes;
    private JLabel lblTotalFee;
    private JButton btnBookTicket , btnAddConcession,btnSubtractConcession;

    private ConnectionClass db;

    private Map<String, Integer> visitorTypes = new HashMap<>();
    private Map<String, Integer> galleries = new HashMap<>();
    private Map<JRadioButton, Integer> visitorTypeRadioButtons = new HashMap<>();
    private Map<JCheckBox, Integer> galleryCheckBoxes = new HashMap<>();

   private Map<String, JCheckBox> concessionCheckBoxes = new HashMap<>();
    private Map<String, JTextField> concessionQuantities = new HashMap<>(); // For storing concession quantities

    private static final Map<String, String> countryPhoneCodes = new HashMap<>();

   static {
    countryPhoneCodes.put("Afghanistan", "+93");
    countryPhoneCodes.put("Albania", "+355");
    countryPhoneCodes.put("Algeria", "+213");
    countryPhoneCodes.put("Andorra", "+376");
    countryPhoneCodes.put("Angola", "+244");
    countryPhoneCodes.put("Antigua and Barbuda", "+1-268");
    countryPhoneCodes.put("Argentina", "+54");
    countryPhoneCodes.put("Armenia", "+374");
    countryPhoneCodes.put("Australia", "+61");
    countryPhoneCodes.put("Austria", "+43");
    countryPhoneCodes.put("Azerbaijan", "+994");
    countryPhoneCodes.put("Bahamas", "+1-242");
    countryPhoneCodes.put("Bahrain", "+973");
    countryPhoneCodes.put("Bangladesh", "+880");
    countryPhoneCodes.put("Barbados", "+1-246");
    countryPhoneCodes.put("Belarus", "+375");
    countryPhoneCodes.put("Belgium", "+32");
    countryPhoneCodes.put("Belize", "+501");
    countryPhoneCodes.put("Benin", "+229");
    countryPhoneCodes.put("Bhutan", "+975");
    countryPhoneCodes.put("Bolivia", "+591");
    countryPhoneCodes.put("Bosnia and Herzegovina", "+387");
    countryPhoneCodes.put("Botswana", "+267");
    countryPhoneCodes.put("Brazil", "+55");
    countryPhoneCodes.put("Brunei", "+673");
    countryPhoneCodes.put("Bulgaria", "+359");
    countryPhoneCodes.put("Burkina Faso", "+226");
    countryPhoneCodes.put("Burundi", "+257");
    countryPhoneCodes.put("Cabo Verde", "+238");
    countryPhoneCodes.put("Cambodia", "+855");
    countryPhoneCodes.put("Cameroon", "+237");
    countryPhoneCodes.put("Canada", "+1");
    countryPhoneCodes.put("Central African Republic", "+236");
    countryPhoneCodes.put("Chad", "+235");
    countryPhoneCodes.put("Chile", "+56");
    countryPhoneCodes.put("China", "+86");
    countryPhoneCodes.put("Colombia", "+57");
    countryPhoneCodes.put("Comoros", "+269");
    countryPhoneCodes.put("Congo, Democratic Republic of the", "+243");
    countryPhoneCodes.put("Congo, Republic of the", "+242");
    countryPhoneCodes.put("Costa Rica", "+506");
    countryPhoneCodes.put("Croatia", "+385");
    countryPhoneCodes.put("Cuba", "+53");
    countryPhoneCodes.put("Cyprus", "+357");
    countryPhoneCodes.put("Czech Republic", "+420");
    countryPhoneCodes.put("Denmark", "+45");
    countryPhoneCodes.put("Djibouti", "+253");
    countryPhoneCodes.put("Dominica", "+1-767");
    countryPhoneCodes.put("Dominican Republic", "+1-809, +1-829, +1-849");
    countryPhoneCodes.put("Ecuador", "+593");
    countryPhoneCodes.put("Egypt", "+20");
    countryPhoneCodes.put("El Salvador", "+503");
    countryPhoneCodes.put("Equatorial Guinea", "+240");
    countryPhoneCodes.put("Eritrea", "+291");
    countryPhoneCodes.put("Estonia", "+372");
    countryPhoneCodes.put("Eswatini", "+268");
    countryPhoneCodes.put("Ethiopia", "+251");
    countryPhoneCodes.put("Fiji", "+679");
    countryPhoneCodes.put("Finland", "+358");
    countryPhoneCodes.put("France", "+33");
    countryPhoneCodes.put("Gabon", "+241");
    countryPhoneCodes.put("Gambia", "+220");
    countryPhoneCodes.put("Georgia", "+995");
    countryPhoneCodes.put("Germany", "+49");
    countryPhoneCodes.put("Ghana", "+233");
    countryPhoneCodes.put("Greece", "+30");
    countryPhoneCodes.put("Grenada", "+1-473");
    countryPhoneCodes.put("Guatemala", "+502");
    countryPhoneCodes.put("Guinea", "+224");
    countryPhoneCodes.put("Guinea-Bissau", "+245");
    countryPhoneCodes.put("Guyana", "+592");
    countryPhoneCodes.put("Haiti", "+509");
    countryPhoneCodes.put("Honduras", "+504");
    countryPhoneCodes.put("Hungary", "+36");
    countryPhoneCodes.put("Iceland", "+354");
    countryPhoneCodes.put("India", "+91");
    countryPhoneCodes.put("Indonesia", "+62");
    countryPhoneCodes.put("Iran", "+98");
    countryPhoneCodes.put("Iraq", "+964");
    countryPhoneCodes.put("Ireland", "+353");
    countryPhoneCodes.put("Israel", "+972");
    countryPhoneCodes.put("Italy", "+39");
    countryPhoneCodes.put("Jamaica", "+1-876");
    countryPhoneCodes.put("Japan", "+81");
    countryPhoneCodes.put("Jordan", "+962");
    countryPhoneCodes.put("Kazakhstan", "+7");
    countryPhoneCodes.put("Kenya", "+254");
    countryPhoneCodes.put("Kiribati", "+686");
    countryPhoneCodes.put("Korea, North", "+850");
    countryPhoneCodes.put("Korea, South", "+82");
    countryPhoneCodes.put("Kosovo", "+383");
    countryPhoneCodes.put("Kuwait", "+965");
    countryPhoneCodes.put("Kyrgyzstan", "+996");
    countryPhoneCodes.put("Laos", "+856");
    countryPhoneCodes.put("Latvia", "+371");
    countryPhoneCodes.put("Lebanon", "+961");
    countryPhoneCodes.put("Lesotho", "+266");
    countryPhoneCodes.put("Liberia", "+231");
    countryPhoneCodes.put("Libya", "+218");
    countryPhoneCodes.put("Liechtenstein", "+423");
    countryPhoneCodes.put("Lithuania", "+370");
    countryPhoneCodes.put("Luxembourg", "+352");
    countryPhoneCodes.put("Madagascar", "+261");
    countryPhoneCodes.put("Malawi", "+265");
    countryPhoneCodes.put("Malaysia", "+60");
    countryPhoneCodes.put("Maldives", "+960");
    countryPhoneCodes.put("Mali", "+223");
    countryPhoneCodes.put("Malta", "+356");
    countryPhoneCodes.put("Marshall Islands", "+692");
    countryPhoneCodes.put("Mauritania", "+222");
    countryPhoneCodes.put("Mauritius", "+230");
    countryPhoneCodes.put("Mexico", "+52");
    countryPhoneCodes.put("Micronesia", "+691");
    countryPhoneCodes.put("Moldova", "+373");
    countryPhoneCodes.put("Monaco", "+377");
    countryPhoneCodes.put("Mongolia", "+976");
    countryPhoneCodes.put("Montenegro", "+382");
    countryPhoneCodes.put("Morocco", "+212");
    countryPhoneCodes.put("Mozambique", "+258");
    countryPhoneCodes.put("Myanmar (Burma)", "+95");
    countryPhoneCodes.put("Namibia", "+264");
    countryPhoneCodes.put("Nauru", "+674");
    countryPhoneCodes.put("Nepal", "+977");
    countryPhoneCodes.put("Netherlands", "+31");
    countryPhoneCodes.put("New Zealand", "+64");
    countryPhoneCodes.put("Nicaragua", "+505");
    countryPhoneCodes.put("Niger", "+227");
    countryPhoneCodes.put("Nigeria", "+234");
    countryPhoneCodes.put("North Macedonia", "+389");
    countryPhoneCodes.put("Norway", "+47");
    countryPhoneCodes.put("Oman", "+968");
    countryPhoneCodes.put("Pakistan", "+92");
    countryPhoneCodes.put("Palau", "+680");
    countryPhoneCodes.put("Palestine", "+970");
    countryPhoneCodes.put("Panama", "+507");
    countryPhoneCodes.put("Papua New Guinea", "+675");
    countryPhoneCodes.put("Paraguay", "+595");
    countryPhoneCodes.put("Peru", "+51");
    countryPhoneCodes.put("Philippines", "+63");
    countryPhoneCodes.put("Poland", "+48");
    countryPhoneCodes.put("Portugal", "+351");
    countryPhoneCodes.put("Qatar", "+974");
    countryPhoneCodes.put("Romania", "+40");
    countryPhoneCodes.put("Russia", "+7");
    countryPhoneCodes.put("Rwanda", "+250");
    countryPhoneCodes.put("Saint Kitts and Nevis", "+1-869");
    countryPhoneCodes.put("Saint Lucia", "+1-758");
    countryPhoneCodes.put("Saint Vincent and the Grenadines", "+1-784");
    countryPhoneCodes.put("Samoa", "+685");
    countryPhoneCodes.put("San Marino", "+378");
    countryPhoneCodes.put("Sao Tome and Principe", "+239");
    countryPhoneCodes.put("Saudi Arabia", "+966");
    countryPhoneCodes.put("Senegal", "+221");
    countryPhoneCodes.put("Serbia", "+381");
    countryPhoneCodes.put("Seychelles", "+248");
    countryPhoneCodes.put("Sierra Leone", "+232");
    countryPhoneCodes.put("Singapore", "+65");
    countryPhoneCodes.put("Slovakia", "+421");
    countryPhoneCodes.put("Slovenia", "+386");
    countryPhoneCodes.put("Solomon Islands", "+677");
    countryPhoneCodes.put("Somalia", "+252");
   
   }

    public cancellationPanel(String username) {
        db = new ConnectionClass();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fetch visitor types and galleries from the database
        fetchVisitorTypes();
        fetchGalleries();
        fetchConcessionTypes();

        // Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Visitor Name:"), gbc);
        txtName = new JTextField(20);
         txtName.setPreferredSize(new Dimension(150, 20)); 
        gbc.gridx = 1;
        add(txtName, gbc);

        // Number of Tickets
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Number of Individuals:"), gbc);
        txtNumberOfTickets = new JTextField(5);
        txtNumberOfTickets.setPreferredSize(new Dimension(50, 20)); // Set preferred size
        gbc.gridx = 1;
        add(txtNumberOfTickets, gbc);

        // Visitor Types
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Visitor Type:"), gbc);
        JPanel visitorTypesPanel = new JPanel();
        visitorTypesPanel.setLayout(new GridLayout(0, 1));
        ButtonGroup visitorTypeGroup = new ButtonGroup();
        for (String type : visitorTypes.keySet()) {
            JRadioButton rbVisitorType = new JRadioButton(type);
            visitorTypesPanel.add(rbVisitorType);
            visitorTypeGroup.add(rbVisitorType);
            visitorTypeRadioButtons.put(rbVisitorType, visitorTypes.get(type));
            rbVisitorType.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateTotalFee();
                }
            });
        }
        gbc.gridx = 1;
        add(visitorTypesPanel, gbc);

        // Galleries
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Galleries:"), gbc);
        JPanel galleryPanel = new JPanel();
        galleryPanel.setLayout(new GridLayout(0, 1));
        for (Map.Entry<String, Integer> entry : galleries.entrySet()) {
            JCheckBox chkGallery = new JCheckBox(entry.getKey());
            galleryPanel.add(chkGallery);
            galleryCheckBoxes.put(chkGallery, entry.getValue());
            chkGallery.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateTotalFee();
                }
            });
        }
        gbc.gridx = 1;
        add(galleryPanel, gbc);

       
        

        // Booking Date
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Booking Date:"), gbc);
        txtBookingDate = new JTextField(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
        txtBookingDate.setPreferredSize(new Dimension(100, 20)); // Set preferred size
        txtBookingDate.setEditable(false); // Make booking date uneditable
        gbc.gridx = 1;
        add(txtBookingDate, gbc);

        // Visit Date
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Visit Date:"), gbc);
        txtVisitDate = new JTextField(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
        txtVisitDate.setPreferredSize(new Dimension(100, 20)); // Set preferred size
        gbc.gridx = 1;
        add(txtVisitDate, gbc);

         // Additional Charges Panel
        JPanel additionalChargesPanel = new JPanel(new GridBagLayout());
        additionalChargesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Additional Charges", TitledBorder.LEFT, TitledBorder.TOP));
        GridBagConstraints gbcCharges = new GridBagConstraints();
        gbcCharges.insets = new Insets(5, 5, 5, 5);
        gbcCharges.fill = GridBagConstraints.VERTICAL;
        additionalChargesPanel.setOpaque(false);

        // Additional Charges
        gbcCharges.gridx = 0;
        gbcCharges.gridy = 0;
        additionalChargesPanel.add(new JLabel("VR Charge:"), gbcCharges);
        txtVRCharge = new JTextField("0", 5);
        txtVRCharge.setPreferredSize(new Dimension(50, 20)); // Set preferred size
        gbcCharges.gridx = 1;
        additionalChargesPanel.add(txtVRCharge, gbcCharges);

        gbcCharges.gridx = 0;
        gbcCharges.gridy = 1;
        additionalChargesPanel.add(new JLabel("Photography Charge:"), gbcCharges);
        txtPhotographyCharge = new JTextField("0", 5);
        txtPhotographyCharge.setPreferredSize(new Dimension(50, 20)); // Set preferred size
        gbcCharges.gridx = 1;
        additionalChargesPanel.add(txtPhotographyCharge, gbcCharges);

        gbcCharges.gridx = 0;
        gbcCharges.gridy = 2;
        additionalChargesPanel.add(new JLabel("Videography Charge:"), gbcCharges);
        txtVideographyCharge = new JTextField("0", 5);
        txtVideographyCharge.setPreferredSize(new Dimension(50, 20)); // Set preferred size
        gbcCharges.gridx = 1;
        additionalChargesPanel.add(txtVideographyCharge, gbcCharges);

        gbcCharges.gridx = 0;
        gbcCharges.gridy = 3;
        additionalChargesPanel.add(new JLabel("2W Parking Charge:"), gbcCharges);
        txtParkingCharge2W = new JTextField("0", 5);
        txtParkingCharge2W.setPreferredSize(new Dimension(50, 20)); // Set preferred size
        gbcCharges.gridx = 1;
        additionalChargesPanel.add(txtParkingCharge2W, gbcCharges);

        gbcCharges.gridx = 0;
        gbcCharges.gridy = 4;
        additionalChargesPanel.add(new JLabel("4W Parking Charge:"), gbcCharges);
        txtParkingCharge4W = new JTextField("0", 5);
        txtParkingCharge4W.setPreferredSize(new Dimension(50, 20)); // Set preferred size
        gbcCharges.gridx = 1;
        additionalChargesPanel.add(txtParkingCharge4W, gbcCharges);

        gbc.gridx = 7;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(additionalChargesPanel, gbc);

       // Concessions Panel
        JPanel concessionPanel = new JPanel(new GridBagLayout());
        concessionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Concessions", TitledBorder.LEFT, TitledBorder.TOP));
        concessionPanel.setPreferredSize(new Dimension(300, 200));
        GridBagConstraints gbcConcessions = new GridBagConstraints();
        gbcConcessions.insets = new Insets(5, 5, 5, 5);

        // Total Quantity Label
        gbcConcessions.gridx = 0;
        gbcConcessions.gridy = 0;
        gbcConcessions.gridwidth = 1; // span one column
        concessionPanel.add(new JLabel("Total Quantity:"), gbcConcessions);

        // Total Quantity Text Field
        gbcConcessions.gridx = 1;
        gbcConcessions.gridwidth = 1; // span one column
        txtTotalQuantity = new JTextField("0", 5);
        txtTotalQuantity.setPreferredSize(new Dimension(50, 30));
        concessionPanel.add(txtTotalQuantity, gbcConcessions);

         // Concession Checkboxes and Quantity TextFields
        
        // Initialize row index for placing components
        int rowIndex = 1;

        // Iterate through concession types and create checkboxes with corresponding text fields
        for (Map.Entry<String, JTextField> entry : concessionQuantities.entrySet()) {
        String concessionType = entry.getKey();
        JTextField txtQuantity = entry.getValue();

        // Checkbox for concession type
        JCheckBox chkConcession = new JCheckBox(concessionType);
        gbcConcessions.gridx = 0;
        gbcConcessions.gridy = rowIndex;
        concessionCheckBoxes.put(concessionType, chkConcession);
        concessionPanel.add(chkConcession, gbcConcessions);

        // Text field for quantity
        gbcConcessions.gridx = 1;
         gbcConcessions.gridy = rowIndex;
        txtQuantity.setPreferredSize(new Dimension(50, 20));
        //    txtQuantity.setEditable(false); // Making it uneditable by default
        txtQuantity.setHorizontalAlignment(JTextField.CENTER);
        concessionPanel.add(txtQuantity, gbcConcessions);

            rowIndex++;
        }

        

        concessionPanel.setOpaque(false);
        // Add the concessionPanel to the main layout
        gbc.gridx = 6; // Adjust the gridx position to place it correctly in your main layout
        gbc.gridy = 4; // Adjust the gridy position to place it correctly in your main layout
        gbc.gridwidth = 2; // Adjust the gridwidth if needed
        add(concessionPanel, gbc);


        // Country
        gbc.gridx = 0;
        gbc.gridy = 8;
        add(new JLabel("Country:"), gbc);
        cmbCountry = new JComboBox<>(countryPhoneCodes.keySet().toArray(new String[0]));
        cmbCountry.setSelectedItem("India"); 
        gbc.gridx = 1;
        add(cmbCountry, gbc);

        // Phone Number
        gbc.gridx = 0;
        gbc.gridy = 9;
        add(new JLabel("Phone Number:"), gbc);
        txtPhoneNumber = new JTextField(countryPhoneCodes.get("India") + " ", 15); // Set default country code to India's code
        txtPhoneNumber.setPreferredSize(new Dimension(150, 20));
        gbc.gridx = 1;
        add(txtPhoneNumber, gbc);


        
        // Email Address
        gbc.gridx = 0;
        gbc.gridy = 10;
        add(new JLabel("Email Address:"), gbc);
        txtEmailAddress = new JTextField(20);
        txtEmailAddress.setPreferredSize(new Dimension(150, 20)); // Set preferred size
        gbc.gridx = 1;
        add(txtEmailAddress, gbc);

        // Address
        gbc.gridx = 0;
        gbc.gridy = 11;
        add(new JLabel("Address:"), gbc);
        txtAddress = new JTextField(20);
        txtAddress.setPreferredSize(new Dimension(150, 20)); // Set preferred size
        gbc.gridx = 1;
        add(txtAddress, gbc);
       

        // Total Fee
        gbc.gridx = 0;
        gbc.gridy = 12;
        add(new JLabel("Total Fee:"), gbc);
        lblTotalFee = new JLabel();
        gbc.gridx = 1;
        add(lblTotalFee, gbc);

        // Book Ticket Button
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        btnBookTicket = new JButton("Book Ticket");
        add(btnBookTicket, gbc);

        // Add ActionListener for country selection
        cmbCountry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCountry = (String) cmbCountry.getSelectedItem();
                String countryCode = countryPhoneCodes.get(selectedCountry);
                txtPhoneNumber.setText(countryCode + " "); // Update the text field with the country code
            }
        });

        
        // Action Listener for the button
        btnBookTicket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookTicket();
            }
        });

        // Add listeners to update the total fee
        addListeners();

        // Initial total fee calculation
        updateTotalFee();
        setOpaque(false);
        
    }

    private void addListeners() {
        DocumentListener listener = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                updateTotalFee();
            }

            public void removeUpdate(DocumentEvent e) {
                updateTotalFee();
            }

            public void insertUpdate(DocumentEvent e) {
                updateTotalFee();
            }
        };

        txtNumberOfTickets.getDocument().addDocumentListener(listener);
        txtVRCharge.getDocument().addDocumentListener(listener);
        txtPhotographyCharge.getDocument().addDocumentListener(listener);
        txtVideographyCharge.getDocument().addDocumentListener(listener);
        txtParkingCharge2W.getDocument().addDocumentListener(listener);
        txtParkingCharge4W.getDocument().addDocumentListener(listener);
        txtVisitDate.getDocument().addDocumentListener(listener);
        txtPhoneNumber.getDocument().addDocumentListener(listener);
        txtEmailAddress.getDocument().addDocumentListener(listener);

         txtTotalQuantity.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTotalFee();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTotalFee();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTotalFee();
            }
        });

    }

    
    
    private void fetchVisitorTypes() {
        try {
            ResultSet rs = db.st.executeQuery( "SELECT * FROM visitor_types vt JOIN gallery_prices gp ON vt.id = gp.visitor_type_id JOIN gallery g ON gp.gallery_id = g.id WHERE fee != 0");
            while (rs.next()) {
                visitorTypes.put(rs.getString("type_name"), rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fetchGalleries() {
        try {
            ResultSet rs = db.st.executeQuery("SELECT * FROM gallery");
            while (rs.next()) {
                galleries.put(rs.getString("name"), rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fetchConcessionTypes() {
        String query = "SELECT type_name FROM visitor_types vt JOIN gallery_prices gp ON vt.id = gp.visitor_type_id JOIN gallery g ON gp.gallery_id = g.id WHERE fee = 0";
        try {
            ResultSet rs = db.st.executeQuery(query);
            while (rs.next()) {
                String concessionType = rs.getString("type_name");
                concessionQuantities.put(concessionType, new JTextField("0"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void updateTotalFee() {
     // Check if the number of tickets field is empty
     
    String numberOfTicketsText = txtNumberOfTickets.getText().trim();
    if (numberOfTicketsText.isEmpty()) {
        lblTotalFee.setText("Please enter the number of tickets.");
        return;
    }
    int numberOfTickets = Integer.parseInt(numberOfTicketsText);
    double totalFee = 0;


    // Determine the selected visitor type
    int selectedVisitorTypeId = 0;
    for (Map.Entry<JRadioButton, Integer> entry : visitorTypeRadioButtons.entrySet()) {
        JRadioButton rb = entry.getKey();
        if (rb.isSelected()) {
            selectedVisitorTypeId = entry.getValue();
            break;
        }
    }

    // Calculate gallery fees based on selected visitor types and galleries
    for (Map.Entry<JCheckBox, Integer> entry : galleryCheckBoxes.entrySet()) {
        JCheckBox chkGallery = entry.getKey();
        if (chkGallery.isSelected()) {
            int galleryId = entry.getValue();
            totalFee += getGalleryFee(galleryId, selectedVisitorTypeId) * numberOfTickets;
        }
    }

    // Add additional charges
    totalFee += convertToDouble(txtVRCharge.getText());
    totalFee += convertToDouble(txtPhotographyCharge.getText());
    totalFee += convertToDouble(txtVideographyCharge.getText());
    totalFee += convertToDouble(txtParkingCharge2W.getText());
    totalFee += convertToDouble(txtParkingCharge4W.getText());

     
            // Calculate concession charges
             String quantitytxt = txtTotalQuantity.getText().trim();

         if (!quantitytxt.isEmpty()) {
        
            int quantity = Integer.parseInt(txtTotalQuantity.getText());
            for (Map.Entry<JCheckBox, Integer> entry : galleryCheckBoxes.entrySet()) {
                JCheckBox chkGallery = entry.getKey();
                if (chkGallery.isSelected()) {
                    int galleryId = entry.getValue();
                    totalFee -= quantity * getGalleryFee(galleryId, selectedVisitorTypeId);
            }
        }
    lblTotalFee.setText(String.format("%.2f", totalFee));
}
    }

    

    private double convertToDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private int getGalleryFee(int galleryId, int visitorTypeId) {
        try {
            ResultSet rs = db.st.executeQuery("SELECT fee FROM gallery_prices WHERE gallery_id = " + galleryId + " AND visitor_type_id = " + visitorTypeId);
            if (rs.next()) {
                return rs.getInt("fee");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    

   private void bookTicket() {
        String name = txtName.getText();
        String numberOfTicketsStr = txtNumberOfTickets.getText();
        String phoneNumber = txtPhoneNumber.getText();
        String emailAddress = txtEmailAddress.getText();
        String bookingDate = txtBookingDate.getText();
        String visitDate = txtVisitDate.getText();
        int vrCharge = Integer.parseInt(txtVRCharge.getText());
        int photographyCharge = Integer.parseInt(txtPhotographyCharge.getText());
        int videographyCharge = Integer.parseInt(txtVideographyCharge.getText());
        int parkingCharge2W = Integer.parseInt(txtParkingCharge2W.getText());
        int parkingCharge4W = Integer.parseInt(txtParkingCharge4W.getText());

        int totalFee = Integer.parseInt(lblTotalFee.getText());

        String visitorType = null;
        for (Map.Entry<JRadioButton, Integer> entry : visitorTypeRadioButtons.entrySet()) {
            JRadioButton radioButton = entry.getKey();
            if (radioButton.isSelected()) {
                visitorType = radioButton.getText();
                break;
            }
        }

        if (visitorType == null) {
            JOptionPane.showMessageDialog(this, "Please select a visitor type.");
            return;
        }

        if (name.isEmpty() || numberOfTicketsStr.isEmpty() || phoneNumber.isEmpty() || emailAddress.isEmpty() || visitDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
            return;
        }

        int numberOfTickets;
        try {
            numberOfTickets = Integer.parseInt(numberOfTicketsStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number of tickets.");
            return;
        }

        StringBuilder selectedGalleries = new StringBuilder();
        for (Map.Entry<JCheckBox, Integer> entry : galleryCheckBoxes.entrySet()) {
            JCheckBox checkBox = entry.getKey();
            if (checkBox.isSelected()) {
                selectedGalleries.append(checkBox.getText()).append(", ");
            }
        }
        if (selectedGalleries.length() > 0) {
            selectedGalleries.setLength(selectedGalleries.length() - 2); // Remove trailing comma and space
        }

        // Insert data into the database
        try {
            String query = "INSERT INTO TicketBooking (Name, VisitorType, NumberOfTickets, PhoneNumber, EmailAddress, BookingDate, VisitDate, Galleries, VRCharge, PhotographyCharge, VideographyCharge, ParkingCharge2W, ParkingCharge4W, TotalFee) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = db.con.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, visitorType);
            pstmt.setInt(3, numberOfTickets);
            pstmt.setString(4, phoneNumber);
            pstmt.setString(5, emailAddress);
            pstmt.setString(6, bookingDate);
            pstmt.setString(7, visitDate);
            pstmt.setString(8, selectedGalleries.toString());
            pstmt.setInt(9, vrCharge);
            pstmt.setInt(10, photographyCharge);
            pstmt.setInt(11, videographyCharge);
            pstmt.setInt(12, parkingCharge2W);
            pstmt.setInt(13, parkingCharge4W);
            pstmt.setInt(14, totalFee);

            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Ticket booked successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error booking ticket.");
        }
    }
}
