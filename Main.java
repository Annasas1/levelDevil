import javax.swing.*;

//This is our Main class in which we will run all our code. (Gameloop)

/** javadoc.
 * 
 */
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Levil devil");
        GamePanel panel = new GamePanel();
        

        frame.add(panel);
        
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        
    }
}
