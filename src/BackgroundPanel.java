package TMS;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;
    private JComponent content;

    // Constructor for background image only
    public BackgroundPanel(String imagePath) {
        this.backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
    }

    // Constructor for background image with content
    public BackgroundPanel(String imagePath, JComponent content) {
        this.backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
        this.content = content;
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(content,gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Optional: Method to change background image dynamically
    public void setBackgroundImage(String imagePath) {
        this.backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
        repaint();
    }
}
