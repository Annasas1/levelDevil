import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// I DID NOT ADD ANY COMMENTS BY NOW
/**Some description of the class and for game in general.
 * 
 */
public class YEYlevelCompleted extends JPanel {

      private Image backgroundImage2;


    /**description of class.
    * 
    * @param frame about frame.
    */
    public YEYlevelCompleted(JFrame frame) {
         backgroundImage2 = new ImageIcon("C:\\Users\\20254214\\OneDrive - TU Eindhoven\\Documents\\Programming\\CBL2\\B6.jpg").getImage();
        
       

      // setBackground(Color.GRAY);
        setLayout(null);

        JButton button = new JButton("return to menu");
        button.setBounds(200, 325, 600, 150);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Broadway", Font.ITALIC | Font.BOLD, 50));
        button.setFocusPainted(false);
        add(button);

      button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                 playSound("C:\\Users\\20254214\\OneDrive - TU Eindhoven\\Documents\\Programming\\CBL2\\SoundClickButtonsWAV.wav");
                YEYMainMenu returnback = new YEYMainMenu(frame);

                frame.setContentPane(returnback);
                frame.revalidate();
                frame.repaint();
                returnback.requestFocusInWindow();
            }
        });
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


        
        Font font = new Font("Broadway", Font.BOLD | Font.ITALIC, 50);
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);
        g2d.drawString("CONGRAAATS BRO", 225, 100);
    }

    public static void main(String[] args) {
        int boardWidth = 1000;
        int boardHeight = 800;

        JFrame frame = new JFrame("Level is completed");
        
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Level devil");
        

       YEYlevelCompleted panel = new YEYlevelCompleted(frame); 
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
