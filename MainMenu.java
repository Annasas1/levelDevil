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
/**Some description of the class and main menu of the game.
 * 
 */

public class MainMenu extends JPanel  {

    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private Image backgroundImage;
    private GameManager gameManager;
    private Font customfont;

    /** Description of class.
     * 
     * @param frame about frame
     */
    public MainMenu(GameManager manager) {
        this.gameManager = manager;

        backgroundImage = new ImageIcon("BackgroungMENU.png").getImage();
        setBackground(Color.GRAY);
        setLayout(null); 
        loadCustomFont();
    
        button1 = new JButton("Level 1");
        button1.setBounds(425, 200, 150, 40);
        button1.setBackground(Color.BLACK);
        button1.setForeground(Color.WHITE);
        button1.setFont(customfont != null ? customfont.deriveFont(Font.PLAIN, 15f)
            : new Font("Broadway", Font.PLAIN, 15));
        button1.setFocusPainted(false);
        add(button1);
        

        button2 = new JButton("Level 2");
        button2.setBounds(425, 300, 150, 40);
        button2.setBackground(Color.BLACK);
        button2.setForeground(Color.WHITE);
        button2.setFont(customfont != null ? customfont.deriveFont(Font.PLAIN, 15f)
            : new Font("Broadway", Font.PLAIN, 15));
        button2.setFocusPainted(false);
        button2.setEnabled(false);
        add(button2);
     
        button3 = new JButton("Level 3");
        button3.setBounds(425, 400, 150, 40);
        button3.setBackground(Color.BLACK);
        button3.setForeground(Color.WHITE);
        button3.setFont(customfont != null ? customfont.deriveFont(Font.PLAIN, 15f)
            : new Font("Broadway", Font.PLAIN, 15));
        button3.setFocusPainted(false);
        button3.setEnabled(false);
        add(button3);
       

        button4 = new JButton("return");
        button4.setBounds(100, 65, 150, 40);
        button4.setBackground(Color.BLACK);
        button4.setForeground(Color.WHITE);
        button4.setFont(customfont != null ? customfont.deriveFont(Font.PLAIN, 15f)
            : new Font("Broadway", Font.PLAIN, 15));
        button4.setFocusPainted(false);
        add(button4);
       

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                playSound("CSoundClickingMenuButtons.wav");
                gameManager.startLevel(1);


                //frame.setContentPane(level1);
                //frame.revalidate();
                //frame.repaint();
                //level1.requestFocusInWindow();
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                playSound("SoundClickingMenuButtons.wav");
                gameManager.startLevel(2);
                

                //frame.setContentPane(level2);
                //frame.revalidate();
                //frame.repaint();
                //level2.requestFocusInWindow();
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                playSound("SoundClickingMenuButtons.wav");
                gameManager.startLevel(3);

                //frame.setContentPane(level3);
                //frame.revalidate();
                //frame.repaint();
                //level3.requestFocusInWindow();
            }
        }); 
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                playSound("SoundClickingMenuButtons.wav");
                //StartMenu returnback = new StartMenu(frame);

                //frame.setContentPane(returnback);
                //frame.revalidate();
                //frame.repaint();
                //returnback.requestFocusInWindow();
                gameManager.switchToScreen("START_MENU");
            }
        });
    
        TimedLabel message1 = new TimedLabel("level 1 is available!", 1000);
        message1.setBounds(350, 150, 300, 50);
        add(message1);
    }

    /**
     * Some summary.
     */
    public void refreshLevelButtons() {
        int highest = gameManager.getHighestLevelCompleted();
        button2.setEnabled(highest >= 1);
        button3.setEnabled(highest >= 2);
        
    }

    private void loadCustomFont() {
        try {
            customfont = Font.createFont(Font.TRUETYPE_FONT, new File("Minecraft.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customfont);
        } catch (FontFormatException | IOException e) {
          //  System.err.println("Failed to load custom font, falling back to default.");
          //  e.printStackTrace();
          //  customfont = null;
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
        //    System.err.println("Sound error: " + e.getMessage());  
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, 988, 565, this);
        }
            
        Font titleFont = customfont != null ? customfont.deriveFont(Font.BOLD,  40f) 
            : new Font("Broadway", Font.BOLD, 40);
        
        g2d.setFont(titleFont);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Level Devil", 386, 100);
            
    }

    /**Some description of class (+game design).
      * 
      */
    private class TimedLabel extends JLabel {
        public TimedLabel(String text, int durationMillis) {
            super(text);
            setForeground(Color.WHITE);
            setFont(customfont != null ? customfont.deriveFont(Font.PLAIN, 20f)
                : new Font("Broadway", Font.PLAIN, 20));
            setHorizontalAlignment(SwingConstants.CENTER);
            setOpaque(false); 

            startBlinking(durationMillis);
            //new Timer(5000, e -> setVisible(false)).start();
        }

        private void startBlinking(int intervalMillis) {
            final int[] blinkCount = {0};
        
            Timer timer = new Timer(intervalMillis, new ActionListener() {
                @Override
            public void actionPerformed(ActionEvent e) {
                    if (blinkCount[0] < 8) { 
                        setVisible(!isVisible());
                        blinkCount[0]++;
                    } else {
                        ((Timer) e.getSource()).stop();
                        setVisible(false); 
                    }
                }
            });
            timer.start();
        }
    }
    
    
}
