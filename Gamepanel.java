import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.*;


/** Our game panel (which extends JPanel) will handle drawing and receiving the key input. 
 * include the levels
 * 
 *  */ 

public class GamePanel extends JPanel implements KeyListener {

    //Creating our objects <3
    private Player box = new Player(250, 300);
    private ArrayList<Platform> platforms = new ArrayList<>();
    Door door = new Door(0, 100, 50, 80); 
    Spikes spike = new Spikes(10, 10, 1000, 30);   
    //Player update = new Player(); 
    

    GamePanel() {
        setBackground(Color.WHITE);
        setFocusable(true);      // allows the panel to receive key input
        addKeyListener(this);    // listens for arrow keys
        


        platforms.add(new Platform(0, 400, 1000, 400));    // ground
        platforms.add(new Platform(200, 200, 200, 30));    // floating platform 1
        platforms.add(new Platform(500, 250, 150, 30));    //floating platform 2
    

        door.setPosition(700, 300); //Placement of door
        spike.setPosition(0, 550); //Placement of spike

        platforms.add(new Platform(0, 0, 50, 600));      // left wall
        platforms.add(new Platform(940, 0, 50, 600));   //right wall
        Timer timer = new Timer(15, e -> { //timer 
            
            for (Platform p : platforms) { //platform moves down
                if (p.getBounds().y == 400 && box.getHitbox().x > 350) {
                    p.activate();
                }
                p.update();
            }

            box.update(); //update the player
          
            boolean landed = false;
            
          
            for (Platform p : platforms) {
                 Rectangle2D.Double platformBounds = p.getBounds();
                 Rectangle2D.Double current = box.getHitbox();
                 Rectangle2D.Double previous = box.getPreviousHitbox();
                 

                if (box.checkCollision(p)) {
                    // check that player is above platform
                    if (box.getHitbox().getMaxY() <= p.getBounds().getY() + box.getVelocityY()) { 
                        box.landOn(p);
                        landed = true;
                        break;
                    } else if (current.intersects(platformBounds)) {
                        box.handleHorizontalCollision(p);
                     }
                }
            }
            if (!landed && box.getVelocityY() > 0) {
                box.setOnGround(false);
            }
            if (door.checkCollision(box.getHitbox())) {
                //winLevel(); 
                System.out.println("You win!"); 
            }
            if (spike.checkCollision(box.getHitbox())) {
                //winLevel(); 
                System.out.println("You lose!");
                
            }

            
            
            repaint();
        });
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // Draw the player as a filled rectangle using hitbox
        g2.setColor(Color.MAGENTA);
        g2.fill(box.getHitbox());
        door.draw((Graphics2D)g);
        spike.draw((Graphics2D)g);
        // Draw platform
         for (Platform p : platforms) {
            p.draw(g2);
        }
        
    }

    // Key handling
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            box.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            box.moveLeft(); /*
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            box.moveUp();  */
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            box.jump();
        }
        //repaint(); // redraws the box at its new position whenever the player moves. 
    }

    

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
         box.stopMoving(); // stop when arrow keys are released
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    
}
