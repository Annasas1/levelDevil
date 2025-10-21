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

// I DID NOT ADD ANY COMMENTS BY NOW
/**Some description of the class and for game in general.
 * 
 */
public class LevelCompleted extends JPanel {

    private Image backgroundImage2;
    private GameManager gameManager;
    private Font customfont;

    /**description of class.
    * 
    * @param frame  about frame.
    */
    public LevelCompleted(GameManager manager) {
        backgroundImage2 = new ImageIcon("C:\\Users\\20254214\\OneDrive - TU Eindhoven\\Documents\\Programming\\CBL FINAL\\BackgroundWinLooseScreen (2).png").getImage();
        
        this.gameManager = manager;
        // setBackground(Color.GRAY);

        setLayout(null);
        loadCustomFont();

        JButton button = new JButton("return to menu");
        button.setBounds(200, 325, 600, 150);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFont(customfont != null ? customfont.deriveFont(Font.BOLD, 40f): new Font("Broadway", Font.PLAIN, 40));
        button.setFocusPainted(false);
        add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                playSound("C:\\Users\\20254214\\OneDrive - TU Eindhoven\\Documents\\Programming\\CBL FINAL\\SoundClickingMenuButtons.wav");
                //MainMenu returnback = new MainMenu(frame);
                /*
                frame.setContentPane(returnback);
                frame.revalidate();
                frame.repaint();
                returnback.requestFocusInWindow();
                 */
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
          //System.err.println("Sound error: " + e.getMessage());  
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (backgroundImage2 != null) {
            g2d.drawImage(backgroundImage2, 0, 0, getWidth(), getHeight(), this);
        } 


        
        Font titleFont = customfont != null ? customfont.deriveFont(Font.PLAIN, 45f) : new Font("Broadway", Font.BOLD, 45);
        g2d.setFont(titleFont);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Congratulations! You won :)", 205, 120);
    } 
}
/*
    public static void main(String[] args) {
        int boardWidth = 1000;
        int boardHeight = 800;

        JFrame frame = new JFrame("Level is completed");
        
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Level devil");
        

       LevelCompleted panel = new LevelCompleted(frame); 
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
     */
