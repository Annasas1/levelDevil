import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.*;

//f.zamfirov@tue.nl

/** Our game panel (which extends JPanel) will handle drawing and receiving the key input. 
 * include the levels
 * Core engine and display sceeen. 
 * 
 *  */ 

 
public class Gamepanel extends JPanel implements KeyListener {
    
    //for fixing the order queue of the jump
    private boolean jumpQueued = false;
    private GameManager gameController; // Reference to the central manager
    private Player box = new Player(150, 300, "Demon1.PNG");
    private Level currentLevel;
    private Timer timer; 
   
    //Creating our objects <3 
    
    //private Player box = new Player(150, 300, "Demon1.PNG");
    //private ArrayList<Platform> platforms = new ArrayList<>();
    //Door door = new Door(0, 100, 50, 80, "door1.PNG"); 
    //private ArrayList<Spikes> spikes = new ArrayList<>();  //Player update = new Player(); 
    
    /** Game is built and main animation loop.
     * 
     */
    Gamepanel(GameManager gameController) {
        this.gameController = gameController;
        
        Color customColory = Color.decode("#525064"); // Set background/setup here
        setBackground(customColory);
        setFocusable(true); 
        addKeyListener(this);
        
        timer = new Timer(15, e -> {
            if (currentLevel != null) {
                final float delta = 0.015f;
                // PASS necessary components (delta, player, jump state, controller)
                currentLevel.update(delta, box, jumpQueued, gameController); 
                jumpQueued = false; // Reset after passing
            }
            repaint();
        });
        /*
        Color customColory = Color.decode("#525064");
        setBackground(customColory);
        setFocusable(true);      // allows the panel to receive key input
        addKeyListener(this);    // listens for arrow keys
        


        platforms.add(new Platform(0, 400, 1000, 400));    // ground
        //platforms.add(new Platform(200, 200, 200, 30));    // floating platform 1
        platforms.add(new Platform(400, 250, 150, 30));    //floating platform 2
        //spikes.add(new Spikes(0, 600, 60));

        // Floating spikes in the middle
        //spikes.add(new Spikes(300, 400, 55));
        //looping array to add bottom row of spikes
        
        int spikeWidth = 50;
        for (int x = 0; x < 1000; x += spikeWidth) {
            spikes.add(new Spikes(x, 508, spikeWidth, 55, "singleSpike.png"));
        }



        // Ceiling spikes
        //spikes.add(new Spikes(20, 100, 10));

        door.setPosition(700, 325); //Placement of door
        platforms.add(new Platform(0, 0, 50, 600));      // left wall
        platforms.add(new Platform(940, 0, 50, 600));   //right wall
        //gameloop
        Timer timer = new Timer(15, e -> { //timer 
            
            for (Platform p : platforms) { //platform ready for game & interactions
                if (p.getBounds().y == 400 && box.getHitbox().x > 250) {
                    p.activateDown();
                }
                p.update();
            }
            
            //We calculate the delta time (15ms) and pass this to the box
            //We do this so it is even with the timer so no issues arrise with jumping
            final float delta = 0.015f;
            box.update(delta);
          
            boolean landed = false;
            
            
            for (Platform p : platforms) {
                //Rectangle2D.Double platformBounds = p.getBounds();
                //Rectangle2D.Double current = box.getHitbox();
                Rectangle2D.Double previous = box.getPreviousHitbox();
                 
                //float groundMargin = 1f;
                
                //COLLISION!
                if (box.checkCollision(p)) {
                    // Check for Vertical CollisionPRIORITY!!!!!! KEEP FIRST OR CODE = UNHAPPY
                    // If the player was above the platform in the previous frame
                    // AND they are moving downwards:
                    
                    boolean wasAbove = previous.getMaxY() <= p.getBounds().getY() + 1; // Tolerance 
        
                    // Landing: If they were above 
                    // OR if they are currently standing on this platform
                    if ((wasAbove && box.getVelocityY() >= 0) || (box.getCurrentPlatform() == p)) {
                        box.landOn(p); // This sets velocityY = 0 and onGround = true
                        landed = true;
                        break; 
                    } else {   // Check for Horizontal Collision so either side.
                        box.handleHorizontalCollision(p);
                    }
                }
            }
            //if no platform to land on and moving down player will not be set straight so fall
            if (!landed && box.getVelocityY() > 0) {
                box.setOnGround(false); // This call makes the player fall!
            }

            //JUMP LOGIC :( We have boolean connected to space: jump ONLY exectes after grounded 
            //state is fully resolved for CURRENT FRAME. 
            if (jumpQueued) {
                box.jump();
                jumpQueued = false; // Reset the flag
            }
            //Check win condition
            if (door.checkCollision(box.getHitbox())) {
                //winLevel(); 
                System.out.println("You win!"); 
            }
            //Check lose condition
            for (Spikes s : spikes) {
                if (s.checkCollision(box.getHitbox())) {
                    System.out.println("You lose!");
                }
            }


            
            
            repaint();
        });
        timer.start();  */
    }

    // New method to load a level object
    public void load(Level newLevel) {
        this.currentLevel = newLevel;
        this.currentLevel.init(this.box);

        
        // might need to reset the Player's position here
        //box.reset(newLevel.getStartPosition());
    }
    
    public void startLevelLoop() {
        if (timer != null) {
            timer.start();
            requestFocusInWindow(); // Ensure the GamePanel grabs focus when the game starts
        }
    }
    
    public void stopLevelLoop() {
        timer.stop();
    }
    
    @Override
    public void paintComponent(Graphics g) {

        //Method is called verytime the loop calls repaint();
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //color for player 
        //Color colorPlayer = Color.decode("#100c2dff");
        //g2.setColor(colorPlayer);

        // Draw the player as a filled rectangle using hitbox
        //g2.fill(box.getHitbox());
        box.draw(g2); /*
        door.draw((Graphics2D) g);
        for (Spikes s : spikes) {
            s.draw(g2);
        }    */
        /*
        // Draw platform
        for (Platform p : platforms) {
            p.draw(g2);
        }
        */
        if (currentLevel != null) {
            currentLevel.draw(g2);
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
            jumpQueued = true; //Executed in gameloop
        }
        //repaint(); 
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
