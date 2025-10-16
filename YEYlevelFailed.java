import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// I DID NOT ADD ANY COMMENTS BY NOW
/**Some description of the class and for game in general.
 * 
 */
public class YEYlevelFailed extends JPanel {


    /**description of class.
    * 
    * @param frame about frame.
    */
    public YEYlevelFailed(JFrame frame) {
       

       setBackground(Color.GRAY);
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
                YEYMainMenu returnback = new YEYMainMenu(frame);

                frame.setContentPane(returnback);
                frame.revalidate();
                frame.repaint();
                returnback.requestFocusInWindow();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        Font font = new Font("Broadway", Font.BOLD | Font.ITALIC, 50);
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);
        g2d.drawString("YOU FAILED HAHA", 225, 100);
    }

    public static void main(String[] args) {
        int boardWidth = 1000;
        int boardHeight = 800;

        JFrame frame = new JFrame("Level is failed");
        YEYlevelFailed Pic = new YEYlevelFailed(frame);
        frame.setSize(boardWidth, boardHeight);
        ImageIcon image1 = new
        ImageIcon("C:\\Users\\20254214\\OneDrive - TU Eindhoven\\Documents\\Programming\\CBL2\\BackgroundMainmenu.jpg");
        frame.add(new JLabel(image1));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Level devil");
        

       YEYlevelFailed panel = new YEYlevelFailed(frame); 
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
