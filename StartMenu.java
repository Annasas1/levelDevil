import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

/**
 * The initial screen displayed when the game starts, acting as a gateway to the Main Menu.
 * It integrates with the GameManager to handle screen transitions via CardLayout.
 */
public class StartMenu extends JPanel {
    private Image backgroundImage;
    private GameManager gameManager; // Field to store the reference to the central manager
    private Font customfont;

    /** nnbv.
     *
     * * @param manager T
     */
    public StartMenu(GameManager manager) {
        this.gameManager = manager; 
        
        backgroundImage = new ImageIcon("C:\\Users\\20254214\\OneDrive - TU Eindhoven\\Documents\\Programming\\CBL FINAL\\BackgroundStartMenu (7).png").getImage();
        
        setBackground(Color.GRAY);
        setLayout(null);

        loadCustomFont();

        JButton button = new JButton("start");
        button.setBounds(315, 350, 370, 150);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFont(customfont != null ? customfont.deriveFont(Font.PLAIN, 40f): new Font("Broadway", Font.PLAIN, 40));
        button.setFocusPainted(false);
        add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 playSound("C:\\Users\\20254214\\OneDrive - TU Eindhoven\\Documents\\Programming\\CBL FINAL\\SoundClickingMenuButtons.wav");
                
                gameManager.switchToScreen("MAIN_MENU");
            }
        });
    }

    private void loadCustomFont() {
        try {
            customfont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\20254214\\OneDrive - TU Eindhoven\\Documents\\Programming\\CBL FINAL\\minecraft\\Minecraft.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customfont);
        } catch (FontFormatException | IOException e) {
            System.err.println("Failed to load custom font, falling back to default.");
            e.printStackTrace();
            customfont = null;
        }
    }

    private void playSound(String soundFilePath) {
        try {
            File soundFile1 = new File(soundFilePath);
            AudioInputStream audio1 = AudioSystem.getAudioInputStream(soundFile1);
            Clip clip = AudioSystem.getClip();
            clip.open(audio1);
            clip.start();
        } catch (Exception e) {
            // Error handling commented out to suppress console messages
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, 1000, 600, this);
        }
        
       Font titleFont = customfont != null ? customfont.deriveFont(Font.BOLD, 60f) : new Font("Broadway", Font.BOLD, 60);
        g2d.setFont(titleFont);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Level Devil", 331, 120);
    }
    
    
    // The main method remains commented out, ensuring GameManager controls the startup.
}
