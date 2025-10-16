import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// I DID NOT ANY COMMENTS BY NOW
/**Some description of the class and main menu of the game.
 * 
 */

public class YEYMainMenu extends JPanel  {

    private Image backgroundImage;
    /**description of class.
     * 
     * @param frame about frame
     */
    public YEYMainMenu(JFrame frame) {

         backgroundImage = new ImageIcon("C:\\Users\\20254214\\OneDrive - TU Eindhoven\\Documents\\Programming\\CBL2\\BackgroundMainmenu.jpg").getImage();
        setBackground(Color.GRAY);
        setLayout(null); 
    
        JButton button1 = new JButton("LEVEL 1");
        button1.setBounds(420, 250, 150, 40);
        button1.setBackground(Color.BLACK);
        button1.setForeground(Color.WHITE);
        button1.setFont(new Font("Broadway", Font.ITALIC | Font.BOLD, 10));
        button1.setFocusPainted(false);
        add(button1);
        

        JButton button2 = new JButton("LEVEL 2");
        button2.setBounds(420, 350, 150, 40);
        button2.setBackground(Color.BLACK);
        button2.setForeground(Color.WHITE);
        button2.setFont(new Font("Broadway", Font.ITALIC | Font.BOLD, 10));
        button2.setFocusPainted(false);
        button2.setEnabled(YEYlockedlevels.lvl1done);
        add(button2);
     
        JButton button3 = new JButton("LEVEL 3");
        button3.setBounds(420, 450, 150, 40);
        button3.setBackground(Color.BLACK);
        button3.setForeground(Color.WHITE);
        button3.setFont(new Font("Broadway", Font.ITALIC | Font.BOLD, 10));
        button3.setFocusPainted(false);
        button3.setEnabled(YEYlockedlevels.lvl2done);
        add(button3);
       

        JButton button4 = new JButton("RETURN");
        button4.setBounds(100, 150, 100, 30);
        button4.setBackground(Color.BLACK);
        button4.setForeground(Color.WHITE);
        button4.setFont(new Font("Broadway", Font.ITALIC | Font.BOLD, 10));
        button4.setFocusPainted(false);
        add(button4);
       

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                YEYlevel1 level1 = new YEYlevel1();

                frame.setContentPane(level1);
                frame.revalidate();
                frame.repaint();
                level1.requestFocusInWindow();
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                YEYlevel2 level2 = new YEYlevel2();

                frame.setContentPane(level2);
                frame.revalidate();
                frame.repaint();
                level2.requestFocusInWindow();
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                YEYlevel3 level3 = new YEYlevel3();

                frame.setContentPane(level3);
                frame.revalidate();
                frame.repaint();
                level3.requestFocusInWindow();
            }
        }); 
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                YEYstartMenu returnback = new YEYstartMenu(frame);

                frame.setContentPane(returnback);
                frame.revalidate();
                frame.repaint();
                returnback.requestFocusInWindow();
            }
        });
    
        TimedLabel message1 = new TimedLabel("LEVEL 1 AVAILABLE", 5000);
        message1.setBounds(350, 180, 300, 40);
        add(message1);
    }

    @Override
            protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

         if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, 1000, 800, this);
        }
            
        Font font = new Font("Broadway", Font.BOLD | Font.ITALIC, 25);
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);
        g2d.drawString("LEVEL DEVIL", 400, 100);
            
    }

    /**Some description of class (+game design).
      * 
      */
    private class TimedLabel extends JLabel {
        public TimedLabel(String text, int durationMillis) {
            super(text);
            setForeground(Color.WHITE);
            setFont(new Font("Algerian", Font.ITALIC | Font.BOLD, 10));
            setHorizontalAlignment(SwingConstants.CENTER);
            setOpaque(false); 

           
            new Timer(5000, e -> setVisible(false)).start();
        }
    }
    
    
}
