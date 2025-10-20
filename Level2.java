import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.*;


public class Level2 implements Level {
    // ---------------------------------------------------------------------
    // Level Specific Data (Platforms, Spikes, Door)
    // ---------------------------------------------------------------------
    private ArrayList<Platform> platforms = new ArrayList<>();
    private ArrayList<Spikes> spikes = new ArrayList<>(); 
    private Door door = new Door(0, 100, 50, 80, "door1.PNG"); 
    
    @Override
    public int getLevelID() { 
        return 2; 
    }
    
    // ---------------------------------------------------------------------
    // Level Setup and Reset Logic
    // ---------------------------------------------------------------------
    @Override
    public void init(Player player) {
        //Clear any old level objects before setting up the new level (Crucial for restarts)
        platforms.clear();
        spikes.clear();

        //player starts pos
        player.setPosition(150, 300); 
        spikes.add(new Spikes(440,350,50,55, "singleSpike.png"));
        
        spikes.add(new Spikes(490,350,50,55, "singleSpike.png"));

        
        spikes.add(new Spikes(220,350,50,55, "singleSpike.png"));
        
        spikes.add(new Spikes(270,350,50,55, "singleSpike.png"));
        int spikeWidth = 50;
       
        for (int x = 400; x < 500; x += spikeWidth) {
            //spikes.add(new Spikes(x, 350, spikeWidth, 55, "singleSpike.png"));
        }

        //ground
        platforms.add(new Platform(0, 401, 1000, 400)); 
        platforms.add(new Platform(0, 0, 50, 600));      // left wall
        platforms.add(new Platform(940, 0, 50, 600));    // right wall
        
        
        
        //win
        door.setPosition(700, 325); 
    }
    
    // ---------------------------------------------------------------------
    // Core Game Loop Logic (Called repeatedly by GamePanel's Timer)
    // ---------------------------------------------------------------------
    @Override
    public void update(float delta, Player box, boolean jumpQueued, GameManager gameController) {
        
        // Update platforms (Platform specific movement logic)
        for (Platform p : platforms) { 
            // Add your old platform movement logic here:
            if (p.getBounds().y == 400 && box.getHitbox().x > 250) {
                p.activateDown();
            }
            p.update(); // Update platform position/state
        } 

        // 2. Update player movement and physics
        box.update(delta);

        for (Spikes s : spikes) {
                if (s.checkCollision(box.getHitbox())) {
                // Level 1 logic: Die!
                
               
                gameController.levelFailed(); 
                
                //System.out.println("boo-hoo");
            }
                if (s.getHitbox().x == 490 && box.getHitbox().x > 410) {
                        s.activateMoving();
                        System.out.println("imh ere");
                    }
                if (s.getHitbox().x == 440 && box.getHitbox().x > 410) {
                        s.activateMoving2();
                        System.out.println("imh ere");
                    }


            s.update();
        }
        
        // 3. Handle collisions 
        boolean landed = false;
        for (Platform p : platforms) {
            
            if (box.checkCollision(p)) {
                Rectangle2D.Double previous = box.getPreviousHitbox();
                
                // Tolerance check for landing from above
                boolean wasAbove = previous.getMaxY() <= p.getBounds().getY() + 1;
            
                // Landing/Vertical Collision:
                if ((wasAbove && box.getVelocityY() >= 0) || (box.getCurrentPlatform() == p)) {
                    box.landOn(p); 
                    landed = true;
                } else { // Horizontal Collision (hitting the side)
                    box.handleHorizontalCollision(p);
                }
            }
        }
        
        // Final check for falling off a platform
        if (!landed && box.getVelocityY() > 0) { 
            box.setOnGround(false); 
        }

        if (jumpQueued) { 
            box.jump(); 
        }

        //  Check Win/Lose Conditions and notify the GameManager
        if (door.checkCollision(box.getHitbox())) {
            // Level 1 logic: Win!
            gameController.levelCompleted(getLevelID()); 
        }
        
    }
    
    // ---------------------------------------------------------------------
    // Drawing Logic
    // ---------------------------------------------------------------------
    @Override
    public void draw(Graphics2D g) {
        // Draw level elements (platforms, door, spikes)
        for (Spikes s : spikes) { 
            s.draw(g); 
        } //spikes have to stay hidden
        for (Platform p : platforms) { 
            p.draw(g); 
        }
        door.draw(g);
        
    }
}
