// Level1.java 
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.*;


public class Level1 implements Level {
    // ---------------------------------------------------------------------
    // Level Specific Data (Platforms, Spikes, Door)
    // ---------------------------------------------------------------------
    private ArrayList<Platform> platforms = new ArrayList<>();
    private ArrayList<Spikes> spikes = new ArrayList<>(); 
    private Door door = new Door(0, 100, 50, 80, "door1.PNG"); 
    
    @Override
    public int getLevelID() { 
        return 1; 
    }
    
    // ---------------------------------------------------------------------
    // Level Setup and Reset Logic
    // ---------------------------------------------------------------------
    @Override
    public void init(Player player) {
        // 1. Clear any old level objects before setting up the new level (Crucial for restarts)
        platforms.clear();
        spikes.clear();

        // 2. RESET PLAYER to start position
        player.setPosition(150, 300); 

        // 3. ADD YOUR PLATFORMS
        platforms.add(new Platform(0, 400, 1000, 400)); // Ground
        // platforms.add(new Platform(200, 200, 200, 30)); // floating platform 1
        platforms.add(new Platform(400, 250, 150, 30)); // Floating platform 2
        platforms.add(new Platform(0, 0, 50, 600));      // left wall
        platforms.add(new Platform(940, 0, 50, 600));    // right wall
        
        // 4. ADD YOUR SPIKES (Example: bottom row of spikes)
        int spikeWidth = 50;
        for (int x = 0; x < 1000; x += spikeWidth) {
            spikes.add(new Spikes(x, 508, spikeWidth, 55, "singleSpike.png"));
        }
        
        // 5. SET DOOR position
        door.setPosition(700, 325); 
    }
    
    // ---------------------------------------------------------------------
    // Core Game Loop Logic (Called repeatedly by GamePanel's Timer)
    // ---------------------------------------------------------------------
    @Override
    public void update(float delta, Player box, boolean jumpQueued, GameManager gameController) {
        
        // 1. Update platforms (Platform specific movement logic)
        for (Platform p : platforms) { 
            // Add your old platform movement logic here:
            if (p.getBounds().y == 400 && box.getHitbox().x > 250) {
                p.activateDown();
            }
            p.update(); // Update platform position/state
        } 

        // 2. Update player movement and physics
        box.update(delta);
        
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

        // 4. Handle jump queue (only jumps if box is on the ground, checked internally by box.jump())
        if (jumpQueued) { 
            box.jump(); 
            // Note: GamePanel resets jumpQueued to false in the next iteration.
        }

        // 5. Check Win/Lose Conditions and notify the GameManager
        if (door.checkCollision(box.getHitbox())) {
            // Level 1 logic: Win!
            gameController.levelCompleted(getLevelID()); 
        }
        for (Spikes s : spikes) {
            if (s.checkCollision(box.getHitbox())) {
                // Level 1 logic: Die!
                gameController.levelFailed(); 
            }
        }
    }
    
    // ---------------------------------------------------------------------
    // Drawing Logic
    // ---------------------------------------------------------------------
    @Override
    public void draw(Graphics2D g) {
        // Draw level elements (platforms, door, spikes)
        for (Platform p : platforms) { p.draw(g); }
        door.draw(g);
        for (Spikes s : spikes) { s.draw(g); }
    }
}
